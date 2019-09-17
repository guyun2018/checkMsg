package com.kayisoft.model.account;

import lombok.Getter;
import lombok.Setter;

/**
 * ZX_SYS_JOB
 * @author
 */
@Setter
@Getter
public class ZxSysJob extends BasePageBean {
    private String id;

    private String jobName;

    private String cron;

    private Integer jobStatus;

    private String classPath;

    private String jobDesc;

    private String lastUpdateUser;

    private String systemCode;

    private static final long serialVersionUID = 1L;


}
