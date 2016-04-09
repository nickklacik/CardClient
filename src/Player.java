
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 *
 * @author Nick
 */
public class Player extends JPanel
{
    final static int SY = 100;
    final static int SX = 100;
    static int x;
    JLabel[] hand = new JLabel[2];
    JLabel name, cash;
    int space = 10;
    
    
    public Player(String name, String cash, int height)
    {
        setBackground(Color.GREEN);
        setLayout(null);
        setBounds(x,height-SY,SX,SY);
        x+=100;
        this.name = new JLabel(name);
        this.name.setFont(Main.font);
        this.name.setBounds(SX/2 - this.name.getFontMetrics(Main.font).stringWidth(name)/2,1,this.name.getFontMetrics(Main.font).stringWidth(name),Main.font.getSize());
        add(this.name);
        hand[0] = new JLabel();
        hand[1] = new JLabel();
        hand[0].setBounds(SX/2 - Card.BACK.getIconWidth()/3 - space/2, 20, Card.BACK.getIconWidth()/3, Card.BACK.getIconHeight()/3);
        hand[1].setBounds(SX/2 + space/2, 20, Card.BACK.getIconWidth()/3, Card.BACK.getIconHeight()/3);
        Image img = Card.BACK.getImage().getScaledInstance(hand[0].getWidth(), hand[0].getHeight(), 3);
        hand[0].setIcon(new ImageIcon(img));
        hand[1].setIcon(new ImageIcon(img));
        add(hand[0]);
        add(hand[1]);
        this.cash = new JLabel("$"+cash);
        this.cash.setFont(Main.font);
        this.cash.setBounds(SX/2 - this.cash.getFontMetrics(Main.font).stringWidth("$"+cash)/2,70,this.cash.getFontMetrics(Main.font).stringWidth("$"+cash),Main.font.getSize());
        add(this.cash);

    }
}
