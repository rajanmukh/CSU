/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CSU;

import GraphicElements.PSpace;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author Istrac
 */
public class DisplayFrame extends javax.swing.JFrame {

    public final JTextField[] rdisp;
    public final JTextField[] sdisp;

    /**
     * Creates new form DisplayFrame
     */
    public DisplayFrame() {
        initComponents();
        JPanel rp;
        {
            rp = new JPanel();
            rp.setPreferredSize(new Dimension(0, 0));
            rp.setLayout(new GridBagLayout());
            JLabel heading = new JLabel("Received");
            heading.setHorizontalAlignment(SwingConstants.CENTER);
            heading.setPreferredSize(new Dimension(0, 0));
            JLabel[] labels = new JLabel[15];
            PSpace[] spaces = new PSpace[15];
            rdisp = new JTextField[15];
            for (int i = 0; i < labels.length; i++) {
                String labeltext = getlabeltext(i + 1);
                labels[i] = new JLabel(labeltext);
                labels[i].setHorizontalAlignment(SwingConstants.CENTER);
                labels[i].setPreferredSize(new Dimension(0, 0));
                rdisp[i] = new JTextField("");
                spaces[i] = new PSpace(rdisp[i], 0.1, 0.1, 0.1, 0.1);
            }

            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.gridwidth = 2;
            gridBagConstraints.gridheight = 1;
            gridBagConstraints.weightx = 1;
            gridBagConstraints.weighty = 1.0 / 16;
            gridBagConstraints.fill = GridBagConstraints.BOTH;
            rp.add(heading, gridBagConstraints);
            gridBagConstraints.gridwidth = 1;
            gridBagConstraints.weightx = 0.5;
            for (int i = 0; i < labels.length; i++) {
                gridBagConstraints.gridy = i + 1;
                gridBagConstraints.gridx = 0;
                rp.add(labels[i], gridBagConstraints);
                gridBagConstraints.gridx = 1;
                rp.add(spaces[i], gridBagConstraints);
            }
            
//            
        }
        JPanel sp;
        {
            sp = new JPanel();
            sp.setPreferredSize(new Dimension(0, 0));
            sp.setLayout(new GridBagLayout());
            JLabel heading = new JLabel("sent Packet");
            heading.setHorizontalAlignment(SwingConstants.CENTER);
            heading.setPreferredSize(new Dimension(0, 0));
            JLabel[] labels = new JLabel[15];
            PSpace[] spaces = new PSpace[15];
            sdisp = new JTextField[15];
            for (int i = 0; i < labels.length; i++) {
                String labeltext = getlabeltext(i + 1);
                labels[i] = new JLabel(labeltext);
                labels[i].setHorizontalAlignment(SwingConstants.CENTER);
                labels[i].setPreferredSize(new Dimension(0, 0));
                sdisp[i] = new JTextField("");
                spaces[i] = new PSpace(sdisp[i], 0.1, 0.1, 0.1, 0.1);
            }
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.gridwidth = 2;
            gridBagConstraints.gridheight = 1;
            gridBagConstraints.weightx = 1;
            gridBagConstraints.weighty = 1.0 / 16;
            gridBagConstraints.fill = GridBagConstraints.BOTH;
            sp.add(heading, gridBagConstraints);
            gridBagConstraints.gridwidth = 1;
            gridBagConstraints.weightx = 0.5;
            for (int i = 0; i < labels.length; i++) {
                gridBagConstraints.gridy = i + 1;
                gridBagConstraints.gridx = 0;
                sp.add(labels[i], gridBagConstraints);
                gridBagConstraints.gridx = 1;
                sp.add(spaces[i], gridBagConstraints);
            }
            JLabel dummy=new JLabel("");
            dummy.setPreferredSize(new Dimension(0,0));
            gridBagConstraints.gridwidth = 2;
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 16;
            gridBagConstraints.weightx = 1;
            gridBagConstraints.weighty = 0.0 / 16;
            sp.add(dummy,gridBagConstraints);
            
        }
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        getContentPane().add(rp, gridBagConstraints);
        gridBagConstraints.gridx = 1;
        getContentPane().add(sp, gridBagConstraints);
        setSize(500,700);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(DisplayFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DisplayFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DisplayFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DisplayFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DisplayFrame().setVisible(true);
            }
        });
    }

    private String getlabeltext(int i) {
        String str = "";
        switch (i) {
            case 1:
                str = "  " + i + "st word";
                break;
            case 2:
                str = "  " + i + "nd word";
                break;
            case 3:
                str = "  " + i + "rd word";
                break;
            default:
                str = "  " + i + "th word";
        }
        return str;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
