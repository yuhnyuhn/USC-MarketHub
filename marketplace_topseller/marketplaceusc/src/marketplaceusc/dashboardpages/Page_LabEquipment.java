/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package marketplaceusc.dashboardpages;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import marketplaceusc.Login;
import marketplaceusc.TheModel;

public class Page_LabEquipment extends javax.swing.JPanel {

    /**
     * Creates new form Page_LabEquipment
     */
    public Page_LabEquipment() {
        initComponents();
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
          
          public ArrayList<Page_LabEquipment.Product> BindTable() {
    ArrayList<Page_LabEquipment.Product> list = new ArrayList<Page_LabEquipment.Product>();
    try (Connection con = getConnection();
         Statement st = con.createStatement();
         ResultSet rs = st.executeQuery("SELECT title, description, price, category, itemcondition, reflink, picture, infodate, schoolemail FROM activeposts WHERE category='Lab Equipments/Wear'")) {

        Page_LabEquipment.Product p;
        while (rs.next()) {
            p = new Page_LabEquipment.Product(
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

        // Assign values to the rows arra
        rows[i][0] = resizedImageIcon; // ImageIcon for the picture column*
        rows[i][1] = date;             // Date for the Date column
        rows[i][2] = schoolEmail;      // School Email for the School Email column
        rows[i][3] = title;            // Title for the Title column
        rows[i][4] = category;         // Category for the Category column

    }
}

        TheModel model1 = new TheModel(rows, columnName);
        labequipmenttable.setModel(model1);
        labequipmenttable.setRowHeight(100);
        

        labequipmenttable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = labequipmenttable.getSelectedRow();

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

        HeaderBG = new javax.swing.JPanel();
        LabEquipment = new javax.swing.JLabel();
        HorizontalGreen = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        labequipmenttable = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));

        HeaderBG.setBackground(new java.awt.Color(255, 255, 255));

        LabEquipment.setBackground(new java.awt.Color(255, 255, 255));
        LabEquipment.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        LabEquipment.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LabEquipment.setText("LAB EQUIPMENT");
        LabEquipment.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        LabEquipment.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        HorizontalGreen.setBackground(new java.awt.Color(73, 95, 65));

        javax.swing.GroupLayout HorizontalGreenLayout = new javax.swing.GroupLayout(HorizontalGreen);
        HorizontalGreen.setLayout(HorizontalGreenLayout);
        HorizontalGreenLayout.setHorizontalGroup(
            HorizontalGreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        HorizontalGreenLayout.setVerticalGroup(
            HorizontalGreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout HeaderBGLayout = new javax.swing.GroupLayout(HeaderBG);
        HeaderBG.setLayout(HeaderBGLayout);
        HeaderBGLayout.setHorizontalGroup(
            HeaderBGLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HeaderBGLayout.createSequentialGroup()
                .addGap(269, 269, 269)
                .addComponent(LabEquipment, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(299, 299, 299))
            .addComponent(HorizontalGreen, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        HeaderBGLayout.setVerticalGroup(
            HeaderBGLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, HeaderBGLayout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(LabEquipment)
                .addGap(18, 18, 18)
                .addComponent(HorizontalGreen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jScrollPane2.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        labequipmenttable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        labequipmenttable.setGridColor(new java.awt.Color(255, 255, 255));
        labequipmenttable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labequipmenttableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(labequipmenttable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(HeaderBG, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jScrollPane2)
                .addGap(32, 32, 32))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(HeaderBG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE)
                .addGap(34, 34, 34))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void labequipmenttableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labequipmenttableMouseClicked
        
    }//GEN-LAST:event_labequipmenttableMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel HeaderBG;
    private javax.swing.JPanel HorizontalGreen;
    private javax.swing.JLabel LabEquipment;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable labequipmenttable;
    // End of variables declaration//GEN-END:variables
}
