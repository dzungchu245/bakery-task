package coding.task.bakery.util;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import coding.task.bakery.entity.Pack;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BakeryHelper {

	private BakeryHelper() {
	}

	public static void sortDsc(List<Pack> packList) {
		packList.sort(new Comparator<Pack>() {
			@Override
			public int compare(Pack o1, Pack o2) {
				return new Integer(o2.getSize()).compareTo(o1.getSize());
			}
		});
	}

	public static double roundNumber(double value) {
		return Math.round(value * 100) / 100.00;
	}

	/**
	 * find minimal number of packs by given total quantity
	 * 
	 * @param packList
	 * @param total
	 * @return
	 */
	public static Map<Pack, Integer> findMinPacks(List<Pack> packList, int total) {
		Map<Pack, Integer> result = new HashMap<>();
		if (packList.size() == 1) {
			Pack pack = packList.get(0);
			int size = pack.getSize();
			if (total % size == 0) {
				result.put(pack, total / size);
			}
			return result;
		} else {
			log.info(packList.toString());
			Pack packMax = packList.get(0);
			int sizeMax = packMax.getSize();
			int minTotalPack = Integer.MAX_VALUE;
			int maxQuantity = total / sizeMax;
			for (int count = maxQuantity; count >= 0; count--) {
				int totalRemain = total - count * sizeMax;
				Map<Pack, Integer> minPacks = findMinPacks(packList.subList(1, packList.size()), totalRemain);
				if (!minPacks.isEmpty()) {
					int totalPack = count + minPacks.values().stream().mapToInt(Integer::intValue).sum();
					if (totalPack < minTotalPack) {
						minTotalPack = totalPack;
						result.put(packMax, count);
						result.putAll(minPacks);
					}
				}
			}
			return result;
		}
	}
}
