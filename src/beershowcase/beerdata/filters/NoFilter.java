
package beershowcase.beerdata.filters;

import beershowcase.beerdata.Beer;

/**
 *
 * @author Grzegorz Łoś
 */
public class NoFilter implements Filter {

    @Override
    public boolean filter(Beer beer) {
        return true;
    }
    
}
