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
public class MTSGStatusFormat extends SH{
    public SH.field ADC1;
    public SH.field ADC2;
    public SH.field ADC3;
    public SH.field ADC4;
    public SH.field pressure;
    public SH.field DIL;
    public SH.field CTIL;
    public SH.field BLIL;
    public SH.field ADIL;
    public SH.field OIL;
    public SH.field VSWRIL;
    public SH.field excessRFIL;
    public SH.field pressureIL;

    public MTSGStatusFormat() {
        super("StatDefs/SenseStats.xml");
        super.endianness=LITTLE;
        ADC1=fields.get(0);
        ADC2=fields.get(1);
        ADC3=fields.get(2);
        ADC4=fields.get(3);
        pressure=fields.get(4);
        DIL=fields.get(5);
        CTIL=fields.get(6);
        BLIL=fields.get(7);
        ADIL=fields.get(8);
        OIL=fields.get(9);
        VSWRIL=fields.get(10);
        excessRFIL=fields.get(11);
        pressureIL=fields.get(12);
    }
    
}

