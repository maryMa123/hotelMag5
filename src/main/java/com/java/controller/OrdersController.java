package com.java.controller;


import com.java.pojo.Orders;
import com.java.service.OrdersService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/orders")
public class OrdersController extends BaseController<Orders> {


    //去到订单支付页面
    @RequestMapping("/toPay")
    public String toPay(){
        return "alipay/ordersPay";  //转发
    }

    //订单支付完成后的路径回调（1.修改订单状态，2.生成消费记录）
    //获取订单的相关数据
    @RequestMapping("/myOrdersPay")
    public String myOrdersPay(String out_trade_no){
            return ordersService.afterOrdersPay(out_trade_no);


    }
}
