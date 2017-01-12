
package beershowcase.beerdata.autostyle.simple;

import beershowcase.beerdata.StyleKeyword;

/**
 *
 * @author Grzegorz Łoś
 */
public class PresentKeyword implements Condition {
    private final StyleKeyword keyword;

    public PresentKeyword(StyleKeyword keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean isSatisfied(Factset factset) {
        return factset.containsKeyword(keyword);
    }
}
