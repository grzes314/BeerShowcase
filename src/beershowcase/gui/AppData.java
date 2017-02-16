
package beershowcase.gui;

import beershowcase.beerdata.BeerKnowledge;
import java.nio.file.FileSystem;

/**
 *
 * @author Grzegorz Łoś
 */
public class AppData {
    public final BeerKnowledge beerKnowledge;
    public final FileSystem fileSystem;

    public AppData() {
        fileSystem = null;
        beerKnowledge = new BeerKnowledge();
    }

    public AppData(BeerKnowledge beerKnowledge, FileSystem fileSystem) {
        if (beerKnowledge == null)
            throw new NullPointerException();
        
        this.beerKnowledge = beerKnowledge;
        this.fileSystem = fileSystem;
    }
}
