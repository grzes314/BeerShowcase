
package beershowcase.external;

import java.net.URL;

/**
 * Interface for classes that fetch contents of WWW pages. 
 * @author Grzegorz Łoś
 */
public interface PageReader {
    
    String read(String address);
    
    String read(URL url);

}
