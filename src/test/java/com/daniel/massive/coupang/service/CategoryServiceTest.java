package com.daniel.massive.coupang.service;

import com.daniel.massive.coupang.component.BabyProductComponent;
import com.daniel.massive.coupang.component.CategoryComponent;
import com.daniel.massive.coupang.constant.enums.SearchMenuUrl;
import com.daniel.massive.coupang.dto.response.BabyProductResponse;
import com.daniel.massive.coupang.dto.response.MainMenuResponse;
import com.daniel.massive.coupang.util.ConnectionUtil;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.daniel.massive.coupang.constant.MenuUrlConstants.*;
import static com.daniel.massive.coupang.constant.TagValueConstants.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceTest {

    @Autowired
    CategoryComponent categoryComponent;

    @Autowired
    BabyProductComponent babyProductComponent;

    @Test
    public void getCategoryMenu() {

        List<MainMenuResponse> responses = new ArrayList<>();

        Connection connection = ConnectionUtil.getConnection(COUPANG_HOME);

        try {
            Document document = connection.get();

            Elements elements = document.select(MAIN_MENU);

            elements.forEach(e -> {

                MainMenuResponse mainMenuResponse = new MainMenuResponse();

                mainMenuResponse.setClassId(e.className());
                mainMenuResponse.setTitle(e.text().split(" ")[0]);

                System.out.println(mainMenuResponse.getClassId());

                // TODO : refactoring
                if (e.select("a").attr("abs:href").matches("javascript:;")) {

                    mainMenuResponse.setLink("empty");

                    Elements subElements = e.select(SUB_MENU);

                    mainMenuResponse.setSubMenuList(categoryComponent.getSubMenuList(subElements));


                } else {
                    mainMenuResponse.setLink(e.select("a").attr("abs:href"));

                    System.out.println(mainMenuResponse.getLink());
                }

                responses.add(mainMenuResponse);

            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        System.out.println(responses);

    }

    @Test
    public void getMenuList() {

        int pageNum = 1;
        String className = "woman-clothe";

        List<BabyProductResponse> response ;

        final String MENU_URL = SearchMenuUrl.valueOf(className.toLowerCase().replaceAll("[^a-zA-Z]", "")).getUrl();

        Connection connection = ConnectionUtil.getConnection(MENU_URL + PAGE + pageNum);

        try {
            Document document = connection.get();

            Elements elements = document.getElementsByClass(BABY_PRODUCT);

            response = babyProductComponent.getMenuList(elements);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println(response);

    }


}