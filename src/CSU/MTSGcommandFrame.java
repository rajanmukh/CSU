/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CSU;

import Utility.Frame.Command;
import Utility.Frame.Intfield;
import Utility.Frame.Packet;
import Utility.Frame.Stringfield;

/**
 *
 * @author Istrac
 */
public class MTSGcommandFrame extends Packet{
    Stringfield header1;    
    Intfield command;
    Intfield spare;
    Stringfield footer;
    private final byte[] buf;

    public MTSGcommandFrame() {
        super(4);
        int index=0;
        field[index++] = header1 = new Stringfield("header", 4);
        field[index++] = command = new Intfield("",1);
        field[index++] = spare = new Intfield("",1);        
        field[index++] = footer = new Stringfield("footer", 4);
        header1.setValue("$$$$");
        footer.setValue("%%%%");
        buf = new byte[10];
        getDataBytes(buf);
    }
    
    public synchronized byte[] getPacket(int no){
        buf[4] = (byte)no;
        switch(no){
            case Command.MTSGSTART:
                buf[4]=1;
                break;
            case Command.MTSGSTOP:
                buf[4]=2;
                break;                
            case Command.SENSESTAT:
                buf[4]=3;
        }
        return buf;        
    } 
    
    
}
