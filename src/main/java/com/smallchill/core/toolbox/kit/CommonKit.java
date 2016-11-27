package com.smallchill.core.toolbox.kit;

import java.util.UUID;

/**
 *
 * Created by yesong on 2016/11/27.
 */
public class CommonKit {

    public static String getNo() {
        return UUID.randomUUID().toString().replace("-","");
    }
}
