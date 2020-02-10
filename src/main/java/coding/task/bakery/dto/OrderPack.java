package coding.task.bakery.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class OrderPack {
	private int quantity;
	private int packSize;
	private double packPrice;
}
