package com.example.productmanagement.service;

import com.example.productmanagement.db.DBConnection;
import com.example.productmanagement.model.Category;
import com.example.productmanagement.model.Product;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class DataRetriever {
    public DataRetriever() {}

    public List<Category> getAllCategories() throws SQLException {
        String categoryQuery = "SELECT id, name FROM product_category";
        List<Category> categories = new ArrayList<>();
        DBConnection dbConnection = new DBConnection();

        try (Connection con = dbConnection.getDBConnection();
             PreparedStatement ps = con.prepareStatement(categoryQuery);
             ResultSet rs = ps.executeQuery()) {

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

        DBConnection dbConnection = new DBConnection();
        List<Product> products = new ArrayList<>();

        int offset = (page - 1) * size;

        try (Connection con = dbConnection.getDBConnection();
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


    public List<Product> getProductsByCriteria(
            String productName,
            String categoryName,
            Instant creationMin,
            Instant creationMax
    ) throws SQLException {

        StringBuilder query = new StringBuilder("""
            SELECT p.id, p.name, p.price, p.creation_datetime,
                   c.id AS cat_id, c.name AS cat_name
            FROM product p
            LEFT JOIN product_category c ON p.id = c.product_id
            WHERE 1=1
        """);

        DBConnection dbConnection = new DBConnection();

        List<Object> parameters = new ArrayList<>();

        if (productName != null) {
            query.append(" AND p.name ILIKE ? ");
            parameters.add("%" + productName + "%");
        }

        if (categoryName != null) {
            query.append(" AND c.name ILIKE ? ");
            parameters.add("%" + categoryName + "%");
        }

        if (creationMin != null) {
            query.append(" AND p.creation_datetime >= ? ");
            parameters.add(Timestamp.from(creationMin));
        }

        if (creationMax != null) {
            query.append(" AND p.creation_datetime <= ? ");
            parameters.add(Timestamp.from(creationMax));
        }

        query.append(" ORDER BY p.id ASC ");

        List<Product> products = new ArrayList<>();

        try (Connection con = dbConnection.getDBConnection();
             PreparedStatement ps = con.prepareStatement(query.toString())) {

            for (int i = 0; i < parameters.size(); i++) {
                ps.setObject(i + 1, parameters.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Category category = new Category(
                            rs.getInt("cat_id"),
                            rs.getString("cat_name")
                    );

                    Product product = new Product(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getDouble("price"),
                            rs.getTimestamp("creation_datetime").toInstant(),
                            category
                    );

                    products.add(product);
                }
            }
        }

        return products;
    }


    public List<Product> getProductsByCriteria(
            String productName,
            String categoryName,
            Instant creationMin,
            Instant creationMax,
            int page,
            int size
    ) throws SQLException {

        List<Product> products = getProductsByCriteria(productName, categoryName, creationMin, creationMax);

        if (page < 1 || size < 1) {
            return new ArrayList<>();
        }

        int start = (page - 1) * size;

        if (start >= products.size()) {
            return new ArrayList<>();
        }

        int end = Math.min(start + size, products.size());

        return products.subList(start, end);
    }

}
