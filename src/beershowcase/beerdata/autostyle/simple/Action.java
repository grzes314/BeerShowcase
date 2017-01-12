
package beershowcase.beerdata.autostyle.simple;

/**
 *
 * @author Grzegorz Łoś
 */
public interface Action {
    /**
     * Run this action to modify a factset.
     * @param factset Factset to be modified.
     */
    void execute(Factset factset);
}
