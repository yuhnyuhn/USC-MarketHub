/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package marketplaceusc.dashboardpages;

import java.awt.Image;
import javax.swing.ImageIcon; 

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.BorderLayout;
import java.sql.PreparedStatement;
import javax.swing.ImageIcon;
import javax.swing.Icon;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Component;
import java.awt.Image;
import java.util.Collections;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import marketplaceusc.Login;
import marketplaceusc.TheModel;

public class Page_Home extends javax.swing.JPanel {
    /**
     * Creates new form Page_Home
     */
    public Page_Home() {
        initComponents();
        populateJTable();
    }
    
    public class TheModel extends DefaultTableModel {
    public TheModel(Object[][] data, Object[] columnNames) {
        super(data, columnNames);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        // Assuming the image is in the first column
        if (columnIndex == 0) {
            return ImageIcon.class;
        }
        // Add more conditions for other column classes if needed
        return super.getColumnClass(columnIndex);
    }

    // You can override other methods if necessary
    // For example, if you want to make certain cells non-editable:
    @Override
    public boolean isCellEditable(int row, int column) {
        // Make all cells non-editable
        return false;
    }
}
    
      public class ImageRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel label = new JLabel();
            label.setIcon((ImageIcon) value);
            return label;
        }
    }
    
    public class Product {
        
    private String schoolemail;
    private String title;
    private String description;
    private String price;
    private String category;
    private String itemcondition;
    private String reflink;
    private byte[] picture;
    private Date infodate;
    MyQuery q = new MyQuery();
    

    // Constructor
    public Product(String title, String description, String price, String category,
            String itemcondition, String reflink, byte[] picture, Date infodate, String schoolemail){
        this.title = title;
        this.description = description;
        this.price = price;
        this.category = category;
        this.itemcondition = itemcondition;
        this.reflink = reflink;
        this.picture = picture;
        this.infodate = infodate;
        this.schoolemail = schoolemail;
    }
        public byte[] getPicture(){
        return picture;
    }
        
        public void setPicture(byte[] picture) {
         this.picture = picture;
        }
        
    
    public String getMobileNumber(){
        
        return q.getMobileNumber(schoolemail);
    }

    public String getPrice(){
        return price;
    }
        
    public void setPrice(String price){
        this.price = price;
    }
    
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public String getCategory(){
        return category;
    }
    public void setCategory(String category){
        this.category = category;
    }
    public String getItemcondition(){
        return itemcondition;
    }
    public void setItemcondition(String itemcondition){
        this.itemcondition = itemcondition;
    }
    public String getReflink(){
        return reflink;
    }
    public void setReflink(String reflink){
        this.reflink = reflink;
    }

    public Date getInfodate(){
        return infodate;
    }
    public void setInfodate(Date infodate){
        this.infodate = infodate;
    }
    public String getSchoolemail(){
        return schoolemail;
    }
    public void setSchoolemail(String schoolemail){
        this.schoolemail= schoolemail;
    }
}
    
public class MyQuery {

        public Connection getConnection() {
           Connection con = null;
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost/users", "root", "");
            } catch (SQLException ex) {
                Logger.getLogger(MyQuery.class.getName()).log(Level.SEVERE, null, ex);
            }
            return con;
        }
          private String getMobileNumber(String email) {
        // Add your code to fetch the mobile number from the database based on the email
        try (Connection con = getConnection()){
            String query = "SELECT mobilenumber FROM userdetails WHERE schoolemail = ?";
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setString(1, email);
                ResultSet result = pstmt.executeQuery();
                if (result.next()) {
                    return result.getString("mobilenumber");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null; // Return null if mobile number is not found
    }
        
   public ArrayList<Product> BindTable() {
    ArrayList<Product> list = new ArrayList<Product>();
    try (Connection con = getConnection();
         Statement st = con.createStatement();
         ResultSet rs = st.executeQuery("SELECT title, description, price, category, itemcondition, reflink, picture, infodate, schoolemail FROM activeposts")) {

        Product p;
        while (rs.next()) {
            p = new Product(
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getString("price"),
                    rs.getString("category"),
                    rs.getString("itemcondition"),
                    rs.getString("reflink"),
                    rs.getBytes("picture"),
                    rs.getDate("infodate"),
                    rs.getString("schoolemail")
            );
            list.add(p);
        }
                    Collections.sort(list, (p1, p2) -> p2.getInfodate().compareTo(p1.getInfodate()));

    } catch (SQLException ex) {
        Logger.getLogger(MyQuery.class.getName()).log(Level.SEVERE, null, ex);
    }
    return list;
}
}
 public void populateJTable() {
        MyQuery mq = new MyQuery();
        ArrayList<Product> list = mq.BindTable();
        String[] columnName = {"Picture","Date", "School Email", "Title", "Category"};
        Object[][] rows = new Object[list.size()][5];

for (int i = 0; i < list.size(); i++) {
    // Extract data from the Product object
    byte[] imageData = list.get(i).getPicture();
    Date date = list.get(i).getInfodate();
    String schoolEmail = list.get(i).getSchoolemail();
    String title = list.get(i).getTitle();
    String category = list.get(i).getCategory();

    // Check if imageData is not null before creating ImageIcon
    ImageIcon imageIcon = (imageData != null) ? new ImageIcon(imageData) : null;

    // Resize the image if needed
    if (imageIcon != null) {
        Image image = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);

        // Create a new ImageIcon with the resized image
        ImageIcon resizedImageIcon = new ImageIcon(image);

        // Assign values to the rows array
        rows[i][0] = resizedImageIcon; // ImageIcon for the picture column
        rows[i][1] = date;             // Date for the Date column
        rows[i][2] = schoolEmail;      // School Email for the School Email column
        rows[i][3] = title;            // Title for the Title column
        rows[i][4] = category;         // Category for the Category column
    }
}

        TheModel model = new TheModel(rows, columnName);
        homepagetable.setModel(model);
        homepagetable.setRowHeight(100);
        

        homepagetable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = homepagetable.getSelectedRow();

             if (selectedRow >= 0) {
            // Retrieve detailed information from the selected row
                String title = list.get(selectedRow).getTitle();
                String description = list.get(selectedRow).getDescription();
                String price = list.get(selectedRow).getPrice();
                String category = list.get(selectedRow).getCategory();
                String itemCondition = list.get(selectedRow).getItemcondition();
                String refLink = list.get(selectedRow).getReflink();
                byte[] imageBytes = list.get(selectedRow).getPicture();
                ImageIcon imageIcon = (imageBytes != null) ? new ImageIcon(imageBytes) : null;
                Image image = imageIcon.getImage();
                Image adjimg = image.getScaledInstance(400, 400, java.awt.Image.SCALE_SMOOTH);
                imageIcon = new ImageIcon(adjimg);
                Date date = list.get(selectedRow).getInfodate();
                String schoolEmail = list.get(selectedRow).getSchoolemail();
                String mobilenumber = list.get(selectedRow).getMobileNumber();


            new CategoryPopUp(title,description,price, category, itemCondition, refLink,
                    imageIcon,date,schoolEmail,mobilenumber).setVisible(true);
        }
    }
});
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        homepagetable = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(803, 532));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        homepagetable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        homepagetable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                homepagetableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(homepagetable);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 521, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("HOME PAGE");
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel1.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        jPanel3.setBackground(new java.awt.Color(73, 95, 65));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(269, 269, 269)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                .addGap(299, 299, 299))
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(57, 57, 57))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void homepagetableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homepagetableMouseClicked
      
    }//GEN-LAST:event_homepagetableMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable homepagetable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
