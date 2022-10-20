package com.daniel.massive.coupang.service;

import com.daniel.massive.coupang.response.CoupangResponse;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.daniel.massive.coupang.constant.CoupangConstants.COUPANG_HOME;

@Service
public class CategoryService {

    public List<CoupangResponse> getCategoryMenu() {

        List<CoupangResponse> response = new ArrayList<>();

        String url = COUPANG_HOME;
        Connection connection = Jsoup.connect(url);

        try {
            Document document = connection.get();

            Elements elements = document.getElementsByClass("menu shopping-menu-list > li");
            elements.forEach(e -> System.out.println(e.attr("abs:href")));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return response;

    }
}
