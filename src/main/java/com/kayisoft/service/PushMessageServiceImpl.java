package com.kayisoft.service;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @Author tianqiu.lan
 * @Date 2019/9/6
 */
@Service
@Slf4j
public class PushMessageServiceImpl implements PushMessageService {

    @Autowired
    WxMpService wxMpService;

    @Override
    public void templateOrderStatus(Object object) {

        //模板消息封装的对象
        WxMpTemplateMessage wxMpTemplateMessage = new WxMpTemplateMessage();
        wxMpTemplateMessage.setTemplateId("KgKKraydHLZWaXgXphe3BHbXU7d5KjAwCeTk_mBKz0s");
        wxMpTemplateMessage.setToUser("要发送的用户的openid");

        List<WxMpTemplateData> wxMpTemplateData = Arrays.asList(
                new WxMpTemplateData("first", "订单完结"),
                new WxMpTemplateData("keyword1", "微信点餐快餐店"),
                new WxMpTemplateData("keyword2", "11111111")
                );
        wxMpTemplateMessage.setData(wxMpTemplateData);
        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(wxMpTemplateMessage);
        } catch (WxErrorException errorException) {
            log.error("【微信模板消息推送】出现错误！");

        }
    }
}
