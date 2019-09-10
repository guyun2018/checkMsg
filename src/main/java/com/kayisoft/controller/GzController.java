package com.kayisoft.controller;

import com.kayisoft.service.QueueService;
import com.kayisoft.util.CheckoutUtil;
import com.kayisoft.util.MessageUtil;
import com.kayisoft.util.WXPayUtils;
import com.kayisoft.util.XMLUtil;
import com.kayisoft.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author tianqiu.lan
 * @Date 2019/9/2
 */
@Controller
public class GzController {

    @Autowired
    QueueService queueService;

    /**
     * 公众号token认证url
     */
    @RequestMapping(value = "/wechat", method = RequestMethod.GET)
    public void checkAuth(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("开始校验");
        PrintWriter print;
        // 微信加密签名
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");
        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        if (signature != null && CheckoutUtil.checkSignature(signature, timestamp, nonce)) {
            try {
                System.out.println("校验通过");
                print = response.getWriter();
                print.write(echostr);
                print.flush();
            } catch (IOException e) {
                System.out.println("校验失败");
                e.printStackTrace();
            }
        }
    }

    /**
     * 处理交互行为
     *
     * @param request  请求体
     * @param response 响应体
     */
    @RequestMapping(value = "/wechat", method = RequestMethod.POST, produces = {"application/xml;charset=utf-8"})
    public void doRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //将XML转为Map
        Map<String, String> map = XMLUtil.getMap(request.getInputStream());
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();

        //这里不要弄混了，微信推过来的信息是用户发过来的，所以ToUserName是我们的公众号，FromUserName是用户的微信openid
        //所以我们既然要回复过去，就要颠倒过来
        String fromUser = map.get("ToUserName");
        String toUser = map.get("FromUserName");
        //携带的参数
        String uuid = map.get("EventKey");
        String content = "";

        //先判断是事件消息，还是普通消息
        if (map.get("MsgType").equals("event")) {

            //如果是被关注事件，向用户回复内容，只需要将整理好的XML文本参数返回给微信即可
            if (map.get("Event").equals("subscribe")) {
                System.out.println("关注");
                if (!"".equals(uuid)) {
                    //获取和openid，存入表进行关联
                    queueService.addOpenId(uuid.substring(8), toUser);
                }
                content = "欢迎关注卡易智慧的测试公众号!";
                //把数据包返回给微信服务器，微信服务器再推给用户
                writer.print(MessageUtil.setMessage(fromUser, toUser, content));
            }
            if (map.get("Event").equals("unsubscribe")) {
                System.out.println("取消关注");
                //获取和openid，存入表进行关联
                queueService.deleteOpenId(toUser);
            }
            if (map.get("Event").equals("SCAN")) {
                // 返回消息时ToUserName的值与FromUserName的互换
                Map<String, String> returnMap = new HashMap<>();
                returnMap.put("ToUserName", toUser);
                returnMap.put("FromUserName", fromUser);
                returnMap.put("CreateTime", System.currentTimeMillis() + "");
                returnMap.put("MsgType", "text");
                returnMap.put("Content", "https://www.baidu.com");

                content = "查询排队情况请回复【1】";

//                String encryptMsg = WXPublicUtils.encryptMsg(WXPayUtils.mapToXml(returnMap), new Date().getTime()+"", WXPublicUtils.getRandomStr());
//                return encryptMsg;
                writer.print(MessageUtil.setMessage(fromUser, toUser, content));
                writer.close();

            }
        } else if (map.get("MsgType").equals("text")) {
            //如果是普通文本消息，先拿到用户发送过来的内容，模拟自动答疑的场景
            String text = map.get("Content");

            if (text.equals("1")) {
                Result result = queueService.sendMsg(toUser);
            } else if (text.equals("2")) {
                content = "如果您购买了本店的产品，订单页面会展示在您的主菜单中";
                //把数据包返回给微信服务器，微信服务器再推给用户
                writer.print(MessageUtil.setMessage(fromUser, toUser, content));
                writer.close();
            } else if (text.equals("3")) {
                content = "如有更多问题，请拨打我们的客服热线：xxxxx";
                //把数据包返回给微信服务器，微信服务器再推给用户
                writer.print(MessageUtil.setMessage(fromUser, toUser, content));
                writer.close();
            } else {
                //否则，不管用户输入什么，都返回给ta这个列表，这也是最常见的场景
                content = "请输入您遇到的问题编号：\n" +
                        "1、如何查看退款进度？\n" +
                        "2、我的订单在哪里查看？\n" +
                        "3、其他问题";
                //把数据包返回给微信服务器，微信服务器再推给用户
                writer.print(MessageUtil.setMessage(fromUser, toUser, content));
                writer.close();
            }
        }

    }

}
