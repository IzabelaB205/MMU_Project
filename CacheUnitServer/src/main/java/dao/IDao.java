package java.dao;

import java.io.IOException;
import java.io.Serializable;

public interface IDao <ID extends Serializable, T> {
    T find(ID id) throws IOException, ClassNotFoundException;
    void save(T value);
    void remove(T value);
}
