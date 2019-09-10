package com.kayisoft.util;

import org.springframework.context.annotation.Profile;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Author tianqiu.lan
 * @Date 2019/9/10
 */
public class PropertiesUtil {
    /**
     * @Title: getProfileByClassLoader
     * @Description: 采用ClassLoader(类加载器)方式进行读取配置信息
     * @return Map<String,Object> 以Map键值对方式返回配置文件内容
     * 优点：可以在非Web应用中读取配置资源信息，可以读取任意的资源文件信息
     * 缺点：只能加载类classes下面的资源文件
     * @throws
     */
    public static Map<String, Object> getProfileByClassLoader(String fileName) {
        // 通过ClassLoader获取到文件输入流对象
        // 配置文件放在resource源包下,直接写文件名即可,需要后缀名"jdbc.properties"
        // 放在包里面的,需要写上包路径,例如：在test包下"com/test/jdbc.properties"),Profile为当前所在类类名
        InputStream in = Profile.class.getClassLoader().getResourceAsStream(fileName);
        // 获取文件的位置
        String filePath = Profile.class.getClassLoader().getResource(fileName).getFile();
        System.out.println(filePath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        Properties props = new Properties();
        Map<String, Object> profileMap = new HashMap<String, Object>();
        try {
            props.load(reader);
            for (Object key : props.keySet()) {
                profileMap.put(key.toString(), props.getProperty(key.toString()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return profileMap;
    }
    public static void updateProperties(String fileName,Map<String, String> keyValueMap) {
        String filePath = Profile.class.getClassLoader().getResource(fileName).getFile();
        System.out.println("propertiesPath:" + filePath);
        Properties props = new Properties();
        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            // 从输入流中读取属性列表（键和元素对）
            br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
            props.load(br);
            br.close();
            // 写入属性文件
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath)));
            // 清空旧的文件
            // props.clear();
            for (String key : keyValueMap.keySet())
                props.setProperty(key, keyValueMap.get(key));
            props.store(bw, "改变数据");
            System.out.println(props.getProperty("url"));
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Visit " + filePath + " for updating " + "" + " value error");
        } finally {
            try {
                br.close();
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
