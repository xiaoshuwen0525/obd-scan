package com.ruoyi.framework.shiro.web.filter.rpaToken;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.ServletUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.shiro.web.filter.AccessControlFilter;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RpaTokenFilter extends AccessControlFilter {


	private String tokenHeader="token";

	private String tokenValue = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5YW4iLCJjcmVhdGVkIjoxNTg2NTAyODU0MDU2LCJleHAiOjE1ODcxMDc2NTR9.2bsG4BknaPFzkjH3ShGQL3RfQ5BWlfqLtt6wZ_muQOGKd1CCtGthZDz9JZ8WLyWLn-ym-snC_zUWBk4kUG8RPQ";
	private static final String CLAIM_KEY_USERNAME = "sub";
	private static final String CLAIM_KEY_CREATED = "created";


	@Override
	protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		request.setCharacterEncoding("UTF-8");

		String authHeader = request.getParameter(this.tokenHeader);
		if(tokenValue.equals(authHeader)){
			return true;
		}
		 ServletUtils.renderString(response, "{\"code\":\"500\",\"msg\":\"token错误\"}");

		return false;

	}

	@Override
	protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
		System.out.println("11");
		return false;
	}


	public  String genToken( ){
		Map<String, Object> claims = new HashMap<>();
		claims.put(CLAIM_KEY_USERNAME, "yan");
		claims.put(CLAIM_KEY_CREATED, new Date());

		String token =Jwts.builder()
				.setClaims(claims)
				.setExpiration( new Date(System.currentTimeMillis() + 604800000))
				.signWith(SignatureAlgorithm.HS512, "mySecret")
				.compact();
		System.out.println(token);
		return token;
	}
}
