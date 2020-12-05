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
                user = Transformer.originalUser2User(oUser);
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

    public Map<String, Object> verifyUser(OriginalUser oUser) {
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
                && userMapper.getUserByEmail(oUser.getEmail()) != null) {
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
                && userMapper.getUserByPhoneNumber(oUser.getPhoneNumber()) != null) {
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
                user = userMapper.getUserByEmail(username);
                if (!user.getPassword().equals(password)) {
                    user = null;
                }
            } else if (isPhoneNumber) {
                user = userMapper.getUserByPhoneNumber(username);
                if (!user.getPassword().equals(password)) {
                    user = null;
                }
            }
        }
        return user;
    }
}
