
package beershowcase.lazyresources;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Grzegorz Łoś
 */
public abstract class LazyResource {
    private static long nextId = 1;
    private static final Logger LOGGER = Logger.getLogger(LazyResource.class.getName());
    
    private final String pathOnFileSystem;
    protected final long id;
    protected Object newValue;

    public LazyResource(String pathOnFileSystem) {
        this.pathOnFileSystem = pathOnFileSystem;
        id = nextId++;
    }
    

    public String getPathOnFileSystem() {
        return pathOnFileSystem;
    }
    
    public Object getResource(FileSystem fileSystem) {
        if (newValue != null)
            return newValue;
        
        Object ob = getCache().getObject(id);
        if (ob != null)
            return ob;
        
        if (!resourceExists(fileSystem))
            return null;
        try {
            ob = read(fileSystem);
            getCache().addObject(id, ob);
            return ob;
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public void setResource(Object ob) {
        newValue = ob;
        getCache().remove(id);
    }
    
    public boolean isChanged() {
        return newValue != null;
    }

    private boolean resourceExists(FileSystem fileSystem) {
        if (fileSystem == null) {
            return false;
        } else {
            Path path = fileSystem.getPath(pathOnFileSystem);
            return Files.exists(path);
        }
    }
    
    public void saveChanges(FileSystem fileSystem) {
        try {            
            if (isChanged()) {
                write(newValue, fileSystem);
                getCache().addObject(id, newValue);
                newValue = null;
            }
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Failed to save {0}", pathOnFileSystem);
        }
    }

    public void saveAs(FileSystem currFileSystem, FileSystem newFileSystem) {
        try {       
            Object ob = getResource(currFileSystem);
            if (ob != null) {
                write(ob, newFileSystem);
                getCache().remove(id);
                newValue = null;
            }
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Failed to save {0}", pathOnFileSystem);
        }
    }
    
    abstract protected ObjectCache getCache();
    
    abstract protected Object read(FileSystem fileSystem) throws IOException;
    
    abstract protected void write(Object ob, FileSystem fileSystem) throws IOException;
}
