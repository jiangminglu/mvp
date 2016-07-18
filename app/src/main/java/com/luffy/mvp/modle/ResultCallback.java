package com.luffy.mvp.modle;

import org.json.JSONObject;

/**
 * Created by jiangminglu on 16/7/6.
 */
public abstract class ResultCallback {
    public void onSuccess(JSONObject response){};
    public void onFail(Error error){};
}
