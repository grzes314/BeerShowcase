
package beershowcase.gui.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * A JLabel which changes its font size depending on the size.
 * @author Grzegorz Łoś
 */
public class AutoLabel extends JPanel {
    Font baseFont = new Font("Default", Font.PLAIN, 12);
    JLabel renderer = new JLabel();
    static Random r = new Random();

    public AutoLabel() {
        super();
        setBorder( BorderFactory.createLineBorder(getColor(), 5));
    }
    
    public AutoLabel(String text) {
        renderer.setText(text);
        setBorder( BorderFactory.createLineBorder(getColor(), 5));
    }

    public AutoLabel(String text, int horizontalAlignment) {
        renderer.setText(text);
        renderer.setHorizontalAlignment(horizontalAlignment);
    }
    
    private Color getColor() {
        return new Color(
                r.nextInt(256),
                r.nextInt(256),
                r.nextInt(256)
        );
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
        //renderer.setPreferredSize(getSize());
    }
}

/*
public class AutoLabel extends JLabel {
    Font baseFont = new Font("Default", Font.PLAIN, 12);
    ComponentListener componentListener = new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                adjustFont();
            }
        };

    public AutoLabel() {
        super();
        addComponentListener(componentListener);
    }

    public AutoLabel(Font baseFont) {
        super();
        this.baseFont = baseFont;
        addComponentListener(componentListener);
    }

    public AutoLabel(String text) {
        super(text);
        addComponentListener(componentListener);
    }

    public AutoLabel(String text, int horizontalAlignment) {
        super(text, horizontalAlignment);
        addComponentListener(componentListener);
    }

    public AutoLabel(Font baseFont, String text) {
        super(text);
        this.baseFont = baseFont;
        addComponentListener(componentListener);
    }

    public Font getBaseFont() {
        return baseFont;
    }

    public void setBaseFont(Font baseFont) {
        this.baseFont = baseFont;
    }
    
    @Override
    public Dimension getPreferredSize() {
        return getSize();
    }
    
    private void adjustFont() {
        Dimension size = getSize();
        int fs = size.height / 2;
        if (baseFont != null) {
            Font font = new Font(baseFont.getFamily(), baseFont.getStyle(), fs);
            this.setFont(font);
        }
    }
}
*/
