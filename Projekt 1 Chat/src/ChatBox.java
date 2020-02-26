import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatBox {
    private JTextField inputMessage;
    private JTextPane MessageBox;
    private JButton sendButton;
    private ClientSocket clientSocketHandler;
    private int friendID;



    public ChatBox(int friendID) {
        this.friendID = friendID;

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String messageToSend = inputMessage.getText();
                clientSocketHandler.send(messageToSend, friendID);
                inputMessage.setText("");
            }
        });
    }
}
