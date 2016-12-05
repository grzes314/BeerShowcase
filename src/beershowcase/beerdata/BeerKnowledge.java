
package beershowcase.beerdata;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;

/**
 * The BeerKnowledge class gathers the information about beers and breweries.
 * It's also a proxy in creation of new beers and breweries.
 * @author Grzegorz Łoś
 */
public class BeerKnowledge implements JsonRepresentable {
    private final BreweryFactory breweryFactory = new BreweryFactory();
    private final ArrayList<Brewery> breweries = new ArrayList<>();
    private final BeerFactory beerFactory = new BeerFactory();
    private final ArrayList<Beer> beers = new ArrayList<>();
    private Map<StyleKeywords, Collection<Beer>> styleToBeers;
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
    }

    public ArrayList<Brewery> getBreweries() {
        return breweries;
    }

    public Brewery makeBrewery() {
        Brewery brewery = breweryFactory.makeBrewery();
        breweries.add(brewery);
        return brewery;
    }

    public Collection<Beer> getBeersOf(Brewery brewery) {
        ArrayList<Beer> res = new ArrayList<>();
        for (Beer b: beers)
            if (b.getBrewery().getId() == brewery.getId())
                res.add(b);
        return res;
    }

    public void deleteBrewery(Brewery brewery) {
        breweries.remove(brewery);
    }
}
