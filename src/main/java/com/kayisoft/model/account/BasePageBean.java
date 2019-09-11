package com.kayisoft.model.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ruopeng.cheng
 * @date 2018-7-10
 */
@Getter
@Setter
public class BasePageBean extends BaseBean {

    /**
     *
     */
    private static final long serialVersionUID = -1339035742147647904L;

    @JsonIgnore
    private String sort;

    @JsonIgnore
    private String order;

    @JsonIgnore
    private Integer page;

    @JsonIgnore
    private Integer pageSize;

    /**
     * 多少条
     */
    @JsonIgnore
    private Integer take;

    /**
     * layui排序名称
     */
    @JsonIgnore
    private String filed;
    /**
     * layui 窗口切换参数
     */
    @JsonIgnore
    private Integer reportType;

    /**
     * 从第几条开始
     */
    @JsonIgnore
    private Integer skip;

    /**
     * 关键字查找
     */
    private String searchKey;

}
