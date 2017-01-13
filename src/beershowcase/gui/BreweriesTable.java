
package beershowcase.gui;

import beershowcase.beerdata.BeerKnowledge;
import beershowcase.beerdata.Brewery;
import java.awt.Component;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author grzes
 */
public class BreweriesTable extends javax.swing.JPanel {
    BreweriesTableModel tableModel;
    
    final int MIN_ROW_HEIGHT = 30;

    public BreweriesTable() {
        tableModel = new BreweriesTableModel();
        initComponents();
        table.setAutoCreateRowSorter(true);
        table.setDefaultRenderer(Image.class, new ImageCellRenderer());
        table.setRowHeight(100);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        searchField = new javax.swing.JTextField();
        searchButton = new javax.swing.JButton();

        table.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        table.setModel(tableModel);
        table.setRequestFocusEnabled(false);
        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(table);

        searchField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchFieldActionPerformed(evt);
            }
        });

        searchButton.setText("Search");
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchButton)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        searchTriggered();
    }//GEN-LAST:event_searchButtonActionPerformed

    private void searchFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchFieldActionPerformed
        searchTriggered();
    }//GEN-LAST:event_searchFieldActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton searchButton;
    private javax.swing.JTextField searchField;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables

    /**
     * Notify BreweriesTable that BeerKnowledge object has been changed.
     */
    public void reset() {
        searchField.setText("");
        tableModel.reset();
        repaint();
        revalidate();
    }

    public Brewery getSelectedItem() {
        int r = table.getSelectedRow();
        if (r < 0)
            return null;
        r = table.convertRowIndexToModel(r);
        BreweriesTableModel model = (BreweriesTableModel) table.getModel();
        return model.getBreweryAt(r);
    }

    private void searchTriggered() {
        String namePart = searchField.getText();
        new HeavyOperation("Selecting breweries") {
            @Override
            protected void timeConsumingTask() {
                tableModel.setRequieredNamePart(namePart);
            }
        }.execute();        
    }
}

class BreweriesTableModel extends AbstractTableModel
        implements BeerKnowledge.ChangeListener, Brewery.ChangeListener {

    ArrayList<Brewery> displayed = new ArrayList<>();
    String namePart = "";

    BreweriesTableModel() {
        RunningApplication.data.beerKnowledge.addChangeListener(this);
        displayed.addAll(RunningApplication.data.beerKnowledge.getBreweries());
    }
    
    public void reset() {
        displayed.clear();
        
        RunningApplication.data.beerKnowledge.addChangeListener(this);
        displayed.addAll(RunningApplication.data.beerKnowledge.getBreweries());
        fireTableDataChanged();
    }
    
    public void setRequieredNamePart(String namePart) {
        displayed.clear();
        this.namePart = namePart.toLowerCase();
        rebuildDisplayed();
        fireTableDataChanged();
    }
    
    private void rebuildDisplayed() {
        for (Brewery br: RunningApplication.data.beerKnowledge.getBreweries()) {
            if (isDisplayed(br))
                displayed.add(br);
        }
    }
    
    private boolean isDisplayed(Brewery br) {
        return br.getName().toLowerCase().contains(namePart);
    }
    
    @Override
    public int getRowCount() {
        return displayed.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }
    
    @Override
    public String getColumnName(int columnIndex) {
        switch(columnIndex) {
            case 0:
                return "Brewery name";
            case 1:
                return "Logo";
            default:
                throw new RuntimeException("Internal error");
        }
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch(columnIndex) {
            case 0:
                return String.class;
            case 1:
                return Image.class;
            default:
                throw new RuntimeException("Internal error");
        }
    }
    
    @Override
    public Object getValueAt(int row, int col) {
        Brewery b = displayed.get(row);
        Image logo = b.getLogo(RunningApplication.data.fileSystem);
        switch(col) {
            case 0:
                return b.getName();
            case 1:
                return logo;
            default:
                throw new RuntimeException("Internal error");
        }
    }
    
    public Brewery getBreweryAt(int row) {
        return displayed.get(row);
    }

    @Override
    public void knowledgeChanged(BeerKnowledge.ChangeEvent event) {
        if (!(event.affectedObject instanceof Brewery))
            return ;
        Brewery br = (Brewery) event.affectedObject;
        if (!isDisplayed(br))
            return;
        
        if (event.changeType == BeerKnowledge.ChangeType.Addition) {
            displayed.add(br);
            br.addChangeListener(this);
        }
        if (event.changeType == BeerKnowledge.ChangeType.Removal) {
            displayed.remove(br);
            br.removeChangeListener(this);
        }
        fireTableDataChanged();
    }

    @Override
    public void breweryEdited(Brewery.EditionEvent event) {
        if (isDisplayed(event.source))
            fireTableDataChanged();
    }
    
}

final class ImageCellRenderer extends ImagePanel implements TableCellRenderer {    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        Image img = (Image) value;
        setImage(img);
        return this;
    }
    
}