
package beershowcase.beerdata.autostyle.simple;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Grzegorz Łoś
 */
public class DeclaredStylePattern implements Condition {

    String regex;
    Pattern p;

    public DeclaredStylePattern(String regex) {
        this.regex = regex;
        p = Pattern.compile(regex);
    }
    
    @Override
    public boolean isSatisfied(Factset factset) {
        Matcher m = p.matcher(factset.getDeclaredStyle());
        return m.find();
    }
    
}
