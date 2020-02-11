package coding.task.bakery.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import coding.task.bakery.dto.OrderPack;
import coding.task.bakery.dto.OrderRequest;
import coding.task.bakery.dto.OrderResponse;
import coding.task.bakery.exception.ApiException;
import coding.task.bakery.exception.ResponseWrapper;
import coding.task.bakery.service.BakeryService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = BakeryController.class)
@AutoConfigureMockMvc
public class BakeryControllerTest {

	@MockBean
	private BakeryService service;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;

	private List<OrderRequest> request;
	private List<OrderResponse> response;

	@BeforeEach
	public void init() {
		initMockData();
	}

	private void initMockData() {
		request = new ArrayList<>();
		request.add(new OrderRequest("MB11", 14));
		response = new ArrayList<>();
		List<OrderPack> packs = new ArrayList<>();
		packs.add(new OrderPack(3, 2, 9.95));
		packs.add(new OrderPack(1, 8, 24.95));
		response.add(new OrderResponse("MB11", 14, packs));
	}

	@Test
	public void collectPacks() throws Exception {
		when(service.collectPacks(request)).thenReturn(response);
		MockHttpServletResponse mvcRes = mockMvc
				.perform(MockMvcRequestBuilders.post("/collect-packs").contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(request)).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn().getResponse();
		int status = mvcRes.getStatus();
		assertEquals(HttpStatus.OK.value(), status);
		String content = mvcRes.getContentAsString();
		log.info("Response: {}", content);
	}

	@Test
	public void collectPacks_invalid() throws Exception {
		when(service.collectPacks(request)).thenThrow(ApiException.class);
		mockMvc.perform(MockMvcRequestBuilders.post("/collect-packs").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(request)).accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void collectPacks_notreadable() throws Exception {
		MockHttpServletResponse mvcRes = mockMvc.perform(MockMvcRequestBuilders.post("/collect-packs")
				.contentType(MediaType.APPLICATION_JSON).content("invalid").accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn().getResponse();
		int status = mvcRes.getStatus();
		assertEquals(HttpStatus.BAD_REQUEST.value(), status);
		String content = mvcRes.getContentAsString();
		ResponseWrapper resWrapper = mapper.readValue(content, ResponseWrapper.class);
		assertEquals(resWrapper.getError(), ApiException.INVALID_REQUEST);
	}
}
