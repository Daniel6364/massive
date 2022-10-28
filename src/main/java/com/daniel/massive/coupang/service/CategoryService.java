package com.daniel.massive.coupang.service;

import com.daniel.massive.coupang.response.CoupangResponse;
import com.daniel.massive.coupang.response.MainMenuResponse;
import com.daniel.massive.coupang.response.SubMenuResponse;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.daniel.massive.common.constant.CommonConstants.MAIN_MENU;
import static com.daniel.massive.common.constant.CommonConstants.USER_AGENT;
import static com.daniel.massive.coupang.constant.CoupangConstants.COUPANG_HOME;

@Service
public class CategoryService {

    public List<MainMenuResponse> getCategoryMenu() {

        List<MainMenuResponse> response = new ArrayList<>();

        Connection connection = Jsoup.connect(COUPANG_HOME)
                                     .userAgent(USER_AGENT)
                                     .header("scheme", "https")
                                     .header("accept-encoding", "gzip, deflate, br")
                                     .header("accept-language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7,es;q=0.6");

        try {
            Document document = connection.get();

            Elements elements = document.select(MAIN_MENU);

            elements.forEach(e -> {

                MainMenuResponse mainMenuResponse = new MainMenuResponse();

                mainMenuResponse.setClassId(e.className());
                mainMenuResponse.setTitle(e.text().split(" ")[0]);

                if (e.select("a").attr("abs:href").matches("javascript:;")) {

                    mainMenuResponse.setLink("empty");

                    Elements subElements = e.select(".second-depth-list > a");

                    List<SubMenuResponse> subList = new ArrayList<>();

                    subElements.forEach(e2 -> {

                        SubMenuResponse subMenuResponse = new SubMenuResponse();

                        subMenuResponse.setTitle(e2.text());
                        subMenuResponse.setLink(e2.attr("abs:href"));

                        JsonObject jsonObject = (JsonObject) JsonParser.parseString(e2.attr("data-log-props"));
                        JsonObject object = (JsonObject) jsonObject.get("param");
                        JsonElement jsonElement = object.get("categoryLabel");
                        subMenuResponse.setClassId(jsonElement.getAsString());

                        subList.add(subMenuResponse);

                    });

                    mainMenuResponse.setSubMenuResponses(subList);

                } else {
                    mainMenuResponse.setLink(e.select("a").attr("abs:href"));
                }

                response.add(mainMenuResponse);

            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return response;

    }
}
