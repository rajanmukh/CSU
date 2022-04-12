/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CSU;

import Utility.Frame.Command;
import Utility.Frame.Intfield;
import Utility.Frame.Intxfield;
import Utility.Frame.Intxsfield;

/**
 *
 * @author Istrac
 */
public class ModAnalogCtrlFrame extends Utility.Frame.Packet {

    Intfield txID;
    Intfield protID;
    Intfield bytecount;
    Intfield slaveaddr;
    Intfield commandcode;
    Intfield writelocation;
    Intfield noOfRegisters;
    Intfield noOfBytesToWrite;
    Intxfield filamentVoltage;
    Intxfield beamOnVoltage;
    Intxfield pulsewidth;
    Intxfield PRF;
    Intxsfield cathodeVoltage;
    byte[] bytearr;

    public ModAnalogCtrlFrame() {
        super(13);
        int index = 0;
        field[index++] = txID = new Intfield("txID", 2);
        field[index++] = protID = new Intfield("protID", 2);
        field[index++] = bytecount = new Intfield("bytecount", 2);
        field[index++] = slaveaddr = new Intfield("slaveaddr", 1);
        field[index++] = commandcode = new Intfield("commandcode", 1);
        field[index++] = writelocation = new Intfield("writelocation", 2);
        field[index++] = noOfRegisters = new Intfield("noOfRegisters", 2);
        field[index++] = noOfBytesToWrite = new Intfield("noOfBytesToWrite", 1);
        field[index++] = filamentVoltage = new Intxfield("filamentVoltage", 2,365.35);
        field[index++] = beamOnVoltage = new Intxfield("beamOnVoltage", 2,-38.21656);
        field[index++] = pulsewidth = new Intxfield("pulsewidth", 2,307.6923);
        field[index++] = PRF = new Intxsfield("PRF", 2,91.6,0.06);
        field[index++] = cathodeVoltage = new Intxsfield("cathodeVoltage", 2,70,62.82857);
        txID.setValue(0);
        protID.setValue(0);
        bytecount.setValue(17);
        slaveaddr.setValue(0);
        commandcode.setValue(Command.ANALOGCTRL);
        writelocation.setValue(new byte[]{10,16});
        noOfRegisters.setValue(5);
        noOfBytesToWrite.setValue(10);
        bytearr=new byte[24];
        
    }

    public byte[] getPacket() {
        super.getDataBytes(bytearr);
        return bytearr;
    }

//    

    public static void main(String[] args) {

        String format = String.format("%.2f", 1.236);
        System.out.println(format);
        ModAnalogCtrlFrame modCtrlFrame = new ModAnalogCtrlFrame();
    }
}
