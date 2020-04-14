package com.java.service.impl;


import com.java.pojo.InRoomInfo;
import com.java.pojo.Rooms;
import com.java.service.InRoomInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *   入住信息的业务层实现类
 */
@Service
@Transactional(readOnly = false)
public class InRoomInfoServiceImpl extends BaseServiceImpl<InRoomInfo> implements InRoomInfoService {

    //重写入住信息添加的方法，入住信息添加和房间状态修改
    @Override
    public String save(InRoomInfo inRoomInfo){
        //1.完成入住信息添加
        Integer insINICount = baseMapper.insert(inRoomInfo);
        //2.完成房间状态修改
        //2.1.新建修改的房间对象
        Rooms rooms = new Rooms();
        //2.2.设置房间数据参数
        rooms.setId(inRoomInfo.getRoomId());
        rooms.setRoomStatus("1");
        //2.3.执行房间状态修改
        Integer updRoomsCount = roomsMapper.updateByPrimaryKeySelective(rooms);
        if(insINICount>0&&updRoomsCount>0){
            return "success";
        }else {
            return "fail";
        }
    }
}
