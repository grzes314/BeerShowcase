
package beershowcase.beerdata.filters;

import beershowcase.beerdata.Beer;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Grzegorz Łoś
 */
public class Or implements Filter {

    Collection<Filter> filters = new ArrayList<>();
    
    public Or() {
        
    }
    
    public Or(Filter ...filters) {
        for (Filter f: filters)
            this.filters.add(f);
    }

    public boolean add(Filter e) {
        return filters.add(e);
    }
    
    @Override
    public boolean filter(Beer beer) {
        for (Filter f: filters) {
            if (f.filter(beer))
                return true;
        }
        return false;
    }
    
}
