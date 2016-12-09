
package beershowcase;

import beershowcase.beerdata.BeerKnowledge;
import java.nio.file.FileSystem;
import javax.swing.JFrame;

/**
 *
 * @author Grzegorz Łoś
 */
public class RunningApplication {
    public static JFrame MainFrame;
    
    public static BeerKnowledge beerKnowledge = new BeerKnowledge();
    
    public static FileSystem fileSystem;
}
