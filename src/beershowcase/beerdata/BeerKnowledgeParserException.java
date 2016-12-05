
package beershowcase.beerdata;

/**
 *
 * @author Grzegorz Łoś
 */
public class BeerKnowledgeParserException extends Exception {

    public BeerKnowledgeParserException() {
    }

    public BeerKnowledgeParserException(String message) {
        super(message);
    }

    public BeerKnowledgeParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public BeerKnowledgeParserException(Throwable cause) {
        super(cause);
    }

    public BeerKnowledgeParserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
