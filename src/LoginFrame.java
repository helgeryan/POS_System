import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

public class LoginFrame extends JFrame {

    private LoginPanel loginPanel;
    private JLabel instructions;

    private boolean authStatus = false;

    public LoginFrame(){

        super("Point of Sale System");
        setLayout(new BorderLayout());


        instructions = new JLabel("Enter your username and password to Login");
        loginPanel = new LoginPanel();


        loginPanel.setLoginListener(new LoginListener() {
            public void loginEventOccured(LoginEvent le) throws FileNotFoundException {
                String username = le.getUsername();
                String password = le.getPassword();

                //System.out.println(le.getUsername() + le.getPassword());

                User user = new User(0,null,null,null, username, password);
                authStatus = Authentication.Authenticate(user);

                if(authStatus == true){

                    loginPanel.clearFields();
                    JFrame AuthSucesss = new JFrame();
                    JOptionPane.showMessageDialog(AuthSucesss, "You have been successfully authenticated");
                    //Working on mainframe of GUI. for now, prompt confirms authentication.
                    //MainFrame mainFrame = new MainFrame();
                    //dispose();
                }
                else{
                    loginPanel.clearFields();
                    JFrame warning = new JFrame();
                    JOptionPane.showMessageDialog(warning, "The username or password you entered is incorrect.");
                }

            }
        });

        instructions.setHorizontalAlignment(JLabel.CENTER);
        add(instructions, BorderLayout.NORTH);

        add(loginPanel, BorderLayout.CENTER);

        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}

class LoginPanel extends JPanel {



    private JLabel userNameLabel;
    private JLabel passwordLabel;
    private JTextField userNameField;
    private JPasswordField passwordField;
    private JLabel status;

    private JButton subBtn;

    private LoginListener loginListener;

    public LoginPanel(){

        Dimension dim = getPreferredSize();
        dim.width =250;
        setPreferredSize(dim);



        userNameLabel = new JLabel("Username: ");
        passwordLabel = new JLabel("Password: ");

        userNameField = new JTextField(10);
        passwordField = new JPasswordField(10);


        subBtn = new JButton("Submit");

        subBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                String username = userNameField.getText();
                String password = String.valueOf(passwordField.getPassword());

                LoginEvent le = new LoginEvent(this, username, password);

                if(loginListener != null){
                    try {
                        loginListener.loginEventOccured(le);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        gc.gridy = 0;

        ///// First Row /////////////

        gc.weightx = 1;
        gc.weighty = .1;

        gc.gridx = 0;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = new Insets(0,0,0,5);
        add(userNameLabel, gc);

        gc.gridx = 1;
        gc.insets = new Insets(0,0,0,0);
        gc.anchor = GridBagConstraints.LINE_START;
        add(userNameField, gc);


        /////// Second Row //////////

        gc.gridy ++;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = new Insets(0,0,0,5);
        add(passwordLabel, gc);

        gc.gridx = 1;
        gc.insets = new Insets(0,0,0,0);
        gc.anchor = GridBagConstraints.LINE_START;
        add(passwordField, gc);


        ///// Third Row //////////

        gc.weightx=1;
        gc.weighty=.2;

        gc.gridy ++;
        gc.gridx = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        gc.insets = new Insets(0,0,0,0);
        add(subBtn, gc);

    }

    public void clearFields(){
        userNameField.setText(null);
        passwordField.setText(null);
    }

    public void setLoginListener(LoginListener loginListener) {
        this.loginListener = loginListener;
    }
}