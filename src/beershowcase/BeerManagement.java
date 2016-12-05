
package beershowcase;

import beershowcase.beerdata.BeerKnowledge;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonStructure;
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
        SwingUtilities.invokeLater(() -> {
            JFrame mainFrame = new BeerManagement();
            mainFrame.setVisible(true);
        });
    }
    
    public BeerManagement() {
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
        
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener((ActionEvent ae) -> {
            exitClicked();
        });
        
        
        JMenu file = new JMenu("File");
        file.add(tabularasa);
        file.add(open);
        file.add(save);
        file.add(exit);
        JMenuBar jMenuBar = new JMenuBar();
        jMenuBar.add(file);
        return jMenuBar;
    }

    private void tabularasaClicked() {
        managementPane.startFromScratch();
    }


    private void openClicked() {
            File file = chooseFileToOpen();
            if (file == null)
                return;
        try {
            JsonObject json = readJsonFromFile(file);
            BeerKnowledge beerKnowledge = new BeerKnowledge();
            beerKnowledge.fromJson(json);
            managementPane.setBeerKnowledge(beerKnowledge);
        } catch (Exception ex) {
            LOGGER.log(Level.INFO, null, ex);
            JOptionPane.showMessageDialog(this, "Selected file seems to be corrupted.",
                    "Error", JOptionPane.ERROR_MESSAGE);
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

    private JsonObject readJsonFromFile(File file) throws FileNotFoundException {
        JsonObject json;
        try (JsonReader jsonReader = Json.createReader(new FileInputStream(file))) {
            json = jsonReader.readObject();
        }
        return json;
    }
    
    /*
    private void openClicked() {
        File file = chooseFileToOpen();
        if (file == null)
            return;
        ByteArrayInputStream bais = readFileToByteStream(file);
        if (bais != null)
            readBeerKnowledlgeFromByteStream(bais);        
    }

    private ByteArrayInputStream readFileToByteStream(File file) {
        try {
            byte[] bytes = Files.readAllBytes(file.toPath());
            return new ByteArrayInputStream(bytes);
        } catch (IOException ex) {
            Logger.getLogger(BeerManagement.class.getName()).log(Level.INFO,
                    "Failed to open beer knowledge file", ex);
            JOptionPane.showMessageDialog(this, "Failed to read from selected file",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    private void readBeerKnowledlgeFromByteStream(ByteArrayInputStream bais) {
        try {
            BeerKnowledge beerKnowledge = new BeerKnowledge();
            beerKnowledge.readFromByteStream(bais);
            managementPane.setBeerKnowledge(beerKnowledge);
            JOptionPane.showMessageDialog(this, "File succesfully read!",
                    "Finished reading", JOptionPane.INFORMATION_MESSAGE);
        } catch (Throwable t) {
            LOGGER.log(Level.INFO, "Failed to read from file", t);
            JOptionPane.showMessageDialog(this, "Error while reading from selected file",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    */

    private void saveClicked() {
        File file = chooseFileToSave();
        if (file == null)
            return;
        JsonObject json = managementPane.getBeerKnowledge().toJson();
        try {
            saveJsonToFile(json, file);
        } catch (IOException ex) {
            LOGGER.log(Level.INFO, null, ex);
            JOptionPane.showMessageDialog(this, "Error while writing to selected file",
                    "Error", JOptionPane.ERROR_MESSAGE);
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
                return file;                
            } catch (Throwable t) {
                JOptionPane.showMessageDialog(this, "Can't write to the selected file",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        } else {
            return null;
        }
    }

    private void saveJsonToFile(JsonObject json, File file) throws IOException {
        try (BufferedWriter out = new BufferedWriter(new FileWriter(file))) {
            String jsonString = json.toString();
            out.write(jsonString, 0, jsonString.length());
        }
    }
    
    /*

    private void saveClicked() {
        File file = chooseFileToSave();
        if (file == null)
            return;
        ByteArrayOutputStream baos = saveToByteStream();
        if (baos != null)
            saveByteStreamToFile(baos, file);        
    }
    
    private ByteArrayOutputStream saveToByteStream() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            managementPane.getBeerKnowledge().addToByteStream(baos);
        } catch (Throwable t) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
                    "Failed to write beer knowledge to byte stream", t);
            JOptionPane.showMessageDialog(this, "Error while creating byte representation of the beer knowledge",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return baos;
    }
    
    private void saveByteStreamToFile(ByteArrayOutputStream baos, File file) {
        try(OutputStream outputStream = new FileOutputStream(file)) {
            baos.writeTo(outputStream);
            
            JOptionPane.showMessageDialog(this, "Data succesfully written!",
                    "Finished saving", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error while writing to file",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }*/

    private void exitClicked() {
        int confirm = JOptionPane.showOptionDialog(
             this, "Are You Sure to Close Application?", 
             "Exit Confirmation", JOptionPane.YES_NO_OPTION, 
             JOptionPane.QUESTION_MESSAGE, null, null, null);
        if (confirm == JOptionPane.YES_OPTION) {
           dispose();
        }
    }
}
