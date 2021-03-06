package com.kayisoft.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kayisoft.model.ManagerQueueBean;
import com.kayisoft.util.ExportTxtUtil;
import com.kayisoft.util.PropertiesUtil;
import com.kayisoft.vo.KendoGrid;
import com.kayisoft.vo.Result;
import org.apache.http.HttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @Author tianqiu.lan
 * @Date 2019/9/10
 */
@Controller
@RequestMapping(value = "/manager")
public class ManagerController {

    @RequestMapping(value = "/init")
    public String init(){
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
        ManagerQueueBean managerQueueBean = new ManagerQueueBean();
        if (!"0".equals(id)) {
            Map<String, Object> map = PropertiesUtil.getProfileByClassLoader("hospitalUrl.properties");
            managerQueueBean.setHospitalCode(id);
            managerQueueBean.setUrl(map.get(id).toString());
        }
        request.setAttribute("bean", managerQueueBean);
        return "serverUrlManagerEdit";
    }

    @RequestMapping(value = "/export")
    public void export(HttpServletResponse response){
        Map<String, Object> map = PropertiesUtil.getProfileByClassLoader("hospitalUrl.properties");
        ExportTxtUtil.exportTxt(map,response);
    }
}
