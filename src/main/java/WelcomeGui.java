import com.mongodb.client.MongoCollection;
import org.bson.Document;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomeGui implements ActionListener {
    private JFrame welcomeFrame;
    private MongoCollection<Document> usernames;
    private String username;
    private Color background = new Color(33,33,33);
    private MatteBorder border = BorderFactory.createMatteBorder(0,0,1,0, Color.WHITE);

    public WelcomeGui(MongoCollection<Document> collection, String testname) {
        usernames = collection;
        username = testname;
    }

    public void welcome() {
        JPanel panel = new JPanel();
        panel.setBackground(background);

        welcomeFrame = new JFrame();
        welcomeFrame.setSize(800,500);
        welcomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        welcomeFrame.setUndecorated(true);
        welcomeFrame.add(panel);

        panel.setLayout(null);

        welcomeFrame.setLocationRelativeTo(null);
        welcomeFrame.setVisible(true);
    }











    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
