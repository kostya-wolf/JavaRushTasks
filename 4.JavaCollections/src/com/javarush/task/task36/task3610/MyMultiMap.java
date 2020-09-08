package com.javarush.task.task36.task3610;

import java.io.Serializable;
import java.util.*;

public class MyMultiMap<K, V> extends HashMap<K, V> implements Cloneable, Serializable {
    static final long serialVersionUID = 123456789L;
    private HashMap<K, List<V>> map;
    private int repeatCount;

    public MyMultiMap(int repeatCount) {
        this.repeatCount = repeatCount;
        map = new HashMap<>();
    }

    @Override
    public int size() {
        if (map.isEmpty()) return 0;
        int result = 0;
        for (List<V> list : map.values()) {
            result += list.size();
        }
        return result;
    }

    @Override
    public V put(K key, V value) {
        if (!map.containsKey(key)) {
            List<V> arrayList = new ArrayList<>();
            arrayList.add(value);
            map.put(key, arrayList);
            return null;
        }
        List<V> list = map.get(key);
        if (list.size() >= repeatCount) {
            list.remove(0);
        }
        list.add(value);
        return list.get(list.size() - 2);
    }

    @Override
    public V remove(Object key) {
        List<V> list = map.get(key);
        if (list == null) {
            return null;
        }
        V result = list.remove(0);
        if (list.isEmpty()) {
            map.remove(key, list);
        }
        return result;
    }

    @Override
    public Set<K> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<V> values() {
        List<V> result = new ArrayList<>();
        for (List<V> list : map.values()) {
            result.addAll(list);
        }
        return result;
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        for (List<V> list : map.values()) {
            if (list.contains(value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        for (Map.Entry<K, List<V>> entry : map.entrySet()) {
            sb.append(entry.getKey());
            sb.append("=");
            for (V v : entry.getValue()) {
                sb.append(v);
                sb.append(", ");
            }
        }
        String substring = sb.substring(0, sb.length() - 2);
        return substring + "}";
    }
}