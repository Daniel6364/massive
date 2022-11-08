package com.daniel.massive.coupang.component;

import com.daniel.massive.coupang.dto.response.BabyProductResponse;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BabyProductComponent {


    public List<BabyProductResponse> getMenuList(Elements elements) {

        List<BabyProductResponse> response = new ArrayList<>();

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

        return response;
    }
}
