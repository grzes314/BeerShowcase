
package beershowcase.gui;

import beershowcase.beerdata.Beer;
import beershowcase.beerdata.StyleKeyword;
import beershowcase.beerdata.filters.And;
import beershowcase.beerdata.filters.Filter;
import beershowcase.beerdata.filters.NoFilter;
import beershowcase.beerdata.filters.Or;
import beershowcase.beerdata.filters.StyleFilter;
import beershowcase.gui.components.AutoLabel;
import beershowcase.gui.components.OptionField;
import beershowcase.gui.components.RangeSelector;
import beershowcase.gui.components.RelativeLayout;
import beershowcase.utils.Box;
import beershowcase.utils.FixedPointReal;
import beershowcase.utils.Range;
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
    private final Map<OptionField, Filter> optionToFilter = new HashMap<>();
    private final Map<RangeSelector, FilterBuilder> rangeSelectorToFilterBuilder = new HashMap<>();
    
    private final OptionField optionNovelty = new OptionField("<html>Only novelties", 0.45);
    
    private final OptionField mainAle = new OptionField("Ale");
    private final OptionField mainLager = new OptionField("Lager");
    private final OptionField mainWild = new OptionField("Wild");
    
    private final OptionField styleBaltic = new OptionField("Baltic Porter");
    private final OptionField styleBelgian = new OptionField("Belgian styles");
    private final OptionField styleBock = new OptionField("Bock");
    private final OptionField styleIpa = new OptionField("IPA");
    private final OptionField styleSour = new OptionField("Sour beers");
    private final OptionField styleStout = new OptionField("Stout & Porter");
    private final OptionField styleWheat = new OptionField("Wheat beers");
    
    private final OptionField colorPale = new OptionField("Pale");
    private final OptionField colorAmber = new OptionField("Amber");
    private final OptionField colorDark = new OptionField("Dark");
    
    private final OptionField tasteBalanced = new OptionField("Well-balanced");
    private final OptionField tasteBitter = new OptionField("Bitter");
    private final OptionField tasteFruitty = new OptionField("Fruitty");
    private final OptionField tasteHoppy = new OptionField("Hoppy");
    private final OptionField tasteMalty = new OptionField("Malty");
    private final OptionField tasteRoasty = new OptionField("Roasty");
    private final OptionField tasteSpice = new OptionField("Spice");
    
    private final OptionField featureAmerican = new OptionField("American");
    private final OptionField featureBrett = new OptionField("Brett");
    private final OptionField featureImperial = new OptionField("Double/Imperial");
    private final OptionField featureMilk = new OptionField("Milk");
    private final OptionField featureOatmeal = new OptionField("Oatmeal");
    private final OptionField featureRye = new OptionField("Rye");
    private final OptionField featureSmoked = new OptionField("Smoked");
    
    private final ArrayList<OptionField> options = new ArrayList<>();
    
    private final JButton clearButton = new JButton("<html> Clear selection");
    private final JButton searchButton = new JButton("<html> Search");
    
    
    public BeerSearchPanel(BeerShowcasePane beerShowcasePane) {
        this.beerShowcasePane = beerShowcasePane;
        
        initOptionList();
        initRangeSelectorList();
        prepareFilters();
        
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
                if (type.equals(OptionField.class))
                    options.add((OptionField) field.get(this));
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(BeerSearchPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void initRangeSelectorList() {
        Class cl = this.getClass();
        Field[] fields = cl.getDeclaredFields();
        for (Field field: fields) {
            try {
                Class type = field.getType();
                if (type.equals(RangeSelector.class))
                    rangeSelectors.add((RangeSelector) field.get(this));
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(BeerSearchPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void prepareFilters() {
        optionToFilter.put(optionNovelty, (Filter) (Beer beer) -> beer.isNovelty());
    
        optionToFilter.put(colorPale, new StyleFilter(StyleKeyword.Pale));
        optionToFilter.put(colorAmber, new StyleFilter(StyleKeyword.Amber));
        optionToFilter.put(colorDark, new StyleFilter(StyleKeyword.Dark));
        
        optionToFilter.put(mainAle, new StyleFilter(StyleKeyword.Ale));
        optionToFilter.put(mainLager, new StyleFilter(StyleKeyword.Lager));
        optionToFilter.put(mainWild, new StyleFilter(StyleKeyword.Wild));
        
        optionToFilter.put(styleIpa, new StyleFilter(StyleKeyword.IpaFamily));
        optionToFilter.put(styleStout, new Or(new StyleFilter(StyleKeyword.StoutFamily),
                new StyleFilter(StyleKeyword.Porter)) );
        optionToFilter.put(styleBaltic, new StyleFilter(StyleKeyword.BalticPorter));
        optionToFilter.put(styleBock, new StyleFilter(StyleKeyword.BockFamily));
        optionToFilter.put(styleWheat, new StyleFilter(StyleKeyword.WheatBeerFamily));
        optionToFilter.put(styleSour, new StyleFilter(StyleKeyword.Sour));
        optionToFilter.put(styleBelgian, new StyleFilter(StyleKeyword.Belgian));
        
        optionToFilter.put(featureAmerican, new StyleFilter(StyleKeyword.American));
        optionToFilter.put(featureImperial, new StyleFilter(StyleKeyword.Imperial));
        optionToFilter.put(featureSmoked, new StyleFilter(StyleKeyword.Smoked));
        optionToFilter.put(featureMilk, new StyleFilter(StyleKeyword.Milk));
        optionToFilter.put(featureOatmeal, new StyleFilter(StyleKeyword.Oatmeal));
        optionToFilter.put(featureRye, new StyleFilter(StyleKeyword.Rye));
        optionToFilter.put(featureBrett, new StyleFilter(StyleKeyword.Brett));
        
        optionToFilter.put(tasteMalty, new StyleFilter(StyleKeyword.Malty));
        optionToFilter.put(tasteBitter, new StyleFilter(StyleKeyword.BitterTaste));
        optionToFilter.put(tasteBalanced, new StyleFilter(StyleKeyword.Balanced));
        optionToFilter.put(tasteHoppy, new StyleFilter(StyleKeyword.Hoppy));
        optionToFilter.put(tasteRoasty, new StyleFilter(StyleKeyword.Roasty));
        optionToFilter.put(tasteFruitty, new StyleFilter(StyleKeyword.Fruitty));
        optionToFilter.put(tasteSpice, new StyleFilter(StyleKeyword.Spice));
        
        rangeSelectorToFilterBuilder.put(priceSelector, (FilterBuilder) () -> {
            Range range = priceSelector.getRange();
            return (Filter) (Beer beer) -> {
                return inBounds(range, beer.getPrice());
            };
        });
        rangeSelectorToFilterBuilder.put(ibuSelector, (FilterBuilder) () -> {
            Range range = ibuSelector.getRange();
            return (Filter) (Beer beer) -> {
                return inBounds(range, beer.getIbu());
            };
        });
        rangeSelectorToFilterBuilder.put(abvSelector, (FilterBuilder) () -> {
            Range range = abvSelector.getRange();
            return (Filter) (Beer beer) -> {
                return inBounds(range, beer.getAbv());
            };
        });
        rangeSelectorToFilterBuilder.put(blgSelector, (FilterBuilder) () -> {
            Range range = blgSelector.getRange();
            return (Filter) (Beer beer) -> {
                return inBounds(range, beer.getPlato());
            };
        });
    }
    
    private boolean inBounds(Range range, Box<FixedPointReal> box) {
        if (box.isEmpty())
            return false;
        else
            return range.inBounds(box.getValue());
    }
    
    private Component makeTopPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(clearButton, BorderLayout.LINE_START);
        AutoLabel label = new AutoLabel("Select beer params", JLabel.CENTER);
        label.setFontStyle(Font.BOLD);
        panel.add(label, BorderLayout.CENTER);
        panel.add(searchButton, BorderLayout.LINE_END);
        
        searchButton.addActionListener((ActionEvent e) -> {
            searchClicked();
        });
        clearButton.addActionListener((ActionEvent e) -> {
            clearClicked();
        });
        return panel;
    }

    private Component makeOptionsPanel() {
        RelativeLayout rl = new RelativeLayout(RelativeLayout.X_AXIS);
        rl.setFill(true);
        
        JPanel panel = new JPanel(rl);
        panel.add(makeMainAndColor(), new Float(2));
        panel.add(makeStyles(), new Float(3));
        panel.add(makeFeatures(), new Float(3));
        panel.add(makeTaste(), new Float(3));
        panel.add(makeOther(), new Float(2));
        return panel;
    }
    
    private Component makeOther() {
        RelativeLayout rl = new RelativeLayout(RelativeLayout.Y_AXIS);
        rl.setFill(true);
        
        JPanel panel = new JPanel(rl);
        panel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        panel.add(new AutoLabel("Other", JLabel.CENTER, Font.BOLD), new Float(1));
        panel.add(optionNovelty, new Float(1));
        panel.add(new JPanel(), new Float(6));
        
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
        JPanel panel = new JPanel();
        RelativeLayout layout = new RelativeLayout(RelativeLayout.Y_AXIS);
        layout.setFill(true);
        panel.setLayout(layout);
        
        panel.add(makeSelectorPanel("Price", priceSelector), new Float(3));
        panel.add(new JPanel(), new Float(1));
        panel.add(makeSelectorPanel("IBU", ibuSelector), new Float(3));
        panel.add(new JPanel(), new Float(1));
        panel.add(makeSelectorPanel("Gravity", blgSelector), new Float(3));
        panel.add(new JPanel(), new Float(1));
        panel.add(makeSelectorPanel("Alc. vol.", abvSelector), new Float(3));
        
        return panel;
    }
    

    private JPanel makeSelectorPanel(String label, RangeSelector rangeSelector) {
        JPanel panel = new JPanel();
        
        RelativeLayout rl = new RelativeLayout(RelativeLayout.X_AXIS);
        rl.setFill(true);
        panel.setLayout(rl);
        
        panel.add(new AutoLabel(label, 0.33, JLabel.RIGHT), new Float(10));
        panel.add(rangeSelector, new Float(90));
        
        return panel;
    }
    
    private void clearClicked() {
        clearButtons();
        clearRanges();
        revalidate();
        repaint();
    }
    
    private void clearRanges() {
        for (RangeSelector rs: rangeSelectors)
            rs.reset();
    }
    
    private void clearButtons() {
        for (OptionField op: options)
            op.setSelected(false);
    }
    
    private void searchClicked() {
        beerShowcasePane.goToBrowsingMode();
    }

    public Filter buildFilter() {
        Filter filterOptions = buildFilterFromOptionFields();
        Filter filterRanges = buildFilterFromRanges();
        if (filterOptions == null) {
            if (filterRanges == null)
                return new NoFilter();
            else
                return filterRanges;
        } else {
            if (filterRanges == null)
                return filterOptions;
            else
                return new And(filterRanges, filterOptions);
        }
    }
    
    private Filter buildFilterFromRanges() {
        Filter lastFilter = null;
        And and = new And();
        int count = 0;
        for (RangeSelector rs: rangeSelectors) {
            if (!rs.isSetToMinimum() || !rs.isSetToMaximum()) {
                lastFilter = makeFilterFromRange(rs);
                and.add(lastFilter);
                count++;
            }
        }
        if (count == 0)
            return null;
        else if (count == 1)
            return lastFilter;
        else
            return and;
    }

    private Filter makeFilterFromRange(RangeSelector rs) {
        FilterBuilder fb = rangeSelectorToFilterBuilder.get(rs);
        if (fb == null)
            throw new RuntimeException("Each RangeSelector should have corresponding FilterBuilder");
        return fb.make();
    }

    private Filter buildFilterFromOptionFields() {
        Filter lastFilter = null;
        And and = new And();
        int count = 0;
        for (OptionField of: options) {
            if (of.isSelected()) {
                lastFilter = optionToFilter.get(of);
                and.add(lastFilter);
                count++;
            }
        }
        if (count == 0)
            return null;
        else if (count == 1)
            return lastFilter;
        else
            return and;        
    }
    
    
    interface FilterBuilder {
        Filter make();
    }
}
