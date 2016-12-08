
package beershowcase.beerdata;

/**
 *
 * @author Grzegorz Łoś
 */
public class BeerKnowledgeException extends Exception {

    public BeerKnowledgeException() {
    }

    public BeerKnowledgeException(String message) {
        super(message);
    }

    public BeerKnowledgeException(String message, Throwable cause) {
        super(message, cause);
    }

    public BeerKnowledgeException(Throwable cause) {
        super(cause);
    }

    public BeerKnowledgeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
