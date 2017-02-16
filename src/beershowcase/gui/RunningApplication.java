
package beershowcase.gui;

import beershowcase.beerdata.BeerKnowledge;
import java.nio.file.FileSystem;
import javax.swing.JFrame;

/**
 *
 * @author Grzegorz Łoś
 */
class RunningApplication {
    static JFrame MainFrame;

    private static AppData appData = new AppData();
    
    static FileSystem getFileSystem() {
        return appData.fileSystem;
    }

    static BeerKnowledge getBeerKnowledge() {
        return appData.beerKnowledge;
    }
    

    static void setData(AppData newAppData) {
        appData = newAppData;
    }
    
    static void resetData() {     
        appData = new AppData();
    }
    
}
