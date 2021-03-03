/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CSU;

import GraphicElements.MultiLineText;
import GraphicElements.PSpace;
import GraphicElements.Rectangle;
import Utility.Frame.Status;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author Istrac
 */
public class IntStat extends javax.swing.JPanel {

    private final MultiLineText[] labels;
    private final JLabel[] stats;
    public JButton intReset;

    /**
     * Creates new form IntStat
     * @param num
     */
    public IntStat(int num) {
        initComponents();
        JPanel p1;
        {
            p1 = new JPanel();
            p1.setPreferredSize(new java.awt.Dimension(0, 0));
            p1.setLayout(new java.awt.GridBagLayout());
            p1.setBackground(Color.WHITE);
            JLabel[] columnNames = new JLabel[2];
            String[] names = new String[]{"PARAMETERS", "STATUS"};
            PSpace[] spaces = new PSpace[2];
            for (int i = 0; i < 2; i++) {
                columnNames[i] = new JLabel(names[i]);
                
                spaces[i] = new PSpace(columnNames[i], 0.2, 0.1, 0.2, 0.1);
                spaces[i].setbkgrnd(Color.WHITE);
                columnNames[i].setBackground(new Color(96,88,133));
                columnNames[i].setOpaque(true);
                columnNames[i].setHorizontalAlignment(SwingConstants.CENTER);
                columnNames[i].setForeground(Color.WHITE);
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
            p2.setBackground(Color.WHITE);
            labels = new MultiLineText[num];
            stats = new JLabel[num];
            PSpace[] spaces1 = new PSpace[num];
            PSpace[] labelspaces = new PSpace[num];
            //String[] names = new String[]{"DOOR FAULT", "LVPS FAULT", "EIK BLOWER FAULT", "MODULATOR MAINS:FAULT", "EIK BLOWER", "HV FAULT", "MODULATION ON:FAULT", "ARC DETECTOR:FAULT", "EXCESS DUTY FAULT", "VSWR FAULT","HELIX CURRENT","CATHODE CURRENT", "TEMPERATURE"};
            String[] names = new String[num];
            for (int i = 0; i < num; i++) {
                names[i] = new String();
                labels[i] = new MultiLineText(names[i]);
                
                labelspaces[i]=new PSpace(labels[i], 0.1, 0.1, 0.1, 0.1);
                labelspaces[i].setbkgrnd(Color.WHITE);
                labels[i].setOutlined(false);
                if (i % 2 == 0) {
                    labels[i].setBackground(new Color(112,39,195));
                } else {
                    labels[i].setBackground(new Color(112,39,195));
                }
                labels[i].setFonttype(Font.BOLD);
                labels[i].setFontsize(12);
                labels[i].setTextanchor(Rectangle.LEFT);
                labels[i].rectangle.settextcolor(Color.WHITE);
                stats[i] = new JLabel("NOT OK");

                spaces1[i] = new PSpace(stats[i], 0.3, 0.1, 0.3, 0.1);

                spaces1[i].setbkgrnd(Color.WHITE);

                stats[i].setBackground(Color.red);
                stats[i].setOpaque(true);
                stats[i].setForeground(Color.WHITE);
                stats[i].setHorizontalAlignment(SwingConstants.CENTER);
                stats[i].setFont(stats[i].getFont().deriveFont(Font.BOLD,12.0f));

                //spaces2[i].setbkgrnd(Color.WHITE);
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

            for (int i = 0; i < num; i++) {
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = i;
                gridBagConstraints.weightx = 0.33;
                p2.add(labelspaces[i], gridBagConstraints);
                gridBagConstraints.gridx = 1;
                gridBagConstraints.weightx = 0.33;
                p2.add(spaces1[i], gridBagConstraints);
                gridBagConstraints.gridx = 2;
                gridBagConstraints.weightx = 0.33;
                //p2.add(spaces2[i], gridBagConstraints);
            }
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
        gridBagConstraints.weighty = 0.9;
        add(p2, gridBagConstraints);
        intReset=new JButton("INTERLOCK RESET");
        //intReset.addActionListener(al);
        gridBagConstraints.gridy = 2;
        gridBagConstraints.weighty = 0.1;
        PSpace pSpace = new PSpace(intReset,0.25,0.3,0.25,0.1);pSpace.setbkgrnd(Color.WHITE);
        add(pSpace,gridBagConstraints);
    }
    public void addListener(ActionListener al){
        intReset.addActionListener(al);
    }
    public void setLabel(int itemIndex, String text) {
        labels[itemIndex].rectangle.label.settext(text);
    }

    public void setStatus(int itemIndex, int status, String txt) {

        switch (status) {
            case Status.FINE:
                stats[itemIndex].setBackground(Color.GREEN);
                stats[itemIndex].setForeground(Color.BLACK);
                break;
            case Status.ERROR:
                stats[itemIndex].setBackground(Color.RED);
                stats[itemIndex].setForeground(Color.WHITE);
                break;
                case Status.DONTCARE:
                stats[itemIndex].setBackground(Color.WHITE);
                stats[itemIndex].setForeground(Color.BLACK);
                break;
            default:
                ;
        }
        stats[itemIndex].setText(txt);
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
