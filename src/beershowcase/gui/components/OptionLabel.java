
package beershowcase.gui.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;

/**
 *
 * @author Grzegorz Łoś
 */
public class OptionLabel extends JPanel {
    private final CheckBox checkBox;
    private final AutoLabel label;
    
    ComponentListener cl = new ComponentAdapter() {
        @Override
        public void componentResized(ComponentEvent e) {
            int a = Math.min(getSize().width, getSize().height);
            checkBox.setPreferredSize(new Dimension(a, a));
        }
    };

    public OptionLabel(String text) {
        this.checkBox = new CheckBox();
        this.label = new AutoLabel(text);
        
        setLayout(new BorderLayout());
        add(checkBox, BorderLayout.LINE_START);
        add(label, BorderLayout.CENTER);
        
        addComponentListener(cl);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                toggleSelection();
                repaint();
            }
        });
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }
    
    public void toggleSelection() {
        checkBox.toggleSelection();
    }

    public boolean isSelected() {
        return checkBox.isSelected();
    }

    public void setSelected(boolean selected) {
        checkBox.setSelected(selected);
    }

    public AutoLabel getLabel() {
        return label;
    }
    
}
