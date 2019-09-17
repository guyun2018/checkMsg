package com.kayisoft.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import com.kayisoft.model.QueueUserInfo;

@Mapper
public interface QueueUserInfoMapper {
    int insert(@Param("queueUserInfo") QueueUserInfo queueUserInfo);

    int insertSelective(@Param("queueUserInfo") QueueUserInfo queueUserInfo);

    int insertList(@Param("queueUserInfos") List<QueueUserInfo> queueUserInfos);

    int updateByPrimaryKeySelective(@Param("queueUserInfo") QueueUserInfo queueUserInfo);

    QueueUserInfo selectOpenId(QueueUserInfo queueUserInfo);

    QueueUserInfo selectHospitalCode(QueueUserInfo queueUserInfo);

    List<QueueUserInfo> selectInfoByOpenId(String openId);

    void deleteByOpenId(String openId);
}
