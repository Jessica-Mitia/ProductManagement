package com.example.productmanagement.main;

import com.example.productmanagement.service.DataRetriever;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        DataRetriever dataRetriever = new DataRetriever();
        System.out.println(dataRetriever.getAllCategories());
        System.out.println(dataRetriever.getProductList(2, 3));
    }
}
