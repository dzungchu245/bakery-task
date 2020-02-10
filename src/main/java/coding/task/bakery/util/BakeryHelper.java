package coding.task.bakery.util;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import coding.task.bakery.dto.Pack;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BakeryHelper {
	
	private BakeryHelper() {}

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

	/* public static void main(String[] args) {
		Pack p1 = new Pack("VS5", "name", 2, 9.95);
		Pack p2 = new Pack("VS5", "name", 5, 16.95);
		Pack p3 = new Pack("VS5", "name", 8, 24.95);

		log.info(findMinPacks(Arrays.asList(p1, p2, p3), 14).toString());
	}*/

	/**
	 * find minimal number of packs: n1 packs p1 and n2 packs p2 n1 * p1.size + n2 *
	 * p1.size = total return result in a HashMap : key = n1,n2 value = p1.size,
	 * p2.size
	 * 
	 * @param p1    first bakery pack
	 * @param p2    second bakery pack
	 * @param total
	 * @return
	 */
	public static Map<Integer, Integer> findMinPacks(Pack p1, Pack p2, int total) {
		int sizeL = Math.max(p1.getSize(), p2.getSize());
		int sizeS = Math.min(p1.getSize(), p2.getSize());
		int maxCountL = total / sizeL;

		int minTotalPack = Integer.MAX_VALUE;
		Map<Integer, Integer> result = new HashMap<>();
		for (int countL = maxCountL; countL >= 0; countL--) {
			int totalRemain = total - countL * sizeL;
			if (totalRemain % sizeS == 0) {
				int countS = totalRemain / sizeS;
				int totalPack = countS + countL;
				if (totalPack < minTotalPack) {
					minTotalPack = totalPack;
					result.put(sizeL, countL);
					result.put(sizeS, countS);
				}
			}
		}
		return result;
	}

	/**
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
