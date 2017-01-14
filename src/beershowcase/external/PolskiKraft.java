
package beershowcase.external;

import beershowcase.beerdata.BeerProperties;
import beershowcase.beerdata.autostyle.StyleFinder;
import beershowcase.beerdata.autostyle.simple.SimpleStyleFinder;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Grzegorz Łoś
 */
public class PolskiKraft implements ExternalSource {

    private PageReader pageReader = new DefaultPageReader();
    private StyleFinder styleFinder = new SimpleStyleFinder();
    private boolean fetchImage = true;
    String content;
    
    @Override
    public BeerProperties readFrom(String url) {
        content = pageReader.read(url);
        BeerProperties props = new BeerProperties();
        props.name = readName();
        props.breweryName = readBrewery();
        props.declaredStyle = readDeclaredStyle();
        props.descritpion = readDescription();
        props.abv = readAbv();
        props.ibu = readIbu();
        props.plato = readBlg();
        props.keywords.addAll(styleFinder.findStyleKeywords(props.declaredStyle));
        if (fetchImage) {
            BufferedImage image = readImage();
            if (image != null)
                props.labelImage.setPicture(image);
        }
        content = null;
        return props;
    }

    PageReader getPageReader() {
        return pageReader;
    }

    void setPageReader(PageReader pageReader) {
        this.pageReader = pageReader;
    }

    StyleFinder getStyleFinder() {
        return styleFinder;
    }

    void setStyleFinder(StyleFinder styleFinder) {
        this.styleFinder = styleFinder;
    }

    boolean isFetchImage() {
        return fetchImage;
    }

    void setFetchImage(boolean fetchImage) {
        this.fetchImage = fetchImage;
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

    private String readDescription() {
        return getElementContent("p", "<p class=\"description\"").trim();
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

    private BufferedImage readImage() {
        try {
            URL url = buildImageUrl();
            if (url != null)
                return ImageIO.read(url);
            else
                return null;
        } catch (IOException ex) {
            Logger.getLogger(PolskiKraft.class.getName()).log(Level.WARNING, null, ex);
            return null;
        }
    }

    private URL buildImageUrl() {
        String locator = "<meta property=\"og:image\"content=\"";
        int i = content.indexOf(locator);
        if (i < 0)
            return null;
        int start = i + locator.length();
        int end = content.indexOf("\"", start);
        String urlString = content.substring(start, end);
        try {
            return new URL(urlString);
        } catch (MalformedURLException ex) {
            Logger.getLogger(PolskiKraft.class.getName()).log(Level.WARNING, null, ex);
            return null;
        }
    }
}
