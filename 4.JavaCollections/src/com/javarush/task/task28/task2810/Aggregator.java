package com.javarush.task.task28.task2810;

import com.javarush.task.task28.task2810.model.HHStrategy;
import com.javarush.task.task28.task2810.model.Model;
import com.javarush.task.task28.task2810.model.Provider;
import com.javarush.task.task28.task2810.view.HtmlView;

public class Aggregator {
    public static void main(String[] args) {
        HtmlView view = new HtmlView();
        Provider provider = new Provider(new HHStrategy());
        Model model = new Model(view, provider);
        view.setController(new Controller(model));
        view.userCitySelectEmulationMethod();
    }
}
