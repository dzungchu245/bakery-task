package coding.task.bakery.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import coding.task.bakery.util.BakeryHelper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class OrderResponse {
	private String packCode;
	private int total;
	private List<OrderPack> packs;

	public double getTotalPrice() {
		return (packs == null && packs.isEmpty()) ? 0.0
				: BakeryHelper.roundNumber(packs.stream().map(p -> p.getPackPrice() * p.getQuantity())
						.mapToDouble(Double::doubleValue).sum());
	}
}
