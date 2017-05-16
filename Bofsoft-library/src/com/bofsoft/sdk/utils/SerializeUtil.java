package com.bofsoft.sdk.utils;

import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializeUtil {

    private static SerializeUtil self;

    private SerializeUtil() {

    }

    public static SerializeUtil getInstence() {
        if (self == null)
            self = new SerializeUtil();
        return self;
    }

    /**
     * 序列化对象
     *
     * @param value
     * @return
     */
    public byte[] toBateArray(Object value) {
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        byte[] b = null;
        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(value);
            b = bos.toByteArray();
        } catch (IOException e) {
            Log.e("序列化失败", e.toString());
        } finally {
            try {
                if (oos != null)
                    oos.close();
                if (bos != null)
                    bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return b;
    }

    /**
     * 反序列化
     *
     * @param b
     * @return
     */
    public Object toObject(byte[] b) {
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;
        Object obj = null;
        try {
            bis = new ByteArrayInputStream(b);
            ois = new ObjectInputStream(bis);
            obj = ois.readObject();
        } catch (Exception e) {
            Log.e("反序列化失败", e.toString());
            return null;
        } finally {
            try {
                if (ois != null)
                    ois.close();
                if (bis != null)
                    bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return obj;
    }
}
