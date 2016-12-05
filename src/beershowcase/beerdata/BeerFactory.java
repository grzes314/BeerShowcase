
package beershowcase.beerdata;

import javax.json.Json;
import javax.json.JsonObject;

/**
 *
 * @author grzes
 */
public class BeerFactory implements JsonRepresentable {
    private long nextBeerId = 1;

    @Override
    public JsonObject toJson() {
        JsonObject value = Json.createObjectBuilder()
             .add("nextBeerId", nextBeerId)
             .build();
        return value;
    }

    @Override
    public void fromJson(JsonObject json) {
        nextBeerId = json.getInt("nextBeerId");
    }
    
    public Beer makeBeer() {
        return new Beer(nextBeerId++);
    }
    
    Beer makeBeerForRead() {
        return new Beer();
    } 
}
