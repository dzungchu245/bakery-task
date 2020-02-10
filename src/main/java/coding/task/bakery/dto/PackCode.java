
package coding.task.bakery.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class PackCode {
	private String code;
	private String name;
	private List<Pack> packs;
}
