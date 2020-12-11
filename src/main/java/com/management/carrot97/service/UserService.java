package com.management.carrot97.service;

import com.management.carrot97.bean.OriginalUser;
import com.management.carrot97.bean.User;
import com.management.carrot97.constant.BooleanConstants;
import com.management.carrot97.constant.StringConstants;
import com.management.carrot97.mapper.UserMapper;
import com.management.carrot97.utils.StringVerify;
import com.management.carrot97.utils.Transformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    /**
     * 验证并添加用户
     * 1.验证用户信息的可用性（合法和是否被占用）
     * 2.若验证成功则进行类型转换（原始用户->用户），转换失败回显错误信息
     * 3.验证成功则添加用户，添加失败回显错误信息
     * Map中返回添加状态（成功或失败）和失败信息
     */
    public Map<String, Object> verifyAndAddUser(OriginalUser oUser) {
        // 验证用户信息的可用性
        Map<String, Object> msg = verifyUser(oUser);
        if (msg.get(StringConstants.VERIFYSTATUS).equals(BooleanConstants.AVAILABLE)) {
            // 验证成功，转换用户类型并保存
            User user = null;
            try {
                user = Transformer.originalUser2User(null, oUser);
            } catch (ParseException e) {
//                System.out.println("用户转换失败");
                msg.put(StringConstants.VERIFYSTATUS, BooleanConstants.UNAVAILABLE);
                msg.put(StringConstants.ERRORMESSAGE, StringConstants.TRANSFORMERROR);
            }
            // 添加用户
            if (msg.get(StringConstants.VERIFYSTATUS).equals(BooleanConstants.AVAILABLE)) {
                if (!addUser(user)) {
                    msg.put(StringConstants.VERIFYSTATUS, BooleanConstants.UNAVAILABLE);
                    msg.put(StringConstants.ERRORMESSAGE, StringConstants.ADDFAILED);
                }
            }

        }
        return msg;
    }

    /**
     * 验证并更新用户
     * 1.验证用户信息的可用性（合法和是否被占用）
     * 2.若验证成功则进行类型转换（原始用户->用户），转换失败回显错误信息
     * 3.验证成功则更新用户，更新失败回显错误信息
     * Map中返回添加状态（成功或失败）、失败信息和更新的用户
     */
    public Map<String, Object> verifyAndUpdateUser(User user, OriginalUser oUser) {
        // 验证用户信息的可用性
        Map<String, Object> msg = verifyUser(user, oUser);
        if (msg.get(StringConstants.VERIFYSTATUS).equals(BooleanConstants.AVAILABLE)) {
            // 验证成功，转换用户类型并保存
            User newUser = null;
            try {
                newUser = Transformer.originalUser2User(user.getUserId(), oUser);
            } catch (ParseException e) {
//                System.out.println("用户转换失败");
                msg.put(StringConstants.VERIFYSTATUS, BooleanConstants.UNAVAILABLE);
                msg.put(StringConstants.ERRORMESSAGE, StringConstants.TRANSFORMERROR);
            }
            // 更新用户
            if (msg.get(StringConstants.VERIFYSTATUS).equals(BooleanConstants.AVAILABLE)) {
//                System.out.println("开始更新用户信息");
                boolean result = updateUser(newUser);
                if (!result) {
                    msg.put(StringConstants.VERIFYSTATUS, BooleanConstants.UNAVAILABLE);
                    msg.put(StringConstants.ERRORMESSAGE, StringConstants.UPDATEFAILED);
                }
            }
            if (msg.get(StringConstants.VERIFYSTATUS).equals(BooleanConstants.AVAILABLE)) {
                msg.put("newUser", newUser);
            }
        }
        return msg;
    }

    public boolean updateUser(User user) {
        Boolean result = userMapper.updateUser(user);
        return result;
    }

    public Map<String, Object> verifyUser(OriginalUser oUser) {
        Map<String, Object> msg = verifyUser(new User(), oUser);
        return msg;
    }

    public Map<String, Object> verifyUser(User user, OriginalUser oUser) {
        Map<String, Object> msg = new HashMap<>();
        msg.put(StringConstants.VERIFYSTATUS, BooleanConstants.UNAVAILABLE);
        // 验证用户名
        // 验证合法性（2-4字中文）
        if (!msg.containsKey(StringConstants.ERRORMESSAGE)
                && !StringVerify.verifyName(oUser.getUsername())) {
            msg.put(StringConstants.ERRORMESSAGE, StringConstants.ILLEGALUSERNAME);
        }

        // 验证密码
        // 验证密码合法性
        if (!msg.containsKey(StringConstants.ERRORMESSAGE)
                && !StringVerify.verifyPassword(oUser.getPassword1())) {
            msg.put(StringConstants.ERRORMESSAGE, StringConstants.ILLEGALPASSWORD);
        }
        // 验证两次密码是否一致
        if (!msg.containsKey(StringConstants.ERRORMESSAGE)
                && !oUser.getPassword1().equals(oUser.getPassword2())) {
            msg.put(StringConstants.ERRORMESSAGE, StringConstants.PASSWORDINCONFORMITY);
        }

        // 学位不能为空
        if (!msg.containsKey(StringConstants.ERRORMESSAGE)
                && oUser.getDegree() == null) {
            msg.put(StringConstants.ERRORMESSAGE, StringConstants.NULLDEGREE);
        }

        // 验证邮箱
        // 验证合法性
        if (!msg.containsKey(StringConstants.ERRORMESSAGE)
                && !StringVerify.verifyEmail(oUser.getEmail())) {
            msg.put(StringConstants.ERRORMESSAGE, StringConstants.ILLEGALEMAIL);
        }
        // 验证数据库中是否存在
        if (!msg.containsKey(StringConstants.ERRORMESSAGE)
                && userMapper.getUserByEmail(user.getEmail(), oUser.getEmail()) != null) {
            msg.put(StringConstants.ERRORMESSAGE, StringConstants.EMAILOCCUPIED);
        }

        // 验证手机号
        // 验证合法性
        if (!msg.containsKey(StringConstants.ERRORMESSAGE)
                && !StringVerify.verifyPhoneNumber(oUser.getPhoneNumber())) {
            msg.put(StringConstants.ERRORMESSAGE, StringConstants.ILLEGALPHONE);
        }
        // 验证数据库中是否存在
        if (!msg.containsKey(StringConstants.ERRORMESSAGE)
                && userMapper.getUserByPhoneNumber(user.getPhoneNumber(), oUser.getPhoneNumber()) != null) {
            msg.put(StringConstants.ERRORMESSAGE, StringConstants.PHONEOCCUPIED);
        }

        // 用户名，邮箱，手机号均验证成功，将状态码修改为可用
        if (!msg.containsKey(StringConstants.ERRORMESSAGE)) {
            msg.put(StringConstants.VERIFYSTATUS, BooleanConstants.AVAILABLE);
        }
        return msg;
    }

    public Boolean addUser(User user) {
        Boolean result = userMapper.addUser(user);
        return result;
    }

    public User checkNameOrPwd(String username, String password) {
        User user = null;
        boolean available = true;
        // 首先验证用户名(并判断使用的是邮箱还是手机号)和密码的合法性
        boolean isEmail = StringVerify.verifyEmail(username);
        boolean isPhoneNumber = StringVerify.verifyPhoneNumber(username);
        if (available && !isEmail && !isPhoneNumber) available = false;
        if (available && !StringVerify.verifyPassword(password)) available = false;

        // 验证正确性
        // 取出用户比较密码
        if (available) {
            if (isEmail) {
                user = userMapper.getUserByEmail(null, username);
                if (user !=null && !password.equals(user.getPassword())) {
                    user = null;
                }
            } else if (isPhoneNumber) {
                user = userMapper.getUserByPhoneNumber(null, username);
                if (user != null && !password.equals(user.getPassword())) {
                    user = null;
                }
            }
        }
        return user;
    }

    /**
     * 1.判断用户是否登录
     * 2.判断上传文件是否合规
     * 3.指定文件名
     * 4.保存文件
     */
    public Map<String, Object> verifyAndSavePic(MultipartFile file, User user) {
        Map<String, Object> msg = new HashMap<>();
        msg.put(StringConstants.VERIFYSTATUS, BooleanConstants.UNAVAILABLE);
        // 1.判断用户是否登录
        if (!msg.containsKey(StringConstants.ERRORMESSAGE) && user==null) {
            msg.put(StringConstants.ERRORMESSAGE, StringConstants.NULLLOGINUSER);
        }
        // 2.判断上传文件是否合规
        if (!msg.containsKey(StringConstants.ERRORMESSAGE) && file.isEmpty()) {
            msg.put(StringConstants.ERRORMESSAGE, StringConstants.NULLFILE);
        }
        if (!msg.containsKey(StringConstants.ERRORMESSAGE)) {
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                // 3.指定文件名
                // 3.1 取出工程路径
                String path = ResourceUtils.getURL("classpath:").getPath();
                // 3.2 指定工程内保存位置
                path += "static/assets/img/";
                // 由于上面判断过用户是否为空，这可以断言
                assert user != null;
                // 3.3 创建文件文件名为user-${userid}
                File targetFile = new File(path + "user-" + user.getUserId() + ".png");
//                System.out.println(path + "user-" + user.getUserId() + ".png");
                // 4.保存文件
                // 4.1 数据流接收文件
                inputStream = file.getInputStream();
                // 4.2 输出流保存文件
                outputStream = new FileOutputStream(targetFile);
                // 4.3 用copy方法保存文件
                FileCopyUtils.copy(inputStream, outputStream);
            } catch (Exception e) {
                msg.put(StringConstants.ERRORMESSAGE, StringConstants.SAVEFAILD);
            }
        }
        if (!msg.containsKey(StringConstants.ERRORMESSAGE)) {
            msg.put(StringConstants.VERIFYSTATUS, BooleanConstants.AVAILABLE);
        }
        return msg;
    }


    /*更新用户头像路径*/
    public Map<String, Object> updateUserPortrait(User user) {
        Map<String, Object> msg = new HashMap<>();
        if (!userMapper.updatePortrait(user)) {
            msg.put(StringConstants.VERIFYSTATUS, BooleanConstants.UNAVAILABLE);
            msg.put(StringConstants.ERRORMESSAGE, StringConstants.DBUPDATEERROR);
        } else {
            msg.put(StringConstants.VERIFYSTATUS, BooleanConstants.AVAILABLE);
        }
        return msg;
    }
}
