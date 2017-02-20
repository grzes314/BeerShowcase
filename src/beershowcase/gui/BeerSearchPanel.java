
package beershowcase.gui;

import beershowcase.beerdata.filters.Filter;
import beershowcase.beerdata.filters.NoFilter;
import beershowcase.gui.components.AutoLabel;
import beershowcase.gui.components.OptionLabel;
import beershowcase.gui.components.RelativeLayout;
import beershowcase.utils.FixedPointReal;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Grzegorz Łoś
 */
public class BeerSearchPanel extends JPanel {
    
    private static final FixedPointReal ZERO = new FixedPointReal("0.0");
    private static final FixedPointReal MAX_PRICE = new FixedPointReal("30.00");
    private static final int MAX_IBU = 120;
    private static final FixedPointReal MAX_ABV = new FixedPointReal("12.0");
    private static final FixedPointReal MAX_BLG = new FixedPointReal("30.0");
    private static final String CURR = "\u20ac";
    
    private final BeerShowcasePane beerShowcasePane;
    private final RangeSelector priceSelector = new RangeSelector(ZERO, MAX_PRICE, RangeSelector.Limits.RIGHT_TO_INF, CURR);
    private final RangeSelector ibuSelector = new RangeSelector(0, MAX_IBU, RangeSelector.Limits.RIGHT_TO_INF, "");
    private final RangeSelector abvSelector = new RangeSelector(ZERO, MAX_ABV, RangeSelector.Limits.RIGHT_TO_INF, "%");
    private final RangeSelector blgSelector = new RangeSelector(ZERO, MAX_BLG, RangeSelector.Limits.RIGHT_TO_INF, "\u00b0");
    private final Collection<RangeSelector> rangeSelectors = new ArrayList<>();
    private final Map<OptionLabel, Filter> optionToFilter = new HashMap<>();
    private final Map<RangeSelector, FilterBuilder> rangeSelectorToFilterBuilder = new HashMap<>();
    
    private final OptionLabel mainAle = new OptionLabel("Ale");
    private final OptionLabel mainLager = new OptionLabel("Lager");
    private final OptionLabel mainWild = new OptionLabel("Wild");
    
    private final OptionLabel styleBaltic = new OptionLabel("Baltic Porter");
    private final OptionLabel styleBelgian = new OptionLabel("Belgian styles");
    private final OptionLabel styleBock = new OptionLabel("Bock");
    private final OptionLabel styleIpa = new OptionLabel("IPA");
    private final OptionLabel styleSour = new OptionLabel("Sour beers");
    private final OptionLabel styleStout = new OptionLabel("Stout & Porter");
    private final OptionLabel styleWheat = new OptionLabel("Wheat beers");
    
    private final OptionLabel colorPale = new OptionLabel("Pale");
    private final OptionLabel colorAmber = new OptionLabel("Amber");
    private final OptionLabel colorDark = new OptionLabel("Dark");
    
    private final OptionLabel tasteBalanced = new OptionLabel("Well-balanced");
    private final OptionLabel tasteBitter = new OptionLabel("Bitter");
    private final OptionLabel tasteFruitty = new OptionLabel("Fruitty");
    private final OptionLabel tasteHoppy = new OptionLabel("Hoppy");
    private final OptionLabel tasteMalty = new OptionLabel("Malty");
    private final OptionLabel tasteRoasty = new OptionLabel("Roasty");
    private final OptionLabel tasteSpice = new OptionLabel("Spice");
    
    private final OptionLabel featureAmerican = new OptionLabel("American");
    private final OptionLabel featureBrett = new OptionLabel("Brett");
    private final OptionLabel featureImperial = new OptionLabel("Double/Imperial");
    private final OptionLabel featureMilk = new OptionLabel("Milk");
    private final OptionLabel featureOatmeal = new OptionLabel("Oatmeal");
    private final OptionLabel featureRye = new OptionLabel("Rye");
    private final OptionLabel featureSmoked = new OptionLabel("Smoked");
    
    private final ArrayList<OptionLabel> options = new ArrayList<>();
    
    private final JButton clearButton = new JButton("<html> Clear selection");
    private final JButton searchButton = new JButton("<html> Search");
    
    
    public BeerSearchPanel(BeerShowcasePane beerShowcasePane) {
        this.beerShowcasePane = beerShowcasePane;
        
        initOptionList();
        
        RelativeLayout rl = new RelativeLayout(RelativeLayout.Y_AXIS);
        rl.setFill(true);
        setLayout(rl);
        add(makeTopPanel(), new Float(8));
        add(makeOptionsPanel(), new Float(46));
        add(makeRangesPanel(), new Float(46));
    }
    
    private void initOptionList() {
        Class cl = this.getClass();
        Field[] fields = cl.getDeclaredFields();
        for (Field field: fields) {
            try {
                Class type = field.getType();
                if (type.equals(OptionLabel.class))
                    options.add((OptionLabel) field.get(this));
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(BeerSearchPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(BeerSearchPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private Component makeTopPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(clearButton, BorderLayout.LINE_START);
        AutoLabel label = new AutoLabel("Select beer params", JLabel.CENTER);
        label.setFontStyle(Font.BOLD);
        panel.add(label, BorderLayout.CENTER);
        panel.add(searchButton, BorderLayout.LINE_END);
        
        searchButton.addActionListener((ActionEvent e) -> {
            clearClicked();
        });
        clearButton.addActionListener((ActionEvent e) -> {
            searchClicked();
        });
        return panel;
    }

    private Component makeOptionsPanel() {
        RelativeLayout rl = new RelativeLayout(RelativeLayout.X_AXIS);
        rl.setFill(true);
        
        JPanel panel = new JPanel(rl);
        panel.add(makeMainAndColor(), new Float(1));
        panel.add(makeStyles(), new Float(1));
        panel.add(makeFeatures(), new Float(1));
        panel.add(makeTaste(), new Float(1));
        return panel;
    }
    
    private Component makeMainAndColor() {
        JPanel panel = new JPanel(new GridLayout(0,1));
        JPanel main = new JPanel(new GridLayout(0,1));
        JPanel color = new JPanel(new GridLayout(0,1));
        main.add(new AutoLabel("Fermentation", JLabel.CENTER, Font.BOLD));
        main.add(mainAle);
        main.add(mainLager);
        main.add(mainWild);
        main.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        
        color.add(new AutoLabel("Colour", JLabel.CENTER, Font.BOLD));
        color.add(colorPale);
        color.add(colorAmber);
        color.add(colorDark);        
        color.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        
        panel.add(main);
        panel.add(color);
        return panel;
    }
    
    private Component makeStyles() {
        JPanel panel = new JPanel(new GridLayout(0,1));
        panel.add(new AutoLabel("Popular styles", JLabel.CENTER, Font.BOLD));
        panel.add(styleIpa);
        panel.add(styleStout);
        panel.add(styleBaltic);
        panel.add(styleBock);
        panel.add(styleWheat);
        panel.add(styleSour);
        panel.add(styleBelgian);
        panel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        return panel;
    }
    
    private Component makeFeatures() {
        JPanel panel = new JPanel(new GridLayout(0,1));
        panel.add(new AutoLabel("Features", JLabel.CENTER, Font.BOLD));
        panel.add(featureAmerican);
        panel.add(featureImperial);
        panel.add(featureSmoked);
        panel.add(featureMilk);
        panel.add(featureOatmeal);
        panel.add(featureRye);
        panel.add(featureBrett);
        panel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        return panel;
    }
    
    private Component makeTaste() {
        JPanel panel = new JPanel(new GridLayout(0,1));
        panel.add(new AutoLabel("Taste", JLabel.CENTER, Font.BOLD));
        panel.add(tasteMalty);
        panel.add(tasteBitter);
        panel.add(tasteBalanced);
        panel.add(tasteHoppy);
        panel.add(tasteRoasty);
        panel.add(tasteFruitty);
        panel.add(tasteSpice);
        panel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        return panel;
    }

    private Component makeRangesPanel() {
        return new JPanel();
    }
    
    private void clearClicked() {
        
    }
    
    private void searchClicked() {
        
    }

    public Filter buildFilter() {
        return new NoFilter();
    }
    
    
    interface FilterBuilder {
        Filter make();
    }
}
