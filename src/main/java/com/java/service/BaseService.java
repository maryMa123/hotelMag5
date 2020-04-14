package com.java.service;

import java.util.List;
import java.util.Map;

/**
 *   基础业务层接口
 * @param <T>  传入的实体对象泛型
 */
public interface BaseService<T> {

    /**
     *   根据主键删除单个数据
     * @param id  主键id
     * @return  删除结果
     */
    String removeByPrimaryKey(Integer id);

    /**
     *   添加数据
     * @param t  添加数据对象
     * @return  添加结果
     */
    String save(T t);

    /**
     *   动态添加数据
     * @param t  添加数据对象
     * @return  添加结果
     */
    String saveSelective(T t);

    /**
     *   根据主键查询单个数据
     * @param id  主键id
     * @return  单个数据结果
     */
    T findByPrimaryKey(Integer id);

    /**
     *   根据主键动态修改数据
     * @param t  要修改的对象数据
     * @return  修改结果
     */
    String updateByPrimaryKeySelective(T t);

    /**
     *   根据主键修改所有字段数据
     * @param t  要修改的对象数据
     * @return 修改结果
     */
    String updateByPrimaryKey(T t);

    /**
     *   根据条件分页加载数据
     * @param page  当前页
     * @param limit  每一页数据条数
     * @param t  查询的条件
     * @return  Layui的table方法渲染需要的分页数据集合
     */
    Map<String,Object> findPageByPramas(Integer page, Integer limit, T t);

    /**
     *   根据条件查询单个数据
     * @param t  条件对象
     * @return  查询的单个数据
     * @throws Exception
     */
    T findByPramas(T t);

    /**
     *   根据条件查询多个数据
     * @param t 条件参数
     * @return  查询的多条数据
     * @throws Exception
     */
    List<T> findManyByPramas(T t);

    /**
     *   根据多个主键ids动态批量修改数据
     * @param ids  多个主键ids数组
     * @param t  修改的数据
     * @return  操作结果
     */
    String updBatchByPrimaryKeySelective(Integer[] ids, T t);



    /**
     * 根据条件查询数据条数(与查询某条数据同义)
     * @param t
     * @return
     */
    Integer getCountByPramas(T t);


    /**
     *   查询表的所有数据
     * @return  数据结果集
     */
    List<T>  findAll();
}
