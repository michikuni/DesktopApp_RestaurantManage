/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Admin
 */
public class HandleImage {
        
    public static Image getScaledImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, type);
        Graphics2D g = resizedImage.createGraphics();

        // Draw the original image onto the resized image, cropping or filling as needed
        double scale = Math.max((double) targetWidth / originalImage.getWidth(), (double) targetHeight / originalImage.getHeight());
        int width = (int) (originalImage.getWidth() * scale);
        int height = (int) (originalImage.getHeight() * scale);
        int x = (targetWidth - width) / 2;
        int y = (targetHeight - height) / 2;
        g.drawImage(originalImage, x, y, width, height, null);

        g.dispose();
        return resizedImage;
    }
    public static void adjustImageSize(JLabel label, ImageIcon icon) {
        int maxWidth = label.getWidth();
        int maxHeight = label.getHeight();

        int iconWidth = icon.getIconWidth();
        int iconHeight = icon.getIconHeight();

        double widthRatio = (double) maxWidth / iconWidth;
        double heightRatio = (double) maxHeight / iconHeight;

        double scaleFactor = Math.min(widthRatio, heightRatio);

        int newWidth = (int) (iconWidth * scaleFactor);
        int newHeight = (int) (iconHeight * scaleFactor);

        Image image = icon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        icon.setImage(image);
        label.setIcon(icon);
        int x = (maxWidth - newWidth) / 2;
        int y = (maxHeight - newHeight) / 2;
        label.setIconTextGap(0); // Remove the default gap
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.CENTER);
        label.setBounds(x, y, newWidth, newHeight);
    }
    public static void adjustImageSizeByWidth(JLabel label, ImageIcon icon){
        int maxWidth = label.getWidth();
        int maxHeight = label.getHeight();

        int iconWidth = icon.getIconWidth();
        int iconHeight = icon.getIconHeight();

        double widthRatio = (double) maxWidth / iconWidth;

        double scaleFactor = widthRatio;

        int newWidth = (int) (iconWidth * scaleFactor);
        int newHeight = (int) (iconHeight * scaleFactor);

        Image image = icon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        icon.setImage(image);
        label.setIcon(icon);
    }
}
