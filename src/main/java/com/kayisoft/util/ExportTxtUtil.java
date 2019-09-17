package com.kayisoft.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;

/**
 * @Author tianqiu.lan
 * @Date 2019/9/16
 */
public class ExportTxtUtil {

    private static final Logger logger = LoggerFactory.getLogger(ExportTxtUtil.class);


    public static void exportTxt(Map<String, Object> map, HttpServletResponse response) {
        String text = formatData(map);
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/plain");
        BufferedOutputStream bufferedOutputStream = null;
        ServletOutputStream servletOutputStream = null;
        try {
            response.addHeader("Content-Disposition", "attachment;filename=sd.txt");
            servletOutputStream = response.getOutputStream();
            bufferedOutputStream = new BufferedOutputStream(servletOutputStream);
            bufferedOutputStream.write(text.getBytes("UTF-8"));
            bufferedOutputStream.flush();
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                bufferedOutputStream.close();
                servletOutputStream.close();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }


    public static String formatData(Map<String, Object> map) {
        StringBuffer text = new StringBuffer();
        text.append("#createDate on " + System.currentTimeMillis() + "\r\n");
        Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
        if (iterator.hasNext()) {
            Map.Entry<String, Object> next = iterator.next();
            text.append(next.getKey() + "=" + next.getValue().toString() + "\r\n");
        }

        return text.toString();
    }


    public static String genAttachmentFileName(String fileName) throws UnsupportedEncodingException {
        String s = new String(fileName.getBytes("utf-8"), "ISO-8859-1");
        return s;
    }
}
