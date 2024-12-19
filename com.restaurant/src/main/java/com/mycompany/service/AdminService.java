/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.service;

import com.mycompany.dao.AdminDAO;
import com.mycompany.model.Admin;
import java.util.List;

/**
 *
 * @author Admin
 */
public class AdminService extends Service{

    public AdminService() {
        super();
    }
    
    static public Admin login(String username, String password){
        Admin a = AdminDAO.login(username, password);
        return a;
    }
    static public List<Admin> getAll(){
        List<Admin> admins = AdminDAO.getAll();
        return admins;
    }
    static public void create(Admin admin){
        AdminDAO.create(admin);
    }
    static public void delete(int id){
        AdminDAO.delete(id);
    }
    static public void update(Admin admin){
        AdminDAO.update(admin);
    }
    static public Admin getByUsername(String username){
        Admin a = AdminDAO.getByUsername( username);
        return a;
    }
    static public Admin getById(int id){
        Admin a = AdminDAO.getById(id);
        return a;
    }
}
