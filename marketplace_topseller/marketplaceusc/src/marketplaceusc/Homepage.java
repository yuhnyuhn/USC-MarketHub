
package marketplaceusc;

import marketplaceusc.dashboardpages.Page_LabEquipment;
import marketplaceusc.dashboardpages.Page_Uniforms;
import marketplaceusc.dashboardpages.Page_SchoolBooks;
import marketplaceusc.dashboardpages.Page_Others;
import marketplaceusc.dashboardpages.Page_TopSellers;
import marketplaceusc.dashboardpages.Page_TopSelling;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import marketplaceusc.dashboardpages.Page_Home;
import marketplaceusc.dashboardpages.Page_Profile;


public class Homepage extends javax.swing.JFrame {

    private Page_Home home;
    private Page_Profile profile;
    private Page_TopSelling topselling;
    private Page_TopSellers topsellers;
    private Page_Uniforms uniforms;
    private Page_SchoolBooks schoolbooks;
    private Page_LabEquipment labequipment;
    private Page_Others others;
    private String schoolemail;
    
    
    public Homepage(int index, String schoolemail){
        initComponents();
        home = new Page_Home();
        profile = new Page_Profile(schoolemail);
        topselling = new Page_TopSelling();
        topsellers = new Page_TopSellers();
        uniforms = new Page_Uniforms();
        schoolbooks = new Page_SchoolBooks();
        labequipment = new Page_LabEquipment();
        others = new Page_Others();
        this.schoolemail = schoolemail;
        
        menu2.addEventMenuSelected(new EventMenuSelected() {
            public void selected(int index){
                if (index== 0){
                    setForm(home);
                }else if (index== 1){
                    setForm(profile);
                }else if(index== 4){
                    setForm(topselling);
                }else if(index== 5){
                    setForm(topsellers);
                }else if (index== 6){
                    setForm(uniforms);
                }else if(index== 7){
                    setForm(schoolbooks);
                }else if(index== 8){
                    setForm(labequipment);
                }else if(index== 9){
                    setForm(others);
                }
                
            } 
        });
        setForm(home);
    }
 
    private EventMenuSelected event;
    
    public void addEventMenuSelected(EventMenuSelected event){
        this.event = event;
    }
    
    private void setForm(JComponent com){
        mainpanel.removeAll();
        mainpanel.add(com);
        mainpanel.repaint();
        mainpanel.revalidate();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        backgroundpanel = new javax.swing.JPanel();
        menu = new javax.swing.JPanel();
        logoutdashboard = new javax.swing.JLabel();
        menu2 = new marketplaceusc.Menu();
        mainpanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        backgroundpanel.setBackground(new java.awt.Color(255, 255, 255));

        menu.setBackground(new java.awt.Color(73, 95, 65));

        logoutdashboard.setBackground(new java.awt.Color(73, 95, 65));
        logoutdashboard.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        logoutdashboard.setForeground(new java.awt.Color(255, 255, 255));
        logoutdashboard.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        logoutdashboard.setText("Log Out");
        logoutdashboard.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        logoutdashboard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logoutdashboardMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout menuLayout = new javax.swing.GroupLayout(menu);
        menu.setLayout(menuLayout);
        menuLayout.setHorizontalGroup(
            menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuLayout.createSequentialGroup()
                .addComponent(menu2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(menuLayout.createSequentialGroup()
                    .addComponent(logoutdashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 23, Short.MAX_VALUE)))
        );
        menuLayout.setVerticalGroup(
            menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(menu2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(menuLayout.createSequentialGroup()
                    .addGap(675, 675, 675)
                    .addComponent(logoutdashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(17, Short.MAX_VALUE)))
        );

        mainpanel.setBackground(new java.awt.Color(255, 255, 255));
        mainpanel.setMinimumSize(new java.awt.Dimension(1200, 500));
        mainpanel.setPreferredSize(new java.awt.Dimension(1200, 500));
        mainpanel.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout backgroundpanelLayout = new javax.swing.GroupLayout(backgroundpanel);
        backgroundpanel.setLayout(backgroundpanelLayout);
        backgroundpanelLayout.setHorizontalGroup(
            backgroundpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundpanelLayout.createSequentialGroup()
                .addComponent(menu, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(mainpanel, javax.swing.GroupLayout.PREFERRED_SIZE, 1171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 35, Short.MAX_VALUE))
        );
        backgroundpanelLayout.setVerticalGroup(
            backgroundpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(mainpanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(backgroundpanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(backgroundpanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void logoutdashboardMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutdashboardMouseClicked
        new Login().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_logoutdashboardMouseClicked

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
            java.util.logging.Logger.getLogger(Homepage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Homepage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Homepage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Homepage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Homepage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel backgroundpanel;
    private javax.swing.JLabel logoutdashboard;
    private javax.swing.JPanel mainpanel;
    private javax.swing.JPanel menu;
    private marketplaceusc.Menu menu2;
    // End of variables declaration//GEN-END:variables

    private Homepage() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
