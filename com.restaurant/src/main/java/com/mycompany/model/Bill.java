/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class Bill {
    private int id ;
    private Map<Dish, Integer> list;
    private LocalDateTime time;

    public Bill(int id ) {
        this.list = new HashMap<>();
        this.id = id;
    }
    public Bill(){
        this.list = new HashMap<>();
    }
    public Bill(int id , LocalDateTime time){
        this.list = new HashMap<>();
        this.id = id;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
    
    public Map<Dish, Integer> getList() {
        return list;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void add(Dish dish, int quantity) {
        if(this.list.containsKey(dish)){
            int oldQuantity = list.get(dish);
             list.put(dish, oldQuantity + quantity );
        }
        else{
             list.put(dish,  quantity );
        }
    }

    public void remove(Dish dish) {
        list.remove(dish);
    }

    public void update(Dish dish, int quantity) {
        if (quantity > 0) {
            list.put(dish, quantity);

        } else {
            list.remove(dish);
        }
    }
    
    public void updateByDishId(int dishId,int quantity){
        Dish dish = findDishById(dishId);
        this.update(dish,quantity);
    }
     public Dish findDishById(int targetId) {
        for (Map.Entry<Dish, Integer> entry : list.entrySet()) {
            Dish dish = entry.getKey();
            if (dish.getId() == targetId) {
                return dish; // Found the dish with the specified ID
            }
        }
        return null; // Dish with the specified ID not found
    }
    public void removeById(int dishId){
        Dish dish = this.findDishById(dishId);
        this.remove(dish);
    }
    public void reset(){
        this.list = new HashMap<>();
    }

    public void setList(Map<Dish, Integer> list) {
        this.list = list;
    }
 
    public BigDecimal calculateTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (Map.Entry<Dish, Integer> entry : list.entrySet()) {
            Dish dish = entry.getKey();
            int quantity = entry.getValue();
            total = total.add(dish.getPrice().multiply(BigDecimal.valueOf(quantity)));
        }
        return total;
    }

    public void printBill() {
        for (Map.Entry<Dish, Integer> entry : list.entrySet()) {
            Dish dish = entry.getKey();
            int quantity = entry.getValue();

            System.out.println(String.valueOf(dish.getName()) + "(" + quantity + "): " + String.valueOf(dish.getPrice().multiply(BigDecimal.valueOf(quantity))) + "$");
        }
        System.out.println("Total: " + String.valueOf(this.calculateTotal()) + "$");
         System.out.println("Time: " + String.valueOf(this.time));
    }
    public Bill createCopy(){
        Bill b = new Bill();
        b.setId(this.id);
        b.setTime(this.time);
        b.setList(this.list);
        return b;
    }
}
