package com.management.carrot97.mapper;

import com.management.carrot97.bean.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

    /**
     * create
     *
     */
    Boolean addUser(User user);


    /**
     * retrieve
     *
     */
    User getUserByName(String username);

    // 按邮箱查找*一个*用户
    User getUserByEmail(String oldEmail, String newEmail);

    // 按手机号查找*一个*用户
    User getUserByPhoneNumber(String oldPhoneNumber, String newPhoneNumber);

    Boolean updatePortrait(User user);

    Boolean updateUser(User user);
}
