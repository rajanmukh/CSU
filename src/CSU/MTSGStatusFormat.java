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
public class MTSGStatusFormat extends SH {

    public SH.field ADC1;
    public SH.field ADC2;
    public SH.field ADC3;
    public SH.field ADC4;
    public SH.field pressure;
    public SH.field fwdpwr_il;
    public SH.field ippwr_il;
    public SH.field press_il;
    public SH.field vswr_il;
    public SH.field temp_il;
    public SH.field DIL;
    public SH.field CTIL;
    public SH.field BLIL;
    public SH.field ADIL;
    public SH.field OIL;
    public SH.field VSWRIL;
    public SH.field excessRFIL;
    public SH.field pressureIL;
    private final int[] idx;

    public MTSGStatusFormat() {
        super("StatDefs/SenseStats.xml");
        super.endianness = LITTLE;
        idx=new int[]{10,11,12,13,15,17};
        ADC1 = fields.get(0);
        ADC2 = fields.get(1);
        ADC3 = fields.get(2);
        ADC4 = fields.get(3);
        pressure = fields.get(4);
        fwdpwr_il = fields.get(5);
        ippwr_il = fields.get(6);
        press_il = fields.get(7);
        vswr_il = fields.get(8);
        temp_il = fields.get(9);
        DIL = fields.get(10);
        CTIL = fields.get(11);
        BLIL = fields.get(12);
        ADIL = fields.get(13);
        OIL = fields.get(14);
        VSWRIL = fields.get(15);
        excessRFIL = fields.get(16);
        pressureIL = fields.get(17);
    }

    @Override
    public int getStatus() {
        int status = Status.FINE;
        for (int i = 0; i < idx.length; i++) {
            field f = fields.get(idx[i]);
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
