package coding.task.bakery.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coding.task.bakery.data.BakeryData;
import coding.task.bakery.dto.OrderPack;
import coding.task.bakery.dto.OrderRequest;
import coding.task.bakery.dto.OrderResponse;
import coding.task.bakery.dto.Pack;
import coding.task.bakery.util.BakeryHelper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BakeryService {

	@Autowired
	private BakeryData bakeryData;

	public List<OrderResponse> collectPacks(List<OrderRequest> orders) {
		return orders.stream().map(this::collectByOrder).collect(Collectors.toList());
	}

	private OrderResponse collectByOrder(OrderRequest od) {
		log.info("order {}", od);
		List<Pack> packList = bakeryData.findByCode(od.getPackCode());
		if (packList != null) {
			// sort by size descending
			BakeryHelper.sortDsc(packList);
			Map<Pack, Integer> minPacks = BakeryHelper.findMinPacks(packList, od.getTotal());
			List<OrderPack> orderPacks = minPacks.entrySet().stream().filter(p -> p.getValue() > 0)
					.map(p -> new OrderPack(p.getValue(), p.getKey().getSize(), p.getKey().getPrice()))
					.collect(Collectors.toList());
			log.info("packs {}", orderPacks);
			return new OrderResponse(od.getPackCode(), od.getTotal(), orderPacks);
		} else {
			return new OrderResponse(od.getPackCode(), od.getTotal(), Collections.emptyList());
		}
	}
}
