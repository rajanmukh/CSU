/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CSU;

import GraphicElements.PSpace;
import Utility.XMLReadWrite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Istrac
 */
public class CalTable extends javax.swing.JPanel {

    private final JButton[] jButton;
    private final JTable jTable1;
    private int btnidx = -1;
    double[][] x, y;
    int[] tablelen;

    /**
     * Creates new form CalTable
     */
    public CalTable() {
        initComponents();
        x = new double[4][50];
        y = new double[4][50];
        tablelen = new int[4];
        for (int i = 0; i < 4; i++) {
            XMLReadWrite xmlReadWrite = new XMLReadWrite("detector table\\table" + (i + 1) + ".xml");
            String[] mVs = xmlReadWrite.getAllTextValuesByTag("voltage");
            String[] powers = xmlReadWrite.getAllTextValuesByTag("power");
            tablelen[i] = mVs.length;
            for (int j = 0; j < tablelen[i]; j++) {
                if (!mVs[j].isEmpty()) {
                    x[i][j] = Double.parseDouble(mVs[j]);
                    y[i][j] = Double.parseDouble(powers[j]);
                }
            }
        }
        JPanel p2;
        {
            p2 = new JPanel();
            p2.setPreferredSize(new java.awt.Dimension(0, 0));
            p2.setLayout(new java.awt.GridBagLayout());
            p2.setBackground(Color.WHITE);
            JPanel p21;
            {
                p21 = new JPanel();
                p21.setPreferredSize(new java.awt.Dimension(0, 0));
                p21.setLayout(new java.awt.GridBagLayout());
                p21.setBackground(Color.WHITE);
                PSpace[] spaces = new PSpace[4];
                jButton = new JButton[4];
                for (int i = 0; i < 4; i++) {
                    jButton[i] = new JButton("Det-" + (i + 1));
                    jButton[i].addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent ae) {

                            Object source = ae.getSource();
                            int i;
                            for (i = 0; i < 4; i++) {
                                if (source == jButton[i]) {
                                    btnidx = i;
                                    break;
                                }
                            }
                            XMLReadWrite xmlReadWrite = new XMLReadWrite("detector table\\table" + (i + 1) + ".xml");

                            String[] mVs = xmlReadWrite.getAllTextValuesByTag("voltage");
                            String[] powers = xmlReadWrite.getAllTextValuesByTag("power");
                            DefaultTableModel dm = new DefaultTableModel(new Object[][]{}, new String[]{"Sr no", "ADC value", "dBm"});
                            for (int j = 0; j < tablelen[i]; j++) {
                                dm.addRow(new Object[]{String.valueOf(j + 1), mVs[j], powers[j]});
                            }
                            jTable1.setModel(dm);
                            jTable1.setRowHeight(25);
                            jTable1.getColumnModel().getColumn(0).setMaxWidth(40);
                        }

                    });
                    spaces[i] = new PSpace(jButton[i], 0.1, 0.1, 0.1, 0.1);
                }

                GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridy = 0;
                gridBagConstraints.gridheight = 1;
                gridBagConstraints.gridwidth = 1;
                gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                gridBagConstraints.weightx = 0.25;
                gridBagConstraints.weighty = 1;
                for (int i = 0; i < 4; i++) {
                    gridBagConstraints.gridx = i;
                    p21.add(spaces[i], gridBagConstraints);
                }

            }
//
            JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
            jTable1 = new javax.swing.JTable();
            DefaultTableModel dm = new DefaultTableModel(new Object[][]{}, new String[]{"Sr no", "ADC value", "dBm"});
//            for(int i=0;i<45;i++){
//                dm.addRow(new Object[]{});
//            }
            jTable1.setModel(dm);
            jTable1.getColumnModel().getColumn(0).setMaxWidth(40);
            jScrollPane1.setViewportView(jTable1);
            jScrollPane1.setPreferredSize(new Dimension(0, 0));
            JButton jb = new JButton("save");
            jb.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    saveTable();
                }
            });
            PSpace btnspace = new PSpace(jb, 0.35, 0.1, 0.35, 0.1);
            GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.gridheight = 1;
            gridBagConstraints.gridwidth = 1;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            gridBagConstraints.weightx = 1;
            gridBagConstraints.weighty = 0.1;
            p2.add(p21, gridBagConstraints);
            gridBagConstraints.gridy = 1;
            gridBagConstraints.weighty = 0.8;
            p2.add(jScrollPane1, gridBagConstraints);
            gridBagConstraints.gridy = 2;
            gridBagConstraints.weighty = 0.1;
            p2.add(btnspace, gridBagConstraints);

        }

        GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 1;
        add(p2, gridBagConstraints);

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

    private void saveTable() {
        if (btnidx == -1) {
            return;
        }
        int rowCount = jTable1.getRowCount();
        String[] mVs = new String[rowCount];
        String[] powers = new String[rowCount];

        for (int i = 0; i < rowCount; i++) {
            mVs[i] = String.valueOf(jTable1.getValueAt(i, 1));
            powers[i] = String.valueOf(jTable1.getValueAt(i, 2));
        }

        XMLReadWrite xmlReadWrite = new XMLReadWrite("detector table\\table" + (btnidx + 1) + ".xml");
        xmlReadWrite.Write("row", "voltage", mVs);
        xmlReadWrite.Write("row", "power", powers);
        xmlReadWrite.printToFile();
    }

    public double getYfromX(double X, int tableno) {
        int L = 0, R = tablelen[tableno]-1;
        while (L < R) {
            int m = (L + R) / 2;
            if (x[tableno][m] < X) {
                L = m + 1;
            } else {
                R = m;
            }
        }
        if (L == 0) {
            return y[tableno][0];
        } else {
            double f = ((double) (X - x[tableno][L - 1])) / (x[tableno][L] - x[tableno][L - 1]);
            double Y = y[tableno][L - 1] + f * (y[tableno][L] - y[tableno][L - 1]);
            return Y;
        }
    }
    public double getXfromY(double Y, int tableno) {
        int length = tablelen[tableno];
        double[] diff=new double[length];
        for(int i=0;i<length;i++){
            diff[i]=Math.abs(Y-y[tableno][i]);            
        }
        double minimum=diff[0];
        int index=0;
        for(int i=1;i<length;i++){
            if(diff[i]<=minimum){
                minimum=diff[i];
                index=i;
            }        
        }
        if (index == 0) {
            return x[tableno][0];
        } else {
            double f = ((double) (Y - y[tableno][index - 1])) / (y[tableno][index] - y[tableno][index - 1]);
            double X = x[tableno][index - 1] + f * (x[tableno][index] - x[tableno][index - 1]);
            return X;
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
