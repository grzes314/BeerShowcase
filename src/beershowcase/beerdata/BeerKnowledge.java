
package beershowcase.beerdata;

import beershowcase.beerdata.properties.BeerPropertyChangeEvent;
import beershowcase.beerdata.properties.BeerPropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;

/**
 * The BeerKnowledge class gathers the information about beers and breweries.
 * It's also a proxy in creation of new beers and breweries.
 * @author Grzegorz Łoś
 */
public class BeerKnowledge implements JsonRepresentable, BeerPropertyChangeListener {
    private final BreweryFactory breweryFactory = new BreweryFactory();
    private final ArrayList<Brewery> breweries = new ArrayList<>();
    private final BeerFactory beerFactory = new BeerFactory();
    private final ArrayList<Beer> beers = new ArrayList<>();
    private Map<StyleKeywords, ArrayList<Beer>> styleToBeers = new HashMap<>();
    static Logger LOGGER = Logger.getLogger(BeerKnowledge.class.getName());


    @Override
    public JsonObject toJson() {
        JsonObject value = Json.createObjectBuilder()
            .add("breweryFactory", breweryFactory.toJson())
            .add("breweriesCount", breweries.size())
            .add("breweries", JsonUtils.toJsonArray(breweries))
            .add("beerFactory", beerFactory.toJson())
            .add("beersCount", beers.size())
            .add("beers", JsonUtils.toJsonArray(beers))
            .build();
        return value;
    }

    @Override
    public void fromJson(JsonObject json) throws BeerKnowledgeParserException {
        beerFactory.fromJson(json.getJsonObject("beerFactory"));
        breweryFactory.fromJson(json.getJsonObject("breweryFactory"));

        int breweriesCount = json.getInt("breweriesCount");
        while (breweriesCount-- > 0)
            breweries.add(breweryFactory.makeBreweryForRead());
        JsonUtils.fillJsonArray(json.getJsonArray("breweries"), breweries);

        int beersCount = json.getInt("beersCount");
        while (beersCount-- > 0)
            beers.add(beerFactory.makeBeerForRead());
        JsonUtils.fillJsonArray(json.getJsonArray("beers"), beers);
        
        rebuildDependencies();
    }

    public ArrayList<Brewery> getBreweries() {
        return breweries;
    }

    public Brewery makeBrewery() {
        Brewery brewery = breweryFactory.makeBrewery();
        breweries.add(brewery);
        return brewery;
    }

    public Beer makeBeer() {
        Beer beer = beerFactory.makeBeer();
        beers.add(beer);
        beer.addPropertyChangeListener(this);
        return beer;
    }

    public Collection<Beer> getBeersOf(Brewery brewery) {
        ArrayList<Beer> res = new ArrayList<>();
        for (Beer b: beers)
            if (b.getBreweryId() == brewery.getId())
                res.add(b);
        return res;
    }

    public void deleteBrewery(Brewery brewery) {
        breweries.remove(brewery);
    }

    private void rebuildDependencies() {
        // TODO
    }

    @Override
    public void propertyChanged(BeerPropertyChangeEvent propertyChange) {
        // TODO
    }

    public ArrayList<Beer> getBeersWithKeywords(ArrayList<StyleKeywords> keywords) {
        if (keywords.isEmpty())
            return (ArrayList<Beer>) beers.clone();
        
        // TODO do it the proper way
        ArrayList<Beer> result = new ArrayList<>();
        for (Beer beer: beers) {
            if (satisfies(beer, keywords))
                result.add(beer);
        }
        return result;
    }

    private boolean satisfies(Beer beer, ArrayList<StyleKeywords> keywords) {
        for (StyleKeywords keyword: keywords) {
            if (!beer.hasStyle(keyword))
                return false;
        }
        return true;
    }

    public Brewery getBreweryOfBeer(Beer beer) {
        //TODO do it right
        for (Brewery b: breweries) {
            if (beer.getBreweryId() == b.getId())
                return b;
        }
        throw new RuntimeException("Beer with no brewery");
    }
}
