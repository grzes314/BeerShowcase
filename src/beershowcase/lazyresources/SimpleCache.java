
package beershowcase.lazyresources;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Grzegorz Łoś
 */
public class SimpleCache implements ObjectCache {

    private final Map<Long, Object> idToResource = new HashMap<>();
            
    @Override
    public Object getObject(long id) {
        return idToResource.get(id);
    }

    @Override
    public void addObject(long id, Object obj) {
        idToResource.put(id, obj);
    }

    @Override
    public void clean() {
        idToResource.clear();
    }

    @Override
    public void remove(long id) {
        idToResource.remove(id);
    }
    
}
