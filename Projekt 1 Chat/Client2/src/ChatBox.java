import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static java.awt.Color.decode;

public class ChatBox{
    private JTextField inputMessage;
    private JPanel MessageBox;
    private JButton sendButton;
    private JPanel MainWindow;
    private JButton sendAsciiArt;
    private JButton sendMulticast;
    private ClientSocket clientSocketHandler;
    private JFrame mainWindow;
    private Box box;
    private  MultimediaSocket multimediaSocket;
    private MulticastClientSocket multicastSocket;


    public ChatBox(ClientSocket clientSocket) {
        this.clientSocketHandler = clientSocket;
        box =  Box.createVerticalBox();
        MessageBox.add(box);
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String messageToSend = inputMessage.getText();
                clientSocketHandler.sendBroadCast(messageToSend);
                newMessage("You: "+messageToSend,true);
                inputMessage.setText("");
            }

        });
        MainWindow.setVisible(true);
        inputMessage.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyChar() == '\n')
                    sendButton.doClick();
            }
        });
        sendAsciiArt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                multimediaSocket.send(localASCIIart.mountain());
                newMessage("You:\n"+localASCIIart.mountain(),true);
                inputMessage.setText("");
            }
        });
        sendMulticast.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                multicastSocket.send(localASCIIart.dog());
                newMessage("You:\n"+localASCIIart.dog(),true);
                inputMessage.setText("");
            }
        });
    }
    public void start(String login){

        mainWindow = new JFrame("AK's Chat -"+login);
        mainWindow.setContentPane(this.MainWindow);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setSize(800,600);
        mainWindow.setVisible(true);
        newMessage("Witaj "+login+" dołącz do grupowej konwersacji",true);
    }
    public void stop (){
        mainWindow.dispose();
    }
    public static String getNick (){
        return JOptionPane.showInputDialog("Podaj nick");
    }

    public void newMessage (String message, boolean self){
        JTextArea newMessage = new JTextArea(message);
        newMessage.setEditable(false);
        if (self==true)
            newMessage.setBackground(decode("#A6F2AA"));
        else
            newMessage.setBackground(decode("#7AF281"));
        box.add(newMessage);
        mainWindow.validate();
        mainWindow.repaint();
    }

//    public void newMultimediaMessage(Message message) {
//    }

    public void setMultimediaSocket(MultimediaSocket multimediaSocket) {
        this.multimediaSocket = multimediaSocket;
    }

    public void setMulticastSocket(MulticastClientSocket multicastSocket) {this.multicastSocket = multicastSocket; }

    public static void alert(String text){
        JOptionPane.showMessageDialog(null,
                text,
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }
}
