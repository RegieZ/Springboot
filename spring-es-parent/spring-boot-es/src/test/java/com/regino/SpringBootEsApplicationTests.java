package com.regino;

import com.regino.pojo.Goods;
import com.regino.repository.GoodRepository;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootEsApplicationTests {

    @Autowired
    private ElasticsearchTemplate template;

    @Autowired
    private GoodRepository goodRepository;

    //删除索引库
    @Test
    public void delMapping() {
        boolean flag = template.deleteIndex(Goods.class);
        System.out.println("删除索引库是否成功： " + flag);
    }

    //创建索引库，创建类型
    @Test
    public void createIndex() {
        boolean index = template.createIndex(Goods.class);
        System.out.println("创建索引库是否成功： " + index);
        boolean putMapping = template.putMapping(Goods.class);
        System.out.println("创建类型是否成功： " + putMapping);
    }

    //新增文档
    @Test
    public void addDoc() {
        //1.准备数据
        Goods goods = new Goods(1L, "苹果手机", "手机", "苹果",
                1999.00, "http://www.apple.com");
        //2.保存（有则修改，无则添加）
        goodRepository.save(goods);
    }

    //批量新增
    @Test
    public void batchAddDoc() {
        //1.准备数据
        List<Goods> list = new ArrayList<>();
        for (long i = 2; i < 10; i++) {
            Goods goods = new Goods(i, "苹果手机" + i, "手机", "苹果",
                    19999.00 + i, "http://www.apple.com");
            list.add(goods);
        }
        //2.保存
        goodRepository.saveAll(list);
    }

    //根据id查询文档
    @Test
    public void findById() {
        //1.根据id查询
        Optional<Goods> optional = goodRepository.findById(1L);
        //2.判断是否有值
        if (optional.isPresent()) { //有值
            Goods goods = optional.get();
            System.out.println("查询结果：" + goods);
        }
    }

    //查询所有--->match all
    @Test
    public void findAll() {
        Iterable<Goods> iterable = goodRepository.findAll();
        for (Goods goods : iterable) {
            System.out.println("查询结果：" + goods);
        }
    }

    //term查询
    @Test
    public void termDoc() {
        //1.构建queryBuilder对象
        QueryBuilder queryBuilder = QueryBuilders.termQuery("title", "大米");
        //2.使用goodRepository完成调用
        Iterable<Goods> search = goodRepository.search(queryBuilder);
        //3.解析结果
        for (Goods goods : search) {
            System.out.println("查询结果：" + goods);
        }
    }

    //分页+排序
    @Test
    public void sesarchPageAndSort() {
        //创建排序对象
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        //创建分页排序对象
        /*
            第一个参数：页数。0-->第一页，1--->第二页，以此类推
            第二个参数：页大小
         */
        Pageable pageable = PageRequest.of(0, 2, sort);
        Page<Goods> page = goodRepository.search(QueryBuilders.matchAllQuery(), pageable);
        //解析结果
        //结果总数
        long totalElements = page.getTotalElements();
        System.out.println("总数： " + totalElements);
        //总页数
        int totalPages = page.getTotalPages();
        System.out.println("总页数： " + totalPages);
        //数据
        List<Goods> goodsList = page.getContent();
        for (Goods goods : goodsList) {
            System.out.println("查询结果数据：" + goods);
        }
    }

    //根据价格范围查询
    @Test
    public void findByPrice () {
        List<Goods> byPriceBetween = goodRepository.findByPriceBetween(20001.00, 20006.00);
        for (Goods goods : byPriceBetween) {
            System.out.println(goods);
        }
    }
}
