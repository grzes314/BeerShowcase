
package beershowcase.beerdata.autostyle.simple;

import beershowcase.beerdata.StyleKeyword;
import static beershowcase.beerdata.StyleKeyword.Ale;

/**
 *
 * @author Grzegorz Łoś
 */
public class AddKeyword implements Action {
    private final StyleKeyword keywordToAdd;

    public AddKeyword(StyleKeyword keywordToAdd) {
        this.keywordToAdd = keywordToAdd;
    }
    
    @Override
    public void execute(Factset factset) {
        factset.addKeyword(keywordToAdd);
    }
    
}
