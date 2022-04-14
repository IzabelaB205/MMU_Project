package main.java.view;

public interface IViewListener {
    <T> void updateUIData(T t);
    void updateUIStatistic(String payload);
}
