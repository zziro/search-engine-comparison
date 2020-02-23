package com.cignium.searchengine.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HightestUtil {

	public static Long getHighestResult(List<Long> list) {
		if (list == null || list.size() == 0) {
			throw new IllegalArgumentException("List has no value");
		}

		List<Long> sortedList = new ArrayList<Long>(list);

		Collections.sort(sortedList);
		return sortedList.get(sortedList.size() - 1);
	}

	public static Long getTotalHighest(Long firstValue, Long secondValue, Long thirdValue) {
		return (firstValue > secondValue) ? (firstValue > thirdValue ? firstValue : thirdValue)
				: (secondValue > thirdValue ? secondValue : thirdValue);
	}
}
