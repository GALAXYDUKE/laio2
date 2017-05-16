package com.bofsoft.sdk.utils;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class AssetsWriteToSD {

    private Context context;
    String fileName = "";
    String filePath = "";

    public AssetsWriteToSD(Context context, String fileName, String filePath) {
        this.context = context;
        this.filePath = filePath;
        this.fileName = fileName;
        write();
    }

    private void write() {
        InputStream inputStream;
        try {
            inputStream = context.getResources().getAssets().open(fileName);
            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(filePath + fileName);
            byte[] buffer = new byte[1024];
            int count = 0;
            while ((count = inputStream.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, count);
            }
            fileOutputStream.flush();
            fileOutputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isExist() {
        File file = new File(filePath);
        return file.exists();
    }

}
