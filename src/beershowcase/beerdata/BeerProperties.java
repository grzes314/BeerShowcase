
package beershowcase.beerdata;

import java.util.ArrayList;

/**
 * Record-like class for storage of the beer properties.
 * @author Grzegorz Łoś
 */
public class BeerProperties {
    /**
     * Name of the beer.
     */
    public String name = "";
    
    /**
     * Style stated by the brewery.
     */
    public String declaredStyle = "";
    
    /**
     * Original gravity in 0.01 blg units.
     */
    public int plato;
    
    /**
     * Bitternes in IBU.
     */
    public int ibu;
    
    /**
     * Ingredients used to produce this beer.
     */
    public Ingredients ingredients = new Ingredients();
    
    /**
     * Commercial description of the beer.
     */
    public String descritpion = "";
    
    /**
     * Image of the beer's label.
     */
    public LazyImage labelImage;
    
    /**
     * Image of the beer's bottle.
     */
    public LazyImage bottleImage;
    
    /**
     * Style keywords associated with this beer.
     */
    public final ArrayList<StyleKeyword> keywords = new ArrayList<>();
    
}
