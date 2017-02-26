
package beershowcase.gui;

import beershowcase.beerdata.Beer;
import beershowcase.beerdata.Brewery;
import beershowcase.beerdata.StyleKeyword;
import beershowcase.gui.components.AutoLabel;
import beershowcase.gui.components.RelativeLayout;
import beershowcase.utils.Box;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Grzegorz Łoś
 */
public class BeerViewPanel extends JPanel {

    private final Beer beer;
    private final Brewery brewery;
    private JPanel beerImageContainer;
    private JPanel breweryImageContainer;
    private String currency = "$";

    public BeerViewPanel(Beer beer, Brewery brewery) {
        this.beer = beer;
        this.brewery = brewery;
        
        RelativeLayout rl = new RelativeLayout(RelativeLayout.X_AXIS);
        rl.setFill(true);
        setLayout(rl);
        
        Box<BufferedImage> imgBox = beer.getLabelImage(RunningApplication.getFileSystem());
        int h = 0, w = 0;
        if (!imgBox.isEmpty()) {
            h = imgBox.getValue().getHeight(null);
            w = imgBox.getValue().getWidth(null);
        }
        
        if (h >= w) {
            add(makeColumn1ForVertical(), new Float(40));
            add(makeColumn2ForVertical(), new Float(30));
            add(makeColumn3ForVertical(), new Float(30));
        } else {
            add(makeColumn1ForHorizontal(), new Float(70));
            add(makeColumn2ForHorizontal(), new Float(30));
        }
        
    }

    private JPanel makeColumn1ForVertical() {
        JPanel panel = new JPanel();
        
        RelativeLayout rl = new RelativeLayout(RelativeLayout.Y_AXIS);
        rl.setFill(true);
        
        panel.setLayout(rl);
        panel.add(makeNameLabel(), new Float(7));
        panel.add(makePriceLabel(), new Float(5));
        panel.add(makeBeerImagePanel(), new Float(88));
        
        return panel;
    }

    private JPanel makeColumn2ForVertical() {
        JPanel panel = new JPanel();
        
        RelativeLayout rl = new RelativeLayout(RelativeLayout.Y_AXIS);
        rl.setFill(true);
        
        panel.setLayout(rl);
        panel.add(makeStyleLabel(), new Float(5));
        panel.add(makeParamsPanel(), new Float(15));
        panel.add( makeDescPanel(), new Float(80));
        
        return panel;
    }

    private JPanel makeColumn3ForVertical() {
        JPanel panel = new JPanel();
        
        RelativeLayout rl = new RelativeLayout(RelativeLayout.Y_AXIS);
        rl.setFill(true);
        
        panel.setLayout(rl);
        panel.add(makeBreweryNamePanel(), new Float(5));
        panel.add(makeBreweryImagePanel(), new Float(35));
        panel.add(makeTagsPanel(), new Float(60));
        
        return panel;
    }
    
    private JPanel makeColumn1ForHorizontal() {
        
        JPanel panel = new JPanel();
        
        RelativeLayout rl = new RelativeLayout(RelativeLayout.Y_AXIS);
        rl.setFill(true);
        
        panel.setLayout(rl);
        panel.add(makeRow1ForHorizontal(), new Float(20));
        panel.add(makeDescPanel(), new Float(20));
        panel.add(makeBeerImagePanel(), new Float(60));
        
        return panel;
    }
    
    private JPanel makeRow1ForHorizontal() {
        JPanel left = new JPanel();
        RelativeLayout leftLayout = new RelativeLayout(RelativeLayout.Y_AXIS);
        leftLayout.setFill(true);
        left.setLayout(leftLayout);
        left.add(makeNameLabel(), new Float(7));
        left.add(makePriceLabel(), new Float(5));
        left.add(new JPanel(), new Float(8));
        
        JPanel right = new JPanel();
        RelativeLayout rightLayout = new RelativeLayout(RelativeLayout.Y_AXIS);
        rightLayout.setFill(true);
        right.setLayout(rightLayout);
        right.add(makeStyleLabel(), new Float(5));
        right.add(makeParamsPanel(), new Float(15));
        
        JPanel row = new JPanel();        
        RelativeLayout rowLayout = new RelativeLayout(RelativeLayout.X_AXIS);
        rowLayout.setFill(true);
        row.setLayout(rowLayout);
        row.add(left, new Float(40));
        row.add(right, new Float(30));
        
        return row;
    }
    
    private JPanel makeColumn2ForHorizontal() {
        return makeColumn3ForVertical();
    }
    
    private Component makeNameLabel() {
        AutoLabel label = new AutoLabel(beer.getName());
        label.setFontStyle(Font.BOLD);
        
        return label;
    }

    private Component makeStyleLabel() {
        AutoLabel label = new AutoLabel(beer.getDeclaredStyle(), JLabel.CENTER);
        label.setFontStyle(Font.ITALIC);
        
        return label;
    }

    private Component makeBeerImagePanel() {
        beerImageContainer = new JPanel();
        beerImageContainer.setLayout(new BorderLayout());
        setBeerImage();
        return beerImageContainer;
    }

    private Component makePriceLabel() {
        AutoLabel label = new AutoLabel("Price: " + beer.getPrice() + " " + currency);
        return label;
    }
    
    private Component makeParamsPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.setBorder(BorderFactory.createEtchedBorder());
        
        panel.add(new AutoLabel("Gravity:", JLabel.RIGHT));
        panel.add(new AutoLabel(beer.getPlato().toString() + "\u00B0Blg", JLabel.LEFT));
        
        panel.add(new AutoLabel("Alc. vol.:", JLabel.RIGHT));
        panel.add(new AutoLabel(beer.getAbv().toString() + "%", JLabel.LEFT));
        
        panel.add(new AutoLabel("IBU:", JLabel.RIGHT));
        panel.add(new AutoLabel("" + beer.getIbu(), JLabel.LEFT));
        
        return panel;
    }

    private Component makeDescPanel() {
        JTextArea area = new JTextArea();
        area.setText("" + beer.getDescritpion(RunningApplication.getFileSystem()));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBackground(getBackground());
        area.setFont(new Font("Purisa", Font.PLAIN, 12));
        area.setEditable(false);
        
        JScrollPane scrollPane = new JScrollPane(area);
        scrollPane.setBorder(null);
        return scrollPane;
    }

    private Component makeBreweryNamePanel() {
        AutoLabel label = new AutoLabel(brewery.getName(), JLabel.RIGHT);
        label.setFontStyle(Font.BOLD);
        
        return label;
    }

    private Component makeBreweryImagePanel() {
        breweryImageContainer = new JPanel();
        breweryImageContainer.setLayout(new BorderLayout());
        setBreweryLogo();
        return breweryImageContainer;
    }

    private Component makeTagsPanel() {
        String bullet = "  \u2022 ";
        JTextArea area = new JTextArea();
        for (StyleKeyword keyword: beer.getStyleKeywords())
            area.append(bullet + keyword.name() + "\n");
        area.setBackground(getBackground());
        area.setEditable(false);
        
        JScrollPane scrollPane = new JScrollPane(area);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Tags"));
        return scrollPane;
    }

    private void setBreweryLogo() {
        Box<BufferedImage> image = brewery.getLogo(RunningApplication.getFileSystem());
        Component comp;
        if (image.isEmpty()) {
            comp = new JLabel("No logo");
        } else {
            comp = new ImagePanel(image.getValue());
        }
        breweryImageContainer.add(comp);
    }

    private void setBeerImage() {
        Box<BufferedImage> image = beer.getLabelImage(RunningApplication.getFileSystem());
        
        Component comp;
        if (image.isEmpty()) {
            comp = new JLabel("No picture");
        } else {
            comp = new ImagePanel(image.getValue());
        }
        beerImageContainer.add(comp);
    }
}
