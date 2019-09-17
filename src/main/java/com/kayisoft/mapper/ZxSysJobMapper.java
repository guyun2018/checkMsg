package com.kayisoft.mapper;


import com.kayisoft.model.account.ZxSysJob;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Administrator
 */

@Mapper
@Component(value = "zxSysJobMapper")
public interface ZxSysJobMapper extends BaseMapper<ZxSysJob,String> {

    /**
     * 根据id修改数据
     * @param zxSysJob m
     */
    void updateById(ZxSysJob zxSysJob);

    /**
     * 获取列表数据
     * @param zxSysJob m
     * @return list
     */
    List<ZxSysJob> selectAll(ZxSysJob zxSysJob);

    /**
     * 获取列表
     * @param job model
     * @return list
     */
    List<ZxSysJob> selectListByPage(ZxSysJob job);
}
