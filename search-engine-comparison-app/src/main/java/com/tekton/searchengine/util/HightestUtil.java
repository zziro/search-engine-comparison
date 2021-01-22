package com.tekton.searchengine.util;

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

    public static Long getTotalHighestWithTwoParameters(Long firstValue, Long secondValue) {

        if (firstValue == null || secondValue == null) {
            throw new IllegalArgumentException("Invalid input");
        }
        if (firstValue > secondValue) {
            return firstValue;
        } else {
            return secondValue;
        }

    }

    public static Long getTotalHighestWithThreeParameters(Long firstValue, Long secondValue, Long thirdValue) {
        if (firstValue == null || secondValue == null || thirdValue == null) {
            throw new IllegalArgumentException("Invalid input");
        }
        return (firstValue > secondValue) ? (firstValue > thirdValue ? firstValue : thirdValue)
                : (secondValue > thirdValue ? secondValue : thirdValue);
    }
}
