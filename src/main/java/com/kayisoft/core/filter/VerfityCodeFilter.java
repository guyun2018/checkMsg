package com.kayisoft.core.filter;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author ruopeng.cheng
 * @date 2018-6-25
 * 验证码拦截
 */
@Getter
@Setter
@SuppressWarnings("all")
public class VerfityCodeFilter extends AccessControlFilter {
    /**
     * 是否开启验证码验证   默认true
     */
    private boolean verfitiCode = true;

    /**
     * 前台提交的验证码name
     */
    private String jcaptchaParam = "code";

    /**
     * 验证失败后setAttribute key
     */
    private String failureKeyAttribute = "shiroLoginFailure";

    private String httpMethod = "post";

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object o) throws Exception {
        //暂时未用到非验证码
        request.setAttribute("verfitiCode", verfitiCode);
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        //2、判断验证码是否禁用 或不是表单提交
        if (!verfitiCode || !httpMethod.equalsIgnoreCase(httpRequest.getMethod())) {
            return true;
        }
        Object code = getSubject(request, response).getSession().getAttribute("_code");
        String storedCode = null;
        if (null != code) {
            storedCode = code.toString();
        }
        //表单提交，校验验证码的正确性
        String currentCode = httpRequest.getParameter(jcaptchaParam);

        return StringUtils.equalsIgnoreCase(storedCode, currentCode);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        servletRequest.setAttribute(failureKeyAttribute, "code.error");
        return true;
    }


}
