
package beershowcase;

import beershowcase.beerdata.BeerKnowledge;
import beershowcase.beerdata.Brewery;
import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author grzes
 */
public class BreweriesTable extends javax.swing.JPanel {

    BeerKnowledge beerKnowledge;
    
    final int MIN_ROW_HEIGHT = 30;

    public BreweriesTable(BeerKnowledge beerKnowledge) {
        this.beerKnowledge = beerKnowledge;
        initComponents();
        setRowHeights();
        table.setAutoCreateRowSorter(true);
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

        table.setModel(new BreweriesTableModel(beerKnowledge));
        table.setRequestFocusEnabled(false);
        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(table);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables


    public final void reset(BeerKnowledge beerKnowledge) {
        this.beerKnowledge = beerKnowledge;
        ((BreweriesTableModel) table.getModel()).setBeerKnowledge(beerKnowledge);
        setRowHeights();
        repaint();
        revalidate();
    }
    
    public void breweryAdded() {
        ((AbstractTableModel) table.getModel()).fireTableDataChanged();
        //setRowHeight( table.getModel().getRowCount() - 1);
        setRowHeights();
    }
    
    public void notifyDataChanged() {
        ((AbstractTableModel) table.getModel()).fireTableDataChanged();
        setRowHeights();
    }
    
    public void setRowHeight(int row) {
        BreweriesTableModel model = (BreweriesTableModel) table.getModel();
        int height = MIN_ROW_HEIGHT;
        Object ob = model.getValueAt(row, 1);
        if (ob != null) {
            height = Math.max(height, ((Icon) ob).getIconHeight());
        }
        table.setRowHeight(row, height);
    }
    
    private void setRowHeights() {
        BreweriesTableModel model = (BreweriesTableModel) table.getModel();
        int rowCount = model.getRowCount();
        for (int row = 0; row < rowCount; ++row) 
            setRowHeight(row);
    }

    public Brewery getSelectedItem() {
        int r = table.getSelectedRow();
        if (r < 0)
            return null;
        r = table.convertRowIndexToModel(r);
        BreweriesTableModel model = (BreweriesTableModel) table.getModel();
        return model.getBreweryAt(r);
    }
}

class BreweriesTableModel extends AbstractTableModel {

    BeerKnowledge beerKnowledge;

    public BreweriesTableModel(BeerKnowledge beerKnowledge) {
        this.beerKnowledge = beerKnowledge;
    }

    public BeerKnowledge getBeerKnowledge() {
        return beerKnowledge;
    }

    public void setBeerKnowledge(BeerKnowledge beerKnowledge) {
        this.beerKnowledge = beerKnowledge;
    }
    
    @Override
    public int getRowCount() {
        return beerKnowledge.getBreweries().size();
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
                return Icon.class;
            default:
                throw new RuntimeException("Internal error");
        }
    }
    
    @Override
    public Object getValueAt(int row, int col) {
        Brewery b = beerKnowledge.getBreweries().get(row);
        Image logo = b.getLogo();
        switch(col) {
            case 0:
                return b.getName();
            case 1:
                return logo == null ? null : new ImageIcon(b.getLogo());
            default:
                throw new RuntimeException("Internal error");
        }
    }
    
    public Brewery getBreweryAt(int row) {
        return beerKnowledge.getBreweries().get(row);
    }
    
}