package com.daniel.massive.coupang.controller;

import com.daniel.massive.coupang.dto.response.MainMenuResponse;
import com.daniel.massive.coupang.service.CategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CoupangControllerTest {

    @Autowired
    CategoryService categoryService;

    @Test
    public void getCategoryMenu() {

        List<MainMenuResponse> result = categoryService.getCategoryMenu();

        System.out.println(result);

    }
}