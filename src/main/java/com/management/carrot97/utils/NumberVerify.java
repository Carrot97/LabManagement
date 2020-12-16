package com.management.carrot97.utils;

public class NumberVerify {
    //验证活动时间
    public static boolean verifyTime(Integer time) {
        return time != null && time != 0;
    }

    // 验证收支类型
    public static boolean verifyBillType(Integer type) {
        return type != null && (type == -1 || type == 1);
    }

    // 验证金额
    public static boolean verifyAmount(Double amount) {
        return amount != null && amount > 0;
    }
}
