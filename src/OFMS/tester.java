package OFMS;

import UI.BlueAlliance.BlueAlli;
import UI.BlueAlliance.Blue_UI_Block;
import UI.Match_Control_UI;
import UI.New_UI;
import UI.RedAlliance.RedAlli;
import UI.RedAlliance.Red_UI_Block;
import java.awt.Toolkit;

/**
 * This is a container for the simple UI panel used in giving basic function
 * control as an FMS system.
 *
 * Complete System developed by:
 *
 * @author Alex Bassett
 * @author Walton Robotics - Team 2974
 * @author Foundation code developed by Josh, Andrew Lobos, Team 225
 * @author Structural assistance and refactoring by Doug Neumann
 */
public class tester extends javax.swing.JFrame {

    /**
     * Holds a local copy of the basic UI.
     */
    private New_UI new_UI1;

    /**
     * Creates new simple UI.
     */
    public tester() {
        initComponents();

        new_UI1 = simple_UI1.getNewUI();
        setUpIndicators();

        System.out.println("MainFMSDialog Constructor");

        this.setTitle("(O!FMS) Open Field Managment System");
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass()
                .getClassLoader().getResource("OFMS/LogoFiles/ofms logo.png")));
        this.setResizable(false);

    }

    /**
     * Gets a reference to the consistent UI.
     *
     * @return A reference to the consistent UI.
     */
   public New_UI getNewUI() {
        return new_UI1;
    }

    /**
     * Sets the active components, such as buttons and text fields, to the UI
     * layer, thus making it available for interaction with the user.
     */
    public final void setUpIndicators() {
        System.out.println("Simple UI - Set Up Indicators");

        //<editor-fold defaultstate="collapsed" desc="Set Team Indicators">
        BlueAlli tempB_Alli = new_UI1.getAllisUI().getBlue();
        Blue_UI_Block tempB1 = tempB_Alli.getBlue1();
        Main.layer.setBlue1(tempB1.getTeamIndic(), tempB1.getBypass(), tempB1.getVoltIndic(),
                tempB1.getRob_Com(), tempB1.getDS_Com());

        Blue_UI_Block tempB2 = tempB_Alli.getBlue2();
        Main.layer.setBlue2(tempB2.getTeamIndic(), tempB2.getBypass(), tempB2.getVoltIndic(),
                tempB2.getRob_Com(), tempB2.getDS_Com());

        Blue_UI_Block tempB3 = tempB_Alli.getBlue3();
        Main.layer.setBlue3(tempB3.getTeamIndic(), tempB3.getBypass(), tempB3.getVoltIndic(),
                tempB3.getRob_Com(), tempB3.getDS_Com());


        RedAlli tempR_Alli = new_UI1.getAllisUI().getRed();
        Red_UI_Block tempR1 = tempR_Alli.get1();
        Main.layer.setRed1(tempR1.getTeamIndic(), tempR1.getBypass(), tempR1.getVoltIndic(),
                tempR1.getRob_Com(), tempR1.getDS_Com());

        Red_UI_Block tempR2 = tempR_Alli.get2();
        Main.layer.setRed2(tempR2.getTeamIndic(), tempR2.getBypass(), tempR2.getVoltIndic(),
                tempR2.getRob_Com(), tempR2.getDS_Com());

        Red_UI_Block tempR3 = tempR_Alli.get3();
        Main.layer.setRed3(tempR3.getTeamIndic(), tempR3.getBypass(), tempR3.getVoltIndic(),
                tempR3.getRob_Com(), tempR3.getDS_Com());
        //</editor-fold>

        Match_Control_UI mcui = simple_UI1.getMatchControl();

        Main.layer.setSideIndicators(new_UI1.getRedIndicator(), new_UI1.getBlueIndicator());
        Main.layer.setFullFieldReady(new_UI1.getFullFieldIndic());
        Main.layer.setMatchButton(mcui.getMatchButton());
        Main.layer.setResetButton(mcui.getResetButton());
        Main.layer.setTimeSetters(mcui.getAutonField(), mcui.getTeleField());
        Main.layer.setMatchTimeField(new_UI1.getFullFieldIndic());
        Main.layer.setStopButton(mcui.getStopMatchButton());
        Main.layer.setProgressBar(new_UI1.getProgBar());
        Main.layer.setSwitchViewButton(mcui.getSwitchButton());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        simple_UI1 = new UI.Simple_UI();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        setMaximumSize(new java.awt.Dimension(805, 525));
        setMinimumSize(new java.awt.Dimension(805, 525));
        setPreferredSize(new java.awt.Dimension(805, 525));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                Bounds2Changed(evt);
            }
        });
        addHierarchyBoundsListener(new java.awt.event.HierarchyBoundsListener() {
            public void ancestorMoved(java.awt.event.HierarchyEvent evt) {
            }
            public void ancestorResized(java.awt.event.HierarchyEvent evt) {
                BoundsChanged(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(simple_UI1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(simple_UI1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BoundsChanged(java.awt.event.HierarchyEvent evt) {//GEN-FIRST:event_BoundsChanged
    }//GEN-LAST:event_BoundsChanged

    private void Bounds2Changed(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_Bounds2Changed
    }//GEN-LAST:event_Bounds2Changed

    /**
     * This shouldn't be called...
     *
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
            java.util.logging.Logger.getLogger(tester.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(tester.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(tester.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(tester.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
       java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                System.out.println("*****ERROR - Called tester main");
                //new tester().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private UI.Simple_UI simple_UI1;
    // End of variables declaration//GEN-END:variables
}