package com.management.carrot97.service;

import com.management.carrot97.bean.Activity;
import com.management.carrot97.bean.Page;
import com.management.carrot97.mapper.ActivityMapper;
import com.management.carrot97.utils.Transformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityService {

    @Autowired
    ActivityMapper activityMapper;

    // 获取指定用户的分页
    // 若未指定则获取全部活动的分页
    public List<Activity> getPage(Page page, String username) {
        List<Activity> activities = activityMapper.selectByPageInfo(page.getNumberStart(),
                page.getNumberStart() + page.getPageSize(), username);
        return activities;
    }

    // 获取最近10个活动
    public List<Activity> getRecent() {
        List<Activity> activities = activityMapper.selectRecent();
        return activities;
    }
}
