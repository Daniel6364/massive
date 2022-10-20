package com.daniel.massive.coupang.controller;

import com.daniel.massive.coupang.response.CoupangResponse;
import com.daniel.massive.coupang.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/coupang")
@RestController
public class CoupangController {


    CategoryService categoryService;

    @GetMapping("/category-menu")
    public ResponseEntity<List<CoupangResponse>> getCategoryMenu() {

        List<CoupangResponse> result = categoryService.getCategoryMenu();



        return new ResponseEntity<>(result, HttpStatus.OK);
    }



}
