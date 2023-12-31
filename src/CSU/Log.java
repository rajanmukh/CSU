/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CSU;

import Utility.Frame.Status;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Istrac
 */
public class Log {

    private static Calendar c;
    //private long pos;
    private static byte[] buffer;
//    private int bufPt;
    private static File file1, file2;
    private static ModStatFrame prevStatFrame;
    private static RandomAccessFile writeFile1;
    private static RandomAccessFile writeFile2;
    private static int pos1;
    private static int pos2;
   
    

    public static void Initialize() throws IOException {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            c = Calendar.getInstance();
            String dateStr = sdf.format(c.getTime());
            String fileName = dateStr.substring(0, 8) + ".arc";
            file1 = new File("General Log/" + fileName);
            file2 = new File("Error Log/" + fileName);
            buffer = new byte[100];
            prevStatFrame = new ModStatFrame();
            writeFile1 = new RandomAccessFile(file1, "rw");
            writeFile2 = new RandomAccessFile(file2, "rw");
            pos1 = (int) writeFile1.length();
            pos2 = (int) writeFile2.length();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void addtoLog(ModStatFrame currframe) {

        boolean changed = prevStatFrame.isChangeSignificant(currframe);

        prevStatFrame.setArr(currframe.getArr());
        if (changed) {
            try {
                c = Calendar.getInstance();
                long time = c.getTimeInMillis();
                long rem;
                long div = time;
                for (int i = 0; i < 8; i++) {
                    rem = div % 256;
                    div = div / 256;
                    buffer[7 - i] = (byte) rem;
                }
                int len1 = currframe.getArr().length;

                System.arraycopy(currframe.getArr(), 0, buffer, 8, len1);

                for (int j = 0; j < currframe.fields.size(); j++) {
                    String pnct;
                    if (j % 3 == 0) {
                        pnct = "\n";
                    } else {
                        pnct = "   ";
                    }
                    //System.out.print(pnct + currdframe.fields.get(j).getName() + " : " + currdframe.fields.get(j).getText());
                }

                //int stat1 = currframe.getStatus();
                int stat2 = currframe.getStatus();
                int len = 8 + len1;
                if (stat2 == Status.ERROR) {
                    writeFile2.seek(pos2);
                    writeFile2.write(buffer, 0, len);
                    pos2 += len;
                } else {
                    writeFile1.seek(pos1);
                    writeFile1.write(buffer, 0, len);
                    pos1 += len;
                }

            } catch (IOException ex) {
                Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

//    public static void addtoLog(ModStatFrame currdframe) {
//
//        writedigital = prevdigitalstatframe.isChangeSignificant(currdframe);
//        
//        prevdigitalstatframe.setArr(currdframe.getArr());
//        if(writeanalog||writedigital){
//            addtoLog(prevanalogstatframe,currdframe);
//        }
//    }
   
}
