/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.util;

import com.mycompany.view.component.ActionPanel;
import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author Admin
 */
public class TableActionCellEditor<T> extends DefaultCellEditor {
    private Class<T> clazz;

    /**
     *
     * @param clazz
     */
    public TableActionCellEditor(Class<T> clazz) {
        super(new JCheckBox());
        this.clazz = clazz;
     
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        ActionPanel panel = new ActionPanel(clazz);
        return panel;
    }
    
    
}
