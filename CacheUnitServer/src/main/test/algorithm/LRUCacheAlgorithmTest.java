package test.algorithm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.algorithm.LRUCacheAlgorithm;

import static org.junit.jupiter.api.Assertions.*;

class LRUCacheAlgorithmTest {
    LRUCacheAlgorithm<Integer, String> lruCacheAlgorithm;

    @BeforeEach
    public void init() {
        lruCacheAlgorithm = new LRUCacheAlgorithm<>(5);

        lruCacheAlgorithm.putElement(1, "A");
        lruCacheAlgorithm.putElement(2, "B");
        lruCacheAlgorithm.putElement(3, "C");
        lruCacheAlgorithm.putElement(4, "D");
        lruCacheAlgorithm.putElement(5, "E");
    }

    @Test
    public void LatestElementValidationTest() {
        assertEquals("A", lruCacheAlgorithm.getElement(1));
    }

    @Test
    public void InsertNewValidElementTest() {
        lruCacheAlgorithm.putElement(6, "F");

        assertNull(lruCacheAlgorithm.getElement(1));
        assertEquals("F", lruCacheAlgorithm.getElement(6));
    }

    @Test
    public void RemoveElementFromCacheTest() {
        lruCacheAlgorithm.removeElement(1);

        assertNull(lruCacheAlgorithm.getElement(1));
    }

    @Test
    public void UpdatePageContentTest() {
        lruCacheAlgorithm.putElement(4, "H");

        assertNotEquals("D", lruCacheAlgorithm.getElement(4));
        assertEquals("H", lruCacheAlgorithm.getElement(4));
    }
}