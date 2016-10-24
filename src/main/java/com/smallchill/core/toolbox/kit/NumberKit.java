package com.smallchill.core.toolbox.kit;

import java.text.DecimalFormat;
import java.util.Random;

/**
 *
 * Created by yesong on 2016/10/20 0020.
 */
public class NumberKit {

    public static String format(Double number) {
        DecimalFormat df = new DecimalFormat("#####.00");
        return df.format(number);
    }

    /**
     * 生成随机数
     * @param scope 随机数范围
     * @return
     */
    public static int generateRandomNumber(int scope) {
        Random random = new Random();
        return random.nextInt(scope);
    }
}
