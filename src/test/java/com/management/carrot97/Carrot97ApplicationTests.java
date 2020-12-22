package com.management.carrot97;

import com.github.pagehelper.PageInfo;
import com.management.carrot97.bean.*;
import com.management.carrot97.mapper.ActivityMapper;
import com.management.carrot97.mapper.ChuangsBillMapper;
import com.management.carrot97.mapper.UserMapper;
import com.management.carrot97.service.ActivityService;
import com.management.carrot97.service.ChuangsBillService;
import com.management.carrot97.service.UserService;
import com.management.carrot97.utils.StringVerify;
import com.management.carrot97.utils.Transformer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Carrot97ApplicationTests {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedisTemplate redisTemplate;


    @Test
    public void contextLoads() {
        System.out.println(stringRedisTemplate.opsForValue().get("msg"));
    }

    @Test
    public void test2() throws ParseException {

    }

}
