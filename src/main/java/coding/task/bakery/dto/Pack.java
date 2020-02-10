package coding.task.bakery.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class Pack {
	//private String code;
	//private String name;
	private int size;
	private double price;
}
