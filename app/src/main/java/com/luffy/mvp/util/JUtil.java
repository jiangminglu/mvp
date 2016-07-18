package com.luffy.mvp.util;


import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * 使用jackson 解析数据
 * Created by luffy on 15/12/8.
 */
public class JUtil {
//    public static ObjectMapper mapper;
//
//    static {
//        mapper = new ObjectMapper();
//    }

    /**
     * 解析从服务器拉下来的Json
     * 这个方法主要用于解析Response 中的数据，重点是data 下，并且将字符串型数据转化为Model 方便操作
     *
     * @param content 拉取下来的json 内容
     * @param cls     data下的数据形式，用于动态封装。
     * @return 封装过的 List 数据
     */
    public static <T> List<T> handleResponseList(final String content, Class<T> cls) throws Exception {
        List<T> list = JSON.parseArray(content, cls);
        return list;
    }

    /**
     * 解析从服务器拉下来的Json
     * 这个方法主要用于解析Response 中的数据，重点是data 下，并且将字符串型数据转化为Model 方便操作
     *
     * @param content 拉取下来的json 内容
     * @param cls     data下的数据形式，用于动态封装。
     * @return 解析后的object
     */
    public static <T extends Object> T handleResponseObject(final String content, Class<T> cls) throws Exception {
        T object = JSON.parseObject(content, cls);
        return object;
    }
}
