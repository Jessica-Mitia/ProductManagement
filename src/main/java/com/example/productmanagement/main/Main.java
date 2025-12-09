package com.example.productmanagement.main;

import com.example.productmanagement.service.DataRetriever;

import java.sql.SQLException;
import java.time.Instant;

public class Main {
    public static void main(String[] args) throws SQLException {
        DataRetriever dataRetriever = new DataRetriever();

        // getAllCategories()
        System.out.println("Method getAllCategories()");
        System.out.println(dataRetriever.getAllCategories());

        // getProductList()
        System.out.println("____________________________________");
        System.out.println("Method getProductList()");
        System.out.println(dataRetriever.getProductList(1,10));
        System.out.println(dataRetriever.getProductList(1,5));
        System.out.println(dataRetriever.getProductList(1,3));
        System.out.println(dataRetriever.getProductList(2,2));

        // getProductsByCriteria()
        System.out.println("____________________________________");
        System.out.println("Method getProductByCriteria()");
        System.out.println(dataRetriever.getProductsByCriteria("Dell", null, null, null));
        System.out.println(dataRetriever.getProductsByCriteria(null, "info", null, null));
        System.out.println(dataRetriever.getProductsByCriteria("Iphone", "mobile", null, null));
        System.out.println(dataRetriever.getProductsByCriteria(null, null, Instant.parse("2024-02-01T00:00:00Z"), Instant.parse("2024-03-01T00:00:00Z")));
        System.out.println(dataRetriever.getProductsByCriteria("Samsung", "bureau", null, null));
        System.out.println(dataRetriever.getProductsByCriteria("Sony", "informatique", null, null));
        System.out.println(dataRetriever.getProductsByCriteria(null, "audio", Instant.parse("2024-01-01T00:00:00Z"), Instant.parse("2024-12-01T00:00:00Z")));
        System.out.println(dataRetriever.getProductsByCriteria(null, null, null, null));

        //getProductsByCriteria() avec pagination
        System.out.println("____________________________________");
        System.out.println("Method getProductByCriteria() with pagination");
        System.out.println(dataRetriever.getProductsByCriteria(null, null, null, null, 1, 10));
        System.out.println(dataRetriever.getProductsByCriteria("Dell", null, null, null, 1, 5));
        System.out.println(dataRetriever.getProductsByCriteria(null, "informatique", null, null, 1, 10));
    }
}
