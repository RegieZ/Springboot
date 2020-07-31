package com.regino.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    private Long id;
    private String title;//标题
    private String category;//分类
    private String brand;
    private Double price;
    private String images;
}
