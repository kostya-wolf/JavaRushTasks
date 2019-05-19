package com.javarush.task.task20.task2028;

import java.io.Serializable;
import java.util.*;

/* 
Построй дерево(1)
*/
public class CustomTree extends AbstractList<String> implements Cloneable, Serializable {
    Entry<String> root;

    private int size;

    public CustomTree() {
        this.root = new Entry<>("roooooooot");
        this.root.lineNumber = 0;
        this.size = 0;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean add(String s) {
        Entry<String> parent = getNodeToAdd();
        Entry<String> entry = new Entry<>(s);
        entry.parent = parent;
        entry.lineNumber = parent.lineNumber + 1;
        if (parent.availableToAddLeftChildren) {
            parent.leftChild = entry;
        } else {
            parent.rightChild = entry;
        }
        size++;
        return true;
    }

    private Entry<String> getNodeToAdd() {
        Entry<String> entry = null;

        Queue<Entry<String>> queue = new ArrayDeque<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            entry = queue.poll();
            entry.checkChildren();
            if (entry.isAvailableToAddChildren()) {
                return entry;
            } else {
                queue.add(entry.leftChild);
                queue.add(entry.rightChild);
            }
        }
        return entry;
    }

    public String getParent(String s) {
        Entry<String> entry = getNodeByName(s);
        if (entry != null) {
            return entry.parent != null ? entry.parent.elementName : null;
        }
        return null;
    }

    private Entry<String> getNodeByName(String name) {
        Queue<Entry<String>> queue = new ArrayDeque<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Entry<String> entry = queue.poll();
            if (entry.elementName.equals(name)) {
                return entry;
            } else {
                if (entry.leftChild != null) {
                    queue.add(entry.leftChild);
                }
                if (entry.rightChild != null) {
                    queue.add(entry.rightChild);
                }
            }
        }
        return null;
    }

    @Override
    public boolean remove(Object o) {
        if (!(o instanceof String)) {
            throw new UnsupportedOperationException();
        }
        Entry<String> entry = getNodeByName((String) o);
        if (entry == null) {
            return false;
        }
        if (entry == root) {
            size = 0;
            root = null;
        } else {
            size = size - sizeFromNode(entry);

            Entry<String> parent = entry.parent;
            if (parent.leftChild == entry) {
                parent.leftChild = null;
            } else {
                parent.rightChild = null;
            }
        }
        return true;
    }

    private int sizeFromNode(Entry<String> node) {
        int result = 1;
        Entry<String> entry = null;
        Queue<Entry<String>> queue = new ArrayDeque<>();
        queue.add(node);
        while (!queue.isEmpty()) {
            entry = queue.poll();
            if (entry.leftChild != null) {
                queue.add(entry.leftChild);
                result++;
            }
            if (entry.rightChild != null) {
                queue.add(entry.rightChild);
                result++;
            }
        }
        return result;
    }

    @Override
    public String get(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String set(int index, String element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, String element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends String> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    static class Entry<T> implements Serializable {
        String elementName;
        int lineNumber;
        boolean availableToAddLeftChildren;
        boolean availableToAddRightChildren;
        Entry<T> parent;
        Entry<T> leftChild;
        Entry<T> rightChild;

        public Entry(String elementName) {
            this.elementName = elementName;
            this.availableToAddLeftChildren = true;
            this.availableToAddRightChildren = true;
        }

        void checkChildren() {
            this.availableToAddLeftChildren = leftChild == null;
            this.availableToAddRightChildren = rightChild == null;
        }

        boolean isAvailableToAddChildren() {
            return availableToAddLeftChildren || availableToAddRightChildren;
        }
    }
}
