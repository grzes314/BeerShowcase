
package beershowcase.beerdata;

import beershowcase.beerdata.properties.BeerPropertyChangeListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.json.Json;
import javax.json.JsonObject;

/**
 *
 * @author Grzegorz Łoś
 */
public class Beer implements JsonRepresentable {
    private int id;
    private String name;
    private int breweryId;
    private Ingredients ingredients = new Ingredients();
    private String descritpion = "";
    private boolean available = true;
    private final LazyImage labelImage = new LazyImage();
    private final LazyImage bottleImage = new LazyImage();
    private final ArrayList<StyleKeywords> keywords = new ArrayList<>();
    private final ArrayList<BeerPropertyChangeListener> changeListeners = new ArrayList<>();

    @Override
    public JsonObject toJson() {
        JsonObject value = Json.createObjectBuilder()
            .add("id", id)
            .add("name", name)
            .add("breweryId", breweryId)
            .add("descritpion", descritpion)
            .add("available", available)
            .add("keywords", JsonUtils.stringListToJson(getKeywordsAsStrings()))
            .build();
        return value;
    }

    @Override
    public void fromJson(JsonObject json) throws BeerKnowledgeParserException {
        id = json.getInt("id");
        name = json.getString("name");
        breweryId = json.getInt("breweryId");
        descritpion = json.getString("descritpion");
        available = json.getBoolean("available");
        addKeywords(JsonUtils.stringListFromJson(json.getJsonArray("keywords")));
        
        bottleImage.setImageName(makeBottleImageName());
        labelImage.setImageName(makeLabelImageName());
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
    
    Beer(int id) {
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

    public int getBreweryId() {
        return breweryId;
    }

    public void setBreweryId(int breweryId) {
        this.breweryId = breweryId;
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

    public BufferedImage getLabelImage() {
        return labelImage.getPicture();
    }

    public void setLabelImage(BufferedImage labelImage) {
        this.labelImage.set(labelImage);
        this.labelImage.setImageName(makeLabelImageName());
        this.labelImage.save();
    }

    public BufferedImage getBottleImage() {
        return bottleImage.getPicture();
    }

    public void setBottleImage(BufferedImage bottleImage) {
        this.bottleImage.set(bottleImage);
        this.bottleImage.setImageName(makeBottleImageName());
        this.bottleImage.save();
    }
    
    public void addPropertyChangeListener(BeerPropertyChangeListener bpcl) {
        changeListeners.add(bpcl);
    }

    private ArrayList<String> getKeywordsAsStrings() {
        ArrayList<String> arr = new ArrayList<>();
        for (StyleKeywords keyword: keywords)
            arr.add(keyword.name());
        return arr;
    }

    private void addKeywords(ArrayList<String> keywordStrings) throws BeerKnowledgeParserException {
        try {
            for (String keywordStr: keywordStrings)
                keywords.add(StyleKeywords.valueOf(keywordStr));
        } catch (IllegalArgumentException ex) {
            throw new BeerKnowledgeParserException(ex);
        }
    }
    
    private String makeBottleImageName() {
        return "bottle_" + id + ".jpg";
    }
    
    private String makeLabelImageName() {
        return "label_" + id + ".jpg";
    }
}
