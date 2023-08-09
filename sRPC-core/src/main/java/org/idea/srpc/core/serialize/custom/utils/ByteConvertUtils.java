package org.idea.srpc.core.serialize.custom.utils;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * 字节转换工具类
 */
public class ByteConvertUtils {
    /**
     * 将int类型的值转换成字节数组
     *
     * @param n int类型的值
     * @return 字节数组
     */
    public static byte[] intToBytes(int n) {
        byte[] buf = new byte[Integer.BYTES];
        for (int i = 0; i < Integer.BYTES; i++) {
            buf[i] = (byte) (n >> (8 * i));
        }
        return buf;
    }

    /**
     * 将一个long值，转换成字节数组
     * @param l long值
     * @return 字节数组
     */
    public static byte[] longToBytes(long l) {
        byte[] buf = new byte[Long.BYTES];
        for (int i = 0; i < Long.BYTES; i++) {
            buf[i] = (byte) (l >> (8 * i));
        }
        return buf;
    }

    /**
     * short值转换成字节数组
     * @param s short值
     * @return 字节数组
     */
    public static byte[] shortToBytes(short s) {
        byte[] buf = new byte[Short.BYTES];
        for (int i = 0; i < Short.BYTES; i++) {
            buf[i] = (byte) (s >> (8 * i));
        }
        return buf;
    }

    /**
     * 将一个字节转换成字符串显示
     * @param b 字节
     * @return 字节字符串
     */
    public static String byteToBit(byte b) {
        return ""
                + (byte) ((b >> 7) & 0x1) + (byte) ((b >> 6) & 0x1)
                + (byte) ((b >> 5) & 0x1) + (byte) ((b >> 4) & 0x1)
                + (byte) ((b >> 3) & 0x1) + (byte) ((b >> 2) & 0x1)
                + (byte) ((b >> 1) & 0x1) + (byte) ((b >> 0) & 0x1);
    }

    /**
     * 将bytes数组转换成int值
     * @param bytes 待转换的bytes数组
     * @return int值
     */
    public static int bytesToInt(byte[] bytes) {
        if (bytes.length != 4) throw new IllegalArgumentException("bytes size must be 4");
        return (bytes[0]) & 0xff | (bytes[1] << 8) & 0xff00 | (bytes[2] << 16) & 0xff0000 | (bytes[3] << 24) & 0xff000000;
    }

    /**
     * bytes数组转换成short值
     * @param bytes bytes数组
     * @return short值
     */
    public static short bytesToShort(byte[] bytes) {
        if (bytes.length != 2) throw new IllegalArgumentException("bytes size must be 2");
        return (short) ((bytes[0]) & 0xff | (bytes[1] << 8) & 0xff00);
    }

    /**
     * bytes数组转换成long值
     * @param bytes bytes数组
     * @return long值
     */
    public static long bytesToLong(byte[] bytes) {
        if (bytes.length != 8) throw new IllegalArgumentException("bytes size must be 8");
        return ((long) bytes[0]) & 0xff
                | (((long) bytes[1]) << 8 & 0xff00)
                | (((long) bytes[2]) << 16 & 0xff0000)
                | (((long) bytes[3]) << 24 & 0xff000000)
                | (((long) bytes[4]) << 32 & 0xff00000000L)
                | (((long) bytes[5]) << 40 & 0xff0000000000L)
                | (((long) bytes[6]) << 48 & 0xff000000000000L)
                | (((long) bytes[7]) << 56 & 0xff00000000000000L);
    }

    public static byte[] stringToBytes(String str) {
        if (str == null || str.length() == 0) {
            return new byte[0];
        }
        byte[] bytes = new byte[str.length()];
        char[] chars = str.toCharArray();
        for (int i = 0; i < bytes.length; i++) {
            int k = chars[i];
            bytes[i] = (byte) k;
        }
        return bytes;
    }
    public static String bytesToString(byte[] bytes) {
        if (bytes.length == 0) {
            return null;
        }
        int len = bytes.length;
        char[] encodeChar = new char[len];
        for (int i = 0; i < bytes.length; i++) {
            encodeChar[i] = (char) bytes[i];
        }
        return String.valueOf(encodeChar);
    }
    public static String convertForList(Object objList) {
        return JSON.toJSONString(objList, true);
    }
    public static <T> List<T> convertForListFromFile(String listString, Class<T> clazz) {
        return JSON.parseArray(listString, clazz);
    }
}
