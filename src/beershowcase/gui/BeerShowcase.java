
package beershowcase.gui;

import beershowcase.beerdata.BeerKnowledge;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JOptionPane;

/**
 *
 * @author Grzegorz Łoś
 */
public class BeerShowcase {

    private static final GraphicsDevice grDevice = GraphicsEnvironment
        .getLocalGraphicsEnvironment().getDefaultScreenDevice();
    private static boolean fullScreen = false;
    private static final Logger LOGGER = Logger.getLogger(BeerShowcase.class.getName());
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        BeerKnowledge beerKnowledge = askForBeerKnowledgeFile();
        if (beerKnowledge != null)
            openTheFrame(beerKnowledge);
    }
        
    private static void toggleFullScreenMode() {
        if (fullScreen)
            grDevice.setFullScreenWindow(null);
        else
            grDevice.setFullScreenWindow(RunningApplication.MainFrame);
        fullScreen = !fullScreen;
    }

    private static void openTheFrame(BeerKnowledge beerKnowledge) {
        setUpTheFrame(beerKnowledge);  
        setUpKeyListener();
        launchApplication();
    }

    private static void launchApplication() {
        try {
            RunningApplication.MainFrame.setVisible(true);
            toggleFullScreenMode();
        } finally {
            grDevice.setFullScreenWindow(null);
        }
    }

    private static void setUpKeyListener() {
        RunningApplication.MainFrame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("toggle full screen");
                if ((e.getKeyCode() == KeyEvent.VK_F) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0))
                    toggleFullScreenMode();
            }
        });
    }

    private static void setUpTheFrame(BeerKnowledge beerKnowledge) throws HeadlessException {
        RunningApplication.MainFrame = new JFrame();
        RunningApplication.MainFrame.setSize(800, 600);
        RunningApplication.MainFrame.setTitle("Beer Showcase");
        RunningApplication.MainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        RunningApplication.MainFrame.setContentPane(new BeerShowcasePane());
    }

    private static BeerKnowledge askForBeerKnowledgeFile() {
        BeerKnowledge beerKnowledge = null;
        ShowcaseStartDialog showcaseStartDialog = new ShowcaseStartDialog(null);
        showcaseStartDialog.setLocationRelativeTo(null);
        while (beerKnowledge == null) {
            showcaseStartDialog.setVisible(true);
            File file = showcaseStartDialog.getSelectedFile();
            if (file == null)
                return null;
            beerKnowledge = readBeerKnowledge(file);
        }
        return beerKnowledge;
    }

    private static BeerKnowledge readBeerKnowledge(File file) {
        try {
            BeerKnowledge beerKnowledge = new BeerKnowledge();
            FileSystem fileSystem = openFileSystem(file);
            beerKnowledge.load(fileSystem);
            RunningApplication.data = new RunningApplication.Data(file, fileSystem, beerKnowledge);
            return beerKnowledge;
        } catch (Exception ex) {
            LOGGER.log(Level.INFO, null, ex);
            JOptionPane.showMessageDialog(null, "Selected file seems to be corrupted.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    private static FileSystem openFileSystem(File file) throws IOException {
        if (file != null) {
            URI uri = URI.create("jar:" + file.toURI());
            Map<String, String> env = new HashMap<>(); 
            env.put("create", "true");
            return FileSystems.newFileSystem(uri, env);
        } else {
            return null;
        }
    }
}
