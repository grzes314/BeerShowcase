
package beershowcase.beerdata;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import javax.json.Json;
import javax.json.JsonObject;

/**
 *
 * @author grzes
 */
public class BeerFactory implements JsonRepresentable, ByteEntity {
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

    @Override
    public void addToByteStream(ByteArrayOutputStream out) {
        ByteUtils.addLongToByteStream(nextBeerId, out);
    }

    @Override
    public void readFromByteStream(ByteArrayInputStream in) {
        if (nextBeerId != 1)
            throw new RuntimeException("Initialization of a factory that has already been used");
        
        nextBeerId = ByteUtils.readLongFromByteStream(in);
    }    
}
