/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.service;

import com.mycompany.dao.BillDAO;
import com.mycompany.model.Bill;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Admin
 */
public class BillService {
    public static List<Bill> getAll(){
        List<Bill> list = BillDAO.getAll();
        return list;
    }
    public static List<Bill> getWithDate(LocalDate d1,LocalDate d2){
        List<Bill> list = BillDAO.getWithDate(d1,d2);
        return list;
    }
    public static Bill getById(int id){
        Bill bill = BillDAO.getById(id);
        return bill;
    }
    public static void create(Bill bill){
        BillDAO.create(bill);
    }
}
