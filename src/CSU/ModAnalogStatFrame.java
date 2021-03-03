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
public class ModAnalogStatFrame extends SH{
    
    
    public SH.field filamentVoltage;
    public SH.field cathodeVoltage;
    public SH.field beamOnVoltage;
    public SH.field filamentCurrent;
    public SH.field helixBodyCurrent;
    public SH.field collectorCurrent;
    double upthreshold = 1.05;
    double lowthreshold = 0.95;

    public ModAnalogStatFrame() {
        super("StatDefs/AnalogStats.xml");
        filamentVoltage = fields.get(0);
        cathodeVoltage = fields.get(1); // (int) format       
        beamOnVoltage = fields.get(2);
        filamentCurrent = fields.get(3);
        helixBodyCurrent = fields.get(4);
        collectorCurrent = fields.get(5);
    }       

    
    public boolean isChangeSignificant(ModAnalogStatFrame frame) {
        boolean result = false;
        for(int i=0;i<fields.size();i++){
            double factor=((double)fields.get(i).getValue())/frame.fields.get(i).getValue();
            result=(factor<lowthreshold)||(factor>upthreshold);
            if(result==true){
                break;
            }
        }
        return result;
    } 
    public static void main(String[] arg){
        ModAnalogStatFrame modAnalogStatFrame = new ModAnalogStatFrame();
        byte[] packet=new byte[25];
        packet[11]=(byte)255;
        packet[12]=(byte)255;
        modAnalogStatFrame.setArr(packet);
        int x=modAnalogStatFrame.cathodeVoltage.getValue();
        int gfgf=0;
        //modAnalogStatFrame.cathodeVoltage
    }
}
