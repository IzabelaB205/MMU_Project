package main.java.model;

public abstract class Model implements IModelListener, IModelObserver{
    public abstract void dataRequest(String path);
    public abstract void statisticRequest();
    public abstract String getStatisticRequest();
}
