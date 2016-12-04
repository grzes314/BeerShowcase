package beershowcase.beerdata.properties;

import beershowcase.beerdata.Beer;
import beershowcase.beerdata.StyleKeywords;

/**
 *
 * @author Grzegorz Łoś
 */
public class BeerStyleKeywordAddedEvent extends BeerPropertyChangeEvent {
    
    public BeerStyleKeywordAddedEvent(Beer source, StyleKeywords newKeyword) {
        super(source, BeerPropertyChangeEvent.Type.StyleKeywordAdded);
        this.newKeyword = newKeyword;
    }

    public final StyleKeywords newKeyword;
}