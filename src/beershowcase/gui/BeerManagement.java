
package beershowcase.gui;

import beershowcase.beerdata.BeerKnowledge;
import beershowcase.beerdata.BeerKnowledgeParserException;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
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
    private JMenuItem save;
    private final BeerKnowledge.ChangeListener saveUnlocker = event -> {
            save.setEnabled(true);
        };
    
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
        System.setProperty("jsse.enableSNIExtension", "false");
        
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
        
        save = new JMenuItem("Save");
        save.addActionListener((ActionEvent ae) -> {
            saveClicked();
        });
        save.setEnabled(false);
        
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
    
    private void tabularasaClicked() {
        boolean confirmed = confirmClosingPrevious();
        if (!confirmed)
            return;
        
        closeFileSystem(RunningApplication.getFileSystem());
        RunningApplication.resetData();
        
        save.setEnabled(false);
        RunningApplication.getBeerKnowledge().addChangeListener(saveUnlocker);
        managementPane.reset();
    }

    private void openClicked() {
        boolean confirmed = confirmClosingPrevious();
        if (!confirmed)
            return;
        
        File file = chooseFileToOpen();
        if (file == null)
            return;
        
        setApplicationData(file);
        managementPane.reset();
    }
    
    private void setApplicationData(File file) {
        try {
            AppData appData = BeerKnowledgeIO.readBeerKnowledge(file);
            closeFileSystem(RunningApplication.getFileSystem());
            RunningApplication.setData(appData);
        } catch (IOException ex) {
            LOGGER.log(Level.INFO, null, ex);
            JOptionPane.showMessageDialog(this, "Error while opening a file.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (BeerKnowledgeParserException ex) {
            LOGGER.log(Level.INFO, null, ex);
            JOptionPane.showMessageDialog(this, "Selected file seems to be corrupted.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    private boolean saveClicked() {
        if (RunningApplication.getFileSystem() == null)
            return saveAsClicked();
        
        try {
            RunningApplication.getBeerKnowledge().saveChanges(
                    RunningApplication.getFileSystem());
            save.setEnabled(false);
            return true;
        } catch (IOException ex) {
            LOGGER.log(Level.INFO, null, ex);
            JOptionPane.showMessageDialog(this, "Could not save data.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    private boolean saveAsClicked() {
        File file = chooseFileToSave();
        if (file == null)
            return false;
        
        FileSystem newFileSystem;
        BeerKnowledge bk;
        try {
            newFileSystem = BeerKnowledgeIO.openFileSystem(file);
            bk = RunningApplication.getBeerKnowledge();
            bk.saveAs(RunningApplication.getFileSystem(), newFileSystem);
            closeFileSystem(newFileSystem);
            newFileSystem = BeerKnowledgeIO.openFileSystem(file);
        } catch (IOException ex) {
            LOGGER.log(Level.INFO, null, ex);
            JOptionPane.showMessageDialog(this, "Error while writing to selected file",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        save.setEnabled(false);
        closeFileSystem(RunningApplication.getFileSystem());
        RunningApplication.setData(new AppData(bk, newFileSystem));
        managementPane.reset();
        return true;
    }
    
    private void exitClicked() {
        boolean allowedToExit = confirmClosingPrevious();
        if (allowedToExit) {
            RunningApplication.resetData();
            dispose();
        }
    }
    
    
    private boolean confirmClosingPrevious() {
        if (!RunningApplication.getBeerKnowledge().isModified())
            return true;
        
        int res = JOptionPane.showOptionDialog(
             this, "It seems that you have some unsaved work.\n" +
                   "Do you want to save it?", 
             "Save confirmation", JOptionPane.YES_NO_CANCEL_OPTION, 
             JOptionPane.QUESTION_MESSAGE, null, null, null);
        
        switch (res) {
            case JOptionPane.YES_OPTION:
                return saveClicked();
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

    private void closeFileSystem(FileSystem fileSystem) {
        try {
            BeerKnowledgeIO.closeFileSystem(fileSystem);
        } catch (IOException ex) {
            LOGGER.log(Level.WARNING, "File system has not been closed properly", ex);
            showCantClosePreviousDocument();
        }
    }
    
    private void showCantClosePreviousDocument() {
        JOptionPane.showMessageDialog(this, "Problem occured while closing" +
                "your previous document.\n The data might have been not saved properly.",
                "Error", JOptionPane.ERROR_MESSAGE);
    }
}
