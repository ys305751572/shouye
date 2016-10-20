package com.smallchill.core.toolbox.kit;

import java.text.DecimalFormat;

/**
 *
 * Created by yesong on 2016/10/20 0020.
 */
public class NumberKit {

    public static String format(Double number) {
        DecimalFormat df = new DecimalFormat("#####.00");
        return df.format(number);
    }
}
