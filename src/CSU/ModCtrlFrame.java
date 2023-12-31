/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CSU;

import Utility.Frame.Intfield;
import Utility.Frame.Intxfield;
import Utility.Frame.Packet;
import Utility.Frame.Status;
import Utility.XMLReadWrite;

/**
 *
 * @author Istrac
 */
public class ModCtrlFrame extends Packet {

    Intfield txID;
    Intfield protID;
    Intfield bytecount;
    Intfield slaveaddr;
    Intfield commandcode;
    Intfield writelocation;
    Intfield noOfRegisters;
    Intfield noOfBytesToWrite;
    Intfield switch1;
    Intfield switch2;
    Intfield spare1;
    Intxfield filamentVoltage;
    Intxfield cathodeVoltage;
    Intxfield beamOnVoltage;
    Intxfield collectorVoltage;
    Intxfield PRF;
    Intxfield pulsewidth;
    Intfield warmUpTimer;
    Intfield spare2;

    byte[] bytearr;

    public ModCtrlFrame() {
        super(19);
        int index = 0;
        field[index++] = txID = new Intfield("txID", 2);
        field[index++] = protID = new Intfield("protID", 2);
        field[index++] = bytecount = new Intfield("bytecount", 2);
        field[index++] = slaveaddr = new Intfield("slaveaddr", 1);
        field[index++] = commandcode = new Intfield("commandcode", 1);
        field[index++] = writelocation = new Intfield("writelocation", 2);
        field[index++] = noOfRegisters = new Intfield("noOfRegisters", 2);
        field[index++] = noOfBytesToWrite = new Intfield("noOfBytesToWrite", 1);
        field[index++] = switch1 = new Intfield("switch1", 2);
        field[index++] = switch2 = new Intfield("switch2", 2);
        field[index++] = spare1 = new Intfield("spare1", 4);
        field[index++] = filamentVoltage = new Intxfield("filamentVoltage", 2, -1000);
        field[index++] = cathodeVoltage = new Intxfield("cathodeVoltage", 2, -100);
        field[index++] = beamOnVoltage = new Intxfield("beamOnVoltage", 2, -100);
        field[index++] = collectorVoltage = new Intxfield("collectorVoltage", 2, 1000);
        field[index++] = PRF = new Intxfield("PRF", 2, 10);
        field[index++] = pulsewidth = new Intxfield("pulsewidth", 2, 10);
        field[index++] = warmUpTimer = new Intfield("warmUpTimer", 2);
        field[index++] = spare2 = new Intfield("spare2", 4);

        txID.setValue(new byte[]{10, 1});
        protID.setValue(new byte[]{0, 0});
        bytecount.setValue(33);
        slaveaddr.setValue(1);
        commandcode.setValue(16);
        writelocation.setValue(0);
        noOfRegisters.setValue(13);
        noOfBytesToWrite.setValue(26);
        
        XMLReadWrite reader = new XMLReadWrite("conf.xml");
        double FV = Double.parseDouble(reader.getTextByTag("FilamentVoltage"));
        double CV = Double.parseDouble(reader.getTextByTag("CathodeVoltage"));
        double BV = Double.parseDouble(reader.getTextByTag("GridVoltage"));
        double ColV = Double.parseDouble(reader.getTextByTag("CollectorVoltage"));
        filamentVoltage.setValueWithDecimal(FV);
        cathodeVoltage.setValueWithDecimal(CV);
        beamOnVoltage.setValueWithDecimal(BV);
        collectorVoltage.setValueWithDecimal(ColV);
        PRF.setValueWithDecimal(1);
        pulsewidth.setValueWithDecimal(1);

        warmUpTimer.setValue(5);

        bytearr = new byte[39];
        switch1.setBit(8);
        switch1.setBit(9);
        switch1.setBit(10);
        switch1.setBit(7);
        switch1.setBit(5);
        switch1.setBit(0);
    }

    public byte[] getPacket() {
        super.getDataBytes(bytearr);
        return bytearr;
    }

    public final void setMains(int stat) {

        if (stat == Status.ON) {
            switch1.resetBit(0);
            switch1.setBit(1);
        } else if (stat == Status.OFF) {
            switch1.setBit(0);
            switch1.resetBit(1);
            switch1.resetBit(2);
            switch1.resetBit(3);
        }
    }

    public void setHV(int stat) {

        if (stat == Status.ON) {
            switch1.setBit(2);
        } else if (stat == Status.OFF) {
            switch1.resetBit(2);
        }
    }

    public void setModulator(int stat) {

        if (stat == Status.ON) {
            switch1.setBit(3);
        } else if (stat == Status.OFF) {
            switch1.resetBit(3);
        }
    }

    public void setReset(boolean flag) {
        setHV(Status.OFF);
        setModulator(Status.OFF);
        if (flag) {
            switch1.setBit(4);
        } else {
            switch1.resetBit(4);
        }
    }

    public void setExt() {
        switch1.setBit(6);
    }

    public void setInt() {
        switch1.resetBit(6);
    }

    public void setRemote() {
        switch1.setBit(5);
    }

    public void setLocal() {
        switch1.resetBit(5);
    }

//    public void emergencyStop(boolean flag) {
//        if (flag) {
//            ON_OFF.setBit(6);
//        } else {
//            ON_OFF.resetBit(6);
//        }
//    }
    void setStop(int status) {
        setModulator(Status.OFF);
        setHV(Status.OFF);
        setMains(Status.OFF);
        if (status == Status.ON) {
            switch1.setBit(11);
        } else {
            switch1.resetBit(11);
        }
    }

}
