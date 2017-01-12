
package beershowcase.beerdata.autostyle.simple;

/**
 *
 * @author grzes
 */
public class Not implements Condition {

    private final Condition cond;

    public Not(Condition cond) {
        this.cond = cond;
    }
    
    @Override
    public boolean isSatisfied(Factset factset) {
        return !cond.isSatisfied(factset);
    }
    
}
