package com.luffy.mvp.http;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.luffy.mvp.BuildConfig;
import com.luffy.mvp.app.MApp;
import com.luffy.mvp.modle.Error;
import com.luffy.mvp.modle.ResultCallback;
import com.luffy.mvp.request.BaseRequest;
import com.luffy.mvp.ssl.SSLSocketFactoryEx;


import org.apache.http.Header;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.security.KeyStore;
import java.util.HashMap;
import java.util.Set;



public class HttpHelper {


    public final static String APP_SECRET = "xxxx";
    public final static String APP_ID = "xxxxx";
    public final static String PATH_LEVEL = "/";
    public final static String API_URL = "http://sugg.us.search.yahoo.net";
    private static AsyncHttpClient client=null;


    private static AsyncHttpClient getClient(){
        client = new AsyncHttpClient();
        client.setConnectTimeout(1000*60);
        client.setResponseTimeout(1000*60);
        client.setSSLSocketFactory(createSSLSocketFactory());
        return  client;
    }


    /**
     * 向服务器发送json数据请求
     * @param method 请求方式 {@link Method}
     * @param request  json 格式请求,方法会把Requestbean 转为 json 字符串
     * @param url 请求的路径 如:auth/register 所有的请求路径请配置在对应的modle中
     * @param handler
     */
    public static void sendRequest(Method method, final BaseRequest request, String url, final ResultCallback handler) {
        AsyncHttpResponseHandler httpResponseHandler = new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                if (handler != null) {
                    if (response.optInt("code") == 0)
                        handler.onSuccess(response);
                    else {
                        Error error = new Error();
                        error.code = response.optInt("code");
                        error.message = response.toString();
                        handler.onFail(error);
                    }
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);
                if (handler != null && !TextUtils.isEmpty(responseString)) {
                    if(statusCode ==200){
                        try {
                            JSONObject jsonObject = new JSONObject(responseString);
                            handler.onSuccess(jsonObject);
                        }catch (Exception e){
                            e.printStackTrace();
                            handler.onSuccess(null);
                        }
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                dealFailure(handler, errorResponse, throwable);
            }
        };
        try {
            String strReuqest = JSON.toJSONString(request);
            StringEntity stringEntity = new StringEntity(strReuqest, "utf-8");

            String requestUrl = API_URL + HttpHelper.PATH_LEVEL + url;
            AsyncHttpClient asyncHttpClient = getClient();
            addHeaders(method.getValue(),asyncHttpClient,request2map(request),url,strReuqest);
            if(method == Method.POST){
                asyncHttpClient.post(MApp.getInstance(), requestUrl, stringEntity, "application/json", httpResponseHandler);
            }else if(method == Method.GET){
                asyncHttpClient.get(MApp.getInstance(), requestUrl, stringEntity, "application/json", httpResponseHandler);
            }else if(method == Method.DELETE){
                asyncHttpClient.delete(MApp.getInstance(), requestUrl, stringEntity, "application/json", httpResponseHandler);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 向服务器发送表单请求
     * @param method 请求方式 {@link Method}
     * @param map 请求的参数
     * @param url 请求的路径 如:auth/register 所有的请求路径请配置在对应的modle中
     * @param handler
     */
    public static void sendRequest(Method method, HashMap<String, Object> map, String url,final ResultCallback handler) {

        AsyncHttpResponseHandler httpResponseHandler = new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                if (handler != null) {
                    if (response.optInt("code") == 0)
                        handler.onSuccess(response);
                    else {
                        Error error = new Error();
                        error.code = response.optInt("code");
                        error.message = response.toString();
                        handler.onFail(error);
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                dealFailure(handler, errorResponse, throwable);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Error error = new Error();
                error.code = -100;
                error.message = responseString;
                handler.onFail(error);
            }
        };
        RequestParams params = dealParam(map);
        String requestUrl = API_URL + HttpHelper.PATH_LEVEL + url;
        AsyncHttpClient asyncHttpClient = getClient();
        addHeaders(method.getValue(),asyncHttpClient,map,HttpHelper.PATH_LEVEL + url,null);
        if(method == Method.POST){
            asyncHttpClient.post(MApp.getInstance(),requestUrl,params,httpResponseHandler);
        }else if(method == Method.GET){
            asyncHttpClient.get(MApp.getInstance(),requestUrl,params,httpResponseHandler);
        }else if(method == Method.DELETE){
            asyncHttpClient.delete(requestUrl,params,httpResponseHandler);
        }
    }

    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactoryEx sf = null;
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);
            sf = new SSLSocketFactoryEx(trustStore);
            sf.setHostnameVerifier(SSLSocketFactoryEx.ALLOW_ALL_HOSTNAME_VERIFIER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sf;
    }

    /**
     * 为 client 设置 header
     * @param method
     * @param client
     * @param map
     * @param url
     */
    private static void addHeaders(String method, AsyncHttpClient client, HashMap<String, Object> map, String url,String jsonBody){
        long timestamp = System.currentTimeMillis();
        String nonce = "CGwMjLIac"+timestamp+"MBESwVRPSB";
        client.addHeader("Accept","application/json");
        client.addHeader("X-Api-Key",APP_ID);
        client.addHeader("X-Signature","xxx");//参数签名,不用刻意取消
//        if(SPUtil.getString("token")!=null && "".equals(SPUtil.getString("token")))
//            client.addHeader("X-Access-Token",SPUtil.getString("token"));
        client.addHeader("X-App-Version", BuildConfig.VERSION_NAME);
        client.addHeader("X-Timestamp",timestamp+"");
        client.addHeader("X-Nonce",nonce);//32未随机字符串
    }

    /**
     * 把hashmap转换为 RequestParam
     * @param map
     * @return
     */
    private static RequestParams dealParam(HashMap<String, Object> map) {
        if (map == null) map = new HashMap<>();

        RequestParams params = new RequestParams();
        params.setForceMultipartEntityContentType(true);

        Set<String> mapKeys = map.keySet();
        for (String key : mapKeys) {
            Object value = map.get(key);
            if (value instanceof File) {
                File file = (File) value;
                try {
                    params.put(key, file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                params.put(key, value);
            }

        }
        return params;
    }
    private static HashMap<String,Object> request2map(BaseRequest request){
        HashMap<String,Object> param = new HashMap<>();

        Class c = request.getClass();
        Field[] fields = c.getDeclaredFields();
        for(Field field:fields){
            field.setAccessible(true);
            try {
                String name = field.getName();
                Object value = field.get(request);
                if(!"CREATOR".equals(name)){
                    param.put(name,value);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return param;
    }


    private static void dealFailure(ResultCallback handler, JSONObject errorResponse, Throwable throwable) {
        try {
            Error error = new Error();
            if (handler != null && errorResponse != null) {
                JSONObject jsonObject = errorResponse;
                error.code = jsonObject.optInt("code");
                error.message = jsonObject.toString();
                handler.onFail(error);
            } else {
                error.message=throwable.getMessage();
                error.code = -100;
                handler.onFail(error);
            }
        } catch (Exception e) {
            Error error = new Error();
            error.message = e.getMessage();
            error.code = -100;
            handler.onFail(error);
        }
    }
}