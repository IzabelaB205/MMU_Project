package main.java.driver;

import main.java.controller.CacheUnitController;
import main.java.controller.IController;
import main.java.model.CacheUnitModel;
import main.java.model.Model;
import main.java.view.CacheUnitView;
import main.java.view.View;

public class Driver {
    public static void main(String[] args) {
        View view = new CacheUnitView();
        Model model = new CacheUnitModel();
        IController controller = new CacheUnitController(model, view);
        model.addObserver(controller);
        view.addObserver(controller);
        view.start();
    }
}
