package test.memory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.algorithm.ICacheAlgorithm;
import java.algorithm.LRUCacheAlgorithm;
import java.dao.DaoFileImpl;
import java.dao.IDao;
import java.dm.DataModel;
import java.memory.CacheUnit;

import static org.junit.jupiter.api.Assertions.*;

class CacheUnitTest {
    ICacheAlgorithm<Long, DataModel<String>> cacheAlgorythem;
    IDao dao;
    CacheUnit<String> cacheUnit;
    DataModel<String>[] dataModelArray;
    String[] contents;
    Long[] ids;
    int capacity;
    String filePath;

    @BeforeEach
    public void init() {
        capacity = 3;
        filePath = "src/resources/datasource.txt";
        cacheAlgorythem = new LRUCacheAlgorithm<>(capacity);
        dao = new DaoFileImpl<String>(filePath);
        cacheUnit = new CacheUnit<String>(cacheAlgorythem, dao);
        dataModelArray = new DataModel[capacity];
        contents = new String[]{"GET", "PUT", "REMOVE"};
        ids = new Long[]{1L, 2L, 3L};

        for(int i = 0; i < capacity; i++) {
            DataModel<String> dataModel = new DataModel<>(ids[i], contents[i]);
            dataModelArray[i] = dataModel;
            dao.save(dataModel);
        }
    }

    @Test
    public void GetDataModelsTest() {
        DataModel<String>[] dataModelArrayTest = cacheUnit.getDataModels(ids);

        assertEquals(dataModelArrayTest[0].getContent(), dataModelArray[0].getContent());
        assertEquals(dataModelArrayTest[1].getContent(), dataModelArray[1].getContent());
        assertEquals(dataModelArrayTest[2].getContent(), dataModelArray[2].getContent());
    }

//    @Test
//    public void PutDataModelsTest() {
//        DataModel<String>[] dataModelsTest = cacheUnit.putDataModels(dataModel);
//
//        assertEquals(dataModel[0].getId(), dataModelsTest[0].getId());
//        assertEquals(dataModel[0].getContent(), dataModelsTest[0].getContent());
//
//        assertEquals(dataModel[1].getId(), dataModelsTest[1].getId());
//        assertEquals(dataModel[1].getContent(), dataModelsTest[1].getContent());
//
//        assertEquals(dataModel[2].getId(), dataModelsTest[2].getId());
//        assertEquals(dataModel[2].getContent(), dataModelsTest[2].getContent());
//    }

//    @Test
//    public void RemoveDataModelsTest() {
//        cacheUnit.removeDataModels(ids);
//        DataModel<String>[] result = cacheUnit.getDataModels(ids);
//
//        assertNull(result[0].getContent());
//        assertNull(result[1].getContent());
//        assertNull(result[2].getContent());
//    }
}