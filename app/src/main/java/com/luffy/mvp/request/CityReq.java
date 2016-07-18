package com.luffy.mvp.request;

/**
 * 用于发送json请求,本示例里面没有用
 */
public class CityReq extends BaseRequest{
    private String uid;
    private CityReq(){};

    public CityReq(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
