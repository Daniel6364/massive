package com.daniel.massive.coupang.controller;

import com.daniel.massive.coupang.dto.response.ProductResponse;
import com.daniel.massive.coupang.dto.response.MainMenuResponse;
import com.daniel.massive.coupang.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/coupang")
@RestController
public class CoupangController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("/main-menu")
    public ResponseEntity<List<MainMenuResponse>> getCategoryMenu() {
        return new ResponseEntity<>(categoryService.getCategoryMenu(), HttpStatus.OK);
    }

    @GetMapping("/{className}/{pageNum}")
    public ResponseEntity<List<ProductResponse>> getMenuListByPage(@PathVariable String className, @PathVariable int pageNum) {
        return new ResponseEntity<>(categoryService.getMenuListByPage(className, pageNum), HttpStatus.OK);
    }

    @GetMapping("/{className}")
    public ResponseEntity<List<ProductResponse>> getMenuListAll(@PathVariable String className) {
        return new ResponseEntity<>(categoryService.getMenuListAll(className), HttpStatus.OK);
    }






}
