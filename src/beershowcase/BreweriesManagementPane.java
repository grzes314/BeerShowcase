
package beershowcase;

import beershowcase.beerdata.BeerKnowledge;
import beershowcase.beerdata.Brewery;
import java.awt.BorderLayout;
import javax.swing.JOptionPane;

/**
 *
 * @author grzes
 */
public class BreweriesManagementPane extends javax.swing.JPanel {

    BeerKnowledge beerKnowledge;
    BreweriesTable breweriesTable;
    
    public BreweriesManagementPane(BeerKnowledge beerKnowledge) {
        initComponents();
        this.beerKnowledge = beerKnowledge;
        breweriesTable = new BreweriesTable(beerKnowledge);
        tableContainer.setLayout(new BorderLayout());
        tableContainer.add(breweriesTable);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        addButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        editButton = new javax.swing.JButton();
        tableContainer = new javax.swing.JPanel();

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        addButton.setText("Add brewery");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        deleteButton.setText("Delete");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        editButton.setText("Edit");
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editButton, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(96, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(deleteButton, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
            .addComponent(addButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(editButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        tableContainer.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout tableContainerLayout = new javax.swing.GroupLayout(tableContainer);
        tableContainer.setLayout(tableContainerLayout);
        tableContainerLayout.setHorizontalGroup(
            tableContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        tableContainerLayout.setVerticalGroup(
            tableContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 365, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tableContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tableContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        addBreweryClicked();
    }//GEN-LAST:event_addButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        deleteClicked();
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButtonActionPerformed
        editClicked();
    }//GEN-LAST:event_editButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JButton editButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel tableContainer;
    // End of variables declaration//GEN-END:variables


    private void addBreweryClicked() {
        AddBreweryDialog addBreweryDialog = new AddBreweryDialog(null);
        addBreweryDialog.setVisible(true);
        if (addBreweryDialog.isApproved()) {
            Brewery brewery = beerKnowledge.makeBrewery();
            brewery.setName(addBreweryDialog.getBreweryName());
            brewery.setLogo(addBreweryDialog.getLogo());
            breweriesTable.breweryAdded();
        }
    }

    void reset(BeerKnowledge beerKnowledge) {
        this.beerKnowledge = beerKnowledge;
        breweriesTable.reset(beerKnowledge);
        repaint();
        revalidate();
    }

    private void deleteClicked() {
        Brewery brewery = breweriesTable.getSelectedItem();
        if (brewery != null) {
            int res = JOptionPane.showOptionDialog(
                this, "Are you sure you want to delete \"" + brewery.getName() + "\"?", 
                "Confirm deletion", JOptionPane.YES_NO_OPTION, 
                JOptionPane.QUESTION_MESSAGE, null, null, null);
            if (res == JOptionPane.YES_OPTION) {
                int count = beerKnowledge.getBeersOf(brewery).size();
                if (count > 0) {
                    JOptionPane.showMessageDialog(this, "Cannot delete brewery, because related beers exist",
                            "Operation failed", JOptionPane.WARNING_MESSAGE);
                } else {
                    beerKnowledge.deleteBrewery(brewery);
                    breweriesTable.notifyDataChanged();
                }
            }
        }
    }

    private void editClicked() {
        Brewery brewery = breweriesTable.getSelectedItem();
        if (brewery != null) {
            AddBreweryDialog dialog = new AddBreweryDialog(null, brewery);
            dialog.setVisible(true);
            if (dialog.isApproved()) {
                brewery.setName(dialog.getBreweryName());
                brewery.setLogo(dialog.getLogo());
                breweriesTable.notifyDataChanged();
            }
        }
    }
}