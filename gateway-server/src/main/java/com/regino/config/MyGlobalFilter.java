package com.regino.config;

import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class MyGlobalFilter implements GlobalFilter, Ordered {

    /*
        编写具体业务逻辑：
            校验是否有token，有则放行无则拦截返回---》无权限访问
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //1.从请求参数中获取token
        String token = exchange.getRequest().getQueryParams().getFirst("token");
        //2.判断token是否为空
        if(StringUtils.isEmpty(token)){
            //3.如果为空---》无权限访问
            //设置无权限状态码
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        //4.如果token不为空---》放行
        return chain.filter(exchange);
    }

    /*
        代表过滤器的执行顺序，值越小优先级越高
        值不要大于等于2
     */
    @Override
    public int getOrder() {
        return 0;
    }
}