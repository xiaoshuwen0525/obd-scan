package com.ruoyi.framework.shiro.web.filter.space;


import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.*;

/**
 * @Date: 2020-4-26 17:24
 * @Author: wcq
 */
public class SpaceHttpServletRequestWrapper extends HttpServletRequestWrapper {

    public SpaceHttpServletRequestWrapper(HttpServletRequest servletRequest) {
        super(servletRequest);
    }

    @Override
    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);
        if (values == null) {
            return new String[0];
        }
        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            encodedValues[i] = values[i].trim();
            try {
                JSONObject jsonObject = JSONObject.parseObject(values[i]);
                for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
                    if (!"".equals(entry.getValue())) {
                        jsonObject.put(entry.getKey(), entry.getValue().toString().trim());
                    }

                }
                encodedValues[i] = jsonObject.toJSONString();
            } catch (Exception e) {
                System.err.println("JSON无法转发--------------------->" + values[i].trim());
            }
        }
        return encodedValues;
    }

    @Override
    public String getParameter(String parameter) {
        String value = super.getParameter(parameter);
        if (value == null) {
            return null;
        }
        return value.trim();
    }

}
