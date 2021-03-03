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
public class ModDigitallOutStatFrame extends SH {

    private SH.field reset;
    private SH.field ext_int;
    private SH.field mod;
    private SH.field hv;
    private SH.field mains;

    public ModDigitallOutStatFrame() {
        super("StatDefs/DigitalOutputStats.xml");
        reset = fields.get(0);
        ext_int = fields.get(1);
        mod = fields.get(2);
        hv = fields.get(3);
        mains = fields.get(4);
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
}
