package com.kayisoft.controller;

import com.kayisoft.model.account.IndexAccountsPage;
import com.kayisoft.vo.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: ruopeng.cheng
 * @date: 2019/8/1 15:59
 */
@Controller
public class IndexController{

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(){
        return "index";
    }


    @RequestMapping(value = "/index/getMenuTree",method = RequestMethod.POST)
    @ResponseBody
    public Result getMenuTree() {

        try{

            Result result = new Result();

            List<IndexAccountsPage> list = new ArrayList<>();
            IndexAccountsPage indexAccountsPage = new IndexAccountsPage();
            indexAccountsPage.setId("B447A63502284A3691C2B8DC30F90697");
            indexAccountsPage.setParentId("a");
            indexAccountsPage.setMenuName("服务路径管理");
            indexAccountsPage.setUrl("/manager/init");

            list.add(indexAccountsPage);

            List<IndexAccountsPage> pageList = new ArrayList<>();
            IndexAccountsPage page = new IndexAccountsPage();
            page.setId("a");
            page.setIcon("fa fa-windows");
            page.setParentId("1");
            page.setMenuName("预约检查");
            page.setChildren(list);
            pageList.add(page);

            result.setResults(pageList);
            result.setSuccess(true);

            return result;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
