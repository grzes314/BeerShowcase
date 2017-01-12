
package beershowcase.beerdata.autostyle.simple;

/**
 *
 * @author grzes
 */
public class Or implements Condition {

    private final Condition[] conds;
    
    public Or(Condition... conds) {
        this.conds = conds;
    }

    @Override
    public boolean isSatisfied(Factset factset) {
        for (Condition cond: conds) {
            if (cond.isSatisfied(factset))
                return true;
        }
        return false;
    }
    
}
