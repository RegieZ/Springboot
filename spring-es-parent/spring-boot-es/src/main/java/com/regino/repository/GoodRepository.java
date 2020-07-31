package com.regino.repository;

import com.regino.pojo.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

//以上服务搭建好以后可以直接运行项目，项目在启动时springboot会自动检测当前索引库是否存在，不存在则自动创建
public interface GoodRepository extends ElasticsearchCrudRepository<Goods,Long> {//Goods中id的类型
}
