package com.management.carrot97.service;

import com.management.carrot97.bean.Activity;
import com.management.carrot97.bean.Page;
import com.management.carrot97.constant.BooleanConstants;
import com.management.carrot97.constant.StringConstants;
import com.management.carrot97.mapper.ActivityMapper;
import com.management.carrot97.utils.StringVerify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * 验证并添加活动
     * 1.验证活动（验证数据库中not null字段非空）
     * 2.添加活动
     * Map中返回添加状态（成功或失败）和失败信息
     */
    public Map<String, Object> verifyAndAdd(Activity activity) {
        // 验证活动
        Map<String, Object> msg = verifyActivity(activity);
        if (msg.get(StringConstants.VERIFYSTATUS).equals(BooleanConstants.AVAILABLE)) {
            // 验证通过添加活动
            if (addActivity(activity) != 1) {
                msg.put(StringConstants.VERIFYSTATUS, BooleanConstants.UNAVAILABLE);
                msg.put(StringConstants.ERRORMESSAGE, StringConstants.ADDFAILED);
            }
        }
        return msg;
    }

    private int addActivity(Activity activity) {
        int result = activityMapper.insertSelective(activity);
        return result;
    }

    private Map<String, Object> verifyActivity(Activity activity) {
        HashMap<String, Object> msg = new HashMap<>();
        msg.put(StringConstants.VERIFYSTATUS, BooleanConstants.AVAILABLE);
        // 验证活动名字
        if (BooleanConstants.AVAILABLE.equals(msg.get(StringConstants.VERIFYSTATUS))
                && StringVerify.isNullOrEmpty(activity.getName())) {
            // 将状态改为不可用，并加入错误信息
            msg.put(StringConstants.VERIFYSTATUS, BooleanConstants.UNAVAILABLE);
            msg.put(StringConstants.ERRORMESSAGE, StringConstants.NULLNAME);
        }

        // 验证活动举办人
        if (BooleanConstants.AVAILABLE.equals(msg.get(StringConstants.VERIFYSTATUS))
                && StringVerify.isNullOrEmpty(activity.getHost())) {
            msg.put(StringConstants.VERIFYSTATUS, BooleanConstants.UNAVAILABLE);
            msg.put(StringConstants.ERRORMESSAGE, StringConstants.NULLHOST);
        }

        // 验证活动地点
        if (BooleanConstants.AVAILABLE.equals(msg.get(StringConstants.VERIFYSTATUS))
                && StringVerify.isNullOrEmpty(activity.getLocation())) {
            msg.put(StringConstants.VERIFYSTATUS, BooleanConstants.UNAVAILABLE);
            msg.put(StringConstants.ERRORMESSAGE, StringConstants.NULLLOCATION);
        }
        return msg;
    }
}
