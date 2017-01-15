
package beershowcase.beerdata;

import beershowcase.beerdata.filters.Filter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
 * The BeerKnowledge class gathers the information about beers and breweries.
 * It's also a proxy in creation of new beers and breweries.
 * @author Grzegorz Łoś
 */
public class BeerKnowledge implements JsonRepresentable,
        Beer.ChangeListener, Brewery.ChangeListener {
    private final BreweryFactory breweryFactory = new BreweryFactory();
    private final BeerFactory beerFactory = new BeerFactory();
    private final ArrayList<Beer> beers = new ArrayList<>();
    private final Map<Integer, Brewery> breweryById = new HashMap<>();
    private final Brewery unknown = new Brewery();
    private boolean modified = false;
    
    private final ArrayList<ChangeListener> changeListeners = new ArrayList<>();

    public BeerKnowledge() {
        unknown.setName("Unknown");
    }
    
    static final Logger LOGGER = Logger.getLogger(BeerKnowledge.class.getName());

    public boolean isModified() {
        return modified;
    }

    @Override
    public void beerEdited(Beer.EditionEvent event) {
        modified = true;
        fireChangeEvent(new ChangeEvent(ChangeType.Edition, event.source));
    }

    @Override
    public void breweryEdited(Brewery.EditionEvent event) {
        modified = true;
        fireChangeEvent(new ChangeEvent(ChangeType.Edition, event.source));
    }


    public static enum ChangeType {
        Addition, Removal, Edition
    }
    
    public static class ChangeEvent {
        public final ChangeType changeType;
        public final Object affectedObject;

        public ChangeEvent(ChangeType changeType, Object affectedObject) {
            this.changeType = changeType;
            this.affectedObject = affectedObject;
        }
    }
    
    public static interface ChangeListener {
        void knowledgeChanged(ChangeEvent event);
    }

    public void addChangeListener(ChangeListener cl) {
        if (!changeListeners.contains(cl))
            changeListeners.add(cl);
    }

    public void removeChangeListener(ChangeListener cl) {
        changeListeners.remove(cl);
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
        while (breweriesCount-- > 0) {
            Brewery brewery = breweryFactory.makeBreweryForRead();
            breweries.add(brewery);
            brewery.addChangeListener(this);
        }
        JsonUtils.fromJsonArray(json.getJsonArray("breweries"), breweries);
        for (Brewery br: breweries)
            breweryById.put(br.getId(), br);

        int beersCount = json.getInt("beersCount");
        while (beersCount-- > 0) {
            Beer beer = beerFactory.makeBeerForRead();
            beers.add(beer);
            beer.addChangeListener(this);
        }
        JsonUtils.fromJsonArray(json.getJsonArray("beers"), beers);
    }
    
    public void load(FileSystem fileSystem) throws IOException, BeerKnowledgeParserException {
        Path path = fileSystem.getPath("BeerKnowledge.json");
        try (   BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
                JsonReader jsonReader = Json.createReader(reader)) {
            JsonObject json = jsonReader.readObject();
            fromJson(json);
        }
    }
    
    public void saveChanges(FileSystem fileSystem) throws IOException {
        saveJson(fileSystem);
        for (Brewery br: breweryById.values())
            br.saveChanges(fileSystem);
        for (Beer b: beers)
            b.saveChanges(fileSystem);
        modified = false;
    }
    
    public void saveEverything(FileSystem fileSystem) throws IOException {
        saveJson(fileSystem);
        for (Brewery br: breweryById.values())
            br.saveForced(fileSystem);
        for (Beer b: beers)
            b.saveForced(fileSystem);
        
        modified = false;
    }
    
    private void saveJson(FileSystem fileSystem) throws IOException {
        Path path = fileSystem.getPath("BeerKnowledge.json");
        try (BufferedWriter writer = Files.newBufferedWriter(path,
                StandardCharsets.UTF_8, StandardOpenOption.CREATE)) {
            JsonObject json = toJson();
            String jsonString = json.toString();
            writer.write(jsonString, 0, jsonString.length());            
        }
    }

    public Collection<Brewery> getBreweries() {
        return breweryById.values();
    }
    
    public Brewery getBreweryByName(String name) {
        for (Brewery br: breweryById.values()) {
            if (br.getName().equalsIgnoreCase(name))
                return br;
        }
        return null;
    }

    public Brewery makeBrewery() {
        Brewery brewery = breweryFactory.makeBrewery();
        breweryById.put(brewery.getId(), brewery);
        fireChangeEvent(new ChangeEvent(ChangeType.Addition, brewery));
        modified = true;
        return brewery;
    }

    public Beer makeBeer() {
        Beer beer = beerFactory.makeBeer();
        beers.add(beer);
        fireChangeEvent(new ChangeEvent(ChangeType.Addition, beer));
        modified = true;
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
        modified = true;
        fireChangeEvent(new ChangeEvent(ChangeType.Removal, brewery));
    }

    public ArrayList<Beer> getBeers(Filter filter) {
        ArrayList<Beer> result = new ArrayList<>();
        for (Beer beer: beers) {
            if (filter.filter(beer))
                result.add(beer);
        }
        return result;
    }
    public ArrayList<Beer> getBeers() {
        ArrayList<Beer> result = new ArrayList<>();
        for (Beer beer: beers) {
            result.add(beer);
        }
        return result;
    }

    public Brewery getBreweryOfBeer(Beer beer) {
        int brId = beer.getBreweryId();
        if (brId <= 0)
            return unknown;
        Brewery brewery = breweryById.get(brId);
        if (brewery != null)
            return brewery;
        else
            throw new RuntimeException("No brewery for beer " + beer.getId());
    }

    public void deleteBeer(Beer beer) {
        beers.remove(beer);
        modified = true;
        fireChangeEvent(new ChangeEvent(ChangeType.Removal, beer));
    }

}
