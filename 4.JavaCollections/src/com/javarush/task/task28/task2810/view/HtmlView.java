package com.javarush.task.task28.task2810.view;

import com.javarush.task.task28.task2810.Controller;
import com.javarush.task.task28.task2810.vo.Vacancy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class HtmlView implements View {
    private Controller controller;
    private final String filePath = "./4.JavaCollections/src/" + this.getClass().getPackage().getName().replace('.', '/') + "/vacancies.html";

    @Override
    public void update(List<Vacancy> vacancies) {
        try {
            String content = getUpdatedFileContent(vacancies);
            updateFile(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void userCitySelectEmulationMethod() {
        controller.onCitySelect("Odessa");
    }

    private String getUpdatedFileContent(List<Vacancy> vacancies) {
        return null;
    }

    private void updateFile(String content) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(content.getBytes());
            fos.flush();
        }
    }
}
