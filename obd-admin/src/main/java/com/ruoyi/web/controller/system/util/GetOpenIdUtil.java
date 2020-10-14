package com.ruoyi.web.controller.system.util;


import net.sf.json.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class GetOpenIdUtil {
    private static String appid = "wx59a7da108926a47b";
    private static String appsecret = "38589ab7d365b8e4c9b207353285a0f4";
    public static String getOpenId(String code) throws IOException {
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appid + "&secret=" + appsecret + "&js_code=" + code + "&grant_type=authorization_code";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();

//        if(response.isSuccessful()){
            String body = response.body().string();
            //System.out.println(body);
            JSONObject jsonObject = JSONObject.fromObject(body);
            //从json字符串获取openid和session_key
            String openid = jsonObject.getString("openid");
            String session_key = jsonObject.getString("session_key");


//        }

        return openid;
    }
}
