package coding.task.bakery.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class OrderPack {
	private int quantity;
	private int packSize;
	private double packPrice;
}
