package com.ruoyi.web.controller.upload.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


/**
 * 请求处理工具类
 */
public class RequestUtil {
	
	/**
	 * 处理请求参数，转换为 Map 请求参数集合再返回
	 * @param request   HttpServletRequest  请求对象
	 * @return  Map 请求参数集合
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String, String> getMapParams(HttpServletRequest request){
		Map<String, String> paramMap = new HashMap<String, String>();
		if(request!=null){
			Enumeration enumerationParameterName =request.getParameterNames();
			if(enumerationParameterName!=null && enumerationParameterName.hasMoreElements()){
				while(enumerationParameterName.hasMoreElements()){
					String paramName = (String) enumerationParameterName.nextElement();
					String paramValue = request.getParameter(paramName);
					if(StringUtils.isNotBlank(paramValue)){
						paramMap.put(paramName,paramValue.trim());
					}
				}
			}
		}
		return paramMap;
	}
	
	/**
	 * 获取客户端的 IP 
	 * @param request  请求对象
	 * @return  返回客户端的 IP 
	 */
	public static String getUserIp(HttpServletRequest request) {
		String ip = "";
		//根据接口获取IP
		/*
		try {
			ip = HttpClientUtil.httpGetRequest("http://117.136.240.6/getUserIP/");
		} catch (Exception e) {
			System.out.println("获取IP接口异常");
			e.printStackTrace();
		}
		*/
		
		//如果接口没获取到IP，则使用下面方法获取
		if("".equals(ip) && request!=null){
	        ip = request.getHeader("x-forwarded-for");  
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	            ip = request.getHeader("Proxy-Client-IP");  
	        }  
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	            ip = request.getHeader("WL-Proxy-Client-IP");  
	        }  
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	            ip = request.getHeader("HTTP_CLIENT_IP");  
	        }  
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	            ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
	        }  
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	            ip = request.getRemoteAddr();  
	        }
		}
		return ip;
	}
	
	
	/**
	 * 判断User-Agent是否移动端
	 * @param request
	 * @return true / false
	 */
	public static boolean isMobile(HttpServletRequest request){
		//定义移动端请求的所有可能类型
		String[] agent = {"android","phone","ipod","mqqbrowser","blackberry","nokia","windowsce","symbian","lg","ucweb","skyfire","webos","incognito","blackberry","mobile","bada"}; 
		String ua = request.getHeader("User-Agent").toLowerCase();
		if (!ua.contains("Windows NT") || (ua.contains("Windows NT") && ua.contains("compatible; MSIE 9.0;"))) {
			//排除PC系统
			if (!ua.contains("Windows NT") && !ua.contains("Macintosh")) {
				for (String item : agent) {
					if (ua.contains(item)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * 判断User-Agent是否来自微信端
	 * @param request
	 * @return true / false
	 */
	public static boolean isWeixin(HttpServletRequest request){
		String ua = request.getHeader("User-Agent").toLowerCase();
		if (ua.indexOf("micromessenger") > 0) {// 是微信浏览器  
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 项目地址
	 * @param typer 1：域名，0：默认 完整地址
	 * @param request
	 * @return
	 */
	public static String getSiteUrl(int typer, HttpServletRequest request) {
		StringBuffer url = new StringBuffer();
		switch (typer) {
		case 1://服务器地址
			url.append(request.getServerName()); 
			break;
			
		case 2: //项目地址
			url.append(request.getScheme() == "https" ? "https://" : "http://");
			url.append(request.getServerName());	//服务器地址
			if(request.getServerPort() != 80 && request.getServerPort() != 443){
				url.append(":" + request.getServerPort());	//端口号
			}
			url.append(request.getContextPath()+"/");	//请求页面或其他地址
			break;
		
		default: //完整地址,80和443端口时不显示端口号
			url.append(request.getScheme() == "https" ? "https://" : "http://");
			url.append(request.getServerName());	//服务器地址
			if(request.getServerPort() != 80 && request.getServerPort() != 443){
				url.append(":" + request.getServerPort());	//端口号
			}
			url.append(request.getContextPath()+"/");	//请求页面或其他地址
			//url.append(request.getServletPath());	//请求页面或其他地址
			url.append(request.getQueryString() == null ? "" : "?" + request.getQueryString());//参数

			break;
		}
		return url.toString() ;
	}
	
	
	/**
	 * 按UTF-8格式读取请求body体中的数据
	 * @param req
	 * @return
	 * @throws IOException 
	 * @throws Exception
	 */
	public static String getRequestBody(HttpServletRequest request) throws IOException{
        String requestStrtoString = "";
        String requestStr = "";
        BufferedReader in = null;
        StringBuffer sbsInput = new StringBuffer();
        try{
            in = new BufferedReader(new InputStreamReader(request.getInputStream(),
                    "UTF-8"));
            while ((requestStr = in.readLine()) != null){
                sbsInput.append(requestStr);
            }
            requestStrtoString = sbsInput.toString();
        }catch (IOException ex){
            ex.printStackTrace();
            throw ex;
		} /*
			 * finally{ if (in != null){ try{ in.close(); }catch (IOException e){
			 * e.printStackTrace(); } in = null; sbsInput = null; } }
			 */
        return requestStrtoString;
    }
}
