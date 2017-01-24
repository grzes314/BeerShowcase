
package beershowcase.external;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Grzegorz Łoś
 */
public class DefaultPageReader implements PageReader {
    private final static Logger LOGGER = Logger.getLogger(DefaultPageReader.class.getName());

    @Override
    public String read(String address) {
        try {
            URL url = new URL(address);
            return read(url);
        } catch (MalformedURLException ex) {
            LOGGER.log(Level.FINE, null, ex);
            return null;
        }
    }
    
    @Override
    public String read(URL url) {
        try {
            URLConnection conn = url.openConnection();
            conn.connect();
            return readPage(conn);
        } catch (IOException ex) {
            LOGGER.log(Level.WARNING, null, ex);
            return null;
        }
    }

    private String readPage(URLConnection conn) throws IOException {
        StringBuilder text;
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader( conn.getInputStream() ) )) {
            text = new StringBuilder("");
            String line;
            while ( (line = in.readLine()) != null)
                text.append(line).append("\n");
        }
        return text.toString();
    }
}
