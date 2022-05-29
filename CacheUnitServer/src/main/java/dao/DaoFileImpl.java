package java.dao;

import java.dm.DataModel;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class DaoFileImpl<T> implements IDao<Long, DataModel<T>> {
    private final String filePath;
    private Map<Long, DataModel<T>> fileObjectsMap;

    public DaoFileImpl(String filePath) {
        this.filePath = filePath;
        fileObjectsMap = new HashMap<>();
    }


    @Override
    public DataModel<T> find(Long key) throws IOException, ClassNotFoundException {
        DataModel<T> dataModel = null;
        readFileObjects();

        if(fileObjectsMap.containsKey(key)) {
            dataModel = fileObjectsMap.get(key);
        }

        return dataModel;
    }

    @Override
    public void save(DataModel<T> dataModel) {
        fileObjectsMap.put(dataModel.getId(), dataModel);

        try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(filePath))) {
            output.writeObject(fileObjectsMap);
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(DataModel<T> dataModel) {
        fileObjectsMap.remove(dataModel.getId());

        try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(filePath))) {
            output.writeObject(fileObjectsMap);
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readFileObjects() {
        try(ObjectInputStream input = new ObjectInputStream(new FileInputStream(filePath))) {
            fileObjectsMap = (Map<Long, DataModel<T>>) input.readObject();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
