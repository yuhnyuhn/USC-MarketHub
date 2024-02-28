
package marketplaceusc;

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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.util.Collections;
import javax.swing.ImageIcon;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Admin extends javax.swing.JFrame {

    ImageIcon admin_icon = new ImageIcon("src\\marketplace\\icons\\profile_dark.png");
    ImageIcon logout_icon = new ImageIcon("src\\marketplace\\icons\\logouticon.png");
    
    public Admin() {
        initComponents();
        populateJTable();
        populateDeletedJTable();
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
    MyQuery q = new MyQuery();
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

private void movePostToDeletedPosts(String title, String schoolEmail) {
    try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost/users", "root", "");
         Statement st = con.createStatement()) {
        
        ResultSet countResult = st.executeQuery("SELECT COUNT(*) AS count FROM deletedposts");
        countResult.next();
        int rowCount = countResult.getInt("count");

        // If the number of rows exceeds 20, delete the oldest entry
        if (rowCount >= 20) {
            for (int i = rowCount; i!=20; i--) {
            String deleteOldestQuery = ("DELETE FROM deletedposts WHERE infodate = (SELECT MIN(infodate) FROM deletedposts) LIMIT 1");
            st.executeUpdate(deleteOldestQuery);
            }
        }
        
        // Insert the post into the deletedposts table
         String moveQuery = ("INSERT INTO deletedposts SELECT * FROM activeposts WHERE title = '"+title+"' AND schoolemail = '"+schoolEmail+"'");
        try (PreparedStatement moveStatement = con.prepareStatement(moveQuery)) {
            moveStatement.executeUpdate();
        }

        // Delete the post from the activeposts table
        String deleteQuery = ("DELETE FROM activeposts WHERE title = '"+title+"' AND schoolemail = '"+schoolEmail+"' ORDER BY infodate ASC LIMIT 1");
        try (PreparedStatement deleteStatement = con.prepareStatement(deleteQuery)) {
    deleteStatement.executeUpdate();
}
    } catch (SQLException ex) {
        Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public ArrayList<Product> BindDeletedPostsTable() {
    ArrayList<Product> list = new ArrayList<Product>();
    try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost/users", "root", "");
         Statement st = con.createStatement();
         ResultSet rs = st.executeQuery("SELECT title, "
                 + "description, price, category, itemcondition, "
                 + "reflink, picture, infodate, schoolemail FROM "
                 + "deletedposts")) {

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
            ResultSet rs = st.executeQuery("SELECT title, description, price, category, itemcondition, "
                    + "reflink, picture, infodate, schoolemail FROM activeposts")) {
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

boolean isDeletedTableMouseListenerAdded = false;

public void populateDeletedJTable() {
    
    
        MyQuery mq = new MyQuery();
     ArrayList<Product> deletedPostsList = mq.BindDeletedPostsTable();
    String[] deletedPostsColumnName = {"Date", "School Email", "Title", "Category"};
    Object[][] deletedPostsRows = new Object[deletedPostsList.size()][4];
        System.out.print("d"+deletedPostsList.size());


    for (int i = 0; i < deletedPostsList.size(); i++) {
        deletedPostsRows[i][0] = deletedPostsList.get(i).getInfodate();
        deletedPostsRows[i][1] = deletedPostsList.get(i).getSchoolemail();
        deletedPostsRows[i][2] = deletedPostsList.get(i).getTitle();
        deletedPostsRows[i][3] = deletedPostsList.get(i).getCategory();
    }
    
    TheModel deletedPostsModel = new TheModel(deletedPostsRows, deletedPostsColumnName);
    deletedpoststable.setModel(deletedPostsModel);
    deletedpoststable.setRowHeight(50);

    if(!isDeletedTableMouseListenerAdded){
    deletedpoststable.addMouseListener(new MouseAdapter() {
    @Override
    public void mouseClicked(MouseEvent e){
        int selectedRow = deletedpoststable.getSelectedRow();

        if (selectedRow >= 0) {
           
            // Retrieve detailed information from the selected row
            JFrame detailFrame = new JFrame("Product Details");

            String title = deletedPostsList.get(selectedRow).getTitle();
            String description = deletedPostsList.get(selectedRow).getDescription();
            String price = deletedPostsList.get(selectedRow).getPrice();
            String category = deletedPostsList.get(selectedRow).getCategory();
            String itemCondition = deletedPostsList.get(selectedRow).getItemcondition();
            String refLink = deletedPostsList.get(selectedRow).getReflink();
            byte[] imageBytes = deletedPostsList.get(selectedRow).getPicture();
            ImageIcon imageIcon = (imageBytes != null) ? new ImageIcon(imageBytes) : null;
            Image image = imageIcon.getImage();
            Image adjimg = image.getScaledInstance(400, 400, java.awt.Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(adjimg);

            Date date = deletedPostsList.get(selectedRow).getInfodate();
            String schoolEmail = deletedPostsList.get(selectedRow).getSchoolemail();

            //Create a JPanel for displaying detailed information
            JPanel mainPanel = new JPanel(new BorderLayout());
            JPanel details = new JPanel();

            // Add image to the panel if available
            if (imageIcon != null) {
                JLabel imageLabel = new JLabel(imageIcon);
                details.add(imageLabel, BorderLayout.CENTER);
            }

            // Add other details to the panel
            JLabel infoLabel = new JLabel("<html>Title: " + title + "<br>Description: " + description +
                    "<br>Price: " + price + "<br>Category: " + category +
                    "<br>Condition: " + itemCondition + "<br>Reference Link: " + refLink +
                    "<br>Date: " + date + "<br>School Email: " + schoolEmail + "</html>");
            details.add(infoLabel, BorderLayout.SOUTH);

            detailFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            detailFrame.getContentPane().add(details);
            detailFrame.pack();
            detailFrame.setLocationRelativeTo(null);
            detailFrame.setVisible(true);
        }
    }
});
isDeletedTableMouseListenerAdded = true;
}
}

boolean isMouseListenerAdded = false;

public void populateJTable() {
    
    
    
    MyQuery mq = new MyQuery();
    ArrayList<Product> activelist = mq.BindTable();
    String[] columnName = {"Date", "School Email", "Title", "Category"};
    Object[][] rows = new Object[activelist.size()][4];
    System.out.print("a"+activelist.size());

    for (int i = 0; i < activelist.size(); i++) {
        rows[i][0] = activelist.get(i).getInfodate();
        rows[i][1] = activelist.get(i).getSchoolemail();
        rows[i][2] = activelist.get(i).getTitle();
        rows[i][3] = activelist.get(i).getCategory();
    }

    TheModel model = new TheModel(rows, columnName);
    activepoststable.setModel(model);
    activepoststable.setRowHeight(50);
        
   
    
        if (!isMouseListenerAdded) {
        activepoststable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = activepoststable.getSelectedRow();

                if (selectedRow >= 0) {
                    JFrame detailFrame = new JFrame("Product Details");
                    // Retrieve detailed information from the selected row
                    String title = activelist.get(selectedRow).getTitle();
                    String description = activelist.get(selectedRow).getDescription();
                    String price = activelist.get(selectedRow).getPrice();
                    String category = activelist.get(selectedRow).getCategory();
                    String itemCondition = activelist.get(selectedRow).getItemcondition();
                    String refLink = activelist.get(selectedRow).getReflink();
                    byte[] imageBytes = activelist.get(selectedRow).getPicture();
                    ImageIcon imageIcon = (imageBytes != null) ? new ImageIcon(imageBytes) : null;
                    Image image = imageIcon.getImage();
                    Image adjimg = image.getScaledInstance(400, 400, java.awt.Image.SCALE_SMOOTH);
                    imageIcon = new ImageIcon(adjimg);
                    Date date = activelist.get(selectedRow).getInfodate();
                    String schoolEmail = activelist.get(selectedRow).getSchoolemail();
                    String mobilenumber = activelist.get(selectedRow).getMobileNumber();

                    // Create a JPanel for displaying detailed information
                    JPanel mainPanel = new JPanel(new BorderLayout());

                    JPanel details = new JPanel();

                    // Add image to the panel if available
                    if (imageIcon != null) {
                        JLabel imageLabel = new JLabel(imageIcon);
                        details.add(imageLabel, BorderLayout.CENTER);
                    }

                    // Add other details to the panel
                    JLabel infoLabel = new JLabel("<html>Title: " + title + "<br>Description: " + description +
                            "<br>Price: " + price + "<br>Category: " + category +
                            "<br>Condition: " + itemCondition + "<br>Date: " + date + "<br>Contact Details:" +
                            "<br>Reference Link:<a href= refLink>" + refLink + "</a>" +
                            "<br>School Email: " + schoolEmail + "<br>Mobile Number: " + mobilenumber + "</html>");
                    details.add(infoLabel, BorderLayout.SOUTH);

                    JButton deletebutton = new JButton("Delete");
                    details.add(deletebutton);

                    deletebutton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            int selectedRow = activepoststable.getSelectedRow();

                            if (selectedRow >= 0) {
                                String title = activelist.get(selectedRow).getTitle();
                                String schoolEmail = activelist.get(selectedRow).getSchoolemail();

                                movePostToDeletedPosts(title, schoolEmail);
                                SwingUtilities.invokeLater(() -> {
                                    populateJTable();
                                    populateDeletedJTable();
                                });
                            }
                            detailFrame.dispose();
                        }
                    });

                    // Create and display the frame for detailed information
                    detailFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    detailFrame.getContentPane().add(details);
                    detailFrame.pack();
                    detailFrame.setLocationRelativeTo(null);
                    detailFrame.setVisible(true);
                }
            }
        });
        isMouseListenerAdded = true;
        //==========================
    }
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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel(logout_icon);
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        activepoststab = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        activepoststable = new javax.swing.JTable();
        deletedpoststab = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        deletedpoststable = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel(admin_icon);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        jLabel1.setText("ADMIN");

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(73, 95, 65));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jTabbedPane1.setBackground(new java.awt.Color(249, 247, 243));
        jTabbedPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jTabbedPane1.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
        });

        activepoststab.setBackground(new java.awt.Color(249, 247, 243));
        activepoststab.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        activepoststable.setBackground(new java.awt.Color(249, 247, 243));
        activepoststable.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        activepoststable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        activepoststable.setAutoscrolls(false);
        activepoststable.setGridColor(new java.awt.Color(255, 255, 255));
        activepoststable.setRowHeight(5);
        activepoststable.setRowMargin(2);
        activepoststable.setSelectionBackground(new java.awt.Color(51, 102, 0));
        activepoststable.setSelectionForeground(new java.awt.Color(255, 255, 255));
        activepoststable.setShowGrid(true);
        activepoststable.setShowVerticalLines(false);
        activepoststable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                activepoststableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(activepoststable);

        javax.swing.GroupLayout activepoststabLayout = new javax.swing.GroupLayout(activepoststab);
        activepoststab.setLayout(activepoststabLayout);
        activepoststabLayout.setHorizontalGroup(
            activepoststabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 788, Short.MAX_VALUE)
        );
        activepoststabLayout.setVerticalGroup(
            activepoststabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 524, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Posts", activepoststab);

        deletedpoststab.setBackground(new java.awt.Color(249, 247, 243));
        deletedpoststab.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane3.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        deletedpoststable.setBackground(new java.awt.Color(249, 247, 243));
        deletedpoststable.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        deletedpoststable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        deletedpoststable.setAutoscrolls(false);
        deletedpoststable.setGridColor(new java.awt.Color(255, 255, 255));
        deletedpoststable.setRowHeight(5);
        deletedpoststable.setRowMargin(2);
        deletedpoststable.setSelectionBackground(new java.awt.Color(51, 102, 0));
        deletedpoststable.setSelectionForeground(new java.awt.Color(255, 255, 255));
        deletedpoststable.setShowGrid(true);
        deletedpoststable.setShowVerticalLines(false);
        deletedpoststable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deletedpoststableMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(deletedpoststable);

        javax.swing.GroupLayout deletedpoststabLayout = new javax.swing.GroupLayout(deletedpoststab);
        deletedpoststab.setLayout(deletedpoststabLayout);
        deletedpoststabLayout.setHorizontalGroup(
            deletedpoststabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 788, Short.MAX_VALUE)
        );
        deletedpoststabLayout.setVerticalGroup(
            deletedpoststabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 524, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Deleted Posts", deletedpoststab);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(jTabbedPane1)
                .addGap(57, 57, 57))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jTabbedPane1)
                .addGap(40, 40, 40))
        );

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jLabel1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        new Login().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jLabel2MouseClicked

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked
        jTabbedPane1.setBackgroundAt(1, new java.awt.Color(249,247,243));
    }//GEN-LAST:event_jTabbedPane1MouseClicked

            
    private void activepoststableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_activepoststableMouseClicked
               
 
    }//GEN-LAST:event_activepoststableMouseClicked

    private void deletedpoststableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deletedpoststableMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_deletedpoststableMouseClicked

    
    
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
            java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Admin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel activepoststab;
    private javax.swing.JTable activepoststable;
    private javax.swing.JPanel deletedpoststab;
    private javax.swing.JTable deletedpoststable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}
