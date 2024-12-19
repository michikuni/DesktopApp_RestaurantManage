/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.model;

/**
 *
 * @author Admin
 */
public class Admin {
    private int id;
    private String name;
    private String username;
    private String password;
    private String role;
    private String phone;
    private boolean isAuth;
    public Admin(int id, String name, String username, String password, String role) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public Admin() {
    }
    
    public void login(String username, String password){
        
    };
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getPhone() {
        return phone;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isIsAuth() {
        return isAuth;
    }

    public void setIsAuth(boolean isAuth) {
        this.isAuth = isAuth;
    }

    @Override
    public String toString() {
        return "Admin{" + "id=" + id + ", name=" + name + ", username=" + username + ", password=" + password + ", role=" + role + ", phone=" + phone + ", isAuth=" + isAuth + '}';
    }
    
}
