
package beershowcase.beerdata.properties;

import beershowcase.beerdata.Beer;

/**
 *
 * @author Grzegorz Łoś
 */
public class BeerNameChangedEvent extends BeerPropertyChangeEvent {
    
    public BeerNameChangedEvent(Beer source, String oldName, String newName) {
        super(source, BeerPropertyChangeEvent.Type.NameChanged);
        
        this.oldName = oldName;
        this.newName = newName;
    }
    
    public final String oldName;
    public final String newName;
}
