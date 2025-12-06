package com.example.productmanagement.service;

import com.example.productmanagement.db.DBConnection;
import com.example.productmanagement.model.Category;
import com.example.productmanagement.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataRetriever {
    public DataRetriever() {}

    public List<Category> getAllCategories() throws SQLException {
        String categoryQuery = "SELECT id, name FROM product_category";
        List<Category> categories = new ArrayList<>();
        try (Connection con = DBConnection.getDBConnection();
             PreparedStatement ps = con.prepareStatement(categoryQuery);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("All categories found");
            while (rs.next()) {
                Category category = new Category();

                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));

                categories.add(category);
            }
        } catch (SQLException e) {
            System.out.println("Error");
            e.printStackTrace();
        }

        return categories;
    }

    public List<Product> getProductList(int page, int size) throws SQLException {
        String productQuery = "SELECT p.id, p.name, p.price, p.creation_datetime, c.id as cat_id, c.name as cat_name" +
                " FROM product p LEFT JOIN product_category c " +
                " ON p.id = c.product_id LIMIT ? OFFSET ?";

        List<Product> products = new ArrayList<>();

        int offset = (page - 1) * size;

        try (Connection con = DBConnection.getDBConnection();
             PreparedStatement ps = con.prepareStatement(productQuery)) {

            ps.setInt(1, size);
            ps.setInt(2, offset);

            try (ResultSet rs = ps.executeQuery()) {

                System.out.println("Product list found");

                while (rs.next()) {
                    Category category = new Category();
                    category.setId(rs.getInt("cat_id"));
                    category.setName(rs.getString("cat_name"));

                    Product product = new Product();
                    product.setId(rs.getInt("id"));
                    product.setName(rs.getString("name"));
                    product.setPrice(rs.getDouble("price"));
                    product.setCreationDatetime(rs.getTimestamp("creation_datetime").toInstant());
                    product.setCategory(category);

                    products.add(product);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error");
            e.printStackTrace();
        }

        return products;
    }



}
