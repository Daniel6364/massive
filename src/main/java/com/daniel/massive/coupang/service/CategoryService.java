package com.daniel.massive.coupang.service;

import com.daniel.massive.coupang.component.ProductComponent;
import com.daniel.massive.coupang.component.CategoryComponent;
import com.daniel.massive.coupang.constant.enums.SearchMenuUrl;
import com.daniel.massive.coupang.dto.response.ProductResponse;
import com.daniel.massive.coupang.dto.response.MainMenuResponse;
import com.daniel.massive.coupang.util.ConnectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.daniel.massive.coupang.constant.TagValueConstants.*;
import static com.daniel.massive.coupang.constant.MenuUrlConstants.*;

@Slf4j
@Service
public class CategoryService {


    @Autowired
    CategoryComponent categoryComponent;

    @Autowired
    ProductComponent productComponent;

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

    public List<ProductResponse> getMenuListByPage(String className, int pageNum) {

        List<ProductResponse> response;

        final String MENU_URL =
                SearchMenuUrl.valueOf(className.toLowerCase().replaceAll("[^a-zA-Z]", "")).getUrl();

        Connection connection = ConnectionUtil.getConnection(MENU_URL + PAGE + pageNum);

        try {
            Document document = connection.get();

            Elements elements = document.getElementsByClass(PRODUCTS);

            response = productComponent.getMenuList(elements);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return response ;
    }

    public List<ProductResponse> getMenuListAll(String className) {

        final String MENU_URL =
                SearchMenuUrl.valueOf(className.toLowerCase().replaceAll("[^a-zA-Z]", "")).getUrl();

        int totalPageNum = categoryComponent.getTotalPageNum(className);

        List<ProductResponse> response = productComponent.getMenuListAll(MENU_URL, totalPageNum);

        return response;
    }


}
