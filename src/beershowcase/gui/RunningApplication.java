
package beershowcase.gui;

import beershowcase.beerdata.BeerKnowledge;
import java.io.File;
import java.io.IOException;
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

    static File getBkFile() {
        return appData.bkFile;
    }
    

    static void setData(AppData newAppData) {
        appData = newAppData;
    }
    
    static void resetData() {     
        appData = new AppData();
    }

    static void flushFileSystem() throws IOException {
        if (appData.bkFile != null) {
            FileSystem reopened = BeerKnowledgeIO.reopen(appData.bkFile, appData.fileSystem);
            appData = new AppData(appData.beerKnowledge, appData.bkFile, reopened);
        }
    }
    
}
