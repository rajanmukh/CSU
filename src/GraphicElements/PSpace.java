/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphicElements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 *
 * @author rajan
 */
public class PSpace extends javax.swing.JPanel {

    private JComponent component;
    private final JPanel left;
    private final JPanel right;
    private final JPanel top;
    private final JPanel bottom;

    /**
     * Creates new form PSpace
     */
    public PSpace(JComponent comp, double l, double t, double r, double b) {
        setPreferredSize(new Dimension(0, 0));
        left = new JPanel();
        right = new JPanel();
        top = new JPanel();
        bottom = new JPanel();
        component = comp;

        java.awt.GridBagConstraints gridBagConstraints;

        setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1 - l - r;
        gridBagConstraints.weighty = t;

        //gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        //gridBagConstraints.insets = new java.awt.Insets(100, 150, 100, 150);
        top.setPreferredSize(new Dimension(0, 0));
        add(top, gridBagConstraints);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1 - l - r;
        gridBagConstraints.weighty = b;

        //gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        //gridBagConstraints.insets = new java.awt.Insets(100, 150, 100, 150);
        bottom.setPreferredSize(new Dimension(0, 0));
        add(bottom, gridBagConstraints);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = l;
        gridBagConstraints.weighty = 1;

        //gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        //gridBagConstraints.insets = new java.awt.Insets(100, 150, 100, 150);
        left.setPreferredSize(new Dimension(0, 0));
        add(left, gridBagConstraints);
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = r;
        gridBagConstraints.weighty = 1;

        //gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        //gridBagConstraints.insets = new java.awt.Insets(100, 150, 100, 150);
        right.setPreferredSize(new Dimension(0, 0));
        add(right, gridBagConstraints);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1 - l - r;
        gridBagConstraints.weighty = 1 - t - b;

        //gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        //gridBagConstraints.insets = new java.awt.Insets(100, 150, 100, 150);
        comp.setPreferredSize(new Dimension(0, 0));
        add(component, gridBagConstraints);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    public void setbkgrnd(Color bg) {
        this.setBackground(bg);
        left.setBackground(bg);
        right.setBackground(bg);
        top.setBackground(bg);
        bottom.setBackground(bg);
        component.getClass().getName();
        String name = component.getClass().getName();
        if (!name.equalsIgnoreCase("javax.swing.JButton") & !name.equalsIgnoreCase("GraphicElements.MyButton")) {
            component.setBackground(bg);
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}