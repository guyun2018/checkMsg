package com.kayisoft.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author ruopeng.cheng
 * @version 1.0
 * @date 2018-7-16
 */
@JsonInclude(Include.NON_NULL)
@Setter
@Getter
public class KendoGrid {

    /**
     * 显示的记录
     */
    private List<?> results;

    /**
     * 总记录数
     */
    private int count;


}
