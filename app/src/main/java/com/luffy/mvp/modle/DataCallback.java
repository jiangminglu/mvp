package com.luffy.mvp.modle;

/**
 * Created by jiangminglu on 16/7/6.
 */
public interface  DataCallback<T> {
    public void onSuccess(T t);
    public void onFailure(Error error);
}
