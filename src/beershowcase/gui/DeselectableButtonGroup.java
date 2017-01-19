
package beershowcase.gui;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;

/**
 *
 * @author Grzegorz Łoś
 */
public class DeselectableButtonGroup extends ButtonGroup {
    @Override
    public void setSelected(ButtonModel bm, boolean b) {
        if (!b)
            clearSelection();
        else
            super.setSelected(bm, b);
    }
}
