package com.daniel.massive.coupang.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class MainMenuResponse {

    public String classId;
    public String title;
    public String link;
    public List<SubMenuResponse> subMenuList;
}
