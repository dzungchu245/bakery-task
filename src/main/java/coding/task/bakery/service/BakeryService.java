package coding.task.bakery.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coding.task.bakery.data.BakeryData;
import coding.task.bakery.dto.OrderPack;
import coding.task.bakery.dto.OrderRequest;
import coding.task.bakery.dto.OrderResponse;
import coding.task.bakery.entity.Pack;
import coding.task.bakery.exception.ApiException;
import coding.task.bakery.util.BakeryHelper;

@Service
public class BakeryService {

	@Autowired
	private BakeryData bakeryData;

	public List<OrderResponse> collectPacks(List<OrderRequest> orders) {
		return orders.stream().map(this::collectByOrder).collect(Collectors.toList());
	}

	private OrderResponse collectByOrder(OrderRequest od) {
		List<Pack> packList = bakeryData.findByCode(od.getProductCode());
		if (packList != null && !packList.isEmpty()) {
			// sort by size descending
			BakeryHelper.sortDsc(packList);
			Map<Pack, Integer> minPacks = BakeryHelper.findMinPacks(packList, od.getTotal());
			List<OrderPack> orderPacks = minPacks.entrySet().stream().filter(p -> p.getValue() > 0)
					.map(p -> new OrderPack(p.getValue(), p.getKey().getSize(), p.getKey().getPrice()))
					.collect(Collectors.toList());
			return new OrderResponse(od.getProductCode(), od.getTotal(), orderPacks);
		} else {
			throw new ApiException(ApiException.NO_PACK_FOUND);
		}
	}
}
