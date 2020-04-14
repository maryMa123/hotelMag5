package com.java.controller;


import com.java.pojo.User;
import com.java.utils.VerifyCodeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 *   用户控制器
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController<User> {

    //获取用户登陆时的验证码
    @RequestMapping("/getVerifyCode")
    public void getVerifyCode(HttpSession session, HttpServletResponse response) throws Exception{
        //1.通过工具类产生随机验证码  3DcfG
        String verifyCode = VerifyCodeUtils.generateVerifyCode(5);
        //2.将服务器端产生的随机验证码中的英文字母转为小写并放在session容器中   3dcfg
        session.setAttribute("verifyCode",verifyCode.toLowerCase());
        //3.将产生的验证码转为图片显示（响应）到页面中   3DcfG
        VerifyCodeUtils.outputImage(250,35,response.getOutputStream(),verifyCode);
    }

    //验证用户输入的验证码
    @RequestMapping("/checkVerifyCode")
    public @ResponseBody String checkVerifyCode(HttpSession session, String verifyCodeIpt){
        //1.从session容器中取出之前装入的验证码
        String verifyCode = (String)session.getAttribute("verifyCode");
        //2.此时将用户输入的验证码与session中取出的验证码进行比较
        //verifyCodeIpt为用户收输入！！！
        if(verifyCodeIpt.equals(verifyCode)){
            return "success";
        }else {
            return "fail";
        }
    }
}
