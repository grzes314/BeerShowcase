
package beershowcase.beerdata;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.json.Json;
import javax.json.JsonObject;

/**
 *
 * @author Grzegorz Łoś
 */
public class Brewery implements JsonRepresentable {
    private int id;
    private String name = "";
    private LazyImage logo = new LazyImage(makeLogoImagePath());
    BufferedImage noLogo = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
    
    private final ArrayList<ChangeListener> changeListeners = new ArrayList<>();
    
    public static class EditionEvent {
        public final Brewery source;

        public EditionEvent(Brewery source) {
            this.source = source;
        }
    }
    
    public static interface ChangeListener {
        void breweryEdited(EditionEvent event);
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
            cl.breweryEdited(event);
        }
    }
    
    public Brewery() {
        id = -1;
    }

    public Brewery(int id) {
        this.id = id;
    }
    
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
    }
    
    public void saveChanges() {
        logo.saveIfChanged();
    }
    
    public void saveForced() {
        logo.saveForced();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        fireEditionEvent(new EditionEvent(this));
    }

    public BufferedImage getLogo() {
        BufferedImage img = logo.getPicture();
        return img == null ? noLogo : img;
    }

    public void setLogo(BufferedImage logo) {
        this.logo.setPicture(logo);
        fireEditionEvent(new EditionEvent(this));
    }    
    
    private String makeLogoImagePath() {
        return "logos/" + id + ".jpg";
    }
}
