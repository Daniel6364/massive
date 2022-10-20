package com.daniel.massive.coupang.service;

import com.daniel.massive.coupang.response.CoupangResponse;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.daniel.massive.common.constant.CommonConstants.USER_AGENT;
import static com.daniel.massive.coupang.constant.CoupangConstants.APPLIANCES;
import static com.daniel.massive.coupang.constant.CoupangConstants.COUPANG_HOME;
import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceTest {

    @Test
    public void getCategoryMenu() {

        List<CoupangResponse> response = new ArrayList<>();

        Connection connection = Jsoup.connect(COUPANG_HOME)
                                     .userAgent(USER_AGENT)
                                     .header("scheme", "https")
                                     .header("accept-encoding", "gzip, deflate, br")
                                     .header("accept-language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7,es;q=0.6")
                                     ;

        try {
            Document document = connection.get();

            Elements elements = document.select(".shopping-menu-list > li");
//            System.out.println(elements);
//            elements.forEach(e -> System.out.println(e.className() + " : " + e.text()));

            elements.forEach(e -> {

                System.out.println("class : " + e.className());

                String[] textArr = e.text().split(" ");
                System.out.println("name : " + textArr[1]);

                System.out.println("link : " + e.select("a").attr("href"));


            });


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}