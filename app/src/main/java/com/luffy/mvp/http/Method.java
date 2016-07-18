package com.luffy.mvp.http;

/**
 * Created by jiangminglu on 16/7/12.
 */
public enum Method {
    POST("POST"),
    GET("GET"),
    DELETE("DELETE");
    private String value;

    Method(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
