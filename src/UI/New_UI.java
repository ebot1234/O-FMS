package UI;

import Communication.DSReceiver;
import OFMS.GovernThread;
import OFMS.Main;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * Represents the "New_UI", aka, the UI that persists between the different
 * views and O!FMS modes.
 *
 * Complete System developed by:
 *
 * @author Alex Bassett
 * @author Walton Robotics - Team 2974
 * @author Foundation code developed by Josh, Andrew Lobos, Team 225
 * @author Structural assistance and refactoring by Doug Neumann
 */
public class New_UI extends javax.swing.JPanel {

    /**
     * A reference to the sole instance of the class - this is that instance.
     */
    private static New_UI _instance;

    /**
     * Creates a new New_UI
     */
    public New_UI() {
        initComponents();
        System.out.println("CREATING NEW UI");
        _instance = this;
        this.addHierarchyListener(new HierarchyListener() {
            @Override
            public void hierarchyChanged(HierarchyEvent e) {
                boolean connected = setupListenersWhenConnected();
                if (connected) {
                    New_UI.this.removeHierarchyListener(this);
                }
            }
        });
    }

    /**
     * Gets a New_UI instance for easier access to the correct buttons and
     * indicators.
     *
     * @return The created instance of "New_UI"
     */
    public static New_UI getInstance() {
        return _instance;
    }

    /**
     * Creates a window closing event listener that disables all robots before
     * allowing the application to exit.
     *
     * @return Whether or not this component was able to locate its parent
     * frame.
     */
    protected boolean setupListenersWhenConnected() {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (parentFrame == null) {
            return false;
        }
        parentFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Implementation here
                System.out.println("This window is closing! Robots Disabled");
                GovernThread game = GovernThread.getInstance();
                if (game != null) {
                    game.disableAllBots();
                    Main.getInstance().shutdownAllNetworkComms();
                }
            }
        });
        return true;
    }

    /**
     * Gets the "Allis UI". Typically used to obtain information about the
     * different teams.
     *
     * @return The Allis UI.
     */
    public Allis_UI getAllisUI() {
        return allis_UI1;
    }

    /**
     * Gets the "Full Field Indicator" which visually shows when the field is
     * and isn't ready by coloring its background green or grey.
     *
     * @return The Full Field Indicator's JTextField.
     */
    public JTextField getFullFieldIndic() {
        return timeFullFieldIdicator;
    }

    /**
     * Gets the Blue indicator used to show blue alliance connections status.
     *
     * @return The Blue indicator JLable
     */
    public JLabel getBlueIndicator() {
        return allis_UI1.getBlue().getBlueIndic();
    }

    /**
     * Gets the Red indicator used to show red alliance connections status.
     *
     * @return The Red indicator JLable
     */
    public JLabel getRedIndicator() {
        return allis_UI1.getRed().getRedIndic();
    }

    /**
     * Gets the match progress bar used to visually show progression through the
     * match.
     *
     * @return The match progress bar.
     */
    public JProgressBar getProgBar() {
        return matchProgressBar;
    }

    /**
     * Gets the message area used to display difference messages.
     *
     * @return The Message JTextArea.
     */
    public JTextArea getMessagesTextArea() {
        return allis_UI1.getMessagesArea();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        matchProgressBar = new javax.swing.JProgressBar();
        timeFullFieldIdicator = new javax.swing.JTextField();
        allis_UI1 = new UI.Allis_UI();

        setBackground(new java.awt.Color(0, 0, 0));

        timeFullFieldIdicator.setEditable(false);
        timeFullFieldIdicator.setBackground(new java.awt.Color(153, 153, 153));
        timeFullFieldIdicator.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        timeFullFieldIdicator.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        timeFullFieldIdicator.setText("010");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(312, 312, 312)
                        .addComponent(timeFullFieldIdicator, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(matchProgressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(allis_UI1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(timeFullFieldIdicator, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(allis_UI1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(matchProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private UI.Allis_UI allis_UI1;
    private javax.swing.JProgressBar matchProgressBar;
    private javax.swing.JTextField timeFullFieldIdicator;
    // End of variables declaration//GEN-END:variables
}
