package java.server;

import java.io.Serializable;
import java.util.Map;

public class Request<T> implements Serializable {
    private Map<String, String> header;
    private T body;

    public Request(Map<String, String> header, T body) {
        this.header = header;
        this.body = body;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }

    @Override
    public String toString() {
        //maybe should to do body.toString() - check in test
        return super.toString();
    }
}
