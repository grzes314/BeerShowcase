
package beershowcase.gui;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;

/**
 *
 * @author Grzegorz Łoś
 */
public class MyButtonGroup extends ButtonGroup {
    @Override
    public void setSelected(ButtonModel bm, boolean s) {
        if (isSelected(bm))
            clearSelection();
        else
            super.setSelected(bm, s);
    }
}
