/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CSU;

import Utility.Frame.SH;

/**
 *
 * @author Istrac
 */
public class ModStatusPacketHeader extends SH {

    public field txID;
    public field protID;
    public field bytecount;
    public field slaveaddr;
    public field commandcode;
    public field noOfDataBytes;

    public ModStatusPacketHeader() {
        super("StatDefs/Header.xml");
        txID = fields.get(0);
        protID = fields.get(1);
        bytecount = fields.get(2);
        slaveaddr = fields.get(3);
        commandcode = fields.get(4);
        noOfDataBytes = fields.get(5);
    }
    public static void main(String[] args){
        ModStatusPacketHeader modStatusPacketHeader = new ModStatusPacketHeader();
        int trt=0;
    }
}
