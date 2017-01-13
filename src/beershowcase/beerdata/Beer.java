
package beershowcase.beerdata;

import java.awt.image.BufferedImage;
import java.nio.file.FileSystem;
import java.util.ArrayList;
import javax.json.Json;
import javax.json.JsonObject;

/**
 *
 * @author Grzegorz Łoś
 */
public class Beer implements JsonRepresentable {
    /**
     * Beer's unique id.
     */
    private int id = -1;
    
    /**
     * Unique id of the brewery that produce this beer.
     */
    private int breweryId = -1;
    
    /**
     * Is this beer available in the shop?
     */
    private boolean available = true;
    
    /**
     * Price in cents.
     */
    private int price;
    
    /**
     * Beer's properties.
     */
    private BeerProperties properties;
    
    private final ArrayList<ChangeListener> changeListeners = new ArrayList<>();

    public static class EditionEvent {
        public final Beer source;

        public EditionEvent(Beer source) {
            this.source = source;
        }
    }
    
    public static interface ChangeListener {
        void beerEdited(EditionEvent event);
    }

    public void addChangeListener(ChangeListener cl) {
        if (!changeListeners.contains(cl))
            changeListeners.add(cl);
    }

    public void removeChangeListener(ChangeListener cl) {
        changeListeners.remove(cl);
    }
    
    public void fireEditionEvent(EditionEvent event) {
        for (ChangeListener cl: changeListeners) {
            cl.beerEdited(event);
        }
    }
    
    @Override
    public JsonObject toJson() {
        JsonObject value = Json.createObjectBuilder()
            .add("id", id)
            .add("name", properties.name)
            .add("declaredStyle", properties.declaredStyle)
            .add("breweryId", breweryId)
            //.add("descritpion", descritpion)
            .add("available", available)
            .add("keywords", JsonUtils.stringListToJson(getKeywordsAsStrings()))
            .build();
        return value;
    }

    @Override
    public void fromJson(JsonObject json) throws BeerKnowledgeParserException {
        id = json.getInt("id");
        properties.name = json.getString("name");
        properties.declaredStyle = json.getString("declaredStyle");
        breweryId = json.getInt("breweryId");
        //descritpion = json.getString("descritpion");
        available = json.getBoolean("available");
        addStyleKeywordsFromStrings(JsonUtils.stringListFromJson(json.getJsonArray("keywords")));
        
        properties.labelImage = new LazyImage(makeLabelImagePath());
        properties.bottleImage = new LazyImage(makeBottleImagePath());
        fireEditionEvent(new EditionEvent(this));
    }
    
    public void saveChanges(FileSystem fileSystem) {
        properties.labelImage.saveIfChanged(fileSystem);
        properties.bottleImage.saveIfChanged(fileSystem);
    }
    
    public void saveForced(FileSystem fileSystem) {
        properties.labelImage.saveForced(fileSystem);
        properties.bottleImage.saveForced(fileSystem);
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
        properties.labelImage = new LazyImage(makeLabelImagePath());
        properties.bottleImage = new LazyImage(makeBottleImagePath());
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return properties.name;
    }

    public void setName(String newName) {
        if (!properties.name.equals(newName)) {
            properties.name = newName;
            fireEditionEvent(new EditionEvent(this));
        }
    }

    public String getDeclaredStyle() {
        return properties.declaredStyle;
    }

    public void setDeclaredStyle(String newDeclaredStyle) {
        if (!properties.declaredStyle.equals(newDeclaredStyle)) {
            properties.declaredStyle = newDeclaredStyle;
            fireEditionEvent(new EditionEvent(this));
        }
    }

    public int getBreweryId() {
        return breweryId;
    }

    public void setBreweryId(int newBrewery) {
        if (newBrewery != breweryId) {
            this.breweryId = newBrewery;
            fireEditionEvent(new EditionEvent(this));
        }
    }

    public Ingredients getIngredients() {
        return properties.ingredients;
    }

    public void setIngredients(Ingredients newIngredients) {
        if (!properties.ingredients.equals(newIngredients)) {
            fireEditionEvent(new EditionEvent(this));
            properties.ingredients = newIngredients;
        }
    }

    public String getDescritpion() {
        return properties.descritpion;
    }

    public void setDescritpion(String newDescritpion) {
        if (!properties.descritpion.equals(newDescritpion)) {
            properties.descritpion = newDescritpion;
            fireEditionEvent(new EditionEvent(this));
        }
    }

    public BufferedImage getLabelImage(FileSystem fileSystem) {
        return properties.labelImage.getPicture(fileSystem);
    }

    public void setLabelImage(BufferedImage newLabelImage) {
        properties.labelImage.setPicture(newLabelImage);
        fireEditionEvent(new EditionEvent(this));
    }

    public BufferedImage getBottleImage(FileSystem fileSystem) {
        return properties.bottleImage.getPicture(fileSystem);
    }

    public void setBottleImage(BufferedImage newBottleImage) {
        properties.bottleImage.setPicture(newBottleImage);
        fireEditionEvent(new EditionEvent(this));
    }
    
    public void addStyleKeyword(StyleKeyword keyword) {
        if (!properties.keywords.contains(keyword)) {
            properties.keywords.add(keyword);
            fireEditionEvent(new EditionEvent(this));
        }        
    }
    
    public void removeStyleKeyword(StyleKeyword keyword) {
        if (properties.keywords.contains(keyword)) {
            properties.keywords.remove(keyword);
            fireEditionEvent(new EditionEvent(this));
        }        
    }

    private ArrayList<String> getKeywordsAsStrings() {
        ArrayList<String> arr = new ArrayList<>();
        for (StyleKeyword keyword: properties.keywords)
            arr.add(keyword.name());
        return arr;
    }
    
    public ArrayList<StyleKeyword> getStyleKeywords() {
        return properties.keywords;
    }

    public void addStyleKeywords(ArrayList<StyleKeyword> newKeywords) {
        fireEditionEvent(new EditionEvent(this));
        for (StyleKeyword keyword: newKeywords)
            addStyleKeyword(keyword);
    }

    private void addStyleKeywordsFromStrings(ArrayList<String> keywordStrings) throws BeerKnowledgeParserException {
        try {
            for (String keywordStr: keywordStrings)
                properties.keywords.add(StyleKeyword.valueOf(keywordStr));
        } catch (IllegalArgumentException ex) {
            throw new BeerKnowledgeParserException(ex);
        }
    }
    
    private String makeBottleImagePath() {
        return "bottles/" + id + ".jpg";
    }
    
    private String makeLabelImagePath() {
        return "labels/" + id + ".jpg";
    }
    
    public boolean hasStyle(StyleKeyword keyword) {
        return properties.keywords.contains(keyword);
    }
}
