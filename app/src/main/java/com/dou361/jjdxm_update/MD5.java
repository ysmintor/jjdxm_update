package com.dou361.jjdxm_update;

import java.security.MessageDigest;

public class MD5 {
    public MD5() {
    }

    public static String stringToMD5(String str) {
        try {
            byte[] e = str.getBytes("utf-8");
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(e);
            return toHexString(mdTemp.digest());
        } catch (Exception var3) {
            return null;
        }
    }

    private static String toHexString(byte[] md) {
        char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        int j = md.length;
        char[] str = new char[j * 2];

        for (int i = 0; i < j; ++i) {
            byte byte0 = md[i];
            str[2 * i] = hexDigits[byte0 >>> 4 & 15];
            str[i * 2 + 1] = hexDigits[byte0 & 15];
        }

        return new String(str);
    }
}
