package com.regino;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootMybatisApplicationTests {

    // 由于没有采用自动配置，所以需要手动创建注入容器
    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void contextLoads() {
		/*
		第一个参数： 请求地址
		第二个参数： 返回值类型
		 */
        String forObject = restTemplate.getForObject("http://www.baidu.com", String.class);
        System.out.println(forObject);
    }

    @Test
    public void testFindAllStr() {
        String forObject = restTemplate.getForObject("http://localhost:8082/findAllStr", String.class);
        System.out.println(forObject);
        System.out.println("==============================");
        List list = restTemplate.getForObject("http://localhost:8082/findAll", List.class);
        System.out.println(list);
    }
}
