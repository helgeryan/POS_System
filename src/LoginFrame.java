import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

public class LoginFrame extends JFrame {

    private LoginPanel loginPanel;
    private JLabel instructions;
    private POS_System pos_system;
    private boolean authStatus = false;

    public LoginFrame(POS_System POS_System) throws FileNotFoundException {

        super("Point of Sale System");
        setLayout(new BorderLayout());

        POS_System pos_system = new POS_System();
        pos_system = POS_System;

        instructions = new JLabel("Enter your username and password to Login");
        loginPanel = new LoginPanel();



        instructions.setHorizontalAlignment(JLabel.CENTER);
        add(instructions, BorderLayout.NORTH);

        add(loginPanel, BorderLayout.CENTER);

        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }


    class LoginPanel extends JPanel {


        private JLabel registerLabel;
        private JTextField registerField;
        private JLabel userNameLabel;
        private JLabel passwordLabel;
        private JTextField userNameField;
        private JPasswordField passwordField;


        private JButton subBtn;

        private LoginListener loginListener;

        public LoginPanel(){

            Dimension dim = getPreferredSize();
            dim.width =250;
            setPreferredSize(dim);


            registerLabel = new JLabel("Register: ");
            userNameLabel = new JLabel("Username: ");
            passwordLabel = new JLabel("Password: ");

            registerField = new JTextField(10);
            userNameField = new JTextField(10);
            passwordField = new JPasswordField(10);


            subBtn = new JButton("Submit");

            subBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent actionEvent) {
                    int registerID =Integer.parseInt(registerField.getText());
                    String username = userNameField.getText();
                    String password = String.valueOf(passwordField.getPassword());
                    boolean authStatus = false;

                    User user = new User(0,null,null,null, username, password);
                    try {
                        authStatus = Authentication.Authenticate(pos_system, registerID, user);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    if(authStatus == true){
                        clearFields();
                        dispose();
                    }
                    else{
                        clearFields();
                        JFrame warning = new JFrame();
                        JOptionPane.showMessageDialog(warning, "The username or password you entered is incorrect.");
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
            add(registerLabel, gc);

            gc.gridx = 1;
            gc.insets = new Insets(0,0,0,0);
            gc.anchor = GridBagConstraints.LINE_START;
            add(registerField, gc);

            gc.gridy ++;

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
}