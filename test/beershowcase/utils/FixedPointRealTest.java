
package beershowcase.utils;

import beershowcase.utils.FixedPointReal;
import junit.framework.TestCase;

/**
 *
 * @author Grzegorz Łoś
 */
public class FixedPointRealTest extends TestCase {
    
    private static double epsilon = 0.000001;
    FixedPointReal a0 = new FixedPointReal(17, 0);
    FixedPointReal a1 = new FixedPointReal(172, 1);
    FixedPointReal a2 = new FixedPointReal(1723, 2);
    FixedPointReal a3 = new FixedPointReal(17230, 3);
    FixedPointReal a4 = new FixedPointReal(172300, 4);
    
    FixedPointReal b0 = new FixedPointReal(19, 0);
    FixedPointReal b1 = new FixedPointReal(188, 1);
    FixedPointReal b2 = new FixedPointReal(1876, 2);
    FixedPointReal b3 = new FixedPointReal(18760, 3);
    FixedPointReal b4 = new FixedPointReal(187600, 4);
    
    public FixedPointRealTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of toAnotherPointPos method, of class FixedPointReal.
     */
    public void testFromString() {
        FixedPointReal a = new FixedPointReal("1234");
        assertEquals(0, a.pointPos);
        assertEquals(1234, a.units);
        
        a = new FixedPointReal("1234.00");
        assertEquals(2, a.pointPos);
        assertEquals(123400, a.units);
        
        a = new FixedPointReal("1234.516");
        assertEquals(3, a.pointPos);
        assertEquals(1234516, a.units);
        
    }
    
    /**
     * Test of toAnotherPointPos method, of class FixedPointReal.
     */
    public void testToAnotherPointPos() {
        assertEquals(0, a2.toAnotherPointPos(0).pointPos);
        assertEquals(a0.units, a2.toAnotherPointPos(0).units);
        
        assertEquals(1, a2.toAnotherPointPos(1).pointPos);
        assertEquals(a1.units, a2.toAnotherPointPos(1).units);
        
        assertEquals(2, a2.toAnotherPointPos(2).pointPos);
        assertEquals(a2.units, a2.toAnotherPointPos(2).units);
        
        assertEquals(3, a2.toAnotherPointPos(3).pointPos);
        assertEquals(a3.units, a2.toAnotherPointPos(3).units);
        
        assertEquals(4, a2.toAnotherPointPos(4).pointPos);
        assertEquals(a4.units, a2.toAnotherPointPos(4).units);
        
        assertEquals(0, b2.toAnotherPointPos(0).pointPos);
        assertEquals(b0.units, b2.toAnotherPointPos(0).units);
        
        assertEquals(1, b2.toAnotherPointPos(1).pointPos);
        assertEquals(b1.units, b2.toAnotherPointPos(1).units);
        
        assertEquals(2, b2.toAnotherPointPos(2).pointPos);
        assertEquals(b2.units, b2.toAnotherPointPos(2).units);
        
        assertEquals(3, b2.toAnotherPointPos(3).pointPos);
        assertEquals(b3.units, b2.toAnotherPointPos(3).units);
        
        assertEquals(4, b2.toAnotherPointPos(4).pointPos);
        assertEquals(b4.units, b2.toAnotherPointPos(4).units);
        
        FixedPointReal another = new FixedPointReal("123.9996");
        assertEquals("124.000", another.toAnotherPointPos(3).toString());
    }

    /**
     * Test of getDoubleValue method, of class FixedPointReal.
     */
    public void testGetDoubleValue() {
        assertEquals(17.0, a0.getDoubleValue(), epsilon);
        assertEquals(17.2, a1.getDoubleValue(), epsilon);
        assertEquals(17.23, a2.getDoubleValue(), epsilon);
        assertEquals(17.23, a3.getDoubleValue(), epsilon);
        assertEquals(17.23, a4.getDoubleValue(), epsilon);
    }

    /**
     * Test of smallerThan method, of class FixedPointReal.
     */
    public void testSmallerThan() {
        assertTrue(a2.smallerThan(b2));
        assertFalse(a2.smallerThan(a4));
        assertFalse(a2.smallerThan(new FixedPointReal(a2)));
        assertFalse(b2.smallerThan(a2));
    }

    /**
     * Test of smallerEq method, of class FixedPointReal.
     */
    public void testSmallerEq() {
        assertTrue(a2.smallerEq(b2));
        assertTrue(a2.smallerEq(a4));
        assertTrue(a2.smallerEq(new FixedPointReal(a2)));
        assertFalse(b2.smallerEq(a2));
    }

    /**
     * Test of greaterThan method, of class FixedPointReal.
     */
    public void testGreaterThan() {
        assertFalse(a2.greaterThan(b2));
        assertFalse(a2.greaterThan(a4));
        assertFalse(a2.greaterThan(new FixedPointReal(a2)));
        assertTrue(b2.greaterThan(a2));
    }

    /**
     * Test of greaterEq method, of class FixedPointReal.
     */
    public void testGreaterEq() {
        assertFalse(a2.greaterEq(b2));
        assertTrue(a2.greaterEq(a4));
        assertTrue(a2.greaterEq(new FixedPointReal(a2)));
        assertTrue(b2.greaterEq(a2));
    }

    /**
     * Test of equals method, of class FixedPointReal.
     */
    public void testEquals() {
        assertFalse(a2.equals(b2));
        assertTrue(a2.equals(a4));
        assertTrue(a2.equals(new FixedPointReal(a2)));
        assertFalse(b2.equals(a2));
    }

    /**
     * Test of hashCode method, of class FixedPointReal.
     */
    public void testHashCode() {
        assertEquals(new Long(172300000000L).hashCode(), a2.hashCode());
        assertTrue(a2.hashCode() == a3.hashCode());
        assertTrue(a2.hashCode() == a4.hashCode());
        assertEquals(new Long(187600000000L).hashCode(), b2.hashCode());
        assertTrue(b2.hashCode() == b3.hashCode());
        assertTrue(b2.hashCode() == b4.hashCode());
    }

    /**
     * Test of compareTo method, of class FixedPointReal.
     */
    public void testCompareTo() {
        assertTrue(a2.compareTo(b2) < 0);
        assertTrue(a2.compareTo(a4) == 0);
        assertTrue(b2.compareTo(a2) > 0);
    }

    /**
     * Test of toString method, of class FixedPointReal.
     */
    public void testToString() {
        assertEquals("17", a0.toString());
        assertEquals("17.2", a1.toString());
        assertEquals("17.23", a2.toString());
        assertEquals("17.230", a3.toString());
        assertEquals("17.2300", a4.toString());
        assertEquals("17.000", new FixedPointReal("17.000").toString());
        assertEquals("17.02", new FixedPointReal("17.02").toString());
    }
    
}
