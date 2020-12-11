package com.management.carrot97.utils;

import com.management.carrot97.bean.Activity;
import com.management.carrot97.bean.OriginalUser;
import com.management.carrot97.bean.User;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Transformer {

    // 原始用户 转 正式用户
    public static User originalUser2User(Integer id, OriginalUser oUser) throws ParseException {
        return new User(id,
                oUser.getUsername(),
                oUser.getPassword1(),
                oUser.getBirthday(),
                oUser.getDegree(),
                oUser.getEmail(),
                oUser.getPhoneNumber());
    }

    // 正式用户 转 原始用户
    public static OriginalUser User2originalUser(User user) {
        return new OriginalUser(user.getUserName(),
                null,
                null,
                user.getBirthday(),
                user.getdegree(),
                user.getEmail(),
                user.getPhoneNumber());
    }

    // 活动时间格式转换
    public static void activityTimeTransform(Activity activity) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E");
        String strDate = format.format(activity.getStartTime());
        java.util.Date newDate;
        try {
            newDate = format.parse(strDate);
            activity.setStartTime(newDate);
        } catch (ParseException e) {
            System.out.println("活动日期转换失败：" + activity.getStartTime().toString());
        }
        System.out.println(strDate);
    }
    public static void activitiesTimeTransform(List<Activity> activities) {
        for (int i = 0; i < activities.size(); i++) {
            activityTimeTransform(activities.get(i));
        }
    }


}
