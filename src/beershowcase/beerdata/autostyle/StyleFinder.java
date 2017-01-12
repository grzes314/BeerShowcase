
package beershowcase.beerdata.autostyle;

import beershowcase.beerdata.StyleKeyword;
import java.util.Collection;

/**
 *
 * @author Grzegorz Łoś
 */
public interface StyleFinder {
    Collection<StyleKeyword> findStyleKeywords(String declaredStyle);
}
