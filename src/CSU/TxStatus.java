/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CSU;

import GraphicElements.Border;
import GraphicElements.MultiLineText;
import GraphicElements.PSpace;
import GraphicElements.Rectangle;
import Utility.Frame.Status;
import java.awt.Color;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author Istrac
 */
public class TxStatus extends javax.swing.JPanel {

    private final MultiLineText[] labels1;
    private final JLabel[] results1;
    private final MultiLineText[] labels2;
    private final JLabel[] results2;

    /**
     * Creates new form TxStatus
     */
    public TxStatus() {
        initComponents();
        JPanel p1;
        {
            p1 = new JPanel();
            p1.setPreferredSize(new java.awt.Dimension(0, 0));
            p1.setLayout(new java.awt.GridBagLayout());
            p1.setBackground(Color.WHITE);
            labels1 = new MultiLineText[10];
            results1 = new JLabel[10];
            PSpace[] spaces = new PSpace[10];
            String[] names = new String[]{"MAINS STATUS", "ANTENNA/:DUMMY LOAD", "DOORS", "LVPS", "EIK BLOWER", "DUTY RATIO", "MODULATOR MAIN", "HV", "MODULATION", "RF INPUT"};

            for (int i = 0; i < 10; i++) {
                labels1[i] = new MultiLineText(names[i]);
                labels1[i].setOutlined(false);
                if (i % 2 == 0) {
                    labels1[i].setBackground(Color.WHITE);
                } else {
                    labels1[i].setBackground(Color.LIGHT_GRAY);
                }
                labels1[i].setTextanchor(Rectangle.LEFT);
                results1[i] = new JLabel("xx.xx");

                spaces[i] = new PSpace(results1[i], 0.2, 0.1, 0.1, 0.1);

                spaces[i].setbkgrnd(Color.WHITE);

                results1[i].setBackground(Color.red);
                results1[i].setOpaque(true);
                results1[i].setHorizontalAlignment(SwingConstants.CENTER);
                //results[i].setOutlined(false);
            }

            GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.gridheight = 1;
            gridBagConstraints.gridwidth = 1;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            gridBagConstraints.weightx = 0.6;
            gridBagConstraints.weighty = 0.1;

            for (int i = 0; i < 10; i++) {
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = i;
                gridBagConstraints.weightx = 0.6;
                p1.add(labels1[i], gridBagConstraints);
                gridBagConstraints.gridx = 1;
                gridBagConstraints.weightx = 0.4;
                p1.add(spaces[i], gridBagConstraints);
            }
        }
        JPanel p2;
        {
            p2 = new JPanel();
            p2.setPreferredSize(new java.awt.Dimension(0, 0));
            p2.setLayout(new java.awt.GridBagLayout());
            p2.setBackground(Color.WHITE);
            labels2 = new MultiLineText[9];
            results2 = new JLabel[9];
            PSpace[] spaces = new PSpace[9];
            String[] names = new String[]{"TX RADIATION", "FAULT SUM", "WAVEGUIDE PRESSURE:LOW", "WAVEGUIDE PRESSURE:HIGH", "TRIGGER", "WAVEGUIDE ARC:DETECTOR", "SECTOR BLANK", "MAN ALOFT", "DATA READY"};

            for (int i = 0; i < 9; i++) {
                labels2[i] = new MultiLineText(names[i]);
                labels2[i].setOutlined(false);
                if (i % 2 == 0) {
                    labels2[i].setBackground(Color.WHITE);
                } else {
                    labels2[i].setBackground(Color.LIGHT_GRAY);
                }
                labels2[i].setTextanchor(Rectangle.LEFT);
                results2[i] = new JLabel("xx.xx");

                spaces[i] = new PSpace(results2[i], 0.2, 0.1, 0.1, 0.1);

                spaces[i].setbkgrnd(Color.WHITE);

                results2[i].setBackground(Color.red);
                results2[i].setOpaque(true);
                results2[i].setHorizontalAlignment(SwingConstants.CENTER);
                //results[i].setOutlined(false);
            }

            GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.gridheight = 1;
            gridBagConstraints.gridwidth = 1;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            gridBagConstraints.weightx = 0.6;
            gridBagConstraints.weighty = 0.11;

            for (int i = 0; i < 9; i++) {
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = i;
                gridBagConstraints.weightx = 0.6;
                p2.add(labels2[i], gridBagConstraints);
                gridBagConstraints.gridx = 1;
                gridBagConstraints.weightx = 0.4;
                p2.add(spaces[i], gridBagConstraints);
            }
        }

        GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 1;
        Border b1 = new Border(p1);
        b1.setborderColor(Color.BLUE);
        b1.setThickness(2.0f);
        b1.setInnerfillColor(Color.WHITE);
        add(new PSpace(b1, 0.2, 0, 0.0, 0), gridBagConstraints);
        gridBagConstraints.gridx = 1;
        Border b2 = new Border(p2);
        b2.setborderColor(Color.BLUE);
        b2.setThickness(2.0f);
        b2.setInnerfillColor(Color.WHITE);
        add(new PSpace(b2, 0.1, 0, 0.1, 0), gridBagConstraints);

    }

    public void setValue(int panelIndex, int itemIndex, String text) {
        JLabel[] p = null;
        if (panelIndex == 0) {
            p = results1;
        } else if (panelIndex == 1) {
            p = results2;
        }

        if (p != null) {
            p[itemIndex].setText(text);
        }
    }

    public void setStatus(int panelIndex, int itemIndex, int status) {
        JLabel[] p = null;
        if (panelIndex == 0) {
            p = results1;
        } else if (panelIndex == 1) {
            p = results2;
        }
        switch (status) {
            case Status.FINE:
                p[itemIndex].setBackground(Color.GREEN);
                setValue(panelIndex, itemIndex,"OK");
                break;
            case Status.ERROR:
                p[itemIndex].setBackground(Color.RED);
                setValue(panelIndex, itemIndex,"NOT OK");
                break;
            default:
                ;
        }
        repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setPreferredSize(new java.awt.Dimension(0, 0));
        setLayout(new java.awt.GridBagLayout());
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
