
package beershowcase.beerdata;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * The BeerKnowledge class gathers the information about beers and breweries.
 * It's also a proxy in creation of new beers and breweries.
 * @author Grzegorz Los
 */
public class BeerKnowledge implements ByteEntity {
    private BeerFactory beerFactory = new BeerFactory();
    private BreweryFactory breweryFactory = new BreweryFactory();
    private ArrayList<Brewery> breweries = new ArrayList<>();
    private ArrayList<Beer> beers = new ArrayList<>();
    private Map<StyleKeywords, Collection<Beer>> styleToBeers;

    @Override
    public void addToByteStream(ByteArrayOutputStream out) {
        breweryFactory.addToByteStream(out);
        ByteUtils.addCollectionToByteStream(breweries, out);
        beerFactory.addToByteStream(out);
        ByteUtils.addCollectionToByteStream(beers, out);
    }

    @Override
    public void readFromByteStream(ByteArrayInputStream in) {
        breweryFactory.readFromByteStream(in);
        ByteUtils.readCollectionFromByteStream(in, breweries, Brewery.class);
        beerFactory.readFromByteStream(in);
        ByteUtils.readCollectionFromByteStream(in, beers, Beer.class);
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
