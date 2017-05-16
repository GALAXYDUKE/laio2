package com.bofsoft.laio.common;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtil {

    static MyLog mylog = new MyLog(FileUtil.class);

    public boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        return state != null && state.equalsIgnoreCase(Environment.MEDIA_MOUNTED);
    }

    public static boolean createFile(String filePath) {
        if (filePath != null && !filePath.equals("")) {
            File file = new File(filePath);
            if (file.isDirectory()) {
                if (file.exists()) {
                    mylog.e(FileUtil.class.toString() + "[" + filePath + "]:"
                            + "the path is already exist of create file!");
                    return false;
                } else {
                    return file.mkdirs();
                }
            } else {
                if (file.exists()) {
                    mylog.e(FileUtil.class.toString() + "[" + filePath + "]:"
                            + ": the path is already exist of create file!");
                    file.delete();
                }

                File parent = file.getParentFile();
                if (!parent.exists()) {
                    parent.mkdirs();
                }
                try {
                    return file.createNewFile();
                } catch (IOException e) {
                    mylog.e(FileUtil.class.toString() + "[" + filePath + "]:"
                            + ": create the file  the is failure! ");
                    return false;
                }
            }
        } else {
            mylog.e(FileUtil.class.toString() + ": the path is empty of create file!");
            return false;
        }
    }

    /**
     * 删除文件或 文件夹
     *
     * @param path 文件或文件夹路径
     * @return
     */
    public static boolean deleteFile(String path) {
        boolean flag = true;
        if (path == null || path.equals("")) {
            return flag;
        }
        File file = new File(path);

        if (!file.exists()) {
            mylog.e("filepath=" + file.getAbsolutePath() + " is not exist!");
            return flag;
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                deleteFile(files[i].getAbsolutePath());
            }
        }
        boolean state = file.delete();
        if (state == false) {
            flag = false;
        }
        mylog.e("filepath=" + file.getAbsolutePath() + " delete: " + state);
        return flag;
    }

    /**
     * 删除文件或 文件夹
     *
     * @param file 文件或文件夹
     * @return
     */
    public static boolean deleteFile(File file) {
        boolean flag = true;
        if (file == null) {
            return flag;
        }
        if (!file.exists()) {
            mylog.e("filepath=" + file.getAbsolutePath() + " is not exist!");
            return flag;
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                deleteFile(files[i]);
            }
        }
        boolean state = file.delete();
        if (state == false) {
            flag = false;
        }
        mylog.e("filepath=" + file.getAbsolutePath() + " delete: " + state);
        return flag;
    }

    public static boolean isFileExist(String path) {
        boolean flag = true;
        if (path == null || path.equals("")) {
            return flag;
        }
        File file = new File(path);
        return file.exists();
    }

    public static boolean isFileExist(File file) {
        boolean flag = true;
        if (file == null) {
            return flag;
        }
        return file.exists();
    }

    /**
     * 根据文件路径获取文件名（包括后缀）
     *
     * @param path
     * @return
     */
    public static String getFileNameByPath(String path) {
        if (path == null || path.equals("")) {
            mylog.e(">>getFileNameByPath>> " + "the path is empty!");
            return "";
        }
        String filepath = path.trim();
        File tempFile = new File(filepath);
        return tempFile.getName();
    }

    /**
     * 从一个输入流里写文件
     *
     * @param destFilePath 要创建的文件的路径
     * @param in           要读取的输入流
     * @return 写入成功返回true, 写入失败返回false
     */
    public static boolean writeFile(String destFilePath, InputStream in) {
        try {
            if (!createFile(destFilePath)) {
                return false;
            }
            FileOutputStream fos = new FileOutputStream(destFilePath);
            int readCount = 0;
            int len = 1024;
            byte[] buffer = new byte[len];
            while ((readCount = in.read(buffer)) != -1) {
                fos.write(buffer, 0, readCount);
            }
            fos.flush();
            if (null != fos) {
                fos.close();
                fos = null;
            }
            if (null != in) {
                in.close();
                in = null;
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 将一个文件拷贝到另外一个地方
     *
     * @param sourceFile    源文件地址
     * @param destFile      目的地址
     * @param shouldOverlay 是否覆盖
     * @return
     */
    public static boolean copyFiles(String sourceFile, String destFile, boolean shouldOverlay) {
        try {
            if (shouldOverlay) {
                deleteFile(destFile);
            }
            FileInputStream fi = new FileInputStream(sourceFile);
            writeFile(destFile, fi);
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

}
