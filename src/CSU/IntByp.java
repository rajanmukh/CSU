/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CSU;

import GraphicElements.MultiLineText;
import GraphicElements.PSpace;
import GraphicElements.Rectangle;
import Utility.XMLReadWrite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author rajan
 */
public class IntByp extends javax.swing.JPanel {

    public JCheckBox[] chks;
    public final JTextField[] limit;
    private final PSpace[] spaces;
    private final PSpace[] spaces1;
    private final int noOfEntries;
    private final MultiLineText[] labels;
    private final PSpace[] labelspaces;
    private final MultiLineText[] labels1;
    private final JPanel p2;

    /**
     * Creates new form IntByp
     *
     * @param num
     */
    public IntByp(int num) {
        initComponents();

        JPanel p1;
        {
            p1 = new JPanel();
            p1.setLayout(new GridBagLayout());
            p1.setPreferredSize(new Dimension(0, 0));
            String[] names = new String[]{"PARAMETERS", "", "LIMIT"};
            int noOfEntries = names.length;
            labels1 = new MultiLineText[noOfEntries];
            spaces = new PSpace[noOfEntries];
            Font font = new Font("DejaVu Sans", Font.BOLD, 10);
            for (int i = 0; i < noOfEntries; i++) {
                labels1[i] = new MultiLineText(names[i]);
                labels1[i].setFont(font);
                labels1[i].setBackground(new Color(96, 88, 133));
                labels1[i].setOutlined(false);
                //labels1[i].setHorizontalAlignment(SwingConstants.CENTER);
                if (i != 1) {
                    labels1[i].setOpaque(true);
                }
                labels1[i].setFontsize(10);
                labels1[i].rectangle.settextcolor(Color.WHITE);

            }
            labels1[1].setBackground(new Color(238, 238, 238));
            spaces[0] = new PSpace(labels1[0], 0.2, 0.1, 0.2, 0.1);
            spaces[1] = new PSpace(labels1[1], 0.2, 0.1, 0.2, 0.1);
            spaces[2] = new PSpace(labels1[2], 0.25, 0.1, 0.25, 0.1);
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridwidth = 1;
            gridBagConstraints.gridheight = 1;
            gridBagConstraints.fill = GridBagConstraints.BOTH;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.weighty = 1.0;

            gridBagConstraints.gridx = 0;
            gridBagConstraints.weightx = 0.4;
            p1.add(spaces[0], gridBagConstraints);
            gridBagConstraints.gridx = 1;
            gridBagConstraints.weightx = 0.15;
            p1.add(spaces[1], gridBagConstraints);
            gridBagConstraints.gridx = 2;
            gridBagConstraints.weightx = 0.45;
            p1.add(spaces[2], gridBagConstraints);

        }

        {
            p2 = new JPanel();
            p2.setLayout(new GridBagLayout());
            p2.setPreferredSize(new Dimension(0, 0));
//            String[] names = new String[]{"", "LVPS FAULT", "EIK BLOWER FAULT", "MODULATOR MAINS:FAULT",
//                "EIK BLOWER", "HV FAULT", "MODULATION ON FAULT", "ARC DETECTOR FAULT", "EXCESSIVE DUTY FAULT", "VSWR FAULT", "EXCESS REVERSE:POWER FAULT"};
            noOfEntries = num;
            labels = new MultiLineText[noOfEntries];
            chks = new JCheckBox[noOfEntries];
            limit = new JTextField[noOfEntries];
            spaces1 = new PSpace[noOfEntries];
            labelspaces = new PSpace[noOfEntries];

            for (int i = 0; i < noOfEntries; i++) {
                labels[i] = new MultiLineText("");
                labelspaces[i] = new PSpace(labels[i], 0.1, 0.1, 0.1, 0.1);
                labels[i].setBackground(new Color(112, 39, 195));
                labels[i].setOutlined(false);
                labels[i].setTextanchor(Rectangle.LEFT);
                labels[i].setFontsize(10);
                labels[i].rectangle.settextcolor(Color.WHITE);

                chks[i] = new JCheckBox();
                //chks[i].setHorizontalAlignment(SwingConstants.CENTER);
                limit[i] = new JTextField(" ");
                limit[i].setHorizontalAlignment(SwingConstants.CENTER);
                //limit[i].setBackground(Color.GREEN);
                //limit[i].setOpaque(true);
                spaces1[i] = new PSpace(limit[i], 0.1, 0.2, 0.3, 0.2);
            }

            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridwidth = 1;
            gridBagConstraints.gridheight = 1;
            gridBagConstraints.fill = GridBagConstraints.BOTH;
            gridBagConstraints.weighty = 1.0 / noOfEntries;
            for (int i = 0; i < noOfEntries; i++) {
                gridBagConstraints.gridy = i;
                gridBagConstraints.gridx = 0;
                gridBagConstraints.weightx = 0.5;
                p2.add(labelspaces[i], gridBagConstraints);
                gridBagConstraints.gridx = 1;
                gridBagConstraints.weightx = 0.15;
                p2.add(chks[i], gridBagConstraints);
                gridBagConstraints.gridx = 2;
                gridBagConstraints.weightx = 0.45;
                p2.add(spaces1[i], gridBagConstraints);
            }
        }

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.weightx = 1;
        add(p1, gridBagConstraints);
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weighty = 0.9;
        add(p2, gridBagConstraints);
    }

    public void eraseColumn() {
        spaces[2].setVisible(false);
        for (int i = 0; i < noOfEntries; i++) {
            spaces1[i].setVisible(false);
        }
    }

    public void rearrangeCheckBoxes() {
        for (int i = 0; i < 8; i++) {
            p2.remove(chks[i]);
        }
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridwidth = 1;
        
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        
        int[] gridys={0,2,3,4,6};
        int[] heights={2,1,1,2,2};
        double[] weightys={2.0/8,1.0/8,1.0/8,2.0/8,2.0/8};
        for (int i = 0; i < 5; i++) {
            gridBagConstraints.weighty = weightys[i];
            gridBagConstraints.gridheight = heights[i];
            gridBagConstraints.gridy = gridys[i];
            gridBagConstraints.gridx = 1;
            gridBagConstraints.weightx = 0.15;
            p2.add(chks[i], gridBagConstraints);
        }
    }

    public void setLabel(int itemIndex, String text) {
        if (!"".equals(text)) {
            labels[itemIndex].rectangle.label.settext(text);
        } else {
            labelspaces[itemIndex].setbkgrnd(new Color(238, 238, 238));
        }
    }

    public void setheading(String text) {
        labels1[0].rectangle.settext(text);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(new java.awt.GridBagLayout());
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
