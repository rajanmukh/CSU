/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CSU;

import Utility.Frame.SH;
import Utility.Frame.Status;

/**
 *
 * @author Istrac
 */
public class ModDigitalInStatFrame extends SH {

    public SH.field helixCurrent;
    public SH.field dutycycle;
    public SH.field modindication;
    public SH.field extintfault;
    public SH.field ready;
    public SH.field mod;
    public SH.field hv;
    public SH.field mains;
    public SH.field vswr;
    public SH.field cathodeCurrent;
    public SH.field extmodpulse;
    public SH.field reset;
    public SH.field temperature;
    public SH.field remote_local;
    private field interlock_bit;

    public ModDigitalInStatFrame() {
        super("StatDefs/DigitalInputStats.xml");
        helixCurrent = fields.get(7);
        dutycycle = fields.get(6);
        modindication = fields.get(5);
        extintfault = fields.get(4);
        ready = fields.get(3);
        mod = fields.get(2);
        hv = fields.get(1);
        mains = fields.get(0);
        vswr = fields.get(14);
        cathodeCurrent = fields.get(13);
        interlock_bit=fields.get(12);
        extmodpulse = fields.get(11);
        reset = fields.get(10);
        temperature = fields.get(9);
        remote_local = fields.get(8);
    }
    public int getMainsStatus() {
        return (mains.getValue()==1)?Status.ON:Status.OFF;
    }

    public int getHVStatus() {
        return (hv.getValue()==1)?Status.ON:Status.OFF;
    }

    public int getModulatorStatus() {
        return (mod.getValue()==1)?Status.ON:Status.OFF;
    }

    public int getResetStatus() {
        return (reset.getValue()==1)?Status.ON:Status.OFF;
    }

    boolean isChangeSignificant(ModDigitalInStatFrame currdframe) {
        boolean result = false;
        for(int i=0;i<fields.size();i++){
            result=fields.get(i).getValue()!=currdframe.fields.get(i).getValue();
            if(result==true){
                break;
            }
        }
        
        
        return result;
    }
}
