/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * GraphPlot.java
 *
 * Created on Mar 8, 2016, 9:18:34 AM
 */
package Utility;

import Utility.Frame.SH;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.data.time.*;
import org.jfree.chart.axis.*;
import java.awt.*;
import java.io.*;
import java.util.Random;

/**
 *
 * @author Trainee
 */
public class GraphPlotSH extends javax.swing.JFrame implements ActionListener {

    SH statusObject;
    int[] fielIndices;
AnalogParamPlotUpdater updater;
    /** Creates new form GraphPlot
     * @param statusObject
     * @param fieldIndices
     * @param units
     * @param ranges */
    public GraphPlotSH(SH statusObject, int[] fieldIndices, String[] units, int[] min, int[] max) {
        this.statusObject = statusObject;
        this.fielIndices = fieldIndices;
        labels = new String[fieldIndices.length];
        for (int i = 0; i < labels.length; i++) {
            labels[i] = statusObject.fields.get(fieldIndices[i]).getName();
        }
        this.units = units;
        this.min = min;
        this.max = max;
        updater=new AnalogParamPlotUpdater();
        timeTicks = new Timer(100, updater);
        myinitComponents();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        for (int i = 0; i < 4; i++) {
            addplot(i);
            ParamList[i].setSelected(true);
        }
    }

    private void myinitComponents() {
        jPanel1 = new javax.swing.JPanel();
        jPanel1.setName("jPanel1"); // NOI18N
        javax.swing.GroupLayout frameLayout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(frameLayout);
        frameLayout.setHorizontalGroup(
                frameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE));
        frameLayout.setVerticalGroup(
                frameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 983, Short.MAX_VALUE));

        series = new TimeSeries[NoOfPanels];
        datasets = new TimeSeriesCollection[NoOfPanels];

        Panel2FieldIndex = new int[NoOfPanels];
        menuItem2PanelIndex = new int[labels.length];
        GraphPanels = new ChartPanel[NoOfPanels];
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new JMenu();
        jMenu2 = new JMenu();
        jMenu3 = new JMenu();
        freeze = new JCheckBoxMenuItem();
        rollon = new JCheckBoxMenuItem();

        String[] colorItemTexts = {"Title", "XaxisLabel", "YaxisLabel", "Xaxisgrid", "Yaxisgrid", "PlotBackground", "PlotForeground", "PanelBackground"};

        ParamList = new JCheckBoxMenuItem[labels.length];
        colorOptionItems = new JMenuItem[colorItemTexts.length];
        dimensions = new Dimension[NoOfPanels + 1];

        for (int i = 0; i < NoOfPanels; i++) {
            Panel2FieldIndex[i] = EMPTY_PANEL;
            series[i] = new TimeSeries("", Millisecond.class);
            datasets[i] = new TimeSeriesCollection(series[i]);
        }
        for (int i = 0; i < labels.length; i++) {
            ParamList[i] = new JCheckBoxMenuItem();
            ParamList[i].setText(labels[i]);
            ParamList[i].addActionListener(this);
            jMenu1.add(ParamList[i]);
        }

        ColorOptionListener colorOptionListener = new ColorOptionListener();
        for (int i = 0; i < colorOptionItems.length; i++) {
            colorOptionItems[i] = new JMenuItem();
            colorOptionItems[i].setText(colorItemTexts[i]);
            colorOptionItems[i].addActionListener(colorOptionListener);
            jMenu3.add(colorOptionItems[i]);
        }

        dimensions[0] = new Dimension(410, 165);
        for (int i = 1; i <= NoOfPanels; i++) {
            dimensions[i] = new Dimension(410, i * 165);
        }

        jMenuBar1.setName("jMenuBar1"); // NOI18N
        jMenu1.setText("Parameters"); // NOI18N
        jMenuBar1.add(jMenu1);
        freeze.setText("freeze");
        PlotControlListener plotControlListener = new PlotControlListener();
        freeze.addActionListener(plotControlListener);
        jMenu2.add(freeze);
        rollon.setText("let roll on");
        rollon.addActionListener(plotControlListener);
        jMenu2.add(rollon);
        jMenu2.setText("Plot Control"); // NOI18N
        jMenuBar1.add(jMenu2);

        jMenu3.setText("Color Option");
        jMenuBar1.add(jMenu3);
        setJMenuBar(jMenuBar1);
        panelLayout = new java.awt.GridLayout(4, 1);
        jPanel1.setLayout(panelLayout);
        rollon.setSelected(true);
        String fileName = "PlotRelatedColors.xml";
        XMLFileStream = new XMLReadWrite(fileName);
        XMLFileStream.defineRoot("Colors");
        timeTicks.start();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("radarcontroller/Bundle"); // NOI18N
        setTitle(bundle.getString("GraphPlotSH.title")); // NOI18N
        getContentPane().setLayout(null);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new GraphPlotSH(null,new int[]{},new String[]{},new int[]{},new int[]{}).setVisible(true);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MenuItemBuf = (JCheckBoxMenuItem) e.getSource();

        for (int i = 0; i < 14; i++) {
            if (ParamList[i].equals(MenuItemBuf)) {
                if (MenuItemBuf.isSelected()) {
                    if (plotcnt < NoOfPanels) {
                        addplot(i);
                    } else {
                        ParamList[i].setSelected(false);
                        JOptionPane.showMessageDialog(this, "Only " + NoOfPanels + " selections are allowed", "Information", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    removeplot(i);
                }
                break;
            }
        }
    }

    private void addplot(int i) {
        int j;
        for (j = 0; j < NoOfPanels; j++) {
            if (Panel2FieldIndex[j] == EMPTY_PANEL) {
                Panel2FieldIndex[j] = fielIndices[i];
                menuItem2PanelIndex[i] = j;
                plotcnt++;
                break;
            }
        }

        JFreeChart chart = ChartFactory.createTimeSeriesChart(labels[i],
                "Time(msec)", units[i], datasets[j], false, true, false);

        GraphPanels[j] = new ChartPanel(chart);
        chartPaint(j);
        jPanel1.add(GraphPanels[j]);
        panelLayout.setRows(plotcnt);
        getContentPane().setPreferredSize(dimensions[plotcnt]);
        pack();
    }

    private void removeplot(int i) {
        series[menuItem2PanelIndex[i]].clear();
        jPanel1.remove(GraphPanels[menuItem2PanelIndex[i]]);
        plotcnt--;
        panelLayout.setRows(plotcnt);
        getContentPane().setPreferredSize(dimensions[plotcnt]);
        pack();
        Panel2FieldIndex[menuItem2PanelIndex[i]] = EMPTY_PANEL;
        ParamList[i].setSelected(false);
    }

    private Paint getColorByTag(String tag) {
        int rgb = 0x000000;
        String colorvalue = XMLFileStream.getTextByTag(tag);
        if (colorvalue != null) {
            rgb = Integer.parseInt(colorvalue);
        }

        return new Color(rgb);
    }

    private void chartPaint(int i) {
        JFreeChart chart = GraphPanels[i].getChart();
        chart.getTitle().setPaint(getColorByTag(colorOptionItems[0].getText()));
        XYPlot plot = chart.getXYPlot();
        ValueAxis xaxis = plot.getDomainAxis();
        //Domain axis would show data of 60 seconds for a time
        xaxis.setFixedAutoRange(60000.0);  // 60 seconds
        xaxis.setVerticalTickLabels(true);
        xaxis.setLabelPaint(getColorByTag(colorOptionItems[1].getText()));
        ValueAxis yaxis = plot.getRangeAxis();
        yaxis.setRange(min[i], max[i]);
        yaxis.setLabelPaint(getColorByTag(colorOptionItems[2].getText()));
        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(getColorByTag(colorOptionItems[3].getText()));
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(getColorByTag(colorOptionItems[4].getText()));
        plot.setBackgroundPaint(getColorByTag(colorOptionItems[5].getText()));
        plot.getRenderer().setSeriesPaint(0, getColorByTag(colorOptionItems[6].getText()));
        chart.setBackgroundPaint(getColorByTag(colorOptionItems[7].getText()));
    }
    @Override
    public void dispose(){
        timeTicks.stop();
        super.dispose();
    }

    class AnalogParamPlotUpdater implements ActionListener {

        Random rand;
        //int[] value=new int[]{78,20,230,5,5,12};
        public AnalogParamPlotUpdater() {
            rand = new Random();
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            for (int i = 0; i < NoOfPanels; i++) {

                if (Panel2FieldIndex[i] != EMPTY_PANEL) {
                    //double value=rand.nextFloat() * ranges[i];
                    double value = ((SH.A2D)(statusObject.fields.get(Panel2FieldIndex[i]))).getValueWithDecimal();
                    series[i].add(new Millisecond(), value);
                }
            }
        }
    }

    class PlotControlListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource().equals(freeze)) {
                timeTicks.stop();
                rollon.setSelected(false);
            } else {
                timeTicks.start();
                freeze.setSelected(false);
            }
        }
    }

    class ColorOptionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Color c = JColorChooser.showDialog(null, "choose a color", getBackground());
            if (c == null) {
                return;
            }

            int color = c.getRGB();
            for (JMenuItem colorOptionItem : colorOptionItems) {
                if (e.getSource() == colorOptionItem) {
                    XMLFileStream.Write(colorOptionItem.getText(), String.valueOf(color));
                    break;
                }
            }
            XMLFileStream.printToFile();
            for (int i = 0; i < plotcnt; i++) {
                chartPaint(i);
            }
        }
    }
    private javax.swing.JPanel jPanel1;
    private javax.swing.JCheckBoxMenuItem[] ParamList;
    private javax.swing.JCheckBoxMenuItem MenuItemBuf;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JCheckBoxMenuItem freeze;
    private javax.swing.JCheckBoxMenuItem rollon;
    private javax.swing.JMenuItem[] colorOptionItems;
    GridLayout panelLayout;
    private final String[] labels;
    private final String[] units;
    private final int[] min,max;
    private ChartPanel[] GraphPanels;
    public TimeSeries[] series;
    public TimeSeriesCollection datasets[];
    private int[] Panel2FieldIndex;
    private int[] menuItem2PanelIndex;
    private int plotcnt = 0;
    final int EMPTY_PANEL = 15;
    private final Timer timeTicks;
    private Dimension[] dimensions;
    private final int NoOfPanels = 6; // No of Panels
    XMLReadWrite XMLFileStream;
    RandomAccessFile f;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
