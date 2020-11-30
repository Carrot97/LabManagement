package com.management.carrot97;

import com.management.carrot97.bean.OriginalUser;
import com.management.carrot97.bean.User;
import com.management.carrot97.mapper.UserMapper;
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
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Carrot97ApplicationTests {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserService userService;

    @Test
    public void contextLoads() {
//        Map<String, Object> user = userService.verifyUser(new OriginalUser(
//                                                        "李旭辉",
//                                                        "123456",
//                                                        "1234567",
//                                                        "04-13",
//                                                        1,
//                                                        "1041217529@qq.com",
//                                                        "18612099318"));
//        System.out.println(user.toString());
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
