package UI;

import javax.swing.JButton;
import javax.swing.JTextField;

/**
 * This represents the buttons used to control the match, hence "match control".
 *
 * Complete System developed by:
 *
 * @author Alex Bassett
 * @author Walton Robotics - Team 2974
 * @author Foundation code developed by Josh, Andrew Lobos, Team 225
 * @author Structural assistance and refactoring by Doug Neumann
 */
public class Match_Control_UI extends javax.swing.JPanel {

    /**
     * Creates a new Match_Control_UI
     */
    public Match_Control_UI() {
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

        beginMatchButton = new javax.swing.JButton();
        stopMatchButton = new javax.swing.JButton();
        resetButton = new javax.swing.JButton();
        switchButton = new javax.swing.JButton();
        matchTimeBox1 = new UI.MatchTimeBox();

        setBackground(new java.awt.Color(0, 0, 0));
        setMaximumSize(new java.awt.Dimension(775, 80));
        setMinimumSize(new java.awt.Dimension(775, 80));
        setPreferredSize(new java.awt.Dimension(775, 80));

        beginMatchButton.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        beginMatchButton.setText("Begin Match");
        beginMatchButton.setEnabled(false);
        beginMatchButton.setMaximumSize(new java.awt.Dimension(145, 30));
        beginMatchButton.setMinimumSize(new java.awt.Dimension(145, 30));
        beginMatchButton.setPreferredSize(new java.awt.Dimension(145, 30));

        stopMatchButton.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        stopMatchButton.setText("Stop Match");
        stopMatchButton.setEnabled(false);

        resetButton.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        resetButton.setText("Reset");
        resetButton.setMaximumSize(new java.awt.Dimension(100, 25));
        resetButton.setMinimumSize(new java.awt.Dimension(100, 25));
        resetButton.setPreferredSize(new java.awt.Dimension(100, 25));

        switchButton.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        switchButton.setText("Swt Vw");
        switchButton.setMaximumSize(new java.awt.Dimension(100, 25));
        switchButton.setMinimumSize(new java.awt.Dimension(100, 25));
        switchButton.setMultiClickThreshhold(500L);
        switchButton.setPreferredSize(new java.awt.Dimension(100, 25));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(matchTimeBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(beginMatchButton, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(stopMatchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(resetButton, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(switchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(matchTimeBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(stopMatchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(beginMatchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(resetButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(switchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Gets the "start match" button.
     *
     * @return The start match JButton.
     */
    public JButton getMatchButton() {
        return beginMatchButton;
    }

    /**
     * Gets the autonomous text field.
     *
     * @return The autonomous text field.
     */
    public JTextField getAutonField() {
        return matchTimeBox1.getAutonField();
    }

    /**
     * Gets the teleop text field.
     *
     * @return The teleop text field.
     */
    public JTextField getTeleField() {
        return matchTimeBox1.getTeleField();
    }

    /**
     * Gets the "Stop Match" button.
     *
     * @return The stop match JButton.
     */
    public JButton getStopMatchButton() {
        return stopMatchButton;
    }

    /**
     * Gets the reset button.
     *
     * @return The Reset JButton.
     */
    public JButton getResetButton() {
        return resetButton;
    }

    /**
     * Gets the "Switch Views" button.
     *
     * @return The switch views button.
     */
    public JButton getSwitchButton() {
        return switchButton;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton beginMatchButton;
    private UI.MatchTimeBox matchTimeBox1;
    private javax.swing.JButton resetButton;
    private javax.swing.JButton stopMatchButton;
    private javax.swing.JButton switchButton;
    // End of variables declaration//GEN-END:variables
}