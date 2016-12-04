/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beershowcase.beerdata;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author grzes
 */
public class ByteUtilsTest {
    
    ByteArrayOutputStream out;
    ByteArrayInputStream in;
    
    public ByteUtilsTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        out = new ByteArrayOutputStream();
    }
    
    @After
    public void tearDown() {
    }
    
    private void makeInFromOut() {
        in = new ByteArrayInputStream(out.toByteArray());
    }

    @Test
    public void readWriteInt() {
        int before1 = 137, before2 = -245, after1, after2;
        ByteUtils.addIntToByteStream(before1, out);
        ByteUtils.addIntToByteStream(before2, out);
        makeInFromOut();
        after1 = ByteUtils.readIntFromByteStream(in);
        after2 = ByteUtils.readIntFromByteStream(in);
        Assert.assertEquals(after1, before1);
        Assert.assertEquals(after2, before2);
    }

    @Test
    public void readWriteLong() {
        long before1 = 137, before2 = -245, after1, after2;
        ByteUtils.addLongToByteStream(before1, out);
        ByteUtils.addLongToByteStream(before2, out);
        makeInFromOut();
        after1 = ByteUtils.readLongFromByteStream(in);
        after2 = ByteUtils.readLongFromByteStream(in);
        Assert.assertEquals(after1, before1);
        Assert.assertEquals(after2, before2);
    }

    @Test
    public void readWriteString() {
        String[] before = {"Hello", "", "Let's add some relatively long string and see what happens"};
        for (String b: before)
            ByteUtils.addStringToByteStream(b, out);
        makeInFromOut();
        for (String b: before) {
            String a = ByteUtils.readStringFromByteStream(in);
            Assert.assertEquals(b, a);   
        }
    }
}
