/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CSU;

import Utility.Frame.BitArrayField;
import Utility.Frame.Intfield;
import Utility.Frame.Intxfield;
import Utility.Frame.Packet;
import Utility.Frame.Stringfield;
import Utility.XMLReadWrite;
import java.io.File;

/**
 *
 * @author Istrac
 */
public class SenseElectronicsFormat extends Packet {

    private Stringfield header;
    public Intfield prfCount;
    public Intxfield PRF;
    public Intxfield pulseWidth;
    public Intxfield baudWidth;
    public Intfield baudNo;
    public Intfield codeEnable;
    public BitArrayField codeA;
    public BitArrayField codeAbar;
    public BitArrayField codeB;
    public BitArrayField codeBbar;
    public Intfield modeSel;
    public Intxfield zeroKmDelay;
    public Intxfield TRSWpredelay;
    public Intxfield TRSWwidth;
    public Intxfield BISWpredelay;
    public Intxfield BISWwidth;
    public Intxfield DRxpredelay;
    public Intxfield DRxwidth;
    public Intxfield SIMpredelay;
    public Intxfield SIMwidth;
    public Intxfield STCpredelay;
    public Intxfield STCwidth;
    public Intfield intbypass1;
    //public Intfield status;
    public Intfield intbypass2;
    public Intxfield outpwrhigh;
    public Intxfield outpwrlow;
    public Intxfield VSWRhigh;
    public Intfield spare1;
    public Intxfield inpwrhigh;
    public Intxfield inpwrlow;
    public Intxfield temphigh;
    public Intfield spare2;
    public Intxfield pressurehigh;
    public Intxfield pressurelow;
    final private Stringfield footer;
    private SenseElectronicsFormat aux = null;
    public double DRxtrail;

    public SenseElectronicsFormat() {
        super(36);
        super.Endianness = BIG;
        int index = 0;
        field[index++] = header = new Stringfield("header", 4);//0-3
        field[index++] = prfCount = new Intfield("PRFcount", 4);//4-7
        field[index++] = PRF = new Intxfield("PRF", 4, 1000);//8-11
        field[index++] = pulseWidth = new Intxfield("pulseWidth", 2, 100);//12-13
        field[index++] = baudWidth = new Intxfield("baudWidth", 2, 100);//14-15
        field[index++] = baudNo = new Intfield("baudNo", 2);//16-17
        field[index++] = codeEnable = new Intfield("codeEnable", 1);//18
        field[index++] = codeA = new BitArrayField("codeA", 8);//19
        field[index++] = codeAbar = new BitArrayField("codeAbar", 8);//27
        field[index++] = codeB = new BitArrayField("codeB", 8);//35
        field[index++] = codeBbar = new BitArrayField("codeBbar", 8);//43
        field[index++] = modeSel = new Intfield("modeSel", 1);//51
        field[index++] = zeroKmDelay = new Intxfield("zeroKmDelay", 2, 100);//52
        field[index++] = TRSWpredelay = new Intxfield("TxTrig1delay", 2, 100);//54
        field[index++] = TRSWwidth = new Intxfield("TxTrig1width", 2, 100);//56
        field[index++] = BISWpredelay = new Intxfield("BISWpredelay", 2, 100);//58
        field[index++] = BISWwidth = new Intxfield("BISWwidth", 2, 100);//60
        field[index++] = DRxpredelay = new Intxfield("TxTrig2delay", 2, 100);//62
        field[index++] = DRxwidth = new Intxfield("TxTrig2width", 4, 100);//64
        field[index++] = SIMpredelay = new Intxfield("SIMpredelay", 4, 100);//68
        field[index++] = SIMwidth = new Intxfield("SIMwidth", 4, 100);//72
        field[index++] = STCpredelay = new Intxfield("STCpredelay", 2, 100);//76
        field[index++] = STCwidth = new Intxfield("STCwidth", 2, 100);//78
        field[index++] = intbypass1 = new Intfield("intbypass1", 1);//80
        //field[index++] = status = new Intfield("status", 4);//81
        field[index++] = intbypass2 = new Intfield("intbypass2", 1);//81
        field[index++] = outpwrhigh = new Intxfield("outpwrhigh", 2, 1);//82
        field[index++] = outpwrlow = new Intxfield("outpwrlow", 2, 1);//84
        field[index++] = VSWRhigh = new Intxfield("VSWRhigh", 2, 100);//86
        field[index++] = spare1 = new Intfield("spare1", 2);//88
        field[index++] = inpwrhigh = new Intxfield("inpwrhigh", 2, 1);//90
        field[index++] = inpwrlow = new Intxfield("inpwrlow", 2, 1);//92
        field[index++] = temphigh = new Intxfield("temphigh", 2, 1);//94
        field[index++] = spare2 = new Intfield("spare2", 2);//96
        field[index++] = pressurehigh = new Intxfield("pressurehigh", 2, 1);//98
        field[index++] = pressurelow = new Intxfield("pressurelow", 2, 1);//100
        field[index++] = footer = new Stringfield("footer", 4);//102-105
        
        for(int i=0;i<field.length;i++){
            field[i].fieldindex=i;
        }

//        header.setValue("####");
//        PRF.setValue("1");
//        prfCount.setValue("250");
//        zeroKmDelay.setValue("6");
//        pulseWidth.setValue("64");
//        baudWidth.setValue("1");
//        baudNo.setValue("64");
//        codeEnable.setValue("1");
//        int[] code = new int[]{1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0};
//        codeA.packbits(code);
//        int[] codebar= new int[64];
//        for(int i=0;i<64;i++){
//            codebar[i]=1-code[i];
//        }
//        codeAbar.packbits(codebar);
//        codeB.packbits(code);
//        codeBbar.packbits(codebar);
//        TRSWpredelay.setValue("4");
//        TRSWwidth.setValue("67");
//        BISWpredelay.setValue("4");
//        BISWwidth.setValue("68");
//        DRxpredelay.setValue("10");
//        DRxwidth.setValue("130");
//        SIMpredelay.setValue("100");
//        SIMwidth.setValue("10");
//        STCpredelay.setValue("25");
//        STCwidth.setValue("15");
//        footer.setValue("&&&&");
    }

    public static void main(String[] args) {
        SenseElectronicsFormat frame = new SenseElectronicsFormat();
        frame.loadFromFile("senseElParams.xml");
        frame.getPacket();
//        frame.printToFile("senseElParams.xml");
        int x = 0;
    }

    public byte[] getPacket() {
        if (aux == null) {
            aux = new SenseElectronicsFormat();
            for (int i = 0; i < field.length; i++) {
                aux.field[i].setValue(this.field[i].getValueAsByteArr());
            }
        }
        
        double prt = 100000 / this.PRF.getValueWithDecimal();
        aux.PRF.setValue((int) prt);
        int a = (int) this.TRSWwidth.getValue();
        int pwx100 = (int) this.DRxwidth.getValue();
        aux.TRSWwidth.setValue(a);
        int b = (int) this.BISWwidth.getValue();
        aux.BISWwidth.setValue(b + pwx100);
        int c = (int) this.TRSWpredelay.getValue();
        int d = (int) this.DRxpredelay.getValue();
        aux.DRxpredelay.setValue(c + d);
        aux.DRxwidth.setValue(pwx100);
        int e = (int) this.SIMwidth.getValue();
        aux.SIMwidth.setValue(e + pwx100);
        int f = (int) this.STCwidth.getValue();
        aux.STCwidth.setValue(f + pwx100);
        int offset = intbypass1.fieldindex;
        for(int i=offset;i<offset+12;i++){
            aux.field[i].setValue(this.field[i].getValueAsByteArr());
        }
        byte[] arr = new byte[106];
        aux.getDataBytes(arr);
        return arr;
    }

    @Override
    public void loadFromFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            XMLReadWrite rwagent = new XMLReadWrite(filePath);
            String str;
            for (int i = 0; i < field.length; i++) {
                str = rwagent.getTextByTag(field[i].getName());
                if (str != null) {
                    field[i].setValue(str);
                }
            }
            DRxtrail = Double.parseDouble(rwagent.getTextByTag("Tx2Trig2Traildelay"));
        }

    }

    @Override
    public void printToFile(String filename) {
        XMLReadWrite rwagent = new XMLReadWrite(filename);

        for (int i = 0; i < field.length; i++) {
            String str1 = field[i].getName();
            String str2 = field[i].getValueAsString();
            rwagent.Write(str1, str2);
        }

        rwagent.Write("Tx2Trig2Traildelay", String.valueOf(DRxtrail));
        rwagent.printToFile();
    }

}
