
package beershowcase.beerdata;

import java.util.ArrayList;
import java.util.Collection;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;

/**
 *
 * @author Grzegorz Łoś
 */
class JsonUtils {

    public static <T extends JsonRepresentable>
    JsonArray toJsonArray(Collection<T> coll) {
        JsonArrayBuilder builder = Json.createArrayBuilder();
        for (T elem: coll)
            builder.add(elem.toJson());
        return builder.build();
    }

    public static <T extends JsonRepresentable>
    void fromJsonArray(JsonArray jsonArray, Collection<T> coll) throws BeerKnowledgeParserException {
        int i = 0;
        for (T elem: coll) {
            try {
                elem.fromJson(jsonArray.getJsonObject(i));
                i++;
            } catch (IndexOutOfBoundsException ex) {
                throw new BeerKnowledgeParserException(ex);
            }
        }
        if (i < coll.size())
            throw new BeerKnowledgeParserException("JsonArray contained less"
                    + " objects than Java collection");
    }
    
    public static JsonArray stringListToJson(ArrayList<String> stringList) {
        JsonArrayBuilder builder = Json.createArrayBuilder();
        for (String str: stringList)
            builder.add(str);
        return builder.build();
    }
    
    public static ArrayList<String> stringListFromJson(JsonArray jsonArray) {
        ArrayList<String> array = new ArrayList<>();
        for (int i = 0; ; ++i) {
            try {
                array.add(jsonArray.getString(i));
            } catch (IndexOutOfBoundsException ex) {
                return array;
            }
        }
        //throw new RuntimeException("Execution flow should not reach this statement.");
    }
}
