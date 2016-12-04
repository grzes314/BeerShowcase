
package beershowcase.beerdata;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 *
 * @author grzes
 */
public class Brewery implements ByteEntity {
    private long id;
    private String name;
    private BufferedImage logo;
    
    public Brewery() {
        id = -1;
    }

    public Brewery(long id) {
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

    public BufferedImage getLogo() {
        return logo;
    }

    public void setLogo(BufferedImage logo) {
        this.logo = logo;
    }

    @Override
    public void addToByteStream(ByteArrayOutputStream out) {
        ByteUtils.addLongToByteStream(id, out);
        ByteUtils.addStringToByteStream(name, out);
        if (logo != null) {
            ByteUtils.addIntToByteStream(1, out);
            ByteUtils.addImageToByteStream(logo, out);
        } else {
            ByteUtils.addIntToByteStream(0, out);
        }
        ByteUtils.addIntToByteStream(112358, out);
    }

    @Override
    public void readFromByteStream(ByteArrayInputStream in) {
        id = ByteUtils.readLongFromByteStream(in);
        name = ByteUtils.readStringFromByteStream(in);
        int hasLogo = ByteUtils.readIntFromByteStream(in);
        if (hasLogo == 1)
            logo = ByteUtils.readImageFromByteStream(in);
        int control = ByteUtils.readIntFromByteStream(in);
    }
    
    
}