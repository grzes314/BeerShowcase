
package beershowcase.gui;

import beershowcase.beerdata.Beer;
import beershowcase.beerdata.BeerProperties;
import beershowcase.beerdata.Brewery;
import beershowcase.utils.FixedPointReal;
import beershowcase.beerdata.StyleKeyword;
import beershowcase.beerdata.autostyle.StyleFinder;
import beershowcase.beerdata.autostyle.simple.SimpleStyleFinder;
import beershowcase.external.ExternalSource;
import beershowcase.external.ExternalSourceDispatcher;
import java.awt.BorderLayout;
import java.util.Collection;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SpinnerNumberModel;

/**
 *
 * @author Grzegorz Łoś
 */
public class EditBeerDialog extends javax.swing.JDialog {
    private final AllKeywordsPanel keywordsPanel = new AllKeywordsPanel();
    private final SelectImagePanel labelImageSelector = new SelectImagePanel();
    private boolean confirmed;
    private Brewery selectedBrewery;
    private final StyleFinder styleFinder = new SimpleStyleFinder();
    private final ExternalSource external = new ExternalSourceDispatcher();
    
    /**
     * Creates new form EditBeerDialog
     */
    public EditBeerDialog(java.awt.Frame parent) {
        super(parent, true);
        initComponents();
        
        setTitle("Add a new beer");
        keywordsContainer.setLayout(new BorderLayout());
        keywordsContainer.add(new JScrollPane(keywordsPanel));
        labelSelectorContainer.setLayout(new BorderLayout());
        labelSelectorContainer.add(labelImageSelector);
    }

    EditBeerDialog(java.awt.Frame parent, Beer beer) {
        super(parent, true);
        initComponents();
        
        setTitle("Edit a beer");
        keywordsContainer.setLayout(new BorderLayout());
        keywordsContainer.add(new JScrollPane(keywordsPanel));
        labelSelectorContainer.setLayout(new BorderLayout());
        labelSelectorContainer.add(labelImageSelector);
        
        setDataFrom(beer);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabbedPane = new javax.swing.JTabbedPane();
        mainPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        nameField = new javax.swing.JTextField();
        breweryField = new javax.swing.JTextField();
        selectBreweryButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        description = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        styleField = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        availableBox = new javax.swing.JCheckBox();
        jLabel6 = new javax.swing.JLabel();
        priceSpinner = new javax.swing.JSpinner();
        jButton1 = new javax.swing.JButton();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        blgSpinner = new javax.swing.JSpinner();
        abvSpinner = new javax.swing.JSpinner();
        ibuSpinner = new javax.swing.JSpinner();
        keywordsContainer = new javax.swing.JPanel();
        imagesPanel = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        labelSelectorContainer = new javax.swing.JPanel();
        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jLabel1.setText("Beer basic information");

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel2.setLabelFor(nameField);
        jLabel2.setText("Name:");

        jLabel3.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel3.setText("Brewery:");

        breweryField.setEditable(false);
        breweryField.setText("Select brewery by clicking a button on the right...");

        selectBreweryButton.setText("Select");
        selectBreweryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectBreweryButtonActionPerformed(evt);
            }
        });

        description.setColumns(20);
        description.setRows(5);
        jScrollPane1.setViewportView(description);

        jLabel4.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel4.setText("Description:");

        jLabel8.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel8.setText("Declared style:");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Store"));

        jLabel5.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel5.setText("Available:");

        availableBox.setSelected(true);

        jLabel6.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel6.setText("Price:");

        priceSpinner.setModel(new SpinnerNumberModel(0.0, 0.0, 1000.0, 0.1));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(priceSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(availableBox)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(availableBox, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(priceSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 12, Short.MAX_VALUE))
        );

        jButton1.setText("Auto fill");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel9.setText("Alc. vol.:");

        jLabel10.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel10.setText("Blg:");

        jLabel11.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel11.setText("IBU:");

        blgSpinner.setModel(new SpinnerNumberModel(0.0, 0.0, 100.0, 0.1));

        abvSpinner.setModel(new SpinnerNumberModel(0.0, 0.0, 100.0, 0.1));

        ibuSpinner.setModel(new javax.swing.SpinnerNumberModel(0, 0, 200, 1));

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(filler1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(24, 24, 24)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(breweryField, javax.swing.GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(selectBreweryButton))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(styleField)
                            .addComponent(nameField)))))
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(abvSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(blgSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ibuSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(nameField, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(styleField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(breweryField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(selectBreweryButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ibuSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(abvSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(blgSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51))
        );

        tabbedPane.addTab("Main", mainPanel);

        javax.swing.GroupLayout keywordsContainerLayout = new javax.swing.GroupLayout(keywordsContainer);
        keywordsContainer.setLayout(keywordsContainerLayout);
        keywordsContainerLayout.setHorizontalGroup(
            keywordsContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 648, Short.MAX_VALUE)
        );
        keywordsContainerLayout.setVerticalGroup(
            keywordsContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 450, Short.MAX_VALUE)
        );

        tabbedPane.addTab("Tags", keywordsContainer);

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 15)); // NOI18N
        jLabel7.setText("Select label image");

        labelSelectorContainer.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout labelSelectorContainerLayout = new javax.swing.GroupLayout(labelSelectorContainer);
        labelSelectorContainer.setLayout(labelSelectorContainerLayout);
        labelSelectorContainerLayout.setHorizontalGroup(
            labelSelectorContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 644, Short.MAX_VALUE)
        );
        labelSelectorContainerLayout.setVerticalGroup(
            labelSelectorContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 446, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout imagesPanelLayout = new javax.swing.GroupLayout(imagesPanel);
        imagesPanel.setLayout(imagesPanelLayout);
        imagesPanelLayout.setHorizontalGroup(
            imagesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(imagesPanelLayout.createSequentialGroup()
                .addComponent(jLabel7)
                .addGap(0, 499, Short.MAX_VALUE))
            .addGroup(imagesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(labelSelectorContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        imagesPanelLayout.setVerticalGroup(
            imagesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(imagesPanelLayout.createSequentialGroup()
                .addComponent(jLabel7)
                .addGap(0, 432, Short.MAX_VALUE))
            .addGroup(imagesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(labelSelectorContainer, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabbedPane.addTab("Picture", imagesPanel);

        okButton.setText("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cancelButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(okButton)
                .addContainerGap())
            .addComponent(tabbedPane)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(tabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 477, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(okButton)
                    .addComponent(cancelButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        cancelClicked();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        okClicked();
    }//GEN-LAST:event_okButtonActionPerformed

    private void selectBreweryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectBreweryButtonActionPerformed
        selectBrewerClicked();
    }//GEN-LAST:event_selectBreweryButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        autoFill();
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSpinner abvSpinner;
    private javax.swing.JCheckBox availableBox;
    private javax.swing.JSpinner blgSpinner;
    private javax.swing.JTextField breweryField;
    private javax.swing.JButton cancelButton;
    private javax.swing.JTextArea description;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JSpinner ibuSpinner;
    private javax.swing.JPanel imagesPanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel keywordsContainer;
    private javax.swing.JPanel labelSelectorContainer;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JTextField nameField;
    private javax.swing.JButton okButton;
    private javax.swing.JSpinner priceSpinner;
    private javax.swing.JButton selectBreweryButton;
    private javax.swing.JTextField styleField;
    private javax.swing.JTabbedPane tabbedPane;
    // End of variables declaration//GEN-END:variables

    private void cancelClicked() {
        confirmed = false;
        dispose();
    }

    public void fill(Beer newBeer) {
        newBeer.setBreweryId(selectedBrewery.getId());
        newBeer.setAvailable(availableBox.isSelected());
        newBeer.setPrice(new FixedPointReal((Double) priceSpinner.getValue(), 2));
        newBeer.setName(nameField.getText().trim());
        newBeer.setDeclaredStyle(styleField.getText());
        newBeer.setPlato(new FixedPointReal((Double) blgSpinner.getValue(), 1));
        newBeer.setAbv(new FixedPointReal((Double) abvSpinner.getValue(), 1));
        newBeer.setIbu((Integer) ibuSpinner.getValue());
        newBeer.addStyleKeywords(keywordsPanel.getSelectedKeywords());
        newBeer.setDescritpion(description.getText());
        newBeer.setLabelImage(labelImageSelector.getImage());
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

    private void selectBrewerClicked() {
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

    private void setDataFrom(Beer beer) {
        nameField.setText(beer.getName());
        selectedBrewery = RunningApplication.getBeerKnowledge().getBreweryOfBeer(beer);
        breweryField.setText(selectedBrewery != null ? selectedBrewery.getName() : "?");
        styleField.setText(beer.getDeclaredStyle());
        blgSpinner.setValue(beer.getPlato().getDoubleValue());
        abvSpinner.setValue(beer.getAbv().getDoubleValue());
        ibuSpinner.setValue(beer.getIbu());
        description.setText(beer.getDescritpion(RunningApplication.getFileSystem()));
        labelImageSelector.setInitialImage(beer.getLabelImage(RunningApplication.getFileSystem())); 
        
        priceSpinner.setValue(beer.getPrice().getDoubleValue());
        availableBox.setSelected(beer.isAvailable());
        keywordsPanel.setSelectedKeywords(beer.getStyleKeywords());       
    }
    
    private void setDataFrom(BeerProperties props) {
        nameField.setText(props.name);
        selectedBrewery = RunningApplication.getBeerKnowledge().getBreweryByName(props.breweryName);
        breweryField.setText(selectedBrewery != null ? selectedBrewery.getName() : "?");
        styleField.setText(props.declaredStyle);
        description.setText(props.descritpion);
        labelImageSelector.setInitialImage(props.labelImage);
        blgSpinner.setValue(props.plato.getDoubleValue());
        abvSpinner.setValue(props.abv.getDoubleValue());
        ibuSpinner.setValue(props.ibu);
        
        autoselectKeywords(props.declaredStyle);
    }

    private void autoselectKeywords(String declaredStyle) {
        Collection<StyleKeyword> keywords = styleFinder.findStyleKeywords(declaredStyle);
        keywordsPanel.setSelectedKeywords(keywords);
    }

    private void autoFill() {
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
}
