package com.atguigu.ssyx.product.controller;

import java.time.LocalDate;
import java.util.UUID;

public class Test {
    public static void main(String[] args) {
        String filename = "http://localhost:9001/admin/product/download/2023/08/23/abc.jpg";
        System.out.println(filename.substring(filename.indexOf("download") + 8));
    }
}
