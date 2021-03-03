/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CSU;

import Utility.Frame.Intfield;
import Utility.Frame.Packet;
import Utility.Frame.Status;

/**
 *
 * @author Istrac
 */
public class ModStatReqFrame extends Packet {

    
    Intfield txID;
    Intfield protID;
    Intfield bytecount;
    Intfield slaveaddr;
    Intfield commandcode;
    Intfield readlocation;
    Intfield noOfBytesToRead;
    byte[] analogReqPacket;
    byte[] digitalInputReqPacket;
    byte[] digitalOutputReqPacket;

    public ModStatReqFrame() {
        super(7);
        int index = 0;
        field[index++] = txID = new Intfield("txID", 2);
        field[index++] = protID = new Intfield("protID", 2);
        field[index++] = bytecount = new Intfield("bytecount", 2);
        field[index++] = slaveaddr = new Intfield("slaveaddr", 1);
        field[index++] = commandcode = new Intfield("commandcode", 1);
        field[index++] = readlocation = new Intfield("readlocation", 2);
        field[index++] = noOfBytesToRead = new Intfield("noOfBytesToRead", 2);

        txID.setValue(0);
        protID.setValue(0);
        bytecount.setValue(6);
        slaveaddr.setValue(0);
        //
        commandcode.setValue(3);
        readlocation.setValue(new byte[]{16, 16});
        noOfBytesToRead.setValue(8);
        analogReqPacket = new byte[12];
        super.getDataBytes(analogReqPacket);
        //
        commandcode.setValue(2);
        readlocation.setValue(new byte[]{0, 4});
        noOfBytesToRead.setValue(16);
        digitalInputReqPacket = new byte[12];
        super.getDataBytes(digitalInputReqPacket);
        //
        commandcode.setValue(1);
        readlocation.setValue(new byte[]{0, 5});
        noOfBytesToRead.setValue(5);
        digitalOutputReqPacket = new byte[12];
        super.getDataBytes(digitalOutputReqPacket);
    }

    public byte[] getPacket(int tag) {
        byte[] buf=null;
        switch(tag){
            case Status.ANALOG:
                buf=analogReqPacket;
                break;
            case Status.DIGITALINPUT:
                buf=digitalInputReqPacket;
                break;
            case Status.DIGITALOUTPUT:
                buf=digitalOutputReqPacket;
        }
        return buf;
    }

}
