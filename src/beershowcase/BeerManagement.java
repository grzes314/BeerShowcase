
package beershowcase;

import beershowcase.beerdata.BeerKnowledge;
import beershowcase.beerdata.BeerKnowledgeParserException;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author grzes
 */
public class BeerManagement extends JFrame {
    private final ManagementPane managementPane = new ManagementPane();
    private static final Logger LOGGER = Logger.getLogger(BeerManagement.class.getName());
    
    JFileChooser fileChooser = new JFileChooser();
    
    WindowListener exitListener = new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
            exitClicked();
        }
    };
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            tabularasa();
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Cannot initialize the application.",
                    "Fatal error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        
        SwingUtilities.invokeLater(() -> {
            RunningApplication.MainFrame = new BeerManagement();
            RunningApplication.MainFrame.setLocationRelativeTo(null);
            RunningApplication.MainFrame.setVisible(true);
        });
    }
    
    public BeerManagement() {
        init();
    }
    
    private void init() {
        setSize(800, 600);
        setTitle("Beer Management");
        setContentPane(managementPane);
        setJMenuBar(makeMenuBar());
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(exitListener);
    }

    private JMenuBar makeMenuBar() {
        JMenuItem tabularasa = new JMenuItem("Tabula Rasa");
        tabularasa.addActionListener((ActionEvent ae) -> {
            tabularasaClicked();
        });
        
        JMenuItem open = new JMenuItem("Open");
        open.addActionListener((ActionEvent ae) -> {
            openClicked();
        });
        
        JMenuItem save = new JMenuItem("Save");
        save.addActionListener((ActionEvent ae) -> {
            saveClicked();
        });
        //save.setEnabled(false);
        
        JMenuItem saveAs = new JMenuItem("Save as");
        saveAs.addActionListener((ActionEvent ae) -> {
            saveAsClicked();
        });
        
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener((ActionEvent ae) -> {
            exitClicked();
        });
        
        
        JMenu file = new JMenu("File");
        file.add(tabularasa);
        file.add(open);
        file.add(save);
        file.add(saveAs);
        file.add(exit);
        JMenuBar jMenuBar = new JMenuBar();
        jMenuBar.add(file);
        return jMenuBar;
    }

    private static void tabularasa() throws IOException {
        closeFileSystem(RunningApplication.data.fileSystem);
        RunningApplication.data = new RunningApplication.Data();
        RunningApplication.data.beerKnowledge = new BeerKnowledge();
    }
    
    private void tabularasaClicked() {
        boolean confirmed = confirmClosingPrevious();
        if (confirmed) {
            try {
                tabularasa();
            } catch (IOException ex) {
                LOGGER.log(Level.WARNING, null, ex);
                showCantClosePreviousDocument();
            } finally {
                RunningApplication.data = new RunningApplication.Data();
                RunningApplication.data.beerKnowledge = new BeerKnowledge();
            }
        }
        managementPane.reset();
    }

    private void openClicked() {
        boolean confirmed = confirmClosingPrevious();
        if (!confirmed)
            return;
        
        File file = chooseFileToOpen();
        if (file == null)
            return;
        
        RunningApplication.Data prevAppData = RunningApplication.data;
        boolean succ = loadNewApplicationData(file);
        
        if (succ) {
            try {
                closeFileSystem(prevAppData.fileSystem);
            } catch (IOException ex) {
                LOGGER.log(Level.WARNING, null, ex);
                showCantClosePreviousDocument();
            }
        } else {
            RunningApplication.data = prevAppData;
        }
        managementPane.reset();
    }
    
    
    private boolean saveClicked() {
        try {
            RunningApplication.data.beerKnowledge.saveChanges();
            flushFileSystem(RunningApplication.data.fileSystem, RunningApplication.data.bkFile);
            return true;
        } catch (IOException ex) {
            LOGGER.log(Level.INFO, null, ex);
            JOptionPane.showMessageDialog(this, "Error while saving file.\n" +
                    "Data might not have been saved.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    private boolean saveAsClicked() {
        File file = chooseFileToSave();
        if (file == null)
            return false;
         
        RunningApplication.Data prevAppData = RunningApplication.data;
        RunningApplication.data = new RunningApplication.Data();
        boolean saved = false;
        try {
            RunningApplication.data.bkFile = file;
            RunningApplication.data.fileSystem = openFileSystem(file);
            RunningApplication.data.beerKnowledge = prevAppData.beerKnowledge;
            RunningApplication.data.beerKnowledge.saveEverything();
            flushFileSystem(RunningApplication.data.fileSystem, RunningApplication.data.bkFile);
            saved = true;
        } catch (IOException ex) {
            LOGGER.log(Level.INFO, null, ex);
            JOptionPane.showMessageDialog(this, "Error while writing to selected file",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        if (saved) {
            try {
                closeFileSystem(prevAppData.fileSystem);
            } catch (IOException ex) {
                LOGGER.log(Level.WARNING, null, ex);
                showCantClosePreviousDocument();
            }
            return true;
        } else {
            RunningApplication.data = prevAppData;
            return false;
        }
    }
    
    private void exitClicked() {
        boolean allowedToExit = confirmClosingPrevious();
        if (allowedToExit) {
            confirmClosingPrevious();
            dispose();
        }
    }
    
    private boolean loadNewApplicationData(File file) {
        try {
            RunningApplication.data = new RunningApplication.Data();
            RunningApplication.data.bkFile = file;
            RunningApplication.data.fileSystem =  openFileSystem(file);
            RunningApplication.data.beerKnowledge = new BeerKnowledge();
            RunningApplication.data.beerKnowledge.load();
            return true;
        } catch (IOException ex) {
            LOGGER.log(Level.INFO, null, ex);
            JOptionPane.showMessageDialog(this, "Selected file seems to be corrupted.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (BeerKnowledgeParserException ex) {
            LOGGER.log(Level.INFO, null, ex);
            JOptionPane.showMessageDialog(this, "Selected file seems to be corrupted.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            try {
                closeFileSystem(RunningApplication.data.fileSystem);
            } catch (IOException ex1) {
                LOGGER.log(Level.SEVERE, null, ex1);
            }
        }
        return false;
    }
    
    private void showCantClosePreviousDocument() {
        JOptionPane.showMessageDialog(this, "There was a problem with closing" +
                "of your previous document.\n The data might have been demaged.",
                "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    private boolean confirmClosingPrevious() {
        if (!RunningApplication.data.beerKnowledge.isModified())
            return true;
        
        int res = JOptionPane.showOptionDialog(
             this, "It seems that you have some unsaved work.\n" +
                   "Do you want to save it?", 
             "Save confirmation", JOptionPane.YES_NO_CANCEL_OPTION, 
             JOptionPane.QUESTION_MESSAGE, null, null, null);
        
        switch (res) {
            case JOptionPane.YES_OPTION:
                return saveBeforeClose();
            case JOptionPane.NO_OPTION:
                return true;
            default:
                return false;
        }
    }
    
    private File chooseFileToOpen() {
        int res = fileChooser.showOpenDialog(this);
        if (res == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fileChooser.getSelectedFile();
                return file;                
            } catch (Throwable t) {
                JOptionPane.showMessageDialog(this, "Can't open selected file",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        } else {
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
    
    private static void flushFileSystem(FileSystem fs, File zipFile) throws IOException {
        if (fs != null) {
            fs.close();
            fs = openFileSystem(zipFile);
        }
    }
    
    private static void closeFileSystem(FileSystem fs) throws IOException {
        if (fs != null) {
            fs.close();
        }        
    }
    
    private File chooseFileToSave() {
        int res = fileChooser.showSaveDialog(this);
        if (res == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fileChooser.getSelectedFile();
                String path = file.getAbsolutePath();
                if (!path.endsWith(".bk"))
                    file = new File(path + ".bk");
                file.getParentFile().mkdirs();
                if (!file.exists() || askIfOverwrite(file))
                    return file;  
                else
                    return null;
            } catch (Throwable t) {
                JOptionPane.showMessageDialog(this, "Can't write to the selected file",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        } else {
            return null;
        }
    }
    
    boolean askIfOverwrite(File file) {
        int confirm = JOptionPane.showOptionDialog(
             this, "File \"" + file.getName() + "\" already exists.\n" +
                   "Do you want to overwrite?", 
             "Overwrite Confirmation", JOptionPane.YES_NO_OPTION, 
             JOptionPane.QUESTION_MESSAGE, null, null, null);
        return confirm == JOptionPane.YES_OPTION;
    }
    
    private boolean saveBeforeClose() {
        if (RunningApplication.data.fileSystem == null)
            return saveAsClicked();
        else
            return saveClicked();
    }
}
