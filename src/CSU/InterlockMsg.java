/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CSU;

import GraphicElements.PSpace;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

/**
 *
 * @author Istrac
 */
public class InterlockMsg extends JPanel {

    private JLabel alert;
    private javax.swing.Timer blinkTimer;
    private javax.swing.Timer delaytick;
    private boolean show;
    private JLabel notification1, notification2;
    private int count;

    public InterlockMsg() {
        setPreferredSize(new Dimension(0, 0));
        setLayout(new GridBagLayout());
        alert = new JLabel();
        alert.setPreferredSize(new Dimension(0, 0));
        alert.setFont(alert.getFont().deriveFont(16.0f));
        alert.setHorizontalAlignment(SwingConstants.CENTER);
        alert.setForeground(Color.red);
        notification1 = new JLabel();
        notification1.setPreferredSize(new Dimension(0, 0));
        notification1.setHorizontalAlignment(SwingConstants.CENTER);
        notification1.setVerticalAlignment(SwingConstants.BOTTOM);
        notification2 = new JLabel();
        notification2.setPreferredSize(new Dimension(0, 0));
        notification2.setHorizontalAlignment(SwingConstants.CENTER);
        notification2.setVerticalAlignment(SwingConstants.TOP);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.weighty = 0.33;
        add(new PSpace(alert,0.1,0.1,0.1,0.1), constraints);
        constraints.gridx = 0;
        constraints.gridy = 1;
        add(notification1, constraints);
        constraints.gridx = 0;
        constraints.gridy = 2;
        add(notification2, constraints);
        blinkTimer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (show) {
                    alert.setBorder(BorderFactory.createLineBorder(Color.RED));
                    alert.setText("Check Interlock");
                } else {
                    alert.setBorder(null);
                    alert.setText("");
                }
                show = !show;
            }
        });
        delaytick = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                count++;
                notification2.setText(count + " s");                
            }
        });
    }

    public void setAlert(boolean flag) {

        if (flag) {
            if (!blinkTimer.isRunning()) {
                show = true;
                blinkTimer.start();
            }
        } else {
            if (blinkTimer.isRunning()) {                
                blinkTimer.stop();
                alert.setBorder(null);
                alert.setText("");
            }
        }
    }

    public void setDelayNotification(boolean flag) {
        if (flag) {
            if (!delaytick.isRunning()) {
                count = 0;
                notification1.setText("HV NOT READY");
                notification2.setText(count + " s");     
                delaytick.start();
            }
        } else {
            if (delaytick.isRunning()) {
                notification1.setText("");
                notification2.setText("");
                delaytick.stop();
            }
        }
    }
}
