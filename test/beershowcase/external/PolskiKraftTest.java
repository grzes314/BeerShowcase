
package beershowcase.external;

import beershowcase.beerdata.BeerProperties;
import beershowcase.beerdata.StyleKeyword;
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
        polskiKraft.setFetchImage(false);
        
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
        assertEquals(props.name, "Kwas Theta");
        assertEquals(props.breweryName, "PINTA");
        assertEquals(props.declaredStyle, "Wild Sour Cheery RIS");
        assertEquals(props.descritpion, "Pinta miesiąca Grudzień 2016.");
        assertEquals(55, props.ibu);
        assertEquals(247, props.plato);
        assertEquals(102, props.abv);
        assertTrue(props.keywords.contains(StyleKeyword.StoutFamily));
        assertTrue(props.keywords.contains(StyleKeyword.Imperial));
        assertTrue(props.keywords.contains(StyleKeyword.Wild));
        assertTrue(props.keywords.contains(StyleKeyword.Sour));
    }
    
}
