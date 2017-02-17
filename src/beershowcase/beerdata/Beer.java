
package beershowcase.beerdata;

import beershowcase.lazyresources.LazyImage;
import beershowcase.lazyresources.LazyText;
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
     * Name of the beer.
     */
    private String name = "";
    
    /**
     * Style stated by the brewery.
     */
    private String declaredStyle = "";
    
    /**
     * Original gravity in blg units.
     */
    private FixedPointReal plato = new FixedPointReal(0,1);
    
    /**
     * Alcohol content by volume in percents.
     */
    private FixedPointReal abv = new FixedPointReal(0,1);
    
    /**
     * Bitternes in IBU.
     */
    private int ibu;
    
    /**
     * Commercial description of the beer.
     */
    public LazyText descritpion;
    
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
        descritpion = new LazyText(makeDescritpionPath());
        image = new LazyImage(makeLabelImagePath());
    }
    
    @Override
    public JsonObject toJson() {
        JsonObject value = Json.createObjectBuilder()
            .add("id", id)
            .add("breweryId", breweryId)
            .add("available", available)
            .add("price", price.toString())
            .add("name", name)
            .add("declaredStyle", declaredStyle)
            .add("plato", plato.toString())
            .add("abv", abv.toString())
            .add("ibu", ibu)
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
        name = json.getString("name");
        declaredStyle = json.getString("declaredStyle");
        plato = new FixedPointReal(json.getString("plato"));
        abv = new FixedPointReal(json.getString("abv"));
        ibu = json.getInt("ibu");
        addStyleKeywordsFromStrings(JsonUtils.stringListFromJson(json.getJsonArray("keywords")));
        
        descritpion = new LazyText(makeDescritpionPath());
        image = new LazyImage(makeLabelImagePath());
        fireEditionEvent(new EditionEvent(this));
    }
    
    public void saveChanges(FileSystem fileSystem) {
        descritpion.saveChanges(fileSystem);
        image.saveChanges(fileSystem);
    }
    
    public void saveAs(FileSystem currFileSystem, FileSystem newFileSystem) {
        descritpion.saveAs(currFileSystem, newFileSystem);
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
        name = beerProps.name;
        declaredStyle = beerProps.declaredStyle;
        plato = beerProps.plato;
        abv = beerProps.abv;
        ibu = beerProps.ibu;
        descritpion.setText(beerProps.descritpion);
        image.setPicture(beerProps.labelImage);
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        if (!name.equals(newName)) {
            name = newName;
            fireEditionEvent(new EditionEvent(this));
        }
    }

    public String getDeclaredStyle() {
        return declaredStyle;
    }

    public void setDeclaredStyle(String newDeclaredStyle) {
        if (!declaredStyle.equals(newDeclaredStyle)) {
            declaredStyle = newDeclaredStyle;
            fireEditionEvent(new EditionEvent(this));
        }
    }

    public FixedPointReal getPlato() {
        return plato;
    }
    
    public void setPlato(FixedPointReal newPlato) {
        if (!plato.equals(newPlato)) {
            plato = newPlato;
            fireEditionEvent(new EditionEvent(this));
        }
    }

    public FixedPointReal getAbv() {
        return abv;
    }
    
    public void setAbv(FixedPointReal newAbv) {
        if (!abv.equals(newAbv)) {
            abv = newAbv;
            fireEditionEvent(new EditionEvent(this));
        }
    }

    public int getIbu() {
        return ibu;
    }
    
    public void setIbu(int newIbu) {
        if (ibu != newIbu) {
            ibu = newIbu;
            fireEditionEvent(new EditionEvent(this));
        }
    }

    public String getDescritpion(FileSystem fileSystem) {
        return descritpion.getText(fileSystem);
    }

    public void setDescritpion(String newDescritpion) {
        descritpion.setText(newDescritpion);
        fireEditionEvent(new EditionEvent(this));
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
        for (StyleKeyword keyword: newKeywords)
            addStyleKeyword(keyword);
        fireEditionEvent(new EditionEvent(this));
    }

    private void addStyleKeywordsFromStrings(ArrayList<String> keywordStrings) throws BeerKnowledgeParserException {
        try {
            for (String keywordStr: keywordStrings)
                keywords.add(StyleKeyword.valueOf(keywordStr));
        } catch (IllegalArgumentException ex) {
            throw new BeerKnowledgeParserException(ex);
        }
    }
    
    private String makeDescritpionPath() {
        return "descriptions/" + id + ".txt";
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
