package com.management.carrot97.utils;

public class StringVerify {
    // 判断字符串是否为空串
    public static boolean isEmpty(String str) {
        return "".equals(str);
    }

    // 验证密码合法性（包含 数字,英文,字符中的两种以上，长度6-20）
    public static boolean verifyPassword(String password) {
        boolean result = false;
        if (password.matches("^(?![0-9]+$)(?![a-z]+$)(?![A-Z]+$)(?!([^(0-9a-zA-Z)])+$).{6,20}$")) {
            result = true;
        }
        return result;
    }

    // 验证中文名正确性（2-4个中文字符）
    public static boolean verifyName(String name) {
        boolean result = false;
        if (name.matches("[\u0391-\uFFE5\\pP]{2,4}")) {
            result = true;
        }
        return result;
    }

    // 验证邮箱正确性
    public static boolean verifyEmail(String email) {
        boolean result = false;
        if (email.matches("^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$")) {
            result = true;
        }
        return result;
    }

    // 验证手机号正确性（以1开头的11位数字）
    public static boolean verifyPhoneNumber(String phoneNumber) {
        boolean result = false;
        if (phoneNumber.matches("^1[3|4|5|7|8][0-9]{9}$")) {
            result = true;
        }
        return result;
    }
}
