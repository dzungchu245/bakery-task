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
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BakeryService {

	@Autowired
	private BakeryData bakeryData;

	/**
	 * collect packs for all order request
	 * 
	 * @param orders
	 * @return
	 */
	public List<OrderResponse> collectPacks(List<OrderRequest> orders) {
		log.debug("Collect packs for all order request");
		return orders.stream().map(this::collectByOrder).collect(Collectors.toList());
	}

	/**
	 * collect pack for one order request
	 * 
	 * @param od
	 * @return
	 */
	private OrderResponse collectByOrder(OrderRequest od) {
		log.debug("Order request {}", od);
		List<Pack> packList = bakeryData.findByCode(od.getProductCode());
		if (packList != null && !packList.isEmpty()) {
			// sort by size descending
			BakeryHelper.sortDsc(packList);
			// find minimal number of packs
			Map<Pack, Integer> minPackMap = BakeryHelper.findMinPacks(packList, od.getTotal());
			List<OrderPack> orderPacks = minPackMap.entrySet().stream().filter(p -> p.getValue() > 0)
					.map(p -> new OrderPack(p.getValue(), p.getKey().getSize(), p.getKey().getPrice()))
					.collect(Collectors.toList());
			log.debug("Matched packs found {}", orderPacks);
			return new OrderResponse(od.getProductCode(), od.getTotal(), orderPacks);
		} else {
			log.debug("No pack found");
			throw new ApiException(ApiException.NO_PACK_FOUND);
		}
	}
}
