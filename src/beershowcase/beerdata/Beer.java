
package beershowcase.beerdata;

import beershowcase.beerdata.properties.BeerPropertyChangeListener;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 *
 * @author grzes
 */
public class Beer implements ByteEntity {
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

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean string) {
        this.available = string;
    }

    public Beer() {
        this.id = -1;
    }
    
    public Beer(long id) {
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

    @Override
    public void addToByteStream(ByteArrayOutputStream out) {
        ByteUtils.addLongToByteStream(id, out);
        ByteUtils.addStringToByteStream(name, out);
        ByteUtils.addLongToByteStream(brewery.getId(), out);
    }

    @Override
    public void readFromByteStream(ByteArrayInputStream in) {
        id = ByteUtils.readLongFromByteStream(in);
        name = ByteUtils.readStringFromByteStream(in);
        long breweryId = ByteUtils.readLongFromByteStream(in);
        // ???????????? how to read brewery
    }
    
    public void addPropertyChangeListener(BeerPropertyChangeListener bpcl) {
        changeListeners.add(bpcl);
    }
}
