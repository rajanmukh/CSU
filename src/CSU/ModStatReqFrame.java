/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CSU;

import Utility.Frame.Intfield;
import Utility.Frame.Packet;

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
    byte[] statReqPacket;

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

        txID.setValue(new byte[]{10,1});
        protID.setValue(0);
        bytecount.setValue(6);
        slaveaddr.setValue(1);
        commandcode.setValue(3);
        readlocation.setValue(new byte[]{100,0});
        noOfBytesToRead.setValue(20);
        statReqPacket=new byte[12];
        super.getDataBytes(statReqPacket);
    }
    
    public byte[] getPacket() {
            return statReqPacket;
    }

}
