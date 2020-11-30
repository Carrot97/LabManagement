package com.management.carrot97.bean;

import java.util.Date;

public class Activity {
    // 活动唯一id
    private final Integer activityId;

    // 活动名称
    private String activityName;

    // 组织者
    private Integer hostId;

    // 开始日期时间
    private Date startTime;

    // 持续时长（分钟）
    private Integer span;

    // 活动地点
    private String location;

    // 预计活动花费
    private Double expense;

    // 细节描述
    private String details;

    public Activity(Integer activityId, String activityName, Integer hostId, Date startTime, Integer span, String location, Double expense, String details) {
        this.activityId = activityId;
        this.activityName = activityName;
        this.hostId = hostId;
        this.startTime = startTime;
        this.span = span;
        this.location = location;
        this.expense = expense;
        this.details = details;
    }

    public Activity(String activityName, Integer hostId, Date startTime, Integer span, String location, Double expense, String details) {
        this(null,
                activityName,
                hostId,
                startTime,
                span,
                location,
                expense,
                details);
    }

    @Override
    public String toString() {
        return "Activity{" +
                "activityId=" + activityId +
                ", activityName='" + activityName + '\'' +
                ", hostId=" + hostId +
                ", startTime=" + startTime +
                ", span=" + span +
                ", location='" + location + '\'' +
                ", expense=" + expense +
                ", details='" + details + '\'' +
                '}';
    }

    public Integer getActivityId() {
        return activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public Integer getHostId() {
        return hostId;
    }

    public void setHostId(Integer hostId) {
        this.hostId = hostId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Integer getSpan() {
        return span;
    }

    public void setSpan(Integer span) {
        this.span = span;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getExpense() {
        return expense;
    }

    public void setExpense(Double expense) {
        this.expense = expense;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
