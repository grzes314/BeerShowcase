
package beershowcase.beerdata;

import beershowcase.beerdata.filters.Filter;
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
public class BeerKnowledge implements JsonRepresentable {
    private final BreweryFactory breweryFactory = new BreweryFactory();
    private final BeerFactory beerFactory = new BeerFactory();
    private final ArrayList<Beer> beers = new ArrayList<>();
    private final Map<Integer, Brewery> breweryById = new HashMap<>();
    
    private final ArrayList<ChangeListener> changeListeners = new ArrayList<>();
    
    static final Logger LOGGER = Logger.getLogger(BeerKnowledge.class.getName());

    public static enum ChangeType {
        Addition, Removal
    }
    
    public static class ChangeEvent {
        public final ChangeType changeType;
        public final Object affectedObject;
        public final Class objectsClass;

        public ChangeEvent(ChangeType changeType, Object affectedObject, Class objectsClass) {
            this.changeType = changeType;
            this.affectedObject = affectedObject;
            this.objectsClass = objectsClass;
        }
    }
    
    public static interface ChangeListener {
        void knowledgeChanged(ChangeEvent event);
    }

    public boolean addChangeListener(ChangeListener cl) {
        return changeListeners.add(cl);
    }

    public boolean removeChangeListener(ChangeListener cl) {
        return changeListeners.remove(cl);
    }
    
    public void fireChangeEvent(ChangeEvent event) {
        for (ChangeListener cl: changeListeners) {
            cl.knowledgeChanged(event);
        }
    }

    @Override
    public JsonObject toJson() {
        JsonObject value = Json.createObjectBuilder()
            .add("breweryFactory", breweryFactory.toJson())
            .add("breweriesCount", breweryById.size())
            .add("breweries", JsonUtils.toJsonArray(breweryById.values()))
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

        ArrayList<Brewery> breweries = new ArrayList<>();
        int breweriesCount = json.getInt("breweriesCount");
        while (breweriesCount-- > 0)
            breweries.add(breweryFactory.makeBreweryForRead());
        JsonUtils.fromJsonArray(json.getJsonArray("breweries"), breweries);
        for (Brewery br: breweries)
            breweryById.put(br.getId(), br);

        int beersCount = json.getInt("beersCount");
        while (beersCount-- > 0) {
            Beer beer = beerFactory.makeBeerForRead();
            beers.add(beer);
        }
        JsonUtils.fromJsonArray(json.getJsonArray("beers"), beers);
    }

    public Collection<Brewery> getBreweries() {
        return breweryById.values();
    }

    public Brewery makeBrewery() {
        Brewery brewery = breweryFactory.makeBrewery();
        breweryById.put(brewery.getId(), brewery);
        fireChangeEvent(new ChangeEvent(ChangeType.Addition, brewery, Brewery.class));
        return brewery;
    }

    public Beer makeBeer() {
        Beer beer = beerFactory.makeBeer();
        beers.add(beer);
        fireChangeEvent(new ChangeEvent(ChangeType.Addition, beer, Beer.class));
        return beer;
    }

    public Collection<Beer> getBeersOf(Brewery brewery) {
        ArrayList<Beer> res = new ArrayList<>();
        for (Beer b: beers)
            if (b.getBreweryId() == brewery.getId())
                res.add(b);
        return res;
    }

    public void deleteBrewery(Brewery brewery) throws BeerKnowledgeException {
        for (Beer beer: beers) {
            if (beer.getBreweryId() == brewery.getId())
                throw new BeerKnowledgeException("Can't delete the selected brewery."
                        + " There are beers related to it.");
        }
        breweryById.remove(brewery.getId());
        fireChangeEvent(new ChangeEvent(ChangeType.Removal, brewery, Brewery.class));
    }

    public ArrayList<Beer> getBeers(Filter filter) {
        ArrayList<Beer> result = new ArrayList<>();
        for (Beer beer: beers) {
            if (filter.filter(beer))
                result.add(beer);
        }
        return result;
    }

    public Brewery getBreweryOfBeer(Beer beer) {
        Brewery brewery = breweryById.get(beer.getBreweryId());
        if (brewery != null)
            return brewery;
        else
            throw new RuntimeException("No brewery for beer " + beer.getId());
    }

}
