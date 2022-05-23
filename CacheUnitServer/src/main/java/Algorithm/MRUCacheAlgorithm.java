package main.java.Algorithm;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MRUCacheAlgorithm<K,V> implements ICacheAlgorithm<K,V>{
    private final int size;
    private List<K> usageOrder;
    private Map<K, V> cache;

    public MRUCacheAlgorithm(int size) {
        this.size = size;
        usageOrder = new LinkedList<>();
        cache = new HashMap<>();
    }

    @Override
    public V putElement(K key, V value) {
        // if cache contains the page we want to add:
        // 1. remove page from linked list and map
        // 2. add to the beginning as most recently used
        if(cache.containsKey(key)) {
            removeElement(key);
        }
        // if the cache is full - remove the most recently used page
        else if(cache.size() == size) {
            K removedKey = usageOrder.remove(size - 1);
            cache.remove(removedKey);
        }

        usageOrder.add(key);
        cache.put(key, value);
        return cache.get(key);
    }

    @Override
    public V getElement(K key) {
        V value = null;

        if(cache.containsKey(key)) {
            value = cache.get(key);
            value = putElement(key, value);
        }
        return value;
    }

    @Override
    public void removeElement(K key) {
        cache.remove(key);
        usageOrder.remove(key);
    }
}
