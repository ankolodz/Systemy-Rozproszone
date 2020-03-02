import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatBox{
    private JTextField inputMessage;
    private JTextPane MessageBox;
    private JButton sendButton;
    private JPanel MainWindow;
    private ClientSocket clientSocketHandler;
    private JFrame mainWindow;


    public ChatBox(ClientSocket clientSocket) {
        this.clientSocketHandler = clientSocket;

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String messageToSend = inputMessage.getText();
                clientSocketHandler.sendBroadCast(messageToSend);
                newMessage("\nYou: "+messageToSend);
                inputMessage.setText("");
            }

        });
        MainWindow.setVisible(true);
    }
    public void start(String login){

        mainWindow = new JFrame("AK's Chat -"+login);
        mainWindow.setContentPane(this.MainWindow);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setSize(800,600);
       // mainWindow.pack();
        mainWindow.setVisible(true);
        newMessage("Witaj "+login+" dołącz do grupowej konwersacji");
    }
    public static String getNick (){
        return JOptionPane.showInputDialog("Podaj nick");
    }
    public void newMessage (String message){
        MessageBox.setText(MessageBox.getText() + "\n" + message);
    }
}
