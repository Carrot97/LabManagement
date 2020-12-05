package com.management.carrot97.controller;

import com.management.carrot97.bean.Activity;
import com.management.carrot97.bean.Page;
import com.management.carrot97.bean.User;
import com.management.carrot97.constant.StringConstants;
import com.management.carrot97.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class ActivityController {

    @Autowired
    ActivityService activityService;


    // 获取最近活动（最近为举行的10个活动）
    @GetMapping(value = "/activity/recent")
    public String recentActivities(Map<String, Object> map) {
        List<Activity> activities = activityService.getRecent();
        map.put("activities", activities);
        return "activity/recent";
    }


    // 获得全部活动分页（默认一页10个）
    @GetMapping(value = "/activity/all")
    public String getActivities(Page page,
                                Map<String, Object> map) {
        List<Activity> activities = activityService.getPage(page, null);
        map.put("activities", activities);
        return "activity/all";
    }


    /**
     * 1.点击主导航栏‘个人中心’默认进入我的活动
     * 2.点击次级导航栏‘我的活动’进入我的活动
     */
    @GetMapping(value = "/activity/user")
    public String getPersonalActivities(Page page,
                                 Map<String, Object> map,
                                 HttpSession session) {
        User loginUser = (User) session.getAttribute(StringConstants.LOGINUSER);
        List<Activity> activities = activityService.getPage(page, loginUser.getUserName());
        map.put("activities", activities);
        return "user/activity";
    }


    /*跳转至添加活动页面*/
    @GetMapping(value = "/activity/add")
    public String getAddPage() {
        return "activity/add";
    }

    /*提交发布活动表单*/
    @PostMapping(value = "/activity")
    public String addActivity(Activity activity) {
        System.out.println(activity);
        return "redirect:/activity/recent";
    }

}
