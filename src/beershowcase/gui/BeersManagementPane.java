
package beershowcase.gui;

import beershowcase.beerdata.Beer;
import beershowcase.beerdata.filters.Filter;
import beershowcase.beerdata.filters.NoFilter;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Grzegorz Łoś
 */
public class BeersManagementPane extends JPanel {
    Filter filter = new NoFilter();
    private BeerBrowserPanel beerBrowserPanel;
    private JPanel beersViewContainer;
    private ActiveDisplayMode displayMode = ActiveDisplayMode.Browser;
    
    private static final Logger LOGGER = Logger.getLogger(BeersManagementPane.class.getName());
    
    /**
     * Creates new form BeersManagementPane
     */
    public BeersManagementPane() {
        initComponents();
        //RunningApplication.beerKnowledge.addChangeListener(this);
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        add(createTop(), BorderLayout.NORTH);
        add(createCenter(), BorderLayout.CENTER);
    }
    
    private Component createTop() {
        JPanel panel = new JPanel();
        BoxLayout bl = new BoxLayout(panel, BoxLayout.X_AXIS);
        panel.setLayout(bl);
        
        JButton add = new JButton("Add");
        add.addActionListener((ActionEvent e) -> addBeerClicked());
        panel.add(add);
        
        JButton edit = new JButton("Edit");
        edit.addActionListener((ActionEvent e) -> editBeerClicked());
        panel.add(edit);
        
        JButton delete = new JButton("Delete");
        delete.addActionListener((ActionEvent e) -> removeClicked());
        panel.add(delete);
        
        //panel.add(new javax.swing.Box.Filler(new Dimension(0,0), new Dimension(1000,50), new Dimension(1000,1000)));
        panel.add(javax.swing.Box.createHorizontalGlue());
        
        JButton toggle = new JButton("Toggle view mode");
        toggle.addActionListener((ActionEvent e) -> toggleClicked());
        panel.add(toggle);
        
        return panel;
    }
    
    private Component createCenter() {
        beersViewContainer = new JPanel();
        beerBrowserPanel = new BeerBrowserPanel();
        beersViewContainer.setLayout(new BorderLayout());
        beersViewContainer.add(beerBrowserPanel);
        return beersViewContainer;
    }
    
    private void addBeerClicked() {
        EditBeerDialog editBeerDialog = new EditBeerDialog(RunningApplication.MainFrame);
        editBeerDialog.setLocationRelativeTo(this);
        editBeerDialog.setVisible(true);
        if (editBeerDialog.isConfirmed()) {
            Beer beer = RunningApplication.getBeerKnowledge().makeBeer();
            editBeerDialog.fill(beer);
            if (filter.filter(beer)) {
                addBeerToComponents(beer);
                beerBrowserPanel.showBeer(beer);
                revalidate();
                repaint();
            }
        }
    }
    
    void reset() {
        //RunningApplication.beerKnowledge.addChangeListener(this);
        addAllBeersToComponents();
        repaint();
        revalidate();
    }
    
    /*@Override
    public void knowledgeChanged(BeerKnowledge.ChangeEvent event) {
        if (!(event.affectedObject instanceof Beer))
            return;
        Beer beer = (Beer) event.affectedObject;
        
        switch (event.changeType) {
            case Addition:
                if (filter.filter(beer))
                    addBeerToComponents(beer);
                break;
            case Removal:
                if (filter.filter(beer))
                    removeBeerFromComponents(beer);
                break;
            default:
                LOGGER.log(Level.WARNING, "Not handling event of type " + event.changeType);
        }
    }*/

    private void addAllBeersToComponents() {
        ArrayList<Beer> beers = RunningApplication.getBeerKnowledge().getBeers();
        beerBrowserPanel.setDisplayedBeers(beers);
    }

    private void addBeerToComponents(Beer beer) {
        beerBrowserPanel.addBeer(beer);
    }

    private void removeBeerFromComponents(Beer beer) {
        ArrayList<Beer> beers = RunningApplication.getBeerKnowledge().getBeers(filter);
        beerBrowserPanel.setDisplayedBeers(beers);
    }
    
    private void editBeerClicked() {
        Beer beer = getSelectedBeer();
        if (beer == null) {
            JOptionPane.showMessageDialog(RunningApplication.MainFrame,
                    "Please select a beer first.");
        } else {
            openEditDialog(beer);
        }
    }
    
    private Beer getSelectedBeer() {
        if (displayMode == ActiveDisplayMode.Browser) {
            return beerBrowserPanel.getCurrBeer();
        } else {
            return null;
        }
    }    

    private void openEditDialog(Beer beer) {
        EditBeerDialog editBeerDialog = new EditBeerDialog(RunningApplication.MainFrame, beer);
        editBeerDialog.setLocationRelativeTo(this);
        editBeerDialog.setVisible(true);
        if (editBeerDialog.isConfirmed()) {
            editBeerDialog.fill(beer);
        }
    }

    private void removeClicked() {
        Beer beer = getSelectedBeer();
        if (beer == null) {
            JOptionPane.showMessageDialog(RunningApplication.MainFrame,
                    "Please select a beer first.");
        } else {
            String question = new StringBuilder()
                    .append("Are you sure you want to delete \"")
                    .append(beer.getName())
                    .append("\"?\n\nRemember you can mark this beer as not available instead.")
                    .toString();
            int res = JOptionPane.showConfirmDialog(RunningApplication.MainFrame,
                    question, "Confirm", JOptionPane.YES_NO_OPTION);
            if (res == JOptionPane.YES_OPTION) {
                RunningApplication.getBeerKnowledge().deleteBeer(beer);
                removeBeerFromComponents(beer);
            }
        }
    }
    
    private void toggleClicked() {
        
    }
    
    private enum ActiveDisplayMode {
        Browser, Table
    }
}
