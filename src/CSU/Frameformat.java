/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CSU;


import Utility.Frame.Packet;
import Utility.Frame.Stringfield;
import Utility.Frame.Intfield;
import Utility.Frame.Intxfield;

/**
 *
 * @author Istrac
 */
public class Frameformat extends Packet{   
    
    Stringfield header;
    Intfield prf;
    Intfield prfcount;
    Intxfield zkmdelay;
    Intxfield pulsewidth;
    Intxfield baudwidth;
    Intfield baudno;
    Intfield coden;
    Intfield codeA;
    Intfield codeAbar;
    Intfield codeB;
    Intfield codeBbar;
    Intxfield TRSW_predelay;
    Intfield TRSW_width;
    Intxfield BISW_predelay;
    Intxfield BISW_width;
    Intxfield DRx_predelay;
    Intxfield DRx_width;
    Intxfield Sim_predelay;
    Intxfield Sim_width;
    Intxfield STC_predelay;
    Intxfield STC_width;
    Intfield mode_sel;
    Intfield sub_control;
    Intfield status;
    Intfield spare;
    Stringfield footer;

    public Frameformat() {
    super(27);
    int index = 0;
    field[index++] = header=new Stringfield("header",4);
    field[index++] = prf = new Intfield("PRF",4);
    field[index++] = prfcount = new Intfield("PRFcount",4);
    field[index++] = zkmdelay = new Intxfield("zerokmdelay",2,100);
    field[index++] = pulsewidth = new Intxfield("pulsewidth",2,100);
    field[index++] = baudwidth = new Intxfield("baudwidth",2,100);
    field[index++] = baudno = new Intfield("baudno",2);
    field[index++] = coden = new Intfield("codeenable",1);
    field[index++] = codeA = new Intfield("codeA",8);
    field[index++] = codeAbar = new Intfield("codeAbar",8);
    field[index++] = codeB = new Intfield("codeB",8);
    field[index++] = codeBbar = new Intfield("codeBbar",8);
    field[index++] = TRSW_predelay = new Intxfield("TRSW_predelay",2,100);
    field[index++] = TRSW_width = new Intxfield("TRSW_width",2,100);
    field[index++] = BISW_predelay = new Intxfield("BISW_predelay",2,100);
    field[index++] = BISW_width = new Intxfield("BISW_width",2,100);
    field[index++] = DRx_predelay = new Intxfield("DRx_predelay",2,100);
    field[index++] = DRx_width = new Intxfield("DRx_width",4,100);
    field[index++] = Sim_predelay = new Intxfield("Sim_predelay",4,100);
    field[index++] = Sim_width = new Intxfield("Sim_width",4,100);
    field[index++] = STC_predelay = new Intxfield("STC_predelay",2,100);
    field[index++] = STC_width = new Intxfield("STC_width",2,100);
    field[index++] = mode_sel = new Intfield("mode_sel",1);
    field[index++] = sub_control = new Intfield("sub_control",1);
    field[index++] = status= new Intfield("status",4);
    field[index++] = spare = new Intfield("spare",8);
    field[index++] = footer=new Stringfield("footer",4);
    header.setValue("$$$$");
    prf.setValue("10000");
    prfcount.setValue("129024");
    zkmdelay.setValue("6");
    pulsewidth.setValue("1");
    baudwidth.setValue("1");
    baudno.setValue("1");
    coden.setValue(1);
    TRSW_predelay.setValue("4");
    TRSW_width.setValue("4");
    BISW_predelay.setValue("4");
    BISW_width.setValue("5");
    DRx_predelay.setValue("8");
    DRx_width.setValue("8");
    Sim_predelay.setValue("100");
    Sim_width.setValue("4");
    STC_predelay.setValue("20");
    STC_width.setValue("10");
    mode_sel.setValue(1);
    footer.setValue("&&&&");
    
    byte[] arr=new byte[97];
    getDataBytes(arr);//testing
    }
//    public static void main(String[] args){
//        Frameformat frameformat = new Frameformat();
//    }
   
}
