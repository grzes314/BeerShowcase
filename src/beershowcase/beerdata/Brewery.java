
package beershowcase.beerdata;

import java.awt.image.BufferedImage;
import javax.json.Json;
import javax.json.JsonObject;

/**
 *
 * @author grzes
 */
public class Brewery implements JsonRepresentable {
    private long id;
    private String name;
    private BufferedImage logo;
    

    @Override
    public JsonObject toJson() {
        JsonObject value = Json.createObjectBuilder()
            .add("id", id)
            .add("name", name)
            .build();
        return value;
    }

    @Override
    public void fromJson(JsonObject json) {
        id = json.getInt("id");
        name = json.getString("name");
    }
    
    public Brewery() {
        id = -1;
    }

    public Brewery(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BufferedImage getLogo() {
        return logo;
    }

    public void setLogo(BufferedImage logo) {
        this.logo = logo;
    }    
}
