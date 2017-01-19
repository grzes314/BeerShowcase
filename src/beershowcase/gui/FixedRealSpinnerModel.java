
package beershowcase.gui;

import beershowcase.beerdata.FixedPointReal;
import javax.swing.AbstractSpinnerModel;

/**
 *
 * @author Grzegorz Łoś
 */
public class FixedRealSpinnerModel extends AbstractSpinnerModel {
    
    private FixedPointReal value;
    private final FixedPointReal step;

    public FixedRealSpinnerModel(FixedPointReal value, FixedPointReal step) {
        this.value = value.toAnotherPointPos(step.pointPos);
        this.step = step;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public void setValue(Object newValue) {
        value = ((FixedPointReal) newValue).toAnotherPointPos(step.pointPos);
    }

    @Override
    public Object getNextValue() {
        return value.plus(step);
    }

    @Override
    public Object getPreviousValue() {
        return value.minus(step);
    }
    
}
