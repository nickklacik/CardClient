
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;


/**
 *
 * @author Nick
 */
public class Panel extends JPanel implements ActionListener 
{
    JLabel[] hand = new JLabel[2], commCards = new JLabel[5];
    JLabel cash, pot;
    JButton bet, woot;
    JTextField bettingAmount;
    int space = 10;
    
    public Panel(int width, int height)
    {
        setLayout(null);
        setPreferredSize(new Dimension(width, height));
        for(int i = 0; i < hand.length; i++)
        {
            hand[i] = new JLabel();
            hand[i].setIcon(Card.BACK);
        }
        
        for(int i = 0; i < commCards.length; i++)
        {
            commCards[i] = new JLabel();
            commCards[i].setIcon(Card.BACK);
        }
        
        hand[0].setBounds(width/2 - Card.BACK.getIconWidth() - space/2, 20, Card.BACK.getIconWidth(), Card.BACK.getIconHeight());
        hand[1].setBounds(width/2 + space/2, 20, Card.BACK.getIconWidth(), Card.BACK.getIconHeight());
        for(JLabel l : hand)
            add(l);
        
        
        int x = 50;
        for(int i = 0; i < commCards.length; i++)
        {
            commCards[i].setBounds(x, 50, Card.BACK.getIconWidth(), Card.BACK.getIconHeight());
            x += Card.BACK.getIconWidth() + space;
        }
        for(JLabel l : commCards)
            add(l);
        
        bettingAmount = new JTextField();
        bet = new JButton("Bet");
        woot = new JButton("Woot");
        bettingAmount.setBounds(hand[0].getX(), hand[0].getY() + hand[0].getHeight() + 10, hand[1].getX() + hand[1].getWidth() - hand[0].getX(), 20);
        bet.setBounds(hand[0].getX(), bettingAmount.getY() + bettingAmount.getHeight() + 10, Card.BACK.getIconWidth(), 20);
        woot.setBounds(hand[1].getX(), bettingAmount.getY() + bettingAmount.getHeight() + 10, Card.BACK.getIconWidth(), 20);
        bettingAmount.addActionListener(this);
        bet.addActionListener(this);
        woot.addActionListener(this);
        add(bettingAmount);
        add(bet);
        add(woot);
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
