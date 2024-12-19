/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.model;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import javax.imageio.ImageIO;

/**
 *
 * @author Admin
 */
public class Dish {
    private int id;
    private String name;
    private BigDecimal price;
    private String category;
    private byte[] image;

    public Dish(String name, BigDecimal price, String category, byte[] image) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.image  = image;
    }
    
    public Dish() {
    }

    public Dish(String name, double price) {
        this.name = name;
        this.price = BigDecimal.valueOf(price);
    }
    public Image getImageAsImage() {
        try {
            if (image != null && image.length > 0) {
                ByteArrayInputStream inputStream = new ByteArrayInputStream(image);
                return ImageIO.read(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public BufferedImage getImageAsBufferedImage() {
        try {
            if (image != null && image.length > 0) {
                ByteArrayInputStream inputStream = new ByteArrayInputStream(image);
                return ImageIO.read(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
    
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public byte[] getImage() {
        return image;
    }

    @Override
    public String toString() {
        return "Dish{" + "id=" + id + ", name=" + name + ", price=" + price + ", category=" + category + '}';
    }
    
    
}
