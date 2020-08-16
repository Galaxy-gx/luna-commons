package com.luna.api.smms.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableMap;
import com.luna.api.smms.constant.SmMsConstant;
import com.luna.common.http.HttpUtils;
import com.luna.common.http.HttpUtilsConstant;
import org.apache.http.HttpResponse;

import java.util.HashMap;

/**
 * @Package: com.luna.api.smMs
 * @ClassName: SmMs
 * @Author: luna
 * @CreateTime: 2020/7/24 23:09
 * @Description:
 */
public class UserApiFromString {

    /**
     * 获取用户TOKEN
     * 
     * @param username
     * @param password
     * @return
     */
    public static String getAuthToken(String username, String password) {
        HashMap<String, String> map = new HashMap();
        map.put("Content-Type", HttpUtilsConstant.FORM_DATA);
        HttpResponse httpResponse =
            HttpUtils.doPost(SmMsConstant.HOST, "/token", map,
                ImmutableMap.of("username", username, "password", password), "");
        JSONObject response = JSON.parseObject(HttpUtils.checkResponseAndGetResult(httpResponse, true));
        return JSON.parseObject(response.getString("data")).getString("token");
    }

    /**
     * 获取用户信息
     * @param token
     * @return
     */
    public static String getUserProfile(String token) {
        HashMap<String, String> map = new HashMap();
        map.put("Content-Type", HttpUtilsConstant.FORM_DATA);
        map.put("Authorization", token);
        HttpResponse httpResponse =
            HttpUtils.doPost(SmMsConstant.HOST, "/profile", map,
                null, "");
        return HttpUtils.checkResponseAndGetResult(httpResponse, true);
    }

}
