
package beershowcase;

import beershowcase.beerdata.Beer;
import beershowcase.beerdata.BeerKnowledge;
import beershowcase.beerdata.filters.Filter;
import java.awt.BorderLayout;
import java.util.ArrayList;

/**
 *
 * @author Grzegorz Łoś
 */
public class BeerShowcasePane extends javax.swing.JPanel {
    private final ParamSelectionPanel paramSelectionPanel;
    private final BeerBrowserPanel beerBrowserPanel;
    
    /**
     * Creates new form BeerShowcasePane
     */
    public BeerShowcasePane() {
        initComponents();
        container.setLayout(new BorderLayout());
        paramSelectionPanel = new ParamSelectionPanel(this);
        beerBrowserPanel = new BeerBrowserPanel();
        backButton.setVisible(false);
        container.add(paramSelectionPanel);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        backButton = new javax.swing.JButton();
        container = new javax.swing.JPanel();

        backButton.setText("<html>Go back to criteria");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout containerLayout = new javax.swing.GroupLayout(container);
        container.setLayout(containerLayout);
        containerLayout.setHorizontalGroup(
            containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        containerLayout.setVerticalGroup(
            containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 254, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 287, Short.MAX_VALUE)
                .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(container, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(container, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        goToSelectionMode();
    }//GEN-LAST:event_backButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backButton;
    private javax.swing.JPanel container;
    // End of variables declaration//GEN-END:variables
    
    void goToBrowsingMode() {
        backButton.setVisible(true);
        container.removeAll();
        
        Filter filter = paramSelectionPanel.buildFilter();
        ArrayList<Beer> beers = RunningApplication.data.beerKnowledge.getBeers(filter);
        beerBrowserPanel.setDisplayedBeers(beers);
        
        container.add(beerBrowserPanel);
        repaint();
        revalidate();
    }
    
    void goToSelectionMode() {
        backButton.setVisible(false);
        container.removeAll();
        container.add(paramSelectionPanel);
        repaint();
        revalidate();
    }

}
