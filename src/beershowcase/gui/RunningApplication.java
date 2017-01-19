
package beershowcase.gui;

import beershowcase.beerdata.BeerKnowledge;
import java.io.File;
import java.nio.file.FileSystem;
import javax.swing.JFrame;

/**
 *
 * @author Grzegorz Łoś
 */
public class RunningApplication {
    public static class Data {

        public File bkFile;

        public FileSystem fileSystem;

        public BeerKnowledge beerKnowledge;

        public Data() {
        }

        public Data(File bkFile, FileSystem fileSystem, BeerKnowledge beerKnowledge) {
            this.bkFile = bkFile;
            this.fileSystem = fileSystem;
            this.beerKnowledge = beerKnowledge;
        }
    }
        
    public static JFrame MainFrame;
    
    public static Data data = new Data();
}
