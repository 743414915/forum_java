package com.forum.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class JsonUtils {
    public static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    /**
     * 对象转json
     *
     * @param obj 被转换对象
     * @return 转换的字符串
     */
    public static String convertObj2Json(Object obj) {
        return JSON.toJSONString(obj);
    }

    /**
     * json 字符串转对象
     *
     * @param json   被转json
     * @param classz 对象
     * @param <T>    返回类型
     * @return 返回的
     */
    public static <T> T convertJson2Obj(String json, Class<T> classz) {
        return JSONObject.parseObject(json, classz);
    }

    /**
     * 字符串数组转集合对象
     * @param json 字符串数组
     * @param classz 对象
     * @param <T> 对象类型
     * @return 转换之后的集合对象
     */
    public static <T> List<T> convertJsonArray2List(String json, Class<T> classz) {
        return JSONArray.parseArray(json, classz);
    }
}
