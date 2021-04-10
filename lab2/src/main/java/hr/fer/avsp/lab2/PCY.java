package hr.fer.avsp.lab2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class PCY {

	public static void main(String[] args) throws IOException {
		task();
	}

	private static void task() throws IOException {
		int basketsCount, threshold, bucketsCount;
		double s;
		List<List<Integer>> baskets = new ArrayList<>();

		try (BufferedReader r = new BufferedReader(new InputStreamReader(System.in))) {
			basketsCount = Integer.parseInt(r.readLine());
			s = Double.parseDouble(r.readLine());
			bucketsCount = Integer.parseInt(r.readLine());
			threshold = (int) Math.floor(basketsCount * s);

			while (basketsCount > 0) {
				String line = r.readLine();

				List<Integer> items = Stream.of(line.split("\\s+"))
						.mapToInt(Integer::parseInt)
						.boxed()
						.collect(Collectors.toList());

				baskets.add(items);
				basketsCount--;
			}
		}

		// count items
		Set<Integer> items = new HashSet<>();
		for (List<Integer> basket : baskets) {
			basket.forEach(it -> items.add(it));
		}
		int itemsCount = items.size();

		int[] itemsCounts = new int[itemsCount + 1];

		// first pass - counting items
		for (List<Integer> basket : baskets) {
			basket.forEach(it -> itemsCounts[it]++);
		}

		// second pass - frequent items buckets
		int[] buckets = new int[bucketsCount];

		for (List<Integer> basket : baskets) {
			for (int ii = 0; ii < basket.size() - 1; ii++) {
				int i = basket.get(ii);
				for (int jj = ii + 1; jj < basket.size(); jj++) {
					int j = basket.get(jj);
					if (itemsCounts[i] >= threshold && itemsCounts[j] >= threshold) {
						int h = (i * itemsCount + j) % bucketsCount;
						buckets[h]++;
					}
				}
			}
		}

		// third pass - getting frequent pairs
		Map<PairInt, Integer> pairsMap = new HashMap<>();

		for (List<Integer> basket : baskets) {
			for (int ii = 0; ii < basket.size() - 1; ii++) {
				int i = basket.get(ii);
				for (int jj = ii + 1; jj < basket.size(); jj++) {
					int j = basket.get(jj);
					if (itemsCounts[i] >= threshold && itemsCounts[j] >= threshold) {
						int h = (i * itemsCount + j) % bucketsCount;
						if (buckets[h] >= threshold) {
							var pair = new PairInt(i, j);
							pairsMap.merge(pair, 1, (old, def) -> old + 1);
						}
					}
				}
			}
		}

		// frequent items
		int m = (int) IntStream.of(itemsCounts)
				.filter(c -> c >= threshold)
				.count();

		int aPrioriResult = (m * (m - 1)) / 2;

		System.out.println(aPrioriResult);
		System.out.println(pairsMap.size());

		List<Integer> counters = new ArrayList<Integer>(pairsMap.values());
		counters.sort((a, b) -> b.compareTo(a));
		counters.forEach(System.out::println);
	}

	private static class PairInt {
		int item1;
		int item2;

		public PairInt(int item1, int item2) {
			super();
			this.item1 = item1;
			this.item2 = item2;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + item1;
			result = prime * result + item2;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			PairInt other = (PairInt) obj;
			if (item1 != other.item1)
				return false;
			if (item2 != other.item2)
				return false;
			return true;
		}

	}

}
