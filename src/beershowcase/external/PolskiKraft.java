
package beershowcase.external;

import beershowcase.beerdata.BeerProperties;

/**
 *
 * @author Grzegorz Łoś
 */
public class PolskiKraft implements ExternalSource {

    private PageReader pageReader = new DefaultPageReader();
    
    @Override
    public BeerProperties readFrom(String url) {
        String content = pageReader.read(url);
        return new BeerProperties();
    }

    public PageReader getPageReader() {
        return pageReader;
    }

    public void setPageReader(PageReader pageReader) {
        this.pageReader = pageReader;
    }
    
}
