package com.java.controller;


import com.java.service.BaseService;

import com.java.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 基础控制层
 * @param <T> 传入的实体对象的泛型
 */


public class BaseController<T> {

    //基础业务层对象代理
    @Autowired
    private BaseService<T> baseService;

    //订单的业务层对象
    @Autowired
    protected OrdersService ordersService;

    /**
     * 根据主键删除单个数据
     * @param id
     * @return
     */
    @RequestMapping("/delByPrimaryKey")
    public @ResponseBody String delByPrimaryKey(Integer id){
        return baseService.removeByPrimaryKey(id);
    }

    /**
     * 插入某条实体类数据
     * @param t  添加数据对象
     * @return   添加结果
     */
    @RequestMapping("/save")
    public @ResponseBody String save(T t){
       return baseService.save(t);
    }


    /**
     * 动态添加某对象数据
     * @param t  数据对象
     * @return
     */
    @RequestMapping("/saveSelective")
   public @ResponseBody String  saveSelective(T t){
        return baseService.saveSelective(t);
    }


    /**
     * 根据主键查询某对象数据
     * @param id  主键id
     * @return   单个数据结果
     */
    @RequestMapping("/findByPrimaryKey")
   public @ResponseBody  T findByPrimaryKey(Integer id){
        return baseService.findByPrimaryKey(id);
    }


    /**
     *根据主键动态修改数据
     * @param t   要修改的对象数据
     * @return
     */
    @RequestMapping("/updateByPrimaryKeySelective")
    public @ResponseBody String updateByPrimaryKeySelective(T t){
        return baseService.updateByPrimaryKeySelective(t);
    }


    /**
     * 根据主键修改所有数据字段
     * @param t   要修改的对象数据
     * @return
     */
    @RequestMapping("/updateByPrimaryKey")
   public @ResponseBody String updateByPrimaryKey(T t){
        return baseService.updateByPrimaryKey(t);
    }


    /**
     * 根据条件分页加载数据
     * @param page  当前页
     * @param limit 每一页的数据个数
     * @param t  查询条件
     * @return  Layui 的table分页集合
     */
    @RequestMapping("/loadPageByPramas")
    public @ResponseBody Map<String,Object> loadPageByPramas(Integer page,Integer limit,T t){
        Map<String,Object> map = new HashMap<String, Object>();
        try {
            map = baseService.findPageByPramas(page,limit,t);
            map.put("code",0);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code",200);
            map.put("msg","数据加载异常。。");
        }
        return map;
    }


    /**
     * 根据条件查询单条数据
     * @param t  某个对象的条件
     * @return    对象
     */
    @RequestMapping("/loadByPramas")
    public @ResponseBody T loadByPramas(T t){
        return baseService.findByPramas(t);
    }


    /**
     *   根据条件加载多个数据
     * @param t  条件参数
     * @return  多条数据
     */
    @RequestMapping("/loadManyByPramas")
    public @ResponseBody
    List<T> loadManyByPramas(T t){
            return baseService.findManyByPramas(t);
    }

    /**
     *   根据多个主键ids动态批量修改数据
     * @param ids  多个主键ids数组
     * @param t  修改的数据
     * @return  操作结果
     *   js中的34,28,32字符串数据可以在springMVC中通过Integer[]接收到[34,28,32]
     */
    @RequestMapping("/updBatchByPrimaryKeySelective")
    public @ResponseBody String updBatchByPrimaryKeySelective(Integer[] ids,T t){

        return baseService.updBatchByPrimaryKeySelective(ids,t);

    }


    /**
     * 根据条件查询数据条数(与查询某条数据同义)
     * @param t
     * @return
     */
    @RequestMapping("/getCountByPramas")
    public @ResponseBody Integer getCountByPramas(T t){
        return baseService.getCountByPramas(t);
    }


    /**
     *   查询表的所有数据
     * @return  数据结果集
     */
    @RequestMapping("/loadAll")
        public @ResponseBody List<T> loadAll(){

        return baseService.findAll();


    }

}
