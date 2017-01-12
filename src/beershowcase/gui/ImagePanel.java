
package beershowcase.gui;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

/**
 *
 * @author Grzegorz Łoś
 */
public class ImagePanel extends JPanel {
    public Image image;

    public ImagePanel() {
    }

    public ImagePanel(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
    
    @Override
    public void paint(Graphics gr) {
        double f = getResizeFactor();
        int W = this.getSize().width;
        int H = this.getSize().height;
        int w = (int)(f * image.getWidth(null));
        int h = (int)(f * image.getHeight(null));
        gr.drawImage(image, (W-w)/2, (H-h)/2, w, h, null);
    }

    private double getResizeFactor() {
        double f1 = this.getSize().getWidth() / image.getWidth(null);
        double f2 = this.getSize().getHeight() / image.getHeight(null);
        return Math.min(f1, f2);
    }
}
