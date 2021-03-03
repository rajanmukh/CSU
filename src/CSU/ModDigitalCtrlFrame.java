/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CSU;

import Utility.Frame.Command;
import Utility.Frame.Intfield;
import Utility.Frame.Packet;
import Utility.Frame.Status;

/**
 *
 * @author Istrac
 */
public class ModDigitalCtrlFrame extends Packet {

    Intfield txID;
    Intfield protID;
    Intfield bytecount;
    Intfield slaveaddr;
    Intfield commandcode;
    Intfield writelocation;
    Intfield noOfCoils;
    Intfield noOfBytesToWrite;
    Intfield ON_OFF;
    byte[] bytearr;

    public ModDigitalCtrlFrame() {
        super(9);
        int index = 0;
        field[index++] = txID = new Intfield("txID", 2);
        field[index++] = protID = new Intfield("protID", 2);
        field[index++] = bytecount = new Intfield("bytecount", 2);
        field[index++] = slaveaddr = new Intfield("slaveaddr", 1);
        field[index++] = commandcode = new Intfield("commandcode", 1);
        field[index++] = writelocation = new Intfield("writelocation", 2);
        field[index++] = noOfCoils = new Intfield("noOfCoils", 2);
        field[index++] = noOfBytesToWrite = new Intfield("noOfBytesToWrite", 1);
        field[index++] = ON_OFF = new Intfield("ON_OFF", 1);
        txID.setValue(0);
        protID.setValue(0);
        bytecount.setValue(8);
        slaveaddr.setValue(0);
        commandcode.setValue(Command.DIGITALCTRL);
        writelocation.setValue(new byte[]{0,8});
        noOfCoils.setValue(8);
        noOfBytesToWrite.setValue(1);
        //ON_OFF.setBit(6);
        bytearr = new byte[14];
        
    }

    public byte[] getPacket() {
        super.getDataBytes(bytearr);
        return bytearr;
    }

    public final void setMains(int stat) {
       
        if (stat == Status.ON) {
            ON_OFF.setBit(0);
        } else if (stat == Status.OFF) {
            ON_OFF.resetBit(0);
             ON_OFF.resetBit(1);
              ON_OFF.resetBit(2);
               ON_OFF.resetBit(3);
        }
    }

    public void setHV(int stat) {
       
        if (stat == Status.ON) {
            ON_OFF.setBit(1);
        } else if (stat == Status.OFF) {
            ON_OFF.resetBit(1);
        }
    }

    public void setModulator(int stat) {
        
        if (stat == Status.ON) {
            ON_OFF.setBit(2);
        } else if (stat == Status.OFF) {
            ON_OFF.resetBit(2);
        }
    }

    public void setReset(boolean flag) {
        setHV(Status.OFF);
        setModulator(Status.OFF);
        if (flag) {
            ON_OFF.setBit(4);
        } else{
            ON_OFF.resetBit(4);
        }
    }

    public void setExt() {
        ON_OFF.setBit(3);
    }

    public void setInt() {
        ON_OFF.resetBit(3);
    }

    public void emergencyStop(boolean flag) {
        if (flag) {
            ON_OFF.setBit(6);
        } else {
            ON_OFF.resetBit(6);
        }
    }
}
