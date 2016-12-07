
package beershowcase;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

/**
 *
 * @author Grzegorz Łoś
 */
public class ImagePanel extends JPanel {
    public final Image image;

    public ImagePanel(Image image) {
        this.image = image;
    }
    
    @Override
    public void paint(Graphics gr) {
        double f = getResizeFactor();
        gr.drawImage(image, 0, 0, (int)(f * image.getWidth(null)),
                (int)(f * image.getHeight(null)), null);
    }

    private double getResizeFactor() {
        double f1 = this.getSize().getWidth() / image.getWidth(null);
        double f2 = this.getSize().getHeight() / image.getHeight(null);
        return Math.min(f1, f2);
    }
}
