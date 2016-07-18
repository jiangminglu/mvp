package com.luffy.mvp.ui.weather;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.luffy.mvp.R;
import com.luffy.mvp.presenter.city.WeatherInfoPresenter;
import com.luffy.mvp.util.ToastUtil;
import com.luffy.mvp.view.IWeatherView;

public class WeatherInfoActivity extends AppCompatActivity implements IWeatherView {

    private WeatherInfoPresenter presenter;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_info);
        init();
    }
    private void init(){

        presenter = new WeatherInfoPresenter(this);
        initProgressDialog();
        findViewById(R.id.get_weather_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.getCityWeatherInfo();
            }
        });
    }
    private void initProgressDialog(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("提示");
        progressDialog.setMessage("正在获取天气信息,请稍后...");
        progressDialog.setProgressStyle(android.R.style.Widget_Holo_Light_ProgressBar);
    }


    @Override
    public TextView getWeatherTv() {
        return (TextView) findViewById(R.id.weather_tv);
    }

    @Override
    public void showLoading() {
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        if(progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }
}
