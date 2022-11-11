package com.daniel.massive.coupang.service;

import com.daniel.massive.coupang.component.ProductComponent;
import com.daniel.massive.coupang.component.CategoryComponent;
import com.daniel.massive.coupang.constant.enums.SearchMenuUrl;
import com.daniel.massive.coupang.dto.response.ProductResponse;
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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.daniel.massive.coupang.constant.MenuUrlConstants.*;
import static com.daniel.massive.coupang.constant.TagValueConstants.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceTest {

    @Autowired
    CategoryComponent categoryComponent;

    @Autowired
    ProductComponent productComponent;

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
    public void getMenuListByPage() {

        int pageNum = 1;
        String className = "womanclothe";

        ////////

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

        System.out.println(response.size());
        System.out.println(response);

    }


    @Test
    public void getMenuListAll() {

        String className = "womanclothe";
        String searchDate = "20221115";
        //////////

        final String MENU_URL =
                SearchMenuUrl.valueOf(className.toLowerCase().replaceAll("[^a-zA-Z]", "")).getUrl();

        int totalPageNum = categoryComponent.getTotalPageNum(className);

//        List<ProductResponse> response = productComponent.getMenuListAll(MENU_URL, totalPageNum);
        List<ProductResponse> response = productComponent.getMenuListAll(MENU_URL, totalPageNum, searchDate);


        System.out.println(response.size());
        System.out.println(response);


    }

    @Test
    public void localDateTest() {

        String[] arrivalDateArr = new String[2];
        arrivalDateArr[0] = "01";
        arrivalDateArr[1] = "11";

        System.out.println(Arrays.toString(arrivalDateArr));

        //////////

//        String presentDate = LocalDateTime.now(ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        int year = LocalDateTime.now(ZoneId.of("Asia/Seoul")).getYear();
        int presentMonth = LocalDateTime.now(ZoneId.of("Asia/Seoul")).getMonthValue();

        int arrivalMonth = Integer.parseInt(arrivalDateArr[0]);
        int arrivalDay = Integer.parseInt(arrivalDateArr[1]);

        if (arrivalMonth < presentMonth) year++;
        LocalDateTime arrivalDataTime = LocalDateTime.of(year, arrivalMonth, arrivalDay, 0, 0);

        String arrivalDate = arrivalDataTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        System.out.println(arrivalDate);

        String tmpDate = "20221111";
        String tmpY = tmpDate.substring(0, 4);
        String tmpM = tmpDate.substring(4, 6);
        String tmpD = tmpDate.substring(6);

        System.out.println(tmpY + tmpM + tmpD);


    }

    @Test
    public void compareDateTest() {

        String arrivalDate = "20221115";
        String searchDate = "20221118";

        arrivalDate.substring(0, 4);
        arrivalDate.substring(4, 6);
        arrivalDate.substring(6);

        LocalDateTime arrivalDateTime = LocalDateTime.of(Integer.parseInt(arrivalDate.substring(0, 4)),
                                                         Integer.parseInt(arrivalDate.substring(4, 6)),
                                                         Integer.parseInt(arrivalDate.substring(6)), 0, 0, 0);

        searchDate.substring(0, 4);
        searchDate.substring(4, 6);
        searchDate.substring(6);

        LocalDateTime searchDateTime = LocalDateTime.of(Integer.parseInt(searchDate.substring(0, 4)),
                                                         Integer.parseInt(searchDate.substring(4, 6)),
                                                         Integer.parseInt(searchDate.substring(6)), 0, 0, 0);

        System.out.println(arrivalDateTime.compareTo(searchDateTime));




    }


}