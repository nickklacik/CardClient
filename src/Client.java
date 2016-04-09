
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;

/**
 *
 * @author Nick and Justin
 */
public class Client extends JFrame implements ActionListener, Runnable
{
    int port, cash;
    int width = 1200;
    int height = 800;
    String ip, name;
    private Socket server;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private JTextArea output;
    private JTextField cmdLine;
    private Panel panel;
    private Font font = new Font("Helvetica", Font.BOLD, 12);
    boolean gameInProgress, running;
    Card[] hand;
    
    public Client(String ip, int port, String name)
    {
        this.ip = ip;
        this.port = port;
        this.name = name;
        
        cmdLine = new JTextField();
        cmdLine.setFont(font);
        cmdLine.setBackground(Color.black);
        cmdLine.setForeground(Color.white);
        cmdLine.addActionListener(this);
        add(cmdLine, BorderLayout.SOUTH);
        
        ImageIcon icon = new ImageIcon("Drill_Icon.png");
        setIconImage(icon.getImage());
        
        output = new JTextArea();
        DefaultCaret caret = (DefaultCaret) output.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        output.setFont(font);
        output.setBackground(Color.black);
        output.setForeground(Color.white);
        output.setEditable(false);
        add(new JScrollPane(output));
        panel = new Panel(width,height/2);
        add(panel, BorderLayout.NORTH);
        Player p = new Player("Nerd", "500", height/2);
        Player p2 = new Player("Nerd 2.0", "500", height/2);
        panel.add(p);
        panel.add(p2);
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    public void run()
    {
        while(true)
        {
            output.append("Attempting connection...\n");
            try {
                server = new Socket(InetAddress.getByName(ip), port);
                in = new ObjectInputStream(server.getInputStream());
                out = new ObjectOutputStream(server.getOutputStream());
                break;
            } catch (IOException ex) {
                System.err.println(ex);
            }   
        }
        try {
            out.writeObject("/setName " + name);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        output.append("connected\n");
        running = true;
        Object obj;
        String s;
        Integer n;
        while(running)
        {
            obj = null;
            try {
                obj = in.readObject();
            } catch (IOException ex) {
//                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
//                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if(obj instanceof Card[])
            {
                hand = (Card[]) obj;
                output.append(Arrays.toString(hand)+"\n");
            }
            else if(obj instanceof Integer)
            {
                cash += (Integer) obj;
                output.append("you have been given $" + (Integer) obj + "\n");
            }
            else if(obj instanceof String)
            {
                s = (String) obj;
                if(s.startsWith("/name "))
                    name = s.substring(6);
                else if(s.startsWith("/drill"))
                    drill();
                else
                    output.append((String) obj);
            }
        }
    }
    
    private void bet(Integer n)
    {
        if(n<=0)
            output.append("can't bet 0 or a negative number\n");
        else if(cash-n<0)
            output.append("not enough money");
        else
        {
            cash-=n;
            try {
                out.writeObject(n);
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void drill()
    {
        try {
            AudioInputStream stream = AudioSystem.getAudioInputStream(new File("drill.wav"));
            Clip clip = (Clip) AudioSystem.getLine(new DataLine.Info(Clip.class, stream.getFormat()));
            clip.open(stream);
            clip.start();
            Thread.sleep(5500);
            clip.close();
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        String command = e.getActionCommand();
        if(!command.equals(""))
        {
            if(command.charAt(0) == '/')
            {
                if(command.startsWith("/bet ")) //
                {
                    bet(Integer.parseInt(command.substring(5)));
                }
                else if(command.startsWith("/peek"))
                {
                    output.append(Arrays.toString(hand) + "\n");
                }
                else if(command.startsWith("/fold")) //
                {
                    try {
                        out.writeObject("/fold");
                    } catch (IOException ex) {
                        Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else if(command.startsWith("/woot")) //
                {
                    try {
                        out.writeObject("/woot");
                    } catch (IOException ex) {
                        Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else if(command.startsWith("/cash"))
                {
                    output.append("You have $" + cash + "\n");
                }
                else if(command.startsWith("/help"))
                {
                    output.append("Commands: \n" +
                                    "\t/peek  --- displays you hand\n" +
                                    "\t/fold  --- fold cards\n" +
                                    "\t/bet <amount of cash you're going to bet> --- bet money \n" +
                                    "\t/woot --- signals that you are done betting \n" +
                                    "\t/cash  --- shows how much money you have\n");
                }
                else if(command.startsWith("/drill"))
                {
                    try {
                        out.writeObject("/drill");
                    } catch (IOException ex) {
                        Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            else
            {
//                output.append(name + ": " + command + "\n");
                try {
                    out.writeObject(name + ": " + command + "\n");
                } catch (IOException ex) {
//                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            cmdLine.setText("");
        }
    }
    
}