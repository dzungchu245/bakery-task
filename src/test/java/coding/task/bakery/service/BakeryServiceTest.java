package coding.task.bakery.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import coding.task.bakery.data.BakeryData;
import coding.task.bakery.dto.OrderRequest;
import coding.task.bakery.dto.OrderResponse;
import coding.task.bakery.entity.Pack;
import coding.task.bakery.exception.ApiException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class BakeryServiceTest {

	@InjectMocks
	private BakeryService service;

	@Mock
	private BakeryData data;

	private String packCode;
	private int total;
	private List<Pack> packs;
	List<OrderRequest> orders;

	@BeforeEach
	public void init() {
		packCode = "MB11";
		total = 14;
		orders = new ArrayList<>();
		orders.add(new OrderRequest(packCode, total));

		packs = new ArrayList<>();
		packs.add(new Pack(2, 9.95));
		packs.add(new Pack(5, 16.95));
		packs.add(new Pack(8, 24.95));
	}

	@Test
	public void collectPacks() throws ApiException {
		when(data.findByCode(packCode)).thenReturn(packs);
		List<OrderResponse> response = service.collectPacks(orders);
		log.info("Result: {}", response);
		assertEquals(1, response.size());
		assertEquals(packCode, response.get(0).getProductCode());
		int totalQ = response.get(0).getPacks().stream().map(p -> p.getPackSize() * p.getQuantity())
				.mapToInt(Integer::intValue).sum();
		assertEquals(total, totalQ);
	}

	@Test
	public void collectPacks_throwException() {
		when(data.findByCode(packCode)).thenThrow(ApiException.class);
		assertThrows(ApiException.class, () -> {
			service.collectPacks(orders);
		});
	}

	@Test
	public void collectPacks_emptyPacks() throws ApiException {
		when(data.findByCode(packCode)).thenReturn(Collections.emptyList());
		assertThrows(ApiException.class, () -> {
			service.collectPacks(orders);
		});
	}

	@Test
	public void collectPacks_nullPack() throws ApiException {
		when(data.findByCode(packCode)).thenReturn(null);
		assertThrows(ApiException.class, () -> {
			service.collectPacks(orders);
		});
	}
}
