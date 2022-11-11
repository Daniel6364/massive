package com.daniel.massive.coupang.component;

import com.daniel.massive.coupang.builder.ProductBuilder;
import com.daniel.massive.coupang.dto.response.ProductResponse;
import com.daniel.massive.coupang.util.ConnectionUtil;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.daniel.massive.coupang.constant.MenuUrlConstants.PAGE;
import static com.daniel.massive.coupang.constant.TagValueConstants.PRODUCTS;

@Component
public class ProductComponent {

    private String getArrivalDate(List<String> arrivalInfo) {

        String[] checkArrivalDateArr = arrivalInfo.get(0).split(" ");
        String[] arrivalDateArr = arrivalInfo.get(0).split(" ")[checkArrivalDateArr.length - 1].split("/");

        int year = LocalDateTime.now(ZoneId.of("Asia/Seoul")).getYear();
        int presentMonth = LocalDateTime.now(ZoneId.of("Asia/Seoul")).getMonthValue();

        int arrivalMonth = Integer.parseInt(arrivalDateArr[0]);
        int arrivalDay = Integer.parseInt(arrivalDateArr[1]);

        if (arrivalMonth < presentMonth) year++;
        LocalDateTime arrivalDataTime = LocalDateTime.of(year, arrivalMonth, arrivalDay, 0, 0);

        return arrivalDataTime.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

    }

//    private void setProduct(Elements elements, List<ProductResponse> response) {
//        elements.forEach(e -> {
//
//            ProductResponse productResponse = new ProductResponse();
//
//            List<String> arrivalInfo = new ArrayList<>();
//            e.getElementsByClass("arrival-info").select("em").forEach(i -> arrivalInfo.add(i.text()));
//
//            if (!arrivalInfo.isEmpty()) productResponse.setArrivalDate(getArrivalDate(arrivalInfo));
//            productResponse.setId(e.id());
//            productResponse.setLink(e.getElementsByClass("baby-product-link").attr("abs:href"));
//            productResponse.setImage(e.getElementsByTag("img").attr("abs:src"));
//            productResponse.setName(e.getElementsByClass("name").text());
//            productResponse.setBasePrice(e.getElementsByClass("base-price").text().replaceAll("[^0-9]", ""));
//            productResponse.setDiscountPrice(e.getElementsByClass("price-value").text().replaceAll("[^0-9]", ""));
//            productResponse.setDiscountPercentage(e.getElementsByClass("discount-percentage").text());
//            productResponse.setArrivalInfo(arrivalInfo);
//            productResponse.setRating(e.getElementsByClass("rating").text());
//            productResponse.setRatingTotalCount(e.getElementsByClass("rating-total-count").text().replaceAll("[^0-9]", ""));
//
//            response.add(productResponse);
//        });
//    }

//    private void setProduct(Elements elements, List<ProductResponse> response, String searchDate) {
//        elements.forEach(e -> {
//
//            ProductResponse productResponse = new ProductResponse();
//
//            List<String> arrivalInfo = new ArrayList<>();
//            e.getElementsByClass("arrival-info").select("em").forEach(i -> arrivalInfo.add(i.text()));
//
//            if (!arrivalInfo.isEmpty()) {
//
//                String arrivalDate = getArrivalDate(arrivalInfo);
//
//
//                productResponse.setArrivalDate(getArrivalDate(arrivalInfo));
//                productResponse.setId(e.id());
//                productResponse.setLink(e.getElementsByClass("baby-product-link").attr("abs:href"));
//                productResponse.setImage(e.getElementsByTag("img").attr("abs:src"));
//                productResponse.setName(e.getElementsByClass("name").text());
//                productResponse.setBasePrice(e.getElementsByClass("base-price").text().replaceAll("[^0-9]", ""));
//                productResponse.setDiscountPrice(e.getElementsByClass("price-value").text().replaceAll("[^0-9]", ""));
//                productResponse.setDiscountPercentage(e.getElementsByClass("discount-percentage").text());
//                productResponse.setArrivalInfo(arrivalInfo);
//                productResponse.setRating(e.getElementsByClass("rating").text());
//                productResponse.setRatingTotalCount(e.getElementsByClass("rating-total-count").text().replaceAll("[^0-9]", ""));
//            }
//
//
//            response.add(productResponse);
//        });
//    }

    private void setProductResponse(Element e, ProductResponse productResponse, List<String> arrivalInfo) {
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
        productResponse.setArrivalDate(getArrivalDate(arrivalInfo));
    }

    private void setProduct(ProductBuilder build) {
        build.getElements().forEach(e -> {

            ProductResponse productResponse = new ProductResponse();

            List<String> arrivalInfo = new ArrayList<>();
            e.getElementsByClass("arrival-info").select("em").forEach(i -> arrivalInfo.add(i.text()));

            if (!arrivalInfo.isEmpty()) setProductResponse(e, productResponse, arrivalInfo);

            build.getResponse().add(productResponse);
        });
    }


    private void setProductBySearchDate(ProductBuilder build) {

        build.getElements().forEach(e -> {

            ProductResponse productResponse = new ProductResponse();

            List<String> arrivalInfo = new ArrayList<>();
            e.getElementsByClass("arrival-info").select("em").forEach(i -> arrivalInfo.add(i.text()));

            if (!arrivalInfo.isEmpty()) {
                if (compareDate(getArrivalDate(arrivalInfo), build.getSearchDate()) >= 0) {
                    System.out.println(arrivalInfo);
                    setProductResponse(e, productResponse, arrivalInfo);
                }
            }
            build.getResponse().add(productResponse);
        });
    }

    private int compareDate(String arrivalDate, String searchDate) {

        LocalDateTime arrivalDateTime = LocalDateTime.of(Integer.parseInt(arrivalDate.substring(0, 4)),
                                                         Integer.parseInt(arrivalDate.substring(4, 6)),
                                                         Integer.parseInt(arrivalDate.substring(6)), 0, 0, 0);

        LocalDateTime searchDateTime = LocalDateTime.of(Integer.parseInt(searchDate.substring(0, 4)),
                                                        Integer.parseInt(searchDate.substring(4, 6)),
                                                        Integer.parseInt(searchDate.substring(6)), 0, 0, 0);

        System.out.println(arrivalDateTime.compareTo(searchDateTime));

        return arrivalDateTime.compareTo(searchDateTime);
    }


    public List<ProductResponse> getMenuList(Elements elements) {

        List<ProductResponse> response = new ArrayList<>();

        setProduct(ProductBuilder.builder().elements(elements).response(response).searchDate("").build());

        return response;
    }

    public List<ProductResponse> getMenuListAll(String MENU_URL, int totalPageNum) {

        System.out.println("=================// getMenuListAll : 2params");

        List<ProductResponse> response = new ArrayList<>();

        for (int pageNum = 1; pageNum < totalPageNum; pageNum++) {
            Connection connection = ConnectionUtil.getConnection(MENU_URL + PAGE + pageNum);

            try {
                Document document = connection.get();

                Elements elements = document.getElementsByClass(PRODUCTS);

                setProduct(ProductBuilder.builder().elements(elements).response(response).searchDate("").build());

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return response;
    }

    public List<ProductResponse> getMenuListAll(String MENU_URL, int totalPageNum, String searchDate) {

        System.out.println("=================// getMenuListAll : 3params");
        System.out.println("=================// searchDate : " + searchDate);


        List<ProductResponse> response = new ArrayList<>();

        for (int pageNum = 1; pageNum < totalPageNum; pageNum++) {
            Connection connection = ConnectionUtil.getConnection(MENU_URL + PAGE + pageNum);

            try {
                Document document = connection.get();

                Elements elements = document.getElementsByClass(PRODUCTS);

                setProductBySearchDate(ProductBuilder.builder().elements(elements).response(response).searchDate(searchDate).build());

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return response;
    }
}
