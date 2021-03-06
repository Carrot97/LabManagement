package com.management.carrot97.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.management.carrot97.bean.Activity;
import com.management.carrot97.constant.BooleanConstants;
import com.management.carrot97.constant.NumberConstants;
import com.management.carrot97.constant.StringConstants;
import com.management.carrot97.mapper.ActivityMapper;
import com.management.carrot97.utils.StringVerify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CacheConfig(cacheNames = "activity")
@Service
public class ActivityService {

    @Autowired
    ActivityMapper activityMapper;


    /*********************************基本操作类型*******************************/
    // 按照指定id获取一个活动
    public Activity getActivity(Integer id) {
        Activity activity = activityMapper.selectByPrimaryKey(id);
        return activity;
    }

    // 获取指定用户的分页
    // 若未指定则获取全部活动的分页
    @Cacheable(keyGenerator = "myKeyGenerator", condition = "#a1==null")
    public PageInfo<Activity> getPage(Integer pageNumber, String username) {
        System.out.println("查询全部活动");
        PageHelper.startPage(pageNumber, NumberConstants.ACTIVITYPAGESIZE);
        List<Activity> activities = activityMapper.selectPage(username);
        PageInfo<Activity> pageInfo = new PageInfo<>(activities);
        return pageInfo;
    }


    // 获取最近10个活动
    @Cacheable(keyGenerator = "myKeyGenerator")
    public List<Activity> getRecent() {
        System.out.println("查询最近活动");
        List<Activity> activities = activityMapper.selectRecent();
        return activities;
    }

    // 添加活动
    private int addActivity(Activity activity) {
        int result = activityMapper.insertSelective(activity);
        return result;
    }

    // 更新活动
    private int updateActivity(Activity activity) {
        int result = activityMapper.updateByPrimaryKey(activity);
        return result;
    }

    // 验证活动信息可用性
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


    /*********************************联合操作类型*******************************/
    /**
     * 验证并添加活动
     * 1.验证活动（验证数据库中not null字段非空）
     * 2.添加活动
     * Map中返回添加状态（成功或失败）和失败信息
     */
    @CacheEvict(allEntries = true, beforeInvocation = false)
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

    /**
     * 验证并更新活动
     * 1.验证活动（验证数据库中not null字段非空）
     * 2.更新活动
     * Map中返回添加状态（成功或失败）和失败信息
     */
    @CacheEvict(allEntries = true, beforeInvocation = false)
    public Map<String, Object> verifyAndUpdate(Activity activity) {
        Map<String, Object> msg = verifyActivity(activity);
        if (msg.get(StringConstants.VERIFYSTATUS).equals(BooleanConstants.AVAILABLE)) {
            // 验证通过添加活动
            if (updateActivity(activity) != 1) {
                msg.put(StringConstants.VERIFYSTATUS, BooleanConstants.UNAVAILABLE);
                msg.put(StringConstants.ERRORMESSAGE, StringConstants.UPDATEFAILED);
            }
        }
        return msg;
    }

    @CacheEvict(allEntries = true, beforeInvocation = false)
    public Map<String, Object> deleteActivity(Integer activityId) {
        HashMap<String, Object> msg = new HashMap<>();
        msg.put(StringConstants.VERIFYSTATUS, BooleanConstants.AVAILABLE);
        if (activityMapper.deleteByPrimaryKey(activityId) != 1) {
            msg.put(StringConstants.VERIFYSTATUS, BooleanConstants.UNAVAILABLE);
            msg.put(StringConstants.ERRORMESSAGE, StringConstants.DELETEFAILED);
        }
        return msg;
    }
}
