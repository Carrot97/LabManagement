package com.management.carrot97;

import com.management.carrot97.bean.Activity;
import com.management.carrot97.bean.OriginalUser;
import com.management.carrot97.bean.Page;
import com.management.carrot97.bean.User;
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
    ActivityMapper activityMapper;

    @Autowired
    ActivityService activityService;

    @Test
    public void contextLoads() {
        List<Activity> activities = activityService.getPage(new Page(1, 5));
        System.out.println(activities.toString());
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
