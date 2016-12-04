
package beershowcase.beerdata.properties;

import beershowcase.beerdata.Beer;

/**
 * Base class for information sent from beer to the change listener.
 * @author Grzegorz Łoś
 */
public class BeerPropertyChangeEvent {
    enum Type {
        StyleKeywordAdded,
        StyleKeywordRemoved,
        NameChanged
    }
    
    public final Beer source;
    public final Type type;

    BeerPropertyChangeEvent(Beer source, Type type) {
        this.source = source;
        this.type = type;
    }
}
