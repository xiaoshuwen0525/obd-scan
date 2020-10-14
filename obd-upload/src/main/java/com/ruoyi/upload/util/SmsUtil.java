package com.ruoyi.upload.util;


import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;



public class SmsUtil {

    public static void phoneAuthCode(String phone,Integer authCode) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4G5ngsHJbfaAYxUMPUDK", "c1s4zPM2ywzqqE3zVRZOuAwZiylfhz");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "潮州基站发电");
        request.putQueryParameter("TemplateCode", "SMS_203672656");
        request.putQueryParameter("TemplateParam", "{\"code\":\"" + authCode + "\"}");

        try {
            CommonResponse response = client.getCommonResponse(request);
//            System.out.println(response.getData());
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

}
