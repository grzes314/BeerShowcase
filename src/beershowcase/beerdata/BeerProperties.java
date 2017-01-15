
package beershowcase.beerdata;

import java.awt.image.BufferedImage;

/**
 * Record-like class for storage of the beer properties.
 * Represents only factual data that is accessible from exteranal sources.
 * @author Grzegorz Łoś
 */
public class BeerProperties {
    /**
     * Name of the beer.
     */
    public String name = "";
    
    /**
     * Name of the brwery that produced this beer.
     */
    public String breweryName = "";
    
    /**
     * Style stated by the brewery.
     */
    public String declaredStyle = "";
    
    /**
     * Original gravity in 0.1 blg units.
     */
    public int plato;
    
    /**
     * Alcohol content by volume in promiles.
     */
    public int abv;
    
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
    public BufferedImage labelImage;

    public BeerProperties() {
    }
    
    /**
     * Copy constructor.
     * @param original 
     */
    public BeerProperties(BeerProperties original) {
        name = original.name;
        breweryName = original.breweryName;
        declaredStyle = original.declaredStyle;
        plato = original.plato;
        abv = original.abv;
        ibu = original.ibu;
        ingredients = new Ingredients();
        descritpion = original.descritpion;
        labelImage = original.labelImage;
    }
}
