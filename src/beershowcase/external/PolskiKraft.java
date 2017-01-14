
package beershowcase.external;

import beershowcase.beerdata.BeerProperties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Grzegorz Łoś
 */
public class PolskiKraft implements ExternalSource {

    private PageReader pageReader = new DefaultPageReader();
    String content;
    
    @Override
    public BeerProperties readFrom(String url) {
        content = pageReader.read(url);
        BeerProperties props = new BeerProperties();
        props.name = readName();
        props.breweryName = readBrewery();
        props.declaredStyle = readDeclaredStyle();
        props.abv = readAbv();
        props.ibu = readIbu();
        props.plato = readBlg();
        content = null;
        return props;
    }

    PageReader getPageReader() {
        return pageReader;
    }

    void setPageReader(PageReader pageReader) {
        this.pageReader = pageReader;
    }
    
    private String getElementContent(String tagType, String wholeMarkup) {
        return getElementContent(tagType, wholeMarkup, 0);
    }
    
    private String getElementContent(String tagType, String wholeMarkup, int start) {
        try {
            int markupStart = findIndex(wholeMarkup, start);
            int contentStart = findIndex(">", markupStart) + 1;
            int contentEnd = findIndex("</" + tagType +">", markupStart);
            //int contentLength = contentEnd - contentStart;
            return content.substring(contentStart, contentEnd);
        } catch (Exception ex) {
            Logger.getLogger(PolskiKraft.class.getName()).log(
                    Level.WARNING, "Failed to read {0}", wholeMarkup);
            return "";
        }
    }
    
    private int findIndex(String substr, int start) throws Exception {
        int i = content.indexOf(substr, start);
        if (i < 0)
            throw new Exception("Substring not found");
        return i;
    }
    
    private String readName() {
        return getElementContent("p", "<p class=\"beer-name\">").trim();
    }
    
    private String readBrewery() {
        return getElementContent("p", "<p class=\"brewery-name\">").trim();
    }
    
    private String readDeclaredStyle() {
        try {
            int i = findIndex("<h3>STYL</h3>", 0);
            return getElementContent("p", "<p", i).trim();
        } catch (Exception ex) {
            Logger.getLogger(PolskiKraft.class.getName()).log(
                    Level.WARNING, "Failed to read <h3>STYL</h3>");
            return "";
        }
    }

    private int readAbv() {
        try {
            String doubleStr = getElementContent("h1", "<h1 id=\"amount-alc-s\">");
            Double d = 10 * Double.parseDouble(doubleStr);
            return d.intValue();
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    private int readIbu() {
        try {
            String intStr = getElementContent("h1", "<h1 id=\"amount-ibu-s\">");
            return Integer.parseInt(intStr);
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    private int readBlg() {
        try {
            String doubleStr = getElementContent("h1", "<h1 id=\"amount-blg-s\">");
            Double d = 10 * Double.parseDouble(doubleStr);
            return d.intValue();
        } catch (NumberFormatException ex) {
            return 0;
        }
    }
}
