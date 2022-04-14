package main.java.model;

import main.java.controller.IController;
import main.java.view.ActionPayload;

public interface IModelObserver {
    void addObserver(IController controller);
    void removeObserver(IController controller);
    void notifyObservers(ActionPayload<String> actionPayload);
}
