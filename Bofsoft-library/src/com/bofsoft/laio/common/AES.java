package com.bofsoft.laio.common;

import java.util.zip.DataFormatException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AES {
    static MyLog mylog = new MyLog(AES.class);

    // 加密
    public static String encrypt(String content, String password) {
        return parseByte2HexStr(encrypt(content.getBytes(), password.getBytes()));
    }

    public static byte[] encrypt(byte[] content, byte[] password) {
        try {
            byte[] enCodeFormat = new byte[16];
            for (int i = 0; i < 16; i++) {
                if (i < password.length)
                    enCodeFormat[i] = password[i];
                else
                    enCodeFormat[i] = 0;
            }
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");// 创建密码器
            byte[] fcontent = null;
            if (content.length % 16 != 0) {
                fcontent = new byte[((content.length / 16) + 1) * 16];
                for (int i = 0; i < fcontent.length; i++) {
                    if (i < content.length)
                        fcontent[i] = content[i];
                    else
                        fcontent[i] = 0;
                }
            } else {
                fcontent = content;
            }
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(fcontent);
            return result; // 加密
        } catch (Exception e) {
            e.printStackTrace();
            mylog.e(e.getStackTrace());
        }
        return null;
    }

    // 解密
    public static String decrypt(String content, String password) throws Exception {
        return decrypt(parseHexStr2Byte(content), password.getBytes());
    }

    public static String decrypt(byte[] content, byte[] password) {
        try {
            byte[] enCodeFormat = new byte[16];
            for (int i = 0; i < 16; i++) {
                if (i < password.length)
                    enCodeFormat[i] = password[i];
                else
                    enCodeFormat[i] = 0;
            }
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(content);
            return (new String(result)).replace("\0", ""); // 加密
        } catch (Exception e) {
            e.printStackTrace();
            mylog.e(e.getStackTrace());
        }
        return null;
    }

    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) throws DataFormatException {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

}
