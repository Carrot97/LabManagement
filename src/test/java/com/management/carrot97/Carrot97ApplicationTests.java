package com.management.carrot97;

import com.github.pagehelper.PageInfo;
import com.management.carrot97.bean.*;
import com.management.carrot97.mapper.ActivityMapper;
import com.management.carrot97.mapper.UserMapper;
import com.management.carrot97.service.ActivityService;
import com.management.carrot97.service.UserService;
import com.management.carrot97.utils.StringVerify;
import com.management.carrot97.utils.Transformer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Carrot97ApplicationTests {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserService userService;

    @Test
    public void contextLoads() throws ParseException {
        User oldUser = Transformer.originalUser2User(1, new OriginalUser("李旭辉",
                "yyws0317.",
                "yyws0317.",
                "04-13",
                 Degree.MASTER,
                "lixuhuiustb@163.com",
                "18612099318"));
        OriginalUser originalUser = new OriginalUser("李旭辉",
                "yyws0413.",
                "yyws0413.",
                "04-13",
                Degree.MASTER,
                "lixuhuiustb@163.com",
                "18612099318");
        System.out.println(userService.verifyAndUpdateUser(oldUser, originalUser));
    }

    @Test
    public void test2() throws ParseException {
//        OriginalUser originalUser = new OriginalUser(
//                "韩树莹",
//                "123456",
//                "123456",
//                "03-17",
//                1,
//                "1041217529@qq.com",
//                "18612099319");
//
//        Map<String, Object> msg = userService.verifyAndAddUser(originalUser);
//        System.out.println(msg.toString());
    }

}
