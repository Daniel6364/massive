package com.daniel.massive.coupang.component;

import com.daniel.massive.coupang.response.SubMenuResponse;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryComponent {

    public List<SubMenuResponse> getSubMenuList(Elements subElements) {

        List<SubMenuResponse> subMenuList = new ArrayList<>();

        subElements.forEach(e2 -> {

            SubMenuResponse subMenuResponse = new SubMenuResponse();

            subMenuResponse.setTitle(e2.text());
            subMenuResponse.setLink(e2.attr("abs:href"));

            JsonObject jsonObject = (JsonObject) JsonParser.parseString(e2.attr("data-log-props"));
            JsonObject object = (JsonObject) jsonObject.get("param");
            JsonElement jsonElement = object.get("categoryLabel");
            subMenuResponse.setClassId(jsonElement.getAsString());

            subMenuList.add(subMenuResponse);

        });

        return subMenuList;
    }
}
