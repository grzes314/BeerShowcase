
package beershowcase.lazyresources;

/**
 *
 * @author Grzegorz Łoś
 */
public interface ObjectCache {
    
    Object getObject(long id);
    
    void addObject(long id, Object obj);
    
    void clean();

    void remove(long id);
}
