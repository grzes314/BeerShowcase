
package beershowcase.beerdata.properties;

import beershowcase.beerdata.Beer;

/**
 *
 * @author Grzegorz Łoś
 */
public class BeerBreweryChangeEvent extends BeerPropertyChangeEvent {
    public BeerBreweryChangeEvent(Beer source, int oldBrewery, int newBrewery) {
        super(source, BeerPropertyChangeEvent.Type.BreweryChanged);
        
        this.oldBrewery = oldBrewery;
        this.newBrewery = newBrewery;
    }
    
    public final int oldBrewery;
    public final int newBrewery;
}
