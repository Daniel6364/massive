package com.daniel.massive.coupang.component;

import com.daniel.massive.coupang.constant.enums.SearchMenuUrl;
import com.daniel.massive.coupang.dto.response.SubMenuResponse;
import com.daniel.massive.coupang.util.ConnectionUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.daniel.massive.coupang.constant.MenuUrlConstants.PAGE;

@Component
public class CategoryComponent {

    public List<SubMenuResponse> getSubMenuList(Elements subElements) {

        List<SubMenuResponse> subMenuList = new ArrayList<>();

        subElements.forEach(e -> {

            SubMenuResponse subMenuResponse = new SubMenuResponse();

            subMenuResponse.setTitle(e.text());
            subMenuResponse.setLink(e.attr("abs:href"));

            JsonObject jsonObject = (JsonObject) JsonParser.parseString(e.attr("data-log-props"));
            JsonObject object = (JsonObject) jsonObject.get("param");
            JsonElement jsonElement = object.get("categoryLabel");
            subMenuResponse.setClassId(jsonElement.getAsString());

            System.out.println(subMenuResponse.getClassId());
            System.out.println(subMenuResponse.getLink());

            subMenuList.add(subMenuResponse);

        });

        return subMenuList;
    }

    public int getTotalPageNum(String className) {

        int totalPageCount = 0;

        final String MENU_URL =
                SearchMenuUrl.valueOf(className.toLowerCase().replaceAll("[^a-zA-Z]", "")).getUrl();

        int pageNum = 1;
        Connection connection = ConnectionUtil.getConnection(MENU_URL + PAGE + pageNum);

        try {
            Document document = connection.get();

            totalPageCount = Integer.parseInt(document.getElementById("product-list-paging").attr("data-total"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return totalPageCount;
    }

}
