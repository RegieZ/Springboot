package com.regino.repository;

import com.regino.pojo.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

//以上服务搭建好以后可以直接运行项目，项目在启动时springboot会自动检测当前索引库是否存在，不存在则自动创建
public interface GoodRepository extends ElasticsearchRepository<Goods, Long> {//Goods中id的类型，ElasticsearchCrudRepository只有增删改查

    //根据价格范围查找
    List<Goods> findByPriceBetween(Double start, Double end);
}
