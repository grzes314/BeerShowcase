
package beershowcase.beerdata;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * Wrapper of a BufferedImage. Wrappee is read from a disc location
 * only when needed.
 * @author Grzegorz Łoś
 */

class LazyImage {
    private static final String PATH_TO_IMG_REPO = "~/.BeerShowcase/pictures/";
    private String imageName;
    private BufferedImage image;
    private boolean imageFileExists = false;
    private static final Logger LOGGER = Logger.getLogger(LazyImage.class.getName());
    
    static {
        File file = new File(PATH_TO_IMG_REPO);
        file.mkdirs();
    }
    
    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
        checkIfImageFileExists();
    }
    
    public String getLocalPath() {
        return PATH_TO_IMG_REPO + imageName;
    }
    
    public void set(BufferedImage picture) {
        this.image = picture;
    }
          
    public BufferedImage getPicture() {
        if (!imageFileExists)
            return null;
        
        try {
            if (image == null)
                readPicture();
        } catch (IOException ex) {
            LOGGER.log(Level.INFO, null, ex);
        }
        return image;
    }

    private void readPicture() throws IOException {
        File file = new File(getLocalPath());
        image = ImageIO.read(file);
    }

    public void save() {
        try {            
            if (image != null)
                ImageIO.write(image, "jpg", new File(getLocalPath()));
            imageFileExists = true;
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Failed to save {0}", getLocalPath());
        }
    }
    
    public void release() {
        image = null;
    }

    private void checkIfImageFileExists() {
        if (imageName == null) {
            imageFileExists = false;
        } else {
            File file = new File(getLocalPath());
            imageFileExists = file.exists();
        } 
    }

}