package com.kayisoft.controller;



import com.kayisoft.model.account.CustomUsernamePasswordToken;
import com.kayisoft.model.account.UserBean;
import com.kayisoft.util.VerifyCodeUtils;
import com.kayisoft.vo.Result;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * 登陆退出
 *
 * @author ruopeng.cheng
 * @date 2018-6-29
 */
@Controller
@Log4j2
public class LoginController{




    /**
     * 首页
     *
     * @return view
     */
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String redirectLogin() {
        return "redirect:index";
    }


    /**
     * GET 登录
     *
     * @return view
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        log.info("GET请求登录");
        if (SecurityUtils.getSubject().isAuthenticated()) {
            return "redirect:/index";
        }
        return "/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Result login(UserBean userBean, HttpServletRequest request) {
        Result result = new Result();
//        String codeMsg = (String) request.getAttribute("shiroLoginFailure");
//        String codeError = "code.error";
        String msg;
//        if (codeError.equals(codeMsg)) {
//            result.setMsg("验证码错误");
//            return result;
//        }
        CustomUsernamePasswordToken token = new CustomUsernamePasswordToken(userBean.getUsername(),
                userBean.getPassword(),"UserLogin");
        Subject subject = SecurityUtils.getSubject();

        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            msg = "账号问题：" + e.getMessage();
            result.setMsg(msg);
            return result;
        } catch (IncorrectCredentialsException e) {
            msg = "密码错误";
            result.setMsg(msg);
            return result;
        } catch (ExcessiveAttemptsException e) {
            msg = "登录失败多次，账户锁定10分钟";
            result.setMsg(msg);
            return result;
        } catch (AuthenticationException e){
            msg = "账号或密码错误！";
            result.setMsg(msg);
            return result;
        }
        result.setSuccess(true);
        return result;
    }
    @RequestMapping(value = "/getCode", method = RequestMethod.GET)
    public void getCode(HttpServletResponse response, HttpServletRequest request) {
        try {
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType("image/jpg");

            //生成随机字串
            String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
            log.info("verifyCode:{}", verifyCode);
            //存入会话session
            HttpSession session = request.getSession(true);
            session.setAttribute("_code", verifyCode.toLowerCase());
            //生成图片
            int w = 146, h = 33;
            VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * 退出
     * @return result
     */
    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    @ResponseBody
    public Result logout() {
        log.info("登出");
        Subject subject = SecurityUtils.getSubject();
        Result result = new Result();
        subject.logout();
        result.setSuccess(true);
        return result;
    }

}
