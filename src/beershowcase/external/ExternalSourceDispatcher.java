
package beershowcase.external;

import beershowcase.beerdata.BeerProperties;
import java.net.URI;
import java.net.URISyntaxException;

/**
 *
 * @author Grzegorz Łoś
 */
public class ExternalSourceDispatcher implements ExternalSource {

    PolskiKraft polskiKraft = new PolskiKraft();
    
    @Override
    public BeerProperties readFrom(String url) {
        try {
            if (!url.startsWith("http"))
                url = "http://" + url;
            URI uri = new URI(url);
            String domain = uri.getHost();
            if (domain.startsWith("www."))
                domain = domain.substring(4);
            return dispatch(domain, url);
        } catch (URISyntaxException ex) {
            return null;
        }
    }

    private BeerProperties dispatch(String domain, String url) {
        /*if (domain.startsWith("polskikraft"))
            return polskiKraft.readFrom(url);
        else
            return null;*/
        return polskiKraft.readFrom(url);
    }
    
}
