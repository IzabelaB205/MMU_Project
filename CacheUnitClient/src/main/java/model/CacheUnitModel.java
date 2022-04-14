package main.java.model;

import main.java.controller.IController;
import main.java.view.ActionPayload;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CacheUnitModel extends Model{
    private final CacheUnitClient client;
    private List<IController> observersList;

    public CacheUnitModel() {
        client = new CacheUnitClient();
        observersList = new ArrayList<>();
    }

    @Override
    public void updateModelData(ActionPayload<String> actionPayload) {
        if(actionPayload.getAction().equals("LOAD_REQUEST")) { //load request button been clicked
            dataRequest(actionPayload.getPayload());
        }
        else { //show statistic button been clicked
            statisticRequest();
        }
    }

    @Override
    public void addObserver(IController controller) {
        observersList.add(controller);
    }

    @Override
    public void removeObserver(IController controller) {
        observersList.remove(controller);
    }

    @Override
    public void notifyObservers(ActionPayload<String> actionPayload) {
        for(IController controller : observersList) {
            controller.update(this, actionPayload);
        }
    }

    @Override
    public void dataRequest(String path) {
        String response = client.sendRequest(readFileAsString(path));
        ActionPayload<String> actionPayload = new ActionPayload<>("UPDATE_VIEW", response);
        notifyObservers(actionPayload);
    }

    @Override
    public void statisticRequest() {
        String response = client.sendRequest(getStatisticRequest());
        ActionPayload<String> actionPayload = new ActionPayload<>("UPDATE_STATISTIC_VIEW", response);
        notifyObservers(actionPayload);
    }

    @Override
    public String getStatisticRequest() {
        return " {\n " +
                "\"headers\":\n" +
                "{\"action\": \"STATISTIC\"},\n" +
                "\"body\":\n" +
                "{}\n" +
                "}";
    }

    private String readFileAsString(String path) {
        String data = "";
        try {
            data = new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }
}
