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
    public SH.field emergencyStop;
    
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
    public SH.field collectorCurrent;
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
        indx=new int[]{7,8,9,10,11,12,13};
        ready = fields.get(6);
        helixCurrent = fields.get(7);
        cathodeCurrent = fields.get(8);
        excessDuty = fields.get(9);
        excessVSWR = fields.get(10);
        extmodpulse = fields.get(22);
        KOT = fields.get(12);
        COT=fields.get(13);        
        BYPASS = fields.get(0);
        mains = fields.get(1);
        hv = fields.get(2);
        mod = fields.get(3);
        reset = fields.get(4);
        emergencyStop=fields.get(5);
        FAULT_STATUS = fields.get(14);
        
        remote_local = fields.get(21);
//        modindication = fields.get(5);
//        extintfault = fields.get(4);       
        
        
        filamentVoltage = fields.get(32);
        filamentCurrent = fields.get(33);              
        beamOnVoltage = fields.get(34);
        beamOFFoltage = fields.get(35);
        collectorVoltage = fields.get(36);
        collectorCurrent = fields.get(37);
        cathodeVoltage = fields.get(38);
        cathodeCurrent = fields.get(39);
        PRF = fields.get(40);
        PW = fields.get(41);
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
        for(int i=7;i<8;i++){
            double factor=((double)fields.get(i).getValue())/currdframe.fields.get(i).getValue();
            result=(factor<lowthreshold)||(factor>upthreshold);
            if(result==true){
                return result;
            }
        }
        for(int i=32;i<42;i++){
            double factor=((double)fields.get(i).getValue())/currdframe.fields.get(i).getValue();
            result=(factor<lowthreshold)||(factor>upthreshold);
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

