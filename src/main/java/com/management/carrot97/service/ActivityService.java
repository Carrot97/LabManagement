package com.management.carrot97.service;

import com.management.carrot97.bean.Activity;
import com.management.carrot97.bean.Page;
import com.management.carrot97.mapper.ActivityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityService {

    @Autowired
    ActivityMapper activityMapper;

    // 获取分页
    public List<Activity> getPage(Page page) {
        List<Activity> activities = activityMapper.selectByPageInfo(page.getNumberStart(),
                page.getNumberStart() + page.getPageSize());
        return activities;
    }

    public List<Activity> getRecent() {
        List<Activity> activities = activityMapper.selectRecent();
        return activities;
    }
}
