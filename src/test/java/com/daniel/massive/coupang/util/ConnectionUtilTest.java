package com.daniel.massive.coupang.util;

import com.daniel.massive.coupang.constant.enums.SearchMenuUrl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConnectionUtilTest {

    @Test
    public void getMenuUrl() {

        SearchMenuUrl searchMenuUrl = SearchMenuUrl.valueOf("womanclothe");
        System.out.println(searchMenuUrl.name());
        System.out.println(searchMenuUrl.getUrl());

    }
}