/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CSU;

import GraphicElements.Border;
import GraphicElements.MultiLineText;
import GraphicElements.MyButton;
import GraphicElements.PSpace;
import GraphicElements.Photoframe;
import GraphicElements.Rectangle;
import Utility.Frame.Command;
import Utility.Frame.SH;
import Utility.Frame.Status;
import Utility.GraphPlotSH;
import Utility.InputKeyListener;
import Utility.Time;
import Utility.XMLReadWrite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowListener;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;

/**
 *
 * @author Istrac
 */
public class MainFrame extends javax.swing.JFrame {

    private PSpace title, name1, name2, name3, name4;
    private Border b1, b2, b3;
    private JPanel p1, p2, p3, lower;
    BlockDiag bd;
    private JLabel time;
    private JLabel date;
    private final MultiLineText desc;
    private boolean CntNCmdvflag;
    private boolean Monitoringvflag;
    private boolean[] buttonflags;
    private JButton navigation;
    private MonitoringSelectionPanel mselpanel;
    private final FlowStatus flowStatus;
    private JPanel[] mpanels;
    private final AnalogMonitoring analogMonitoring;
    private final TxStatus txStatus;
    private final IntStat intStat1;
    private final InterlockMsg interlockMsg;
    private final TrigDelay trigDelay;
    private final CalTable calTable1;
    private final IntByp intByp1;
    private final Bite bite;
    private final PSpace bitedetailframe;
    private final JPanel bitedetail;
    private JButton subnav;
    private JCheckBox chk2;
    private JCheckBox chk1;
    boolean intChk = true;
    boolean extChk = true;
    private JTextField prfchoose;
    private JTextField pwchoose;
    double prf, pw;
    private JRadioButton[] prfbtns;
    private JRadioButton[] pwbtns;
    private JLabel dutyratioAlarm;
    private JPanel pgroup;
    private MyButton config;
    private MyButton cancel;
    private ButtonGroup group1;
    private ButtonGroup group2;
    javax.swing.border.Border normalborder;
    ModCtrlFrame analogctrlframe;
    ModCtrlFrame digitalctrlframe;
    SenseElectronicsFormat senseFrame;
    private Socket socketM;
    private Socket socketS;
    private DataOutputStream outM;
    private DataInputStream inputM;
    boolean editEnabled = false;
    private JTextField CVtext;
    private JTextField BVtext;
    private JTextField FVtext;
    private JTextField warmUPTimeText;
    private double CV;
    private double BV;
    private double FV;
    private int warmUPTime;
    private double FVLimit, CVLimit, BVLimit;
    private boolean prfpwset = false;
    private JButton[] ons;
    private JButton[] offs;
    private final ModStatFrame analogstat;
    private MultiLineText connstat;
    private MyButton[] p1btns;
    private MyButton manual;
    private MyButton autoON;
    private JButton intReset;
    private JLabel[] p3results;
    private boolean configDone = false;
//    private final Timer wdgTimer;
    private byte[] bufM = new byte[49];
    private byte[] bufS = new byte[200];
    byte[] packetM;
    private volatile boolean autoONinprogress;
    private volatile boolean autoOFFinprogress;
    private boolean[] onclicked;
    private boolean[] offclicked;
    private int[] OnOffstat;
    private int autobtnindex;
    private MyButton autoOFF;
    private JLabel modConn;
    private JLabel senseConn;
    private boolean modulatorConnected;
    private boolean senseElConnected;
    private DataInputStream inputS;
    private DataOutputStream outS;
    private final DisplayFrame displayFrame;
    private final Timer modstatReqTimer;
    private final Timer sensestatReqTimer;
    private final Timer intResetTimer;
    private final Timer mainsONTimer;
    ModStatReqFrame modStatReqFrame;
    private final ModStatFrame digitalInputstat;
    private MTSGcommandFrame mtsgcommand;
    private MTSGcommandFrame senseAck;
    private ActionListener resetListener;
    private final MTSGStatusFormat senseStatus;
    private boolean plotOpen;
    private GraphPlotSH plot;
    private WindowListener plotWindowListener;
    private final Timer autoONTimer, autoOFFTimer, manualTimer;
    private final Timer checkDelayTimer;
    private int[] autoONdelays = new int[]{0, 26000, 10000, 1000};
    private int[] autoOFFdelays = new int[]{2000, 2000, 2000, 0};
    private int currentStage = -1;
    private final int MAINS = 0, HV = 1, MOD = 2, RF = 3;
    private int FVfaultCount = 0, CVfaultCount = 0, BVfaultCount = 0;
    private final IntStat intStat2;
    private int[] map1 = new int[]{7, 8, 10, 9, 13, 12, 4};
    private int[] map2 = new int[]{10, 13, 12, 11, 7, 9, 8, 6, 5};
    private final IntByp intByp2;
    private final IntByp intByp3;
    private double VSWRLIMIT;
    private boolean VSWRinterlockset = false;
    private boolean intSelected = false;
    private boolean extSelected = true;
    private int[] modFieldMap = new int[]{32, 38, 34, 33, 37, 39};
    private final JButton stopButton;
    private boolean otg = true;

    /**
     * Creates new form NewJFrame
     */
    public MainFrame() {
        initComponents();
        desc = new MultiLineText("CONTROL AND COMMAND");
        desc.setFontsize(14);
        desc.rectangle.setFillcolor(Color.MAGENTA);
        desc.rectangle.settextcolor(Color.WHITE);
        title = new PSpace(desc, 0.05, 0.25, 0.66, 0.25);

        stopButton = new JButton("Emergency Stop");
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendEmergencyStop();
            }
        });
        stopButton.setBackground(Color.YELLOW);
        PSpace emStop = new PSpace(stopButton, 0.25, 0.30, 0.25, 0.30);
        p1 = new JPanel();
        addp1comps();
        b1 = new Border(p1);
        b1.setborderColor(Color.BLUE);
        b1.setThickness(2.0f);
        b1.setInnerfillColor(Color.WHITE);

        p2 = new JPanel();
        addp2comps();
        b2 = new Border(p2);
        b2.setborderColor(Color.BLUE);
        b2.setThickness(2.0f);
        b2.setInnerfillColor(Color.WHITE);

        p3 = new JPanel();
        addp3comps();
        b3 = new Border(p3);
        b3.setborderColor(Color.BLUE);
        b3.setThickness(2.0f);
        //b3.setOuterfillColor(Color.YELLOW);
        b3.setInnerfillColor(Color.WHITE);
        interlockMsg = new InterlockMsg();

        bd = new BlockDiag();

        MultiLineText comp1 = new MultiLineText("CONFIGURATIONS");
        comp1.setFontsize(13);
        //comp1.setFonttype(Font.BOLD);
        comp1.rectangle.setStroke(0.0f);
        comp1.setOutlined(true);
        //comp1.setBackground(Color.yellow);
        name1 = new PSpace(comp1, 0.1, 0.1, 0.1, 0.2);

        comp1 = new MultiLineText("OPERATIONS");
        comp1.setFontsize(13);
        //comp1.setFonttype(Font.PLAIN);
        comp1.rectangle.setStroke(0.0f);
        comp1.setOutlined(true);
        name2 = new PSpace(comp1, 0.1, 0.1, 0.1, 0.2);

        comp1 = new MultiLineText("PRE-REQUISITE :VERIFICATION");
        comp1.setFontsize(13);
        //comp1.setFonttype(Font.PLAIN);
        comp1.rectangle.setStroke(0.0f);
        comp1.setOutlined(true);
        name3 = new PSpace(comp1, 0.1, 0.1, 0.1, 0.2);

        //comp=new JLabel(); 
        comp1 = new MultiLineText("PRESENT STAGE ON:FLOW DIAGRAM");
        comp1.setFontsize(13);
        //comp1.setFonttype(Font.PLAIN);
        comp1.rectangle.setStroke(0.0f);
        comp1.setOutlined(true);
        name4 = new PSpace(comp1, 0.1, 0.1, 0.1, 0.2);

        lower = new JPanel();
        addplowercomps();

//        Border brd=new Border(bd);
//        brd.setColor(Color.red);
//        brd.setThickness(2.0f);
        datetimeupdate.start();
        validate();
        mselpanel = new MonitoringSelectionPanel();
        mselpanel.setPreferredSize(new Dimension(0, 0));
        mpanels = new JPanel[8];

        mpanels[0] = flowStatus = new FlowStatus();

        mpanels[1] = analogMonitoring = new AnalogMonitoring();

        mpanels[2] = txStatus = new TxStatus();

        bite = new Bite();
        bite.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
        mpanels[3] = new PSpace(bite, 0, 0, 0.1, 0);
        bitedetail = new JPanel();
        bitedetail.setLayout(new GridBagLayout());
        bitedetailframe = new PSpace(bitedetail, 0.3, 0, 0.3, 0);
        for (int i = 0; i < bite.buttons.length; i++) {
            bite.buttons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    replacesubpanel(e);
                }
            });
        }

        JPanel intstatpanel = new JPanel();
        intstatpanel.setLayout(new GridBagLayout());
        intStat1 = new IntStat(10);
        intStat1.addListener(resetListener);
        intStat1.setBackground(Color.WHITE);
        Border bis1 = new Border(intStat1);
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 1;
        intstatpanel.add(bis1, gridBagConstraints);
        intStat2 = new IntStat(9);
        intStat2.intReset.setVisible(true);
        intStat2.addListener(resetListener);
        intStat2.setBackground(Color.WHITE);
        Border bis2 = new Border(intStat2);
        gridBagConstraints.gridx = 1;
        intstatpanel.add(bis2, gridBagConstraints);
        mpanels[4] = new PSpace(intstatpanel, 0.05, 0, 0.05, 0);

        JPanel intbyppanel = new JPanel();
        intbyppanel.setLayout(new GridBagLayout());
        intByp1 = new IntByp(8);
        intByp1.eraseColumn();
        intByp1.setBackground(Color.WHITE);
        Border bib1 = new Border(intByp1);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.weightx = .25;
        gridBagConstraints.weighty = 0.9;
        intbyppanel.add(bib1, gridBagConstraints);
        intByp2 = new IntByp(8);
        intByp2.rearrangeCheckBoxes();
        intByp2.setBackground(Color.WHITE);
        XMLReadWrite reader = new XMLReadWrite("limits.xml");

        Border bib2 = new Border(intByp2);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.weightx = .375;
        intbyppanel.add(bib2, gridBagConstraints);
        intByp3 = new IntByp(8);
        intByp3.setBackground(Color.WHITE);

        String fvlimittext = reader.getTextByTag("FVLimit");
        String cvlimittext = reader.getTextByTag("CVLimit");
        String bvlimittext = reader.getTextByTag("BVLimit");
        FVLimit = Double.parseDouble(fvlimittext);
        CVLimit = Double.parseDouble(cvlimittext);
        BVLimit = Double.parseDouble(bvlimittext);
        intByp3.limit[0].setText(fvlimittext);
        intByp3.limit[1].setText(cvlimittext);
        intByp3.limit[2].setText(bvlimittext);
        Border bib3 = new Border(intByp3);
        gridBagConstraints.gridx = 2;
        gridBagConstraints.weightx = .375;
        intbyppanel.add(bib3, gridBagConstraints);
        PSpace bypsave;
        {
            JButton button = new JButton("SAVE");
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    //for int byp1
                    XMLReadWrite writer = new XMLReadWrite("interlockconfig.xml");
                    String[] tags = {"DIL", "CTIL", "BIL", "ADIL"};
                    for (int i = 0; i < 4; i++) {
                        if (intByp1.chks[i].isSelected()) {
                            senseFrame.intbypass1.setBit(5 - i);
                            writer.Write(tags[i], "1");
                        } else {
                            senseFrame.intbypass1.resetBit(5 - i);
                            writer.Write(tags[i], "0");
                        }
                    }

                    //for int byp2
                    String[] tags2 = {"PRESSURE", "TEMP", "VSWR", "INPUT", "OUTPUT"};
                    for (int i = 0; i < 5; i++) {
                        if (intByp2.chks[i].isSelected()) {
                            senseFrame.intbypass2.setBit(i);
                            writer.Write(tags2[i], "1");
                        } else {
                            senseFrame.intbypass2.resetBit(i);
                            writer.Write(tags2[i], "0");
                        }
                    }
                    double[] hardlimits = {24, 18, 100, 2, 13, 7, 64.88, 63.88};
                    double[] byp2limits = new double[8];
                    boolean[] limexceeded = new boolean[8];
                    //for int byp3
                    String fvaltext = intByp3.limit[0].getText();
                    String cvaltext = intByp3.limit[1].getText();
                    String bvaltext = intByp3.limit[2].getText();
                    try {
                        for (int i = 0; i < 8; i++) {
                            byp2limits[i] = Double.parseDouble(intByp2.limit[i].getText());
                        }
                        limexceeded[0] = byp2limits[0] > hardlimits[0];
                        limexceeded[1] = byp2limits[1] < hardlimits[1];
                        limexceeded[2] = byp2limits[2] > hardlimits[2];
                        limexceeded[3] = byp2limits[3] > hardlimits[3];
                        limexceeded[4] = byp2limits[4] > hardlimits[4];
                        limexceeded[5] = byp2limits[5] < hardlimits[5];
                        limexceeded[6] = byp2limits[6] > hardlimits[6];
                        limexceeded[7] = byp2limits[7] < hardlimits[7];
                        boolean overall = false;
                        for (int i = 0; i < 8; i++) {
                            overall = overall || limexceeded[i];
                        }
                        boolean[] inconcistencies = new boolean[3];
                        inconcistencies[0] = byp2limits[0] <= byp2limits[1];
                        inconcistencies[1] = byp2limits[4] <= byp2limits[5];
                        inconcistencies[2] = byp2limits[6] <= byp2limits[7];
                        overall = overall || inconcistencies[0] || inconcistencies[1] || inconcistencies[2];
                        double fval = Double.parseDouble(fvaltext);
                        double cval = Double.parseDouble(cvaltext);
                        double bval = Double.parseDouble(bvaltext);
                        boolean flimexceeded = fval > 1.2;//hard limits
                        boolean climexceeded = cval > 0.5;//hard limits
                        boolean blimexceeded = bval > 5;//hard limits

                        String[] messages = {"Pressure upper limit is too high", "Pressure lower limit is too low",
                            "Temperature upper limit is too high", "VSWR upper limit is too high",
                            "Input power upper limit is too high", "Input power lower limit is too low",
                            "Output power upper limit is too high", "Output power lower limit is too low"};
                        for (int i = 0; i < 8; i++) {
                            if (limexceeded[i]) {
                                JOptionPane.showMessageDialog(null, messages[i]);
                                break;
                            }
                        }
                        String[] messages1 = {"Pressure", "Input power", "Output power"};
                        for (int i = 0; i < 3; i++) {
                            if (inconcistencies[i]) {
                                JOptionPane.showMessageDialog(null, messages1[i] + " upper limit should be greater than lower limit");
                                break;
                            }
                        }

                        if (flimexceeded) {
                            JOptionPane.showMessageDialog(null, "Filament voltage variation limit should be within 1.2");
                        }
                        if (climexceeded) {
                            JOptionPane.showMessageDialog(null, "Cathode voltage variation limit should be within 0.5");
                        }
                        if (blimexceeded) {
                            JOptionPane.showMessageDialog(null, "Beam voltage variation limit should be within 5");
                        }
                        overall = overall || flimexceeded || climexceeded || blimexceeded;
                        if (!overall) {
                            //save in current variables for immediate use
                            FVLimit = fval;
                            CVLimit = cval;
                            BVLimit = bval;
                            String txt = intByp3.chks[0].isSelected() ? "1" : "0";
                            writer.Write("FVV", txt);
                            txt = intByp3.chks[1].isSelected() ? "1" : "0";
                            writer.Write("CVV", txt);
                            txt = intByp3.chks[2].isSelected() ? "1" : "0";
                            writer.Write("BVV", txt);
                            writer.printToFile();
                            //save in the text file for session persistence
                            writer = new XMLReadWrite("limits.xml");
                            writer.Write("FVLimit", fvaltext);
                            writer.Write("CVLimit", cvaltext);
                            writer.Write("BVLimit", bvaltext);
                            tags = new String[]{"PRESSUREHIGH", "PRESSURELOW", "TEMPHIGH", "VSWRHIGH", "INPUTHIGH", "INPUTLOW", "OUTPUTHIGH", "OUTPUTLOW"};
                            for (int i = 0; i < 8; i++) {
                                writer.Write(tags[i], String.valueOf(byp2limits[i]));
                            }
                            writer.printToFile();
                            senseFrame.pressurehigh.setValueWithDecimal(byp2limits[0]);
                            senseFrame.pressurelow.setValueWithDecimal(byp2limits[1]);
                            double mv0 = calTable1.getXfromY(byp2limits[2], 3);
                            senseFrame.temphigh.setValueWithDecimal(mv0);
                            senseFrame.VSWRhigh.setValueWithDecimal(byp2limits[3]);
                            VSWRLIMIT = senseFrame.VSWRhigh.getValueWithDecimal();
                            double mv1 = calTable1.getXfromY(byp2limits[4], 0);
                            senseFrame.inpwrhigh.setValueWithDecimal(mv1);
                            double mv2 = calTable1.getXfromY(byp2limits[5], 0);
                            senseFrame.inpwrlow.setValueWithDecimal(mv2);
                            double mv3 = calTable1.getXfromY(byp2limits[6], 1);
                            senseFrame.outpwrhigh.setValueWithDecimal(mv3);
                            double mv4 = calTable1.getXfromY(byp2limits[7], 1);
                            senseFrame.outpwrlow.setValueWithDecimal(mv4);

                            writer = new XMLReadWrite("senseElParams.xml");

                            int offset = senseFrame.intbypass1.fieldindex;
                            for (int i = offset; i < offset + 12; i++) {
                                writer.Write(senseFrame.field[i].getName(), senseFrame.field[i].getValueAsString());
                            }
                            writer.printToFile();

                            byte[] packetS = senseFrame.getPacket();
                            sendtoSenseElectronics(packetS);
                            System.out.println(packetS[80]);
                        }

                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Input valid number");
                    }
                }
            });
            bypsave = new PSpace(button, 0.40, 0.25, 0.50, 0.25);
        }
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.gridwidth = 3;
        intbyppanel.add(bypsave, gridBagConstraints);
        mpanels[5] = new PSpace(intbyppanel, 0.05, 0, 0.05, 0);

        calTable1 = new CalTable();
        calTable1.setBackground(Color.WHITE);
        Border bct = new Border(calTable1);
        mpanels[6] = new PSpace(bct, 0.1, 0, 0.5, 0);

        trigDelay = new TrigDelay();
        trigDelay.setBackground(Color.WHITE);
        Border btd = new Border(trigDelay);
        mpanels[7] = new PSpace(btd, 0.1, 0, 0.4, 0);

        buttonflags = new boolean[8];

        for (int i = 0; i < 6; i++) {
            final int bindex = i;
            mselpanel.selectButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Monitoringvflag = false;
                    for (int k = 0; k < 6; k++) {
                        buttonflags[k] = (k == bindex);
                    }
                    getContentPane().remove(mselpanel);
                    applyvflags();
                }
            });
        }
        gridBagConstraints = new java.awt.GridBagConstraints();

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.7;
        gridBagConstraints.weighty = 0.15;
        getContentPane().add(title, gridBagConstraints);
        gridBagConstraints.gridx = 3;
        gridBagConstraints.weightx = 0.3;
        gridBagConstraints.gridwidth = 1;
        getContentPane().add(emStop, gridBagConstraints);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 0.15;
        getContentPane().add(lower, gridBagConstraints);

        CntNCmdvflag = true;
        applyvflags();
        //panelenable(false);
        setSize(1200, 700);
        normalborder = new JTextField().getBorder();
//        wdgTimer = new Timer(600, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                p1btns[1].setBackground(Color.RED);
//                p1btns[0].setBackground(Color.GREEN);
//            }
//        });
        try {
            Log.Initialize();
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        modstatReqTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statreqActionPerformed(null);
            }
        });
        sensestatReqTimer = new Timer(330, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendStatReq2SenseActionPerformed(null);
            }
        });
        intResetTimer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendReset(Status.OFF);
                intReset.setBackground(new Color(238, 238, 238));
            }
        });
        intResetTimer.setRepeats(false);
        mainsONTimer = new Timer(6000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetListener.actionPerformed(null);
            }
        });
        mainsONTimer.setRepeats(false);
        modStatReqFrame = new ModStatReqFrame();
        ModStatFrame frame = new ModStatFrame();
        analogstat = frame;
        digitalInputstat = frame;
        senseStatus = new MTSGStatusFormat();
        ModCtrlFrame modCtrlFrame = new ModCtrlFrame();
        packetM = modCtrlFrame.getPacket();
        analogctrlframe = modCtrlFrame;
        digitalctrlframe = modCtrlFrame;
        digitalctrlframe.setInt();//to match the initial default selection
//        digitalctrlframe.setRemote();
        senseFrame = new SenseElectronicsFormat();
        senseFrame.loadFromFile("senseElParams.xml");
        VSWRLIMIT = senseFrame.VSWRhigh.getValueWithDecimal();

        for (int i = 0; i < 6; i++) {
            analogMonitoring.setLabel(0, i, analogstat.fields.get(modFieldMap[i]).getName());
        }
        for (int i = 0; i < 7; i++) {
            intStat1.setLabel(i, digitalInputstat.fields.get(map1[i]).getName());
        }
        intStat1.setLabel(7, "FILAMENT VOLTAGE VARIATION");
        intStat1.setLabel(8, "CATHODE VOLTAGE VARIATION");
        intStat1.setLabel(9, "BEAM ON VOLTAGE VARIATION");
        for (int i = 0; i < 4; i++) {
            intStat2.setLabel(i, senseStatus.fields.get(map2[i]).getName());
        }
        intStat2.setLabel(4, "WAVEGUIDE PRESSURE");
        intStat2.setLabel(5, "COLLECTOR TEMPERATURE");
        intStat2.setLabel(6, "VSWR");
        intStat2.setLabel(7, "RF INPUT POWER");
        intStat2.setLabel(8, "RF OUTPUT POWER");
        for (int i = 0; i < 4; i++) {
            intStat2.setLabel(i, senseStatus.fields.get(map2[i]).getName());
        }
        for (int i = 0; i < 5; i++) {
            analogMonitoring.setLabel(1, i, senseStatus.fields.get(i).getName());
        }
        for (int i = 15; i < 23; i++) {
            //intStat1.setLabel(i, senseStatus.fields.get(i - 10).getName());
        }

        intByp1.setheading("EXTERNAL:SENSOR");
        intByp1.setLabel(0, "DOOR");
        intByp1.setLabel(1, "COLLECTOR:THERMAL SWITCH");
        intByp1.setLabel(2, "BLOWER");
        intByp1.setLabel(3, "ARC DETECTOR");
        intByp1.setLabel(4, "");
        intByp1.setLabel(5, "");
        intByp1.setLabel(6, "");
        intByp1.setLabel(7, "");
        reader = new XMLReadWrite("interlockconfig.xml");
        boolean chkstat = reader.getTextByTag("DIL").equalsIgnoreCase("1");
        intByp1.chks[0].setSelected(chkstat);
        chkstat = reader.getTextByTag("CTIL").equalsIgnoreCase("1");
        intByp1.chks[1].setSelected(chkstat);
        chkstat = reader.getTextByTag("BIL").equalsIgnoreCase("1");
        intByp1.chks[2].setSelected(chkstat);
        chkstat = reader.getTextByTag("ADIL").equalsIgnoreCase("1");
        intByp1.chks[3].setSelected(chkstat);
        for (int i = 4; i < 8; i++) {
            intByp1.chks[i].setVisible(false);
        }
        chkstat = reader.getTextByTag("PRESSURE").equalsIgnoreCase("1");
        intByp2.chks[0].setSelected(chkstat);
        chkstat = reader.getTextByTag("TEMP").equalsIgnoreCase("1");
        intByp2.chks[1].setSelected(chkstat);
        chkstat = reader.getTextByTag("VSWR").equalsIgnoreCase("1");
        intByp2.chks[2].setSelected(chkstat);
        chkstat = reader.getTextByTag("INPUT").equalsIgnoreCase("1");
        intByp2.chks[3].setSelected(chkstat);
        chkstat = reader.getTextByTag("OUTPUT").equalsIgnoreCase("1");
        intByp2.chks[4].setSelected(chkstat);

        intByp2.setheading("EXTERNAL:SENSOR");
        intByp2.setLabel(0, "PRESSURE:HIGH LIMIT");
        intByp2.setLabel(1, "PRESSURE:LOW LIMIT");
        intByp2.setLabel(2, "COLLECTOR:TEMPERATURE:HIGH LIMIT");
        intByp2.setLabel(3, "VSWR:HIGH LIMIT");
        intByp2.setLabel(4, "RF INPUT:HIGH LIMIT");
        intByp2.setLabel(5, "RF INPUT:LOW LIMIT");
        intByp2.setLabel(6, "RF OUTPUT:HIGH LIMIT");
        intByp2.setLabel(7, "RF OUTPUT:LOW LIMIT");
        reader = new XMLReadWrite("limits.xml");
        String[] tags = new String[]{"PRESSUREHIGH", "PRESSURELOW", "TEMPHIGH", "VSWRHIGH", "INPUTHIGH", "INPUTLOW", "OUTPUTHIGH", "OUTPUTLOW"};

        for (int i = 0; i < 8; i++) {
            intByp2.limit[i].setText(reader.getTextByTag(tags[i]));
        }

        reader = new XMLReadWrite("interlockconfig.xml");
        intByp3.setheading("EIK:MODULATOR");
        intByp3.setLabel(0, "FILAMENT VOLTAGE:VARIATION");
        intByp3.setLabel(1, "CATHODE VOLTAGE:VARIATION");
        intByp3.setLabel(2, "BEAM ON VOLTAGE:VARIATION");
        intByp3.setLabel(3, "");
        intByp3.setLabel(4, "");
        intByp3.setLabel(5, "");
        intByp3.setLabel(6, "");
        intByp3.setLabel(7, "");
        chkstat = reader.getTextByTag("FVV").equalsIgnoreCase("1");
        intByp3.chks[0].setSelected(chkstat);
        chkstat = reader.getTextByTag("CVV").equalsIgnoreCase("1");
        intByp3.chks[1].setSelected(chkstat);
        chkstat = reader.getTextByTag("BVV").equalsIgnoreCase("1");
        intByp3.chks[2].setSelected(chkstat);
        for (int i = 3; i < 8; i++) {
            intByp3.chks[i].setVisible(false);
            intByp3.limit[i].setVisible(false);
        }

        mtsgcommand = new MTSGcommandFrame();
        senseAck = new MTSGcommandFrame();
        plotWindowListener = new WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
                plotOpen = false;
                plot.dispose();
            }
        };
        autoONTimer = new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (autoONinprogress && !autoOFFinprogress) {
                    btnPressed(autobtnindex, Command.ON);
                }
            }
        });
        autoONTimer.setRepeats(false);
        autoOFFTimer = new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnPressed(autobtnindex, Command.OFF);
            }
        });
        autoOFFTimer.setRepeats(false);

        manualTimer = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        manualTimer.setRepeats(false);
        checkDelayTimer = new Timer(30000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        checkDelayTimer.setRepeats(false);
        //// temporary
        displayFrame = new DisplayFrame();
        Dimension fsize = displayFrame.getSize();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        displayFrame.setLocation(screenSize.width - fsize.width - 50, screenSize.height - fsize.height - 50);
        displayFrame.setVisible(true);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenuItem15 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        statreq = new javax.swing.JMenuItem();
        sendStatReq2Sense = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Cloud Radar CSU");
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jMenu1.setText("Tools");

        jMenuItem4.setText("Connect");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuItem5.setText("Disconnect");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem5);

        jMenuItem8.setText("Enable edit Voltages");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem8);

        jMenu4.setText("Configure Limits");

        jMenuItem6.setText("Modulator");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem6);

        jMenu1.add(jMenu4);

        jMenuItem1.setText("MTSG Parameters");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem7.setText("stop mtsg");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopMTSG(evt);
            }
        });
        jMenu1.add(jMenuItem7);

        jMenuItem9.setText("start mtsg");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startMTSG(evt);
            }
        });
        jMenu1.add(jMenuItem9);

        jMenuItem10.setText("download MTSG parameters");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downloadMTSG(evt);
            }
        });
        jMenu1.add(jMenuItem10);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Log");

        jMenuItem2.setText("General Log");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuItem3.setText("Error Log");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);

        jMenuBar1.add(jMenu2);

        jMenu5.setText("Plot");

        jMenuItem14.setText("current");
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem14);

        jMenuItem15.setText("from archive");
        jMenuItem15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem15ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem15);

        jMenuBar1.add(jMenu5);

        jMenu3.setText("test");

        statreq.setText("statreq");
        statreq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statreqActionPerformed(evt);
            }
        });
        jMenu3.add(statreq);

        sendStatReq2Sense.setText("stat req sense");
        sendStatReq2Sense.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendStatReq2SenseActionPerformed(evt);
            }
        });
        jMenu3.add(sendStatReq2Sense);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        try {
            // TODO add your handling code here:
            if (modulatorConnected) {
                socketM.close();
                modulatorConnected = false;
                modConn.setText("NOT CONNECTED TO MODULATOR");
                modConn.setForeground(Color.RED);
            }
            if (senseElConnected) {
                socketS.close();
                senseElConnected = false;
                senseConn.setText("NOT CONNECTED TO SENSOR ELECTRONICS");
                senseConn.setForeground(Color.RED);
            }
            if (!senseElConnected || !modulatorConnected) {
                panelenable(false);
            }
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        final JFrame f = new JFrame();
        f.setLayout(new GridBagLayout());
        JLabel heading = new JLabel("Connect to CSU & Modulator");
        heading.setFont(heading.getFont().deriveFont(Font.BOLD, 14));
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setPreferredSize(new Dimension(0, 0));
        final int num = 4;
        JLabel[] labels = new JLabel[num];
        PSpace[] spaces1 = new PSpace[num];
        final JTextField[] vals = new JTextField[num];
        final XMLReadWrite reader = new XMLReadWrite("conf.xml");
        String ipaddrM = reader.getTextByTag("IPAddressM");
        int portnoM = Integer.parseInt(reader.getTextByTag("portnoM"));
        String ipaddrS = reader.getTextByTag("IPAddressS");
        int portnoS = Integer.parseInt(reader.getTextByTag("portnoS"));

        labels[0] = new JLabel(" " + "Modulator IP");
        labels[1] = new JLabel(" " + "Modulator port no");
        labels[2] = new JLabel(" " + "CSU IP");
        labels[3] = new JLabel(" " + "CSU port no");

        vals[0] = new JTextField(ipaddrM);
        vals[1] = new JTextField(String.valueOf(portnoM));
        vals[2] = new JTextField(ipaddrS);
        vals[3] = new JTextField(String.valueOf(portnoS));

        vals[0].setInputVerifier(new IPverifier());
        vals[1].addKeyListener(new InputKeyListener());
        vals[2].setInputVerifier(new IPverifier());
        vals[3].addKeyListener(new InputKeyListener());
        for (int i = 0; i < 4; i++) {
            labels[i].setPreferredSize(new Dimension(0, 0));
            spaces1[i] = new PSpace(vals[i], 0.1, 0.1, 0.1, 0.1);
        }

        JPanel p;
        {
            p = new JPanel();
            p.setPreferredSize(new Dimension(0, 0));
            p.setLayout(new GridBagLayout());
            JButton ok = new JButton("OK");

            ok.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    reader.Write("IPAddressM", vals[0].getText());
                    reader.Write("portnoM", vals[1].getText());
                    reader.Write("IPAddressS", vals[2].getText());
                    reader.Write("portnoS", vals[3].getText());
                    reader.printToFile();
                    f.setVisible(false);
                    if (!modulatorConnected) {
                        connectToModulator();
                    } else {
                        JOptionPane.showMessageDialog(null, "Modulator already connected");
                    }
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (!senseElConnected) {
                        new Thread() {
                            @Override
                            public void run() {
                                connectToSensorElectronics();
                            }
                        }.start();

                    } else {
                        JOptionPane.showMessageDialog(null, "Sensor Electronics already connected");
                    }
                }
            });

            JButton cancel = new JButton("Cancel");
            cancel.setMargin(new Insets(0, 0, 0, 0));
            cancel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    f.setVisible(false);
                }
            });
            PSpace oksp = new PSpace(ok, 0.4, 0.2, 0.1, 0.2);
            PSpace cancelsp = new PSpace(cancel, 0.1, 0.2, 0.4, 0.2);

            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.gridwidth = 1;
            gridBagConstraints.gridheight = 1;
            gridBagConstraints.weightx = 0.5;
            gridBagConstraints.weighty = 1;
            gridBagConstraints.fill = GridBagConstraints.BOTH;
            p.add(oksp, gridBagConstraints);
            gridBagConstraints.gridx = 1;
            p.add(cancelsp, gridBagConstraints);
        }
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 1.0 / (num + 3);
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        f.getContentPane().add(heading, gridBagConstraints);
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.weightx = 0.1;
        JLabel name = new JLabel("name");
        name.setPreferredSize(new Dimension(0, 0));
        name.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel val = new JLabel("value");
        name.setPreferredSize(new Dimension(0, 0));
        val.setHorizontalAlignment(SwingConstants.CENTER);
        name.setPreferredSize(new Dimension(0, 0));
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.weightx = 0.7;
        //f.getContentPane().add(name, gridBagConstraints);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.weightx = 0.3;
        //f.getContentPane().add(val, gridBagConstraints);
        for (int i = 0; i < labels.length; i++) {
            gridBagConstraints.gridy = i + 1;
            gridBagConstraints.gridx = 0;
            gridBagConstraints.weightx = 0.6;
            f.getContentPane().add(labels[i], gridBagConstraints);
            gridBagConstraints.gridx = 1;
            gridBagConstraints.weightx = 0.4;
            f.getContentPane().add(spaces1[i], gridBagConstraints);
        }

        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = num + 1;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 1.2 / (num + 3);
        f.getContentPane().add(p, gridBagConstraints);
        f.setSize(300, 300);
        Point location = this.getLocation();
        f.setLocation(location.x + 10, location.y + 60);
        f.setVisible(true);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        // TODO add your handling code here:
        if (!editEnabled) {
            editEnabled = true;
            jMenuItem8.setText("Disable Edit Voltage");
        } else {
            editEnabled = false;
            jMenuItem8.setText("Enable Edit Voltage");
        }
        this.CVtext.setEditable(editEnabled);
        this.BVtext.setEditable(editEnabled);
        this.FVtext.setEditable(editEnabled);

    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        // TODO add your handling code here:
        final JFrame f = new JFrame();
        f.setSize(450, 400);
        f.setLayout(new GridBagLayout());
        JLabel heading = new JLabel("Limits for Interlock");
        heading.setFont(heading.getFont().deriveFont(Font.BOLD, 14));
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setPreferredSize(new Dimension(0, 0));
        JLabel[] labels = new JLabel[7];
        PSpace[] spaces1 = new PSpace[7];
        final JTextField[] mins = new JTextField[7];
        PSpace[] spaces2 = new PSpace[7];
        final JTextField[] maxs = new JTextField[7];
        String[] names = new String[]{"PRF(kHz)", "PW(us)", "Grid Pulse Voltage(V)", "Cathode Voltage(kV)", "Collector Current(mA)", "Helix Current(mA)", "Filament Voltage(V)"};
        final String[] tags = new String[]{"PRF", "PW", "GridPulseVoltage", "CathodeVoltage", "CollectorCurrent", "HelixCurrent", "FilamentVoltage"};
        final XMLReadWrite xmlReadWrite = new XMLReadWrite("limits.xml");
        for (int i = 0; i < labels.length; i++) {
            labels[i] = new JLabel(" " + names[i]);
            labels[i].setPreferredSize(new Dimension(0, 0));
            String minval = xmlReadWrite.getTextByTag(tags[i], "min");
            mins[i] = new JTextField(minval);
            spaces1[i] = new PSpace(mins[i], 0.1, 0.1, 0.1, 0.1);
            String maxval = xmlReadWrite.getTextByTag(tags[i], "max");
            maxs[i] = new JTextField(maxval);
            spaces2[i] = new PSpace(maxs[i], 0.1, 0.1, 0.1, 0.1);
        }
        mins[0].setEditable(false);
        mins[1].setEditable(false);
        maxs[0].setEditable(false);
        maxs[1].setEditable(false);
        JPanel p;
        {
            p = new JPanel();
            p.setPreferredSize(new Dimension(0, 0));
            p.setLayout(new GridBagLayout());
            JButton ok = new JButton("OK");
            ok.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    for (int i = 0; i < 7; i++) {
                        xmlReadWrite.Write(tags[i], "min", mins[i].getText());
                        xmlReadWrite.Write(tags[i], "max", maxs[i].getText());
                    }
                    xmlReadWrite.printToFile();
                    analogMonitoring.loadLimits();
                    f.setVisible(false);
                }
            });
            JButton cancel = new JButton("Cancel");
            cancel.setMargin(new Insets(0, 0, 0, 0));
            cancel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    f.setVisible(false);
                }
            });
            PSpace oksp = new PSpace(ok, 0.4, 0.2, 0.2, 0.2);
            PSpace cancelsp = new PSpace(cancel, 0.2, 0.2, 0.4, 0.2);

            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.gridwidth = 1;
            gridBagConstraints.gridheight = 1;
            gridBagConstraints.weightx = 0.5;
            gridBagConstraints.weighty = 1;
            gridBagConstraints.fill = GridBagConstraints.BOTH;
            p.add(oksp, gridBagConstraints);
            gridBagConstraints.gridx = 1;
            p.add(cancelsp, gridBagConstraints);
        }
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 1.0 / 10;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        f.getContentPane().add(heading, gridBagConstraints);
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.weightx = 0.33;
        JLabel name = new JLabel("name");
        name.setPreferredSize(new Dimension(0, 0));
        name.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel min = new JLabel("min");
        name.setPreferredSize(new Dimension(0, 0));
        min.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel max = new JLabel("max");
        name.setPreferredSize(new Dimension(0, 0));
        max.setHorizontalAlignment(SwingConstants.CENTER);
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridx = 0;
        f.getContentPane().add(name, gridBagConstraints);
        gridBagConstraints.gridx = 1;
        f.getContentPane().add(min, gridBagConstraints);
        gridBagConstraints.gridx = 2;
        f.getContentPane().add(max, gridBagConstraints);
        for (int i = 0; i < labels.length; i++) {
            gridBagConstraints.gridy = i + 2;
            gridBagConstraints.gridx = 0;
            f.getContentPane().add(labels[i], gridBagConstraints);
            gridBagConstraints.gridx = 1;
            f.getContentPane().add(spaces1[i], gridBagConstraints);
            gridBagConstraints.gridx = 2;
            f.getContentPane().add(spaces2[i], gridBagConstraints);
        }
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 2.0 / 10;
        f.getContentPane().add(p, gridBagConstraints);
        f.setLocationRelativeTo(p2);
        f.setVisible(true);
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        ViewLog viewLog = new ViewLog("General Log/");
        viewLog.setLocationRelativeTo(this.intReset);
        viewLog.setTitle("General Log");
        viewLog.setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        ViewLog viewLog = new ViewLog("Error Log/");
        viewLog.setTitle("Error Log");
        viewLog.setVisible(true);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
//        // TODO add your handling code here:
        final JFrame f = new JFrame();
        f.setSize(300, 500);
        f.setLayout(new GridBagLayout());
        JLabel heading = new JLabel("MTSG Parameters");
        heading.setFont(heading.getFont().deriveFont(Font.BOLD, 14));
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setPreferredSize(new Dimension(0, 0));
        final int num = senseFrame.field.length;
        JLabel[] labels = new JLabel[num];
        PSpace[] spaces1 = new PSpace[num];
        final JTextField[] vals = new JTextField[num];
        final String[] names = new String[num];
        for (int i = 0; i < num; i++) {
            names[i] = senseFrame.field[i].getName();
        }
        final XMLReadWrite xmlReadWrite = new XMLReadWrite("senseElParams.xml");
        for (int i = 0; i < labels.length; i++) {
            labels[i] = new JLabel(" " + names[i]);
            labels[i].setPreferredSize(new Dimension(0, 0));
            String val = xmlReadWrite.getTextByTag(names[i]);
            vals[i] = new JTextField(val);
            vals[i].addKeyListener(new InputKeyListener());
            spaces1[i] = new PSpace(vals[i], 0.1, 0.1, 0.1, 0.1);
        }
        double trig2traildelay = Double.parseDouble(xmlReadWrite.getTextByTag("Tx2Trig2Traildelay"));

        JPanel p;
        {
            p = new JPanel();
            p.setPreferredSize(new Dimension(0, 0));
            p.setLayout(new GridBagLayout());
            JButton ok = new JButton("OK");

            ok.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    //double d2=Double.parseDouble(vals[13].getText());
                    double w2 = Double.parseDouble(vals[14].getText());
                    double d3 = Double.parseDouble(vals[17].getText());
                    double w3 = Double.parseDouble(vals[18].getText());

                    boolean dutyOK = false;
                    boolean verify1 = verify(vals[2], 0.1, 20.06);
                    boolean verify2 = verify(vals[18], 0.2, 13);
                    if (verify1 && verify2) {
                        String text1 = vals[2].getText();
                        String text2 = vals[18].getText();
                        if (!text1.equals("")) {
                            prf = Double.parseDouble(text1);
                        }
                        if (!text2.equals("")) {
                            pw = Double.parseDouble(text2);
                        }

                        if (!text1.equals("") && !text2.equals("")) {
                            dutyOK = dutyRatioOK();
                            prfchoose.setText(text1);
                            pwchoose.setText(text2);

                        }
                        if (dutyOK && (w2 * prf <= 54) && (d3 >= 0.2) && (w2 - d3 - w3 >= 0.2) && (w2 > w3)) {
                            for (int i = 0; i < num; i++) {
                                xmlReadWrite.Write(names[i], vals[i].getText());
                            }
                            xmlReadWrite.printToFile();
                            senseFrame.loadFromFile("senseElParams.xml");
                            //senseFrame;
                            f.setVisible(false);
                            prfchoose.setText(text1);
                            pwchoose.setText(text2);
                        } else {
                            JOptionPane.showMessageDialog(null, "Duty ratio Parameter values are not OK");
                        }
                    }
                }
            });

            JButton cancel = new JButton("Cancel");

            cancel.setMargin(
                    new Insets(0, 0, 0, 0));
            cancel.addActionListener(
                    new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    f.setVisible(false);
                }
            });
            PSpace oksp = new PSpace(ok, 0.4, 0.2, 0.1, 0.2);
            PSpace cancelsp = new PSpace(cancel, 0.1, 0.2, 0.4, 0.2);
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.gridwidth = 1;
            gridBagConstraints.gridheight = 1;
            gridBagConstraints.weightx = 0.5;
            gridBagConstraints.weighty = 1;
            gridBagConstraints.fill = GridBagConstraints.BOTH;

            p.add(oksp, gridBagConstraints);
            gridBagConstraints.gridx = 1;

            p.add(cancelsp, gridBagConstraints);
        }

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 1.0 / (num + 3);
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        f.getContentPane().add(heading, gridBagConstraints);
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.weightx = 0.1;
        JLabel name = new JLabel("name");
        name.setPreferredSize(new Dimension(0, 0));
        name.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel val = new JLabel("value");
        name.setPreferredSize(new Dimension(0, 0));
        val.setHorizontalAlignment(SwingConstants.CENTER);
        name.setPreferredSize(new Dimension(0, 0));
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.weightx = 0.7;
        f.getContentPane().add(name, gridBagConstraints);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.weightx = 0.3;
        f.getContentPane().add(val, gridBagConstraints);
        for (int i = 0; i < labels.length; i++) {
            gridBagConstraints.gridy = i + 2;
            gridBagConstraints.gridx = 0;
            gridBagConstraints.weightx = 0.7;
            f.getContentPane().add(labels[i], gridBagConstraints);
            gridBagConstraints.gridx = 1;
            gridBagConstraints.weightx = 0.3;
            f.getContentPane().add(spaces1[i], gridBagConstraints);
        }
        boolean[] flags = new boolean[labels.length];
        flags[1] = true;
        flags[2] = true;
        flags[12] = false;
        flags[13] = true;
        flags[14] = true;
        flags[17] = true;
        flags[18] = true;
        String[] units = new String[labels.length];
        units[2] = "kHz";
        units[12] = "us";
        units[13] = "us";
        units[14] = "us";
        units[17] = "us";
        units[18] = "us";
        for (int i = 0; i < labels.length; i++) {
            labels[i].setVisible(flags[i]);
            spaces1[i].setVisible(flags[i]);
            if (flags[i]) {
                String text = labels[i].getText();
                if (i != 1) {
                    labels[i].setText(" " + text + " (" + units[i] + ")");
                } else {
                    labels[i].setText(" " + text);
                }
            }
        }
        labels[13].setText(" " + "Beam Pulse Delay" + " (" + units[14] + ")");
        labels[14].setText(" " + "Beam Pulse width" + " (" + units[14] + ")");
        labels[17].setText(" " + "RF Pulse Delay" + " (" + units[14] + ")");
        labels[18].setText(" " + "RF Pulsewidth" + " (" + units[14] + ")");
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = num + 2;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 1.2 / (num + 3);
        f.getContentPane().add(p, gridBagConstraints);
        f.setLocationRelativeTo(p2);
        f.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void stopMTSG(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopMTSG
        // TODO add your handling code here:
        byte[] buf1 = mtsgcommand.getPacket(Command.MTSGSTOP);
        sendtoSenseElectronics(buf1);
    }//GEN-LAST:event_stopMTSG

    private void startMTSG(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startMTSG
        if (VSWRinterlockset) {
            JOptionPane.showMessageDialog(null, "VSWR Interlock set");
            return;
        }

        byte[] buf1 = mtsgcommand.getPacket(Command.MTSGSTART);
        sendtoSenseElectronics(buf1);

    }//GEN-LAST:event_startMTSG

    private void downloadMTSG(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downloadMTSG
//        double pw = senseFrame.baudWidth.getValueWithDecimal() * senseFrame.baudNo.getValue();
//        senseFrame.pulseWidth.setValueWithDecimal(pw);
//        senseFrame.TRSWwidth.setValueWithDecimal(pw);
        senseFrame.loadFromFile("senseElParams.xml");
        byte[] packetS = senseFrame.getPacket();
        for (int i = 27; i < 40; i++) {
            String text = getString(packetS, 2 * i) + " " + getString(packetS, 2 * i + 1);
            displayFrame.sdisp[i - 27].setText(text);
        }
        sendtoSenseElectronics(packetS);
    }//GEN-LAST:event_downloadMTSG

    private void statreqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statreqActionPerformed
        // TODO add your handling code here:
        byte[] packet = modStatReqFrame.getPacket();

//        for (int i = 0; i < 6; i++) {
//            String text = getString(packet, 2 * i) + " " + getString(packet, 2 * i + 1);
//            displayFrame.sdisp[i].setText(text);
//        }
//        for (int i = 7; i < 10; i++) {
//            displayFrame.sdisp[i].setText("");
//        }
        sendtoModulator(packet);
        try {
            Thread.sleep(250);
        } catch (InterruptedException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        sendtoModulator(packetM);


    }//GEN-LAST:event_statreqActionPerformed

    private void sendStatReq2SenseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendStatReq2SenseActionPerformed
        // TODO add your handling code here:
        byte[] buf1 = mtsgcommand.getPacket(Command.SENSESTAT);
        try {
            outS.write(buf1);
            outS.flush();
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_sendStatReq2SenseActionPerformed

    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem14ActionPerformed

        int[] fieldIndices;
        String[] units;
        int[] min, max;
        if (plotOpen == false) {

            fieldIndices = new int[]{0, 1, 2, 3, 4, 5};

            units = new String[]{"V", "kV", "Volts(V)", "A", "mA", "Volts(V)"};
            min = new int[]{-10, -20, -70, 0, 0, -20};
            max = new int[]{0, -10, 0, 4, 250, 0};

            plot = new GraphPlotSH(analogstat, fieldIndices, units, min, max);
            plot.addWindowListener(plotWindowListener);
            plotOpen = true;

            plot.setTitle("modulator parameters");
            plot.setLocationRelativeTo(null);
            plot.setVisible(true);
        }
    }//GEN-LAST:event_jMenuItem14ActionPerformed

    private void jMenuItem15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem15ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem15ActionPerformed
    private String getString(byte[] buf, int index) {
        int val = buf[index];
        if (val < 0) {
            val += 256;
        }
        int uppernibble = val / 16;
        int lowernibble = val % 16;

        return (nibble2String(uppernibble) + nibble2String(lowernibble));
    }

    private String nibble2String(int nibble) {
        String str = "";
        switch (nibble) {
            case 15:
                str = "F";
                break;
            case 14:
                str = "E";
                break;
            case 13:
                str = "D";
                break;
            case 12:
                str = "C";
                break;
            case 11:
                str = "B";
                break;
            case 10:
                str = "A";
                break;
            default:
                str = String.valueOf(nibble);

        }
        return str;
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
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    private void addp1comps() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        p1.setLayout(new java.awt.GridBagLayout());
        //
        ActionListener checkboxlistener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputStyleSel();
            }
        };

        ActionListener configListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                configRadioBtnClicked(e);
            }
        };

        KeyAdapter enterListener1 = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                configDone = false;
                prfpwset = false;
                flowStatus.setStatus(TxComps.PW_PRF, Status.ERROR);
                //bd.setStatus(TxComps.PW_PRF, Status.ERROR);
                txStatus.setStatus(0, 5, Status.FINE);
                super.keyReleased(e); //To change body of generated methods, choose Tools | Templates.
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (verification1()) {
                        pgroup.setVisible(true);
                        prfpwset = true;
                    }
                } else {

                    dutyratioAlarm.setText("Press Enter");
                    dutyratioAlarm.setBackground(new Color(240, 240, 240));
                    dutyratioAlarm.setVisible(true);
//                    if (verification2()) {
                    pgroup.setVisible(false);
//                    }
                }
            }
        };
        KeyAdapter enterListener2 = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                configDone = false;
                flowStatus.setStatus(TxComps.PW_PRF, Status.ERROR);
                //bd.setStatus(TxComps.PW_PRF, Status.ERROR);
                txStatus.setStatus(0, 5, Status.FINE);
                super.keyReleased(e); //To change body of generated methods, choose Tools | Templates.
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (verification2() && verification1()) {
                        pgroup.setVisible(true);
                    }
                } else {
//                    if (verification1()) {
                    pgroup.setVisible(false);
//                    }
                }
            }
        };
        JPanel p11 = new JPanel();
        {
            p11.setPreferredSize(new Dimension(0, 0));
            p11.setLayout(new java.awt.GridBagLayout());
            p11.setBackground(Color.WHITE);
            MultiLineText[] labels = new MultiLineText[2];
            PSpace[] labelspaces = new PSpace[2];
            PSpace[] spaces = new PSpace[4];
            String[] names = new String[]{"TRIGGER", "WAVEGUIDE:SWITCH"};// 
            for (int i = 0; i < 2; i++) {
                labels[i] = new MultiLineText(names[i]);
                labels[i].setOutlined(false);
                labels[i].setBackground(Color.CYAN);
                labels[i].rectangle.setRounded(false);
                labels[i].rectangle.setFillcolor(Color.CYAN);
                labels[i].rectangle.label.setFonttype(Font.BOLD);
                labels[i].rectangle.label.setfontSize(12);
                labelspaces[i] = new PSpace(labels[i], 0.1, 0.1, 0.1, 0.1);
            }
            String[] names1 = new String[]{"INTERNAL", "EXTERNAL", "DUMMY:LOAD", "ANTENNA"};
            p1btns = new MyButton[4];
            for (int i = 0; i < 4; i++) {
                p1btns[i] = new MyButton(names1[i]);
                p1btns[i].label.setFonttype(Font.BOLD);
                p1btns[i].setFontSize(12);
                spaces[i] = new PSpace(p1btns[i], 0.15, 0.15, 0.15, 0.15);
                spaces[i].setbkgrnd(Color.WHITE);

            }
            p1btns[0].setBackground(Color.GREEN);
            p1btns[0].label.setColor(Color.BLACK);
            p1btns[1].setBackground(Color.RED);
            p1btns[1].label.setColor(Color.WHITE);
            p1btns[2].setBackground(Color.GREEN);
            p1btns[2].label.setColor(Color.BLACK);
            p1btns[3].setBackground(Color.RED);
            p1btns[3].label.setColor(Color.WHITE);

            ActionListener intextListener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    if (ae.getSource() == p1btns[0] && extSelected) {//int selection
                        digitalctrlframe.setInt();
                        p1btns[0].setBackground(Color.ORANGE);
                    } else if (intSelected) {//ext selection
                        digitalctrlframe.setExt();
                        p1btns[1].setBackground(Color.ORANGE);
                    }
                    if (verification2()) {
                        analogctrlframe.beamOnVoltage.setValueWithDecimal(BV);
                        analogctrlframe.cathodeVoltage.setValueWithDecimal(CV);
                        analogctrlframe.filamentVoltage.setValueWithDecimal(FV);
                        analogctrlframe.warmUpTimer.setValue(warmUPTime);
                        packetM = digitalctrlframe.getPacket();
                        for (int i = 0; i < 15; i++) {
                            String text = getString(packetM, 2 * i) + " " + getString(packetM, 2 * i + 1);
                            displayFrame.sdisp[i].setText(text);
                        }
                    }
                }
            };

            p1btns[0].addActionListener(intextListener);
            p1btns[1].addActionListener(intextListener);
            GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.fill = GridBagConstraints.BOTH;
            gridBagConstraints.weightx = 0.3;
            gridBagConstraints.weighty = 0.5;
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            p11.add(labelspaces[0], gridBagConstraints);
            gridBagConstraints.gridy = 1;
            p11.add(labelspaces[1], gridBagConstraints);
            gridBagConstraints.weightx = 0.35;
            for (int i = 0; i < 2; i++) {
                gridBagConstraints.gridy = i;
                gridBagConstraints.gridx = 1;
                p11.add(spaces[2 * i], gridBagConstraints);
                gridBagConstraints.gridx = 2;
                p11.add(spaces[2 * i + 1], gridBagConstraints);
            }
        }
        //
        JPanel p12;
        {
            p12 = new JPanel();
            p12.setPreferredSize(new Dimension(0, 0));
            p12.setLayout(new java.awt.GridBagLayout());
            MultiLineText label1 = new MultiLineText("CATHODE:VOLTAGE");
            label1.setOutlined(false);
            //label1.setBackground(Color.CYAN);
            label1.rectangle.label.setFonttype(Font.BOLD);
            label1.rectangle.label.setfontSize(12);
            label1.setToolTipText("RANGE: -18kV to -15kV");
            MultiLineText label2 = new MultiLineText("BEAM ON:VOLTAGE");
            //label2.setBackground(Color.CYAN);
            label2.rectangle.label.setFonttype(Font.BOLD);
            label2.rectangle.label.setfontSize(12);
            label2.setOutlined(false);
            label2.setToolTipText("RANGE: -60V to 0V");
            MultiLineText label3 = new MultiLineText("FILAMENT:VOLTAGE");
            //label3.setBackground(Color.CYAN);
            label3.rectangle.label.setFonttype(Font.BOLD);
            label3.rectangle.label.setfontSize(12);
            label3.setOutlined(false);
            label3.setToolTipText("RANGE: -8.5V to -5.5V");
            MultiLineText label4 = new MultiLineText("WARM UP:TIME");
            //label3.setBackground(Color.CYAN);
            label4.rectangle.label.setFonttype(Font.BOLD);
            label4.rectangle.label.setfontSize(12);
            label4.setOutlined(false);
            label4.setToolTipText("RANGE: 0 to 180 SEC");
            XMLReadWrite reader = new XMLReadWrite("conf.xml");
            String text1 = reader.getTextByTag("CathodeVoltage");
            CVtext = new JTextField(text1);
            CVtext.addKeyListener(enterListener2);
            CVtext.setEditable(false);
            PSpace sp1 = new PSpace(CVtext, 0, 0.1, 0, 0.1);
            String text2 = reader.getTextByTag("GridVoltage");
            BVtext = new JTextField(text2);
            BVtext.addKeyListener(enterListener2);
            BVtext.setEditable(false);
            PSpace sp2 = new PSpace(BVtext, 0, 0.1, 0, 0.1);
            String text3 = reader.getTextByTag("FilamentVoltage");
            FVtext = new JTextField(text3);
            FVtext.addKeyListener(enterListener2);
            FVtext.setEditable(false);
            PSpace sp3 = new PSpace(FVtext, 0, 0.1, 0, 0.1);
            warmUPTimeText = new JTextField("180");
            warmUPTimeText.addKeyListener(enterListener2);
            FV = Double.parseDouble(text3);
            BV = Double.parseDouble(text2);
            CV = Double.parseDouble(text1);

            PSpace sp4 = new PSpace(warmUPTimeText, 0, 0.1, 0, 0.1);
            GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.fill = GridBagConstraints.BOTH;
            gridBagConstraints.weightx = 0.25;
            gridBagConstraints.weighty = 1;
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            p12.add(label1, gridBagConstraints);
            gridBagConstraints.gridx = 1;
            p12.add(sp1, gridBagConstraints);
            gridBagConstraints.gridx = 2;
            p12.add(label2, gridBagConstraints);
            gridBagConstraints.gridx = 3;
            p12.add(sp2, gridBagConstraints);
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 1;
            p12.add(label3, gridBagConstraints);
            gridBagConstraints.gridx = 1;
            p12.add(sp3, gridBagConstraints);
            gridBagConstraints.gridx = 2;
            p12.add(label4, gridBagConstraints);
            gridBagConstraints.gridx = 3;
            p12.add(sp4, gridBagConstraints);
        }
        JPanel p13;
        {
            p13 = new JPanel();
            p13.setPreferredSize(new Dimension(0, 0));
            p13.setLayout(new java.awt.GridBagLayout());
            JLabel PRF = new JLabel("PW (us)");
            PRF.setHorizontalAlignment(SwingConstants.CENTER);
            PRF.setBackground(Color.lightGray);
            PRF.setOpaque(true);
            PSpace p121 = new PSpace(PRF, 0.4, 0, 0.2, 0);
            JLabel PW = new JLabel("PRF (kHz)");
            PW.setHorizontalAlignment(SwingConstants.CENTER);
            PW.setBackground(Color.lightGray);
            PW.setOpaque(true);
            PSpace p122 = new PSpace(PW, 0.2, 0, 0.2, 0);
            GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.fill = GridBagConstraints.BOTH;
            gridBagConstraints.weightx = 0.5;
            gridBagConstraints.weighty = 1;
            gridBagConstraints.gridx = 0;
            p13.add(p121, gridBagConstraints);
            gridBagConstraints.gridx = 1;
            p13.add(p122, gridBagConstraints);
        }
        //PSpace p12=new PSpace(prf,0.1,0.1,0.1,0.1);
        //

        JPanel p14 = new JPanel();
        {
            p14.setPreferredSize(new Dimension(0, 0));
            p14.setLayout(new java.awt.GridBagLayout());
            chk1 = new JCheckBox();
            chk1.addActionListener(checkboxlistener);
            chk1.setPreferredSize(new Dimension(0, 0));
            PSpace p131 = new PSpace(chk1, 0, 0, 0, 0.8);
            JPanel p132;
            {
                p132 = new JPanel();
                p132.setPreferredSize(new Dimension(0, 0));
                p132.setLayout(new java.awt.GridBagLayout());
                prfbtns = new JRadioButton[3];
                group1 = new ButtonGroup();
                GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.fill = GridBagConstraints.BOTH;
                gridBagConstraints.weightx = 1;
                gridBagConstraints.weighty = .33;
                gridBagConstraints.gridx = 0;
                String[] prfvalues = new String[]{"5", "10", "20"};
                for (int i = 0; i < 3; i++) {
                    prfbtns[i] = new JRadioButton();
                    prfbtns[i].setPreferredSize(new Dimension(0, 0));
                    prfbtns[i].setText(prfvalues[i]);
                    prfbtns[i].addActionListener(configListener);
                    group1.add(prfbtns[i]);
                    gridBagConstraints.gridy = i;
                    p132.add(prfbtns[i], gridBagConstraints);
                }
                //prfbtns[0].setSelected(true);
            }

            JPanel p133;
            {
                p133 = new JPanel();
                p133.setPreferredSize(new Dimension(0, 0));
                p133.setLayout(new java.awt.GridBagLayout());
                pwbtns = new JRadioButton[3];
                group2 = new ButtonGroup();
                GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.fill = GridBagConstraints.BOTH;
                //gridBagConstraints.anchor=GridBagConstraints.WEST;
                gridBagConstraints.weightx = 1;
                gridBagConstraints.weighty = .33;
                gridBagConstraints.gridx = 0;
                String[] pwvalues = new String[]{"10", "5", "2.5"};
                for (int i = 0; i < 3; i++) {
                    pwbtns[i] = new JRadioButton();
                    pwbtns[i].setPreferredSize(new Dimension(0, 0));
                    pwbtns[i].setText(pwvalues[i]);
                    pwbtns[i].addActionListener(configListener);
                    //pws[i]
                    group2.add(pwbtns[i]);
                    gridBagConstraints.gridy = i;
                    p133.add(pwbtns[i], gridBagConstraints);
                }
                //pwbtns[0].setSelected(true);
            }
            GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.fill = GridBagConstraints.BOTH;
            gridBagConstraints.weightx = 0.2;
            gridBagConstraints.weighty = 1;
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            p14.add(p131, gridBagConstraints);
            gridBagConstraints.gridx = 2;
            gridBagConstraints.weightx = 0.4;
            p14.add(p132, gridBagConstraints);
            gridBagConstraints.gridx = 1;
            gridBagConstraints.weightx = 0.4;
            p14.add(p133, gridBagConstraints);

        }
        //
        JPanel p15 = new JPanel();
        {
            p15.setPreferredSize(new Dimension(0, 0));
            p15.setLayout(new java.awt.GridBagLayout());
            chk2 = new JCheckBox();
            chk2.addActionListener(checkboxlistener);
            chk2.setPreferredSize(new Dimension(0, 0));
            //JComboBox prfchoose = new JComboBox();
            prfchoose = new JTextField();

            //prfchoose.setInputVerifier(myInputVerifier1);
            prfchoose.addKeyListener(new InputKeyListener());
            prfchoose.addKeyListener(enterListener1);
            javax.swing.border.Border border = prfchoose.getBorder();
            PSpace sp2 = new PSpace(prfchoose, 0.2, 0, 0.1, 0);
            //JComboBox pwchoose = new JComboBox();
            pwchoose = new JTextField();
            //pwchoose.setInputVerifier(myInputVerifier2);
            pwchoose.addKeyListener(new InputKeyListener());
            pwchoose.addKeyListener(enterListener1);
            PSpace sp3 = new PSpace(pwchoose, 0.1, 0, 0.2, 0);
            GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.fill = GridBagConstraints.BOTH;
            gridBagConstraints.weightx = 0.1;
            gridBagConstraints.weighty = 1;
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            p15.add(chk2, gridBagConstraints);
            gridBagConstraints.gridx = 1;
            gridBagConstraints.weightx = 0.45;
            p15.add(sp3, gridBagConstraints);
            gridBagConstraints.gridx = 2;
            gridBagConstraints.weightx = 0.45;
            p15.add(sp2, gridBagConstraints);
        }
        ButtonGroup group3 = new ButtonGroup();
        group3.add(chk1);
        group3.add(chk2);

        //
        PSpace p16;
        {
            dutyratioAlarm = new JLabel("DUTY RATIO");
            dutyratioAlarm.setHorizontalAlignment(SwingConstants.CENTER);
            //dutyratioAlarm.setBackground(Color.red);
            dutyratioAlarm.setOpaque(true);
            dutyratioAlarm.setVisible(false);
            p16 = new PSpace(dutyratioAlarm, 0.2, 0.1, 0.2, 0.1);
        }

        //
        PSpace p17;

        {
            pgroup = new JPanel();
            pgroup.setPreferredSize(new Dimension(0, 0));
            pgroup.setLayout(new java.awt.GridBagLayout());

            config = new MyButton("Config");
            config.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    if (verification2()) {
                        analogctrlframe.cathodeVoltage.setValueWithDecimal(CV);
                        analogctrlframe.beamOnVoltage.setValueWithDecimal(BV);
                        analogctrlframe.filamentVoltage.setValueWithDecimal(FV);
                        analogctrlframe.warmUpTimer.setValue(warmUPTime);
                        senseFrame.loadFromFile("senseElParams.xml");
                        if (chk2.isSelected()) {
                            if (verification1()) {
                                prf = Double.parseDouble(prfchoose.getText());
                                analogctrlframe.PRF.setValueWithDecimal(prf);
                                pw = Double.parseDouble(pwchoose.getText());
                                //analogctrlframe.pulsewidth.setValueWithDecimal(pw);
                                senseFrame.PRF.setValueWithDecimal(prf);
                                senseFrame.pulseWidth.setValueWithDecimal(pw);
                                senseFrame.DRxwidth.setValueWithDecimal(pw);
                                configDone = true;
                                flowStatus.setStatus(TxComps.PW_PRF, Status.FINE);
                                //bd.setStatus(TxComps.PW_PRF, Status.FINE);
                                txStatus.setStatus(0, 5, Status.FINE);
                            }
                        } else {
                            if (configRadioBtnClicked(null)) {
                                analogctrlframe.PRF.setValueWithDecimal(prf);
                                //analogctrlframe.pulsewidth.setValueWithDecimal(pw);
                                senseFrame.PRF.setValueWithDecimal(prf);
                                senseFrame.pulseWidth.setValueWithDecimal(pw);
                                senseFrame.DRxwidth.setValueWithDecimal(pw);
                                configDone = true;
                                flowStatus.setStatus(TxComps.PW_PRF, Status.FINE);
                                //bd.setStatus(TxComps.PW_PRF, Status.FINE);
                                txStatus.setStatus(0, 5, Status.FINE);
                            }
                        }

                        double d3 = senseFrame.DRxpredelay.getValueWithDecimal();
                        double w3 = senseFrame.DRxwidth.getValueWithDecimal();
                        double t3 = senseFrame.DRxtrail;
                        double w2 = d3 + w3 + t3;
                        analogctrlframe.pulsewidth.setValueWithDecimal(pw);
                        senseFrame.TRSWwidth.setValueWithDecimal(w2);
                        double bw = pw / senseFrame.baudNo.getValue();
                        senseFrame.baudWidth.setValueWithDecimal(bw);
                        pgroup.setVisible(false);
                        packetM = analogctrlframe.getPacket();
                        for (int i = 0; i < 15; i++) {
                            String text = getString(packetM, 2 * i) + " " + getString(packetM, 2 * i + 1);
                            displayFrame.sdisp[i].setText(text);
                        }

//                        for (int i = 0; i < 20; i++) {
//                            try {
//                                Thread.sleep(50);
//                            } catch (InterruptedException ex) {
//                                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
//                            }
//
//                            sendtoModulator(packetM);
//                        }
                        byte[] packetS = senseFrame.getPacket();
                        senseFrame.printToFile("senseElParams.xml");
                        sendtoSenseElectronics(packetS);

                    } else {
                    }
                }
            });
            PSpace sp1 = new PSpace(config, 0.2, 0.1, 0.05, 0.1);
            sp1.setbkgrnd(Color.lightGray);
            cancel = new MyButton("Cancel");
            cancel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    pgroup.setVisible(false);
                    group1.clearSelection();
                    group2.clearSelection();
                    prfchoose.setText("");
                    pwchoose.setText("");
                    dutyratioAlarm.setVisible(false);
                }
            });
            PSpace sp2 = new PSpace(cancel, 0.05, 0.1, 0.2, 0.1);
            sp2.setbkgrnd(Color.lightGray);
            GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.fill = GridBagConstraints.BOTH;
            gridBagConstraints.weightx = 0.5;
            gridBagConstraints.weighty = 1;
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            pgroup.add(sp1, gridBagConstraints);
            gridBagConstraints.gridx = 1;
            pgroup.add(sp2, gridBagConstraints);
        }
        pgroup.setVisible(false);
        p17 = new PSpace(pgroup, 0.2, 0, 0.2, 0);
        //

        GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 0.33;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        p1.add(p11, gridBagConstraints);
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weighty = 0.15;
        p1.add(p12, gridBagConstraints);
        gridBagConstraints.gridy = 2;
        gridBagConstraints.weighty = 0.05;
        p1.add(p13, gridBagConstraints);
        gridBagConstraints.gridy = 3;
        gridBagConstraints.weighty = 0.33;
        p1.add(p14, gridBagConstraints);
        gridBagConstraints.gridy = 4;
        gridBagConstraints.weighty = 0.1;
        p1.add(p15, gridBagConstraints);
        gridBagConstraints.gridy = 5;
        gridBagConstraints.weighty = 0.1;
        p1.add(p16, gridBagConstraints);
        gridBagConstraints.gridy = 6;
        gridBagConstraints.weighty = 0.1;
        p1.add(p17, gridBagConstraints);

        chk1.setSelected(true);
        checkboxlistener.actionPerformed(null);

    }

    private void addp2comps() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        p2.setLayout(new java.awt.GridBagLayout());
        ////
        JPanel p21 = new JPanel();
        p21.setPreferredSize(new Dimension(0, 0));
        p21.setLayout(new java.awt.GridBagLayout());
        manual = new MyButton("MANUAL");
        manual.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                autoONinprogress = false;
                autoONTimer.stop();
                //autoOFFinprogress = false;
                autoOFFTimer.stop();
                manual.setBackground(Color.green);
                autoON.setBackground(Color.RED);
                autoOFF.setBackground(Color.RED);

            }
        });
        manual.label.setFonttype(Font.BOLD);
        manual.label.setfontSize(12);
        manual.setBackground(Color.green);
        autoON = new MyButton("AUTO ON");
        autoON.label.setFonttype(Font.BOLD);
        autoON.label.setfontSize(12);
        autoON.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (autoONinprogress) {
                    JOptionPane.showMessageDialog(autoON, "Auto ON already in progress");
                } else {
                    //startAutoON();
                }
            }
        });
        autoOFF = new MyButton("AUTO OFF");
        autoOFF.label.setFonttype(Font.BOLD);
        autoOFF.label.setfontSize(12);
        autoOFF.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (autoOFFinprogress) {
                    JOptionPane.showMessageDialog(autoON, "Auto OFF already in progress");
                } else {
                    startAutoOFF();
                }
            }
        });
        PSpace ps1 = new PSpace(manual, 0.3, 0.15, 0.1, 0.35);
        PSpace ps2 = new PSpace(autoON, 0.1, 0.15, 0.1, 0.35);
        PSpace ps3 = new PSpace(autoOFF, 0.1, 0.15, 0.2, 0.35);
        ps1.setbkgrnd(Color.WHITE);
        ps2.setbkgrnd(Color.WHITE);
        ps3.setbkgrnd(Color.WHITE);
        p21.setBackground(Color.WHITE);
        //manual.setBackground(Color.RED);
        //manual.setBackground(Color.red);
        autoON.setBackground(Color.red);
        autoON.label.setColor(Color.WHITE);
        autoOFF.setBackground(Color.red);
        autoOFF.label.setColor(Color.WHITE);
        //auto.setOpaque(true);
        GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.BOTH;

        gridBagConstraints.weighty = 1;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.weightx = 0.36;
        p21.add(ps1, gridBagConstraints);
        gridBagConstraints.weightx = 0.28;
        gridBagConstraints.gridx = 1;
        p21.add(ps2, gridBagConstraints);
        gridBagConstraints.weightx = 0.36;
        gridBagConstraints.gridx = 2;
        p21.add(ps3, gridBagConstraints);
        //
        JPanel p22 = new JPanel();
        p22.setPreferredSize(new Dimension(0, 0));
        p22.setLayout(new java.awt.GridBagLayout());
        String[] names = new String[]{"TX MAINS", "HV", "MOD", "RF", "TX:RADIATION"};
        MultiLineText[] labels = new MultiLineText[5];
//        JLabel[] ons=new JLabel[6];
//        JLabel[] offs=new JLabel[6];
        ons = new JButton[5];
        offs = new JButton[5];
        PSpace[] onspaces = new PSpace[5];
        PSpace[] offspaces = new PSpace[5];
        PSpace[] labelspaces = new PSpace[5];
        onclicked = new boolean[5];
        offclicked = new boolean[5];
        OnOffstat = new int[5];
        for (int i = 0; i < 5; i++) {
            labels[i] = new MultiLineText(names[i]);
            labels[i].setOutlined(false);
            labels[i].rectangle.setRounded(false);
            labels[i].rectangle.setFillcolor(Color.CYAN);
            //labels[i].rectangle.setGradientcolor(false);
            labels[i].rectangle.label.setColor(Color.BLUE);
            labels[i].setFontsize(12);
            if (i % 2 == 0) {
                labels[i].setBackground(Color.CYAN);
            } else {
                labels[i].setBackground(Color.CYAN);
            }
            labels[i].setTextanchor(Rectangle.LEFT);

            ons[i] = new JButton("ON");
            ons[i].setForeground(Color.WHITE);
            ons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (autoONinprogress || autoOFFinprogress) {
                        JOptionPane.showMessageDialog(ons[0], "Auto in progress");
                        return;
                    }
                    int index = 0;
                    for (int i = 0; i < ons.length; i++) {
                        if (e.getSource().equals(ons[i])) {
                            index = i;
                            break;
                        }
                    }

                    btnPressed(index, Command.ON);
                }
            });
            OnOffstat[i] = Status.OFF;
            offs[i] = new JButton("OFF");
            offs[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (autoONinprogress || autoOFFinprogress) {
                        JOptionPane.showMessageDialog(ons[0], "Auto in progress");
                        return;
                    }
                    int index = 0;
                    for (int i = 0; i < offs.length; i++) {
                        if (e.getSource().equals(offs[i])) {
                            index = i;
                            break;
                        }
                    }
                    btnPressed(index, Command.OFF);
                }
            });
            onspaces[i] = new PSpace(ons[i], 0.15, 0.15, 0.15, 0.15);
            offspaces[i] = new PSpace(offs[i], 0.15, 0.15, 0.15, 0.15);
            labelspaces[i] = new PSpace(labels[i], 0.1, 0.1, 0.1, 0.1);
            onspaces[i].setbkgrnd(Color.WHITE);
            offspaces[i].setbkgrnd(Color.WHITE);
            ons[i].setBackground(Color.RED);
            ons[i].setForeground(Color.WHITE);
            //ons[i].setOpaque(true);
            offs[i].setBackground(Color.GREEN);
            offs[i].setForeground(Color.BLACK);
            //offs[i].setOpaque(true);
        }
        //p21.setBackground(Color.WHITE);
        p22.setBackground(Color.WHITE);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.6;
        gridBagConstraints.weighty = 0.1;
        for (int i = 0; i < 4; i++) {
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = i;
            gridBagConstraints.weightx = 0.4;
            p22.add(labelspaces[i], gridBagConstraints);
            gridBagConstraints.gridx = 1;
            gridBagConstraints.weightx = 0.3;
            p22.add(onspaces[i], gridBagConstraints);
            gridBagConstraints.gridx = 2;
            gridBagConstraints.weightx = 0.3;
            p22.add(offspaces[i], gridBagConstraints);
        }
//        JLabel dummy = new JLabel("");
//        dummy.setPreferredSize(new Dimension(0, 0));
//        gridBagConstraints.gridwidth = 2;
//        gridBagConstraints.weightx = 1;
//        gridBagConstraints.gridx = 0;
//        gridBagConstraints.gridy = 4;
//        p22.add(dummy, gridBagConstraints);
        ///

        intReset = new JButton("INTERLOCK RESET");
        resetListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (e.getSource() == intStat2.intReset) {
                    VSWRinterlockset = false;
                    intStat2.setStatus(6, Status.FINE, "OK");
                } else {
                    sendReset(Status.ON);
                    if (e != null) {
                        intReset.setBackground(Color.GREEN);
                    }
                    intResetTimer.restart();
                }
            }
        };
        intReset.addActionListener(resetListener);
        PSpace p23 = new PSpace(intReset, 0.3, 0.3, 0.1, 0.2);
        p23.setbkgrnd(Color.white);

        /////
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 0.2;
        p2.add(p21, gridBagConstraints);
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weighty = 0.6;
        p2.add(p22, gridBagConstraints);
        gridBagConstraints.gridy = 2;
        gridBagConstraints.weighty = 0.2;
        p2.add(p23, gridBagConstraints);
    }

    private void addp3comps() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        p3.setLayout(new java.awt.GridBagLayout());
        MultiLineText[] labels = new MultiLineText[10];

        p3results = new JLabel[9];
        PSpace[] spaces = new PSpace[9];
        PSpace[] labelspaces = new PSpace[9];
        String[] names = new String[]{"DOOR LOCK", "ARC DETECTOR", "BLOWERS", "WAVEGUIDE PRESSURE", "THERMAL SWITCh", "FILAMENT:CURRENT (A)", "CATHODE:VOLTAGE", "BEAM ON:VOLTAGE", "FILAMENT VOLTAGE"};

        for (int i = 0; i < 9; i++) {
            labels[i] = new MultiLineText(names[i]);
            labels[i].rectangle.label.setfontSize(12);
            labels[i].rectangle.label.setFonttype(Font.BOLD);
            labels[i].setOutlined(false);
            if (i % 2 == 0) {
                labels[i].setBackground(Color.CYAN);
            } else {
                labels[i].setBackground(Color.CYAN);
            }
            labels[i].setTextanchor(Rectangle.LEFT);
            labelspaces[i] = new PSpace(labels[i], 0.15, 0.15, 0.15, 0.15);
            p3results[i] = new JLabel("NA");

            spaces[i] = new PSpace(p3results[i], 0.2, 0.1, 0.1, 0.1);

            spaces[i].setbkgrnd(Color.WHITE);

            p3results[i].setBackground(Color.LIGHT_GRAY);
            p3results[i].setOpaque(true);
            p3results[i].setHorizontalAlignment(SwingConstants.CENTER);
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

        for (int i = 0; i < 9; i++) {
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = i;
            gridBagConstraints.weightx = 0.6;
            p3.add(labelspaces[i], gridBagConstraints);
            gridBagConstraints.gridx = 1;
            gridBagConstraints.weightx = 0.4;
            p3.add(spaces[i], gridBagConstraints);
        }

    }

    private void addplowercomps() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        lower.setLayout(new java.awt.GridBagLayout());
        JPanel lower1;
        {
            lower1 = new JPanel();
            lower1.setPreferredSize(new Dimension(0, 0));
            lower1.setLayout(new java.awt.GridBagLayout());
            PSpace lower11;
            {
                subnav = new MyButton("BITE DISPLAY");
                subnav.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        subnavPressed();
                    }
                });
                subnav.setVisible(false);
                lower11 = new PSpace(subnav, 0.1, 0.1, 0.1, 0.1);
            }
            PSpace lower12;
            {
                navigation = new JButton("Monitoring");
                lower12 = new PSpace(navigation, 0.1, 0.1, 0.1, 0.3);
                navigation.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        navigateActionPerformed(e);
                    }
                });
                //lower12.setbkgrnd(Color.CYAN);
            }
            GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();

            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.gridheight = 1;
            gridBagConstraints.gridwidth = 1;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            gridBagConstraints.weightx = 1;
            gridBagConstraints.weighty = 0.5;
            lower1.add(lower11, gridBagConstraints);
            gridBagConstraints.gridy = 1;
            lower1.add(lower12, gridBagConstraints);
        }
//        PSpace lower2;
//        {
//            panelenable(false);
//            connstat = new MultiLineText("NOT CONNECTED TO SENSOR ELECTRONICS");
//            connstat.rectangle.setRounded(false);
//            connstat.setFontsize(12);
//            connstat.rectangle.setFillcolor(Color.RED);
//            connstat.rectangle.setGradientcolor(true);
//            //comp1.setFonttype(Font.PLAIN);
//            connstat.rectangle.setStroke(4.0f);
//            connstat.setOutlined(true);
//            lower2 = new PSpace(connstat, 0.2, 0.5, 0.1, 0.2);
//            //lower2.setbkgrnd((Color.CYAN));
//        }
        JPanel lower2;
        {
            lower2 = new JPanel();
            lower2.setPreferredSize(new Dimension(0, 0));
            lower2.setLayout(new java.awt.GridBagLayout());
            JLabel blank = new JLabel();
            blank.setPreferredSize(new Dimension(0, 0));
            modConn = new JLabel("NOT CONNECTED TO MODULATOR");
            modConn.setPreferredSize(new Dimension(0, 0));
            modConn.setHorizontalAlignment(SwingConstants.CENTER);
            modConn.setForeground(Color.red);
            senseConn = new JLabel("NOT CONNECTED TO MTSG");
            senseConn.setPreferredSize(new Dimension(0, 0));
            senseConn.setHorizontalAlignment(SwingConstants.CENTER);
            senseConn.setForeground(Color.red);
            GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.gridheight = 1;
            gridBagConstraints.gridwidth = 1;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            gridBagConstraints.weightx = 1;
            gridBagConstraints.weighty = 0.5;
            lower2.add(blank, gridBagConstraints);
            gridBagConstraints.gridy = 1;
            gridBagConstraints.weighty = 0.25;
            lower2.add(modConn, gridBagConstraints);
            gridBagConstraints.gridy = 2;
            gridBagConstraints.weighty = 0.25;
            lower2.add(senseConn, gridBagConstraints);
        }
        JPanel lower3;
        {
            lower3 = new JPanel();
            lower3.setPreferredSize(new Dimension(0, 0));
            lower3.setLayout(new java.awt.GridBagLayout());
            PSpace lower31;
            {
                Photoframe photoframe = new Photoframe("pic.png");
                lower31 = new PSpace(photoframe, 0.4, 0.1, 0.1, 0.1);
            }
            time = new JLabel(Time.getTime());
            time.setHorizontalAlignment(SwingConstants.CENTER);
            PSpace lower32 = new PSpace(time, 0.4, 0, 0, 0);

            date = new JLabel(Time.getDate());
            date.setHorizontalAlignment(SwingConstants.CENTER);
            PSpace lower33 = new PSpace(date, 0, 0, 0.4, 0);

            GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.gridheight = 1;
            gridBagConstraints.gridwidth = 2;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            gridBagConstraints.weightx = 1;
            gridBagConstraints.weighty = 0.7;
            lower3.add(lower31, gridBagConstraints);
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 1;
            gridBagConstraints.gridwidth = 1;
            gridBagConstraints.weightx = 0.6;
            gridBagConstraints.weighty = 0.3;
            lower3.add(lower32, gridBagConstraints);
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 1;
            gridBagConstraints.weightx = 0.4;
            lower3.add(lower33, gridBagConstraints);
        }
        GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 1;

        lower.add(lower1, gridBagConstraints);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.weightx = 0.5;
        lower.add(lower2, gridBagConstraints);
        gridBagConstraints.gridx = 2;
        gridBagConstraints.weightx = 0.3;
        lower.add(lower3, gridBagConstraints);
    }
    Timer datetimeupdate = new Timer(1000, new ActionListener() {
        private boolean datechanged = true;
        boolean initialized = false;

        @Override
        public void actionPerformed(ActionEvent e) {//To change body of generated methods, choose Tools | Templates.                

            time.setText(Time.getTime());
            datechanged = Time.isDatechanged();
            if (datechanged || (!initialized)) {
                if (!initialized) {
                    initialized = true;
                }
                date.setText(Time.getDate());
            }
        }
    });

    private void navigateActionPerformed(ActionEvent e) {
        if (CntNCmdvflag) {
            Monitoringvflag = true;
            CntNCmdvflag = false;
            getContentPane().remove(b1);
            getContentPane().remove(b2);
            getContentPane().remove(b3);
            getContentPane().remove(interlockMsg);
            getContentPane().remove(bd);
            getContentPane().remove(name1);
            getContentPane().remove(name2);
            getContentPane().remove(name3);
            getContentPane().remove(name4);
        } else if (Monitoringvflag) {
            CntNCmdvflag = true;
            Monitoringvflag = false;
            getContentPane().remove(mselpanel);
        } else if (any(buttonflags)) {
            Monitoringvflag = true;
            int index = which(buttonflags);
            buttonflags[index] = false;

            switch (index) {
                case 0:
                    getContentPane().remove(flowStatus);
                    break;
//                case 3:
//                    subnav.setVisible(false);
//                    bitedetail.removeAll();
//                    getContentPane().remove(mpanels[3]);
//                    getContentPane().remove(bitedetailframe);
//                    getContentPane().remove(bd);
//                    getContentPane().remove(name4);
//                    break;
                default:
                    if (index > 1) {
                        getContentPane().remove(mpanels[index + 2]);
                    } else {
                        getContentPane().remove(mpanels[index]);
                    }
                    getContentPane().remove(interlockMsg);
                    getContentPane().remove(bd);
                    getContentPane().remove(name4);
            }
        }

        applyvflags();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JMenuItem sendStatReq2Sense;
    private javax.swing.JMenuItem statreq;
    // End of variables declaration//GEN-END:variables

    private void applyvflags() {
        if (CntNCmdvflag) {
            desc.rectangle.settext("CONTROL AND COMMANDS");
            navigation.setText("MONITORING");
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 1;
            gridBagConstraints.gridheight = 2;
            gridBagConstraints.gridwidth = 1;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            gridBagConstraints.weightx = 0.25;
            gridBagConstraints.weighty = 0.55;
            gridBagConstraints.insets = new Insets(0, 20, 0, 0);
            getContentPane().add(b1, gridBagConstraints);

            gridBagConstraints.insets = new Insets(0, 0, 0, 0);
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 1;
            gridBagConstraints.gridheight = 2;
            gridBagConstraints.gridwidth = 1;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            gridBagConstraints.weightx = 0.25;
            gridBagConstraints.weighty = 0.55;
            getContentPane().add(b2, gridBagConstraints);

            gridBagConstraints.gridx = 2;
            gridBagConstraints.gridy = 1;
            gridBagConstraints.gridheight = 2;
            gridBagConstraints.gridwidth = 1;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            gridBagConstraints.weightx = 0.25;
            gridBagConstraints.weighty = 0.55;
            getContentPane().add(b3, gridBagConstraints);
            {
                gridBagConstraints.gridx = 3;
                gridBagConstraints.gridy = 1;
                gridBagConstraints.gridheight = 1;
                gridBagConstraints.gridwidth = 1;
                gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                gridBagConstraints.weightx = 0.25;
                gridBagConstraints.weighty = 0.37;
                getContentPane().add(interlockMsg, gridBagConstraints);

                gridBagConstraints.gridx = 3;
                gridBagConstraints.gridy = 2;
                gridBagConstraints.gridheight = 1;
                gridBagConstraints.gridwidth = 1;
                gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                gridBagConstraints.weightx = 0.25;
                gridBagConstraints.weighty = 0.18;
                getContentPane().add(bd, gridBagConstraints);
            }

            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 3;
            gridBagConstraints.gridheight = 1;
            gridBagConstraints.gridwidth = 1;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            gridBagConstraints.weightx = 0.25;
            gridBagConstraints.weighty = 0.1;
            getContentPane().add(name1, gridBagConstraints);

            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 3;
            gridBagConstraints.gridheight = 1;
            gridBagConstraints.gridwidth = 1;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            gridBagConstraints.weightx = 0.25;
            gridBagConstraints.weighty = 0.1;
            getContentPane().add(name2, gridBagConstraints);

            gridBagConstraints.gridx = 2;
            gridBagConstraints.gridy = 3;
            gridBagConstraints.gridheight = 1;
            gridBagConstraints.gridwidth = 1;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            gridBagConstraints.weightx = 0.25;
            gridBagConstraints.weighty = 0.1;
            getContentPane().add(name3, gridBagConstraints);

            gridBagConstraints.gridx = 3;
            gridBagConstraints.gridy = 3;
            gridBagConstraints.gridheight = 1;
            gridBagConstraints.gridwidth = 1;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            gridBagConstraints.weightx = 0.25;
            gridBagConstraints.weighty = 0.1;
            getContentPane().add(name4, gridBagConstraints);

        } else if (Monitoringvflag) {
            desc.rectangle.settext("MONITORING");
            navigation.setText("CONTROL AND COMMANDS");
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 1;
            gridBagConstraints.gridheight = 3;
            gridBagConstraints.gridwidth = 4;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            gridBagConstraints.weightx = 1;
            gridBagConstraints.weighty = 0.65;
            getContentPane().add(mselpanel, gridBagConstraints);
        } else {
            String[] labels = new String[]{"FLOW STATUS", "ANALOG MONITORING", "INTERLOCK DISPLAY", "INTERLOCK BYPASS", "RF DETECTOR CAL TABLE", "TRIGGER DELAY"};
            int index = which(buttonflags);
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            switch (index) {
                case 0:
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 1;
                    gridBagConstraints.gridheight = 3;
                    gridBagConstraints.gridwidth = 4;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    gridBagConstraints.weightx = 1;
                    gridBagConstraints.weighty = 0.65;
                    getContentPane().add(flowStatus, gridBagConstraints);
                    break;
//                case 3:
//                    gridBagConstraints.gridx = 0;
//                    gridBagConstraints.gridy = 1;
//                    gridBagConstraints.gridheight = 1;
//                    gridBagConstraints.gridwidth = 4;
//                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
//                    gridBagConstraints.weightx = 1;
//                    gridBagConstraints.weighty = 0.37;
//                    getContentPane().add(mpanels[3], gridBagConstraints);
//                    gridBagConstraints.gridx = 0;
//                    gridBagConstraints.gridy = 2;
//                    gridBagConstraints.gridheight = 2;
//                    gridBagConstraints.gridwidth = 3;
//                    gridBagConstraints.weightx = 0.75;
//                    gridBagConstraints.weighty = 0.28;
//                    getContentPane().add(bitedetailframe, gridBagConstraints);
//                    gridBagConstraints.gridx = 3;
//                    gridBagConstraints.gridy = 2;
//                    gridBagConstraints.gridheight = 1;
//                    gridBagConstraints.gridwidth = 1;
//                    gridBagConstraints.weightx = 0.25;
//                    gridBagConstraints.weighty = 0.18;
//                    getContentPane().add(bd, gridBagConstraints);
//                    gridBagConstraints.gridx = 3;
//                    gridBagConstraints.gridy = 3;
//                    gridBagConstraints.weightx = 0.25;
//                    gridBagConstraints.weighty = 0.1;
//                    getContentPane().add(name4, gridBagConstraints);
//                    break;
                default:
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 1;
                    gridBagConstraints.gridheight = 3;
                    gridBagConstraints.gridwidth = 3;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    gridBagConstraints.weightx = 0.75;
                    gridBagConstraints.weighty = 0.65;
                    if (index > 1) {
                        getContentPane().add(mpanels[index + 2], gridBagConstraints);
                    } else {
                        getContentPane().add(mpanels[index], gridBagConstraints);
                    }
                    gridBagConstraints.gridx = 3;
                    gridBagConstraints.gridy = 1;
                    gridBagConstraints.gridheight = 1;
                    gridBagConstraints.gridwidth = 1;
                    gridBagConstraints.weightx = 0.25;
                    gridBagConstraints.weighty = 0.37;
                    getContentPane().add(interlockMsg, gridBagConstraints);
                    gridBagConstraints.gridy = 2;
                    gridBagConstraints.weighty = 0.18;
                    getContentPane().add(bd, gridBagConstraints);
                    gridBagConstraints.gridx = 3;
                    gridBagConstraints.gridy = 3;
                    gridBagConstraints.weightx = 0.25;
                    gridBagConstraints.weighty = 0.1;
                    getContentPane().add(name4, gridBagConstraints);
            }
            navigation.setText("MONITORING");
            desc.rectangle.settext(labels[index]);
        }
        repaint();
    }

    private boolean any(boolean[] arr) {
        boolean result = false;
        for (int i = 0; i < arr.length; i++) {
            result = result | arr[i];
        }
        return result;
    }

    private int which(boolean[] arr) {
        int result = -1;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == true) {
                result = i;
                break;
            }
        }
        return result;
    }

    private void replacesubpanel(ActionEvent e) {
        subnav.setVisible(true);
        bitedetail.removeAll();
        JPanel panel = bite.getSubPanel(e);
        //subpanel.setBackground(Color.WHITE);
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1;
        gridBagConstraints.weightx = 1;
        bitedetail.add(panel, gridBagConstraints);
        validate();
        repaint();
    }

    private void subnavPressed() {
        bitedetail.removeAll();
        bitedetail.repaint();
        subnav.setVisible(false);
    }

    private void inputStyleSel() {

        boolean chk1selected = chk1.isSelected();
        boolean chk2selected = chk2.isSelected();

        for (int i = 0; i < prfbtns.length; i++) {
            prfbtns[i].setEnabled(chk1selected);
        }
        for (int i = 0; i < pwbtns.length; i++) {
            pwbtns[i].setEnabled(chk1selected);
        }
        if (true) {//statframe.getModulatorStatus() != Status.ON
            prfchoose.setEnabled(chk2selected);
            pwchoose.setEnabled(chk2selected);
        }

        prfchoose.setText("");
        pwchoose.setText("");
        prfchoose.setBorder(normalborder);
        pwchoose.setBorder(normalborder);
        prfchoose.setForeground(Color.BLACK);
        pwchoose.setForeground(Color.BLACK);

        group1.clearSelection();
        group2.clearSelection();

        pgroup.setVisible(false);
        dutyratioAlarm.setVisible(false);
    }

    private boolean configRadioBtnClicked(ActionEvent e) {
        double[] prfvalus = new double[]{5.0, 10.0, 20.0};
        double[] pwvalues = new double[]{10, 5, 2.5};
        int index1 = -1;
        int index2 = -1;
        boolean result = false;
        if (e != null) {
            configDone = false;
            flowStatus.setStatus(TxComps.PW_PRF, Status.ERROR);
            //bd.setStatus(TxComps.PW_PRF, Status.ERROR);
            txStatus.setStatus(0, 5, Status.FINE);

            for (int i = 0; i < prfbtns.length; i++) {
                if (e.getSource().equals(prfbtns[i])) {
                    index1 = i;
                }
            }

            for (int i = 0; i < pwbtns.length; i++) {
                if (e.getSource().equals(pwbtns[i])) {
                    index2 = i;
                }
            }

        } else {
            for (int i = 0; i < prfbtns.length; i++) {
                if (prfbtns[i].isSelected()) {
                    index1 = i;
                }
            }

            for (int i = 0; i < pwbtns.length; i++) {
                if (pwbtns[i].isSelected()) {
                    index2 = i;
                }
            }
        }
        if (index2 != -1) {
            pw = pwvalues[index2];
            if (e != null) {
                prf = prfvalus[index2];
                prfbtns[index2].setSelected(true);
            }
        }
        if (index1 != -1) {
            prf = prfvalus[index1];
            if (e != null) {
                pw = pwvalues[index1];
                pwbtns[index1].setSelected(true);
            }
        }
        if ((index1 != -1) || (index2 != -1)) {
            result = dutyRatioOK();
            boolean configPermissible = (e != null) && result;
            pgroup.setVisible(configPermissible);
        }
        return result;
    }

    private boolean dutyRatioOK() {
        double dutyratioThreshold = 0.05;
        double dutyratio = pw * prf * 1e-3;
        String str = String.format("%.3f", dutyratio);
        dutyratioAlarm.setText("Duty Ratio = " + str);
        boolean result = dutyratio <= dutyratioThreshold;
        Color c = result ? new Color(240, 240, 240) : Color.RED;
        dutyratioAlarm.setBackground(c);
        dutyratioAlarm.setVisible(true);
        return result;
    }

    private boolean verify(JComponent input, double min, double max) {

        boolean retvalue = false;
        JTextField t = (JTextField) input;
        String text = t.getText();
        double val;
        try {
            val = Double.parseDouble(text);

            if ((val > max) || (val < min)) {
                t.setForeground(Color.red);
                t.setBorder(javax.swing.BorderFactory.createLineBorder(Color.RED));
                JOptionPane.showMessageDialog(null, "value outside valid range from " + min + " to " + max);
                dutyratioAlarm.setVisible(false);
            } else {
                t.setForeground(Color.BLACK);
                t.setBorder(normalborder);
                retvalue = true;
            }

        } catch (NumberFormatException ex) {
            t.setForeground(Color.red);
            t.setBorder(javax.swing.BorderFactory.createLineBorder(Color.RED));
            dutyratioAlarm.setVisible(false);
        }

        return retvalue;
    }

    private boolean verification1() {
        boolean result;
        if (chk2.isSelected()) {
            prf = -1;
            pw = -1;
            boolean dutyOK = false;
            boolean verify1 = verify(prfchoose, 0.1, 20);
            boolean verify2 = verify(pwchoose, 0.2, 13);
            if (verify1 && verify2) {
                String text1 = prfchoose.getText();
                String text2 = pwchoose.getText();
                if (!text1.equals("")) {
                    prf = Double.parseDouble(text1);
                }
                if (!text2.equals("")) {
                    pw = Double.parseDouble(text2);
                }

                if (!text1.equals("") && !text2.equals("")) {
                    dutyOK = dutyRatioOK();

                }
            }
            result = verify1 && verify2 && dutyOK;
        } else {
            result = prfbtns[0].isSelected() || prfbtns[1].isSelected() || prfbtns[2].isSelected();
        }
        return result;
    }

    private boolean verification2() {
        boolean verify1 = verify(CVtext, -18, -15);
        boolean verify2 = verify(BVtext, -60, 0);
        boolean verify3 = verify(FVtext, -8.5, -5.5);
        boolean verify4 = verify(warmUPTimeText, 0, 180);
        boolean result = verify1 && verify2 && verify3 && verify4;
        if (result) {
            XMLReadWrite writer = new XMLReadWrite("conf.xml");
            String text1 = CVtext.getText();
            String text2 = BVtext.getText();
            String text3 = FVtext.getText();
            String text4 = warmUPTimeText.getText();
            if (!text1.equals("")) {
                CV = Double.parseDouble(text1);
                writer.Write("CathodeVoltage", text1);
            }
            if (!text2.equals("")) {
                BV = Double.parseDouble(text2);
                writer.Write("GridVoltage", text2);
            }
            if (!text3.equals("")) {
                FV = Double.parseDouble(text3);
                writer.Write("FilamentVoltage", text3);
            }
            if (!text4.equals("")) {
                warmUPTime = (int) Double.parseDouble(text4);
            }
            writer.printToFile();
//            if (!text1.equals("") && !text2.equals("")) {
//                if (prfpwset) {
//                    pgroup.setVisible(true);
//                }
//            }
        }
        return result;
    }

    private boolean btnPressed(int index, int flag) {
        boolean mainsOnPressed = false;
//        if (any(offclicked) || any(onclicked)) {
//            JOptionPane.showMessageDialog(ons[0], "pending operation detected");
//            return false;
//        }

        if (flag == Command.ON) {
            if (OnOffstat[index] == Command.ON) {
                JOptionPane.showMessageDialog(ons[index], "already ON");
                return false;
            } else {
                if (!manualTimer.isRunning()) {
                    manualTimer.start();
                    onclicked[index] = true;
                    ons[index].setBackground(Color.ORANGE);
                } else {
                    JOptionPane.showMessageDialog(ons[index], "Interval required");
                    return false;
                }
            }
        } else {
            if (OnOffstat[index] == Command.OFF) {
                JOptionPane.showMessageDialog(ons[index], "already OFF");
                return false;
            } else {
                offclicked[index] = true;
                offs[index].setBackground(Color.ORANGE);
            }
        }
        boolean verificationSuccess = false;
        switch (index) {
            case 0:
                if (verificationSuccess = verifyPrerequisiteMains(flag)) {
                    digitalctrlframe.setMains(flag);
                    mainsOnPressed = true;
                    reset();
                }
                break;
            case 1:
                if (verificationSuccess = verifyPrerequisiteHV(flag)) {
                    digitalctrlframe.setHV(flag);
                }
                break;
            case 2:
                if (verificationSuccess = verifyPrerequisiteModulator(flag)) {
                    digitalctrlframe.setModulator(flag);
                }
                break;
            case 3:
                //digitalctrlframe.setReset(flag);
                startMTSG(null);
                break;
            case 4:
        }
        if (!verificationSuccess) {
            if (flag == Command.ON) {
                onclicked[index] = false;
            } else {
                offclicked[index] = false;
            }
            return false;
        }

        packetM = digitalctrlframe.getPacket();
        for (int i = 0; i < 15; i++) {
            String text = getString(packetM, 2 * i) + " " + getString(packetM, 2 * i + 1);
            displayFrame.sdisp[i].setText(text);
        }
//        for (int i = 7; i < 15; i++) {
//            displayFrame.sdisp[i].setText("");
//        }
//        boolean result = false;
//        for (int i = 0; i < 10; i++) {
//            try {
//                Thread.sleep(50);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            result = sendtoModulator(packetM);
//        }
        if (mainsOnPressed) {
            mainsONTimer.restart();
        }
        return modulatorConnected;
    }

    private void setMonitoringStatus(byte[] buf) {

        analogstat.setArr(buf);
        String text;
        for (int i = 0; i < 6; i++) {
            if (i == 1 && digitalInputstat.getHVStatus() == Status.OFF) {
                text = String.format("%.1f", 0.0d);

            } else if (i == 4 && digitalInputstat.getMainsStatus() == Status.ON && digitalInputstat.getModulatorStatus() == Status.OFF) {
                text = String.format("%.1f", 0.0);

            } else if (i == 5) {
                if (digitalInputstat.getHVStatus() == Status.OFF) {
                    text = String.format("%.1f", 0.0d);
                } else {
                    double catv = Double.parseDouble(analogstat.cathodeCurrent.getValueAsString());
//                    double colv = Double.parseDouble(analogstat.collectorCurrent.getValueAsString());
                    text = String.format("%.1f", catv);
                }

            } else {
                text = analogstat.fields.get(modFieldMap[i]).getValueAsString();
            }

            analogMonitoring.setValue(0, i, text);
            if (i == 0) {
                p3results[8].setText(text);
            }
            if (i == 1) {
                p3results[6].setText(text);
            }
            if (i == 2) {
                p3results[7].setText(text);
            }
            if (i == 3) {
                p3results[5].setText(text);
            }

        }

//        Log.addtoLog(analogstat);
        //intrlock generation
        double fv = ((SH.A2D) analogstat.filamentVoltage).getValueWithDecimal();
        double cv = ((SH.A2D) analogstat.cathodeVoltage).getValueWithDecimal();
        double bv = ((SH.A2D) analogstat.beamOnVoltage).getValueWithDecimal();
        double fvdev = fv - FV;
        double cvdev = cv - CV;
        double bvdev = bv - BV;
        if (currentStage >= MAINS) {
            if (!checkDelayTimer.isRunning()) {
                if (!intByp3.chks[1].isSelected()) {
                    if ((fvdev < -FVLimit) || (fvdev > FVLimit)) {
                        FVfaultCount++;
                        if (FVfaultCount == 5) {
                            //System.out.println(fvdev);
                            intStat1.setStatus(7, Status.ERROR, "NOT OK");
                            //btnPressed(0, Command.OFF);
                            FVfaultCount = 0;
                        }
                    } else {
                        FVfaultCount = 0;
                        intStat1.setStatus(7, Status.FINE, "OK");
                    }
                }
            }
        }
        if (currentStage >= HV) {
            if (!checkDelayTimer.isRunning()) {
                if (!intByp3.chks[2].isSelected()) {
                    if ((cvdev < -CVLimit) || (cvdev > CVLimit)) {
                        CVfaultCount++;
                        if (CVfaultCount == 5) {
                            System.out.println(cvdev);
                            intStat1.setStatus(8, Status.ERROR, "NOT OK");
                            btnPressed(1, Command.OFF);
                            CVfaultCount = 0;
                        }
                    } else {
                        CVfaultCount = 0;
                        intStat1.setStatus(8, Status.FINE, "OK");
                    }
                }
            }
        }
        if (currentStage >= MOD) {
            if (!checkDelayTimer.isRunning()) {
                if (!intByp3.chks[3].isSelected()) {
                    if ((bvdev < -BVLimit) || (bvdev > BVLimit)) {
                        BVfaultCount++;
                        if (BVfaultCount == 5) {
                            System.out.println(bvdev);
                            intStat1.setStatus(9, Status.ERROR, "NOT OK");
                            btnPressed(2, Command.OFF);
                            BVfaultCount = 0;
                        }
                    } else {
                        BVfaultCount = 0;
                        intStat1.setStatus(9, Status.FINE, "OK");
                    }
                }
            }
        }

        digitalInputstat.setArr(buf);
        int overallstat = digitalInputstat.getStatus();
       
        if ((overallstat == Status.ERROR) && (digitalInputstat.getMainsStatus() == Status.ON)) {
            if (digitalInputstat.fields.get(14).getValue() != 0) {
                interlockMsg.setFaultDetail(digitalInputstat.fields.get(14).getText());
            }
            interlockMsg.setAlert(true,InterlockMsg.Modulator);            

        } else {
            interlockMsg.setAlert(false,InterlockMsg.Modulator);
        }

        for (int i = 0; i < 7; i++) {
            int status = digitalInputstat.fields.get(map1[i]).getStatus();
            String txt = digitalInputstat.fields.get(map1[i]).getText();
            intStat1.setStatus(i, status, txt);
        }
        int v = digitalInputstat.remote_local.getValue();
        bd.setStatus(TxComps.LOCAL_REMOTE, (v == 1) ? Status.FINE : Status.ERROR);
        v = digitalInputstat.extmodpulse.getValue();
        intSelected = v == 0;
        extSelected = v == 1;
        if (intSelected) {
            if (intChk) {
                p1btns[0].setBackground(Color.GREEN);
                p1btns[0].label.setColor(Color.BLACK);
                p1btns[1].setBackground(Color.RED);
                p1btns[1].label.setColor(Color.WHITE);
                intChk = false;
                extChk = true;
                digitalctrlframe.setInt();
            }

        } else {//ext selected
            if (extChk) {
                p1btns[1].setBackground(Color.GREEN);
                p1btns[1].label.setColor(Color.BLACK);
                p1btns[0].setBackground(Color.RED);
                p1btns[0].label.setColor(Color.WHITE);
                intChk = true;
                extChk = false;
                digitalctrlframe.setExt();
            }
        }

        int value = digitalInputstat.FAULT_STATUS.getValue();
        if (value != 0) {
            digitalInputstat.FAULT_STATUS.getValue();
        }
//        System.out.println(value);
        int[] bits = getbits(buf[9]);
        for (int i = 0; i < 3; i++) {
            int val = digitalInputstat.fields.get(i + 1).getValue();
            if (i == 0) {
                val = val - 1;
            }
            if (val == 1) {
                if (OnOffstat[i] == Status.OFF) {
                    OnOffstat[i] = Status.ON;
                    if (i == 1) {
                        mainsONTimer.stop();
                    }
                    if (autoONinprogress) {
                        autoONHandler(i);
                        if (i == 2) {
                            autoONinprogress = false;
                            manual.setBackground(Color.GREEN);
                            autoON.setBackground(Color.red);
                        }
                    }
                }
                if (onclicked[i] = true) {
                    onclicked[i] = false;
//                    
                }
                ons[i].setBackground(Color.GREEN);
                ons[i].setForeground(Color.BLACK);
                offs[i].setBackground(Color.RED);
                offs[i].setForeground(Color.WHITE);
            } else {
                if (OnOffstat[i] == Status.ON) {
                    OnOffstat[i] = Status.OFF;
                    System.out.println("OFF i =" + i + "autoOFFinprogress=" + autoOFFinprogress);
                    if (i == 0) {
                        mainsONTimer.stop();
                    }
                    if (autoOFFinprogress) {
                        System.out.println("i =" + i);
                        if (i <= 1) {
                            autoOFFinprogress = false;
                            manual.setBackground(Color.GREEN);
                            autoOFF.setBackground(Color.red);
                        } else {
                            autoOFFHandler(i);
                        }
                    }
                }

                offclicked[i] = false;
                ons[i].setBackground(Color.RED);
                ons[i].setForeground(Color.WHITE);
                offs[i].setBackground(Color.GREEN);
                offs[i].setForeground(Color.BLACK);
            }
        }
        boolean flag1 = bits[1] == 0;
        CVtext.setEnabled(flag1);
        BVtext.setEnabled(flag1);
        FVtext.setEnabled(flag1);
        if (digitalInputstat.getModulatorStatus() == Status.ON) {
            flowStatus.setStatus(TxComps.MODULATION, Status.FINE);
            bd.setStatus(TxComps.MODULATION, Status.FINE);
            txStatus.setStatus(0, 8, Status.FINE);
        } else {
            flowStatus.setStatus(TxComps.MODULATION, Status.ERROR);
            bd.setStatus(TxComps.MODULATION, Status.ERROR);
            txStatus.setStatus(0, 8, Status.ERROR);
        }
        if (digitalInputstat.getHVStatus() == Status.ON) {
            flowStatus.setStatus(TxComps.HV, Status.FINE);
            bd.setStatus(TxComps.HV, Status.FINE);
            txStatus.setStatus(0, 7, Status.FINE);
        } else {
            flowStatus.setStatus(TxComps.HV, Status.ERROR);
            bd.setStatus(TxComps.HV, Status.ERROR);
            txStatus.setStatus(0, 7, Status.ERROR);
        }
        if (digitalInputstat.getMainsStatus() == Status.ON) {
            boolean isready = digitalInputstat.ready.getValue() == 1;
            interlockMsg.setDelayNotification(!isready);
            flowStatus.setStatus(TxComps.MODULATOR_MAINS, Status.FINE);
            bd.setStatus(TxComps.MODULATOR_MAINS, Status.FINE);
            txStatus.setStatus(0, 6, Status.FINE);
//                    wdgTimer.restart();
//                    //Local/remote
//                    p1btns[0].setBackground(Color.RED);
//                    p1btns[1].setBackground(Color.GREEN);
        } else {
            interlockMsg.setDelayNotification(false);
            flowStatus.setStatus(TxComps.MODULATOR_MAINS, Status.ERROR);
            bd.setStatus(TxComps.MODULATOR_MAINS, Status.ERROR);

            txStatus.setStatus(0, 6, Status.ERROR);
//                    wdgTimer.stop();

        }

        boolean flag2 = bits[3] == 0;
        for (int i = 0; i < prfbtns.length; i++) {
            prfbtns[i].setEnabled(chk1.isSelected() && flag2);
            pwbtns[i].setEnabled(chk1.isSelected() && flag2);
        }
        prfchoose.setEnabled(chk2.isSelected() && flag2);
        pwchoose.setEnabled(chk2.isSelected() && flag2);

        for (int i = 0; i < 4; i++) {
            p1btns[i].setEnabled(flag2);
        }

        boolean flag3 = (digitalInputstat.getMainsStatus() != Status.OFF);
        //autoOFF.setEnabled(flag3);

        boolean flag4 = (digitalInputstat.getModulatorStatus() != Status.ON);
        //autoON.setEnabled(flag4);
//        Log.addtoLog(digitalInputstat);
        //assigning current stage
        if (digitalInputstat.getModulatorStatus() == Status.ON) {
            if (currentStage != MOD) {
                currentStage = MOD;
                checkDelayTimer.setInitialDelay(30000);
                checkDelayTimer.restart();
            }
        } else if (digitalInputstat.getHVStatus() == Status.ON) {
            if (currentStage != HV) {
                currentStage = HV;
                checkDelayTimer.setInitialDelay(30000);
                checkDelayTimer.restart();
            }
        } else if (digitalInputstat.getMainsStatus() == Status.ON) {
            if (currentStage != MAINS) {
                currentStage = MAINS;
                checkDelayTimer.setInitialDelay(30000);
                checkDelayTimer.restart();
            }
        } else {
            currentStage = -1;
            if (digitalInputstat.emergencyStop.getValue() == 1) {
                if (digitalInputstat.getMainsStatus() == Status.OFF) {
                    digitalctrlframe.setStop(Status.OFF);
                    stopButton.setBackground(Color.YELLOW);
                }
            }
        }
        if (otg) {
            if (currentStage >= MAINS) {
                digitalctrlframe.setMains(Status.ON);
                if (currentStage >= HV) {
                    digitalctrlframe.setHV(Status.ON);
                    if (currentStage == MOD) {
                        digitalctrlframe.setModulator(Status.ON);
                    }
                }
            }            
            packetM = digitalctrlframe.getPacket();
            otg = false;
        }
        
        
        
        //panel p2
//                int[] bits = getbits(buf[9]);
//                for (int i = 0; i < 3; i++) {
//                    if (digitalOutputstat.fields.get(4 - i).getValue() == 1) {
//                        if (OnOffstat[i] == Status.OFF) {
//                            OnOffstat[i] = Status.ON;
//                            if (autoONinprogress) {
//                                autoONHandler(i);
//                                if (i == 2) {
//                                    autoONinprogress = false;
//                                    manual.setBackground(Color.GREEN);
//                                    autoON.setBackground(Color.red);
//                                }
//                            }
//                        }
//                        if (onclicked[i] = true) {
//                            onclicked[i] = false;
////                    
//                        }
//                        ons[i].setBackground(Color.GREEN);
//                        offs[i].setBackground(Color.RED);
//                    } else {
//                        if (OnOffstat[i] == Status.ON) {
//                            OnOffstat[i] = Status.OFF;
//                            if (autoOFFinprogress) {
//                                autoOFFHandler(i);
//                                if (i == 0) {
//                                    autoOFFinprogress = false;
//                                    manual.setBackground(Color.GREEN);
//                                    autoOFF.setBackground(Color.red);
//                                }
//                            }
//                        }
//
//                        offclicked[i] = false;
//                        ons[i].setBackground(Color.RED);
//                        offs[i].setBackground(Color.GREEN);
//                    }
//                }
//                boolean flag1 = bits[0] == 0;
//                CVtext.setEnabled(flag1);
//                BVtext.setEnabled(flag1);
//                if (digitalOutputstat.getMainsStatus() == Status.ON) {
//                    flowStatus.setStatus(TxComps.MODULATOR_MAINS, Status.FINE);
//                    bd.setStatus(TxComps.MODULATOR_MAINS, Status.FINE);
//                    txStatus.setStatus(0, 6, Status.FINE);
////                    wdgTimer.restart();
////                    //Local/remote
////                    p1btns[0].setBackground(Color.RED);
////                    p1btns[1].setBackground(Color.GREEN);
//                } else {
//                    flowStatus.setStatus(TxComps.MODULATOR_MAINS, Status.ERROR);
//                    bd.setStatus(TxComps.MODULATOR_MAINS, Status.ERROR);
//
//                    txStatus.setStatus(0, 6, Status.ERROR);
////                    wdgTimer.stop();
//
//                }
//                if (digitalOutputstat.getHVStatus() == Status.ON) {
//                    flowStatus.setStatus(TxComps.HV, Status.FINE);
//                    bd.setStatus(TxComps.HV, Status.FINE);
//                    txStatus.setStatus(0, 7, Status.FINE);
//                } else {
//                    flowStatus.setStatus(TxComps.HV, Status.ERROR);
//                    bd.setStatus(TxComps.HV, Status.ERROR);
//                    txStatus.setStatus(0, 7, Status.ERROR);
//                }
//                if (digitalOutputstat.getModulatorStatus() == Status.ON) {
//                    flowStatus.setStatus(TxComps.MODULATION, Status.FINE);
//                    bd.setStatus(TxComps.MODULATION, Status.FINE);
//                    txStatus.setStatus(0, 8, Status.FINE);
//                } else {
//                    flowStatus.setStatus(TxComps.MODULATION, Status.ERROR);
//                    bd.setStatus(TxComps.MODULATION, Status.ERROR);
//                    txStatus.setStatus(0, 8, Status.ERROR);
//                }
//                boolean flag2 = bits[2] == 0;
//
//                for (int i = 0; i < prfbtns.length; i++) {
//                    prfbtns[i].setEnabled(chk1.isSelected() && flag2);
//                    pwbtns[i].setEnabled(chk1.isSelected() && flag2);
//                }
//                prfchoose.setEnabled(chk2.isSelected() && flag2);
//                pwchoose.setEnabled(chk2.isSelected() && flag2);
//
//                boolean flag3 = (digitalOutputstat.getMainsStatus() != Status.OFF);
//                autoOFF.setEnabled(flag3);
//
//                boolean flag4 = (digitalOutputstat.getModulatorStatus() != Status.ON);
//                autoON.setEnabled(flag4);
//        //temporary
        for (int i = 0; i < 15; i++) {
            String text1 = getString(bufM, 2 * i + 9) + " " + getString(bufM, 2 * i + 1 + 9);
            displayFrame.rdisp[i].setText(text1);
        }
//            for (int i = expectedLength / 2 + 1; i < 15; i++) {
//                displayFrame.rdisp[i].setText("");
//            }

    }

    private void setSensorMonitoringStatus(byte[] buf) {

        senseStatus.setArr(buf);
        senseStatus.getStatus();
        for (int i = 0; i < 15; i++) {
            String text = getString(buf, 2 * i) + " " + getString(buf, 2 * i + 1);
            displayFrame.rdisp[i].setText(text);
        }
        for (int i = 15; i < 15; i++) {
            displayFrame.rdisp[i].setText("");
        }
        double fwdPwr = 0, revPwr;
        for (int i = 0; i < 5; i++) {

            String str;
            if (i < 4) {
                int valx = senseStatus.fields.get(i).getValue();
                int valxx = 4 * (valx / 4);
                double valy = this.calTable1.getYfromX(valxx, i);
                if (i == 1) {
                    fwdPwr = valy;
                }
                if (i == 2) {
                    revPwr = valy;

                    double retLoss = fwdPwr - revPwr;
                    double r = Math.exp(0.115 * retLoss);
                    double vswr = (r + 1) / (r - 1);
                    if (vswr >= VSWRLIMIT && fwdPwr > 40) {
                        //System.out.println("VSWR limit reached");
                        VSWRinterlockset = true;
                        intStat2.setStatus(6, Status.ERROR, "NOT OK");
                        stopMTSG(null);
                        if (!autoOFFinprogress) {
                            startAutoOFF();
                        }
                    }

                    str = String.format("%.1f", retLoss);
                } else {
                    str = String.format("%.1f", valy);
                }
            } else {
                str = senseStatus.fields.get(i).getText();
            }
            analogMonitoring.setValue(1, i, str);
        }

        for (int i = 0; i < 4; i++) {
            int status = senseStatus.fields.get(map2[i]).getStatus();
            String text = senseStatus.fields.get(map2[i]).getText();
            intStat2.setStatus(i, status, text);

        }
        p3setstatus(0, senseStatus.DIL.getStatus(), senseStatus.DIL.getText());
        p3setstatus(1, senseStatus.ADIL.getStatus(), senseStatus.ADIL.getText());
        p3setstatus(2, senseStatus.BLIL.getStatus(), senseStatus.BLIL.getText());
        int value = senseStatus.pressure.getValue();
        int wgpressure = senseStatus.pressureIL.getStatus();
        String wgpressuretext = senseStatus.pressureIL.getText();
        intStat2.setStatus(4, wgpressure, String.valueOf(value));
        for (int i = 5; i < 9; i++) {
            int status = senseStatus.fields.get(map2[i]).getStatus();
            String text = senseStatus.fields.get(map2[i]).getText();
            if (i != 6) {
                intStat2.setStatus(i, status, text);
            }
        }
        p3setstatus(3, wgpressure, wgpressuretext);
        //p3setstatus(4, wgpressurelow, wgpressurelowtext);
        p3setstatus(4, senseStatus.CTIL.getStatus(), senseStatus.CTIL.getText());
        if(senseStatus.getStatus()==Status.ERROR){
            interlockMsg.setFaultDetail(null);
            interlockMsg.setAlert(true,InterlockMsg.Sensor);
        }else{
            interlockMsg.setAlert(false,InterlockMsg.Sensor);
        }
        
    }

    private void panelenable(boolean flag) {
        chk1.setEnabled(flag);
        chk2.setEnabled(flag);
        CVtext.setEnabled(flag);
        BVtext.setEnabled(flag);
        FVtext.setEnabled(flag);
        pgroup.setEnabled(flag);
        for (int i = 0; i < prfbtns.length; i++) {
            prfbtns[i].setEnabled(flag);
            pwbtns[i].setEnabled(flag);
        }
        for (int i = 0; i < ons.length; i++) {
            ons[i].setEnabled(flag);
            offs[i].setEnabled(flag);
        }
        for (int i = 0; i < p1btns.length; i++) {
            p1btns[i].setEnabled(flag);
        }
        manual.setEnabled(flag);
        autoON.setEnabled(false);
        autoOFF.setEnabled(false);
        intReset.setEnabled(flag);
        for (int i = 0; i < p3results.length; i++) {
            p3results[i].setEnabled(flag);
        }
    }

    private boolean verifyPrerequisiteMains(int flag) {
        boolean result = false;
        if (flag == Command.ON) {
            if (verification2()) {
                analogctrlframe.cathodeVoltage.setValueWithDecimal(CV);
                analogctrlframe.beamOnVoltage.setValueWithDecimal(BV);
                analogctrlframe.filamentVoltage.setValueWithDecimal(FV);
                analogctrlframe.warmUpTimer.setValue(warmUPTime);
                result = true;
            } else {
                JOptionPane.showMessageDialog(ons[0], "voltage setting NOT OK");
            }

        } else {

            result = (digitalInputstat.getHVStatus() == Status.OFF);
            if (result == false) {
                JOptionPane.showMessageDialog(ons[1], "Next stage should be OFF first");
            }
        }
        return result;
    }

    private boolean verifyPrerequisiteHV(int flag) {
        boolean result;
        if (digitalInputstat.ready.getValue() != 1) {
            JOptionPane.showMessageDialog(ons[1], "System not yet ready");
            return false;
        }
        if (flag == Command.ON) {
            result = (digitalInputstat.getMainsStatus() == Status.ON);
            if (result == false) {
                JOptionPane.showMessageDialog(ons[1], "Previous stage should be ON first");
            }
        } else {
            result = (digitalInputstat.getModulatorStatus() == Status.OFF);
            if (result == false) {
                JOptionPane.showMessageDialog(ons[1], "Next stage should be OFF first");
            }
        }
        return result;
    }

    private boolean verifyPrerequisiteModulator(int flag) {
        if (flag == Command.ON) {
            boolean result = false;
            boolean prfpwvf;
            if (chk1.isSelected()) {
                prfpwvf = configRadioBtnClicked(null);
            } else {
                prfpwvf = verification1();
            }
            if (prfpwvf) {
                analogctrlframe.PRF.setValueWithDecimal(prf);
                analogctrlframe.pulsewidth.setValueWithDecimal(pw);
                senseFrame.PRF.setValueWithDecimal(prf);
                senseFrame.pulseWidth.setValueWithDecimal(pw);
                senseFrame.DRxwidth.setValueWithDecimal(pw);
                if (!configDone) {
                    pgroup.setVisible(true);
                    JOptionPane.showMessageDialog(ons[0], "First update configuration by pressing config button");
                } else {
                    result = true;
                }
            } else {
                JOptionPane.showMessageDialog(ons[0], "PRF PW setting NOT OK");
            }
            boolean onresult = (digitalInputstat.getHVStatus() == Status.ON);
            if (onresult == false) {
                JOptionPane.showMessageDialog(ons[1], "Previous stage should be ON first");
            }
            return result && onresult;
        } else {
//            boolean result = (digitalInputstat.getResetStatus() == Status.OFF);
//            if (result == false) {
//                JOptionPane.showMessageDialog(ons[1], "Next stage should be OFF first");
//            }
            return true;
        }
    }

    private void reset() {
//        this.setMonitoringStatus(new byte[30]);
        analogstat.setArr(new byte[49]);
        //analogMonitoring.setValue(0, 0, statframe.PRF.getValueAsString());
        //analogMonitoring.setValue(0, 1, statframe.pw.getValueAsString());
        //analogMonitoring.setValue(0, 2, statframe.filamentVoltage.getValueAsString());
        //analogMonitoring.setValue(0, 4, statframe.gridOnVoltage.getValueAsString());
        //analogMonitoring.setValue(0, 5, statframe.cathodeVoltage.getValueAsString());
        //analogMonitoring.setValue(0, 6, statframe.beamOnVoltage.getValueAsString());
        //analogMonitoring.setValue(0, 8, statframe.collectorCurrent.getValueAsString());
        //analogMonitoring.setValue(0, 9, statframe.helixCurrent.getValueAsString());
        p3results[8].setText(analogstat.filamentVoltage.getValueAsString());
    }

    private void startAutoOFF() {
        int index = 0;
        for (int i = OnOffstat.length - 1; i >= 0; i--) {
            if (OnOffstat[i] == Status.ON) {
                index = i;
                break;
            }
        }
        if (autoONinprogress) {
            autoONinprogress = false;
            autoON.setBackground(Color.RED);
            manual.setBackground(Color.GREEN);
        }
        if (index >= 1) {
            autoOFFinprogress = true;
            if (btnPressed(index, Command.OFF)) {
                autoOFF.setBackground(Color.green);
                autoON.setBackground(Color.RED);
                manual.setBackground(Color.RED);
            }
        }
    }

    private void startAutoON() {

        int index = 0;
        for (int i = 0; i < OnOffstat.length; i++) {
            if (OnOffstat[i] == Status.OFF) {
                index = i;
                break;
            }
        }
        if (index < 2) {
            boolean result = false;
            boolean prfpwvf;
            if (chk1.isSelected()) {
                prfpwvf = configRadioBtnClicked(null);
            } else {
                prfpwvf = verification1();
            }
            if (prfpwvf) {
                analogctrlframe.PRF.setValueWithDecimal(prf);
                analogctrlframe.pulsewidth.setValueWithDecimal(pw);
                senseFrame.PRF.setValueWithDecimal(prf);
                senseFrame.pulseWidth.setValueWithDecimal(pw);
                senseFrame.DRxwidth.setValueWithDecimal(pw);
                if (!configDone) {
                    pgroup.setVisible(true);
                    JOptionPane.showMessageDialog(ons[0], "First update configuration by pressing config button");
                    return;
                }
            } else {
                JOptionPane.showMessageDialog(ons[0], "PRF PW setting NOT OK");
                return;
            }
        }
        if (autoOFFinprogress) {
            autoOFFinprogress = false;
            autoOFF.setBackground(Color.RED);
            manual.setBackground(Color.GREEN);
        }

        if (btnPressed(index, Command.ON)) {
            autoONinprogress = true;
            autoON.setBackground(Color.green);
            manual.setBackground(Color.RED);
        }
    }

    private void autoONHandler(int i) {
        if (i < 2) {
            autobtnindex = i + 1;
            autoONTimer.setInitialDelay(autoONdelays[autobtnindex]);
            autoONTimer.start();

        }
    }

    private void autoOFFHandler(int i) {
        if (i > 0) {
            autobtnindex = i - 1;
            System.out.println("stage" + autobtnindex);
            autoOFFTimer.setInitialDelay(autoOFFdelays[autobtnindex]);
            autoOFFTimer.restart();
        }
    }

    void setlevel(int i) {
    }

    private void connectToModulator() {

        try {
            final XMLReadWrite reader = new XMLReadWrite("conf.xml");
            String ipaddrM = reader.getTextByTag("IPAddressM");
            int portnoM = Integer.parseInt(reader.getTextByTag("portnoM"));
            socketM = new Socket(InetAddress.getByName(ipaddrM), portnoM);
            System.out.println("modulator Connected");
            modulatorConnected = true;
            panelenable(true);
            modConn.setText("CONNECTED TO MODULATOR");
            modConn.setForeground(Color.BLUE);
            inputM = new DataInputStream(new BufferedInputStream(socketM.getInputStream()));
            outM = new DataOutputStream(socketM.getOutputStream());
            modstatReqTimer.start();
            //            this.connstat.rectangle.settext("CONNECTED TO MODULATOR");
            //            connstat.rectangle.setFillcolor(Color.GREEN);
            //            connstat.repaint();

            new Thread() {
                @Override
                public void run() {

                    try {
                        while (true) {

                            while (true) {// match txID byte0 & 1 and protocol ID byte 2&3
                                byte byte0 = inputM.readByte();
                                if (byte0 != 1) {
                                    continue;
                                }
                                byte byte1 = inputM.readByte();
                                if (byte1 != 10) {
                                    continue;
                                }
                                byte byte2 = inputM.readByte();
                                if (byte2 != 0) {
                                    continue;
                                }
                                byte byte3 = inputM.readByte();
                                if (byte3 == 0) {
                                    break;
                                }
                            }

                            inputM.readFully(bufM, 4, 5);
                            int code = bufM[7];//command code
                            if (code == 3) {//status packet
                                inputM.readFully(bufM, 9, 40);
                                setMonitoringStatus(bufM);
                            } else if (code == 16) {//control packet acknowledgement
                                inputM.readFully(bufM, 9, 4);
                            }
                        }

                    } catch (IOException ex) {
                        modulatorConnected = false;
                        modConn.setText("NOT CONNECTED TO MODULATOR");
                        modConn.setForeground(Color.RED);
                        panelenable(false);
                        Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }.start();

//            
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void connectToSensorElectronics() {
        try {
            final XMLReadWrite reader = new XMLReadWrite("conf.xml");
            String ipaddrS = reader.getTextByTag("IPAddressS");
            int portnoS = Integer.parseInt(reader.getTextByTag("portnoS"));
            socketS = new Socket(InetAddress.getByName(ipaddrS), portnoS);
            System.out.println("sense electronics Connected");
            senseElConnected = true;
            panelenable(true);
            senseConn.setText("CONNECTED TO MTSG");
            senseConn.setForeground(Color.BLUE);
            inputS = new DataInputStream(new BufferedInputStream(socketS.getInputStream()));
            outS = new DataOutputStream(socketS.getOutputStream());
            sensestatReqTimer.start();
            new Thread() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            while (true) {//pattern match for '&&&&'
                                byte byte0 = inputS.readByte();
                                if (byte0 != '&') {
                                    continue;
                                }
                                byte byte1 = inputS.readByte();
                                if (byte1 != '&') {
                                    continue;
                                }
                                byte byte2 = inputS.readByte();
                                if (byte2 != '&') {
                                    continue;
                                }
                                byte byte3 = inputS.readByte();
                                if (byte3 == '&') {
                                    break;
                                }
                            }
                            byte byte4 = inputS.readByte();
                            if (byte4 == Status.SENSESTAT) {
                                inputS.readFully(bufS, 5, 43);
                                setSensorMonitoringStatus(bufS);
                            }
                        }

                    } catch (IOException ex) {
                        senseElConnected = false;
                        senseConn.setText("NOT CONNECTED TO MTSG");
                        senseConn.setForeground(Color.RED);
                        Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }.start();

        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private int[] getbits(byte b) {
        int[] m = new int[8];
        byte bin = b;
        for (int i = 0; i < 8; i++) {
            int bitpos = i % 8;
            m[i] = (bin >> bitpos) & (0x1);
        }
        return m;
    }

    private void sendReset(int status) {

        if (status == Status.ON) {
            digitalctrlframe.setReset(true);
        } else {
            digitalctrlframe.setReset(false);
        }
        byte[] packetM = digitalctrlframe.getPacket();
        for (int i = 0; i < 7; i++) {
            String text = getString(packetM, 2 * i) + " " + getString(packetM, 2 * i + 1);
            displayFrame.sdisp[i].setText(text);
        }
        for (int i = 7; i < 15; i++) {
            displayFrame.sdisp[i].setText("");
        }

    }

    private boolean sendtoModulator(byte[] packet) {
        boolean result = false;
        try {
            if (modulatorConnected) {
                outM.write(packet);
                outM.flush();
                result = true;
            }
        } catch (IOException ex) {
            modulatorConnected = false;
            modConn.setText("NOT CONNECTED TO MODULATOR");
            modConn.setForeground(Color.RED);
            panelenable(false);
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    private boolean sendtoSenseElectronics(byte[] packet) {
        boolean result = false;
        try {
            if (senseElConnected) {
                outS.write(packet);
                outS.flush();
                result = true;
            }
        } catch (IOException ex) {
            senseElConnected = false;
            senseConn.setText("NOT CONNECTED TO SENSOR ELECTRONICS");
            senseConn.setForeground(Color.RED);
            panelenable(false);
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    private void p3setstatus(int itemIndex, int status, String text) {
        switch (status) {
            case Status.FINE:
                p3results[itemIndex].setBackground(Color.GREEN);
                break;
            case Status.ERROR:
                p3results[itemIndex].setBackground(Color.RED);
                break;
            case Status.DONTCARE:
                p3results[itemIndex].setBackground(Color.WHITE);
                break;
            default:
                ;
        }
        p3results[itemIndex].setText(text);
    }

    private void sendEmergencyStop() {
        if (currentStage >= MAINS) {
            digitalctrlframe.setStop(Status.ON);
            stopButton.setBackground(Color.red);

            packetM = digitalctrlframe.getPacket();
        }
    }
}
