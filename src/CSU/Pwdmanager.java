/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

 /*
 * Pwdmgt.java
 *
 * Created on Nov 18, 2016, 2:32:14 PM
 */
package CSU;

import Utility.XMLReadWrite;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.channels.FileLock;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.button.StandardButtonShaper;
import org.jvnet.substance.skin.ModerateSkin;
import org.jvnet.substance.title.BrushedMetalHeaderPainter;
import org.jvnet.substance.title.Glass3DTitlePainter;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *
 * @author Administrator
 */
public class Pwdmanager extends javax.swing.JFrame {

    class ipverify extends InputVerifier {

        @Override
        public boolean verify(JComponent input) {
            boolean result = true;
            String text = ((JTextField) input).getText();
            if (!text.equals("")) {
                if (text.startsWith(" ")) {
                    JOptionPane.showMessageDialog(null, "field should not start with whitespace");
                    result = false;
                }
            } else {
                result = false;
            }
            Color bc = (result == false) ? Color.RED : Color.BLACK;
            ((JTextField) input).setBorder(javax.swing.BorderFactory.createLineBorder(bc));
            return result;
        }
    }
    private DefaultTableModel model;
    int num = 0;
    String userData[];
    private final XMLReadWrite rwagent;
    private NewUser newUser;
    private Login login;

    /**
     * Creates new form Pwdmgt
     */
    public Pwdmanager() {

        rwagent = new XMLReadWrite("data/UserRegistration.xml");
        rwagent.defineRoot("Accounts");
        userData = rwagent.getAllTextValuesByTag("user");
    }

    public void showLogin() {
        login = new Login(new ipverify());
        login.setLocationRelativeTo(null);
        login.Login.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                logIn();
            }
        });
        login.setVisible(true);
    }

    private void logIn() {
        String userName = login.txtLoginName.getText();
        char[] userPassword = login.txtPassword.getPassword();
        String loginName = "a";
        String passwd = "a";
        String userLevel = "Admin";

        if (userName.equals("") || userPassword.length == 0) {

            JOptionPane.showMessageDialog(null, "Empty username or password ....", "ERROR", 0);

        } else {
            boolean match = false;
            int i = 0;
            while (true) {
                if (userName.equals(loginName) && String.valueOf(userPassword).equals(passwd)) {
                    MainFrame mainFrame = new MainFrame();
                    if (userLevel.equals("Admin")) {
                        mainFrame.setlevel(3);
                    } else if (userLevel.equals("Manager")) {
                        mainFrame.setlevel(2);
                    } else if (userLevel.equals("Operator")) {
                        mainFrame.setlevel(1);
                    }
                    match = true;
                    mainFrame.setLocationRelativeTo(null);
                    mainFrame.setVisible(true);
                    break;
                }
                if (i < userData.length) {
                    String[] userDetails = splitReaddata(userData[i++], "+");
                    loginName = decrypt(userDetails[0]);
                    passwd = decrypt(userDetails[1]);
                    userLevel = decrypt(userDetails[2]);
                } else {
                    break;
                }
            }

            if (!match) {
                JOptionPane.showMessageDialog(null, "Username or Password is Incorrect", "Error", 0);
                login.txtLoginName.setText("");
                login.txtPassword.setText("");
            } else {
                login.dispose();
                dispose();
            }

        }
    }

    public void viewUsers() {
        int noOfUsers = userData.length;
        String[][] accountDetails = new String[noOfUsers][2];

        for (int i = 0; i < noOfUsers; i++) {
            String[] splitData = splitReaddata(userData[i], "+");
            accountDetails[i][0] = decrypt(splitData[0]);
            accountDetails[i][1] = decrypt(splitData[2]);
        }
        model = new DefaultTableModel(accountDetails, new String[]{"login Id", "Level"});
        initComponents();
        setLocationRelativeTo(null);
        setVisible(true);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        AddUser = new javax.swing.JButton();
        Close = new javax.swing.JButton();
        Delete = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Password management");

        AddUser.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        AddUser.setForeground(new java.awt.Color(0, 51, 204));
        AddUser.setText("New User");
        AddUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddUserActionPerformed(evt);
            }
        });

        Close.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        Close.setForeground(new java.awt.Color(0, 51, 204));
        Close.setText("Close");
        Close.setToolTipText("");
        Close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CloseActionPerformed(evt);
            }
        });

        Delete.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        Delete.setForeground(new java.awt.Color(0, 51, 204));
        Delete.setText("Delete");
        Delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteActionPerformed(evt);
            }
        });

        table.setModel(model);
        table.setSelectionBackground(new java.awt.Color(255, 0, 255));
        jScrollPane1.setViewportView(table);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(AddUser)
                        .addGap(27, 27, 27)
                        .addComponent(Delete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Close, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AddUser)
                    .addComponent(Delete)
                    .addComponent(Close))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void AddUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddUserActionPerformed
        newUser = new NewUser(new ipverify());
        newUser.jButton1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                addUser();
            }
        });
        newUser.setLocationRelativeTo(null);
        newUser.setVisible(true);// TODO add your handling code here:
}//GEN-LAST:event_AddUserActionPerformed

    private void CloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CloseActionPerformed
        dispose();
}//GEN-LAST:event_CloseActionPerformed

    private void DeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteActionPerformed

        int selectedRow = table.getSelectedRow();
        int result = JOptionPane.showConfirmDialog(null, "Do You want to delete user : " + model.getValueAt(selectedRow, 0), "Confirmation", 0);
        // System.out.println("y---"+y);
        if (result == JOptionPane.YES_OPTION) {
            model.removeRow(selectedRow);
            userData = rwagent.getAllTextValuesByTag("user");
            rwagent.removeAll("user");
            for (int i = 0; i < userData.length; i++) {
                if (i != selectedRow) {
                    rwagent.add("user", userData[i]);
                }
                rwagent.printToFile();
            }
        }
}//GEN-LAST:event_DeleteActionPerformed
    private void addUser() {

        String loginName = newUser.txtLoginName.getText();
        String Password = new String(newUser.jtxtPassword.getPassword());
        String confirmPassword = new String(this.newUser.jtxtConfirmPassword.getPassword());

        if (!Password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(null, "Passwords doesnt match.", "Error", 0);
        }
        String level = newUser.cmbLevel.getSelectedItem().toString();
        userData = rwagent.getAllTextValuesByTag("user");
        int noOfUsers = userData.length;

        for (int i = 0; i < noOfUsers; i++) {
            String[] splitData = splitReaddata(userData[i], "+");
            String existingName = decrypt(splitData[0]);
            if (loginName.equals(existingName)) {
                JOptionPane.showMessageDialog(null, "Login Name already exists..try different", "Error", 0);
            }
        }

        if ((loginName.charAt(0) == ' ') && (Password.charAt(0) == ' ')) {
            JOptionPane.showMessageDialog(null, "Fields should not start with white space..\n Please retype correctly..", "Error", 0);
        } else if (loginName.equals(" ") || Password.equals(" ")) {
            JOptionPane.showMessageDialog(null, "Login contains empty/irrelevant fields ..\nPlease re-enter all details correctly..", "Error", 0);
        } else {
            newUser.dispose();
            model.addRow(new Object[]{loginName, level});
            rwagent.add("user", encrypt(loginName) + "+" + encrypt(Password) + "+" + encrypt(level));
            rwagent.printToFile();
        }
    }

    private String[] splitReaddata(String userDet, String separator) {
        StringTokenizer strTok = new StringTokenizer(userDet, separator);
        String[] userDetails = new String[strTok.countTokens()];
        int count = 0;
        for (int i = 0; i < userDetails.length; i++) {
            userDetails[count] = strTok.nextToken();
            count++;
        }
        return userDetails;
    }

    private String encrypt(String param) {
        String getString = param;
        String encode = null;
        BASE64Encoder encoder = new BASE64Encoder();

        try {
            encode = encoder.encode(getString.getBytes("UTF-8"));

        } catch (UnsupportedEncodingException e) {
        }
        return encode;
    }

    private String decrypt(String param) {
        String getString = param;
        String decode = null;

        BASE64Decoder decoder = new BASE64Decoder();
        try {
            decode = new String(decoder.decodeBuffer(getString));
        } catch (IOException e) {
        }
        return decode;
    }

    /**
     * @param args the command line arguments
     */
    private static boolean isInstanceAlreadyRunning() {
        try {
            final File file = new File("LockCSU.dat");
            final RandomAccessFile rafile = new RandomAccessFile(file, "rw");
            final FileLock fileLock = rafile.getChannel().tryLock();
            if (fileLock != null) {
                Runtime.getRuntime().addShutdownHook(new Thread() {

                    @Override
                    public void run() {
                        try {
                            fileLock.release();
                            rafile.close();
                            file.delete();
                        } catch (IOException ex) {
                            Logger.getLogger(Pwdmanager.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
                return false;
            }
        } catch (IOException ex) {
            Logger.getLogger(Pwdmanager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    private static void setupUI() {
        try {
            UIManager.setLookAndFeel(new SubstanceLookAndFeel());
            SubstanceLookAndFeel.setSkin(new ModerateSkin());
            SubstanceLookAndFeel.setToUseConstantThemesOnDialogs(true);
            SubstanceLookAndFeel.setCurrentTitlePainter(new BrushedMetalHeaderPainter());
            SubstanceLookAndFeel.setCurrentTitlePainter(new Glass3DTitlePainter());
            SubstanceLookAndFeel.setCurrentButtonShaper(new StandardButtonShaper());
            JFrame.setDefaultLookAndFeelDecorated(true);
            JDialog.setDefaultLookAndFeelDecorated(true);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private static void otherUI(){
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Metal".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    public static void main(String args[]) {
//        setupUI();
        otherUI();
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {

                if (!isInstanceAlreadyRunning()) {
                    Pwdmanager pwdmgt = new Pwdmanager();
                    pwdmgt.showLogin();
                } else {
                    JOptionPane.showMessageDialog(null, "one instance already running");
                }

            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddUser;
    private javax.swing.JButton Close;
    private javax.swing.JButton Delete;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
