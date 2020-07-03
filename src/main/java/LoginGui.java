import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;
import org.bson.Document;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGui implements ActionListener {
    private JFrame loginFrame;
    private JTextField userText;
    private JTextField passText;
    private JLabel correct;
    private MongoCollection<Document> usernames;

    //The constructor takes in a MongoDB collection and assigns the "usernames" field to the collection
    public LoginGui(MongoCollection<Document> collection) {
        usernames = collection;
    }

    /*
        getFrame() is a getter method for the current LoginGui frame.
        returns the current LoginGui JFrame
     */
    public JFrame getFrame() {
        return loginFrame;
    }

    /*
        login() is the main logic behind the Login GUI, it calls on the Register GUI if a new
        username wants to be created.
     */
    public void login() {
        /*
            attemptLogin allows logging in from pressing enter from either text field
            as well as clicking the login button.
        */
        Action attemptLogin = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String password = passText.getText();
                boolean loggedIn = false;
                //System.out.println("Username: " + username);
                //System.out.println("Password: " + password);
                if (username.isEmpty() && password.isEmpty()) {
                    correct.setText("Please enter login credentials.");
                }
                else if (username.isEmpty()) {
                    correct.setText("Please enter a username.");
                }
                else if (password.isEmpty()) {
                    correct.setText("Please enter a password.");
                }
                //Checks if the username exists in the database, if so, close the window.
                else {
                    correct.setText("");
                    for (Document document : usernames.find(eq("username", username))) {
                        if (document.get("password").equals(password)) {
                            loggedIn = true;
                        }
                    }
                    if (loggedIn) {
                        loginFrame.dispose();
                        InformationGui ig = new InformationGui(usernames, username);
                        ig.enterInfo();
                    }
                    else {
                        correct.setText("Incorrect username/password.");
                    }
                }
            }
        };

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);

        loginFrame = new JFrame();
        loginFrame.setSize(400,525);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setUndecorated(true);
        loginFrame.add(panel);

        panel.setLayout(null);

        /*
        JLabel userLabel = new JLabel("Username");
        userLabel.setBounds(10, 20, 80, 25);
        userLabel.setFont(new Font("Montserrat", Font.PLAIN, 14));
        panel.add(userLabel);

         */

        userText = new JTextField(20);
        userText.setBounds(50,150,300,35);
        userText.setFont(new Font("Montserrat", Font.PLAIN, 14));
        userText.addActionListener(attemptLogin);
        panel.add(userText);

        TextPrompt userPrompt = new TextPrompt("Username", userText);
        userPrompt.changeAlpha(.5f);
        userPrompt.setShow(TextPrompt.Show.FOCUS_LOST);

        /*
        JLabel passLabel = new JLabel("Password");
        passLabel.setBounds(10, 50, 80, 25);
        panel.add(passLabel);

         */

        passText = new JPasswordField(20);
        passText.setBounds(100,50,165,25);
        passText.addActionListener(attemptLogin);
        panel.add(passText);

        JButton login = new JButton("Login");
        login.setBounds(10, 80, 80, 25);
        login.addActionListener(attemptLogin);
        panel.add(login);

        /*
            This button opens up a Register window and sends the current login instance and collection
            to be used. Hides the current login GUI.
         */
        JButton register = new JButton("Register");
        register.setBounds(100, 80, 90, 25);
        register.addActionListener(e -> {
            RegisterGui rg = new RegisterGui(usernames);
            rg.createNewUser(this);
            loginFrame.setVisible(false);
        });
        panel.add(register);

        JButton quit = new JButton("X");
        quit.setFont(new Font("Montserrat", Font.BOLD, 14));
        quit.setBounds(210, 80, 50, 20);
        quit.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        panel.add(quit);

        correct = new JLabel("");
        correct.setBounds(10,110,300,25);
        panel.add(correct);

        loginFrame.setLocationRelativeTo(null);
        loginFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /*
        String username = userText.getText();
        String password = passText.getText();
        boolean loggedIn = false;
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        if (username.isEmpty() && password.isEmpty()) {
            correct.setText("Please enter login credentials.");
        }
        else if (username.isEmpty()) {
            correct.setText("Please enter a username.");
        }
        else if (password.isEmpty()) {
            correct.setText("Please enter a password.");
        }
        else {
            correct.setText("");
            for (Document document : usernames.find(eq("username", username))) {
                if (document.get("password").equals(password)) {
                    loggedIn = true;
                }
            }
            if (loggedIn) {
                frame.dispose();
            }
            else {
                correct.setText("Incorrect username/password.");
            }
        }

         */
    }
}
