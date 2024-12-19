/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.service;

import com.mycompany.dao.DishDAO;
import com.mycompany.model.Dish;
import java.sql.Blob;
import java.util.List;

/**
 *
 * @author Admin
 */
public class DishService extends Service{

    public DishService() {
        super();
    }
    public static Dish createDishFromBlob(Blob blob){
        Dish posMenu = DishDAO.createDishFromBlob(blob);
        return posMenu;
    }
    public static List<Dish> getAll() {
        List<Dish> menu = DishDAO.getAll();
        return menu;
    }
    public static void create(Dish dish){
        DishDAO.create(dish);
    }

    /**
     *
     */
    public static void update(Dish dish){
        DishDAO.update(dish);
    }
 
    static public void delete(int id){
        DishDAO.delete(id);
       
    }
    static public Dish getById(int id){
        Dish dish = DishDAO.getById(id);
        return dish;
    }
    static public List<String>getCategory(){
        List<String> categories = DishDAO.getCategory();
        return categories;
    }
    static public List<Dish>getByCategory(String category){
        List<Dish> dishs = DishDAO.getByCategory(category);
        return dishs;
    }
}
