
package beershowcase.beerdata.autostyle.simple;

import beershowcase.beerdata.StyleKeyword;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Grzegorz Łoś
 */
public class Factset {
    String declaredStyle = "";
    Set<StyleKeyword> keywords = new TreeSet<>();

    public Factset() {
    }

    public Factset(String declaredStyle) {
        this.declaredStyle = declaredStyle.toLowerCase();
    }

    public String getDeclaredStyle() {
        return declaredStyle;
    }

    public void setDeclaredStyle(String declaredStyle) {
        this.declaredStyle = declaredStyle.toLowerCase();
    }

    public Set<StyleKeyword> getKeywords() {
        return keywords;
    }

    public boolean containsKeyword(StyleKeyword keyword) {
        return keywords.contains(keyword);
    }

    public boolean addKeyword(StyleKeyword keyword) {
        return keywords.add(keyword);
    }

    public boolean removeKeyword(StyleKeyword keyword) {
        return keywords.remove(keyword);
    }
    
}
