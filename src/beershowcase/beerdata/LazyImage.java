
package beershowcase.beerdata;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * Wrapper of a BufferedImage. Wrappee is read from a disc location
 * only when needed.
 * @author Grzegorz Łoś
 */

public class LazyImage {
    private final String pathOnFileSystem;
    private BufferedImage image;
    private boolean imageFileChanged = false;
    private static final Logger LOGGER = Logger.getLogger(LazyImage.class.getName());

    public LazyImage(String pathOnFileSystem) {
        this.pathOnFileSystem = pathOnFileSystem;
    }
    
    public String getPath() {
        return pathOnFileSystem;
    }
    
    public void setPicture(BufferedImage picture) {
        this.image = picture;
        imageFileChanged = true;
    }
          
    public BufferedImage getPicture(FileSystem fileSystem) {
        if (image == null && !imageFileExists(fileSystem))
            return null;
        
        try {
            if (image == null)
                readPicture(fileSystem);
        } catch (IOException ex) {
            LOGGER.log(Level.WARNING, null, ex);
        }
        return image;
    }

    private void readPicture(FileSystem fileSystem) throws IOException {
        Path path = fileSystem.getPath(pathOnFileSystem);
        try(InputStream is = Files.newInputStream(path)) {
            image = ImageIO.read(is);
        }
    }

    public void saveIfChanged(FileSystem fileSystem) {
        try {            
            if (image != null && imageFileChanged)
                writeOnFileSystem(fileSystem);
            imageFileChanged = false;
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Failed to save {0}", pathOnFileSystem);
        }
    }

    public void saveForced(FileSystem fileSystem) {
        try {            
            if (image != null)
                writeOnFileSystem(fileSystem);
            imageFileChanged = false;
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Failed to save {0}", pathOnFileSystem);
        }
    }
    
    private void writeOnFileSystem(FileSystem fileSystem) throws IOException {
        Path path = fileSystem.getPath(pathOnFileSystem);
        Path parentDir = path.getParent();
        if (!Files.exists(parentDir))
            Files.createDirectories(parentDir);

        try(OutputStream out = Files.newOutputStream(path, StandardOpenOption.CREATE)) {
            ImageIO.write(image, "jpg", out);
        }
    }
    
    /**
     * Don't store the image object in memory any more.
     */
    public void release() {
        image = null;
    }

    private boolean imageFileExists(FileSystem fileSystem) {
        if (fileSystem == null) {
            return false;
        } else {
            Path path = fileSystem.getPath(pathOnFileSystem);
            return Files.exists(path);
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