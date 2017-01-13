
package beershowcase.external;

import beershowcase.beerdata.BeerProperties;

/**
 *
 * @author grzes
 */
public interface ExternalSource {
    BeerProperties readFrom(String url);
}
