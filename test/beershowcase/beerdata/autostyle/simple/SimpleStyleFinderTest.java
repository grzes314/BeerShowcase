
package beershowcase.beerdata.autostyle.simple;

import static beershowcase.beerdata.StyleKeyword.*;
import java.util.Collection;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 *
 * @author Grzegorz Łoś
 */
@RunWith(Parameterized.class)
public class SimpleStyleFinderTest {
    
    private final SimpleStyleFinder finder = new SimpleStyleFinder();
    
    public static class Task {
        String declaredStyle;
        Object[] musts;
        Object[] mustnots;

        Task(String declaredStyle, Object[] musts, Object[] mustnots) {
            this.declaredStyle = declaredStyle;
            this.musts = musts;
            this.mustnots = mustnots;
        }
        
    }
    
    @Parameters
    public static Object[] createTasks() {
        return c(
            // Popular styles
            new Task("American Wheat",
                c(Pale, Lager, WheatBeerFamily, Balanced),
                c(Ale)),
            new Task("International Pale Lager",
                c(Pale, Lager, PaleLagerFamily, Balanced),
                c(Ale)),
            new Task("International Amber Lager",
                c(Amber, Lager, AmberLagerFamily, Malty),
                c(Ale)),
            new Task("International Dark Lager",
                c(Dark, Lager, DarkLagerFamily, Malty),
                c(Ale)),
            new Task("Premium Pale Lager",
                c(Pale, Lager, PilsnerFamily, BitterTaste, Hoppy),
                c(Ale)),
            new Task("Munich Helles",
                c(Pale, Lager, PaleLagerFamily, Munich, Malty),
                c(Ale)),
            new Task("Helles Bock",
                c(Pale, Lager, BockFamily, Malty),
                c(Ale, Amber, Dark)),
            new Task("German Pils",
                c(Pale, Lager, PilsnerFamily, BitterTaste),
                c(Ale)),
            new Task("Märzen",
                c(Amber, Lager, AmberLagerFamily, Marzen, Malty),
                c(Ale)),
            new Task("Rauchbier",
                c(Amber, Lager, AmberLagerFamily, Rauchbier, Malty, Smoked, SmokeTaste),
                c(Ale)),
            new Task("Bock",
                c(Amber, Lager, BockFamily, Malty),
                c(Ale)),
            new Task("Vienna Lager",
                c(Amber, Lager, AmberLagerFamily, ViennaLager, Balanced),
                c(Ale)),
            new Task("Altbier",
                c(Amber, Ale, AmberAleFamily, Altbier, BitterTaste),
                c(Lager, Malty)),
            new Task("Kellerbier",
                c(Lager, Kellerbier, Balanced),
                c(Ale, Dark)),
            new Task("Munich Dunkel",
                c(Dark, Lager, DarkLagerFamily, Munich, Malty),
                c(Ale)),
            new Task("Schwarzbier",
                c(Dark, Lager, DarkLagerFamily, Schwarzbier, Balanced, Roasty),
                c(Ale)),
            new Task("Doppelbock",
                c(Amber, Lager, BockFamily, Malty, Imperial),
                c(Ale)),
            new Task("Baltic porter",
                c(Dark, Lager, PorterFamily, BalticPorter, Malty),
                c(Ale, Porter)),
            new Task("Weissbier",
                c(Pale, Ale, WheatBeerFamily, Hefeweizen, Malty),
                c(Lager)),
            new Task("Hefeweizen",
                c(Pale, Ale, WheatBeerFamily, Hefeweizen, Malty),
                c(Lager)),
            new Task("Dunkles Weissbier",
                c(Amber, Ale, WheatBeerFamily, Hefeweizen, Malty),
                c(Lager)),
            new Task("Weizenbock",
                c(Amber, Ale, WheatBeerFamily, Weizenbock, Malty),
                c(Lager)),
            new Task("Ordinary bitter",
                c(Amber, Ale, AmberAleFamily, BitterStyle, BitterTaste, British),
                c(Lager)),
            new Task("Extra special bitter",
                c(Amber, Ale, AmberAleFamily, BitterStyle, BitterTaste, Imperial, British),
                c(Lager)),
            new Task("British golden ale",
                c(Pale, Ale, PaleAleFamily, GoldenAle, BitterTaste, Hoppy, British),
                c(Lager)),
            new Task("Summer ale",
                c(Pale, Ale, PaleAleFamily, GoldenAle, BitterTaste, Hoppy, British),
                c(Lager)),
            new Task("English IPA",
                c(Pale, Ale, IpaFamily, BitterTaste, Hoppy, British),
                c(Lager, American)),
            new Task("Dark Mild",
                c(Dark, Ale, BrownAleFamily, Mild, Malty),
                c(Lager)),
            new Task("English Porter",
                c(Dark, Ale, PorterFamily, Porter, Malty, Roasty),
                c(Lager)),
            new Task("Irish Red Ale",
                c(Amber, Ale, AmberAleFamily, Balanced, British),
                c(Lager)),
            new Task("Dry stout",
                c(Dark, Ale, StoutFamily, BitterTaste, Roasty, British),
                c(Lager)),
            new Task("Sweet stout",
                c(Dark, Ale, StoutFamily, Malty, Roasty, Milk, British),
                c(Lager)),
            new Task("Oatmeal stout",
                c(Dark, Ale, StoutFamily, Balanced, Roasty, Oatmeal, British),
                c(Lager)),
            new Task("Foreign extra stout",
                c(Dark, Ale, StoutFamily, Balanced, Roasty, Imperial, British),
                c(Lager)),
            new Task("English Barleywine",
                c(Amber, Ale, StrongAleFamily, Malty, British),
                c(Lager)),
            new Task("American Pale Ale",
                c(Pale, Ale, PaleAleFamily, BitterTaste, Hoppy, American),
                c(Lager)),
            new Task("American Amber Ale",
                c(Amber, Ale, AmberAleFamily, Balanced, Hoppy, American),
                c(Lager)),
            new Task("American Brown Ale",
                c(Dark, Ale, BrownAleFamily, Balanced, Hoppy, American),
                c(Lager)),
            new Task("American Porter",
                c(Dark, Ale, PorterFamily, Porter, BitterTaste, Roasty, Hoppy, American),
                c(Lager, BalticPorter)),
            new Task("Russian imperial stout",
                c(Dark, Ale, StoutFamily, Roasty, Imperial),
                c(Lager, BalticPorter, BitterTaste, Malty)),
            new Task("American IPA",
                c(Pale, Ale, IpaFamily, BitterTaste, Hoppy, American),
                c(Lager)),
            new Task("Belgian IPA",
                c(Pale, Ale, IpaFamily, BitterTaste, Hoppy, Belgian),
                c(Lager)),
            new Task("Black IPA",
                c(Dark, Ale, IpaFamily, BitterTaste, Hoppy),
                c(Lager, Pale, Amber)),
            new Task("Red IPA",
                c(Amber, Ale, IpaFamily, BitterTaste, Hoppy),
                c(Lager, Pale, Dark)),
            new Task("Rye IPA",
                c(Amber, Ale, IpaFamily, BitterTaste, Hoppy, Rye),
                c(Lager, Pale, Dark)),
            new Task("White IPA",
                c(Pale, Ale, IpaFamily, BitterTaste, Hoppy, Spice),
                c(Lager)),
            new Task("Double IPA",
                c(Pale, Ale, IpaFamily, BitterTaste, Hoppy, Imperial),
                c(Lager)),
            new Task("Brett IPA",
                c(Pale, Ale, IpaFamily, BitterTaste, Hoppy, Brett),
                c(Lager)),
            new Task("American Barleywine",
                c(Amber, Ale, StrongAleFamily, BarleyWine, BitterTaste, Hoppy),
                c(Lager)),
            new Task("American wheatwine",
                c(Amber, Ale, StrongAleFamily, WheatBeerFamily, WheatWine, Balanced, Hoppy, American),
                c(Lager)),
            new Task("Sour",
                c(Sour),
                c()),
            new Task("Berliner weisse",
                c(Pale, Ale, WheatBeerFamily, BerlinerWeisse, Sour),
                c(Lager, Malty, BitterTaste, Balanced)),
            new Task("Lambic",
                c(Pale, Wild, WheatBeerFamily, Lambic, Sour),
                c(Ale, Lager, Malty, BitterTaste, Balanced)),
            new Task("Gueuze",
                c(Pale, Wild, WheatBeerFamily, Gueuze, Sour),
                c(Ale, Lager, Malty, BitterTaste, Balanced)),
            new Task("Kriek",
                c(Amber, Wild, WheatBeerFamily, Lambic, Sour, Fruit),
                c(Ale, Lager, Malty, BitterTaste, Balanced)),
            new Task("Witbier",
                c(Pale, Ale, WheatBeerFamily, Witbier, Malty, Spice, Belgian),
                c(Lager, BitterTaste)),
            new Task("Belgian pale ale",
                c(Pale, Ale, PaleAleFamily, Balanced, Belgian),
                c(Lager)),
            new Task("Biere de garde",
                c(Amber, Ale, AmberAleFamily, BiereDeGarde, Malty),
                c(Lager)),
            new Task("Belgian blond ale",
                c(Pale, Ale, Balanced, Belgian),
                c(Lager)),
            new Task("Saison",
                c(Pale, Ale, Saison, BitterTaste, Belgian),
                c(Lager)),
            new Task("Dubbel",
                c(Amber, Ale, Trappist, Dubbel, Malty, Belgian),
                c(Lager, PaleAleFamily)),
            new Task("Tripel",
                c(Pale, Ale, Trappist, Tripel, BitterTaste, Belgian),
                c(Lager, AmberAleFamily)),
            new Task("Quadruppel",
                c(Amber, Ale, Trappist, Quadruppel, BitterTaste, Belgian),
                c(Lager)),
            new Task("Belgian strong ale",
                c(Amber, Ale, StrongAleFamily, Malty, Belgian),
                c(Lager)),
            new Task("Gose",
                c(Pale, Ale, WheatBeerFamily, Gose, Sour, Spice),
                c(Lager, Malty, BitterTaste, Balanced)),
            new Task("Lichtenhainer",
                c(Pale, Ale, WheatBeerFamily, Lichtenhainer, Sour, Smoked, SmokeTaste),
                c(Lager, Malty, BitterTaste, Balanced)),
            new Task("Piwo Grodziskie",
                c(Pale, Ale, WheatBeerFamily, Grodziskie, Smoked, SmokeTaste),
                c(Lager)),
            new Task("Sahti",
                c(Amber, Ale, Sahti, Spice),
                c(Lager)),

            // unusual connections
            new Task("Smoked RIS",
                c(Dark, Ale, StoutFamily, Roasty, Imperial, SmokeTaste, Smoked),
                c(BalticPorter, BitterTaste, Malty)),

            new Task("Imperial Baltic Porter",
                c(PorterFamily, BalticPorter, Imperial),
                c(Porter)),

            new Task("Smoked Doppelbock",
                c(Amber, Lager, BockFamily, Malty, Imperial, SmokeTaste, Smoked),
                c()),

            new Task("American weissbier",
                c(Pale, Ale, WheatBeerFamily, Hefeweizen, Malty, Hoppy, American),
                c()),
            
            new Task("Robust Porter",
                c(PorterFamily, Porter, BitterTaste),
                c(BalticPorter)),
            
            new Task("Wild Sour Cherry RIS",
                c(Wild, StoutFamily, Imperial, Sour, Fruitty),
                c(Ale))
                
                
        );
    }
    
    //@Parameter(value = 0)
    public Task task;
            
    
    public SimpleStyleFinderTest(Task task) {
        this.task = task;
    }
    
    /*
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }*/
    
    private static Object[] c(Object... arr) {
        return arr;
    }
    
    @Test
    public void test() {
        Collection<?> keywords = finder.findStyleKeywords(task.declaredStyle.toLowerCase());
        String errMssg = "Assertion failed for \"" + task.declaredStyle + "\".";
        for (Object keyword: task.musts)
            assertTrue(errMssg + "Should contain " + keyword, keywords.contains(keyword));
        for (Object keyword: task.mustnots)
            assertFalse(errMssg + "Shouldn't contain " + keyword, keywords.contains(keyword));
    }    
}
