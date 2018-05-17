package UI.RedAlliance;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * Acts as a container for a teams UI elements - the Driver Station and Robot
 * connection indicators, voltage indicators, team number fields, and bypassed
 * boxes.
 *
 * Complete System developed by:
 *
 * @author Alex Bassett
 * @author Walton Robotics - Team 2974
 * @author Foundation code developed by Josh, Andrew Lobos, Team 225
 * @author Structural assistance and refactoring by Doug Neumann
 */
public class Red_UI_Block extends javax.swing.JPanel {

    /**
     * Creates a new Red_UI_Block
     */
    public Red_UI_Block() {
        initComponents();
        System.out.println("Red Block Created");
    }

    /**
     * Gets the bypass control checkbox used to disable a robot.
     *
     * @return The bypass checkbox.
     */
    public JCheckBox getBypass() {
        return bypass;
    }

    /**
     * Gets the teams voltage indicator.
     *
     * @return A JLabel acting as the voltage indicator.
     */
    public JTextField getVoltIndic() {
        return voltage;
    }

    /**
     * Gets the team number field used to set the team's team number.
     *
     * @return A text field acting as the team number field.
     */
    public JTextField getTeamIndic() {
        return teamNum;
    }

    /**
     * Gets the Driver Station communication indicator.
     *
     * @return A JLabel acting as the DS communication indicator.
     */
    public JLabel getDS_Com() {
        return redCommStatus1.getDS();
    }

    /**
     * Gets the Robot communication indicator.
     *
     * @return A JLabel acting as the robot communication indicator.
     */
    public JLabel getRob_Com() {
        return redCommStatus1.getRob();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        teamNum = new javax.swing.JTextField();
        voltage = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        bypass = new javax.swing.JCheckBox();
        redCommStatus1 = new UI.RedAlliance.RedCommStatus();

        setBackground(new java.awt.Color(255, 0, 0));

        teamNum.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        teamNum.setText("0001");

        voltage.setEditable(false);
        voltage.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        voltage.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        voltage.setText("12.57");

        jLabel3.setBackground(new java.awt.Color(255, 0, 0));
        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Voltage");

        bypass.setBackground(new java.awt.Color(255, 0, 0));
        bypass.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        bypass.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                    .addComponent(voltage))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(redCommStatus1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(teamNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bypass)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addComponent(voltage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(redCommStatus1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(teamNum, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(bypass, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox bypass;
    private javax.swing.JLabel jLabel3;
    private UI.RedAlliance.RedCommStatus redCommStatus1;
    private javax.swing.JTextField teamNum;
    private javax.swing.JTextField voltage;
    // End of variables declaration//GEN-END:variables
}
