package com.javarush.task.task28.task2810.view;

import com.javarush.task.task28.task2810.Controller;
import com.javarush.task.task28.task2810.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
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
        try {
            Document document = getDocument();

            Element template = document
                    .getElementsByClass("template")
                    .clone()
                    .removeAttr("style")
                    .removeClass("template")
                    .first();

            document
                    .select("tr.vacancy")
                    .not("tr.template")
                    .remove();

            for (Vacancy vacancy : vacancies) {
                Element vacToTag = template.clone();
                vacToTag.getElementsByClass("city").append(vacancy.getCity());
                vacToTag.getElementsByClass("companyName").append(vacancy.getCompanyName());
                vacToTag.getElementsByClass("salary").append(vacancy.getSalary());
                vacToTag.getElementsByTag("a")
                        .append(vacancy.getTitle())
                        .attr("href", vacancy.getUrl());
                document
                        .getElementsByClass("template")
                        .before(vacToTag.outerHtml());
            }

            return document.html();
        } catch (Exception e) {
            e.printStackTrace();
            return "Some exception occurred";
        }
    }

    private void updateFile(String content) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(content.getBytes());
            fos.flush();
        }
    }

    protected Document getDocument() throws IOException {
        return Jsoup.parse(new File(filePath), "UTF-8");
    }
}
