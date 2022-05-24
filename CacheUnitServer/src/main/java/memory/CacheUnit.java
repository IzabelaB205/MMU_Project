package memory;

import Algorithm.ICacheAlgorithm;
import dao.IDao;
import dm.DataModel;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CacheUnit<T> {
    private ICacheAlgorithm<Long, DataModel<T>> cacheAlgorithm;
    private IDao<Serializable, DataModel<T>> dao;

    public CacheUnit(ICacheAlgorithm<Long, DataModel<T>> cacheAlgorithm, IDao<Serializable, DataModel<T>> dao) {
        this.cacheAlgorithm = cacheAlgorithm;
        this.dao = dao;
    }

    public DataModel<T>[] getDataModels(Long[] ids) {
        List<DataModel<T>> result = new ArrayList<>();

        try {
            for(long id : ids) {
                DataModel<T> value = cacheAlgorithm.getElement(id);

                // The page did not exist in the cache memory.
                // Find the relevant page in the hard drive.
                if(value == null) {
                    value = dao.find(id);
                    cacheAlgorithm.putElement(id, value);
                }

                result.add(value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return (DataModel<T>[]) result.toArray();
    }

    public DataModel<T>[] putDataModels(DataModel<T>[] dataModels) {
        List<DataModel<T>> dataModelList = new ArrayList<>();

        for(DataModel<T> dataModel : dataModels) {
            long key = dataModel.getId();
            DataModel<T> result = cacheAlgorithm.putElement(key, dataModel);

            if(result != null) {
                dataModelList.add(result);
            }
        }

        return (DataModel<T>[]) dataModelList.toArray();
    }

    public void removeDataModels(Long[] ids) {
        for(long id : ids) {
            cacheAlgorithm.removeElement(id);
        }
    }
}
