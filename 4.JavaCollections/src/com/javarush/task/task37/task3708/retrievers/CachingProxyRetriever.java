package com.javarush.task.task37.task3708.retrievers;

import com.javarush.task.task37.task3708.cache.LRUCache;
import com.javarush.task.task37.task3708.storage.Storage;

public class CachingProxyRetriever implements Retriever {
    OriginalRetriever originalRetriever;
    LRUCache lruCache = new LRUCache(5);

    public CachingProxyRetriever(Storage storage) {
        this.originalRetriever = new OriginalRetriever(storage);
    }

    @Override
    public Object retrieve(long id) {
        Object retrievedObject = lruCache.find(id);
        if (retrievedObject == null) {
            retrievedObject = originalRetriever.retrieve(id);
            lruCache.set(id, retrievedObject);
        }
        return retrievedObject;
    }
}
