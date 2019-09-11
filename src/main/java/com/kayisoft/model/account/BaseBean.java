package com.kayisoft.model.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ruopeng.cheng
 * @date 2018-6-25 10:09:41
 */
@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class BaseBean implements Serializable {

    /**
     *
     */
    private static final Long serialVersionUID = 8078206827726774278L;

    private String createUser;

    private String createDate;

    @JsonIgnore
    private String lastUpdateUser;

    @JsonIgnore
    private Date lastUpdateDate;

    @JsonIgnore
    private Boolean delFlag;

    @JsonIgnore
    private String delUser;

    @JsonIgnore
    private String delDate;

    @JsonIgnore
    private String currentUser;

    private String wxUserId;

}
