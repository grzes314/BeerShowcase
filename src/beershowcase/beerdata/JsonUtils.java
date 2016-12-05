
package beershowcase.beerdata;

import java.util.ArrayList;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;

/**
 *
 * @author Grzegorz Łoś
 */
class JsonUtils {

    public static <T extends JsonRepresentable>
    JsonArray toJsonArray(ArrayList<T> arrayList) {
        JsonArrayBuilder builder = Json.createArrayBuilder();
        for (T elem: arrayList)
            builder.add(elem.toJson());
        return builder.build();
    }

    public static <T extends JsonRepresentable>
    void fillJsonArray(JsonArray jsonArray, ArrayList<T> breweries) {
        for (int i = 0; i < breweries.size(); ++i) {
            breweries.get(i).fromJson(jsonArray.getJsonObject(i));
        }
    }
    
}
