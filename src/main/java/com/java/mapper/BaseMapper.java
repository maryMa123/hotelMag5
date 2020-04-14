package com.java.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *  基础Mapper代理对象
 * @param <T>  传入的实体对象泛型
 */
public interface BaseMapper<T> {

    /**
     *   根据主键删除单个数据
     * @param id  主键id
     * @return  删除结果
     */
    Integer deleteByPrimaryKey(Integer id);

    /**
     *   添加数据
     * @param t  添加数据对象
     * @return  添加结果
     */
    Integer insert(T t);

    /**
     *   动态添加数据
     * @param t  添加数据对象
     * @return  添加结果
     */
    Integer insertSelective(T t);

    /**
     *   根据主键查询单个数据
     * @param id  主键id
     * @return  单个数据结果
     */
    T selectByPrimaryKey(Integer id);

    /**
     *   根据主键动态修改数据
     * @param t  要修改的对象数据
     * @return  修改结果
     */
    Integer updateByPrimaryKeySelective(T t);

    /**
     *   根据主键修改所有字段数据
     * @param t  要修改的对象数据
     * @return 修改结果
     */
    Integer updateByPrimaryKey(T t);

    /**
     *   根据条件分页查询数据
     * @param t  查询的条件
     * @return  分页查询的数据
     * @throws Exception
     *
     * @Param("t") 指明传入到Mapper.xml中的对象名称为"t"
     */
    List<T> selectPageByPramas(@Param("t") T t);

    /**
     *   根据条件查询单个数据
     * @param t  条件对象
     * @return  查询的单个数据
     * @throws Exception
     */
    T selectByPramas(@Param("t") T t);

    /**
     *   根据条件查询多个数据
     * @param t 条件参数
     * @return  查询的多条数据
     * @throws Exception
     */
    List<T> selectManyByPramas(@Param("t") T t);

    /**
     *   根据多个主键ids动态批量修改数据
     * @param ids  多个主键ids数组
     * @param t  修改的数据
     * @return  操作结果
     */
     Integer updBatchByPrimaryKeySelective(@Param("ids") Integer[] ids, @Param("t") T t);


    /**
     * 根据条件查询数据条数(与查询某条数据同义)
     * @param t
     * @return
     */
    Integer getCountByPramas(@Param("t") T t);


    /**
     *   查询表的所有数据
     * @return  数据结果集
     */
    List<T>  selectAll();

}
