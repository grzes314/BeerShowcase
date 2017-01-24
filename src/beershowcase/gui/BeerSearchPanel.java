
package beershowcase.gui;

import beershowcase.beerdata.Beer;
import beershowcase.utils.FixedPointReal;
import beershowcase.beerdata.StyleKeyword;
import beershowcase.beerdata.filters.And;
import beershowcase.beerdata.filters.Filter;
import beershowcase.beerdata.filters.NoFilter;
import beershowcase.beerdata.filters.Or;
import beershowcase.beerdata.filters.StyleFilter;
import beershowcase.utils.Range;
import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.swing.AbstractButton;
import javax.swing.JPanel;

/**
 *
 * @author Grzegorz Łoś
 */
public class BeerSearchPanel extends javax.swing.JPanel {

    private static final FixedPointReal ZERO = new FixedPointReal("0.0");
    private static final FixedPointReal MAX_PRICE = new FixedPointReal("30.00");
    private static final int MAX_IBU = 120;
    private static final FixedPointReal MAX_ABV = new FixedPointReal("12.0");
    private static final FixedPointReal MAX_BLG = new FixedPointReal("30.0");
    private static final String CURR = "\u20ac";
    
    private final BeerShowcasePane beerShowcasePane;
    private DeselectableButtonGroup buttonGroupPopularStyles = new DeselectableButtonGroup();
    private DeselectableButtonGroup buttonGroupFermentation = new DeselectableButtonGroup();
    private DeselectableButtonGroup buttonGroupColor = new DeselectableButtonGroup();
    private RangeSelector priceSelector = new RangeSelector(ZERO, MAX_PRICE, RangeSelector.Limits.RIGHT_TO_INF, CURR);
    private RangeSelector ibuSelector = new RangeSelector(0, MAX_IBU, RangeSelector.Limits.RIGHT_TO_INF, "");
    private RangeSelector abvSelector = new RangeSelector(ZERO, MAX_ABV, RangeSelector.Limits.RIGHT_TO_INF, "%");
    private RangeSelector blgSelector = new RangeSelector(ZERO, MAX_BLG, RangeSelector.Limits.RIGHT_TO_INF, "\u00b0");
    private Collection<JPanel> buttonPanels = new ArrayList<>();
    private Collection<RangeSelector> rangeSelectors = new ArrayList<>();
    private Map<Component, Filter> compToFilter = new HashMap<>();
    private Map<RangeSelector, FilterBuilder> rangeSelectorToFilterBuilder = new HashMap<>();

    
    public BeerSearchPanel(BeerShowcasePane beerShowcasePane) {
        this.beerShowcasePane = beerShowcasePane;
        initComponents();
        priceRangeContainer.setLayout(new BorderLayout());
        priceRangeContainer.add(priceSelector);
        ibuRangeContainer.setLayout(new BorderLayout());
        ibuRangeContainer.add(ibuSelector);
        abvContainer.setLayout(new BorderLayout());
        abvContainer.add(abvSelector);
        blgRangeContainer.setLayout(new BorderLayout());
        blgRangeContainer.add(blgSelector);
        
        buttonPanels.add(colourPanel);
        buttonPanels.add(fermentationPanel);
        buttonPanels.add(stylesPanel);
        buttonPanels.add(featuresPanel);
        buttonPanels.add(tastePanel);
        
        rangeSelectors.add(priceSelector);
        rangeSelectors.add(ibuSelector);
        rangeSelectors.add(abvSelector);
        rangeSelectors.add(blgSelector);
        
        prepareFilters();
    }
    
    private void prepareFilters() {
        compToFilter.put(rbPale, new StyleFilter(StyleKeyword.Pale));
        compToFilter.put(rbAmber, new StyleFilter(StyleKeyword.Amber));
        compToFilter.put(rbDark, new StyleFilter(StyleKeyword.Dark));
        
        compToFilter.put(rbAle, new StyleFilter(StyleKeyword.Ale));
        compToFilter.put(rbLager, new StyleFilter(StyleKeyword.Lager));
        compToFilter.put(rbWild, new StyleFilter(StyleKeyword.Wild));
        
        compToFilter.put(rbIpa, new StyleFilter(StyleKeyword.IpaFamily));
        compToFilter.put(rbStout, new Or(new StyleFilter(StyleKeyword.StoutFamily),
                new StyleFilter(StyleKeyword.Porter)) );
        compToFilter.put(rbBaltic, new StyleFilter(StyleKeyword.BalticPorter));
        compToFilter.put(rbBock, new StyleFilter(StyleKeyword.BockFamily));
        compToFilter.put(rbWheat, new StyleFilter(StyleKeyword.WheatBeerFamily));
        compToFilter.put(rbSour, new StyleFilter(StyleKeyword.Sour));
        compToFilter.put(rbBelgian, new StyleFilter(StyleKeyword.Belgian));
        
        compToFilter.put(featureAmerican, new StyleFilter(StyleKeyword.American));
        compToFilter.put(featureImperial, new StyleFilter(StyleKeyword.Imperial));
        compToFilter.put(featureSmoked, new StyleFilter(StyleKeyword.Smoked));
        compToFilter.put(featureMilk, new StyleFilter(StyleKeyword.Milk));
        compToFilter.put(featureOatmeal, new StyleFilter(StyleKeyword.Oatmeal));
        compToFilter.put(featureRye, new StyleFilter(StyleKeyword.Rye));
        compToFilter.put(featureBrett, new StyleFilter(StyleKeyword.Brett));
        
        compToFilter.put(tasteMalty, new StyleFilter(StyleKeyword.Malty));
        compToFilter.put(tasteBitter, new StyleFilter(StyleKeyword.BitterTaste));
        compToFilter.put(tasteBalanced, new StyleFilter(StyleKeyword.Balanced));
        compToFilter.put(tasteHoppy, new StyleFilter(StyleKeyword.Hoppy));
        compToFilter.put(tasteRoasty, new StyleFilter(StyleKeyword.Roasty));
        compToFilter.put(tasteFruitty, new StyleFilter(StyleKeyword.Fruitty));
        compToFilter.put(tasteSpice, new StyleFilter(StyleKeyword.Spice));
        
        rangeSelectorToFilterBuilder.put(priceSelector, (FilterBuilder) () -> {
            Range range = priceSelector.getRange();
            return (Filter) (Beer beer) -> {
                return range.inBounds(beer.getPrice());
            };
        });
        rangeSelectorToFilterBuilder.put(ibuSelector, (FilterBuilder) () -> {
            Range range = ibuSelector.getRange();
            return (Filter) (Beer beer) -> {
                return range.inBounds(new FixedPointReal(beer.getIbu(), 0));
            };
        });
        rangeSelectorToFilterBuilder.put(abvSelector, (FilterBuilder) () -> {
            Range range = abvSelector.getRange();
            return (Filter) (Beer beer) -> {
                return range.inBounds(beer.getAbv());
            };
        });
        rangeSelectorToFilterBuilder.put(blgSelector, (FilterBuilder) () -> {
            Range range = blgSelector.getRange();
            return (Filter) (Beer beer) -> {
                return range.inBounds(beer.getPlato());
            };
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        stylesPanel = new javax.swing.JPanel();
        rbIpa = new javax.swing.JRadioButton();
        rbStout = new javax.swing.JRadioButton();
        rbBaltic = new javax.swing.JRadioButton();
        rbBock = new javax.swing.JRadioButton();
        rbWheat = new javax.swing.JRadioButton();
        rbSour = new javax.swing.JRadioButton();
        rbBelgian = new javax.swing.JRadioButton();
        featuresPanel = new javax.swing.JPanel();
        featureAmerican = new javax.swing.JCheckBox();
        featureSmoked = new javax.swing.JCheckBox();
        featureMilk = new javax.swing.JCheckBox();
        featureRye = new javax.swing.JCheckBox();
        featureOatmeal = new javax.swing.JCheckBox();
        featureBrett = new javax.swing.JCheckBox();
        featureImperial = new javax.swing.JCheckBox();
        jPanel3 = new javax.swing.JPanel();
        buttonClear = new javax.swing.JButton();
        buttonSearch = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        priceRangeContainer = new javax.swing.JPanel();
        ibuRangeContainer = new javax.swing.JPanel();
        abvContainer = new javax.swing.JPanel();
        blgRangeContainer = new javax.swing.JPanel();
        fermentationPanel = new javax.swing.JPanel();
        rbAle = new javax.swing.JRadioButton();
        rbLager = new javax.swing.JRadioButton();
        rbWild = new javax.swing.JRadioButton();
        colourPanel = new javax.swing.JPanel();
        rbPale = new javax.swing.JRadioButton();
        rbAmber = new javax.swing.JRadioButton();
        rbDark = new javax.swing.JRadioButton();
        tastePanel = new javax.swing.JPanel();
        tasteMalty = new javax.swing.JCheckBox();
        tasteBitter = new javax.swing.JCheckBox();
        tasteBalanced = new javax.swing.JCheckBox();
        tasteHoppy = new javax.swing.JCheckBox();
        tasteRoasty = new javax.swing.JCheckBox();
        tasteFruitty = new javax.swing.JCheckBox();
        tasteSpice = new javax.swing.JCheckBox();

        stylesPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Popular styles"));

        buttonGroupPopularStyles.add(rbIpa);
        rbIpa.setText("IPA");

        buttonGroupPopularStyles.add(rbStout);
        rbStout.setText("Stout & Porter");

        buttonGroupPopularStyles.add(rbBaltic);
        rbBaltic.setText("Baltic Porter");

        buttonGroupPopularStyles.add(rbBock);
        rbBock.setText("Bock");

        buttonGroupPopularStyles.add(rbWheat);
        rbWheat.setText("Wheat beer");

        buttonGroupPopularStyles.add(rbSour);
        rbSour.setText("Sour beers");

        buttonGroupPopularStyles.add(rbBelgian);
        rbBelgian.setText("Belgian styles");

        javax.swing.GroupLayout stylesPanelLayout = new javax.swing.GroupLayout(stylesPanel);
        stylesPanel.setLayout(stylesPanelLayout);
        stylesPanelLayout.setHorizontalGroup(
            stylesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(stylesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(stylesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rbIpa)
                    .addComponent(rbStout)
                    .addComponent(rbBaltic)
                    .addComponent(rbBock)
                    .addComponent(rbWheat)
                    .addComponent(rbSour)
                    .addComponent(rbBelgian))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        stylesPanelLayout.setVerticalGroup(
            stylesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(stylesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rbIpa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbStout)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbBaltic)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbBock)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbWheat)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbSour)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbBelgian)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        featuresPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Features"));

        featureAmerican.setText("American");

        featureSmoked.setText("Smoked");

        featureMilk.setText("<html> Milk / <br>with lactose");

        featureRye.setText("Rye");

        featureOatmeal.setText("Oatmeal");

        featureBrett.setText("Brett");

        featureImperial.setText("<html> Double / <br>Imperial");

        javax.swing.GroupLayout featuresPanelLayout = new javax.swing.GroupLayout(featuresPanel);
        featuresPanel.setLayout(featuresPanelLayout);
        featuresPanelLayout.setHorizontalGroup(
            featuresPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(featuresPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(featuresPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, featuresPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(featuresPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(featureMilk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(featureSmoked)
                            .addComponent(featureAmerican)))
                    .addGroup(featuresPanelLayout.createSequentialGroup()
                        .addGroup(featuresPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(featureOatmeal)
                            .addComponent(featureRye)
                            .addComponent(featureBrett)
                            .addComponent(featureImperial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        featuresPanelLayout.setVerticalGroup(
            featuresPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(featuresPanelLayout.createSequentialGroup()
                .addComponent(featureAmerican)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(featureImperial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(featureSmoked)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(featureMilk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(featureOatmeal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(featureRye)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(featureBrett)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        buttonClear.setText("Clear selection");
        buttonClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonClearActionPerformed(evt);
            }
        });

        buttonSearch.setText("Search");
        buttonSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSearchActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Select beer parameters");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(buttonClear)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filler2, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filler1, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(buttonClear, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
            .addComponent(buttonSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(filler1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(filler2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel1.setText("ABV:");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel2.setText("Price:");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel3.setText("IBU:");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel4.setText("Blg:");

        javax.swing.GroupLayout priceRangeContainerLayout = new javax.swing.GroupLayout(priceRangeContainer);
        priceRangeContainer.setLayout(priceRangeContainerLayout);
        priceRangeContainerLayout.setHorizontalGroup(
            priceRangeContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        priceRangeContainerLayout.setVerticalGroup(
            priceRangeContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout ibuRangeContainerLayout = new javax.swing.GroupLayout(ibuRangeContainer);
        ibuRangeContainer.setLayout(ibuRangeContainerLayout);
        ibuRangeContainerLayout.setHorizontalGroup(
            ibuRangeContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        ibuRangeContainerLayout.setVerticalGroup(
            ibuRangeContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 49, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout abvContainerLayout = new javax.swing.GroupLayout(abvContainer);
        abvContainer.setLayout(abvContainerLayout);
        abvContainerLayout.setHorizontalGroup(
            abvContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        abvContainerLayout.setVerticalGroup(
            abvContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout blgRangeContainerLayout = new javax.swing.GroupLayout(blgRangeContainer);
        blgRangeContainer.setLayout(blgRangeContainerLayout);
        blgRangeContainerLayout.setHorizontalGroup(
            blgRangeContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        blgRangeContainerLayout.setVerticalGroup(
            blgRangeContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(blgRangeContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ibuRangeContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(abvContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(priceRangeContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                    .addComponent(priceRangeContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ibuRangeContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                    .addComponent(abvContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                    .addComponent(blgRangeContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        fermentationPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Fermentation"));

        buttonGroupFermentation.add(rbAle);
        rbAle.setText("Ale");

        buttonGroupFermentation.add(rbLager);
        rbLager.setText("Lager");

        buttonGroupFermentation.add(rbWild);
        rbWild.setText("Wild");

        javax.swing.GroupLayout fermentationPanelLayout = new javax.swing.GroupLayout(fermentationPanel);
        fermentationPanel.setLayout(fermentationPanelLayout);
        fermentationPanelLayout.setHorizontalGroup(
            fermentationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fermentationPanelLayout.createSequentialGroup()
                .addGroup(fermentationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rbWild)
                    .addComponent(rbLager)
                    .addComponent(rbAle))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        fermentationPanelLayout.setVerticalGroup(
            fermentationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fermentationPanelLayout.createSequentialGroup()
                .addComponent(rbAle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbLager)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbWild)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        colourPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Colour"));

        buttonGroupColor.add(rbPale);
        rbPale.setText("Pale");

        buttonGroupColor.add(rbAmber);
        rbAmber.setText("Amber");

        buttonGroupColor.add(rbDark);
        rbDark.setText("Dark");

        javax.swing.GroupLayout colourPanelLayout = new javax.swing.GroupLayout(colourPanel);
        colourPanel.setLayout(colourPanelLayout);
        colourPanelLayout.setHorizontalGroup(
            colourPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(colourPanelLayout.createSequentialGroup()
                .addGroup(colourPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rbPale)
                    .addComponent(rbAmber)
                    .addComponent(rbDark))
                .addGap(0, 36, Short.MAX_VALUE))
        );
        colourPanelLayout.setVerticalGroup(
            colourPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(colourPanelLayout.createSequentialGroup()
                .addComponent(rbPale)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbAmber)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbDark))
        );

        tastePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Taste"));

        tasteMalty.setText("Malty");

        tasteBitter.setText("Bitter");

        tasteBalanced.setText("Well-balanced");

        tasteHoppy.setText("Hoppy");

        tasteRoasty.setText("Roasty");

        tasteFruitty.setText("Fruitty");

        tasteSpice.setText("Spice");

        javax.swing.GroupLayout tastePanelLayout = new javax.swing.GroupLayout(tastePanel);
        tastePanel.setLayout(tastePanelLayout);
        tastePanelLayout.setHorizontalGroup(
            tastePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tastePanelLayout.createSequentialGroup()
                .addGroup(tastePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tasteMalty)
                    .addComponent(tasteBitter)
                    .addComponent(tasteBalanced)
                    .addComponent(tasteHoppy)
                    .addComponent(tasteRoasty)
                    .addComponent(tasteFruitty)
                    .addComponent(tasteSpice))
                .addGap(0, 16, Short.MAX_VALUE))
        );
        tastePanelLayout.setVerticalGroup(
            tastePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tastePanelLayout.createSequentialGroup()
                .addComponent(tasteMalty)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tasteBitter)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tasteBalanced)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tasteHoppy)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tasteRoasty)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tasteFruitty)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tasteSpice)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(fermentationPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(colourPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(stylesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(featuresPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tastePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(colourPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fermentationPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(stylesPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(featuresPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tastePanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void buttonSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSearchActionPerformed
        beerShowcasePane.goToBrowsingMode();
    }//GEN-LAST:event_buttonSearchActionPerformed

    private void buttonClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonClearActionPerformed
        clearButtons();
        clearRanges();
        revalidate();
        repaint();
    }//GEN-LAST:event_buttonClearActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel abvContainer;
    private javax.swing.JPanel blgRangeContainer;
    private javax.swing.JButton buttonClear;
    private javax.swing.JButton buttonSearch;
    private javax.swing.JPanel colourPanel;
    private javax.swing.JCheckBox featureAmerican;
    private javax.swing.JCheckBox featureBrett;
    private javax.swing.JCheckBox featureImperial;
    private javax.swing.JCheckBox featureMilk;
    private javax.swing.JCheckBox featureOatmeal;
    private javax.swing.JCheckBox featureRye;
    private javax.swing.JCheckBox featureSmoked;
    private javax.swing.JPanel featuresPanel;
    private javax.swing.JPanel fermentationPanel;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.JPanel ibuRangeContainer;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel priceRangeContainer;
    private javax.swing.JRadioButton rbAle;
    private javax.swing.JRadioButton rbAmber;
    private javax.swing.JRadioButton rbBaltic;
    private javax.swing.JRadioButton rbBelgian;
    private javax.swing.JRadioButton rbBock;
    private javax.swing.JRadioButton rbDark;
    private javax.swing.JRadioButton rbIpa;
    private javax.swing.JRadioButton rbLager;
    private javax.swing.JRadioButton rbPale;
    private javax.swing.JRadioButton rbSour;
    private javax.swing.JRadioButton rbStout;
    private javax.swing.JRadioButton rbWheat;
    private javax.swing.JRadioButton rbWild;
    private javax.swing.JPanel stylesPanel;
    private javax.swing.JCheckBox tasteBalanced;
    private javax.swing.JCheckBox tasteBitter;
    private javax.swing.JCheckBox tasteFruitty;
    private javax.swing.JCheckBox tasteHoppy;
    private javax.swing.JCheckBox tasteMalty;
    private javax.swing.JPanel tastePanel;
    private javax.swing.JCheckBox tasteRoasty;
    private javax.swing.JCheckBox tasteSpice;
    // End of variables declaration//GEN-END:variables

    public Filter buildFilter() {
        And and = new And();
        Filter lastConjunct = null;
        int count = 0;
        
        for (JPanel bttnPanel: buttonPanels) {
            Filter conjunct = makeFilterFromPanel(bttnPanel);
            if (conjunct != null) {
                lastConjunct = conjunct;
                count++;
                and.add(conjunct);
            }
        }
        for (RangeSelector rs: rangeSelectors) {
            Filter conjunct = makeFilterFromRange(rs);
            if (conjunct != null) {
                lastConjunct = conjunct;
                count++;
                and.add(conjunct);
            }
        }
        
        if (count > 1)
            return and;
        else if (count == 1)
            return lastConjunct;
        else
            return new NoFilter();
    }

    private Filter makeFilterFromPanel(JPanel bttnPanel) {
        Or or = new Or();
        Filter lastAlternative = null;
        int count = 0;
        
        Component[] bttns = bttnPanel.getComponents();
        for (Component comp: bttns) {
            AbstractButton button = (AbstractButton) comp;
            if (button.isSelected()) {
                lastAlternative = compToFilter.get(comp);
                if (lastAlternative == null) {
                    throw new RuntimeException();
                }
                count++;
                or.add(lastAlternative);
            }
        }
        
        if (count > 1)
            return or;
        else if (count == 1)
            return lastAlternative;
        else
            return null;
    }

    private void clearButtons() {
        for (JPanel bttnPanel: buttonPanels) {
            clearButtonsOnPanel(bttnPanel);
        }
    }

    private void clearButtonsOnPanel(JPanel bttnPanel) {
        Component[] bttns = bttnPanel.getComponents();
        for (Component comp: bttns) {
            AbstractButton button = (AbstractButton) comp;
            button.setSelected(false);
        }
    }

    private void clearRanges() {
        for (RangeSelector rs: rangeSelectors)
            rs.reset();
    }

    private Filter makeFilterFromRange(RangeSelector rs) {
        FilterBuilder fb = rangeSelectorToFilterBuilder.get(rs);
        if (fb == null)
            throw new RuntimeException("Each RangeSelector should have corresponding FilterBuilder");
        return fb.make();
    }
}

interface FilterBuilder {
    Filter make();
}