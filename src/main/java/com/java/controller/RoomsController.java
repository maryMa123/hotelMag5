package com.java.controller;


import com.java.pojo.Rooms;
import com.java.utils.QiNiuUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 *   房间控制器层
 */
@Controller
@RequestMapping("/rooms")
public class RoomsController extends BaseController<Rooms> {

    //房间的封面图上传
    @RequestMapping("/uploadRoomPic")
    public @ResponseBody Map<String,Object> uploadRoomPic(MultipartFile myFile) {
        try {
            return QiNiuUtil.fileUpload(myFile);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
