
package beershowcase.beerdata;

import beershowcase.RunningApplication;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * Wrapper of a BufferedImage. Wrappee is read from a disc location
 * only when needed.
 * @author Grzegorz Łoś
 */

class LazyImage {
    private String pathOnFileSystem;
    private BufferedImage image;
    private boolean imageFileExists = false;
    private static final Logger LOGGER = Logger.getLogger(LazyImage.class.getName());
    
    public String getPath() {
        return pathOnFileSystem;
    }

    public void setPath(String path) {
        this.pathOnFileSystem = path;
        checkIfImageFileExists();
    }
    
    public void set(BufferedImage picture) {
        this.image = picture;
    }
          
    public BufferedImage getPicture() {
        if (image == null && !imageFileExists)
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
        Path path = RunningApplication.fileSystem.getPath(pathOnFileSystem);
        try(InputStream is = Files.newInputStream(path)) {
            image = ImageIO.read(is);
        }
    }

    public void save() {
        try {            
            if (image != null)
                writeOnFileSystem();
            imageFileExists = true;
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Failed to save {0}",pathOnFileSystem);
        }
    }
    
    private void writeOnFileSystem() throws IOException {
        Path path = RunningApplication.fileSystem.getPath(pathOnFileSystem);
        try(OutputStream out = Files.newOutputStream(path)) {
            ImageIO.write(image, "jpg", out);
        }
    }
    
    public void release() {
        image = null;
    }

    private void checkIfImageFileExists() {
        if (pathOnFileSystem == null) {
            imageFileExists = false;
        } else {
            Path path = RunningApplication.fileSystem.getPath(pathOnFileSystem);
            imageFileExists = Files.exists(path);
        } 
    }

    /*public void delete() {
        if (imageFileExists) {
            File file = new File(getLocalPath());
            file.delete();
        }
    }*/
}