package com.regino;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.Map;

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

        //source过滤
        searchSourceBuilder.fetchSource(new String[]{"title", "price"}, new String[]{"brand"});//也可以是null

        //排序
        //  第一个参数指定排序字段
        //  第二个参数指定排序类型
        searchSourceBuilder.sort("id", SortOrder.DESC);
        searchSourceBuilder.sort("price", SortOrder.DESC);//id一样才排

        //分页
        //  from指定起始索引位置 int start = (pageNum-1) * pageSize，即往页数量
        //  size指定每页大小
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(2);

        //高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        //设置前置标签
        highlightBuilder.preTags("<font color='red'>");
        //设置后置标签
        highlightBuilder.postTags("</font>");
        //设置高亮字段
        highlightBuilder.field("title");
        searchSourceBuilder.highlighter(highlightBuilder);

        searchRequest.source(searchSourceBuilder);
        try {
            //4.使用client通信
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            //5.解析结果
            SearchHit[] hits = searchResponse.getHits().getHits();
            for (SearchHit hit : hits) {
                String sourceAsString = hit.getSourceAsString();
                System.out.println("结果：" + sourceAsString);

                //解析高亮结果
                Map<String, HighlightField> highlightFieldMap = hit.getHighlightFields();
                //最好先判断map是否为空
                if (!CollectionUtils.isEmpty(highlightFieldMap)) {
                    HighlightField highlightField = highlightFieldMap.get("title");
                    Text[] fragments = highlightField.getFragments();
                    for (Text fragment : fragments) {
                        System.out.println("高亮结果：" + fragment.toString());
                    }
                }
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