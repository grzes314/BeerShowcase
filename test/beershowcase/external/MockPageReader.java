
package beershowcase.external;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Grzegorz Łoś
 */
public class MockPageReader implements PageReader {

    private static final Logger LOGGER = Logger.getLogger(MockPageReader.class.getName());
    
    @Override
    public String read(String address) {
        return read((URL) null);
    }

    @Override
    public String read(URL ignoredParam) {
        URL url = MockPageReader.class.getResource("PolskiKraftExamplePage.html");
        try (BufferedReader reader = new BufferedReader(new FileReader(url.getPath()))) {
            StringBuilder sb = new StringBuilder();
            for (Object str: reader.lines().toArray())
                sb.append(str).append("\n");
            return sb.toString();
        } catch (IOException ex) {
            Logger.getLogger(MockPageReader.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }
    
}
