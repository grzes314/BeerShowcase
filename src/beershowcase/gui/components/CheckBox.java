
package beershowcase.gui.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 *
 * @author Grzegorz Łoś
 */
public class CheckBox extends JPanel {
    private Color frameColor = Color.BLACK;
    private Color crossColor = Color.BLACK;
    private Color backColor = Color.WHITE;
    private boolean selected = false;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void toggleSelection() {
        selected = !selected;
    }

    public Color getFrameColor() {
        return frameColor;
    }

    public void setFrameColor(Color frameColor) {
        this.frameColor = frameColor;
    }

    public Color getCrossColor() {
        return crossColor;
    }

    public void setCrossColor(Color crossColor) {
        this.crossColor = crossColor;
    }

    public Color getBackColor() {
        return backColor;
    }

    public void setBackColor(Color backColor) {
        this.backColor = backColor;
    }    
    
    @Override
    public void paint(Graphics gr) {
        drawFrame(gr);
        
        if (selected) {
            Dimension size = getSize();
            int m = Math.min(size.height, size.width);
            int M = Math.max(size.height, size.width);
            int a = Math.min(M/2, m);
            int rw = (int) (0.8 * a * Math.sqrt(2.0));
            int rh = (int) Math.ceil(rw/16.0);

            int offsetX = (size.width - a) / 2;
            int offsetY = (size.height - a) / 2;


            Graphics2D g = (Graphics2D) gr;
            g.setColor(Color.BLACK);
            g.translate(offsetX, offsetY);
            g.rotate(Math.PI/4);
            g.fillRoundRect((int) (a * Math.sqrt(2.0) - rw) / 2, -rh/2, rw, rh, a/10, a/10);

            g.rotate(-Math.PI/4);
            g.translate(-offsetX, -offsetY);
            // we are back at the original coordinates

            g.translate(offsetX, offsetY + a);
            g.rotate(-Math.PI/4);
            g.fillRoundRect((int) (a * Math.sqrt(2.0) - rw) / 2, -rh/2, rw, rh, a/10, a/10);

            g.rotate(Math.PI/4);
            g.translate(-offsetX, -offsetY - a);
        }
    }
    
    private void drawFrame(Graphics g) {
        Dimension size = getSize();
        int m = Math.min(size.height, size.width);
        int M = Math.max(size.height, size.width);
        int a = Math.min(M/2, m);
        int b = a - (int) Math.round(Math.ceil(a / 10.0));
        
        g.setColor(Color.BLACK);
        g.fillRect((size.width - a) / 2, (size.height - a) / 2, a, a);
        g.setColor(Color.WHITE);
        g.fillRect((size.width - b) / 2, (size.height - b) / 2, b, b);
        
    }
}
