package com.daniel.massive.coupang.builder;

import com.daniel.massive.coupang.dto.response.ProductResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.bytebuddy.implementation.bind.annotation.IgnoreForBinding;
import org.jsoup.select.Elements;
import org.springframework.lang.Nullable;

import java.util.List;

@Getter
@Setter
@Builder
public class ProductBuilder {

    private Elements elements;
    private List<ProductResponse> response;
    private String searchDate;



}
