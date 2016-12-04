package beershowcase.beerdata.properties;

import beershowcase.beerdata.Beer;
import beershowcase.beerdata.StyleKeywords;

/**
 *
 * @author Grzegorz Łoś
 */
public class BeerStyleKeywordRemovedEvent extends BeerPropertyChangeEvent {
    
    public BeerStyleKeywordRemovedEvent(Beer source, StyleKeywords deletedKeyword) {
        super(source, BeerPropertyChangeEvent.Type.StyleKeywordRemoved);
        this.deletedKeyword = deletedKeyword;
    }

    public final StyleKeywords deletedKeyword;
}
