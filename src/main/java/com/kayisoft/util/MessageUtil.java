package com.kayisoft.util;

/**
 * @Author tianqiu.lan
 * @Date 2019/9/2
 */

public class MessageUtil {
    /**
     * 要回复的消息
     * @param fromUser 发送方
     * @param toUser 接收方
     * @param content 回复给用户的内容
     * @return 整理好的XML文本
     * */
    public static String setMessage(String fromUser,String toUser,String content){

        return "<xml>\n" +
                "  <ToUserName><![CDATA["+toUser+"]]></ToUserName>\n" +
                "  <FromUserName><![CDATA["+fromUser+"]]></FromUserName>\n" +
                "  <CreateTime>12345678</CreateTime>\n" +
                "  <MsgType><![CDATA[text]]></MsgType>\n" +
                "  <Content><![CDATA["+content+"]]></Content>\n" +
                "</xml>";
    }
}
