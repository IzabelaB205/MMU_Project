package main.java.view;

import main.java.controller.IController;

public interface IViewObserver {
    void addObserver(IController controller);
    void removeObserver(IController controller);
    void notifyObservers(ActionPayload<String> actionPayload);
}
