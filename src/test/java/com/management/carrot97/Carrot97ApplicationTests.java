package com.management.carrot97;

import com.management.carrot97.utils.textTransform.Transformer;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.management.carrot97.utils.textTransform.TransformerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Carrot97ApplicationTests {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedisTemplate redisTemplate;


    @Test
    public void contextLoads() {
        String str = "欺骗干扰是通过对卫星信号重放转发或模仿伪造，使\n" +
                "用户终端做出错误判断的干扰攻击。 由于无需大功率的干\n" +
                "扰信号，因此它在空间上比压制干扰作用域更广，但这种\n" +
                "攻击需要对卫星信号编码特征具有一定了解。\n" +
                "Warner 等人在参考文 献 [9] 中 介 绍 了 针 对 GPS 中 C/A\n" +
                "码的欺骗干扰手段，并提出了能量鉴别、角度鉴别、认证加\n" +
                "密等反制措施。 能量鉴别是对干扰信号中的真实信号进行\n" +
                "残留检测，该方法只有在真实信号没有被完全抑制的情况\n" +
                "下才能奏效[10]；角度鉴别是通过干扰源与星座信号发射角\n" +
                "度的差异来检测真实信号，该方法只能鉴别单一方向的干\n";
        Transformer transformer = TransformerFactory.getTransformer(null);
        System.out.println(transformer.transform(str));
    }

    @Test
    public void test2() throws ParseException {

    }

}
