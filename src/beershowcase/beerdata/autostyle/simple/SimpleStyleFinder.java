
package beershowcase.beerdata.autostyle.simple;

import beershowcase.beerdata.StyleKeyword;
import beershowcase.beerdata.autostyle.StyleFinder;
import com.sun.istack.internal.logging.Logger;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Grzegorz Łoś
 */
public class SimpleStyleFinder implements StyleFinder {

    private ArrayList<Rule> rules = new ArrayList<>();
    private static final int MAX_ITER = 20;
    private static final Logger LOGGER = Logger.getLogger(SimpleStyleFinder.class);
    
    public SimpleStyleFinder() {
        rules = new RulesCreator().makeRules();
    }
    
    @Override
    public Collection<StyleKeyword> findStyleKeywords(String declaredStyle) {
        Factset factset = new Factset(declaredStyle);
        prepareRules();
        runRules(factset);
        return factset.getKeywords();
    }
    
    private void prepareRules() {
        for (Rule rule: rules)
            rule.reset();
    }
    
    private void runRules(Factset factset) {
        boolean sthChanged = true;
        int iter = 0;
        while (sthChanged && iter < MAX_ITER) {
            iter++;
            sthChanged = false;
            for (Rule rule: rules) {
                if (rule.isTriggered(factset)) {
                    sthChanged = true;
                    rule.execute(factset);
                }
            }
        }
        if (iter >= MAX_ITER) {
            LOGGER.warning("Number of iterations exceeded safe limit.");
        }
    }
}
