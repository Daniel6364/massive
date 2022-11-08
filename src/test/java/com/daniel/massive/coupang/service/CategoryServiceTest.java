package com.daniel.massive.coupang.service;

import com.daniel.massive.coupang.component.CategoryComponent;
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

import static com.daniel.massive.coupang.constant.CommonConstants.*;
import static com.daniel.massive.coupang.constant.CoupangConstants.*;


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

        List<BabyProductResponse> response = new ArrayList<>();

        Connection connection = ConnectionUtil.getConnection(APPLIANCES_DIGITAL);

        try {
            Document document = connection.get();

            Elements elements = document.getElementsByClass(BABY_PRODUCT);

            elements.forEach(e -> {

                BabyProductResponse babyProductResponse = new BabyProductResponse();

                List<String> arrivalInfo = new ArrayList<>();
                e.getElementsByClass("arrival-info").select("em").forEach(i -> arrivalInfo.add(i.text()));

                babyProductResponse.setId(e.id());
                babyProductResponse.setLink(e.getElementsByClass("baby-product-link").attr("abs:href"));
                babyProductResponse.setImage(e.getElementsByTag("img").attr("abs:src"));
                babyProductResponse.setName(e.getElementsByClass("name").text());
                babyProductResponse.setBasePrice(e.getElementsByClass("base-price").text().replaceAll("[^0-9]", ""));
                babyProductResponse.setDiscountPrice(e.getElementsByClass("price-value").text().replaceAll("[^0-9]", ""));
                babyProductResponse.setDiscountPercentage(e.getElementsByClass("discount-percentage").text());
                babyProductResponse.setArrivalInfo(arrivalInfo);
                babyProductResponse.setRating(e.getElementsByClass("rating").text());
                babyProductResponse.setRatingTotalCount(e.getElementsByClass("rating-total-count").text().replaceAll("[^0-9]", ""));

                response.add(babyProductResponse);

            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println(response);

    }


}