
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
    private int id = -1;
    private String name = "";
    private String declaredStyle = "";
    private int breweryId = -1;
    private Ingredients ingredients = new Ingredients();
    private String descritpion = "";
    private boolean available = true;
    private final LazyImage labelImage = new LazyImage();
    private final LazyImage bottleImage = new LazyImage();
    private final ArrayList<StyleKeyword> keywords = new ArrayList<>();
    /**
     * Price in cents.
     */
    int price;
    /**
     * Density in 0.01 blg units.
     */
    int plato;
    /**
     * Bitternes in IBU.
     */
    int ibu;
    
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
        
        bottleImage.setPath(makeBottleImagePath());
        labelImage.setPath(makeLabelImagePath());
        fireEditionEvent(new EditionEvent(this));
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
        if (!name.equals(newName)) {
            this.name = newName;
            fireEditionEvent(new EditionEvent(this));
        }
    }

    public String getDeclaredStyle() {
        return declaredStyle;
    }

    public void setDeclaredStyle(String newDeclaredStyle) {
        if (!declaredStyle.equals(newDeclaredStyle)) {
            this.declaredStyle = declaredStyle;
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
        return ingredients;
    }

    public void setIngredients(Ingredients newIngredients) {
        if (!ingredients.equals(newIngredients)) {
            fireEditionEvent(new EditionEvent(this));
            this.ingredients = ingredients;
        }
    }

    public String getDescritpion() {
        return descritpion;
    }

    public void setDescritpion(String newDescritpion) {
        if (!descritpion.equals(newDescritpion)) {
            this.descritpion = descritpion;
            fireEditionEvent(new EditionEvent(this));
        }
    }

    public BufferedImage getLabelImage() {
        return labelImage.getPicture();
    }

    public void setLabelImage(BufferedImage newLabelImage) {
        if (labelImage.getPicture() != newLabelImage) {
            this.labelImage.set(newLabelImage);
            this.labelImage.setPath(makeLabelImagePath());
            this.labelImage.save();
            fireEditionEvent(new EditionEvent(this));
        }
    }

    public BufferedImage getBottleImage() {
        return bottleImage.getPicture();
    }

    public void setBottleImage(BufferedImage newBottleImage) {
        if (bottleImage.getPicture() != newBottleImage) {
            this.bottleImage.set(newBottleImage);
            this.bottleImage.setPath(makeBottleImagePath());
            this.bottleImage.save();
        fireEditionEvent(new EditionEvent(this));
        }
    }
    
    public void addStyleKeyword(StyleKeyword keyword) {
        fireEditionEvent(new EditionEvent(this));
        if (!keywords.contains(keyword)) {
            keywords.add(keyword);
        }        
    }
    
    public void removeStyleKeyword(StyleKeyword keyword) {
        fireEditionEvent(new EditionEvent(this));
        if (keywords.contains(keyword)) {
            keywords.remove(keyword);
        }        
    }

    private ArrayList<String> getKeywordsAsStrings() {
        ArrayList<String> arr = new ArrayList<>();
        for (StyleKeyword keyword: keywords)
            arr.add(keyword.name());
        return arr;
    }
    
    public ArrayList<StyleKeyword> getStyleKeywords() {
        return keywords;
    }

    public void addStyleKeywords(ArrayList<StyleKeyword> newKeywords) {
        fireEditionEvent(new EditionEvent(this));
        for (StyleKeyword keyword: newKeywords)
            addStyleKeyword(keyword);
    }

    private void addStyleKeywordsFromStrings(ArrayList<String> keywordStrings) throws BeerKnowledgeParserException {
        try {
            for (String keywordStr: keywordStrings)
                keywords.add(StyleKeyword.valueOf(keywordStr));
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
        return keywords.contains(keyword);
    }
}
