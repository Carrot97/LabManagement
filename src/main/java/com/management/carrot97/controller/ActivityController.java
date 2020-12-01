package com.management.carrot97.controller;

import com.management.carrot97.bean.Activity;
import com.management.carrot97.bean.Page;
import com.management.carrot97.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class ActivityController {

    @Autowired
    ActivityService activityService;

    // 获取分页页面
    @GetMapping(value = "activities")
    public String getActivities(Page page,
                                Map<String, Object> map) {
        List<Activity> activities = activityService.getPage(page);
        map.put("activities", activities);
        return "/activity/list";
    }
}
