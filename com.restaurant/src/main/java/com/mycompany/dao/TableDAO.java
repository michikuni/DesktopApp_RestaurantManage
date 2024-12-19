/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dao;

import com.mycompany.model.Table;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class TableDAO {

    public TableDAO() {
        super();
    }

    public static List<Table> getAll() {
        List<Table> tables = new ArrayList<Table>();
        Connection connect = null;
        try {
            connect = JDBCConnection.getJDBCConnection();
            String sql = "select * from _table";
            Statement statment = connect.createStatement();
            ResultSet rs = statment.executeQuery(sql);
            while (rs.next()) {
                Table table = new Table();
                table.setId(rs.getInt("id"));
                table.setName(rs.getString("name"));

                tables.add(table);
            }
            return tables;
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
        return tables;
    }

    public static void create(Table table) {
        Connection connect = null;
        try {
            connect = JDBCConnection.getJDBCConnection();
            String sql = "INSERT INTO _table (name) VALUES (?);";
            PreparedStatement preparedStatment = connect.prepareStatement(sql);
            preparedStatment.setString(1, table.getName());

            int rs = preparedStatment.executeUpdate();

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
            String sql = "delete from _table where id = ?";
            PreparedStatement preparedStatment = connect.prepareStatement(sql);
            preparedStatment.setInt(1, id);
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

    public static Table getById(int id) {

        Table table = null;
        Connection connect = null;
        try {
            connect = JDBCConnection.getJDBCConnection();
            String sql = "select * from _table where id = ?";
            PreparedStatement preparedStatment = connect.prepareStatement(sql);
            preparedStatment.setInt(1, id);
            ResultSet rs = preparedStatment.executeQuery();
            while (rs.next()) {
                table = new Table();
                table.setId(rs.getInt("id"));
                table.setName(rs.getString("name"));

            }
            return table;
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
        return table;
    }

    public static void update(Table table) {

        Connection connect = null;
        try {
            connect = JDBCConnection.getJDBCConnection();
            String sql = "update _table set name = ? where id = ?";
            PreparedStatement preparedStatment = connect.prepareStatement(sql);

            preparedStatment.setString(1, table.getName());

            preparedStatment.setInt(2, table.getId());
            System.out.println(table.toString());
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
}
