/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.view;

import com.mycompany.view.DashBoardForm;
import com.mycompany.dao.DishDAO;
import com.mycompany.model.Bill;
import com.mycompany.model.Dish;
import com.mycompany.model.Table;
import com.mycompany.util.*;
import com.mycompany.service.DishService;
import com.mycompany.service.TableService;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import com.mycompany.view.DashBoardForm;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Component;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Tomioka
 */
public class POSForm extends javax.swing.JFrame {

    private DishService dishService;
    private Bill bill;
    private List<Table> tableList;
    private Table orderTable;

    /**
     * Creates new form POSForm
     */
    public POSForm() {
        setTitle("POS");
        this.bill = new Bill();
        this.tableList = TableService.getAll();
        initComponents();
        customComponents();
        List<String> list = DishService.getCategory();
        DefaultListModel<String> model = new DefaultListModel<>();
        for (String cate : list) {
            model.addElement(cate);
        }
        catergList.setModel(model);
        catergList.setCellRenderer(new CustomCellRenderer());
        catergList = new javax.swing.JList<>();
        handleOrderTable(null);
    }

    class gradientPanel extends JPanel {

        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            int width = getWidth();
            int height = getHeight();

            Color color2 = new Color(255, 255, 255);
            Color color1 = new Color(157, 98, 245);
            GradientPaint gp = new GradientPaint(0, 0, color1, 180, height, color2);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, width, height);

        }
    }

    public boolean validateNatualNumber(String str) {
        String regex = "^[1-9][0-9]*$";
        String strr = str.trim();
        if (strr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Quantity field mustn't be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else {
            if (Pattern.matches(regex, strr)) {
                return true;
            }
            JOptionPane.showMessageDialog(this, "Quantity field must be a natural number!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;

        }

    }

    public void handleOrderTable(Table table) {
        this.orderTable = table;

        if (this.orderTable == null) {
            jLabel4.setText("Order ");
            jLabel6.setText("takeaway");
        } else {

            jLabel4.setText("Order for ");
            jLabel6.setText(table.getName());
        }
    }

    public void productHandleTable(String cate) {
        List<Dish> dishList;
        if (cate.equals("all")) {
            dishList = DishService.getAll();
        } else {
            dishList = DishService.getByCategory(cate);
        }
        DefaultTableModel proModel = (DefaultTableModel) prodctTable.getModel();
        proModel.setRowCount(0);

        // Đặt số lượng cột hiển thị ảnh
        int numImageColumns = 4;

        // Điều chỉnh kích thước của ảnh và panel
        int imageWidth = 150;
        int imageHeight = 150;
        int panelWidth = imageWidth + 20; // Thêm khoảng trắng giữa ảnh và mô tả
        int panelHeight = imageHeight + 45; // Đủ để chứa cả label mô tả

        // Tạo một mảng để lưu trữ tất cả các panel
        JPanel[][] imagePanels = new JPanel[dishList.size()][numImageColumns];

        // Điền mảng với panel từ danh sách Dish
        int rowCounter = 0;
        int columnCounter = 0;
        for (Dish dish : dishList) {
            // Tạo panel chứa ảnh và mô tả
            JPanel imagePanel = new JPanel();
            imagePanel.setLayout(new BorderLayout());

            // Tạo label chứa ảnh
            JLabel imageLabel = new JLabel();
            BufferedImage originalImage = null;
            String imagePath = "/image/image.png";
            if (dish.getImage() == null) {
                URL url = getClass().getResource(imagePath);
                File file = new File(url.getPath());

                try {
                    originalImage = ImageIO.read(file);
                } catch (IOException ex) {
                    Logger.getLogger(DashBoardForm.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                originalImage = dish.getImageAsBufferedImage();
            }
            Image scaledImage = HandleImage.getScaledImage(originalImage, imageWidth, imageHeight);
            ImageIcon icon = new ImageIcon(scaledImage);
            imageLabel.setIcon(icon);

            // Tạo label chứa mô tả
            Dimension preferredSize = new Dimension(150, 20);

            JLabel descriptionLabel = new JLabel(dish.getName());
            descriptionLabel.setPreferredSize(preferredSize);
            descriptionLabel.setHorizontalAlignment(JLabel.CENTER);
            descriptionLabel.setVerticalAlignment(JLabel.CENTER);
            JLabel priceLabel = new JLabel("Price: " + dish.getPrice());

            priceLabel.setPreferredSize(preferredSize);
            priceLabel.setHorizontalAlignment(JLabel.CENTER);
            priceLabel.setVerticalAlignment(JLabel.CENTER);

            imagePanel.add(descriptionLabel, BorderLayout.NORTH);
            imagePanel.add(imageLabel, BorderLayout.CENTER);

            imagePanel.add(priceLabel, BorderLayout.SOUTH);
            imagePanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Sử dụng BoxLayout để sắp xếp các thành phần theo chiều dọc

            // Thêm panel vào mảng
            imagePanels[rowCounter][columnCounter] = imagePanel;

            columnCounter++;
            if (columnCounter == numImageColumns) {
                columnCounter = 0;
                rowCounter++;
            }
        }

        // Thêm dữ liệu vào mô hình bảng
        for (int row = 0; row < imagePanels.length; row++) {
            proModel.addRow(imagePanels[row]);
        }

        // Đặt renderer cho tất cả các cột chứa hình ảnh và mô tả
        for (int i = 0; i < numImageColumns; i++) {
            prodctTable.getColumnModel().getColumn(i).setCellRenderer(new ImageCellRenderer());
        }

        // Đặt chiều cao của dòng trong bảng
        prodctTable.setRowHeight(panelHeight + 20);
        productMouseListener(cate);
    }

    class ImageCellRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value instanceof JLabel) {
                return (JLabel) value;
            }
            return (Component) value;
        }
    }

    public void handleBillReset() {
        this.bill = new Bill();
        System.out.println("reset");
        handleTablePreview();
    }

    public void handleStatusBtn() {

    }

    public void productMouseListener(String cate) {
        List<Dish> dishList;
        if (cate.equals("all")) {
            dishList = DishService.getAll();
        } else {
            dishList = DishService.getByCategory(cate);
        }
//        DefaultTableModel previewModel = (DefaultTableModel) previewBillTable.getModel();
        int numImageColumns = 4;

        MouseListener[] mouseListeners = prodctTable.getMouseListeners();
        for (MouseListener listener : mouseListeners) {
            prodctTable.removeMouseListener(listener);
        }

        prodctTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = prodctTable.rowAtPoint(e.getPoint());
                int column = prodctTable.columnAtPoint(e.getPoint());
                // Kiểm tra xem dòng và cột có hợp lệ không
                if (row >= 0 && column >= 0 && row < dishList.size() && column < numImageColumns) {
                    Dish selectedDish = dishList.get(row * numImageColumns + column);

                    // Hiển thị thông tin vào bảng previewBillTable
                    // Thêm thông tin của selectedDish vào bảng previewBillTable
                    POSForm.this.bill.add(selectedDish, 1);
                    handleTablePreview();
                }
            }
        });
    }

    public void handleTablePreview() {
        DefaultTableModel previewModel = (DefaultTableModel) previewBillTable.getModel();
        previewModel.setRowCount(0);
        int index = 1;
        for (Map.Entry<Dish, Integer> entry : bill.getList().entrySet()) {
            Dish dish = entry.getKey();
            int quantity = entry.getValue();
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(JLabel.CENTER);
            for (int i = 0; i < previewBillTable.getColumnCount(); i++) {
                previewBillTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
            previewModel.addRow(new Object[]{index++, dish.getId(), dish.getName(), dish.getPrice(), quantity, dish.getPrice().multiply(BigDecimal.valueOf(quantity))});
        }
    }

    public void MouseClickListener() {
        MouseListener[] mouseListeners = prodctTable.getMouseListeners();
        for (MouseListener listener : mouseListeners) {
            prodctTable.removeMouseListener(listener);
        }
        allCateLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                productHandleTable("all");
            }
        });

        catergList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JList<String> cateList = (JList<String>) e.getSource();
                int index = cateList.locationToIndex(e.getPoint());
                List<String> selectedCatergory = cateList.getSelectedValuesList();
                if (index != -1) {
                    for (String cate : selectedCatergory) {
                        productHandleTable(cate);
                    }
                }
            }
        });
    }

    public List<Table> getTableList() {
        return tableList;
    }

    public Table getOrderTable() {
        return orderTable;
    }

    public void customComponents() {
        MouseClickListener();
        productHandleTable("all");
        handleTablePreview();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menuBar1 = new java.awt.MenuBar();
        menu1 = new java.awt.Menu();
        menu2 = new java.awt.Menu();
        menuBar2 = new java.awt.MenuBar();
        menu3 = new java.awt.Menu();
        menu4 = new java.awt.Menu();
        menuBar3 = new java.awt.MenuBar();
        menu5 = new java.awt.Menu();
        menu6 = new java.awt.Menu();
        menuBar4 = new java.awt.MenuBar();
        menu7 = new java.awt.Menu();
        menu8 = new java.awt.Menu();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        deliIcon2 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        takeIcon = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        catergList = new javax.swing.JList<>();
        allCatePanel = new javax.swing.JPanel();
        allCateLabel = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        prodctTable = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        previewBillTable = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        updateBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        menu1.setLabel("File");
        menuBar1.add(menu1);

        menu2.setLabel("Edit");
        menuBar1.add(menu2);

        menu3.setLabel("File");
        menuBar2.add(menu3);

        menu4.setLabel("Edit");
        menuBar2.add(menu4);

        menu5.setLabel("File");
        menuBar3.add(menu5);

        menu6.setLabel("Edit");
        menuBar3.add(menu6);

        menu7.setLabel("File");
        menuBar4.add(menu7);

        menu8.setLabel("Edit");
        menuBar4.add(menu8);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setBackground(new java.awt.Color(255, 153, 153));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/POS.png"))); // NOI18N

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/off.png"))); // NOI18N
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });

        jPanel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel11MouseClicked(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("Table");

        deliIcon2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Table.png"))); // NOI18N

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(deliIcon2)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel9)))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addComponent(deliIcon2, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addGap(14, 14, 14))
        );

        jPanel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel12MouseClicked(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setText("Take Away");

        takeIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/take.png"))); // NOI18N

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(takeIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(10, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addComponent(takeIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(19, 19, 19))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        catergList.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        catergList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        catergList.setToolTipText("");
        catergList.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        catergList.setFixedCellHeight(25);
        catergList.setSelectionBackground(new java.awt.Color(242, 242, 242));
        catergList.setValueIsAdjusting(true);
        catergList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                catergListMouseClicked(evt);
            }
        });
        catergList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                catergListValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(catergList);

        allCatePanel.setBackground(new java.awt.Color(255, 255, 255));
        allCatePanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        allCatePanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                allCatePanelMouseClicked(evt);
            }
        });

        allCateLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        allCateLabel.setText("All Catergories");
        allCateLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                allCateLabelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout allCatePanelLayout = new javax.swing.GroupLayout(allCatePanel);
        allCatePanel.setLayout(allCatePanelLayout);
        allCatePanelLayout.setHorizontalGroup(
            allCatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(allCateLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        allCatePanelLayout.setVerticalGroup(
            allCatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(allCateLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(allCatePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(allCatePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        prodctTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "", "", "", ""
            }
        ));
        prodctTable.setRowHeight(120);
        prodctTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                prodctTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(prodctTable);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 620, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE)
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        previewBillTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "SR", "Id", "Name", "Price", "Qty", "Amount"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(previewBillTable);
        if (previewBillTable.getColumnModel().getColumnCount() > 0) {
            previewBillTable.getColumnModel().getColumn(0).setMinWidth(40);
            previewBillTable.getColumnModel().getColumn(0).setPreferredWidth(40);
            previewBillTable.getColumnModel().getColumn(0).setMaxWidth(40);
            previewBillTable.getColumnModel().getColumn(1).setMinWidth(0);
            previewBillTable.getColumnModel().getColumn(1).setPreferredWidth(0);
            previewBillTable.getColumnModel().getColumn(1).setMaxWidth(0);
            previewBillTable.getColumnModel().getColumn(2).setResizable(false);
            previewBillTable.getColumnModel().getColumn(3).setMinWidth(60);
            previewBillTable.getColumnModel().getColumn(3).setPreferredWidth(60);
            previewBillTable.getColumnModel().getColumn(3).setMaxWidth(60);
            previewBillTable.getColumnModel().getColumn(4).setMinWidth(40);
            previewBillTable.getColumnModel().getColumn(4).setPreferredWidth(40);
            previewBillTable.getColumnModel().getColumn(4).setMaxWidth(40);
            previewBillTable.getColumnModel().getColumn(5).setMinWidth(60);
            previewBillTable.getColumnModel().getColumn(5).setPreferredWidth(60);
            previewBillTable.getColumnModel().getColumn(5).setMaxWidth(60);
        }

        jButton1.setText("Check out");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Reset");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        updateBtn.setText("Update");
        updateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateBtnActionPerformed(evt);
            }
        });

        deleteBtn.setText("Delete");
        deleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtnActionPerformed(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("SansSerif", 1, 16)); // NOI18N
        jLabel4.setText("Order for:");
        jPanel5.add(jLabel4);

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("SansSerif", 1, 16)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("jLabel6");
        jPanel5.add(jLabel6);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2)
                        .addGap(12, 12, 12)
                        .addComponent(updateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(deleteBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(updateBtn)
                    .addComponent(deleteBtn))
                .addGap(6, 6, 6))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        POSForm posForm = new POSForm();
        posForm.setDefaultCloseOperation(DashBoardForm.DISPOSE_ON_CLOSE);
        this.setVisible(false);
    }//GEN-LAST:event_jLabel2MouseClicked

    private void catergListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_catergListValueChanged


    }//GEN-LAST:event_catergListValueChanged

    private void catergListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_catergListMouseClicked

    }//GEN-LAST:event_catergListMouseClicked

    private void prodctTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_prodctTableMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_prodctTableMouseClicked

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked

    }//GEN-LAST:event_jButton2MouseClicked

    private void allCateLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_allCateLabelMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_allCateLabelMouseClicked

    private void allCatePanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_allCatePanelMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_allCatePanelMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        BillCheckOutForm b = new BillCheckOutForm(this, this.bill);
        b.setLocationRelativeTo(null);
        b.setVisible(true);
        b.setDefaultCloseOperation(b.HIDE_ON_CLOSE);
        b.setResizable(false);
        System.out.println("!!!!!!!!");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        handleBillReset();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void updateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBtnActionPerformed
        if (previewBillTable.getSelectedRowCount() == 1) {
            int id = Integer.parseInt(previewBillTable.getValueAt(previewBillTable.getSelectedRow(), 1).toString());
            String quantityCurr = previewBillTable.getValueAt(previewBillTable.getSelectedRow(), 4).toString();

            String value = JOptionPane.showInputDialog("Quantity", quantityCurr);
            if (value != null) {
                if (validateNatualNumber(value)) {
                    int quantity = Integer.parseInt(value);
                    this.bill.updateByDishId(id, quantity);
                    handleTablePreview();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "You must select an item before performing this action!");
        }


    }//GEN-LAST:event_updateBtnActionPerformed

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed

        if (previewBillTable.getSelectedRowCount() == 1) {
            int id = Integer.parseInt(previewBillTable.getValueAt(previewBillTable.getSelectedRow(), 1).toString());
            this.bill.removeById(id);
            handleTablePreview();
        } else {
            JOptionPane.showMessageDialog(this, "You must select an item before performing this action!");
        }
    }//GEN-LAST:event_deleteBtnActionPerformed

    private void jPanel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel11MouseClicked
        SelectTableForm selectTableForm = new SelectTableForm(this);
        selectTableForm.setLocationRelativeTo(null);
        selectTableForm.setVisible(true);
        selectTableForm.setResizable(false);
        selectTableForm.setDefaultCloseOperation(selectTableForm.HIDE_ON_CLOSE);
    }//GEN-LAST:event_jPanel11MouseClicked

    private void jPanel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel12MouseClicked
       this.orderTable =null;
       handleOrderTable(this.orderTable);
       
    }//GEN-LAST:event_jPanel12MouseClicked
    private static class CustomCellRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            // Thiết lập viền cho JLabel
            label.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));

            // Căn giữa nội dung của JLabel
            label.setHorizontalAlignment(JLabel.CENTER);

            return label;
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(POSForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(POSForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(POSForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(POSForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                new POSForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel allCateLabel;
    private javax.swing.JPanel allCatePanel;
    private javax.swing.JList<String> catergList;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JLabel deliIcon2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private java.awt.Menu menu1;
    private java.awt.Menu menu2;
    private java.awt.Menu menu3;
    private java.awt.Menu menu4;
    private java.awt.Menu menu5;
    private java.awt.Menu menu6;
    private java.awt.Menu menu7;
    private java.awt.Menu menu8;
    private java.awt.MenuBar menuBar1;
    private java.awt.MenuBar menuBar2;
    private java.awt.MenuBar menuBar3;
    private java.awt.MenuBar menuBar4;
    private javax.swing.JTable previewBillTable;
    private javax.swing.JTable prodctTable;
    private javax.swing.JLabel takeIcon;
    private javax.swing.JButton updateBtn;
    // End of variables declaration//GEN-END:variables
}
