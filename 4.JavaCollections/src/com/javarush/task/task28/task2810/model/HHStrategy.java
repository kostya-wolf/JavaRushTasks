package com.javarush.task.task28.task2810.model;

import com.javarush.task.task28.task2810.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HHStrategy implements Strategy {
    private static final String URL_FORMAT = "https://hh.ru/search/vacancy?text=java+%s&page=%d";
    private static final String USER_AGENT = "User-Agent: Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:84.0) Gecko/20100101 Firefox/84.0";
    private static final String REFERER = "Referer: https://hh.ru/";

    @Override
    public List<Vacancy> getVacancies(String searchString) {
        try {
            Document document = Jsoup.connect(URL_FORMAT)
                    .userAgent(USER_AGENT)
                    .referrer(REFERER)
                    .get();
            return new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
