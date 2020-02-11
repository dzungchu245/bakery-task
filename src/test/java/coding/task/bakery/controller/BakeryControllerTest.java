package coding.task.bakery.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import coding.task.bakery.dto.OrderPack;
import coding.task.bakery.dto.OrderRequest;
import coding.task.bakery.dto.OrderResponse;
import coding.task.bakery.exception.ApiException;
import coding.task.bakery.exception.ResponseWrapper;
import coding.task.bakery.exception.RestExceptionHandler;
import coding.task.bakery.service.BakeryService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
public class BakeryControllerTest {

	@InjectMocks
	private BakeryController controller;

	@Mock
	private BakeryService service;

	private MockMvc mockMvc;

	private ObjectMapper mapper = new ObjectMapper();

	private List<OrderRequest> request;
	private List<OrderResponse> response;

	@Before
	public void init() {
		initMockData();
		mockMvc = MockMvcBuilders.standaloneSetup(controller).setControllerAdvice(new RestExceptionHandler()).build();
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
		String jsonReq = mapper.writeValueAsString(request);
		MockHttpServletResponse mvcRes = mockMvc.perform(MockMvcRequestBuilders.post("/collect-packs")
				.contentType(MediaType.APPLICATION_JSON).content(jsonReq).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn().getResponse();
		int status = mvcRes.getStatus();
		assertEquals(HttpStatus.OK.value(), status);
		String content = mvcRes.getContentAsString();
		log.info("Response: {}", content);
		assertEquals(mapper.writeValueAsString(response), content);
	}
	
	@Test
	public void collectPacks_invalid() throws Exception {
		when(service.collectPacks(request)).thenThrow(ApiException.class);
		String jsonReq = mapper.writeValueAsString(request);
		MockHttpServletResponse mvcRes = mockMvc.perform(MockMvcRequestBuilders.post("/collect-packs")
				.contentType(MediaType.APPLICATION_JSON).content(jsonReq).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn().getResponse();
		int status = mvcRes.getStatus();
		assertEquals(HttpStatus.BAD_REQUEST.value(), status);
		String content = mvcRes.getContentAsString();
		log.info("Invalid Response: {}", content);
	}
	
	@Test
	public void collectPacks_notreadable() throws Exception {
		String jsonReq = "invalid";
		MockHttpServletResponse mvcRes = mockMvc.perform(MockMvcRequestBuilders.post("/collect-packs")
				.contentType(MediaType.APPLICATION_JSON).content(jsonReq).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn().getResponse();
		int status = mvcRes.getStatus();
		assertEquals(HttpStatus.BAD_REQUEST.value(), status);
		String content = mvcRes.getContentAsString();
		ResponseWrapper resWrapper = mapper.readValue(content, ResponseWrapper.class);
		assertEquals(resWrapper.getError(), ApiException.INVALID_REQUEST);
	}
}
