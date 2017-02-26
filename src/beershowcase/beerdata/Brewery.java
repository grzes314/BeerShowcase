
package beershowcase.beerdata;

import beershowcase.lazyresources.LazyImage;
import beershowcase.utils.Box;
import java.awt.image.BufferedImage;
import java.nio.file.FileSystem;
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
    private LazyImage logo;
    
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
        logo = new LazyImage(makeLogoImagePath());
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
        logo = new LazyImage(makeLogoImagePath());
    }
    
    public void saveChanges(FileSystem fileSystem) {
        logo.saveChanges(fileSystem);
    }
    
    public void saveAs(FileSystem currFileSystem, FileSystem newFileSystem) {
        logo.saveAs(currFileSystem, newFileSystem);
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

    public Box<BufferedImage> getLogo(FileSystem fileSystem) {
        return logo.getPicture(fileSystem);
    }

    public void setLogo(BufferedImage logo) {
        this.logo.setPicture(logo);
        fireEditionEvent(new EditionEvent(this));
    }    
    
    private String makeLogoImagePath() {
        return "logos/" + id + ".jpg";
    }
}
