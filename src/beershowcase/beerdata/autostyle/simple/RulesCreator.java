
package beershowcase.beerdata.autostyle.simple;

import beershowcase.beerdata.StyleKeyword;
import static beershowcase.beerdata.StyleKeyword.*;
import java.util.ArrayList;

/**
 *
 * @author Grzegorz Łoś
 */
public class RulesCreator {
    public ArrayList<Rule> makeRules() {
        ArrayList<Rule> rules = new ArrayList<>();
        addRules(rules, makeDirectMainBranchRules());
        addRules(rules, makeSpecificStyleRules());
        addRules(rules, makeGenericFamilyRules());
        addRules(rules, makeGenericMainBranchRules());
        addRules(rules, makeFeaturesRules());
        addRules(rules, makeColorRules());
        addRules(rules, makeTasteRules());
        return rules;
    }
    
    private static Condition[] Conds(Condition... conds) {
        return conds;
    }

    private static Action[] Actions(Action... actions) {
        return actions;
    }

    private static Condition DC(String substr) {
        return new Not(new DeclaredStyleContains(substr));
    }
    
    private static Condition C(String substr) {
        return new DeclaredStyleContains(substr);
    }
    
    private static Condition P(String substr) {
        return new DeclaredStylePattern(substr);
    }
    
    private static Condition K(StyleKeyword keyword) {
        return new PresentKeyword(keyword);
    }
    
    private static AddKeyword A(StyleKeyword keyword) {
        return new AddKeyword(keyword);
    }
    
    private void addRules(ArrayList<Rule> allRules, Rule[] newRules) {
        for (Rule r: newRules)
            allRules.add(r);
    }
    

    private Rule[] makeDirectMainBranchRules() {
        return new Rule[]{
            new Rule(
                Conds( P("\\bale\\b") ),
                Actions( A(Ale) )
            ),
            new Rule(
                Conds( P("\\blager\\b") ),
                Actions( A(Lager) )
            ),
            new Rule(
                Conds( P("\\bwild\\b") ),
                Actions( A(Wild) )
            ),
        };
    }
    
    private Rule[] makeGenericMainBranchRules() {
        Condition HasFerment = new Or(K(Ale), K(Lager), K(Wild));
                
        return new Rule[]{
            new Rule(
                Conds( K(PaleAleFamily) ),
                Actions( A(Ale) )
            ),
            new Rule(
                Conds( K(IpaFamily) ),
                Actions( A(Ale) )
            ),
            new Rule(
                Conds( K(AmberAleFamily) ),
                Actions( A(Ale) )
            ),
            new Rule(
                Conds( K(BrownAleFamily) ),
                Actions( A(Ale) )
            ),
            new Rule(
                Conds( K(PorterFamily), new Not(HasFerment) ),
                Actions( A(Ale) )
            ),
            new Rule(
                Conds( K(StoutFamily), new Not(HasFerment) ),
                Actions( A(Ale) )
            ),
            new Rule(
                Conds( K(StrongAleFamily) ),
                Actions( A(Ale) )
            ),
            new Rule(
                Conds( K(WheatBeerFamily), new Not(HasFerment) ),
                Actions( A(Ale) )
            ),
            
            
            new Rule(
                Conds( K(PaleLagerFamily) ),
                Actions( A(Lager) )
            ),
            new Rule(
                Conds( K(PilsnerFamily) ),
                Actions( A(Lager) )
            ),
            new Rule(
                Conds( K(AmberLagerFamily) ),
                Actions( A(Lager) )
            ),
            new Rule(
                Conds( K(DarkLagerFamily) ),
                Actions( A(Lager) )
            ),
            new Rule(
                Conds( K(BockFamily), new Not(HasFerment) ),
                Actions( A(Lager) )
            )
        };
    }
    
    private Rule[] makeGenericFamilyRules() {
        return new Rule[]{
            new Rule(
                Conds( C("pale|blond"), K(Ale), DC("india") ),
                Actions( A(PaleAleFamily) )
            ),
            new Rule(
                Conds( P("\\bapa\\b") ),
                Actions( A(PaleAleFamily) )
            ),
            new Rule(
                Conds( P("\\bipa\\b") ),
                Actions( A(IpaFamily) )
            ),
            new Rule(
                Conds( C("india"), K(Ale) ),
                Actions( A(IpaFamily) )
            ),
            new Rule(
                Conds( C("\\bcda\\b") ),
                Actions( A(IpaFamily) )
            ),
            new Rule(
                Conds( C("cascadian"), C("dark"),  P("\\bale\\b") ),
                Actions( A(IpaFamily) )
            ),
            new Rule(
                Conds( P("\\bamber\\b"), K(Ale) ),
                Actions( A(AmberAleFamily) )
            ),
            new Rule(
                Conds( P("\\bred\\b"), K(Ale) ),
                Actions( A(AmberAleFamily) )
            ),
            new Rule(
                Conds( P("\\bbrown\\b"), K(Ale) ),
                Actions( A(BrownAleFamily) )
            ),
            new Rule(
                Conds( P("\\bdark"), K(Mild) ),
                Actions( A(BrownAleFamily) )
            ),
            new Rule(
                Conds( K(Porter) ),
                Actions( A(PorterFamily) )
            ),
            new Rule(
                Conds( K(BalticPorter) ),
                Actions( A(PorterFamily) )
            ),
            new Rule(
                Conds( C("stout") ),
                Actions( A(StoutFamily) )
            ),
            new Rule(
                Conds( P("\\bfes\\b") ),
                Actions( A(StoutFamily) )
            ),
            new Rule(
                Conds( P("\\bris\\b") ),
                Actions( A(StoutFamily) )
            ),
            new Rule(
                Conds( C("strong"), K(Ale) ),
                Actions( A(StrongAleFamily) )
            ),
            new Rule(
                Conds( C("wheat|weizen|weiss") ),
                Actions( A(WheatBeerFamily) )
            ),
            new Rule(
                Conds( C("bock") ),
                Actions( A(BockFamily) )
            ),
            new Rule(
                Conds( C("pale|light|hell"), K(Lager), DC("premium") ),
                Actions( A(PaleLagerFamily) )
            ),
            new Rule(
                Conds( C("hell"), C("bier") ),
                Actions( A(PaleLagerFamily) )
            ),
            new Rule(
                Conds( C("pils|pilzner") ),
                Actions( A(PilsnerFamily) )
            ),
            new Rule(
                Conds( C("premium"), C("pale"), K(Lager) ),
                Actions( A(PilsnerFamily) )
            ),
            new Rule(
                Conds( C("amber"), K(Lager) ),
                Actions( A(AmberLagerFamily) )
            ),
            new Rule(
                Conds( C("dark|dunk"), K(Lager) ),
                Actions( A(DarkLagerFamily) )
            ),
        };
    }   
    
    private Rule[] makeSpecificStyleRules() {
        return new Rule[]{
            new Rule(
                Conds( C("marzen|märzen") ),
                Actions( A(Marzen), A(AmberLagerFamily) )
            ),
            new Rule(
                Conds( C("hefeweizen|weissbier") ),
                Actions( A(Hefeweizen) )
            ),
            new Rule(
                Conds( C("weizenbock") ),
                Actions( A(Weizenbock) )
            ),
            new Rule(
                Conds( C("roggenbier") ),
                Actions( A(Roggenbier), A(WheatBeerFamily) )
            ),
            new Rule(
                Conds( C("schwarzbier") ),
                Actions( A(Schwarzbier), A(DarkLagerFamily), A(Balanced), A(Roasty) )
            ),
            new Rule(
                Conds( C("baltic"), C("porter") ),
                Actions( A(BalticPorter), A(Lager), A(DarkLagerFamily) )
            ),
            new Rule(
                Conds( C("vienna"), C("lager") ),
                Actions( A(ViennaLager), A(AmberLagerFamily), A(Balanced) )
            ),
            new Rule(
                Conds( C("kellerbier") ),
                Actions( A(Kellerbier), A(AmberLagerFamily), A(Balanced) )
            ),
            new Rule(
                Conds( C("rauchbier") ),
                Actions( A(Rauchbier), A(AmberLagerFamily) )
            ),
            new Rule(
                Conds( C("altbier") ),
                Actions( A(Altbier), A(AmberAleFamily), A(BitterTaste) )
            ),
            new Rule(
                Conds( C("berliner"), C("weisse") ),
                Actions( A(BerlinerWeisse), A(Sour) )
            ),
            new Rule(
                Conds( C("munich") ),
                Actions( A(Munich), A(Lager), A(Malty) )
            ),
            new Rule(
                Conds( C("porter"), DC("baltic") ),
                Actions( A(Porter), A(Roasty) )
            ),
            new Rule(
                Conds( C("bitter|esb") ),
                Actions( A(BitterStyle), A(AmberAleFamily), A(BitterTaste) )
            ),
            new Rule(
                Conds( C("golden|summer"), P("\\bale\\b") ),
                Actions( A(GoldenAle), A(PaleAleFamily), A(BitterTaste), A(Hoppy) )
            ),
            new Rule(
                Conds( C("mild") ),
                Actions( A(Mild), A(Ale) )
            ),
            new Rule(
                Conds( C("barley"), C("wine") ),
                Actions( A(BarleyWine), A(StrongAleFamily) )
            ),
            new Rule(
                Conds( C("wheat"), C("wine") ),
                Actions( A(WheatWine), A(Amber), A(StrongAleFamily), A(American), A(Balanced) )
            ),
            new Rule(
                Conds( C("american"), P("\\bwheat\\b") ),
                Actions( A(AmericanWheat), A(Lager), A(Balanced) )
            ),
            new Rule(
                Conds( C("biere"), C("garde") ),
                Actions( A(BiereDeGarde), A(AmberAleFamily) )
            ),
            new Rule(
                Conds( C("dubbel") ),
                Actions( A(Dubbel), A(Trappist), A(AmberAleFamily) )
            ),
            new Rule(
                Conds( C("tripel") ),
                Actions( A(Tripel), A(Trappist), A(StrongAleFamily), A(Pale) )
            ),
            new Rule(
                Conds( C("quadruppel") ),
                Actions( A(Quadruppel), A(Trappist), A(StrongAleFamily) )
            ),
            new Rule(
                Conds( C("witbier") ),
                Actions( A(Witbier), A(WheatBeerFamily), A(Spice) )
            ),
            new Rule(
                Conds( C("lambic") ),
                Actions( A(Lambic), A(Wild), A(WheatBeerFamily) )
            ),
            new Rule(
                Conds( C("kriek") ),
                Actions( A(Lambic), A(Kriek), A(Wild), A(WheatBeerFamily), A(Fruit), A(Amber) )
            ),
            new Rule(
                Conds( C("saison") ),
                Actions( A(Saison), A(PaleAleFamily), A(BitterTaste) )
            ),
            new Rule(
                Conds( C("gueuze") ),
                Actions( A(Gueuze), A(WheatBeerFamily), A(Wild) )
            ),
            new Rule(
                Conds( C("gose") ),
                Actions( A(Gose), A(WheatBeerFamily), A(Sour), A(Spice) )
            ),
            new Rule(
                Conds( C("lichtenhainer") ),
                Actions( A(Lichtenhainer), A(WheatBeerFamily), A(Smoked), A(Sour) )
            ),
            new Rule(
                Conds( C("grodziskie|gratzer|grätzer") ),
                Actions( A(Grodziskie), A(WheatBeerFamily), A(Smoked), A(BitterTaste) )
            ),
            new Rule(
                Conds( C("sahti") ),
                Actions( A(Sahti), A(WheatBeerFamily), A(Amber), A(Spice) )
            )
        };
    }
    
    private Rule[] makeFeaturesRules() {
        return new Rule[]{
            new Rule(
                Conds( C("american|aipa|apa") ),
                Actions( A(American) )
            ),
            new Rule(
                Conds( C("english|british|scottish|irish") ),
                Actions( A(British) )
            ),
            new Rule(
                Conds( DC("american"), new Or(K(Porter), K(BitterStyle), K(GoldenAle),
                        K(Mild), K(BarleyWine), K(StoutFamily)) ),
                Actions( A(British) )
            ),
            new Rule(
                Conds( C("belgian") ),
                Actions( A(Belgian) )
            ),
            new Rule(
                Conds( DC("american"), new Or(K(Trappist), K(Witbier),
                        K(Saison), K(Lambic), K(Gueuze)) ),
                Actions( A(Belgian) )
            ),
            
            new Rule(
                Conds( C("imperial|fes|ris|extra|double|triple|doppel") ),
                Actions( A(Imperial) )
            ),
            new Rule(
                Conds( C("smoke|whisky|whiskey|rauch") ),
                Actions( A(Smoked) )
            ),
            new Rule(
                Conds( C("milk|sweet|lactose") ),
                Actions( A(Milk) )
            ),
            new Rule(
                Conds( C("brett") ),
                Actions( A(Brett) )
            ),
            new Rule(
                Conds( C("rye|roggen") ),
                Actions( A(Rye) )
            ),
            new Rule(
                Conds( C("oatmeal|haferflock") ),
                Actions( A(Oatmeal) )
            ),
            new Rule(
                Conds( C("rice") ),
                Actions( A(Rice) )
            ),
            new Rule(
                Conds( C("fruit") ),
                Actions( A(Fruit) )
            ),
        };
    }
    
    private Rule[] makeColorRules() {
        Condition HasColor = new Or(K(Pale), K(Amber), K(Dark));
        
        return new Rule[]{
            //exceptions
            new Rule(
                Conds( C("dunk"), K(Hefeweizen) ),
                Actions( A(Amber) )
            ),
            
            // color explicitly given in style name
            new Rule(
                Conds( C("pale|light|hell|golden") ),
                Actions( A(Pale) )
            ),
            new Rule(
                Conds( C("amber|red") ),
                Actions( A(Amber) )
            ),
            new Rule(
                Conds( C("dark|dunk|black") ),
                Actions( A(Dark) )
            ),
            
            //Obvious families
            new Rule(
                Conds( K(PaleLagerFamily) ),
                Actions( A(Pale) )
            ),
            new Rule(
                Conds( K(PilsnerFamily) ),
                Actions( A(Pale) )
            ),
            new Rule(
                Conds( K(PaleAleFamily) ),
                Actions( A(Pale) )
            ),
            new Rule(
                Conds( K(AmberLagerFamily) ),
                Actions( A(Amber) )
            ),
            new Rule(
                Conds( K(AmberAleFamily) ),
                Actions( A(Amber) )
            ),
            new Rule(
                Conds( K(DarkLagerFamily) ),
                Actions( A(Dark) )
            ),
            new Rule(
                Conds( K(BrownAleFamily) ),
                Actions( A(Dark) )
            ),
            new Rule(
                Conds( K(StoutFamily) ),
                Actions( A(Dark) )
            ),
            new Rule(
                Conds( K(PorterFamily) ),
                Actions( A(Dark) )
            ),
            
            // IPA colors
            new Rule(
                Conds( C("bipa|cda") ),
                Actions( A(Dark) )
            ),
            new Rule(
                Conds( C("ripa") ),
                Actions( A(Amber) )
            ),
            new Rule(
                Conds( K(Rye), K(IpaFamily), new Not(HasColor) ),
                Actions( A(Amber) )
            ),
            new Rule(
                Conds( K(IpaFamily), new Not(HasColor) ),
                Actions( A(Pale) )
            ),
            
            new Rule(
                Conds( K(StrongAleFamily), new Not(HasColor) ),
                Actions( A(Amber) )
            ),
            new Rule(
                Conds( K(BockFamily), new Not(HasColor) ),
                Actions( A(Amber) )
            ),
            new Rule(
                Conds( K(WheatBeerFamily), new Not(HasColor) ),
                Actions( A(Pale) )
            )
        };            
    }   
    
    private Rule[] makeTasteRules() {
        Condition HasSth = new Or(K(Malty),
                K(Balanced), K(BitterTaste), K(Sour));
        
        return new Rule[]{
            // obvious rules
            new Rule(
                Conds( K(Smoked) ),
                Actions( A(SmokeTaste) )                    
            ),
            new Rule(
                Conds( K(Fruit) ),
                Actions( A(Fruitty) )                    
            ),
            new Rule(
                Conds( C("Cherry|Apple|Lemon|Lime") ),
                Actions( A(Fruitty) )                    
            ),
            new Rule(
                Conds( C("sour") ),
                Actions( A(Sour) )                    
            ),
            new Rule(
                Conds( K(Wild) ),
                Actions( A(Sour) )                    
            ),
            new Rule(
                Conds( C("American") ),
                Actions( A(Hoppy) )                    
            ),
            new Rule(
                Conds( K(StoutFamily) ),
                Actions( A(Roasty) )                    
            ),
            
            // specific rules
            new Rule(
                Conds( K(AmberAleFamily), C("irish") ),
                Actions( A(Balanced) )                    
            ),
            new Rule(
                Conds( K(StoutFamily), C("irish|dry") ),
                Actions( A(BitterTaste) )                    
            ),
            new Rule(
                Conds( K(StoutFamily), K(Milk) ),
                Actions( A(Malty) )                    
            ),
            new Rule(
                Conds( K(StoutFamily), K(Oatmeal), new Not(K(Milk)) ),
                Actions( A(Balanced) )                    
            ),
            new Rule(
                Conds( C("American|Robust"), K(Porter) ),
                Actions( A(BitterTaste) )                    
            ),
            new Rule(
                Conds( C("white"), K(IpaFamily) ),
                Actions( A(Spice) )                    
            ),
            
            
            // "default" rules
            new Rule(
                Conds( K(American), K(PaleAleFamily), new Not(HasSth) ),
                Actions( A(BitterTaste) )                    
            ),
            new Rule(
                Conds( K(PaleAleFamily), new Not(HasSth) ),
                Actions( A(Balanced) )                    
            ),
            new Rule(
                Conds( K(IpaFamily), new Not(HasSth) ),
                Actions( A(BitterTaste), A(Hoppy) )                    
            ),
            new Rule(
                Conds( K(American), K(AmberAleFamily), new Not(HasSth) ),
                Actions( A(Balanced) )                    
            ),
            new Rule(
                Conds( K(AmberAleFamily), new Not(HasSth) ),
                Actions( A(Malty) )                    
            ),
            new Rule(
                Conds( K(American), K(BrownAleFamily), new Not(HasSth) ),
                Actions( A(Balanced) )                    
            ),
            new Rule(
                Conds( K(BrownAleFamily), new Not(HasSth) ),
                Actions( A(Malty) )                    
            ),
            new Rule(
                Conds( K(American), K(PorterFamily), new Not(HasSth) ),
                Actions( A(Balanced) )                    
            ),
            new Rule(
                Conds( K(PorterFamily), new Not(HasSth) ),
                Actions( A(Malty) )                    
            ),
            new Rule(
                Conds( K(StoutFamily), new Not(HasSth) ),
                Actions( A(Balanced) )                    
            ),
            
            /*new Rule(
                Conds( K(StrongAleFamily), K(British), new Not(HasSth) ),
                Actions( A(Malty) )                    
            ),*/
            new Rule(
                Conds( K(StrongAleFamily), new Or(K(American), K(Trappist)), new Not(HasSth) ),
                Actions( A(BitterTaste) )                    
            ),
            new Rule(
                Conds( K(StrongAleFamily), new Not(HasSth) ),
                Actions( A(Malty) )                    
            ),
            
            new Rule(
                Conds( K(PaleLagerFamily), new Not(HasSth) ),
                Actions( A(Balanced) )                    
            ),
            new Rule(
                Conds( K(AmberLagerFamily), new Not(HasSth) ),
                Actions( A(Malty) )                    
            ),
            new Rule(
                Conds( K(DarkLagerFamily), new Not(HasSth) ),
                Actions( A(Malty) )                    
            ),
            new Rule(
                Conds( K(PilsnerFamily), new Not(HasSth) ),
                Actions( A(BitterTaste), A(Hoppy) )                    
            ),
            new Rule(
                Conds( K(BockFamily), new Not(HasSth) ),
                Actions( A(Malty) )                    
            ),
            new Rule(
                Conds( K(WheatBeerFamily), new Not(HasSth) ),
                Actions( A(Malty) )                    
            )            
        };
    }   
}
