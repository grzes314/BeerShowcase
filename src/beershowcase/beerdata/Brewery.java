
package beershowcase.beerdata;

import java.awt.image.BufferedImage;
import javax.json.Json;
import javax.json.JsonObject;

/**
 *
 * @author Grzegorz Łoś
 */
public class Brewery implements JsonRepresentable {
    private int id;
    private String name;
    private LazyImage logo = new LazyImage();
    
    @Override
    public JsonObject toJson() {
        JsonObject value = Json.createObjectBuilder()
            .add("id", id)
            .add("name", name)
            .build();
        return value;
    }

    @Override
    public void fromJson(JsonObject json) throws BeerKnowledgeParserException {
        id = json.getInt("id");
        name = json.getString("name");
        
        logo.setImageName(makeLogoImageName());
    }
    
    public Brewery() {
        id = -1;
    }

    public Brewery(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BufferedImage getLogo() {
        return logo.getPicture();
    }

    public void setLogo(BufferedImage logo) {
        this.logo.set(logo);
        this.logo.setImageName(makeLogoImageName());
        this.logo.save();
    }    
    
    private String makeLogoImageName() {
        return "brewery_" + id + ".jpg";
    }
}
