package com.management.carrot97.bean;

import java.util.Date;


public class User {
    // 唯一序列号
    private final Integer userId;

    // 用户名
    private String userName;

    // 密码
    private String password;

    // 生日
    private Date birthday;

    // 头像保存路径
    private String imgPath;

    // 学位（硕士or博士）
    private Degree degree;

    // 电子邮箱
    private String email;

    // 电话号码
    private String phoneNumber;

    // 权限
    private Privilege privilege;

    // 主构造函数
    public User(Integer userId, String userName, String password, Date birthday, String imgPath, Degree degree, String email, String phoneNumber, Privilege privilege) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.birthday = birthday;
        this.imgPath = imgPath;
        this.degree = degree;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.privilege = privilege;
    }

    /**
     * 注册时构造函数1
     * userId为数据库自增主键
     * privilege权限默认为普通用户
     */
    public User(String userName, String password, Date birthday, String imgPath, Degree degree, String email, String phoneNumber) {
        this(null,
                userName,
                password,
                birthday,
                imgPath,
                degree,
                email,
                phoneNumber,
                Privilege.USER);
    }

    /**
     * 注册时构造函数2
     * 在1的基础上支持不上传头像，使用默认头像
     */
    public User(String userName, String password, Date birthday, Degree degree, String email, String phoneNumber) {
        this(null,
                userName,
                password,
                birthday,
                "default",
                degree,
                email,
                phoneNumber,
                Privilege.USER);
    }

    /**
     * 注册时构造函数3
     * 在2的基础上添加id
     */
    public User(Integer id, String userName, String password, Date birthday, Degree degree, String email, String phoneNumber) {
        this(id,
            userName,
            password,
            birthday,
            "default",
            degree,
            email,
            phoneNumber,
            Privilege.USER);
    }

    /***
     *  提取时构造器
     *  默认用户权限为user
     */
    public User(Integer userId, String userName, String password, Date birthday, String imgPath, Degree degree, String email, String phoneNumber) {
        this(userId,
            userName,
            password,
            birthday,
            imgPath,
            degree,
            email,
            phoneNumber,
            Privilege.USER);
    }

    public User() {
        this(1, null,null,null,null,null,null,null,null);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", birthday=" + birthday +
                ", imgPath='" + imgPath + '\'' +
                ", degree=" + degree +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", privilege=" + privilege +
                '}';
    }

    public Integer getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Degree getdegree() {
        return degree;
    }

    public void setdegree(Degree degree) {
        this.degree = degree;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Privilege getPrivilege() {
        return privilege;
    }

    public void setPrivilege(Privilege privilege) {
        this.privilege = privilege;
    }
}
