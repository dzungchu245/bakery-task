package coding.task.bakery.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class OrderRequest {
	private String productCode;
	private int total;
}
