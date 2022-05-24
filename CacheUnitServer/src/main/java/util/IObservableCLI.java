package util;

import server.IObserverServer;

public interface IObservableCLI {
    void add(IObserverServer observer);
    void remove(IObserverServer observer);
    void notifyObserver();
}
