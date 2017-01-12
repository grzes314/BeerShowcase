
package beershowcase.beerdata.autostyle.simple;


/**
 *
 * @author Grzegorz Łoś
 */
public class Rule {
    private final Condition[] conds;
    private final Action[] actions;
    private boolean wasExecuted = true;

    public Rule(Condition[] conds, Action[] actions) {
        this.conds = conds;
        this.actions = actions;
    }
    
    public boolean isTriggered(Factset factset) {
        if (wasExecuted)
            return false;
        
        for (Condition c: conds) {
            if (!c.isSatisfied(factset))
                return false;
        }
        return true;
    }
    
    public void execute(Factset factset) {
        wasExecuted = true;
        for (Action a: actions)
            a.execute(factset);
    }

    void reset() {
        wasExecuted = false;
    }
}
