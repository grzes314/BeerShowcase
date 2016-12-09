
package beershowcase;

import beershowcase.beerdata.StyleKeyword;
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
    private Map<StyleKeyword, JCheckBox> checkBoxes = new HashMap<>();
    
    public StylesPanel() {
        init();
    }
    
    private void init() {
        setLayout(new GridLayout(12, 5));
        StyleKeyword[] possibleValues = StyleKeyword.class.getEnumConstants();
        for (StyleKeyword keyword: possibleValues) {
            JCheckBox checkBox = new JCheckBox(keyword.name());
            checkBoxes.put(keyword, checkBox);
            add(checkBox);
        }
    }
    
    public ArrayList<StyleKeyword> getSelectedKeywords() {
        ArrayList<StyleKeyword> arr = new ArrayList<>();
        for (Map.Entry<StyleKeyword, JCheckBox> pair: checkBoxes.entrySet()) {
            if (pair.getValue().isSelected())
                arr.add(pair.getKey());
        }
        return arr;
    }

    public void reset() {
        for (Map.Entry<StyleKeyword, JCheckBox> pair: checkBoxes.entrySet())
            pair.getValue().setSelected(false);
    }

    void setSelectedKeywords(ArrayList<StyleKeyword> styleKeywords) {
        for (Map.Entry<StyleKeyword, JCheckBox> pair: checkBoxes.entrySet()) {
            if (styleKeywords.contains(pair.getKey()))
                pair.getValue().setSelected(true);
            else
                pair.getValue().setSelected(false);
        }
    }
}
