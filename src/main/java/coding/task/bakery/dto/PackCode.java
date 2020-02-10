
package coding.task.bakery.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PackCode {
	private String code;
	private String name;
	private List<Pack> packs;
}
