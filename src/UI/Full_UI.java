package UI;

/**
 * A representation of the Full UI. This includes features such as Viewmarq
 * control and general field hardware.
 *
 * Complete System developed by:
 *
 * @author Alex Bassett
 * @author Walton Robotics - Team 2974
 * @author Foundation code developed by Josh, Andrew Lobos, Team 225
 * @author Structural assistance and refactoring by Doug Neumann
 */
public class Full_UI extends javax.swing.JPanel {

    /**
     * Creates new form Full_UI
     */
    public Full_UI() {
        initComponents();
    }

    /**
     * Gets the standard UI showing robots and general connection.
     *
     * @return The "new UI"
     */
    public New_UI getNew_UI() {
        return new_UI1;
    }

    /**
     * Get the match control pane full of buttons.
     *
     * @return The current "Match Control UI"
     */
    public Match_Control_UI getMatchControl() {
        return match_Control_UI1;
    }

    /**
     * Gets the ESTOP panel.
     *
     * @return The ESTOP UI panel
     */
    public ESTOP_Panel getESTOP_Panel() {
        return eSTOP_Panel1;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        new_UI1 = new UI.New_UI();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        match_Control_UI1 = new UI.Match_Control_UI();
        aD_Test_Panel1 = new UI.AD_Test_Panel();
        eSTOP_Panel1 = new UI.ESTOP_Panel();
        aD_Custom_Vm1 = new UI.AD_Custom_Vm();
        aD_Custom_Vm_Editor1 = new UI.AD_Custom_Vm_Editor();

        setBackground(new java.awt.Color(0, 0, 0));

        jTabbedPane1.setMaximumSize(new java.awt.Dimension(794, 326));
        jTabbedPane1.addTab("Match Config", match_Control_UI1);

        aD_Test_Panel1.setBackground(new java.awt.Color(0, 0, 0));
        jTabbedPane1.addTab("Field Hardware", aD_Test_Panel1);
        jTabbedPane1.addTab("ESTOP_Panel", eSTOP_Panel1);

        aD_Custom_Vm1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                aD_Custom_Vm1FocusGained(evt);
            }
        });
        jTabbedPane1.addTab("Send VM Message", aD_Custom_Vm1);

        aD_Custom_Vm_Editor1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                aD_Custom_Vm_Editor1FocusGained(evt);
            }
        });
        jTabbedPane1.addTab("Message Editor", aD_Custom_Vm_Editor1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(new_UI1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 785, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(new_UI1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("TabbedPane");
    }// </editor-fold>//GEN-END:initComponents

    private void aD_Custom_Vm_Editor1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_aD_Custom_Vm_Editor1FocusGained
        System.out.println("Editor focus Gained...");
    }//GEN-LAST:event_aD_Custom_Vm_Editor1FocusGained

    private void aD_Custom_Vm1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_aD_Custom_Vm1FocusGained
        System.out.println("Message focus gained...");
    }//GEN-LAST:event_aD_Custom_Vm1FocusGained

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private UI.AD_Custom_Vm aD_Custom_Vm1;
    private UI.AD_Custom_Vm_Editor aD_Custom_Vm_Editor1;
    private UI.AD_Test_Panel aD_Test_Panel1;
    private UI.ESTOP_Panel eSTOP_Panel1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private UI.Match_Control_UI match_Control_UI1;
    private UI.New_UI new_UI1;
    // End of variables declaration//GEN-END:variables
}
