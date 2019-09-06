package com.kayisoft.mapper;

import java.io.Serializable;

/**
 * @author ruopeng,cheng
 * @date 2018-7-12

 */

public interface BaseMapper<T,E extends Serializable>{

    /**
     * 根据id删除
     * @param id id
     * @return int
     */
    int deleteByPrimaryKey(E id);

    /**
     * 插入
     * @param record model
     * @return int
     */
    int insert(T record);

    /**
     *插入非空字段
     * @param record model
     * @return int
     */
    int insertSelective(T record);

    /**
     * 根据id查询
     * @param id id
     * @return bean
     */
    T selectByPrimaryKey(E id);

    /**
     * 更新非空数据
     * @param record model
     * @return int
     */
    int updateByPrimaryKeySelective(T record);

    /**
     * 更新
     * @param record model
     * @return int
     */
    int updateByPrimaryKey(T record);

}
