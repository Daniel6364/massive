package com.daniel.massive.coupang.component;

import com.daniel.massive.coupang.dto.response.ProductResponse;
import com.daniel.massive.coupang.util.ConnectionUtil;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.daniel.massive.coupang.constant.MenuUrlConstants.PAGE;
import static com.daniel.massive.coupang.constant.TagValueConstants.PRODUCTS;

@Component
public class ProductComponent {


    public List<ProductResponse> getMenuList(Elements elements) {

        List<ProductResponse> response = new ArrayList<>();

        elements.forEach(e -> {

            ProductResponse productResponse = new ProductResponse();

            List<String> arrivalInfo = new ArrayList<>();
            e.getElementsByClass("arrival-info").select("em").forEach(i -> arrivalInfo.add(i.text()));

            productResponse.setId(e.id());
            productResponse.setLink(e.getElementsByClass("baby-product-link").attr("abs:href"));
            productResponse.setImage(e.getElementsByTag("img").attr("abs:src"));
            productResponse.setName(e.getElementsByClass("name").text());
            productResponse.setBasePrice(e.getElementsByClass("base-price").text().replaceAll("[^0-9]", ""));
            productResponse.setDiscountPrice(e.getElementsByClass("price-value").text().replaceAll("[^0-9]", ""));
            productResponse.setDiscountPercentage(e.getElementsByClass("discount-percentage").text());
            productResponse.setArrivalInfo(arrivalInfo);
            productResponse.setRating(e.getElementsByClass("rating").text());
            productResponse.setRatingTotalCount(e.getElementsByClass("rating-total-count").text().replaceAll("[^0-9]", ""));

            response.add(productResponse);
        });

        return response;
    }

    public List<ProductResponse> getMenuListAll(String MENU_URL, int totalPageNum) {

        List<ProductResponse> response = new ArrayList<>();

        for (int pageNum = 1; pageNum < totalPageNum; pageNum++) {
            Connection connection = ConnectionUtil.getConnection(MENU_URL + PAGE + pageNum);

            try {
                Document document = connection.get();

                Elements elements = document.getElementsByClass(PRODUCTS);

                elements.forEach(e -> {

                    ProductResponse productResponse = new ProductResponse();

                    List<String> arrivalInfo = new ArrayList<>();
                    e.getElementsByClass("arrival-info").select("em").forEach(i -> arrivalInfo.add(i.text()));

                    productResponse.setId(e.id());
                    productResponse.setLink(e.getElementsByClass("baby-product-link").attr("abs:href"));
                    productResponse.setImage(e.getElementsByTag("img").attr("abs:src"));
                    productResponse.setName(e.getElementsByClass("name").text());
                    productResponse.setBasePrice(e.getElementsByClass("base-price").text().replaceAll("[^0-9]", ""));
                    productResponse.setDiscountPrice(e.getElementsByClass("price-value").text().replaceAll("[^0-9]", ""));
                    productResponse.setDiscountPercentage(e.getElementsByClass("discount-percentage").text());
                    productResponse.setArrivalInfo(arrivalInfo);
                    productResponse.setRating(e.getElementsByClass("rating").text());
                    productResponse.setRatingTotalCount(e.getElementsByClass("rating-total-count").text().replaceAll("[^0-9]", ""));

                    response.add(productResponse);
                });

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return response;
    }
}
