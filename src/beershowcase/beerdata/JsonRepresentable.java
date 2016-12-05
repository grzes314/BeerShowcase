
package beershowcase.beerdata;

import javax.json.JsonObject;

/**
 * Interface for classes whose objects can be represented in JSON.
 * @author Grzegorz Łoś
 */
public interface JsonRepresentable {
    JsonObject toJson();
    void fromJson(JsonObject json) throws BeerKnowledgeParserException;
}
