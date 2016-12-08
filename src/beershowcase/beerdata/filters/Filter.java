
package beershowcase.beerdata.filters;

import beershowcase.beerdata.Beer;

/**
 *
 * @author Grzegorz Łoś
 */
public interface Filter {
    boolean filter(Beer beer);
}
