
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
    void fillJsonArray(JsonArray jsonArray, ArrayList<T> array) throws BeerKnowledgeParserException {
        for (int i = 0; i < array.size(); ++i) {
            try {
                array.get(i).fromJson(jsonArray.getJsonObject(i));
            } catch (IndexOutOfBoundsException ex) {
                throw new BeerKnowledgeParserException(ex);
            }
        }
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
