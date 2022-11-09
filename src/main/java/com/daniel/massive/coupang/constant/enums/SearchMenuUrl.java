package com.daniel.massive.coupang.constant.enums;

import static com.daniel.massive.coupang.constant.MenuUrlConstants.*;

public enum SearchMenuUrl {

    womanclothe(WOMANCLOTHE),
    manclothe(MANCLOTHE),
    unisexfashion(UNISEXFASHION),
    childfashion(CHILDFASHION),
    beauty(BEAUTY),
    childbirth(CHILD_BIRTH),
    food(FOOD),
    kitchen(KITCHEN),
    life(LIFE),
    homedecoration(HOME_DECORATION),
    appliancesdigital(APPLIANCES_DIGITAL),
    sports(SPORTS),
    car(CAR),
    book(BOOK),
    hobby(HOBBY),
    office(OFFICE),
    pet(PET),
    health(HEALTH);


    private String url;

    SearchMenuUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
    }
