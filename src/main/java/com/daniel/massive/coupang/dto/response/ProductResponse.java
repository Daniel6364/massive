package com.daniel.massive.coupang.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ProductResponse {

    private String id;
    private String link;
    private String image;
    private String name;
    private String basePrice;
    private String discountPrice;
    private String discountPercentage;
    private List<String> arrivalInfo;
    private String rating;
    private String ratingTotalCount;


}
