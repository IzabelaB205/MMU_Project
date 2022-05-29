package java.utillity;

import java.server.IObserverServer;

public interface IObservableCLI {
    void add(IObserverServer observer);
    void remove(IObserverServer observer);
    void notifyObserver();
}
