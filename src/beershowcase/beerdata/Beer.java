
package beershowcase.beerdata;

import beershowcase.lazyresources.LazyImage;
import beershowcase.utils.FixedPointReal;
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
     * Price.
     */
    private FixedPointReal price = new FixedPointReal(0,2);
    
    /**
     * Beer's properties.
     */
    private BeerProperties properties = new BeerProperties();
    
    /**
     * Beer's or beer label's picture.
     */
    private LazyImage image;
    
    /**
     * Style keywords associated with this beer.
     */
    private final ArrayList<StyleKeyword> keywords = new ArrayList<>();
    
    /**
     * List of objects following beer's changes.
     */
    private final ArrayList<ChangeListener> changeListeners = new ArrayList<>();
    
    /**
     * Constructs object with an "invalid state". Must be followed by 
     * fromJson method which will initialize it properly. Package access as
     * Beer objects should be create only by factories.
     */
    Beer() {
        this.id = -1;
    }
    
    /**
     * Constructs a new beer. Package access as
     * Beer objects should be create only by factories.
     * @param id beer's unique id.
     */
    Beer(int id) {
        this.id = id;
        image = new LazyImage(makeLabelImagePath());
    }
    
    @Override
    public JsonObject toJson() {
        JsonObject value = Json.createObjectBuilder()
            .add("id", id)
            .add("breweryId", breweryId)
            .add("available", available)
            .add("price", price.toString())
            .add("name", properties.name)
            .add("declaredStyle", properties.declaredStyle)
            .add("plato", properties.plato.toString())
            .add("abv", properties.abv.toString())
            .add("ibu", properties.ibu)
            //.add("descritpion", descritpion)
            .add("keywords", JsonUtils.stringListToJson(getKeywordsAsStrings()))
            .build();
        return value;
    }

    @Override
    public void fromJson(JsonObject json) throws BeerKnowledgeParserException {
        id = json.getInt("id");
        breweryId = json.getInt("breweryId");
        available = json.getBoolean("available");
        price = new FixedPointReal(json.getString("price"));
        properties.name = json.getString("name");
        properties.declaredStyle = json.getString("declaredStyle");
        properties.plato = new FixedPointReal(json.getString("plato"));
        properties.abv = new FixedPointReal(json.getString("abv"));
        properties.ibu = json.getInt("ibu");
        //descritpion = json.getString("descritpion");
        addStyleKeywordsFromStrings(JsonUtils.stringListFromJson(json.getJsonArray("keywords")));
        
        image = new LazyImage(makeLabelImagePath());
        fireEditionEvent(new EditionEvent(this));
    }
    
    public void saveChanges(FileSystem fileSystem) {
        image.saveChanges(fileSystem);
    }
    
    public void saveAs(FileSystem currFileSystem, FileSystem newFileSystem) {
        image.saveAs(currFileSystem, newFileSystem);
    }

    public int getId() {
        return id;
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
    
    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean string) {
        this.available = string;
    }
    
    /**
     * Returns beer's price in cents.
     * @return 
     */
    public FixedPointReal getPrice() {
        return price;
    }
    
    /**
     * Sets the price of beer in cents.
     * @param price 
     */
    public void setPrice(FixedPointReal price) {
        this.price = price;
    }
    
    public void setProperties(BeerProperties beerProps) {
        properties = new BeerProperties(beerProps);
        image.setPicture(properties.labelImage);
        properties.labelImage = null;
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

    public FixedPointReal getPlato() {
        return properties.plato;
    }
    
    public void setPlato(FixedPointReal plato) {
        if (!properties.plato.equals(plato)) {
            properties.plato = plato;
            fireEditionEvent(new EditionEvent(this));
        }
    }

    public FixedPointReal getAbv() {
        return properties.abv;
    }
    
    public void setAbv(FixedPointReal abv) {
        if (!properties.abv.equals(abv)) {
            properties.abv = abv;
            fireEditionEvent(new EditionEvent(this));
        }
    }

    public int getIbu() {
        return properties.ibu;
    }
    
    public void setIbu(int ibu) {
        if (properties.ibu != ibu) {
            properties.ibu = ibu;
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
        return image.getPicture(fileSystem);
    }

    public void setLabelImage(BufferedImage newLabelImage) {
        image.setPicture(newLabelImage);
        fireEditionEvent(new EditionEvent(this));
    }
    
    public void addStyleKeyword(StyleKeyword keyword) {
        if (!keywords.contains(keyword)) {
            keywords.add(keyword);
            fireEditionEvent(new EditionEvent(this));
        }        
    }
    
    public void removeStyleKeyword(StyleKeyword keyword) {
        if (keywords.contains(keyword)) {
            keywords.remove(keyword);
            fireEditionEvent(new EditionEvent(this));
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
    
    private String makeLabelImagePath() {
        return "labels/" + id + ".jpg";
    }
    
    public boolean hasStyle(StyleKeyword keyword) {
        return keywords.contains(keyword);
    }
    
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
}
