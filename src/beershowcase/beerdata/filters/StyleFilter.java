
package beershowcase.beerdata.filters;

import beershowcase.beerdata.Beer;
import beershowcase.beerdata.StyleKeyword;

/**
 *
 * @author Grzegorz Łoś
 */
public class StyleFilter implements Filter {

    private final StyleKeyword style;

    public StyleFilter(StyleKeyword style) {
        this.style = style;
    }
    
    @Override
    public boolean filter(Beer beer) {
        return beer.hasStyle(style);
    }
    
}
