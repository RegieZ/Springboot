package com.regino;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class Test02 {

    RestHighLevelClient client;

    //初始化客户端
    @Before
    public void initClient() {
        client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")));
    }

    //匹配查询（match）
    @Test
    public void search() {
        //3.创建 QueryBuilder 对象，通过工厂方法创建，不需要new（指定查询类型 match/match all/term/terms）
        QueryBuilder queryBuilder = QueryBuilders.matchQuery("title", "苹果");
        commonSearch(queryBuilder);
    }

    //匹配查询（matchAll）（巩固）
    @Test
    public void matchAll() {
        //3.创建queryBuilder对象，指定match all查询
        QueryBuilder queryBuilder = QueryBuilders.matchAllQuery();
        commonSearch(queryBuilder);
    }

    //公共查询方法
    private void commonSearch(QueryBuilder queryBuilder) {
        //1.创建搜索请求对象 SearchRequest（封装的搜索请求对象）
        SearchRequest searchRequest = new SearchRequest("reggie");
        //2.创建 SearchSourceBuilder（指定排序、高亮、分页）
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(queryBuilder);
        searchRequest.source(searchSourceBuilder);
        try {
            //4.使用client通信
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            //5.解析结果
            SearchHit[] hits = searchResponse.getHits().getHits();
            for (SearchHit hit : hits) {
                String sourceAsString = hit.getSourceAsString();
                System.out.println("结果：" + sourceAsString);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //关闭客户端
    @After
    public void closeClient() {
        //释放资源
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}