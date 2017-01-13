
package beershowcase.external;

import beershowcase.beerdata.BeerProperties;
import junit.framework.TestCase;

/**
 *
 * @author Grzegorz Łoś
 */
public class PolskiKraftTest extends TestCase {
    
    public PolskiKraft polskiKraft;
    
    public PolskiKraftTest(String testName) {
        super(testName);
        polskiKraft = new PolskiKraft();
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        polskiKraft.setPageReader(new MockPageReader());
        
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of readFrom method, of class PolskiKraft.
     */
    public void testReadFrom() {
        BeerProperties props = polskiKraft.readFrom("ADDRESS DOESNT MATTER");
        assertEquals(55, props.ibu);
        assertEquals(247, props.plato);
        assertEquals(102, props.abv);
    }
    
}
