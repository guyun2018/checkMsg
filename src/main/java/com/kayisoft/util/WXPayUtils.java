package com.kayisoft.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Author tianqiu.lan
 * @Date 2019/9/2
 */
public class WXPayUtils {
    /**
     * 将输入流转换为图片
     *
     * @param input    输入流
     * @param savePath 图片需要保存的路径
     * @param fileType 图片类型
     */
    public static String inputStreamToMedia(InputStream input, String savePath, String folderName, String fileName, String fileType) throws Exception {
        String filePath = savePath + "/" + folderName + "/" + fileName + "." + fileType;
        File file = new File(filePath);
        if (!file.getParentFile().exists()) {

            if (!file.getParentFile().mkdirs()) {

                throw new IOException("创建目标文件所在目录失败！");
            }
        }
        FileOutputStream outputStream = new FileOutputStream(file);
        int length;
        byte[] data = new byte[1024];
        while ((length = input.read(data)) != -1) {
            outputStream.write(data, 0, length);
        }
        outputStream.flush();
        outputStream.close();
        return filePath;
    }
}
