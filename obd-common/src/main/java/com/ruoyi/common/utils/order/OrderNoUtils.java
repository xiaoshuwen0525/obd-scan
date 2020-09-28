package com.ruoyi.common.utils.order;

import java.util.Random;

/**
 * Created by LCX on 2020/3/23
 */
public class OrderNoUtils {
    public static String generateOrderNo() {
        Random random = new Random();
        String result = "";
        for (int i = 0; i < 5; i++) {
            result += random.nextInt(10);

        }

        //随机数加上时间戳
        result = result+System.currentTimeMillis();

        return result;
    }
}
