package main.java.view;

public class ActionPayload <T> {
    private T action;
    private String payload;

    public ActionPayload(T action, String payload) {
        this.action = action;
        this.payload = payload;
    }

    public T getAction() {
        return this.action;
    }

    public void setAction(T action) {
        this.action = action;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
