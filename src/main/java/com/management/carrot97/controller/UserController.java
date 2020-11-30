package com.management.carrot97.controller;

import com.management.carrot97.bean.OriginalUser;
import com.management.carrot97.bean.User;
import com.management.carrot97.constant.StringConstants;
import com.management.carrot97.service.UserService;
import com.management.carrot97.constant.BooleanConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    /*获取登录页面*/
    @GetMapping(value = "/login")
    public String getLogin() {
        return "user/login";
    }

    /*提交登录页面*/
    @PostMapping(value = "/login")
    public String postLogin(@RequestParam("username") String username,
                            @RequestParam("password") String password,
                            Map<String, Object> map,
                            HttpSession session) {
//        System.out.println("收到登录请求");
        // 通过账号密码取出用户，取失败则返回null
        User user = userService.checkNameOrPwd(username, password);
        System.out.println(user);
        if (user != null) {
            session.setAttribute("loginUser", user);
            return "redirect:/main";
        } else {
            // 回显错误信息
            map.put("msg", "用户名或密码错误");
            map.put("username", username);
            return "user/login";
        }
    }

    /*获取注册页面*/
    @GetMapping(value = "/register")
    public String getRegister() {
        return "user/register";
    }

    /**
     * 提交注册页面
     * 1.验证并添加用户--得到状态反馈信息
     * 2.判断反馈信息为成功，则重定向至登录页面
     * 3.若不成功则返回注册页面，并回显用户和错误信息
     */
    @PostMapping(value = "/register")
    public String PostRegister(OriginalUser originalUser,
                               Map<String, Object> map) {
//        System.out.println(originalUser);
        // 1.验证并添加用户
        Map<String, Object> msg = userService.verifyAndAddUser(originalUser);
        // 2.若返回状态为成功，则重定向至登录页面
        if (msg.get(StringConstants.VERIFYSTATUS).equals(BooleanConstants.AVAILABLE)) {
//            System.out.println("添加成功");
            map.put("msg", "注册成功，请登录");
            return "redirect:/login";
        } else {
//            3.若不成功则返回注册页面，并回显用户和错误信息
//            System.out.println("添加失败，错误信息" + msg.toString());
            map.put("msg", msg.get("details"));
            map.put("user", originalUser);
            return "user/register";
        }
    }


}
