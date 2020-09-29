package com.ruoyi.web.sms;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

public class SendSms {
	/*
	pom.xml
	<dependency>
	  <groupId>com.aliyun</groupId>
	  <artifactId>aliyun-java-sdk-core</artifactId>
	  <version>4.5.3</version>
	</dependency>
	*/
	    public static void main(String[] args) {
	        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4G5ngsHJbfaAYxUMPUDK", "c1s4zPM2ywzqqE3zVRZOuAwZiylfhz");
	        IAcsClient client = new DefaultAcsClient(profile);

	        CommonRequest request = new CommonRequest();
	        request.setSysMethod(MethodType.POST);
	        request.setSysDomain("dysmsapi.aliyuncs.com");
	        request.setSysVersion("2017-05-25");
	        request.setSysAction("SendSms");
	        request.putQueryParameter("RegionId", "cn-hangzhou");
	        request.putQueryParameter("PhoneNumbers", "15360886162");
	        request.putQueryParameter("SignName", "潮州基站发电");
	        request.putQueryParameter("TemplateCode", "SMS_203672656");
	        
	        Map<String,Object> param = new HashMap<String, Object>();
	        param.put("code", "1111");
	        //# ${code}的赋值，json格式
	        request.putQueryParameter("TemplateParam", JSON.toJSONString(param));
	        try {
	            CommonResponse response = client.getCommonResponse(request);
	            System.out.println(response.getData());
	        } catch (ServerException e) {
	            e.printStackTrace();
	        } catch (ClientException e) {
	            e.printStackTrace();
	        }
	    }
	}
