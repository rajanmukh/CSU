/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CSU;

import java.awt.Color;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Istrac
 */
class IPverifier extends InputVerifier {

        @Override
        public boolean verify(JComponent input) {
            boolean result = true;
            String text = ((JTextField) input).getText();
            if (!text.equals("")) {
                if (text.startsWith(" ")) {
                    JOptionPane.showMessageDialog(null, "field should not start with whitespace");
                    result = false;
                }
            } else {
                result = false;
            }
            Color bc = (result == false) ? Color.RED : Color.BLACK;
            ((JTextField) input).setBorder(javax.swing.BorderFactory.createLineBorder(bc));
            return result;
        }
    }
