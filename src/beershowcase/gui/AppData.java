
package beershowcase.gui;

import beershowcase.beerdata.BeerKnowledge;
import java.io.File;
import java.nio.file.FileSystem;

/**
 *
 * @author Grzegorz Łoś
 */
public class AppData {
    public final BeerKnowledge beerKnowledge;
    public final File bkFile;
    public final FileSystem fileSystem;

    public AppData() {
        bkFile = null;
        fileSystem = null;
        beerKnowledge = new BeerKnowledge();
    }

    public AppData(BeerKnowledge beerKnowledge, File bkFile, FileSystem fileSystem) {
        if (beerKnowledge == null)
            throw new NullPointerException();
        
        this.bkFile = bkFile;
        this.fileSystem = fileSystem;
        this.beerKnowledge = beerKnowledge;
    }
}
