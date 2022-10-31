package com.daniel.massive.coupang.service;

import com.daniel.massive.coupang.component.CategoryComponent;
import com.daniel.massive.coupang.response.CoupangResponse;
import com.daniel.massive.coupang.response.MainMenuResponse;
import com.daniel.massive.coupang.response.SubMenuResponse;
import com.daniel.massive.coupang.util.ConnectionUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.daniel.massive.common.constant.CommonConstants.*;
import static com.daniel.massive.coupang.constant.CoupangConstants.COUPANG_HOME;

@Service
public class CategoryService {


    @Autowired
    CategoryComponent categoryComponent;

    public List<MainMenuResponse> getCategoryMenu() {

        List<MainMenuResponse> response = new ArrayList<>();

        Connection connection = ConnectionUtil.getConnection(COUPANG_HOME);

        try {
            Document document = connection.get();

            Elements elements = document.select(MAIN_MENU);

            elements.forEach(e -> {

                MainMenuResponse mainMenuResponse = new MainMenuResponse();

                mainMenuResponse.setClassId(e.className());
                mainMenuResponse.setTitle(e.text().split(" ")[0]);

                if (e.select("a").attr("abs:href").matches("javascript:;")) {

                    mainMenuResponse.setLink("empty");

                    Elements subElements = e.select(SUB_MENU);
//
                    mainMenuResponse.setSubMenuList(categoryComponent.getSubMenuList(subElements));

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
