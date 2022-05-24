package services;

import Algorithm.ICacheAlgorithm;
import Algorithm.LRUCacheAlgorithm;
import dao.DaoFileImpl;
import dao.IDao;
import dm.DataModel;
import memory.CacheUnit;

import java.io.Serializable;

public class CacheUnitService<T> {
    private CacheUnit<T> cacheUnit;

    public CacheUnitService() {
        int capacity = 100;
        String filePath = "src/resources/datasource.txt";
        IDao<Serializable, DataModel<T>> dao = new DaoFileImpl(filePath);
        ICacheAlgorithm<Long, DataModel<T>> cacheAlgorithm = new LRUCacheAlgorithm<>(capacity);
        cacheUnit = new CacheUnit<>(cacheAlgorithm, dao);
    }

    public DataModel<T>[] getDataModels(DataModel<T>[] dataModels) {
        Long[] ids = getDataModelIds(dataModels);
        return cacheUnit.getDataModels(ids);
    }

    public boolean updateDataModels(DataModel<T>[] dataModels) {
        cacheUnit.putDataModels(dataModels);
        return true;
    }

    public boolean removeDataModels(DataModel<T>[] dataModels) {
        Long[] ids = getDataModelIds(dataModels);
        cacheUnit.removeDataModels(ids);
        return true;
    }

    private Long[] getDataModelIds(DataModel<T>[] dataModels) {
        Long[] ids = new Long[dataModels.length];

        for(int i = 0; i < dataModels.length; i++) {
            ids[i] = dataModels[i].getId();
        }

        return ids;
    }
}
