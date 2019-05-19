package com.javarush.task.task37.task3707;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

public class AmigoSet<E> extends AbstractSet<E> implements Serializable, Cloneable, Set<E> {
    private static final Object PRESENT = new Object();

    private transient HashMap<E, Object> map;

    public AmigoSet() {
        map = new HashMap<>();
    }

    public AmigoSet(Collection<? extends E> collection) {
        int capacity = Math.max(16, (int) ((collection.size()/.75f) + 1));
        this.map = new HashMap<>(capacity);
        addAll(collection);
    }

    @Override
    public Iterator iterator() {
        return map.keySet().iterator();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return map.keySet().contains(o);
    }

    @Override
    public boolean add(E e) {
        return map.put(e, PRESENT) == null;
    }

    @Override
    public boolean remove(Object o) {
        return map.keySet().remove(o);
    }

    @Override
    public void clear() {
        map.clear();
    }


    @Override
    public Object clone() {
        try {
            AmigoSet<E> result = result = (AmigoSet<E>) super.clone();
            result.map = (HashMap<E, Object>) map.clone();
            return result;
        } catch (Exception e) {
            throw new InternalError(e);
        }
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(this);
        out.writeFloat(HashMapReflectionHelper.callHiddenMethod(map, "loadFactor"));
        out.writeInt(HashMapReflectionHelper.callHiddenMethod(map, "capacity"));
        out.writeInt(size());
        for (E elem : map.keySet()) {
            out.writeObject(elem);
        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        AmigoSet<E> res = (AmigoSet<E>) in.readObject();
        float loadFactor = in.readFloat();
        int capacity = in.readInt();
        res.map = new HashMap<>(capacity, loadFactor);
        int size = in.readInt();
        for (int i = 0; i < size; i++) {
            map.put((E) in.readObject(), PRESENT);
        }
    }

}
