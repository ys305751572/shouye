package com.smallchill.core.toolbox;

import com.smallchill.core.toolbox.kit.NumberKit;

import java.util.UUID;

/**
 * 公共方法
 * Created by yesong on 2016/10/24 0024.
 */
public class LeomanKit {

    /**
     * 生成6未验证码
     * @return code
     */
    public static String  generateCode(){
        return generateCode(6);
    }

    /**
     * 生成length位验证码
     * @param length 验证码长度
     * @return code
     */
    public static String generateCode(int length) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            buffer.append(NumberKit.generateRandomNumber(10));
        }
        return buffer.toString();
    }

    /**
     * 生成UUID
     * @return
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString().replace("-","");
    }
}
