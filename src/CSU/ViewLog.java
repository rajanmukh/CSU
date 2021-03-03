/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CSU;

import GraphicElements.PSpace;
import Utility.Frame.Status;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.DefaultCaret;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

/**
 *
 * @author Istrac
 */
public class ViewLog extends javax.swing.JFrame {

    private Calendar c;
    private SimpleDateFormat dateFormat;
    private byte[] buf;
    private SimpleDateFormat timeFormat;
    private ModAnalogStatFrame anlogstatFrame;
    private static ModDigitalInStatFrame digitalstatframe;
    private boolean threadfinished = true;
    private boolean destroy = false;
    private int adatalen;
    private int ddatalen;
    private int skip;

    public class commObject {

        public synchronized void waitForSignal() {
        }

        public synchronized void sendSignal() {
        }
    }
    private JDatePickerImpl jDatePickerImpl;
    private JDatePickerImpl From;
    private JDatePickerImpl To;
    private int[] fileno;

    public class DateLabelFormatter extends AbstractFormatter {

        private SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");

        @Override
        public Object stringToValue(String text) throws ParseException {
            return sdf.parseObject(text);
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                return sdf.format(((Calendar) value).getTime());
            } else {
                return "";
            }
        }
    }
    String dirpath;
    private JScrollPane jScrollPane2;
    private JTextArea jTextArea1;
    private JButton search, next;
    private JTextField noOfsave;
    private JComboBox hour1;
    private JComboBox hour2;
    private Object waitObject = new Object();

    /**
     * Creates new form ViewLog
     */
    public ViewLog(String dirpath) {
        this.dirpath = dirpath;
        initcomponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void initcomponents() {
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        ((DefaultCaret) jTextArea1.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        jScrollPane2.setViewportView(jTextArea1);
        jScrollPane2.setPreferredSize(new Dimension(0, 0));
        c = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        timeFormat = new SimpleDateFormat("hh:mm:ss:SSS a");
        int yyyy = c.get(Calendar.YEAR);
        int mm = c.get(Calendar.MONTH);
        int dd = c.get(Calendar.DAY_OF_MONTH);
        UtilDateModel dateModel1 = new UtilDateModel();
        dateModel1.setDate(yyyy, mm, dd);
        dateModel1.setSelected(true);
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel1 = new JDatePanelImpl(dateModel1, p);
        From = new JDatePickerImpl(datePanel1, new DateLabelFormatter());
        From.setPreferredSize(new Dimension(0, 0));
        UtilDateModel dateModel2 = new UtilDateModel();
        dateModel2.setDate(yyyy, mm, dd);
        dateModel2.setSelected(true);
        JDatePanelImpl datePanel2 = new JDatePanelImpl(dateModel2, p);
        To = new JDatePickerImpl(datePanel2, new DateLabelFormatter());
        To.setPreferredSize(new Dimension(0, 0));
        hour1 = new JComboBox();
        hour1.setPreferredSize(new Dimension(0, 0));
        String[] h1 = new String[24];
        for (int i = 0; i < 24; i++) {
            h1[i] = String.valueOf(i + 1);
        }
        hour1.setModel(new javax.swing.DefaultComboBoxModel(h1));
        hour2 = new JComboBox();
        hour2.setPreferredSize(new Dimension(0, 0));
        String[] h2 = new String[24];
        for (int i = 0; i < 24; i++) {
            h2[i] = String.valueOf(i + 1);
        }
        hour2.setModel(new javax.swing.DefaultComboBoxModel(h2));
        hour2.setSelectedIndex(23);
        search = new JButton("Find");
        search.addActionListener(new ActionListener() {
            private Thread thread;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (threadfinished) {
                    jTextArea1.setText("");
                    thread = new Thread() {
                        @Override
                        public void run() {
                            threadfinished = false;
                            searchAndDisplay();
                            threadfinished = true;
                        }
                    };
                    thread.start();
                } else {
                    destroy = true;
                    nextClicked();
                    jTextArea1.setText("");
                    thread = new Thread() {
                        @Override
                        public void run() {
                            threadfinished = false;
                            searchAndDisplay();
                            threadfinished = true;
                        }
                    };
                    thread.start();
                }
            }
        });

        next = new JButton("Next");
        noOfsave = new JTextField("");
        noOfsave.setHorizontalAlignment(SwingConstants.CENTER);
//        JPanel p1;
//        {
//            p1 = new JPanel();
//            p1.setPreferredSize(new java.awt.Dimension(0, 0));
//            p1.setLayout(new java.awt.GridBagLayout());
//            GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
//            gridBagConstraints.gridy = 0;
//            gridBagConstraints.gridheight = 1;
//            gridBagConstraints.gridwidth = 1;
//            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
//            gridBagConstraints.weightx = 0.33;
//            gridBagConstraints.weighty = 0.5;
//            JLabel label = new JLabel("Day");
//            label.setPreferredSize(new Dimension(0, 0));
//            label.setHorizontalAlignment(SwingConstants.CENTER);
//            gridBagConstraints.gridx = 0;
//            p1.add(label, gridBagConstraints);
//            label = new JLabel("Month");
//            label.setPreferredSize(new Dimension(0, 0));
//            label.setHorizontalAlignment(SwingConstants.CENTER);
//            gridBagConstraints.gridx = 1;
//            p1.add(label, gridBagConstraints);
//            label = new JLabel("Year");
//            label.setPreferredSize(new Dimension(0, 0));
//            label.setHorizontalAlignment(SwingConstants.CENTER);
//            gridBagConstraints.gridx = 2;
//            p1.add(label, gridBagConstraints);
//
//            gridBagConstraints.gridy = 1;
//            gridBagConstraints.gridx = 0;
//            p1.add(new PSpace(day, 0.2, 0.1, 0.2, 0.1), gridBagConstraints);
//            gridBagConstraints.gridx = 1;
//            p1.add(new PSpace(month, 0.2, 0.1, 0.2, 0.1), gridBagConstraints);
//            gridBagConstraints.gridx = 2;
//            p1.add(new PSpace(year, 0.2, 0.1, 0.2, 0.1), gridBagConstraints);
//        }
        JPanel p2;
        {
            p2 = new JPanel();
            p2.setPreferredSize(new java.awt.Dimension(0, 0));
            p2.setLayout(new java.awt.GridBagLayout());
            GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();

            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridheight = 1;
            gridBagConstraints.gridwidth = 1;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            gridBagConstraints.weightx = 1;
            gridBagConstraints.weighty = 0.5;

            gridBagConstraints.gridy = 0;
            p2.add(new PSpace(search, 0.3, 0.15, 0.3, 0.15), gridBagConstraints);
            gridBagConstraints.gridy = 1;
            p2.add(new PSpace(noOfsave, 0.1, 0.1, 0.1, 0.1), gridBagConstraints);
        }
        JPanel p1;
        {
            p1 = new JPanel();
            p1.setPreferredSize(new java.awt.Dimension(0, 0));
            p1.setLayout(new java.awt.GridBagLayout());
            GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridy = 0;
            gridBagConstraints.gridheight = 1;
            gridBagConstraints.gridwidth = 2;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            gridBagConstraints.weightx = 0.33;
            gridBagConstraints.weighty = 0.2;
            JLabel label = new JLabel("From");
            label.setPreferredSize(new Dimension(0, 0));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            gridBagConstraints.gridx = 0;
            p1.add(label, gridBagConstraints);
            label = new JLabel("To");
            label.setPreferredSize(new Dimension(0, 0));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            gridBagConstraints.gridx = 2;
            p1.add(label, gridBagConstraints);
            gridBagConstraints.weighty = 0.3;
            gridBagConstraints.gridy = 1;
            gridBagConstraints.gridx = 0;
            p1.add(new PSpace(From, 0.1, 0.1, 0.1, 0), gridBagConstraints);
            gridBagConstraints.gridx = 2;
            p1.add(new PSpace(To, 0.1, 0.1, 0.1, 0), gridBagConstraints);

            JLabel hourlabel1 = new JLabel("Hour");
            hourlabel1.setPreferredSize(new Dimension(0, 0));
            hourlabel1.setHorizontalAlignment(SwingConstants.CENTER);
            hourlabel1.setVerticalAlignment(SwingConstants.TOP);
            JLabel hourlabel2 = new JLabel("Hour");
            hourlabel2.setPreferredSize(new Dimension(0, 0));
            hourlabel2.setHorizontalAlignment(SwingConstants.CENTER);
            hourlabel2.setVerticalAlignment(SwingConstants.TOP);
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 2;
            gridBagConstraints.weightx = 0.33;
            gridBagConstraints.weighty = 0.3;
            gridBagConstraints.gridwidth = 1;
            p1.add(hourlabel1, gridBagConstraints);
            gridBagConstraints.gridx = 1;
            p1.add(new PSpace(hour1, 0, 0, 0.3, 0.5), gridBagConstraints);
            gridBagConstraints.gridx = 2;
            p1.add(hourlabel2, gridBagConstraints);
            gridBagConstraints.gridx = 3;
            p1.add(new PSpace(hour2, 0, 0, 0.3, 0.5), gridBagConstraints);
        }
        GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.2;
//        getContentPane().add(p1, gridBagConstraints);
//        gridBagConstraints.gridy = 1;
        getContentPane().add(p1, gridBagConstraints);
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 0.2;
        JLabel label = new JLabel("No Of save");
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        getContentPane().add(new PSpace(label, 0.2, 0.5, 0, 0.2), gridBagConstraints);
        gridBagConstraints.gridx = 2;
        gridBagConstraints.weightx = 0.3;
        getContentPane().add(new PSpace(p2, 0.1, 0.1, 0.1, 0.1), gridBagConstraints);
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 0.8;
        getContentPane().add(jScrollPane2, gridBagConstraints);
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextClicked();
            }
        });
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 0.1;
        getContentPane().add(new PSpace(next, 0.4, 0.2, 0.4, 0.1), gridBagConstraints);
        setSize(700, 700);
        buf = new byte[440];
        anlogstatFrame = new ModAnalogStatFrame();
        digitalstatframe = new ModDigitalInStatFrame();
        adatalen = anlogstatFrame.getArr().length;
        ddatalen = digitalstatframe.getArr().length;
    }

    private synchronized void searchAndDisplay() {
        //open file

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dt1 = sdf.format(From.getModel().getValue());
        String dt2 = sdf.format(To.getModel().getValue());
        int date1 = Integer.parseInt(dt1);
        int date2 = Integer.parseInt(dt2);
        if (date2 < date1) {
            JOptionPane.showMessageDialog(From, "End date should be later than start date");
            return;
        }
        File dir = new File(dirpath);
        String[] list = dir.list();
        fileno = new int[list.length];
        for (int i = 0; i < list.length; i++) {
            fileno[i] = Integer.parseInt(list[i].substring(0, 8));
        }
        int index1 = getIndex(date1);
        if (index1 == -1) {
            noOfsave.setText("no result");
            return;
        }
        int index2 = getIndex(date2);
        int totalno = getTotalno(list, index1, index2);
        noOfsave.setText("" + totalno);
        int n = 0;
        for (int i = index1; i <= index2; i++) {
            try {
                RandomAccessFile f = new RandomAccessFile(dirpath + list[i], "r");
                f.seek(skip*44);
                while (true) {
                    
                    int readBytes = f.read(buf);
                    if (readBytes == -1) {
                        break;
                    }
                    int displayedBytes = 0;
                    int index = 0;
                    while (displayedBytes < readBytes) {
                        if ((n >= 0) && (n < totalno)) {
                            display(index, n);
                        }

                        index++;
                        displayedBytes += (8 + adatalen + ddatalen);
                        n++;
                        if ((n % 10 == 0) && (n != totalno)) {
                            wait();
                            if (destroy) {
                                destroy = false;
                                return;
                            }
                        }

                    }
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ViewLog.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ViewLog.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(ViewLog.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //seasrch in the hour interval
        //write
    }

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
            java.util.logging.Logger.getLogger(ViewLog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewLog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewLog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewLog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewLog("General Log/").setVisible(true);
            }
        });
    }

    private int getIndex(int index) {
        for (int i = 0; i < fileno.length; i++) {
            if (index <= fileno[i]) {
                return i;
            }
        }
        return -1;
    }

    private void display(int i, int globalindex) {

        int timeSt = i * (8 + adatalen + ddatalen);
        int dataSt = timeSt + 8;
        long time = ByteBuffer.wrap(buf, timeSt, 8).getLong();
        c.setTimeInMillis(time);
        Date datetime = c.getTime();
        jTextArea1.append("" + (globalindex + 1) + "    ");
        String str = "Date: " + dateFormat.format(datetime) + "\tTime: " + timeFormat.format(datetime);
        jTextArea1.append(str);
        anlogstatFrame.setFromArray(buf, dataSt);
        digitalstatframe.setFromArray(buf, dataSt + adatalen);
        jTextArea1.append("\n\n");
        for (int j = 0; j < anlogstatFrame.fields.size(); j++) {
            jTextArea1.append(anlogstatFrame.fields.get(j).getName() + " : " + anlogstatFrame.fields.get(j).getValueAsString() + "\n");
        }
        jTextArea1.append("\n");
        //digitalstatframe.getStatus();
        for (int j = 0; j < digitalstatframe.fields.size(); j++) {
            String pnct;
            if (j % 3 == 0) {
                pnct = "\n";
            } else {
                pnct = "   ";
            }
            
            jTextArea1.append(pnct + digitalstatframe.fields.get(j).getName() + " : "+digitalstatframe.fields.get(j).getText());            
            
        }
        jTextArea1.append("\n\n\n");
    }

    private synchronized void nextClicked() {
        notify();
    }

    private int getTotalno(String[] list, int index1, int index2) {
        int noWithinLimits = 0;
        int count1 = 0, count2 = 0, totalcount = 0;
        try {
            for (int i = index1; i <= index2; i++) {

                RandomAccessFile f = new RandomAccessFile(dirpath + list[i], "r");
                int len = (int) f.length();
                int nos = len / (8 + adatalen + ddatalen);



                if (nos > 0) {
                    if (i == index1) {
                        while (true) {
                            int readBytes = f.read(buf);
                            if (readBytes == -1) {
                                break;
                            }
                            int nosinChunk = readBytes / (8 + adatalen + ddatalen);
                            int j;
                            int hr = 0;
                            for (j = 0; j < nosinChunk; j++) {
                                int timeSt = j * (8 + adatalen + ddatalen);
                                long time = ByteBuffer.wrap(buf, timeSt, 8).getLong();
                                c.setTimeInMillis(time);
                                hr = c.get(Calendar.HOUR_OF_DAY);
                                int selectedIndex = hour1.getSelectedIndex();
                                if (hr > hour1.getSelectedIndex()) {
                                    break;
                                } else {
                                    count1++;
                                }
                            }
                            if (hr > hour1.getSelectedIndex()) {
                                break;
                            }
                        }
                        skip = count1;
                    }
                    if (i == index2) {
                        f.seek(0);
                        while (true) {
                            int readBytes = f.read(buf);
                            if (readBytes == -1) {
                                break;
                            }
                            int nosinChunk = readBytes / (8 + adatalen + ddatalen);
                            int j;
                            int hr = 25;
                            for (j = 0; j < nosinChunk; j++) {
                                int timeSt = j * (8 + adatalen + ddatalen);
                                long time = ByteBuffer.wrap(buf, timeSt, 8).getLong();
                                c.setTimeInMillis(time);
                                hr = c.get(Calendar.HOUR_OF_DAY);
                                int selectedIndex = hour2.getSelectedIndex();
                                if (hr <= hour2.getSelectedIndex()) {
                                    count2++;
                                } else {
                                    break;
                                }

                            }

                            if (hr > hour2.getSelectedIndex() || count2 == nos) {
                                break;
                            }
                        }
                        count2 = nos - count2;
                    }
                }
                totalcount += nos;
            }
            noWithinLimits = totalcount - count1 - count2;
            if (noWithinLimits == 0) {
                noOfsave.setText("no result");
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ViewLog.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ViewLog.class.getName()).log(Level.SEVERE, null, ex);
        }
        return noWithinLimits;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
