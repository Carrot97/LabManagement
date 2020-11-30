package com.management.carrot97.utils;

import com.management.carrot97.bean.Degree;
import com.management.carrot97.bean.OriginalUser;
import com.management.carrot97.bean.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transformer {

    // 原始用户 转 正式用户
    public static User originalUser2User(OriginalUser oUser) throws ParseException {
        // 默认年份2020
        // 仅适应输入，知道月日就可以（祝生日快乐了），没必要知道年龄（知道也不好）
        String birthday = "2020-" + oUser.getBirthday();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(birthday);
        return new User(oUser.getUsername(),
                oUser.getPassword1(),
                date,
                oUser.getDegree(),
                oUser.getEmail(),
                oUser.getPhoneNumber());
    }
}
