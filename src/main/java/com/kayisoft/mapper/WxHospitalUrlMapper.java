package com.kayisoft.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import com.kayisoft.model.UrlBean;

@Mapper
public interface WxHospitalUrlMapper {
    int insert(@Param("urlBean") UrlBean urlBean);

    int insertSelective(@Param("urlBean") UrlBean urlBean);

    int insertList(@Param("urlBeans") List<UrlBean> urlBeans);

    int updateByPrimaryKeySelective(@Param("urlBean") UrlBean urlBean);

    UrlBean selectByHospitalCode(String hospitalCode);
}
