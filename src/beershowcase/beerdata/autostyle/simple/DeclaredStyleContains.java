
package beershowcase.beerdata.autostyle.simple;

/**
 *
 * @author Grzegorz Łoś
 */
public class DeclaredStyleContains implements Condition {

    private final String[] substrs;

    public DeclaredStyleContains(String substr) {
        substr = substr.toLowerCase();
        substrs = substr.split("\\|");
    }
    
    @Override
    public boolean isSatisfied(Factset factset) {
        String declaredStyle = factset.getDeclaredStyle();
        for (String substr: substrs) {
            //if (contains(declaredStyle, substr))
            //    return true;
            if (declaredStyle.contains(substr))
                return true;
        }
        return false;
    }
    
    /*private boolean contains(String declaredStyle, String substr) {
        int i = declaredStyle.indexOf(substr);
        if (i == 0)
            return true;
        while (i != -1) {
            if (Character.isWhitespace(declaredStyle.charAt(i-1)))
                return true;
            i = declaredStyle.indexOf(substr, i+1);
        }
        return false;
    }*/
    
}
