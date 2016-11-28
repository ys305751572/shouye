package com.smallchill.core.toolbox.kit;

import java.util.Random;
import java.util.UUID;

/**
 *
 * Created by yesong on 2016/11/27.
 */
public class CommonKit {

    public static String getNo() {
        return UUID.randomUUID().toString().replace("-","");
    }

    /**
     * 生成订单号
     */
    public static String generateSn() {
        Random r = new Random();
        int random = r.nextInt(1000);
        long current = System.currentTimeMillis();
        return String.valueOf(current) + String.valueOf(random);
    }
}
