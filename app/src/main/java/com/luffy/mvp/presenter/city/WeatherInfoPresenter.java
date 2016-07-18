package com.luffy.mvp.presenter.city;

import com.luffy.mvp.modle.DataCallback;
import com.luffy.mvp.modle.Error;
import com.luffy.mvp.modle.city.WeatherModle;
import com.luffy.mvp.presenter.BasePresenter;
import com.luffy.mvp.reponse.GossipBean;
import com.luffy.mvp.util.ToastUtil;
import com.luffy.mvp.view.IWeatherView;

/**
 * Created by jiangminglu on 16/7/6.
 */
public class WeatherInfoPresenter extends BasePresenter {

    private IWeatherView iWeatherView;
    private WeatherModle modle;

    private WeatherInfoPresenter(){};
    public WeatherInfoPresenter(IWeatherView iWeatherView) {
        this.iWeatherView = iWeatherView;
        modle = new WeatherModle();

    }

    /**
     * 获取城市天气信息
     */
    public void getCityWeatherInfo(){
        iWeatherView.showLoading();

        modle.getWetherInfoByCityName("广", new DataCallback<GossipBean>() {
            @Override
            public void onSuccess(GossipBean weather) {
                iWeatherView.hideLoading();
                iWeatherView.getWeatherTv().setText(weather.toString());
            }

            @Override
            public void onFailure(Error error) {
                iWeatherView.hideLoading();
                ToastUtil.showToast(error.message);
            }

        });
    }
}
