
package beershowcase.beerdata;

import beershowcase.lazyresources.LazyImage;
import beershowcase.lazyresources.LazyText;
import beershowcase.utils.Box;
import beershowcase.utils.FixedPointReal;
import java.awt.image.BufferedImage;
import java.nio.file.FileSystem;
import java.util.ArrayList;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

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
    private final Box<FixedPointReal> price = new Box<>();
    
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
    private final Box<FixedPointReal> plato = new Box<>();
    
    /**
     * Alcohol content by volume in percents.
     */
    private final Box<FixedPointReal> abv = new Box<>();
    
    /**
     * Bitternes in IBU.
     */
    private final Box<FixedPointReal> ibu = new Box<>();
    
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
        JsonObjectBuilder job = Json.createObjectBuilder()
            .add("id", id)
            .add("breweryId", breweryId)
            .add("available", available)
            .add("name", name)
            .add("declaredStyle", declaredStyle)
            .add("keywords", JsonUtils.stringListToJson(getKeywordsAsStrings()));
        maybeAdd(job, "price", price);
        maybeAdd(job, "plato", plato);
        maybeAdd(job, "abv", abv);
        maybeAdd(job, "ibu", ibu);
        return job.build();
    }

    private void maybeAdd(JsonObjectBuilder job, String key, Box<FixedPointReal> box) {
        if (!box.isEmpty())
            job.add(key, box.getValue().toString());
    }

    @Override
    public void fromJson(JsonObject json) throws BeerKnowledgeParserException {
        id = json.getInt("id");
        breweryId = json.getInt("breweryId");
        available = json.getBoolean("available");
        
        price.setValue(JsonUtils.safeGet(json, "price", FixedPointReal.class));
        name = JsonUtils.safeGetString(json, "name");
        declaredStyle = JsonUtils.safeGetString(json, "declaredStyle");
        plato.setValue(JsonUtils.safeGet(json, "plato", FixedPointReal.class));
        abv.setValue(JsonUtils.safeGet(json, "abv", FixedPointReal.class));
        ibu.setValue(JsonUtils.safeGet(json, "ibu", FixedPointReal.class));
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
    public Box<FixedPointReal> getPrice() {
        return price;
    }
    
    /**
     * Sets the price of beer in cents.
     * @param price 
     */
    public void setPrice(FixedPointReal price) {
        setValue(this.price, price, 2);
    }
    
    private void setValue(Box<FixedPointReal> box, FixedPointReal newValue, int pointPos) {
        if (newValue == null)
            box.unpack();
        else
            box.setValue(newValue.toAnotherPointPos(pointPos));
        fireEditionEvent(new EditionEvent(this));
    }
    
    public void setProperties(BeerProperties beerProps) {
        name = beerProps.name;
        declaredStyle = beerProps.declaredStyle;
        setValue(plato, beerProps.plato, 1);
        setValue(abv, beerProps.abv, 1);
        setValue(ibu, beerProps.ibu, 0);
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

    public Box<FixedPointReal> getPlato() {
        return plato;
    }
    
    public void setPlato(FixedPointReal newPlato) {
        setValue(plato, newPlato, 1);
    }

    public Box<FixedPointReal> getAbv() {
        return abv;
    }
    
    public void setAbv(FixedPointReal newAbv) {
        setValue(abv, newAbv, 1);
    }

    public Box<FixedPointReal> getIbu() {
        return ibu;
    }
    
    public void setIbu(FixedPointReal newIbu) {
        setValue(ibu, newIbu, 0);
    }

    public Box<String> getDescritpion(FileSystem fileSystem) {
        return descritpion.getText(fileSystem);
    }

    public void setDescritpion(String newDescritpion) {
        descritpion.setText(newDescritpion);
        fireEditionEvent(new EditionEvent(this));
    }

    public Box<BufferedImage> getLabelImage(FileSystem fileSystem) {
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
