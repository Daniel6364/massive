package com.daniel.massive.coupang.util;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import static com.daniel.massive.coupang.constant.CommonConstants.USER_AGENT;

public class ConnectionUtil {

    public static Connection getConnection(String menu) {
        return Jsoup.connect(menu)
                    .userAgent(USER_AGENT)
                    .header("scheme", "https")
                    .header("accept-encoding", "gzip, deflate, br")
                    .header("accept-language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7,es;q=0.6");
    }

}
