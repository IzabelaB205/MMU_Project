package dm;

import java.io.Serializable;

public class DataModel<T> implements Serializable {
    private long id;
    private T content;

    public DataModel(long id, T content) {
        this.id = id;
        this.content = content;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() { return id; }

    public void setContent(T content) {
        this.content = content;
    }

    public T getContent() { return content; }

    @Override
    public String toString() {
        return "id:  " + id + " name:  " + content.toString();
    }
}
