
package beershowcase.gui;

import beershowcase.beerdata.BeerKnowledge;
import beershowcase.beerdata.BeerKnowledgeParserException;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 *
 * @author Grzegorz Łoś
 */
public class BeerKnowledgeIO {
    
    private static final Logger LOGGER = Logger.getLogger(BeerKnowledgeIO.class.getName());
    
    public static void closeFileSystem(FileSystem fileSystem) throws IOException {
        if (fileSystem != null)
            fileSystem.close();
    }
    
    public static FileSystem openFileSystem(File file) throws IOException {
        try {
            if (file != null) {
                URI uri = URI.create("jar:" + file.toURI());
                Map<String, String> env = new HashMap<>(); 
                env.put("create", "true");
                return FileSystems.newFileSystem(uri, env);
            } else {
                return null;
            }
        } catch (IOException ex) {
            throw ex;
        } catch (RuntimeException ex) {
            throw new IOException("RuntimeException while opening file system "
                    + file.getAbsolutePath(), ex);
        }
    }
    
    public static AppData readBeerKnowledge(File file)
            throws IOException, BeerKnowledgeParserException {
        FileSystem fileSystem = openFileSystem(file);
        BeerKnowledge bk = new BeerKnowledge();
        try {
            bk.load(fileSystem);
        } catch (BeerKnowledgeParserException ex) {
            fileSystem.close();
            throw ex;
        }
        return new AppData(bk, file, fileSystem);
    }

    static FileSystem reopen(File bkFile, FileSystem fileSystem) throws IOException {
        closeFileSystem(fileSystem);
        return BeerKnowledgeIO.openFileSystem(bkFile);
    }
}
