package com.zlp.common.util;

public class CacheUtil {
    /**
     * 生成排序索引key
     */
    public static synchronized String createSortedListKey(String conditionId, String sortName, String sortOrder) {
        String sortedListKey = conditionId + ":" + sortName + ":" + sortOrder;
        return sortedListKey;
    }
}
