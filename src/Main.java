
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


/**
 *
 * @author Nick and Justin
 */
public class Main
{
    
    static Font font = new Font("Helvetica", Font.BOLD, 12);
    static String ip, name;
    static int port;
    
    public static void main(String args[]) //Creates initial frame for player to enter their name, and the server ip and port
    {    
        int x = 300;
        int y = 300;
        
        JFrame frame = new JFrame("");
        JPanel panel = new JPanel();
        JTextField ipTxt = new JTextField();
        JTextField portTxt = new JTextField();
        JTextField nameTxt = new JTextField();
        JLabel ipLabel = new JLabel("Ip:");
        JLabel portLabel = new JLabel("Port:");
        JLabel nameLabel = new JLabel("Nickname:");
        JButton connect = new JButton("Connect");
        
        ipLabel.setFont(font);
        portLabel.setFont(font);
        nameLabel.setFont(font);
        
        frame.setSize(x + 15, y + 30);
        ipTxt.setBounds(x/3, 50, 100, 25);
        portTxt.setBounds(x/3, 100, 100, 25);
        nameTxt.setBounds(x/3, 150, 100, 25);
        connect.setBounds(x/2 - 50, 200, 100, 25);
        
        int ipLen = ipLabel.getFontMetrics(font).stringWidth("Ip:") + 2;
        int portLen = portLabel.getFontMetrics(font).stringWidth("Port:") + 2;
        int nameLen = nameLabel.getFontMetrics(font).stringWidth("Nickname:") + 2;
        ipLabel.setBounds(x/3 - ipLen, 50, ipLen, 25);
        portLabel.setBounds(x/3 - portLen, 100, portLen, 25);
        nameLabel.setBounds(x/3 - nameLen, 150, nameLen, 25);
        
        connect.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                ip = ipTxt.getText();
                port = Integer.parseInt(portTxt.getText());
                name = nameTxt.getText();
                Client c = new Client(ip,port,name); //starts client
                frame.dispose();
                Thread t = new Thread(c);
                t.start();
                
            }
        });
        
        frame.add(panel);
        panel.setLayout(null);
        panel.add(ipTxt);
        panel.add(portTxt);
        panel.add(nameTxt);
        panel.add(connect);
        panel.add(ipLabel);
        panel.add(portLabel);
        panel.add(nameLabel);
            
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    } 
}
