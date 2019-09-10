package com.kayisoft.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kayisoft.model.ManagerQueueBean;
import com.kayisoft.util.PropertiesUtil;
import com.kayisoft.vo.KendoGrid;
import com.kayisoft.vo.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @Author tianqiu.lan
 * @Date 2019/9/10
 */
@Controller
@RequestMapping(value = "/manager")
public class ManagerController {

    @RequestMapping(value = "/index")
    public String index() {
        return "serverUrlManager";
    }

    @RequestMapping(value = "/getPropertiesInfo")
    @ResponseBody
    public KendoGrid getPropertiesInfo() {
        Map<String, Object> map = PropertiesUtil.getProfileByClassLoader("hospitalUrl.properties");
        List<ManagerQueueBean> list = new ArrayList();
        Iterator it = map.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next().toString();
            ManagerQueueBean bean = new ManagerQueueBean();
            bean.setHospitalCode(key);
            bean.setUrl(map.get(key).toString());
            list.add(bean);
        }
        KendoGrid kendoGrid = new KendoGrid();
        kendoGrid.setResults(list);
        return kendoGrid;
    }

    @RequestMapping(value = "/addAndUpdateQueue")
    @ResponseBody
    public Result addAndUpdateQueue(@RequestBody ManagerQueueBean managerQueueBean) {
        Map map = new HashMap();
        map.put(managerQueueBean.getHospitalCode(), managerQueueBean.getUrl());
        PropertiesUtil.updateProperties("hospitalUrl.properties", map);
        return new Result(true,"修改成功");
    }

    @RequestMapping(value = "/serverUrlManagerEdit/{id}")
    public String serverUrlManagerEdit(@PathVariable("id") String id, HttpServletRequest request) {
        Map<String, Object> map = PropertiesUtil.getProfileByClassLoader("hospitalUrl.properties");
        ManagerQueueBean managerQueueBean = new ManagerQueueBean();
        managerQueueBean.setHospitalCode(id);
        managerQueueBean.setUrl(map.get(id).toString());
        request.setAttribute("bean", managerQueueBean);
        return "serverUrlManagerEdit";
    }
}
