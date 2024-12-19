/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dao;

import com.mycompany.model.Dish;

import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class DishDAO {

    public DishDAO() {
        super();
    }

    public static Dish createDishFromBlob(Blob blob) {
        Dish dish = new Dish();
        try {
            if (blob != null) {
                int blobLength = (int) blob.length();
                byte[] imageBytes = blob.getBytes(1, blobLength);
                dish.setImage(imageBytes);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dish;
    }

    public static List<Dish> getAll() {
        List<Dish> dishs = new ArrayList<Dish>();
        Connection connect = null;
        try {
            connect = JDBCConnection.getJDBCConnection();
            String sql = "select * from dish";
            Statement statment = connect.createStatement();
            ResultSet rs = statment.executeQuery(sql);
            while (rs.next()) {
                Dish dish = new Dish();
                dish.setId(rs.getInt("id"));
                dish.setName(rs.getString("name"));
                dish.setPrice(rs.getBigDecimal("price"));
                dish.setCategory(rs.getString("category"));
                Blob blob = rs.getBlob("image");
                if (blob != null) {
                    int blobLength = (int) blob.length();
                    byte[] imageBytes = blob.getBytes(1, blobLength);
                    dish.setImage(imageBytes);
                }
                dishs.add(dish);
            }
            return dishs;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connect != null) {
                try {
                    connect.close();
                } catch (SQLException e) {
                    e.printStackTrace();  // Handle the exception appropriately
                }
            }

        }
        return dishs;
    }

    public static void create(Dish dish) {
        Connection connect = null;
        try {
            connect = JDBCConnection.getJDBCConnection();
            String sql = "INSERT INTO dish (name, price, category ,image) VALUES (?, ?, ?, ?);";
            PreparedStatement preparedStatment = connect.prepareStatement(sql);
            preparedStatment.setString(1, dish.getName());
            preparedStatment.setBigDecimal(2, dish.getPrice());
            preparedStatment.setString(3, dish.getCategory());
            if (dish.getImage() != null) {
                ByteArrayInputStream imageStream = new ByteArrayInputStream(dish.getImage());
                preparedStatment.setBinaryStream(4, imageStream);

            } else {
                System.out.print("Image is null");
                preparedStatment.setNull(4, Types.BLOB);
            }
            int rs = preparedStatment.executeUpdate();
            System.out.print(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connect != null) {
                try {
                    connect.close();
                } catch (SQLException e) {
                    e.printStackTrace();  // Handle the exception appropriately
                }
            }

        }

    }

    public static void delete(int id) {
        Connection connect = null;
        try {
            connect = JDBCConnection.getJDBCConnection();
            String sql = "delete from dish where id = ?";
            PreparedStatement preparedStatment = connect.prepareStatement(sql);
            preparedStatment.setInt(1, id);
            preparedStatment.executeUpdate();
            System.out.println("Delete dish " + id);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connect != null) {
                try {
                    connect.close();
                } catch (SQLException e) {
                    e.printStackTrace();  // Handle the exception appropriately
                }
            }

        }
    }

    public static Dish getById(int id) {

        Dish dish = null;
        Connection connect = null;
        try {
            connect = JDBCConnection.getJDBCConnection();
            String sql = "select * from dish where id = ?";
            PreparedStatement preparedStatment = connect.prepareStatement(sql);
            preparedStatment.setInt(1, id);
            ResultSet rs = preparedStatment.executeQuery();
            while (rs.next()) {
                dish = new Dish();
                dish.setId(rs.getInt("id"));
                dish.setName(rs.getString("name"));
                dish.setPrice(rs.getBigDecimal("price"));
                dish.setCategory(rs.getString("category"));
                Blob blob = rs.getBlob("image");
                if (blob != null) {
                    int blobLength = (int) blob.length();
                    byte[] imageBytes = blob.getBytes(1, blobLength);
                    dish.setImage(imageBytes);
                }

            }
            return dish;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connect != null) {
                try {
                    connect.close();
                } catch (SQLException e) {
                    e.printStackTrace();  // Handle the exception appropriately
                }
            }

        }
        return dish;
    }

    public static void update(Dish dish) {

        Connection connect = null;
        try {
            connect = JDBCConnection.getJDBCConnection();
            String sql = "update dish set name = ?, price = ?, category = ? ,image = ? where id = ?";
            PreparedStatement preparedStatment = connect.prepareStatement(sql);

            preparedStatment.setString(1, dish.getName());
            preparedStatment.setBigDecimal(2, dish.getPrice());
            preparedStatment.setString(3, dish.getCategory());
            if (dish.getImage() != null) {
                ByteArrayInputStream imageStream = new ByteArrayInputStream(dish.getImage());
                preparedStatment.setBinaryStream(4, imageStream);

            } else {
                System.out.print("Image is null");
                preparedStatment.setNull(4, Types.BLOB);
            }
            preparedStatment.setInt(5, dish.getId());
            System.out.println(dish.toString());
            preparedStatment.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connect != null) {
                try {
                    connect.close();
                } catch (SQLException e) {
                    e.printStackTrace();  // Handle the exception appropriately
                }
            }

        }

    }

    public static List<String> getCategory() {

        List<String> categories = new ArrayList<String>();
        Connection connect = null;
        try {
            connect = JDBCConnection.getJDBCConnection();
            String sql = "select distinct category from dish";
            Statement statment = connect.createStatement();
            ResultSet rs = statment.executeQuery(sql);
            while (rs.next()) {

                categories.add(rs.getString("category"));
            }
            return categories;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connect != null) {
                try {
                    connect.close();
                } catch (SQLException e) {
                    e.printStackTrace();  // Handle the exception appropriately
                }
            }

        }
        return categories;

    }

    public static List<Dish> getByCategory(String category) {

        List<Dish> dishs = new ArrayList<Dish>();
        Connection connect = null;
        try {
            connect = JDBCConnection.getJDBCConnection();
            String sql = "select * from dish where category = ?";
            PreparedStatement preparedStatement = connect.prepareStatement(sql);
            preparedStatement.setString(1, category);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Dish dish = new Dish();
                dish.setId(rs.getInt("id"));
                dish.setName(rs.getString("name"));
                dish.setPrice(rs.getBigDecimal("price"));
                dish.setCategory(rs.getString("category"));
                Blob blob = rs.getBlob("image");
                if (blob != null) {
                    int blobLength = (int) blob.length();
                    byte[] imageBytes = blob.getBytes(1, blobLength);
                    dish.setImage(imageBytes);
                }
                dishs.add(dish);
            }
            return dishs;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connect != null) {
                try {
                    connect.close();
                } catch (SQLException e) {
                    e.printStackTrace();  // Handle the exception appropriately
                }
            }

        }
        return dishs;

    }
}
