package java.algorithm;

public interface ICacheAlgorithm <K,V>{
    V putElement(K key, V value);
    V getElement(K key);
    void removeElement(K key);
}
