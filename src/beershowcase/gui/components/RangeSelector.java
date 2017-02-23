
package beershowcase.gui.components;

import beershowcase.utils.FixedPointReal;
import beershowcase.utils.Range;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;

/**
 *
 * @author Grzegorz Łoś
 */
public class RangeSelector extends JPanel {
    private FixedPointReal minValue, maxValue;
    private final Limits limits;
    private final String unitSign;
    private static final char INF = '\u221e';
    JSlider sliderMin = new JSlider();
    JSlider sliderMax = new JSlider();
    AutoLabel labelMin = new AutoLabel();
    AutoLabel labelMax = new AutoLabel();
    

    public RangeSelector(int min, int max, Limits limits, String unitSign) {
        this.unitSign = unitSign;
        this.limits = limits;
        initComponents();
        setRange(min, max);
    }

    public RangeSelector(FixedPointReal min, FixedPointReal max, Limits limits, String unitSign) {
        this.unitSign = unitSign;
        this.limits = limits;
        initComponents();
        setRange(min, max);
    }
    
    private void initComponents() {
        sliderMin.addChangeListener((ChangeEvent ce) -> {
            adjustIfNecessary(sliderMax, sliderMin.getValue());
            labelMin.setText(makeLabelText(sliderMin.getValue(), false));
        });
        
        sliderMax.addChangeListener((ChangeEvent ce) -> {
            adjustIfNecessary(sliderMin, sliderMax.getValue());
            labelMax.setText(makeLabelText(sliderMax.getValue(), true));
        });
        RelativeLayout rl = new RelativeLayout(RelativeLayout.Y_AXIS);
        rl.setFill(true);
        setLayout(rl);
        add(makeSliderPanel(sliderMin, labelMin), new Float(1));
        add(makeSliderPanel(sliderMax, labelMax), new Float(1));
    }

    private JPanel makeSliderPanel(JSlider slider, JComponent label) {
        RelativeLayout rl = new RelativeLayout(RelativeLayout.X_AXIS);
        rl.setFill(true);
        rl.setGap(10);
        JPanel panel = new JPanel(rl);
        panel.add(slider, new Float(90));
        panel.add(label, new Float(10));
        return panel;
    }
    
    public final void setRange(int min, int max) {
        if (max < min)
            max = min;
        
        minValue = new FixedPointReal(min, 0);
        maxValue = new FixedPointReal(max, 0);
        setSliderLimits(sliderMin, min);
        setSliderLimits(sliderMax, max);
    }
    
    public final void setRange(FixedPointReal min, FixedPointReal max) {
        if (max.smallerThan(min))
            max = min;
        if (min.pointPos < max.pointPos)
            min = min.toAnotherPointPos(max.pointPos);
        if (max.pointPos < min.pointPos)
            max = max.toAnotherPointPos(min.pointPos);
        
        minValue = min;
        maxValue = max;
        setSliderLimits(sliderMin, (int) min.units);
        setSliderLimits(sliderMax, (int) max.units);
    }
    
    public void reset() {
        sliderMin.setValue((int) minValue.units);
        sliderMax.setValue((int) maxValue.units);
    }

    private void setSliderLimits(JSlider slider, int val) {
        slider.setMinimum((int) minValue.units);
        slider.setMaximum((int) maxValue.units);
        slider.setValue(val);
    }
    
    public FixedPointReal getFromValue() {
        int pp = minValue.pointPos;
        return new FixedPointReal(sliderMin.getValue(), pp);
    }
    
    public FixedPointReal getToValue() {
        int pp = maxValue.pointPos;
        return new FixedPointReal(sliderMax.getValue(), pp);
    }
    
    public Range getRange() {
        if (limits.areBothToInf() && isSetToMaximum() && isSetToMinimum())
            return new Range(Range.Type.INF_INF);
        else if (limits.isRightToInf() && isSetToMaximum())
            return new Range(getFromValue(), Range.Type.VAL_INF);
        else if (limits.isLeftToInf() && isSetToMinimum())
            return new Range(getToValue(), Range.Type.INF_VAL);
        else
            return new Range(getFromValue(), getToValue());
    }
    
    public boolean isSetToMaximum() {
        return sliderMax.getValue() >= maxValue.units;
    }
    
    public boolean isSetToMinimum() {
        return sliderMin.getValue() <= minValue.units;
    }

    private void adjustIfNecessary(JSlider slider, int val) {
        if (sliderMax.getValue() < sliderMin.getValue()) {
            slider.setValue(val);
        }
    }
    
    private String makeLabelText(int valueOnSlider, boolean forMaxSlider) {
        int pp = minValue.pointPos;
        return makeNumberString(
                new FixedPointReal(valueOnSlider, pp), forMaxSlider) + unitSign;
    }
    
    private String makeNumberString(FixedPointReal value, boolean forMaxSlider) {
        if (limits.isRightToInf() && forMaxSlider && value.greaterEq(maxValue))
            return "" + INF;
        if (limits.isLeftToInf() && !forMaxSlider && value.smallerEq(minValue))
            return "-" + INF;
        return value.toString();
    }

    public enum Limits {
        LEFT_TO_INF(true, false), RIGHT_TO_INF(false, true),
        BOTH_TO_INF(true, true), NONE_TO_INF(false, false);

        private Limits(boolean isLeftToInf, boolean isRightToInf) {
            this.isLeftToInf = isLeftToInf;
            this.isRightToInf = isRightToInf;
        }

        private final boolean isLeftToInf, isRightToInf;
        
        private boolean areBothToInf() {
            return isLeftToInf && isRightToInf;
        }

        private boolean isRightToInf() {
            return isRightToInf;
        }

        private boolean isLeftToInf() {
            return isLeftToInf;
        }
    }
}
