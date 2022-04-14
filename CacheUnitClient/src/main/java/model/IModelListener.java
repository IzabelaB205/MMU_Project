package main.java.model;

import main.java.view.ActionPayload;

public interface IModelListener {
    void updateModelData(ActionPayload<String> actionPayload);
}
