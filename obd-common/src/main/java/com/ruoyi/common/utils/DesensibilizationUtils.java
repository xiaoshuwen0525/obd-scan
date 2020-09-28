package com.ruoyi.common.utils;


import java.util.Collection;
import java.util.Map;
import com.ruoyi.common.core.text.StrFormatter;

/**
 * 字符串工具类
 *
 * @author lcx
 */
public class DesensibilizationUtils
{
        /**
         * 身份证
         */
        public static String getIdentification(String data)
        {
            String value = "";
            if (StringUtils.isNotEmpty(data))
            {
                if(data.length()<4){
                    return value;
                }

                String str = data.substring(data.length()-4, data.length());
                value = "***********" + str;
            }
            return value;
        }

        /**
         * phone number
         */
        public static String getPhoneNumber(String data)
        {
            String value = "";
            if (StringUtils.isNotEmpty(data))
            {
                if(data.length()<7){
                    return value;
                }

                String str = data.substring(0, 3);
                String str2 = data.substring(data.length()-4, data.length());
                value = str + "****" + str2;
            }
            return value;
        }

        /**
         * Mail number
         */
        public static String getMailNumber(String data)
        {
            String value = "";
            if (StringUtils.isNotEmpty(data))
            {

                String[] str = data.split("@");
                if(str.length > 1){
                    return value;
                }
                value = "********" + str[1];
            }
            return value;
        }

        /**
         * Address Information
         */
        public static String getAddressInformation(String data)
        {
            String value = "";

            if (StringUtils.isNotEmpty(data))
            {

                if(data.length()>12){
                    String str = data.substring(0,6);
                    value = str + "********" ;
                }
                else{
                    int count = data.length() / 2;

                    String str = data.substring(0,count);
                    value = str + "********" ;
                }


            }
            return value;
        }

        /**
         * name
         */
        public static String getName(String data)
        {
            String value = "";
            if (StringUtils.isNotEmpty(data))
            {
                if(data.length()>2){
                    String str = data.substring(2, data.length());
                    value = "**" + str;
                }else{
                    String str = data.substring(1, data.length());
                    value = "**" + str;
                }

            }
            return value;
        }

        /**
         * 社交号
         */
        public static String getSocialNumber(String data)
        {
            String value = "";
            if (StringUtils.isNotEmpty(data))
            {
                if(data.length()<4){
                    return value;
                }

                String str = data.substring(data.length()-4, data.length());
                value = "****" + str;
            }
            return value;
        }
}
