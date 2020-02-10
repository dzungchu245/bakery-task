package coding.task.bakery.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import coding.task.bakery.util.BakeryHelper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class OrderResponse {
	private String packCode;
	private int total;
	private List<OrderPack> packs;

	public double getTotalPrice() {
		return packs != null ? BakeryHelper.roundNumber(
				packs.stream().map(p -> p.getPackPrice() * p.getQuantity()).mapToDouble(Double::doubleValue).sum())
				: 0.0;
	}
}
