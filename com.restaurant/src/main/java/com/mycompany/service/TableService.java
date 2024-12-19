/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.service;

import com.mycompany.dao.DishDAO;
import com.mycompany.dao.TableDAO;
import com.mycompany.model.Dish;
import com.mycompany.model.Table;
import java.util.List;

/**
 *
 * @author Admin
 */
public class TableService {
      public static List<Table> getAll() {
        List<Table> tables = TableDAO.getAll();
        return tables;
    }
    public static void create(Table table){
        TableDAO.create(table);
    }

    /**
     *
     */
    public static void update(Table table){
        TableDAO.update(table);
    }
 
    static public void delete(int id){
        TableDAO.delete(id);
       
    }
    static public Table getById(int id){
        Table table = TableDAO.getById(id);
        return table;
    }
}
