package com.javarush.task.task28.task2810.model;

import com.javarush.task.task28.task2810.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HabrCareerStrategy implements Strategy {
    private static final String URL_HABR = "https://career.habr.com";
    private static final String URL_FORMAT = URL_HABR + "/vacancies?q=java+%s&page=%d";
    private static final String CACHED_PAGE = "https://javarush.ru/testdata/big28data2.html?q=java+%s&page=%d&";
    private static final String USER_AGENT = "User-Agent: Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:84.0) Gecko/20100101 Firefox/84.0";
    private static final String REFERER = "Referer: https://habr.com/";

    @Override
    public List<Vacancy> getVacancies(String searchString) {
        List<Vacancy> result = new ArrayList<>();
        try {
            int page = 0;
            Document document;
            while (true) {
                document = getDocument(searchString, page);
                Elements elements = document.getElementsByClass("job");
                if (elements.isEmpty()) {
                    break;
                }
                for (Element element : elements) {
                    Vacancy vacancy = new Vacancy();
                    vacancy.setTitle(element.getElementsByClass("title").text());
                    Elements salary = element.getElementsByClass("salary");
                    vacancy.setSalary(salary.hasText() ? salary.text() : "");
                    vacancy.setCity(element.getElementsByClass("location").text());
                    vacancy.setCompanyName(element.select("div.company_name").text());
                    vacancy.setSiteName(URL_FORMAT);
                    String url = element.getElementsByClass("title").first().child(0).attr("href");
                    vacancy.setUrl(URL_HABR + url);
                    result.add(vacancy);
                }
                page++;
            }
        } catch (Exception e) {
        }
        return result;
    }

    protected Document getDocument(String searchString, int page) throws IOException {
        return Jsoup.connect(String.format(URL_FORMAT, searchString, page))
                .userAgent(USER_AGENT)
                .referrer(REFERER)
                .get();
    }
}
