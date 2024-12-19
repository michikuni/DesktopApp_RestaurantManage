/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.util;

import com.mycompany.model.Bill;
import java.util.List;

/**
 *
 * @author Admin
 */
public class BillUtil {
    public static Bill findBillById(List<Bill> list, int id) {
        for (Bill bill : list) {
            if (bill.getId() == id) {
                return bill;
            }
        }
        return null;
    }
}
