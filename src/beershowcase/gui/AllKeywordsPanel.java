
package beershowcase.gui;

import beershowcase.beerdata.StyleKeyword;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Grzegorz Łoś
 */
public class AllKeywordsPanel extends JPanel {
    private Map<StyleKeyword, JCheckBox> checkBoxes = new HashMap<>();
    
    public AllKeywordsPanel() {
        init();
    }
    
    private void init() {
        int rows = 15;
        int cols = 5;
        GridLayout layout = new GridLayout(rows, cols);
        setLayout(layout);
        StyleKeyword[] possibleValues = getStyleKeywords(); 
        for (int row = 0; row < rows; ++row) {
            for (int col = 0; col < cols; ++col) {
                int index = row + col*rows;
                if (index < possibleValues.length)
                    addCheckBox(possibleValues[index]);
                else
                    add(new JLabel(""));
            }
        }
    }
    
    private StyleKeyword[] getStyleKeywords() {
        StyleKeyword[] allKeywords = StyleKeyword.class.getEnumConstants();
        Arrays.sort(allKeywords, (StyleKeyword a, StyleKeyword b) -> a.name().compareTo(b.name()));
        return allKeywords;
    }
    
    private void addCheckBox(StyleKeyword keyword) {
        JCheckBox checkBox = new JCheckBox(keyword.name());
        checkBoxes.put(keyword, checkBox);
        add(checkBox);
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

    void setSelectedKeywords(Collection<StyleKeyword> styleKeywords) {
        for (Map.Entry<StyleKeyword, JCheckBox> pair: checkBoxes.entrySet()) {
            if (styleKeywords.contains(pair.getKey()))
                pair.getValue().setSelected(true);
            else
                pair.getValue().setSelected(false);
        }
    }
}
