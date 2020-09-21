package com.javarush.task.task33.task3310.strategy;

import java.util.Objects;

public class FileStorageStrategy implements StorageStrategy {
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final long DEFAULT_BUCKET_SIZE_LIMIT = 10_000;
    private FileBucket[] table = new FileBucket[DEFAULT_INITIAL_CAPACITY];
    private int size;
    private long bucketSizeLimit = DEFAULT_BUCKET_SIZE_LIMIT;
    private long maxBucketSize;

    public long getBucketSizeLimit() {
        return bucketSizeLimit;
    }

    public void setBucketSizeLimit(long bucketSizeLimit) {
        this.bucketSizeLimit = bucketSizeLimit;
    }

    private int hash(Long k) {
        int h;
        return (k == null) ? 0 : (h = k.hashCode()) ^ (h >>> 16);
    }

    private int indexFor(int hash, int length) {
        return hash & (length - 1);
    }

    private Entry getEntry(Long key) {
        int hash = hash(key);
        int length = table.length;
        int bucketIndex = indexFor(hash, length);
        Entry first;
        Long k;
        Entry e;
        FileBucket fb = table[bucketIndex];

        if (fb != null && (first = fb.getEntry()) != null) {
            if (first.hash == hash &&
                    ((k = first.key) == key || (key != null && key.equals(k))))
                return first;
            if ((e = first.next) != null) {
                do {
                    if (e.hash == hash &&
                            ((k = e.key) == key || (key != null && key.equals(k))))
                        return e;
                } while ((e = e.next) != null);
            }
        }
        return null;
    }


    private void transfer(FileBucket[] newTable) {
        int newCapacity = newTable.length;
        for (FileBucket fb : table) {
            Entry e = fb.getEntry();
            while (null != e) {
                Entry next = e.next;
                int i = indexFor(e.hash, newCapacity);
                e.next = newTable[i].getEntry();
                newTable[i].putEntry(e);
                e = next;
            }
            fb.remove();
        }
    }

    private void resize(int newCapacity) {
        FileBucket[] newTable = new FileBucket[newCapacity];
        transfer(newTable);
        table = newTable;
    }

    private void createEntry(int hash, Long key, String value, int bucketIndex) {
        FileBucket fb = table[bucketIndex];
        Entry e = null;
        if (fb == null) {
            table[bucketIndex] = new FileBucket();
        } else {
            e = fb.getEntry();
        }
        table[bucketIndex].putEntry(new Entry(hash, key, value, e));
        size++;
    }

    private void addEntry(int hash, Long key, String value, int bucketIndex) {
        if ((null != table[bucketIndex]) && (table[bucketIndex].getFileSize() > bucketSizeLimit)) {
            resize(2 * table.length);
            hash = hash(key);
            bucketIndex = indexFor(hash, table.length);
        }

        createEntry(hash, key, value, bucketIndex);
    }

    @Override
    public boolean containsKey(Long key) {
        return getEntry(key) != null;
    }

    @Override
    public boolean containsValue(String value) {
        return containsKey(getKey(value));
    }

    @Override
    public void put(Long key, String value) {
        int hash = hash(key);
        int bucketIndex = indexFor(hash, table.length);
        FileBucket fb = table[bucketIndex];
        if (fb != null) {
            for (Entry e = fb.getEntry(); e != null; e = e.next) {
                Object k;
                if (e.hash == hash && ((k = e.key) == key || key.equals(k))) {
                    e.value = value;
                    return;
                }
            }
        }
        addEntry(hash, key, value, bucketIndex);
    }

    @Override
    public Long getKey(String value) {
        for (FileBucket fb : table) {
                Entry e;
                if (fb != null && (e = fb.getEntry()) != null) {
                    do {
                        if (Objects.equals(value, e.getValue()))
                            return e.getKey();
                    } while ((e = e.next) != null);
                }
        }
        return null;
    }

    @Override
    public String getValue(Long key) {
        Entry e;
        return (e = getEntry(key)) == null ? null : e.value;
    }
}
