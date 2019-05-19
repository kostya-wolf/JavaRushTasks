package com.javarush.task.task35.task3509;

import java.util.*;


/* 
Collections & Generics
*/
public class Solution {

    public static void main(String[] args) {
    }

    public static <T> ArrayList<T> newArrayList(T... elements) {
        ArrayList<T> result = new ArrayList<>();
        for (T elem : elements) {
            result.add(elem);
        }
        return result;
    }

    public static <T> HashSet<T> newHashSet(T... elements) {
        HashSet<T> result = new HashSet<T>();
        for (T elem : elements) {
            result.add(elem);
        }
        return result;
    }

    public static <K, V> HashMap<K, V> newHashMap(List<? extends K> keys, List<? extends V> values) {
        HashMap<K, V> result = new HashMap<>();
        if (keys.size() != values.size()) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < keys.size(); i++) {
            result.put(keys.get(i), values.get(i));
        }
        return result;
    }
}
