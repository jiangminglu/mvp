package com.luffy.mvp.modle.city;

import com.luffy.mvp.http.HttpHelper;
import com.luffy.mvp.http.Method;
import com.luffy.mvp.modle.ResultCallback;
import com.luffy.mvp.modle.BaseModle;
import com.luffy.mvp.modle.DataCallback;
import com.luffy.mvp.reponse.GossipBean;
import com.luffy.mvp.util.JUtil;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by jiangminglu on 16/7/6.
 */
public class WeatherModle extends BaseModle {


    public void getWetherInfoByCityName(String cityName, final DataCallback<GossipBean> dataCallback){
        HashMap<String,Object> param = new HashMap<>();
        param.put("appid","weather");
        param.put("output","json");
        param.put("command",cityName);
        HttpHelper.sendRequest(Method.GET,param, "gossip-gl-location", new ResultCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                super.onSuccess(response);
                try {
                    GossipBean weather = JUtil.handleResponseObject(response.toString(),GossipBean.class);
                    dataCallback.onSuccess(weather);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFail(com.luffy.mvp.modle.Error error) {
                super.onFail(error);
                dataCallback.onFailure(error);
            }
        });
    }
}
