
package beershowcase.beerdata;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 *
 * @author grzes
 */
public class BeerFactory implements ByteEntity {
    private long nextBeerId = 1;
    
    public Beer makeBeer() {
        return new Beer(nextBeerId++);
    }

    @Override
    public void addToByteStream(ByteArrayOutputStream out) {
        ByteUtils.addLongToByteStream(nextBeerId, out);
    }

    @Override
    public void readFromByteStream(ByteArrayInputStream in) {
        if (nextBeerId != 1)
            throw new RuntimeException("Initialization of a factory that has already been used");
        
        nextBeerId = ByteUtils.readLongFromByteStream(in);
    }    
}
