/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CSU;

import GraphicElements.MultiLineText;
import GraphicElements.PSpace;
import GraphicElements.Rectangle;
import Utility.XMLReadWrite;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author Istrac
 */
public class TrigDelay extends javax.swing.JPanel {

    private final MultiLineText[] labels;
    private final JTextField[] values;
    private int pulsecount;
    private double delay1 = 1;
    private double delay2 = 1;
    private double delay3 = 1;
    SenseElectronicsFormat fmt;
    /**
     * Creates new form TrigDelay
     */
    public TrigDelay() {
        initComponents();
        JPanel p1;
        {
            p1 = new JPanel();
            p1.setPreferredSize(new java.awt.Dimension(0, 0));
            p1.setLayout(new java.awt.GridBagLayout());
            //p1.setBackground(Color.WHITE);
            JLabel[] columnNames = new JLabel[3];
            String[] names = new String[]{"PARAMETERS", "VALUES"};
            PSpace[] spaces = new PSpace[3];
            for (int i = 0; i < 2; i++) {
                columnNames[i] = new JLabel(names[i]);
                columnNames[i].setBackground(new Color(96, 88, 133));
                columnNames[i].setOpaque(true);
                columnNames[i].setHorizontalAlignment(SwingConstants.CENTER);
                columnNames[i].setForeground(Color.WHITE);
                spaces[i] = new PSpace(columnNames[i], 0.2, 0.1, 0.1, 0.1);

            }
            GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();

            gridBagConstraints.gridheight = 1;
            gridBagConstraints.gridwidth = 1;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            gridBagConstraints.weightx = 0.33;
            gridBagConstraints.weighty = 1;

            for (int i = 0; i < 2; i++) {
                gridBagConstraints.gridx = i;
                gridBagConstraints.gridy = 0;
                p1.add(spaces[i], gridBagConstraints);
            }

        }
        JPanel p2;
        {
            p2 = new JPanel();
            p2.setPreferredSize(new java.awt.Dimension(0, 0));
            p2.setLayout(new java.awt.GridBagLayout());
            //p2.setBackground(Color.WHITE);
            labels = new MultiLineText[4];
            values = new JTextField[4];
            PSpace[] spaces1 = new PSpace[4];
            PSpace[] spaces0 = new PSpace[4];
            String[] names = new String[]{"BEAM-RF:RISE TIME DELAY", "RF-BEAM:FALL TIME DELAY", "IPP-BEAM:RISE TIME DELAY","PULSE COUNT"};

            for (int i = 0; i < 4; i++) {
                labels[i] = new MultiLineText(names[i]);
                labels[i].setOutlined(false);
                if (i % 2 == 0) {
                    labels[i].setBackground(new Color(112, 39, 195));
                } else {
                    labels[i].setBackground(new Color(112, 39, 195));
                }
                labels[i].setTextanchor(Rectangle.LEFT);
                labels[i].rectangle.settextcolor(Color.WHITE);
                spaces0[i] = new PSpace(labels[i], 0.1, 0.1, 0.1, 0.1);

                values[i] = new JTextField("XXX.XX");

                spaces1[i] = new PSpace(values[i], 0.3, 0.1, 0.2, 0.1);

                //spaces1[i].setbkgrnd(Color.WHITE);
                values[i].setBackground(Color.WHITE);
                values[i].setOpaque(true);
                values[i].setHorizontalAlignment(SwingConstants.CENTER);
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

            for (int i = 0; i < 4; i++) {
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = i;
                gridBagConstraints.weightx = 0.33;
                p2.add(spaces0[i], gridBagConstraints);
                gridBagConstraints.gridx = 1;
                gridBagConstraints.weightx = 0.33;
                p2.add(spaces1[i], gridBagConstraints);
            }
            final XMLReadWrite xmlReadWrite = new XMLReadWrite("senseElParams.xml");
            values[3].setText(xmlReadWrite.getTextByTag("PRFcount"));
            values[0].setText(xmlReadWrite.getTextByTag("TxTrig2delay"));
            values[1].setText(xmlReadWrite.getTextByTag("Tx2Trig2Traildelay"));
            values[2].setText(xmlReadWrite.getTextByTag("TxTrig1delay"));

        }
        PSpace p3;
        {
            JButton button = new JButton("SAVE");
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    int pc = Integer.parseInt(values[3].getText());
                    double d2 = Double.parseDouble(values[0].getText());
                    double d3 = Double.parseDouble(values[1].getText());
                    double d1 = Double.parseDouble(values[2].getText());
                    if (d1 < 0.25) {
                        JOptionPane.showMessageDialog(values[1], "value should be greater than 0.25");
                        return;
                    }
                    if (d2 < 0.05) {
                        JOptionPane.showMessageDialog(values[2], "value should be greater than 0.05");
                        return;
                    }
                    if (d3 < 0) {
                        JOptionPane.showMessageDialog(values[3], "value should be positive");
                        return;
                    }
                    pulsecount = pc;
                    delay1 = d1;
                    delay2 = d2;
                    delay3 = d3;
                    final XMLReadWrite xmlReadWrite = new XMLReadWrite("senseElParams.xml");
                    xmlReadWrite.Write("TxTrig1delay", String.valueOf(d1));
                    xmlReadWrite.Write("TxTrig2delay", String.valueOf(d2));
                    xmlReadWrite.Write("Tx2Trig2Traildelay", String.valueOf(d3));
                    xmlReadWrite.Write("PRFcount", String.valueOf(pc));
                    xmlReadWrite.printToFile();
                }
            });
            p3 = new PSpace(button, 0.35, 0.25, 0.35, 0.25);

        }

        GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 0.1;
        add(p1, gridBagConstraints);
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weighty = 0.5;
        add(p2, gridBagConstraints);
        gridBagConstraints.gridy = 2;
        gridBagConstraints.weighty = 0.1;
        add(p3, gridBagConstraints);
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
