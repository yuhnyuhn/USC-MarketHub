/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
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
import java.util.Collections;
import javax.swing.ImageIcon;
import javax.swing.Icon;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import marketplaceusc.Homepage;
import marketplaceusc.Login;
import marketplaceusc.TheModel;
import marketplaceusc.Model_Menu;



public class ItemSelling extends javax.swing.JFrame {
    
    private static String schoolemail;
    
    public ItemSelling(String schoolEmail) {
        
        initComponents();
        this.schoolemail = schoolEmail;
        populateJTable();
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
    public byte[] getPicture(){
        return picture;
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
    public void setSchoolemail(String schoolEmail){
        schoolemail= schoolEmail;
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
   
          
          public ArrayList<ItemSelling.Product> BindTable() throws SQLException {
            ArrayList<ItemSelling.Product> list = new ArrayList<>();
            try (Connection con = getConnection()){
                String query = "SELECT title, description, price, category, itemcondition, reflink, picture, infodate, schoolemail FROM activeposts WHERE schoolemail= ?";
                try (PreparedStatement pstmt = con.prepareStatement(query)) {
                    pstmt.setString(1, schoolemail);
                    ResultSet rs = pstmt.executeQuery();
            
                ItemSelling.Product p;
                while (rs.next()) {
                    p = new ItemSelling.Product(
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
    }

    
    public void populateJTable() {
        try {
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
        itemsellingtable.setModel(model);
        itemsellingtable.setRowHeight(100);
        

        itemsellingtable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = itemsellingtable.getSelectedRow();

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

            new ItemsSellingPopUp(title,description,price, category, itemCondition, refLink,
                    imageIcon,date,schoolEmail,mobilenumber).setVisible(true);
            
       
        }
    }
            });     } catch (SQLException ex) {
            Logger.getLogger(ItemSelling.class.getName()).log(Level.SEVERE, null, ex);
        }this.setVisible(false);
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
        HeaderBG10 = new javax.swing.JPanel();
        ItemSelling = new javax.swing.JLabel();
        HorizontalGreen10 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        itemsellingtable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setLocation(new java.awt.Point(300, 50));
        setMaximumSize(new java.awt.Dimension(950, 650));
        setMinimumSize(new java.awt.Dimension(950, 650));
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(950, 650));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        HeaderBG10.setBackground(new java.awt.Color(255, 255, 255));

        ItemSelling.setBackground(new java.awt.Color(255, 255, 255));
        ItemSelling.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        ItemSelling.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ItemSelling.setText("ITEM SELLING");
        ItemSelling.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        ItemSelling.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        HorizontalGreen10.setBackground(new java.awt.Color(73, 95, 65));

        javax.swing.GroupLayout HorizontalGreen10Layout = new javax.swing.GroupLayout(HorizontalGreen10);
        HorizontalGreen10.setLayout(HorizontalGreen10Layout);
        HorizontalGreen10Layout.setHorizontalGroup(
            HorizontalGreen10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        HorizontalGreen10Layout.setVerticalGroup(
            HorizontalGreen10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Verdana", 0, 24)); // NOI18N
        jLabel1.setText("<");
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout HeaderBG10Layout = new javax.swing.GroupLayout(HeaderBG10);
        HeaderBG10.setLayout(HeaderBG10Layout);
        HeaderBG10Layout.setHorizontalGroup(
            HeaderBG10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HeaderBG10Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel1)
                .addGap(216, 216, 216)
                .addComponent(ItemSelling, javax.swing.GroupLayout.DEFAULT_SIZE, 419, Short.MAX_VALUE)
                .addGap(299, 299, 299))
            .addComponent(HorizontalGreen10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        HeaderBG10Layout.setVerticalGroup(
            HeaderBG10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, HeaderBG10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(HeaderBG10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(HeaderBG10Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(ItemSelling))
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(HorizontalGreen10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jScrollPane1MouseClicked(evt);
            }
        });

        itemsellingtable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(itemsellingtable);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(HeaderBG10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jScrollPane1)
                .addGap(32, 32, 32))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(HeaderBG10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1)
                .addGap(34, 34, 34))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jScrollPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane1MouseClicked
   
    }//GEN-LAST:event_jScrollPane1MouseClicked

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked

        this.setVisible(false);
    }//GEN-LAST:event_jLabel1MouseClicked

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
            java.util.logging.Logger.getLogger(ItemSelling.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ItemSelling.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ItemSelling.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ItemSelling.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ItemSelling(schoolemail).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel HeaderBG10;
    private javax.swing.JPanel HorizontalGreen10;
    private javax.swing.JLabel ItemSelling;
    private javax.swing.JTable itemsellingtable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
    }


