
package beershowcase.gui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 *
 * @author grzes
 */
public class ImageUtils {
    
    public static BufferedImage getPicture(URL url) {
        try {  
            BufferedImage image = ImageIO.read(url);
            return image;
        } catch (IOException ex) {
            return null;
        }
    }
    
    public static BufferedImage getPicture(String url) {
        try {
            return getPicture(new URL(url));
        } catch (MalformedURLException ex) {
            return null;
        }
    }
    
    public static BufferedImage getLocalPicture(File file) {
        try {  
            BufferedImage image = ImageIO.read(file);
            return image;
        } catch (IOException ex) {
            return null;
        }
    }
    
    public static BufferedImage getLocalPicture(String pathToFile) {
        return getLocalPicture(new File(pathToFile));
    }
}
