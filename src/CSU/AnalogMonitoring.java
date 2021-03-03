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
import Utility.XMLReadWrite;
import java.awt.Color;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author Istrac
 */
public class AnalogMonitoring extends JPanel {

    private final MultiLineText[] labels1;
    private final JLabel[] results1;
    private final MultiLineText[] labels2;
    private final JLabel[] results2;
    private final double[] min, max;
    private final int[] status;

    /**
     * Creates new form AnalogMonitoring
     */
    public AnalogMonitoring() {
        initComponents();
        JPanel p1;
        {
            p1 = new JPanel();
            p1.setPreferredSize(new java.awt.Dimension(0, 0));
            p1.setLayout(new java.awt.GridBagLayout());
            //p1.setBackground(Color.WHITE);
            JLabel heading1 = new JLabel("MODULATOR");
            heading1.setForeground(Color.WHITE);
            heading1.setHorizontalAlignment(SwingConstants.CENTER);
            PSpace headingSpace1 = new PSpace(heading1, 0.3, 0.3, 0.3, 0.3);
            //headingSpace1.setbkgrnd(Color.WHITE);

            heading1.setBackground(new Color(96, 88, 133));
            heading1.setOpaque(true);
            labels1 = new MultiLineText[10];
            PSpace[] labelspaces1 = new PSpace[10];
            results1 = new JLabel[10];
            min = new double[10];
            max = new double[10];
            status = new int[10];
            PSpace[] spaces = new PSpace[10];
            String[] names = new String[10];

            for (int i = 0; i < 8; i++) {
                names[i] = new String();
                labels1[i] = new MultiLineText(names[i]);
                labelspaces1[i] = new PSpace(labels1[i], 0.1, 0.1, 0.1, 0.1);
                labels1[i].setOutlined(false);
                if (i % 2 == 0) {
                    labels1[i].setBackground(new Color(112, 39, 195));
                } else {
                    labels1[i].setBackground(new Color(112, 39, 195));
                }
                labels1[i].setTextanchor(Rectangle.LEFT);
                labels1[i].setFontsize(12);
                labels1[i].rectangle.settextcolor(Color.WHITE);
                results1[i] = new JLabel("xx.xx");
                status[i] = Status.FINE;
                spaces[i] = new PSpace(results1[i], 0.2, 0.1, 0.1, 0.1);

                //spaces[i].setbkgrnd(Color.WHITE);
                results1[i].setBackground(Color.WHITE);
                results1[i].setOpaque(true);
                results1[i].setHorizontalAlignment(SwingConstants.CENTER);
            }
            for (int i = 6; i < 8; i++) {
                labels1[i].setBackground(new Color(238, 238, 238));
                results1[i].setText("");
                results1[i].setBackground(new Color(238, 238, 238));
            }
            GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.gridheight = 1;
            gridBagConstraints.gridwidth = 1;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            gridBagConstraints.weightx = 0.6;
            gridBagConstraints.weighty = 0.1;

            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridwidth = 2;
            gridBagConstraints.weightx = 1;
            gridBagConstraints.weighty = 2 / 10.0;
            p1.add(headingSpace1, gridBagConstraints);
            gridBagConstraints.gridwidth = 1;
            gridBagConstraints.weighty = 1 / 10.0;
            for (int i = 0; i < 8; i++) {
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = i + 1;
                gridBagConstraints.weightx = 0.6;
                p1.add(labelspaces1[i], gridBagConstraints);
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
            JLabel heading2 = new JLabel("EXTERNAL SENSOR");
            heading2.setForeground(Color.WHITE);
            heading2.setHorizontalAlignment(SwingConstants.CENTER);
            PSpace headingSpace2 = new PSpace(heading2, 0.3, 0.3, 0.3, 0.3);
            //headingSpace1.setbkgrnd(Color.WHITE);

            heading2.setBackground(new Color(96, 88, 133));
            heading2.setOpaque(true);
            labels2 = new MultiLineText[8];
            PSpace[] labelspaces2 = new PSpace[10];
            results2 = new JLabel[8];
            PSpace[] spaces = new PSpace[8];
            String[] names = new String[8];//{"R PHASE VOLTAGE", "Y PHASE VOLTAGE", "B PHASE VOLTAGE", "EIK COLLECTOR:TEMPERATURE", "RF FORWARD POWER", " ", " ", " "};

            for (int i = 0; i < 8; i++) {
                names[i] = new String();
                labels2[i] = new MultiLineText(names[i]);
                labelspaces2[i] = new PSpace(labels2[i], 0.1, 0.1, 0.1, 0.1);
                labels2[i].setOutlined(false);
                if (i % 2 == 0) {
                    labels2[i].setBackground(new Color(112, 39, 195));
                } else {
                    labels2[i].setBackground(new Color(112, 39, 195));
                }
                labels2[i].setTextanchor(Rectangle.LEFT);
                labels2[i].setFontsize(12);
                labels2[i].rectangle.settextcolor(Color.WHITE);
                results2[i] = new JLabel("xx.xx");

                spaces[i] = new PSpace(results2[i], 0.2, 0.1, 0.1, 0.1);

                //spaces[i].setbkgrnd(Color.WHITE);
                results2[i].setBackground(Color.WHITE);
                results2[i].setOpaque(true);
                results2[i].setHorizontalAlignment(SwingConstants.CENTER);
                //results[i].setOutlined(false);
            }
            for (int i = 5; i < 8; i++) {
                labels2[i].setBackground(new Color(238, 238, 238));
                results2[i].setText("");
                results2[i].setBackground(new Color(238, 238, 238));
            }
            GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.gridheight = 1;
            gridBagConstraints.gridwidth = 1;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            gridBagConstraints.weightx = 0.6;
            gridBagConstraints.weighty = 0.11;
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridwidth = 2;
            gridBagConstraints.weightx = 1;
            gridBagConstraints.weighty = 2 / 10.0;
            p2.add(headingSpace2, gridBagConstraints);
            gridBagConstraints.gridwidth = 1;
            gridBagConstraints.weighty = 1 / 10.0;
            for (int i = 0; i < 8; i++) {
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = i+1;
                gridBagConstraints.weightx = 0.6;
                p2.add(labelspaces2[i], gridBagConstraints);
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
        b1.setInnerfillColor(new Color(238, 238, 238));
        add(new PSpace(b1, 0.2, 0, 0.0, 0), gridBagConstraints);
        gridBagConstraints.gridx = 1;
        Border b2 = new Border(p2);
        b2.setborderColor(Color.BLUE);
        b2.setThickness(2.0f);
        b2.setInnerfillColor(new Color(238, 238, 238));
        //b2.setInnerfillColor(Color.WHITE);
        add(new PSpace(b2, 0.1, 0, 0.1, 0), gridBagConstraints);
        loadLimits();
    }

    public void setLabel(int panelIndex, int itemIndex, String text) {
        MultiLineText[] p = null;
        if (panelIndex == 0) {
            p = labels1;
        } else if (panelIndex == 1) {
            p = labels2;
        }

        if (p != null) {
            p[itemIndex].rectangle.label.settext(text);
        }
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
//        double val = Double.parseDouble(text);
//        int stat = (val < min[itemIndex] || val > max[itemIndex]) ? Status.ERROR : Status.FINE;
//        setStatus(panelIndex, itemIndex, stat);
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
                break;
            case Status.ERROR:
                p[itemIndex].setBackground(Color.RED);
                break;

            default:
                ;
        }
        repaint();
    }

    public final void loadLimits() {
        XMLReadWrite reader = new XMLReadWrite("limits.xml");
        int[] map = new int[]{0, 1, 6, 5, 8, 9, 2};
        final String[] tags = new String[]{"PRF", "PW", "GridPulseVoltage", "CathodeVoltage", "CollectorCurrent", "HelixCurrent", "FilamentVoltage"};
        for (int i = 0; i < 7; i++) {
            min[map[i]] = Double.parseDouble(reader.getTextByTag(tags[i], "min"));
            max[map[i]] = Double.parseDouble(reader.getTextByTag(tags[i], "max"));
        }
        min[4] = -60;
        max[4] = -10;
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
