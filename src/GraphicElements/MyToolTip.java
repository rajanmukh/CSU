/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphicElements;

import java.awt.Font;
import javax.swing.JComponent;
import javax.swing.JToolTip;

/**
 *
 * @author Admin
 */
public class MyToolTip extends JToolTip{

    public MyToolTip(JComponent component) {
        super();
        setComponent(component);
        String name = super.getFont().getFontName();
        setFont(new Font(name,Font.BOLD,16));
    }
    
}
