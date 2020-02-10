package coding.task.bakery.service;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import coding.task.bakery.data.BakeryData;
import coding.task.bakery.dto.OrderRequest;
import coding.task.bakery.dto.OrderResponse;
import coding.task.bakery.dto.Pack;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
public class BakeryServiceTest {

	@InjectMocks
	private BakeryService service;

	@Mock
	private BakeryData data;
	
	@Test
	public void collectPacks() {
		String packCode = "MB11";
		Integer total = 14;
		List<OrderRequest> orders = createMockOrders(packCode, total);

		List<Pack> packs = new ArrayList<>();
		packs.add(new Pack(2, 9.95));
		packs.add(new Pack(5, 16.95));
		packs.add(new Pack(8, 24.95));

		when(data.findByCode(packCode)).thenReturn(packs);
		List<OrderResponse> response = service.collectPacks(orders);
		log.info("Result: {}", response);
		assertEquals(1, response.size());
		assertEquals(packCode, response.get(0).getPackCode());
		assertEquals(total, response.get(0).getPacks().stream().map(p -> p.getPackSize() * p.getQuantity())
				.mapToInt(Integer::intValue).sum());

	}

	private List<OrderRequest> createMockOrders(String packCode, Integer total) {
		List<OrderRequest> orders = new ArrayList<>();
		orders.add(new OrderRequest(packCode, total));
		return orders;
	}
	
	@Test
	public void collectPacks_notFound() {
		String packCode = "MB11";
		Integer total = 14;
		List<OrderRequest> orders = createMockOrders(packCode, total);

		when(data.findByCode(packCode)).thenReturn(null);
		List<OrderResponse> response = service.collectPacks(orders);
		log.info("Result: {}", response);
		assertEquals(1, response.size());
		assertEquals(packCode, response.get(0).getPackCode());
		assertTrue(response.get(0).getPacks().isEmpty());
	}
}
