
package beershowcase.beerdata;

import javax.json.Json;
import javax.json.JsonObject;

/**
 *
 * @author Grzegorz Łoś
 */
public class BreweryFactory  implements JsonRepresentable {
    private int nextBreweryId = 1;
    
    @Override
    public JsonObject toJson() {
        JsonObject value = Json.createObjectBuilder()
             .add("nextBreweryId", nextBreweryId)
             .build();
        return value;
    }

    @Override
    public void fromJson(JsonObject json) {
        nextBreweryId = json.getInt("nextBreweryId");
    }
    
    public Brewery makeBrewery() {
        return new Brewery(nextBreweryId++);
    }
    
    public Brewery makeBreweryForRead() {
        return new Brewery();
    }  
}