/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package marketplaceusc.dashboardpages;

/**
 *
 * @author shewe
 */
public class Page_TopSelling extends javax.swing.JPanel {

    /**
     * Creates new form Page_TopSelling
     */
    public Page_TopSelling() {
        initComponents();
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
        TopSelling = new javax.swing.JLabel();
        HorizontalGreen = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));

        HeaderBG.setBackground(new java.awt.Color(255, 255, 255));

        TopSelling.setBackground(new java.awt.Color(255, 255, 255));
        TopSelling.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        TopSelling.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TopSelling.setText("TOP SELLING");
        TopSelling.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        TopSelling.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

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
                .addComponent(TopSelling, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(299, 299, 299))
            .addComponent(HorizontalGreen, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        HeaderBGLayout.setVerticalGroup(
            HeaderBGLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, HeaderBGLayout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(TopSelling)
                .addGap(18, 18, 18)
                .addComponent(HorizontalGreen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(HeaderBG, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jScrollPane1)
                .addGap(32, 32, 32))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(HeaderBG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE)
                .addGap(34, 34, 34))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel HeaderBG;
    private javax.swing.JPanel HorizontalGreen;
    private javax.swing.JLabel TopSelling;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}