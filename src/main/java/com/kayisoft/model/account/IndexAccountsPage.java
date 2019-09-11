package com.kayisoft.model.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 系统资源表
 *@author ruopeng.cheng
 */
@Getter
@Setter
@JsonIgnoreProperties(value = {"handler"})
public class IndexAccountsPage implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     / 资源Id
     */
    private String id;

    /**
     *  资源名称
     */
    private String menuName;

    /**
     *  菜单URL地址
     */
    private String url;

    /**
     * 显示的图标
     */

    private String icon;

    /**
     *  子节点
     */

    private List<IndexAccountsPage> children;

    /**
     * 为了生成用户的菜单树
     */
    private String userId;

    private Integer orderNum;

    private String parentId;
}
