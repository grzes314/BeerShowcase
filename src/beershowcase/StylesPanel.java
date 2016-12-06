
package beershowcase;

import beershowcase.beerdata.StyleKeywords;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

/**
 *
 * @author Grzegorz Łoś
 */
public class StylesPanel extends JPanel {
    private Map<StyleKeywords, JCheckBox> checkBoxes = new HashMap<>();
    
    public StylesPanel() {
        init();
    }
    
    private void init() {
        setLayout(new GridLayout(12, 5));
        StyleKeywords[] possibleValues = StyleKeywords.class.getEnumConstants();
        for (StyleKeywords keyword: possibleValues) {
            JCheckBox checkBox = new JCheckBox(keyword.name());
            checkBoxes.put(keyword, checkBox);
            add(checkBox);
        }
    }
    
    public ArrayList<StyleKeywords> getSelectedKeywords() {
        ArrayList<StyleKeywords> arr = new ArrayList<>();
        for (Map.Entry<StyleKeywords, JCheckBox> pair: checkBoxes.entrySet()) {
            if (pair.getValue().isSelected())
                arr.add(pair.getKey());
        }
        return arr;
    }

    public void reset() {
        for (Map.Entry<StyleKeywords, JCheckBox> pair: checkBoxes.entrySet())
            pair.getValue().setSelected(false);
    }
}
