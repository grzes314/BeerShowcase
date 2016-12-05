
package beershowcase.beerdata;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.json.Json;
import javax.json.JsonObject;

/**
 * Cl
 * @author Grzegorz Łoś
 */

class LazyImage implements JsonRepresentable {
    
    String localPath;
    transient BufferedImage picture;
    private static final Logger LOGGER = Logger.getLogger(LazyImage.class.getName());
    
    @Override
    public JsonObject toJson() {
        JsonObject value = Json.createObjectBuilder()
            .add("localPath", localPath)
            .build();
        return value;
    }

    @Override
    public void fromJson(JsonObject json) throws BeerKnowledgeParserException {
        localPath = json.getString("localPath");
    }

    public LazyImage(String localPath) {
        this.localPath = localPath;
    }
    
    public void set(BufferedImage picture) {
        this.picture = picture;
    }
          
    public BufferedImage getPicture() {
        try {
            if (picture == null)
                readPicture();
        } catch (IOException ex) {
            LOGGER.log(Level.INFO, null, ex);
        }
        return picture;
    }

    private void readPicture() throws IOException {
        File file = new File(localPath);
        picture = ImageIO.read(file);
    }

    public void save() {
        try {
            if (picture != null)
                    ImageIO.write(picture, "jpg", new File(localPath));
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Failed to save {0}", localPath);
        }
    }
    
    public void release() {
        picture = null;
    }

}