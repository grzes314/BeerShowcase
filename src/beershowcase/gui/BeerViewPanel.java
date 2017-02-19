
package beershowcase.gui;

import beershowcase.beerdata.Beer;
import beershowcase.beerdata.Brewery;
import beershowcase.gui.components.AutoLabel;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * @author Grzegorz Łoś
 */
public class BeerViewPanel extends JPanel {

    private final Beer beer;
    private final Brewery brewery;

    public BeerViewPanel(Beer beer, Brewery brewery) {
        this.beer = beer;
        this.brewery = brewery;
        
        setLayout(new GridBagLayout());
        addComp(this, makeColumn1());
        addComp(this, makeColumn2());
        addComp(this, makeColumn3());
    }
    
    private void addComp(JPanel panel, Pair p) {
        panel.add(p.comp, p.gbc);
    }

    private Pair makeColumn1() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        
        addComp(panel, makeNameLabel());
        addComp(panel, makeStyleLabel());
        addComp(panel, makeImagePanel());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.weightx = 0.4;
        gbc.weighty = 1.0;
        
        return new Pair(panel, gbc);
    }

    private Pair makeColumn2() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        
        addComp(panel, makePriceLabel());
        addComp(panel, makeParamsPanel());
        addComp(panel, makeDescPanel());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.weightx = 0.3;
        gbc.weighty = 1.0;
        
        return new Pair(panel, gbc);
    }

    private Pair makeColumn3() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        
        addComp(panel, makeBreweryNamePanel());
        addComp(panel, makeBreweryImagePanel());
        addComp(panel, makeTagsPanel());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.weightx = 0.3;
        gbc.weighty = 1.0;
        
        return new Pair(panel, gbc);
    }
    
    private Pair makeNameLabel() {
        AutoLabel label = new AutoLabel(beer.getName());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.weightx = 1.0;
        gbc.weighty = 0.07;
        
        return new Pair(label, gbc);
    }

    private Pair makeStyleLabel() {
        AutoLabel label = new AutoLabel(beer.getDeclaredStyle());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.weightx = 1.0;
        gbc.weighty = 0.05;
        
        return new Pair(label, gbc);
    }

    private Pair makeImagePanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Tu bedzie obrazek"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.weightx = 1.0;
        gbc.weighty = 0.88;
        
        return new Pair(panel, gbc);
    }

    private Pair makePriceLabel() {
        AutoLabel label = new AutoLabel("Price: " + beer.getPrice());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.weightx = 1.0;
        gbc.weighty = 0.05;
        
        return new Pair(label, gbc);
    }
    
    private Pair makeParamsPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.setBorder(BorderFactory.createEtchedBorder());
        
        panel.add(new AutoLabel("Gravity:", JLabel.RIGHT));
        panel.add(new AutoLabel(beer.getPlato().toString() + "\u00B0Blg", JLabel.LEFT));
        
        panel.add(new AutoLabel("Alc. vol.:", JLabel.RIGHT));
        panel.add(new AutoLabel(beer.getAbv().toString() + "%", JLabel.LEFT));
        
        panel.add(new AutoLabel("IBU:", JLabel.RIGHT));
        panel.add(new AutoLabel("" + beer.getIbu(), JLabel.LEFT));
        
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.weightx = 1.0;
        gbc.weighty = 0.15;
        
        return new Pair(panel, gbc);
    }

    private Pair makeDescPanel() {
        JTextArea area = new JTextArea();
        area.setText(beer.getDescritpion(RunningApplication.getFileSystem()));
        area.setLineWrap(true);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.weightx = 1.0;
        gbc.weighty = 0.8;
        
        return new Pair(area, gbc);
    }

    private Pair makeBreweryNamePanel() {
        AutoLabel label = new AutoLabel(brewery.getName());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.weightx = 1.0;
        gbc.weighty = 0.05;
        
        return new Pair(label, gbc);
    }

    private Pair makeBreweryImagePanel() {
        JPanel panel = new JPanel();
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.weightx = 1.0;
        gbc.weighty = 0.35;
        
        return new Pair(panel, gbc);
    }

    private Pair makeTagsPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Tags"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.weightx = 1.0;
        gbc.weighty = 0.6;
        
        return new Pair(panel, gbc);
    }
    
    private class Pair {
        Component comp;
        GridBagConstraints gbc;

        public Pair(Component comp, GridBagConstraints gbc) {
            this.comp = comp;
            this.gbc = gbc;
        }
    }
}
