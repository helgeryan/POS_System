import javax.swing.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class POS_SystemApp {

	public static void main(String[] args) throws FileNotFoundException {

		Register register = new Register();
		register.setId(1);
		POS_System pos_system = new POS_System();

		//User currUser = new User(1,"Joe","Smith","cashier","jsmith","password1",true);
/*
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				//Register register = new Register();
				try {
					new MainFrame(pos_system, register, currUser);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				//new MainFrame(register);
			}
		});

 */

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				//Register register = new Register();
				try {
					new LoginFrame(pos_system);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				//new MainFrame(register);
			}
		});

		//System.out.println(r.toString());

	}

}
