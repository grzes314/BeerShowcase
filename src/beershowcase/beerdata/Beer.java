
package beershowcase.beerdata;

import beershowcase.beerdata.properties.BeerPropertyChangeListener;
import java.awt.image.RenderedImage;
import java.util.ArrayList;
import javax.json.Json;
import javax.json.JsonObject;

/**
 *
 * @author grzes
 */
public class Beer implements JsonRepresentable {
    private long id;
    private String name;
    private Brewery brewery;
    private Ingredients ingredients;
    private String descritpion;
    private boolean available;
    private RenderedImage labelImage;
    private RenderedImage bottleImage;
    private ArrayList<StyleKeywords> keywords = new ArrayList<>();
    private ArrayList<BeerPropertyChangeListener> changeListeners = new ArrayList<>();

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
    
    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean string) {
        this.available = string;
    }

    Beer() {
        this.id = -1;
    }
    
    Beer(long id) {
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

    public Brewery getBrewery() {
        return brewery;
    }

    public void setBrewery(Brewery brewery) {
        this.brewery = brewery;
    }

    public Ingredients getIngredients() {
        return ingredients;
    }

    public void setIngredients(Ingredients ingredients) {
        this.ingredients = ingredients;
    }

    public String getDescritpion() {
        return descritpion;
    }

    public void setDescritpion(String descritpion) {
        this.descritpion = descritpion;
    }

    public RenderedImage getLabelImage() {
        return labelImage;
    }

    public void setLabelImage(RenderedImage labelImage) {
        this.labelImage = labelImage;
    }

    public RenderedImage getBottleImage() {
        return bottleImage;
    }

    public void setBottleImage(RenderedImage bottleImage) {
        this.bottleImage = bottleImage;
    }
    
    public void addPropertyChangeListener(BeerPropertyChangeListener bpcl) {
        changeListeners.add(bpcl);
    }
}
