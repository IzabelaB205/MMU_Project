package services;

import dm.DataModel;

public class CacheUnitController<T> {
    private CacheUnitService<T> cacheUnitService;

    public CacheUnitController() {
        cacheUnitService = new CacheUnitService<>();
    }

    public DataModel<T>[] get(DataModel<T>[] dataModels) {
        return cacheUnitService.getDataModels(dataModels);
    }

    public boolean remove(DataModel<T>[] dataModels) {
        return cacheUnitService.removeDataModels(dataModels);
    }

    public boolean update(DataModel<T>[] dataModels) {
        return cacheUnitService.updateDataModels(dataModels);
    }
}
