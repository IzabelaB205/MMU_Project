package test.algorithm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.algorithm.MRUCacheAlgorithm;

import static org.junit.jupiter.api.Assertions.*;

class MRUCacheAlgorithmTest {
    MRUCacheAlgorithm<Integer, String> mruCacheAlgorithm;

    @BeforeEach
    public void init() {
        mruCacheAlgorithm = new MRUCacheAlgorithm<>(5);

        mruCacheAlgorithm.putElement(1, "A");
        mruCacheAlgorithm.putElement(2, "B");
        mruCacheAlgorithm.putElement(3, "C");
        mruCacheAlgorithm.putElement(4, "D");
        mruCacheAlgorithm.putElement(5, "E");
    }

    @Test
    public void MostRecentlyUsedElementTest() {
        assertEquals("E", mruCacheAlgorithm.getElement(5));
    }

    @Test
    public void InsertNewValidElementTest() {
        mruCacheAlgorithm.putElement(6, "F");

        assertNull(mruCacheAlgorithm.getElement(5));
        assertEquals("F", mruCacheAlgorithm.getElement(6));
    }

    @Test
    public void RemoveElementFromCacheTest() {
        mruCacheAlgorithm.removeElement(1);

        assertNull(mruCacheAlgorithm.getElement(1));
    }

    @Test
    public void UpdatePageContentTest() {
        mruCacheAlgorithm.putElement(4, "H");

        assertNotEquals("D", mruCacheAlgorithm.getElement(4));
        assertEquals("H", mruCacheAlgorithm.getElement(4));
    }
}