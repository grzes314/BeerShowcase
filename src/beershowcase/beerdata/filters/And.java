
package beershowcase.beerdata.filters;

import beershowcase.beerdata.Beer;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Grzegorz Łoś
 */
public class Conjunction implements Filter {

    Collection<Filter> filters = new ArrayList<>();
    
    public Conjunction() {
        
    }
    
    public Conjunction(Filter ...filters) {
        for (Filter f: filters)
            this.filters.add(f);
    }

    public boolean add(Filter e) {
        return filters.add(e);
    }
    
    @Override
    public boolean filter(Beer beer) {
        for (Filter f: filters) {
            if (!f.filter(beer))
                return false;
        }
        return true;
    }
    
}
