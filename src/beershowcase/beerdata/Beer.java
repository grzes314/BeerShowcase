
package beershowcase.beerdata;

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
    private String declaredStyle;
    private int breweryId;
    private Ingredients ingredients = new Ingredients();
    private String descritpion = "";
    private boolean available = true;
    private final LazyImage labelImage = new LazyImage();
    private final LazyImage bottleImage = new LazyImage();
    private final ArrayList<StyleKeywords> keywords = new ArrayList<>();

    @Override
    public JsonObject toJson() {
        JsonObject value = Json.createObjectBuilder()
            .add("id", id)
            .add("name", name)
            .add("declaredStyle", declaredStyle)
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
        declaredStyle = json.getString("declaredStyle");
        breweryId = json.getInt("breweryId");
        descritpion = json.getString("descritpion");
        available = json.getBoolean("available");
        addStyleKeywordsFromStrings(JsonUtils.stringListFromJson(json.getJsonArray("keywords")));
        
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

    public void setName(String newName) {
        String oldName = this.name;
        this.name = newName;
    }

    public String getDeclaredStyle() {
        return declaredStyle;
    }

    public void setDeclaredStyle(String declaredStyle) {
        this.declaredStyle = declaredStyle;
    }

    public int getBreweryId() {
        return breweryId;
    }

    public void setBreweryId(int newBrewery) {
        int oldBrewery = this.breweryId;
        this.breweryId = newBrewery;
        
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
        if (labelImage != null) {
            this.labelImage.set(labelImage);
            this.labelImage.setImageName(makeLabelImageName());
            this.labelImage.save();
        }
    }

    public BufferedImage getBottleImage() {
        return bottleImage.getPicture();
    }

    public void setBottleImage(BufferedImage bottleImage) {
        if (bottleImage != null) {
            this.bottleImage.set(bottleImage);
            this.bottleImage.setImageName(makeBottleImageName());
            this.bottleImage.save();
        }
    }
    
    public void addStyleKeyword(StyleKeywords keyword) {
        if (!keywords.contains(keyword)) {
            keywords.add(keyword);
        }        
    }
    
    public void removeStyleKeyword(StyleKeywords keyword) {
        if (keywords.contains(keyword)) {
            keywords.remove(keyword);
        }        
    }

    private ArrayList<String> getKeywordsAsStrings() {
        ArrayList<String> arr = new ArrayList<>();
        for (StyleKeywords keyword: keywords)
            arr.add(keyword.name());
        return arr;
    }

    public void addStyleKeywords(ArrayList<StyleKeywords> newKeywords) {
        for (StyleKeywords keyword: newKeywords)
            addStyleKeyword(keyword);
    }

    private void addStyleKeywordsFromStrings(ArrayList<String> keywordStrings) throws BeerKnowledgeParserException {
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
    
    public boolean hasStyle(StyleKeywords keyword) {
        return keywords.contains(keyword);
    }
}
