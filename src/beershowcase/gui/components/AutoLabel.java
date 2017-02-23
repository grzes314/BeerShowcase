
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
    double heightFactor = 0.5;

    public AutoLabel() {
        this("", 0.5, JLabel.LEFT, Font.PLAIN);
    }
    
    public AutoLabel(String text) {
        this(text, 0.5, JLabel.LEFT, Font.PLAIN);
    }

    public AutoLabel(String text, int horizontalAlignment) {
        this(text, 0.5, horizontalAlignment, Font.PLAIN);
    }

    public AutoLabel(String text, int horizontalAlignment, int fontStyle) {
        this(text, 0.5, horizontalAlignment, fontStyle);
    }
    
    
    public AutoLabel(double heightFactor) {
        this("", heightFactor, JLabel.LEFT, Font.PLAIN);
    }
    
    public AutoLabel(String text, double heightFactor) {
        this(text, heightFactor, JLabel.LEFT, Font.PLAIN);
    }

    public AutoLabel(String text, double heightFactor, int horizontalAlignment) {
        this(text, heightFactor, horizontalAlignment, Font.PLAIN);
    }

    public AutoLabel(String text, double heightFactor, int horizontalAlignment, int fontStyle) {
        renderer.setText(text);
        renderer.setHorizontalAlignment(horizontalAlignment);
        baseFont = baseFont.deriveFont(fontStyle);
        this.heightFactor = heightFactor;
    }

    public void setText(String text) {
        renderer.setText(text);
        repaint();
    }
    
    public String getText() {
        return renderer.getText();
    }
    
    public Font getBaseFont() {
        return baseFont;
    }

    public void setBaseFont(Font baseFont) {
        this.baseFont = baseFont;
        repaint();
    }
    
    public void setFontStyle(int style) {
        baseFont = baseFont.deriveFont(style);
        repaint();
    }
    
    @Override
    public void paint(Graphics gr) {
        super.paint(gr);
        adjustFont();
        renderer.paint(gr);
    }
    
    private void adjustFont() {
        Dimension size = getSize();
        int fs = (int) (heightFactor * size.height);
        if (baseFont != null) {
            Font font = new Font(baseFont.getFamily(), baseFont.getStyle(), fs);
            renderer.setFont(font);
        }
        renderer.setSize(getSize());
    }
}
