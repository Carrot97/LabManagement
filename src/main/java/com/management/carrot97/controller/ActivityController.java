package com.management.carrot97.controller;

import com.github.pagehelper.PageInfo;
import com.management.carrot97.bean.Activity;
import com.management.carrot97.bean.Page;
import com.management.carrot97.bean.User;
import com.management.carrot97.constant.BooleanConstants;
import com.management.carrot97.constant.StringConstants;
import com.management.carrot97.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
        PageInfo<Activity> pageInfo = new PageInfo<>(activities);
        map.put("pageInfo", pageInfo);
        return "activity/recent";
    }


    // 获得全部活动分页（默认一页10个）
    @GetMapping(value = "/activity/all")
    public String getActivities(Page page,
                                Map<String, Object> map) {
        PageInfo<Activity> pageInfo = activityService.getPage(page, null);
        map.put("pageInfo", pageInfo);
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
        PageInfo<Activity> pageInfo = activityService.getPage(page, loginUser.getUserName());
        map.put("pageInfo", pageInfo);
        return "user/activity";
    }


    /*提取指定活动信息并跳转至修改页面*/
    @GetMapping(value = {"/activity/{id}", "/activity/"})
    public String getActivity(@PathVariable(value = "id", required = false) Integer activityId,
                              Map<String, Object> map) {
        if (activityId != null) {
            Activity activity = activityService.getActivity(activityId);
            map.put("update", "1");
            map.put("activity", activity);
        }
        return "activity/add";
    }


    /*提交发布活动表单*/
    @PostMapping(value = "/activity")
    public String addActivity(Activity activity,
                              Map<String, Object> map) {
        System.out.println("活动添加操作");
        Map<String, Object> msg = activityService.verifyAndAdd(activity);
        if (BooleanConstants.AVAILABLE.equals(msg.get(StringConstants.VERIFYSTATUS))) {
            // 添加成功返回主页面
            return "redirect:/activity/recent";
        } else {
            // 添加失败回显活动信息和错误信息
            map.put(StringConstants.FEEDBACKMSG, msg.get(StringConstants.ERRORMESSAGE));
            map.put("activity", activity);
            return "activity/add";
        }
    }


    /*提交修改活动表单*/
    @PutMapping(value = "/activity")
    public String updateActivity(Activity activity,
                                 Map<String, Object> map) {
        System.out.println("活动修改操作");
        Map<String, Object> msg = activityService.verifyAndUpdate(activity);
        if (BooleanConstants.AVAILABLE.equals(msg.get(StringConstants.VERIFYSTATUS))) {
            // 添加成功返回主页面
            return "redirect:/activity/user?pageNumber=1";
        } else {
            // 添加失败回显活动信息和错误信息
            map.put(StringConstants.FEEDBACKMSG, msg.get(StringConstants.ERRORMESSAGE));
            map.put("update", "1");
            map.put("activity", activity);
            return "activity/add";
        }
    }

    /*提交活动删除请求*/
    @DeleteMapping(value = "/activity/{id}")
    public String deleteActivity(@PathVariable("id") Integer activityId) {
        // 反馈信息暂时不用
        Map<String, Object> msg = activityService.deleteActivity(activityId);
        return "redirect:/activity/user?pageNumber=1";
    }

}
