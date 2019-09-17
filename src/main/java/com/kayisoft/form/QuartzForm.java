package com.kayisoft.form;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @Author: ruopeng.cheng
 * @Date: 2018/9/18 9:55
 */
@Setter
@Getter
@JsonIgnoreProperties(value = {"handler"})
public class QuartzForm {

    @NotBlank(message = "ID不能为空")
    private String id;

    private String jobName;

    private Integer jobStatus;

    private String cron;

    private String classPath;

    private String jobDesc;

    private String systemCode;
}
