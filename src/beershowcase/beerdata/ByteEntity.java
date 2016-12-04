
package beershowcase.beerdata;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 *
 * @author grzes
 */
public interface ByteEntity {
    
    void addToByteStream(ByteArrayOutputStream out);
    
    void readFromByteStream(ByteArrayInputStream in);
}
