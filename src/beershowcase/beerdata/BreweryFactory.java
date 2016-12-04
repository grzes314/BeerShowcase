
package beershowcase.beerdata;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 *
 * @author grzes
 */
public class BreweryFactory  implements ByteEntity {
    private long nextBreweryId = 1;
    
    public Brewery makeBrewery() {
        return new Brewery(nextBreweryId++);
    }

    @Override
    public void addToByteStream(ByteArrayOutputStream out) {
        ByteUtils.addLongToByteStream(nextBreweryId, out);
    }

    @Override
    public void readFromByteStream(ByteArrayInputStream in) {
        if (nextBreweryId != 1)
            throw new RuntimeException("Initialization of a factory that has already been used");
        
        nextBreweryId = ByteUtils.readLongFromByteStream(in);
    }    
}