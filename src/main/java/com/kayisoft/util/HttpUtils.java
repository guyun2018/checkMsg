package com.kayisoft.util;


import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j2;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;

/**
 * @Author tianqiu.lan
 * @Date 2019/9/2
 */
@Log4j2
public class HttpUtils {
    /**
     * 发送https请求
     * @param requestUrl 请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param data 提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
//    public static JSONObject httpsRequest(String requestUrl, String requestMethod, String data) {
//        JSONObject jsonObject = null;
//        InputStream inputStream = null;
//        InputStreamReader inputStreamReader = null;
//        BufferedReader bufferedReader = null;
//        try {
//            URL url = new URL(requestUrl);
//            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
//            conn.setDoOutput(true);
//            conn.setDoInput(true);
//            conn.setUseCaches(false);
//            // 设置请求方式（GET/POST）
//            conn.setRequestMethod(requestMethod);
//            conn.connect();
//            // 当data不为null时向输出流写数据
//            if (null != data) {
//                // getOutputStream方法隐藏了connect()方法
//                OutputStream outputStream = conn.getOutputStream();
//                // 注意编码格式
//                outputStream.write(data.getBytes("UTF-8"));
//                outputStream.close();
//            }
//            // 从输入流读取返回内容
//            inputStream = conn.getInputStream();
//            inputStreamReader = new InputStreamReader(inputStream, "utf-8");
//            bufferedReader = new BufferedReader(inputStreamReader);
//            String str = null;
//            StringBuffer buffer = new StringBuffer();
//            while ((str = bufferedReader.readLine()) != null) {
//                buffer.append(str);
//            }
//            conn.disconnect();
//            jsonObject = JSONObject.fromObject(buffer.toString());
//            return jsonObject;
//        } catch (Exception e) {
//            logger.error("发送https请求失败，失败", e);
//            return null;
//        } finally {
//            // 释放资源
//            try {
//                if(null != inputStream) {
//                    inputStream.close();
//                }
//                if(null != inputStreamReader) {
//                    inputStreamReader.close();
//                }
//                if(null != bufferedReader) {
//                    bufferedReader.close();
//                }
//            } catch (IOException e) {
//                logger.error("释放资源失败，失败", e);
//            }
//        }
//    }
    /**
     * 发送https请求，返回二维码图片
     * @param requestUrl 请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param data 提交的数据
     * @param savePath 图片保存路径
     * @param fileName 图片名称
     * @param fileType 图片类型
     * @return filePath 图片路径
     */
    public static String httpsRequestPicture(String requestUrl, String requestMethod, String data, String savePath,String folderName, String fileName, String fileType) throws Exception {
        InputStream inputStream = null;
        try {
            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            //设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);
            conn.connect();
            //当data不为null时向输出流写数据
            if (null != data) {
                //getOutputStream方法隐藏了connect()方法
                OutputStream outputStream = conn.getOutputStream();
                //注意编码格式
                outputStream.write(data.getBytes("UTF-8"));
                outputStream.close();
            }
            // 从输入流读取返回内容
            inputStream = conn.getInputStream();
            System.out.println("开始生成微信二维码...");
            String filePath = WXPayUtils.inputStreamToMedia(inputStream, savePath, folderName, fileName, fileType);
            System.out.println("微信二维码生成成功!!!");
            conn.disconnect();
            return filePath;
        } catch (Exception e) {
            log.info(e);
            throw new Exception(e);
        }finally {
            //释放资源
            try {
                if(null != inputStream) {
                    inputStream.close();
                }
            } catch (IOException e) {
                log.info(e);
            }
        }
    }
}
