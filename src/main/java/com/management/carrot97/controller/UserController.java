package com.management.carrot97.controller;

import com.management.carrot97.bean.OriginalUser;
import com.management.carrot97.bean.User;
import com.management.carrot97.constant.StringConstants;
import com.management.carrot97.service.UserService;
import com.management.carrot97.constant.BooleanConstants;
import com.management.carrot97.utils.Transformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    /*********************登录************************/
    /*获取登录页面*/
    @GetMapping(value = "/user/login")
    public String getLogin() {
        return "user/login";
    }


    /*提交登录页面*/
    @PostMapping(value = "/user/login")
    public String postLogin(@RequestParam("username") String username,
                            @RequestParam("password") String password,
                            Map<String, Object> map,
                            HttpSession session) {
//        System.out.println("收到登录请求");
        // 通过账号密码取出用户，取失败则返回null
        User user = userService.checkNameOrPwd(username, password);
//        System.out.println(user);
        if (user != null) {
            session.setAttribute(StringConstants.LOGINUSER, user);
            // 重定向至集体活动页面（主页面）默认页码1，每页5条
            return "redirect:/activity/recent";
        } else {
            // 回显错误信息
            map.put(StringConstants.FEEDBACKMSG, StringConstants.ERRORLOGIN);
            map.put("username", username);
            return "user/login";
        }
    }

    /*********************注销************************/
    /*注销操作*/
    @GetMapping(value = "/user/logout")
    public String logout(HttpSession session) {
        if (session.getAttribute(StringConstants.LOGINUSER) != null) {
            session.removeAttribute(StringConstants.LOGINUSER);
        }
        return "redirect:/activity/recent";
    }

    /*********************注册************************/
    /*获取注册页面*/
    @GetMapping(value = "/user/register")
    public String getRegister() {
        return "user/register";
    }


    /**
     * 提交注册页面
     * 1.验证并添加用户--得到状态反馈信息
     * 2.判断反馈信息为成功，则重定向至登录页面
     * 3.若不成功则返回注册页面，并回显用户和错误信息
     */
    @PostMapping(value = "/user/register")
    public String doRegister(OriginalUser originalUser,
                               Map<String, Object> map) {
//        System.out.println(originalUser);
        // 1.验证并添加用户
        Map<String, Object> msg = userService.verifyAndAddUser(originalUser);
        // 2.若返回状态为成功，则重定向至登录页面
        if (msg.get(StringConstants.VERIFYSTATUS).equals(BooleanConstants.AVAILABLE)) {
//            System.out.println("添加成功");
            return "redirect:/login";
        } else {
//            3.若不成功则返回注册页面，并回显用户和错误信息
//            System.out.println("添加失败，错误信息" + msg.toString());
            map.put(StringConstants.FEEDBACKMSG, msg.get(StringConstants.ERRORMESSAGE));
            map.put("user", originalUser);
            return "user/register";
        }
    }

    /*********************修改个人信息************************/
    // 跳转至个人信息页面
    @GetMapping(value = "/user/update")
    public String getUpdatePage(Map<String, Object> map,
                                HttpSession session) {
        User user = (User) session.getAttribute(StringConstants.LOGINUSER);
        OriginalUser oUser = Transformer.User2originalUser(user);
        map.put("user", oUser);
        return "user/update_info";
    }

    /**
     * 修改个人信息
     * 1.验证并添加用户--得到状态反馈信息
     * 2.若成功则将新用户信息保存如session，并重定向至个人信息页面
     * 3.若失败则将返回个人信息页面，并回显错误信息
     */
    @PutMapping(value = "/user/register")
    public String updateUser(OriginalUser originalUser,
                             Map<String, Object> map,
                             HttpSession session) {
//        System.out.println(originalUser);
        User user = (User) session.getAttribute(StringConstants.LOGINUSER);
        // 1.验证并添加用户--得到状态反馈信息
        Map<String, Object> msg = userService.verifyAndUpdateUser(user, originalUser);
//        System.out.println("验证完成");
//        System.out.println(msg.toString());
        // 2.成功则将新用户信息保存如session，并重定向至个人信息页面
        if (msg.get(StringConstants.VERIFYSTATUS).equals(BooleanConstants.AVAILABLE)) {
//            System.out.println("开始更新session");
            User newUser = (User) msg.get("newUser");
            session.setAttribute(StringConstants.LOGINUSER, newUser);
//            System.out.println("session更新完成");
            return "redirect:/user/update";
        } else {
            // 3.若失败则将返回个人信息页面，并回显错误信息
            map.put(StringConstants.FEEDBACKMSG, msg.get(StringConstants.ERRORMESSAGE));
            map.put("user", originalUser);
            return "user/update_info";
        }
    }


    /*********************更换头像************************/
    /*获取更换头像页面*/
    @GetMapping(value = "/portrait")
    public String getPortraitChange() {
        return "user/portrait";
    }

    /**
     * 上传头像操作
     * 1.取出登录用户
     * 2.保存图片
     * 3.保存失败则返回上传图片页面并回显错误信息
     * 5.保存成功则更新user数据库中imgPath字段
     * 4.修改成功返回个人中心首页
     */
    @PostMapping(value = "/portrait")
    public String uploadPortrait(@RequestParam(value = "file") MultipartFile file,
                                 HttpSession session,
                                 Map<String, Object> map) {
        User user = (User) session.getAttribute(StringConstants.LOGINUSER);
        Map<String, Object> msg = userService.verifyAndSavePic(file, user);
        System.out.println(msg);
        if (BooleanConstants.UNAVAILABLE.equals(msg.get(StringConstants.VERIFYSTATUS))) {
            map.put(StringConstants.FEEDBACKMSG, msg.get(StringConstants.ERRORMESSAGE));
            return "user/portrait";
        }
        if ("default".equals(user.getImgPath())) {
            user.setImgPath("obeyId-" + user.getUserId());
            session.setAttribute(StringConstants.LOGINUSER, user);
            msg = userService.updateUserPortrait(user);
            if (BooleanConstants.UNAVAILABLE.equals(msg.get(StringConstants.VERIFYSTATUS))) {
                map.put(StringConstants.FEEDBACKMSG, msg.get(StringConstants.ERRORMESSAGE));
                return "user/portrait";
            }
        }
        return "redirect:/activity/user?pageNumber=1";
    }


}































