package com.daniel.massive.coupang.service;

import com.daniel.massive.coupang.component.CategoryComponent;
import com.daniel.massive.coupang.response.MainMenuResponse;
import com.daniel.massive.coupang.util.ConnectionUtil;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
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

import static com.daniel.massive.common.constant.CommonConstants.*;
import static com.daniel.massive.coupang.constant.CoupangConstants.*;
import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceTest {

    @Autowired
    CategoryComponent categoryComponent;

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

                if (e.select("a").attr("abs:href").matches("javascript:;")) {

                    mainMenuResponse.setLink("empty");

                    Elements subElements = e.select(SUB_MENU);

                    mainMenuResponse.setSubMenuList(categoryComponent.getSubMenuList(subElements));

                } else {
                    mainMenuResponse.setLink(e.select("a").attr("abs:href"));
                }

                responses.add(mainMenuResponse);

            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println(responses);

    }

    @Test
    public void getMenuList() {

        List<MainMenuResponse> response = new ArrayList<>();

        Connection connection = ConnectionUtil.getConnection(WOMANCLOTHE);

        try {
            Document document = connection.get();

            Elements elements = document.getElementsByClass(BABY_PRODUCT);

            elements.forEach(e -> {

                String id = e.id();
                String babyLink = e.getElementsByClass("baby-product-link").attr("abs:href");

                System.out.println("상품아이디 : " + id);
                System.out.println("상품링크 : " + babyLink);

            });



        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}