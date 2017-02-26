
package beershowcase.lazyresources;

import beershowcase.utils.Box;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import javax.imageio.ImageIO;

/**
 * Wrapper of a BufferedImage. Wrappee is read from a disc location
 * only when needed.
 * @author Grzegorz Łoś
 */

public class LazyImage extends LazyResource {
    
    private static final ObjectCache CACHE = new SimpleCache();
    
    public LazyImage(String pathOnFileSystem) {
        super(pathOnFileSystem);
    }
    
    public void setPicture(BufferedImage picture) {
        setResource(picture);
    }
          
    public Box<BufferedImage> getPicture(FileSystem fileSystem) {
        Box box =  getResource(fileSystem);
        if (box.isEmpty())
            return new Box<>();
        else
            return new Box<>((BufferedImage) box.getValue());
    }

    @Override
    protected Object read(FileSystem fileSystem) throws IOException {
        Path path = fileSystem.getPath(getPathOnFileSystem());
        try(InputStream is = Files.newInputStream(path)) {
            return ImageIO.read(is);
        }
    }
    
    @Override
    protected void write(Object ob, FileSystem fileSystem) throws IOException {
        Path path = fileSystem.getPath(getPathOnFileSystem());
        Path parentDir = path.getParent();
        if (!Files.exists(parentDir))
            Files.createDirectories(parentDir);
        BufferedImage image = (BufferedImage) ob;
        try(OutputStream out = Files.newOutputStream(path, StandardOpenOption.CREATE)) {
            ImageIO.write(image, "jpg", out);
        }
    }

    @Override
    protected ObjectCache getCache() {
        return CACHE;
    }
}