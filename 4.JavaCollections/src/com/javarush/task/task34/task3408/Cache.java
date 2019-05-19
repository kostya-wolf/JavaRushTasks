package com.javarush.task.task34.task3408;

        import java.lang.reflect.Method;
        import java.util.Map;
        import java.util.WeakHashMap;

public class Cache<K, V> {
    private Map<K, V> cache = new WeakHashMap<>();

    public V getByKey(K key, Class<V> clazz) throws Exception {
        V object = cache.get(key);
        if (object == null) {
            object = clazz.getConstructor(key.getClass()).newInstance(key);
            cache.put(key, object);
        }
        return object;
    }

    public boolean put(V obj) {
        try {
            Method method = obj.getClass().getDeclaredMethod("getKey");
            method.setAccessible(true);
            K key = (K) method.invoke(obj);
            cache.put(key, obj);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public int size() {
        return cache.size();
    }
}
