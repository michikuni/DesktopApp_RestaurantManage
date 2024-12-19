/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dao;

import com.mycompany.model.Bill;
import com.mycompany.model.Dish;
import com.mycompany.model.Table;
import com.mycompany.util.BillUtil;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class BillDAO {

    public BillDAO() {
        super();
    }

    public static List<Bill> getAll() {
        List<Bill> list = new ArrayList<Bill>();
        Connection connect = null;
        try {
            connect = JDBCConnection.getJDBCConnection();
            String sql = "SELECT b.id AS bill_id, b.time, b.price AS bill_price, d.id AS dish_id, d.name, d.price AS dish_price, bd.quantity FROM bill b JOIN billDish bd ON b.id = bd.billId JOIN dish d ON bd.dishId = d.id";
            Statement statment = connect.createStatement();
            ResultSet rs = statment.executeQuery(sql);
            while (rs.next()) {
                Bill billAv = BillUtil.findBillById(list, rs.getInt("bill_id"));
                Dish dish = new Dish();
                dish.setName(rs.getString("name"));
                dish.setPrice(rs.getBigDecimal("dish_price"));
                dish.setId(rs.getInt("dish_id"));
                int quantity = rs.getInt("quantity");
                if (billAv != null) {
                    billAv.add(dish, quantity);
                } else {
                    Bill bill = new Bill();
                    bill.setId(rs.getInt("bill_id"));
                    bill.setTime(rs.getTimestamp("time").toLocalDateTime());
                    bill.add(dish, quantity);
                    list.add(bill);
                }

            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (connect != null) {
                try {
                    connect.close();
                } catch (SQLException e) {
                    e.printStackTrace();  // Handle the exception appropriately
                }
            }

        }
        return list;
    }

    public static List<Bill> getWithDate(LocalDate d1, LocalDate d2) {
        List<Bill> list = new ArrayList<Bill>();
        Connection connect = null;
        try {
            connect = JDBCConnection.getJDBCConnection();
            String sql = "SELECT b.id AS bill_id, b.time, b.price AS bill_price, d.id AS dish_id, d.name, d.price AS dish_price, bd.quantity FROM bill b JOIN billDish bd ON b.id = bd.billId JOIN dish d ON bd.dishId = d.id where time between ? and ?";
            PreparedStatement preparedstatment = connect.prepareStatement(sql);
            preparedstatment.setDate(1, Date.valueOf(d1));
            preparedstatment.setDate(2, Date.valueOf(d2));
            ResultSet rs = preparedstatment.executeQuery();
            while (rs.next()) {
                Bill billAv = BillUtil.findBillById(list, rs.getInt("bill_id"));
                Dish dish = new Dish();
                dish.setName(rs.getString("name"));
                dish.setPrice(rs.getBigDecimal("dish_price"));
                dish.setId(rs.getInt("dish_id"));
                int quantity = rs.getInt("quantity");
                if (billAv != null) {
                    billAv.add(dish, quantity);
                } else {
                    Bill bill = new Bill();
                    bill.setId(rs.getInt("bill_id"));
                    bill.setTime(rs.getTimestamp("time").toLocalDateTime());
                    bill.add(dish, quantity);
                    list.add(bill);
                }

            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (connect != null) {
                try {
                    connect.close();
                } catch (SQLException e) {
                    e.printStackTrace();  // Handle the exception appropriately
                }
            }

        }
        return list;
    }

    public static Bill getById(int id) {

        Bill bill = null;
       Connection connect = null;
        try {
            connect = JDBCConnection.getJDBCConnection();
            String sql = "SELECT b.id AS bill_id, b.time, b.price AS bill_price, d.id AS dish_id, d.name, d.price AS dish_price, bd.quantity FROM bill b JOIN billDish bd ON b.id = bd.billId JOIN dish d ON bd.dishId = d.id where b.id = ?";
            PreparedStatement preparedstatment = connect.prepareStatement(sql);
            preparedstatment.setInt(1, id);

            ResultSet rs = preparedstatment.executeQuery();
            while (rs.next()) {

                Dish dish = new Dish();
                dish.setName(rs.getString("name"));
                dish.setPrice(rs.getBigDecimal("dish_price"));
                dish.setId(rs.getInt("dish_id"));
                int quantity = rs.getInt("quantity");
                if (bill != null) {
                    bill.add(dish, quantity);
                } else {
                    bill = new Bill();
                    bill.setId(rs.getInt("bill_id"));
                    bill.setTime(rs.getTimestamp("time").toLocalDateTime());
                    bill.add(dish, quantity);
                }
            }
            return bill;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (connect != null) {
                try {
                    connect.close();
                } catch (SQLException e) {
                    e.printStackTrace();  // Handle the exception appropriately
                }
            }

        }
        return bill;
    }

    public static void create(Bill bill) {
        Connection connect = null;
        try {
            connect = JDBCConnection.getJDBCConnection();
            String sql = "INSERT INTO bill (time, price) VALUES" + "(? , ?)";
            PreparedStatement billPreparedstatment = connect.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            billPreparedstatment.setObject(1, bill.getTime());
            billPreparedstatment.setBigDecimal(2, bill.calculateTotal());

            int affectedRows = billPreparedstatment.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating bill failed, no rows affected.");
            }
                try (ResultSet generatedKeys = billPreparedstatment.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int billId = generatedKeys.getInt(1);

                        for (Map.Entry<Dish, Integer> entry : bill.getList().entrySet()) {
                            Dish dish = entry.getKey();
                            int quantity = entry.getValue();

                            String insertBillDishQuery = "INSERT INTO billDish (billId, dishId, quantity) VALUES (?, ?, ?)";
                            try (PreparedStatement billDishStatement = connect.prepareStatement(insertBillDishQuery)) {
                                billDishStatement.setInt(1, billId);
                                billDishStatement.setInt(2, dish.getId());
                                billDishStatement.setInt(3, quantity);
                                billDishStatement.executeUpdate();
                            }
                        }
                    } else {
                        throw new SQLException("Creating bill failed, no ID obtained.");
                    }
                }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
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
