
package beershowcase.gui;

import beershowcase.beerdata.Beer;
import beershowcase.gui.components.AutoLabel;
import beershowcase.gui.components.RelativeLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Grzegorz Łoś
 */
public class BeerBrowserPanel extends JPanel implements Beer.ChangeListener {
    private ArrayList<Beer> displayed = new ArrayList<>();
    private final HashMap<Integer, BeerViewPanel> beerViews = new HashMap<>();
    
    private int index = 0;
    private final JButton nextButton = new JButton("Next");
    private final JButton prevButton = new JButton("Prev");
    private final JPanel beerViewContainer = new JPanel();
    private JPanel noResultsPanel;
    
    private static final Logger LOGGER = Logger.getLogger(BeerBrowserPanel.class.getName());
    
    /**
     * Creates new form BeerBrowserPanel
     */
    public BeerBrowserPanel() {
        initComponents();
        setButtonAvailability();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        beerViewContainer.setLayout(new BorderLayout());
        add(beerViewContainer, BorderLayout.CENTER);
        add(makeButtonsEnvelope(nextButton), BorderLayout.EAST);
        add(makeButtonsEnvelope(prevButton), BorderLayout.WEST);
        nextButton.addActionListener((ActionEvent e) -> nextButtonActionPerformed(e));
        prevButton.addActionListener((ActionEvent e) -> prevButtonActionPerformed(e));
        
        initNoResultsPanel();
    }
    
    private JPanel makeButtonsEnvelope(JButton button) {
        RelativeLayout rl = new RelativeLayout(RelativeLayout.Y_AXIS);
        rl.setFill(true);
        JPanel panel = new JPanel(rl);
        panel.add(new JPanel(), new Float(45));
        panel.add(button, new Float(10));
        panel.add(new JPanel(), new Float(45));
        return panel;
    }
    
    private void nextButtonActionPerformed(ActionEvent evt) {                                           
        if (index < displayed.size() - 1)
            index++;
        setBeerView();
        setButtonAvailability();
    }                                          

    private void prevButtonActionPerformed(ActionEvent evt) {                                           
        if (index > 0)
            index--;
        setBeerView();
        setButtonAvailability();
    }
    
    public void setDisplayedBeers(ArrayList<Beer> beers) {
        clean();
        displayed = beers;
        index = 0;
        buildBeerViews();
        setButtonAvailability();
        setBeerView();
    }
    
    private void clean() {
        for (Beer b: displayed)
            b.removeChangeListener(this);
        displayed.clear();
        beerViews.clear();
    }
    
    private void buildBeerViews() {
        for (Beer beer: displayed) {
            buildBeerView(beer);
        }
    }
    
    private void buildBeerView(Beer beer) {
        BeerViewPanel beerViewPanel = new BeerViewPanel(
                beer, RunningApplication.getBeerKnowledge().getBreweryOfBeer(beer));
        beerViews.put(beer.getId(), beerViewPanel);      
        beer.addChangeListener(this);
    }
    
    private void setBeerView() {
        beerViewContainer.removeAll();
        if (displayed.isEmpty()) {
            beerViewContainer.add(noResultsPanel);
        } else {
            Beer beer = displayed.get(index);
            BeerViewPanel beerViewPanel = beerViews.get(beer.getId());
            beerViewContainer.add(beerViewPanel);
        }        
        revalidate();
        repaint();
    }

    private void setButtonAvailability() {
        nextButton.setEnabled(index < displayed.size() - 1);
        prevButton.setEnabled(index > 0);            
    }


    @Override
    public void beerEdited(Beer.EditionEvent event) {
        Beer beer = event.source;
        buildBeerView(beer);
        setBeerView();
    }

    void addBeer(Beer beer) {
        displayed.add(beer);
        buildBeerView(beer);
    }

    void showBeer(Beer beer) {
        for (int i = 0; i < displayed.size(); ++i) {
            if (displayed.get(i) == beer) {
                index = i;
                setBeerView();
                setButtonAvailability();
                break;
            }
        }
    }

    public Beer getCurrBeer() {
        if (displayed.isEmpty())
            return null;
        else
            return displayed.get(index);
    }

    private void initNoResultsPanel() {
        RelativeLayout rl = new RelativeLayout(RelativeLayout.Y_AXIS);
        rl.setFill(true);
        noResultsPanel = new JPanel(rl);
        noResultsPanel.add(new JPanel(), new Float(45));
        noResultsPanel.add(new AutoLabel("No beers met selected criteria", JLabel.CENTER), new Float(10));
        noResultsPanel.add(new JPanel(), new Float(45));
        
    }
}
