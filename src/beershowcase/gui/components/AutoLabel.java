
package beershowcase.gui.components;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * A JLabel which changes its font size depending on the size.
 * @author Grzegorz Łoś
 */
public class AutoLabel extends JPanel {
    Font baseFont = new Font("Default", Font.PLAIN, 12);
    JLabel renderer = new JLabel();

    public AutoLabel() {
        super();
    }
    
    public AutoLabel(String text) {
        renderer.setText(text);
    }

    public AutoLabel(String text, int horizontalAlignment) {
        renderer.setText(text);
        renderer.setHorizontalAlignment(horizontalAlignment);
    }

    public AutoLabel(String text, int horizontalAlignment, int fontStyle) {
        renderer.setText(text);
        renderer.setHorizontalAlignment(horizontalAlignment);
        baseFont = baseFont.deriveFont(fontStyle);
    }

    public void setText(String text) {
        renderer.setText(text);
    }
    
    public String getText() {
        return renderer.getText();
    }
    
    public Font getBaseFont() {
        return baseFont;
    }

    public void setBaseFont(Font baseFont) {
        this.baseFont = baseFont;
    }
    
    public void setFontStyle(int style) {
        baseFont = baseFont.deriveFont(style);
    }
    
    @Override
    public void paint(Graphics gr) {
        super.paint(gr);
        adjustFont();
        renderer.paint(gr);
    }
    
    private void adjustFont() {
        Dimension size = getSize();
        int fs = size.height / 2;
        if (baseFont != null) {
            Font font = new Font(baseFont.getFamily(), baseFont.getStyle(), fs);
            renderer.setFont(font);
        }
        renderer.setSize(getSize());
    }
}
