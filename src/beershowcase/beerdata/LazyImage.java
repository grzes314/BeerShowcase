
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
    private final String pathOnFileSystem;
    private BufferedImage image;
    private boolean imageFileExists = false;
    private boolean imageFileChanged = false;
    private static final Logger LOGGER = Logger.getLogger(LazyImage.class.getName());

    public LazyImage(String pathOnFileSystem) {
        this.pathOnFileSystem = pathOnFileSystem;
        checkIfImageFileExists();
    }
    
    public String getPath() {
        return pathOnFileSystem;
    }
    
    public void setPicture(BufferedImage picture) {
        this.image = picture;
        imageFileChanged = true;
    }
          
    public BufferedImage getPicture() {
        if (!imageFileExists)
            return null;
        
        try {
            if (image == null)
                readPicture();
        } catch (IOException ex) {
            LOGGER.log(Level.WARNING, null, ex);
        }
        return image;
    }

    private void readPicture() throws IOException {
        Path path = RunningApplication.data.fileSystem.getPath(pathOnFileSystem);
        try(InputStream is = Files.newInputStream(path)) {
            image = ImageIO.read(is);
        }
    }

    public void saveIfChanged() {
        try {            
            if (image != null && imageFileChanged)
                writeOnFileSystem();
            imageFileExists = true;
            imageFileChanged = false;
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Failed to save {0}", pathOnFileSystem);
        }
    }

    public void saveForced() {
        try {            
            if (image != null)
                writeOnFileSystem();
            imageFileExists = true;
            imageFileChanged = false;
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Failed to save {0}", pathOnFileSystem);
        }
    }
    
    private void writeOnFileSystem() throws IOException {
        Path path = RunningApplication.data.fileSystem.getPath(pathOnFileSystem);
        try(OutputStream out = Files.newOutputStream(path)) {
            ImageIO.write(image, "jpg", out);
        }
    }
    
    /**
     * Don't store the image object in memory any more.
     */
    public void release() {
        image = null;
    }

    private void checkIfImageFileExists() {
        if (RunningApplication.data.fileSystem == null) {
            imageFileExists = false;
        } else {
            Path path = RunningApplication.data.fileSystem.getPath(pathOnFileSystem);
            imageFileExists = Files.exists(path);
        }
    }
    
    public boolean isChanged() {
        return imageFileChanged;
    }

    /*public void delete() {
        if (imageFileExists) {
            File file = new File(getLocalPath());
            file.delete();
        }
    }*/
}