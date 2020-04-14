package com.java.service.impl;

import com.java.pojo.InRoomInfo;
import com.java.pojo.Orders;
import com.java.pojo.RoomSale;
import com.java.pojo.Rooms;
import com.java.service.OrdersService;
import com.java.utils.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *   订单业务层实现类
 */
@Service
@Transactional(readOnly = false)
public class OrdersServiceImpl extends BaseServiceImpl<Orders> implements OrdersService {

    //当基础业务层方法无法完成复杂的业务层功能时，可以将基础的业务层方法进行重写
    //重写订单添加的基础业务层方法，要完成订单数据添加，入住信息和房间信息修改
    @Override
    public String save(Orders orders){
        //1.完成订单添加，baseMapper此对象中的泛型为Orders（因为此类为订单的业务层实现类）
        Integer insOrdersCount = baseMapper.insert(orders);
        //2.完成入住信息的修改
        //2.1.新建修改的入住信息对象
        InRoomInfo inRoomInfo = new InRoomInfo();
        inRoomInfo.setId(orders.getIriId());
        inRoomInfo.setOutRoomStatus("1");
        //2.2.执行入住信息状态修改
        Integer updINICount = inRoomInfoMapper.updateByPrimaryKeySelective(inRoomInfo);
       /* //人为出一个异常
        int i = 1/0;  //除数不为0异常*/
        //3.完成房间状态的修改
        //3.1.根据入住信息id查询出房间id
        Integer roomId = inRoomInfoMapper.selectByPrimaryKey(orders.getIriId()).getRoomId();
        //3.2.新建房间修改对象
        Rooms rooms = new Rooms();
        rooms.setId(roomId);
        rooms.setRoomStatus("2");
        //3.3.执行房间状态的修改
        Integer updRoomsCount = roomsMapper.updateByPrimaryKeySelective(rooms);
        //返回操作结果
        if(insOrdersCount>0&&updINICount>0&&updRoomsCount>0){
            return "success";
        }else {
            return "fail";
        }
    }

    //支付成功之后的操作（1.修改订单状态，2.生成消费记录）
    @Override
    public String afterOrdersPay(String out_trade_no){
        //1.新建查询的条件订单对象
        Orders praOrders = new Orders();
        praOrders.setOrderNum(out_trade_no);
        //2.根据订单编号查询订单数据
        Orders orders = baseMapper.selectByPramas(praOrders);
        //3.修改订单状态
        orders.setOrderStatus("1");
        //执行修改订单状态
        Integer updOrdersCount = baseMapper.updateByPrimaryKeySelective(orders);
        //4.完成消费记录的生成
        //获取订单中其他字段数组
        String[] orderOther = orders.getOrderOther().split(",");
        //获取订单中其他金额数组
        String[] orderPrice = orders.getOrderPrice().split(",");
        //新建消费记录的对象
        RoomSale roomSale = new RoomSale();
        roomSale.setRoomNum(orderOther[0]);
        roomSale.setCustomerName(orderOther[1]);
        roomSale.setStartDate(DateUtils.stringToDate(orderOther[2]));
        roomSale.setEndDate(DateUtils.stringToDate(orderOther[3]));
        roomSale.setDays(Integer.valueOf(orderOther[4]));
        roomSale.setRoomPrice(Double.valueOf(orderPrice[0]));
        roomSale.setOtherPrice(Double.valueOf(orderPrice[1]));
        roomSale.setRentPrice(Double.valueOf(orderPrice[2]));
        roomSale.setSalePrice(orders.getOrderMoney());  //订单实际支付金额
        //优惠金额:单价*天数-租金
        Double discountPrice = Double.valueOf(orderPrice[0])*Integer.valueOf(orderOther[4])-Double.valueOf(orderPrice[2]);
        roomSale.setDiscountPrice(discountPrice);
        //完成添加消费记录
        Integer insRoomSalesCount = roomSaleMapper.insert(roomSale);
        if(updOrdersCount>0&&insRoomSalesCount>0){
            return "redirect:/model/toIndex";
        }else {
            return "payError";
        }
    }
}
