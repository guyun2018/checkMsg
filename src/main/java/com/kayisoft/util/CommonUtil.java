package com.kayisoft.util;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: ruopeng.cheng
 * @Date: 2018/7/12 9:30
 */
@SuppressWarnings("unused")
public class CommonUtil {



    /**
     * 字符串转换成日期
     *
     * @param dateString 日期格式字符串
     * @return date
     * @throws ParseException 异常
     */
    public static Date convertToDate(String dateString) throws ParseException {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date ;
        date = format.parse(dateString);
        return date;
    }

    /**
     * 获取当前时间
     *
     * @return 日期
     */
    public static String getCurrentTime() {
        // 设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return df.format(new Date());
    }

    /**
     * 获取当前年份
     *
     * @return 年
     */
    public static Integer getCurrentYear() {

        Calendar a = Calendar.getInstance();

        return a.get(Calendar.YEAR);
    }

    /**
     * 获取当前月份
     *
     * @return 月
     */
    public static Integer getCurrentMonth() {

        Calendar a = Calendar.getInstance();

        return a.get(Calendar.MONTH) + 1;
    }


    /**
     * 判断对象或对象数组中每一个对象是否为空: 对象为null，字符序列长度为0，集合类、Map为empty
     *
     * @param obj 对象
     * @return 判断
     */
    public static boolean isNullOrEmpty(Object obj) {

        if (obj == null) {
            return true;
        }
        if (obj instanceof CharSequence) {
            return ((CharSequence) obj).length() == 0;
        }
        if (obj instanceof Collection) {
            return ((Collection<?>) obj).isEmpty();
        }
        if (obj instanceof Map) {
            return ((Map<?, ?>) obj).isEmpty();
        }
        return obj.getClass().isArray() && Array.getLength(obj) == 0;

    }

    /**
     * 随机生成一个GUID编号
     *
     * @return GUID
     */
    public static  String getGUID() {
        // 创建 GUID 对象
        UUID uuid = UUID.randomUUID();
        // 得到对象产生的ID并转换为大写
        String guid = uuid.toString().toUpperCase();
        // 替换 - 返回
        return guid.replaceAll("-", "");
    }

    /**
     * 随机获取4位数
     * @return
     */
    public static String getRandfour(int count) {
        StringBuffer sb = new StringBuffer();
        String str = "0123456789";
        Random r = new Random();
        for (int i = 0; i < count; i++) {
            int num = r.nextInt(str.length());
            sb.append(str.charAt(num));
            str = str.replace((str.charAt(num) + ""),"");
        }
        return sb.toString();
    }
    /**
     * 将驼峰式命名的字符串转换为下划线大写方式。如果转换前的驼峰式命名的字符串为空，则返回空字符串。</br>
     * 例如：HelloWorld->HELLO_WORLD
     * @param name 转换前的驼峰式命名的字符串
     * @return 转换后下划线大写方式命名的字符串
     */
    public static String underscoreName(String name) {
        StringBuilder result = new StringBuilder();
        if (name != null && name.length() > 0) {
            // 将第一个字符处理成大写
            result.append(name.substring(0, 1).toUpperCase());
            // 循环处理其余字符
            for (int i = 1; i < name.length(); i++) {
                String s = name.substring(i, i + 1);
                // 在大写字母前添加下划线
                if (s.equals(s.toUpperCase()) && !Character.isDigit(s.charAt(0))) {
                    result.append("_");
                }
                // 其他字符直接转成大写
                result.append(s.toUpperCase());
            }
        }
        return result.toString();
    }
}
