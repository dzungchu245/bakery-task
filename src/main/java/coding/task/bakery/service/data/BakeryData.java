package coding.task.bakery.service.data;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import coding.task.bakery.dto.Pack;
import coding.task.bakery.dto.PackCode;

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

	public List<Pack> findByCode(String code) {
		PackCode packCode = allPacks.stream().filter(p -> code.equals(p.getCode())).findFirst().orElse(null);
		return packCode != null ? packCode.getPacks() : null;
	}

}
