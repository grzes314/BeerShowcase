
package beershowcase.gui;

import beershowcase.beerdata.Beer;
import beershowcase.beerdata.BeerKnowledge.ChangeEvent;
import beershowcase.beerdata.BeerProperties;
import beershowcase.beerdata.Brewery;
import beershowcase.beerdata.StyleKeyword;
import beershowcase.beerdata.autostyle.StyleFinder;
import beershowcase.beerdata.autostyle.simple.SimpleStyleFinder;
import beershowcase.external.ExternalSource;
import beershowcase.external.ExternalSourceDispatcher;
import beershowcase.gui.components.AutoLabel;
import beershowcase.gui.components.RelativeLayout;
import beershowcase.utils.Box;
import beershowcase.utils.FixedPointReal;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.image.BufferedImage;
import java.util.Collection;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author Grzegorz Łoś
 */
public class EditBeerDialog extends JDialog {
    private final AllKeywordsPanel keywordsPanel = new AllKeywordsPanel();
    private final SelectImagePanel labelImageSelector = new SelectImagePanel();
    private boolean confirmed = false;
    private Brewery selectedBrewery;
    private final StyleFinder styleFinder = new SimpleStyleFinder();
    private final ExternalSource external = new ExternalSourceDispatcher();
    
    JButton autofillButton = new JButton("Auto fill");
    JButton selectBreweryButton = new JButton("...");
    JButton okButton = new JButton("OK");
    JButton cancelButton = new JButton("Cancel");
    
    JCheckBox available = new JCheckBox("Available", true);
    JCheckBox novelty = new JCheckBox("Novelty", false);
    JCheckBox priceKnown = new JCheckBox("set price");
    JCheckBox blgKnown = new JCheckBox();
    JCheckBox abvKnown = new JCheckBox();
    JCheckBox ibuKnown = new JCheckBox();
    
    JSpinner priceSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 1000.0, 0.10));
    JSpinner blgSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 100.0, 0.10));
    JSpinner abvSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 100.0, 0.10));
    JSpinner ibuSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 200.0, 1.0));
    
    JTextField nameField = new JTextField();
    JTextField styleField = new JTextField();
    JTextField breweryField = new JTextField();
    JTextArea description = new JTextArea();
    
    /**
     * Creates new form EditBeerDialog
     * @param parent
     */
    public EditBeerDialog(java.awt.Frame parent) {
        super(parent, true);
        initComponents();
        setTitle("Add a new beer");
        setSize(800,600);
    }

    EditBeerDialog(java.awt.Frame parent, Beer beer) {
        super(parent, true);
        initComponents();
        setDataFrom(beer);
        setSize(800,600);
        setTitle("Edit a beer");
    }
    
    private void initComponents() {
        RelativeLayout rl = new RelativeLayout(RelativeLayout.Y_AXIS);
        rl.setFill(true);
        setLayout(rl);
        add(new AutoLabel("Fill-in beer information", JLabel.CENTER, Font.BOLD), new Float(8));
        
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("Main", createMainTab());
        tabbedPane.add("Tags", new JScrollPane(keywordsPanel));
        tabbedPane.add("Image", labelImageSelector);
        add(tabbedPane, new Float(92));
        
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                setFontSizes();
            }
        });
    }
    
    private JPanel createMainTab() {
        RelativeLayout rl = new RelativeLayout(RelativeLayout.Y_AXIS);
        rl.setFill(true);
        JPanel panel = new JPanel(rl);
        panel.add(createTopPanel(), new Float(2));
        panel.add(createNamePanel(), new Float(2));
        panel.add(new JPanel(), new Float(1));
        panel.add(createBreweryPanel(), new Float(2));
        panel.add(new JPanel(), new Float(1));
        panel.add(createDescPanel(), new Float(6));
        panel.add(new JPanel(), new Float(1));
        panel.add(createBottomPanel(), new Float(6));
        panel.add(createConfirmPanel(), new Float(3));
        return panel;
    }
    
    private JPanel createTopPanel() {
        JPanel panel = new JPanel();
        autofillButton.addActionListener((ActionEvent e) -> autoFillClicked());
        panel.add(autofillButton);
        return panel;
    }
    
    private JPanel createNamePanel() {
        RelativeLayout rl = new RelativeLayout(RelativeLayout.X_AXIS);
        rl.setFill(true);
        JPanel panel = new JPanel(rl);
        panel.add(new AutoLabel("Beer name: ", 0.4, JLabel.RIGHT), new Float(2));
        panel.add(nameField, new Float(8));
        return panel;
    }
    
    private JPanel createBreweryPanel() {
        RelativeLayout rl = new RelativeLayout(RelativeLayout.X_AXIS);
        rl.setFill(true);
        JPanel panel = new JPanel(rl);
        panel.add(new AutoLabel("Brewery: ", 0.4, JLabel.RIGHT), new Float(2));
        breweryField.setEditable(false);
        selectBreweryButton.addActionListener((ActionEvent e) -> selectBreweryClicked());
        panel.add(breweryField, new Float(7));
        panel.add(selectBreweryButton, new Float(1));
        return panel;
    }
    
    private JPanel createDescPanel() {
        RelativeLayout rl = new RelativeLayout(RelativeLayout.X_AXIS);
        rl.setFill(true);
        JPanel panel = new JPanel(rl);
        panel.add(new AutoLabel("Description: ", 0.15, JLabel.RIGHT), new Float(2));
        panel.add(new JScrollPane(description), new Float(8));
        return panel;
    }
    
    private JPanel createBottomPanel() {
        RelativeLayout rl = new RelativeLayout(RelativeLayout.X_AXIS);
        rl.setFill(true);
        JPanel panel = new JPanel(rl);
        panel.add(createParamsPanel(), new Float(5));
        panel.add(createStorePanel(), new Float(5));
        return panel;
    }
    
    private JPanel createParamsPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 3));
        
        panel.add(new JLabel());
        panel.add(new AutoLabel("is known?", JLabel.CENTER));
        panel.add(new AutoLabel("value", JLabel.CENTER));
        
        panel.add(new AutoLabel("Gravity (Blg)", JLabel.RIGHT));
        blgKnown.setHorizontalAlignment(AbstractButton.CENTER);
        blgKnown.addItemListener((ItemEvent e) -> {
            blgSpinner.setEnabled(blgKnown.isSelected());
        });
        blgSpinner.setEnabled(false);
        panel.add(blgKnown);
        panel.add(blgSpinner);
        
        panel.add(new AutoLabel("Alc. vol.", JLabel.RIGHT));
        abvKnown.setHorizontalAlignment(AbstractButton.CENTER);
        abvSpinner.setEnabled(false);
        abvKnown.addItemListener((ItemEvent e) -> {
            abvSpinner.setEnabled(abvKnown.isSelected());
        });
        panel.add(abvKnown);
        panel.add(abvSpinner);
        
        panel.add(new AutoLabel("IBU", JLabel.RIGHT));
        ibuKnown.setHorizontalAlignment(AbstractButton.CENTER);
        ibuSpinner.setEnabled(false);
        ibuKnown.addItemListener((ItemEvent e) -> {
            ibuSpinner.setEnabled(ibuKnown.isSelected());
        });
        panel.add(ibuKnown);
        panel.add(ibuSpinner);
        
        return panel;
    }
    
    private JPanel createStorePanel() {
        JPanel panel = new JPanel(new GridLayout(1,0, 10, 10));
        panel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        
        RelativeLayout rl = new RelativeLayout(RelativeLayout.Y_AXIS);
        rl.setFill(true);
        JPanel pricePanel = new JPanel(rl);
        priceKnown.setVerticalAlignment(AbstractButton.BOTTOM);
        priceSpinner.setEnabled(false);
        priceKnown.addItemListener((ItemEvent e) -> {
            priceSpinner.setEnabled(priceKnown.isSelected());
        });
        pricePanel.add(new AutoLabel("Store"), new Float(1));
        pricePanel.add(priceKnown, new Float(1));
        pricePanel.add(priceSpinner, new Float(1));
        pricePanel.add(new JPanel(), new Float(1));
        
        panel.add(pricePanel);
        //panel.add(new JPanel());
        panel.add(available);
        //panel.add(new JPanel());
        panel.add(novelty);
        
        return panel;
    }
    
    private JPanel createConfirmPanel() {
        JPanel panel = new JPanel();
        BoxLayout bl = new BoxLayout(panel, BoxLayout.X_AXIS);
        panel.setLayout(bl);
        panel.add(javax.swing.Box.createHorizontalGlue());
        cancelButton.addActionListener((ActionEvent e) -> cancelClicked());
        cancelButton.setVerticalAlignment(JButton.BOTTOM);
        okButton.addActionListener((ActionEvent e) -> okClicked());
        okButton.setVerticalAlignment(JButton.BOTTOM);
        panel.add(cancelButton);
        panel.add(okButton);
        return panel;
    }

    private void cancelClicked() {
        confirmed = false;
        dispose();
    }

    private void okClicked() {
        if (nameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Beer name cannot be left empty.",
                    "No name", JOptionPane.INFORMATION_MESSAGE);
            return ;
        }
        if (selectedBrewery == null) {
            JOptionPane.showMessageDialog(this, "You must select a brewery.",
                    "No name", JOptionPane.INFORMATION_MESSAGE);
            return ;
        }
        
        confirmed = true;
        dispose();
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    private void selectBreweryClicked() {
        SelectBreweryDialog selectBreweryDialog = new SelectBreweryDialog(
                RunningApplication.MainFrame);
        selectBreweryDialog.setLocationRelativeTo(this);
        selectBreweryDialog.setVisible(true);
        Brewery br = selectBreweryDialog.getSelectedBrewery();
        if (br != null) {
            selectedBrewery = br;
            breweryField.setText(selectedBrewery.getName());
        }
    }


    public void fill(Beer newBeer) {
        newBeer.setBreweryId(selectedBrewery.getId());
        newBeer.setAvailable(available.isSelected());
        newBeer.setNovelty(novelty.isSelected());
        newBeer.setName(nameField.getText().trim());
        newBeer.setDeclaredStyle(styleField.getText());
        
        if (priceKnown.isSelected())
            newBeer.setPrice(new FixedPointReal((Double) priceSpinner.getValue(), 2));
        else
            newBeer.setPrice(null);
        
        if (blgKnown.isSelected())
            newBeer.setPlato(new FixedPointReal((Double) blgSpinner.getValue(), 1));
        else
            newBeer.setPlato(null);
        
        if (abvKnown.isSelected())
            newBeer.setAbv(new FixedPointReal((Double) abvSpinner.getValue(), 1));
        else
            newBeer.setAbv(null);
        
        if (ibuKnown.isSelected())
            newBeer.setIbu(new FixedPointReal(Math.round((Double) ibuSpinner.getValue()), 0));
        else
            newBeer.setIbu(null);
        
        newBeer.addStyleKeywords(keywordsPanel.getSelectedKeywords());
        newBeer.setDescritpion(description.getText());
        newBeer.setLabelImage(labelImageSelector.getImage());
    }
    
    private void setDataFrom(Beer beer) {
        nameField.setText(beer.getName());
        selectedBrewery = RunningApplication.getBeerKnowledge().getBreweryOfBeer(beer);
        breweryField.setText(selectedBrewery != null ? selectedBrewery.getName() : "?");
        styleField.setText(beer.getDeclaredStyle());
        description.setText("" + beer.getDescritpion(RunningApplication.getFileSystem()));
        Box<BufferedImage> beerImage = beer.getLabelImage(RunningApplication.getFileSystem());
        if (!beerImage.isEmpty())
            labelImageSelector.setInitialImage(beerImage.getValue());
        available.setSelected(beer.isAvailable());
        keywordsPanel.setSelectedKeywords(beer.getStyleKeywords());
        novelty.setSelected(beer.isNovelty());
        
        setComponentFromBox(beer.getPlato(), blgKnown, blgSpinner);
        setComponentFromBox(beer.getAbv(), abvKnown, abvSpinner);
        setComponentFromBox(beer.getIbu(), ibuKnown, ibuSpinner);
        setComponentFromBox(beer.getPrice(), priceKnown, priceSpinner);     
    }
    
    private void setDataFrom(BeerProperties props) {
        nameField.setText(props.name);
        selectedBrewery = RunningApplication.getBeerKnowledge().getBreweryByName(props.breweryName);
        breweryField.setText(selectedBrewery != null ? selectedBrewery.getName() : "?");
        styleField.setText(props.declaredStyle);
        description.setText(props.descritpion);
        labelImageSelector.setInitialImage(props.labelImage);
        
        setComponentFromValue(props.plato, blgKnown, blgSpinner);
        setComponentFromValue(props.abv, abvKnown, abvSpinner);
        setComponentFromValue(props.ibu, ibuKnown, ibuSpinner);
        
        autoselectKeywords(props.declaredStyle);
    }
    
    private void setComponentFromBox(Box<FixedPointReal> box, JCheckBox isKnown, JSpinner spinner) {
        setComponentFromValue(box.isEmpty() ? null : box.getValue(), isKnown, spinner);
    }
    private void setComponentFromValue(FixedPointReal value, JCheckBox isKnown, JSpinner spinner) {
        isKnown.setSelected(value != null);
        spinner.setEnabled(value != null);
        if (value != null)
            spinner.setValue(value.getDoubleValue());
    }

    private void autoselectKeywords(String declaredStyle) {
        Collection<StyleKeyword> keywords = styleFinder.findStyleKeywords(declaredStyle);
        keywordsPanel.setSelectedKeywords(keywords);
    }

    private void autoFillClicked() {
        String address = JOptionPane.showInputDialog(rootPane,
                "Paste address of the webpage storing beer's data",
                "Input a web address", JOptionPane.QUESTION_MESSAGE);
        if (address != null) {
            new HeavyOperation("Fetching data from external source") {
                @Override
                protected void timeConsumingTask() {
                    BeerProperties props = external.readFrom(address);
                    handleFetchedProperties(props);
                }
                
            }.execute();
        }
    }

    private void handleFetchedProperties(BeerProperties props) {
        if (props == null) {
            JOptionPane.showMessageDialog(rootPane, "Provided address was invalid",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return ;
        }
        setDataFrom(props);
    }
    
    private void setFontSizes() {
        Font fieldFont = nameField.getFont().deriveFont(getSize().height / 25.0f);
        Font descFont = description.getFont().deriveFont(getSize().height / 35.0f);
        nameField.setFont(fieldFont);
        breweryField.setFont(fieldFont);
        description.setFont(descFont);
    }
}
