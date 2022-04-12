/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CSU;

import Utility.Frame.SH;
import Utility.Frame.Status;

/**
 *
 * @author Istrac
 */
public class ModStatFrame extends SH {
    
    public SH.field ready;
    public SH.field helixCurrent;
//    public SH.field CollectorOC;
    public SH.field KOC;
    public SH.field excessDuty;
    public SH.field excessVSWR;
    public SH.field extmodpulse;
    public SH.field KOT;
    public SH.field COT;
    public SH.field BYPASS;
    public SH.field mains;
    public SH.field hv;
    public SH.field mod;
    public SH.field reset;
    public SH.field FAULT_STATUS;
    
    public SH.field MAINS_IN;
    public SH.field HV_IN;
    public SH.field MOD_IN;
    public SH.field RESET_IN;
    public SH.field remote_local;
    public SH.field PULSE_SOURCE_IN;
    public SH.field VSWR_IN;
    public SH.field EXT_INT1_IN;
    public SH.field EXT_INT2_IN;
    
    public SH.field BYPASS_IN;
    public SH.field HOC_BYPASS_IN;
    public SH.field KOC_BYPASS_IN;
    public SH.field EXCESS_DUTY_BYPASS_IN;
    public SH.field EXCESS_VSWR_BYPASS_IN;
    public SH.field EXT_INT_BYPASS_IN;
    public SH.field KOT_BYPASS_IN;
    public SH.field COT_BYPASS_IN;
    
    public SH.field filamentVoltage;
    public SH.field filamentCurrent;
    public SH.field beamOnVoltage;
    public SH.field beamOFFoltage;
    public SH.field collectorVoltage;
    public SH.field helixBodyCurrent;
    public SH.field cathodeVoltage;
    public SH.field cathodeCurrent;
    public SH.field PRF;
    public SH.field PW;
//    public SH.field modindication;
//    public SH.field extintfault;
    
    
    
//    public SH.field collectorCurrent;
    
    double upthreshold = 1.05;
    double lowthreshold = 0.95;
    private int[] indx;

    public ModStatFrame() {
        super("StatDefs/ModStats.xml");
        indx=new int[]{6,7,8,9,10,11,12};
        ready = fields.get(5);
        helixCurrent = fields.get(6);
        cathodeCurrent = fields.get(7);
        excessDuty = fields.get(8);
        excessVSWR = fields.get(9);
        extmodpulse = fields.get(10);
        KOT = fields.get(11);
        COT=fields.get(12);        
        BYPASS = fields.get(0);
        mains = fields.get(1);
        hv = fields.get(2);
        mod = fields.get(3);
        reset = fields.get(4);
        FAULT_STATUS = fields.get(13);
        
        remote_local = fields.get(20);
//        modindication = fields.get(5);
//        extintfault = fields.get(4);       
        
        
        filamentVoltage = fields.get(31);
        filamentCurrent = fields.get(32);              
        beamOnVoltage = fields.get(33);
        beamOFFoltage = fields.get(34);
        collectorVoltage = fields.get(35);
        helixBodyCurrent = fields.get(36);
        cathodeVoltage = fields.get(37);
        cathodeCurrent = fields.get(38);
        PRF = fields.get(39);
        PW = fields.get(40);
    }
    public int getMainsStatus() {
        
        return (mains.getValue()==2)?Status.ON:Status.OFF;
        
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

    boolean isChangeSignificant(ModStatFrame currdframe) {
        boolean result = false;
        for(int i=0;i<13;i++){
            result=fields.get(i).getValue()!=currdframe.fields.get(i).getValue();
            if(result==true){
                return result;
            }
        }
        for(int i=31;i<38;i++){
            double factor=((double)fields.get(i).getValue())/currdframe.fields.get(i).getValue();
            result=(factor<lowthreshold)||(factor>upthreshold);
            if(result==true){
                return result;
            }
        }
        for(int i=39;i<40;i++){
            result=fields.get(i).getValue()!=currdframe.fields.get(i).getValue();
            if(result==true){
                return result;
            }
        }
        return result;
    }

    @Override
    public int getStatus() {
         int status = Status.FINE;
        int noOfFields = 7;
        for (int i = 0; i < noOfFields; i++) {
            field f = fields.get(indx[i]);
            if (f.getSize() != 0) {
                int elementStatus = f.getStatus();
                if (elementStatus == Status.ERROR) {
                    status = Status.ERROR;
                    break;
                } else if (elementStatus == Status.WARNING) {
                    status = Status.WARNING;
                }
            }
        }
        return status;
    }
    
}

