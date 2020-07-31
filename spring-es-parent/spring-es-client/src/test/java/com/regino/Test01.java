package com.regino;

import com.alibaba.fastjson.JSON;
import com.regino.pojo.Product;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class Test01 {

    RestHighLevelClient client;

    //初始化客户端
    @Before
    public void initClient() {
        //es集群
        /*RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("192.168.200.132", 9201, "http"),
                        new HttpHost("192.168.200.132", 9202, "http"),
                        new HttpHost("192.168.200.132", 9203, "http")));*/
        //链接es客户端
        client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("192.168.200.132", 9200, "http")));
    }

    //新增文档 IndexRequest
    @Test
    public void createDoc() {
        //1.创建json数据
        Product product = new Product(2L, "苹果手机", "手机", "苹果", 1999.00, "http://www.apple.com");//这里的id是_sourceID
        String json = JSON.toJSONString(product);
        //2.创建请求对象
        IndexRequest indexRequest = new IndexRequest("reggie", "product", "1");//这里的id是_id
        //3.封装请求数据至请求对象
        indexRequest.source(json, XContentType.JSON);
        //4.使用client进行传输
        try {
            client.index(indexRequest, RequestOptions.DEFAULT);
            //5.接收相应结果并打印
            System.out.println("结果信息：" + JSON.toJSONString(indexRequest));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //根据id查询文档 GetResponse
    @Test
    public void getDocById() {
        //1.创建查询请求对象
        GetRequest getRequest = new GetRequest("reggie", "product", "1");
        //2.使用client进行传输
        try {
            client.get(getRequest, RequestOptions.DEFAULT);
            //3.接收响应结果并打印
            System.out.println("结果信息：" + JSON.toJSONString(getRequest));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //批量新增
    @Test
    public void batchAddDoc() {
        //1.创建批量请求对象
        BulkRequest bulkRequest = new BulkRequest();
        //2.准备批量新增数据
        for (long i = 2; i < 10; i++) {
            //1.创建json数据
            Product product = new Product(i, "苹果手机" + i, "手机", "苹果",
                    2999.00 + i, "http://www.apple.com");
            String json = JSON.toJSONString(product);
            //2.创建请求对象 id--->_id 必须全局唯一
            IndexRequest indexRequest = new IndexRequest("reggie", "product", i + "");
            //3.封装请求数据至请求对象
            indexRequest.source(json, XContentType.JSON);
            //4.将indexRequest对象添加到BulkRequest对象中
            bulkRequest.add(indexRequest);
        }
        //3.使用client进行传输
        try {
            client.bulk(bulkRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //根据id修改数据
    @Test
    public void updateDocById() {
        //1.创建修改请求对象
        UpdateRequest updateRequest = new UpdateRequest("reggie", "product", "1");
        //2.准备数据
        Product product = new Product(10L, "苹果手机", "手机", "苹果",
                2899.00, "http://www.apple.com");
        String json = JSON.toJSONString(product);
        //3.将数据封装到修改请求对象中
        updateRequest.doc(json, XContentType.JSON);
        try {
            //4.使用client进行传输
            UpdateResponse updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
            //5.解析结果
            System.out.println("结果信息：" + JSON.toJSONString(updateResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //删除与根据id查询类似
    @Test
    public void delDocById() {
        DeleteRequest deleteRequest = new DeleteRequest();
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
