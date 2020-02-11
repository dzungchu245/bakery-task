package coding.task.bakery.data;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import coding.task.bakery.entity.Pack;
import coding.task.bakery.entity.PackCode;
import coding.task.bakery.exception.ApiException;

@Component
public class BakeryData {

	private static List<PackCode> allPacks = new ArrayList<>();
	static {
		List<Pack> packs = new ArrayList<>();
		packs.add(new Pack(3, 6.99));
		packs.add(new Pack(5, 8.99));
		allPacks.add(new PackCode("VS5", "Vegemite Scroll", packs));
		packs = new ArrayList<>();
		packs.add(new Pack(2, 9.95));
		packs.add(new Pack(5, 16.95));
		packs.add(new Pack(8, 24.95));
		allPacks.add(new PackCode("MB11", "Blueberry Muffin", packs));
		packs = new ArrayList<>();
		packs.add(new Pack(3, 5.95));
		packs.add(new Pack(5, 9.95));
		packs.add(new Pack(9, 16.99));
		allPacks.add(new PackCode("CF", "Croissant", packs));
	}

	public List<Pack> findByCode(String code) throws ApiException {
		if (StringUtils.isEmpty(code)) {
			throw new ApiException(ApiException.INVALID_PACKCODE);
		}
		return allPacks.stream().filter(p -> code.equals(p.getCode())).findFirst().map(PackCode::getPacks)
				.orElseThrow(() -> new ApiException(ApiException.INVALID_PACKCODE));
	}

}
