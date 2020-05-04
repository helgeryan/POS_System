import javax.swing.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class POS_SystemApp {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		//User u = new User();

		Register register = new Register();
		POS_System pos_system = new POS_System();
/*
		Inventory.setInventory("Milk", 1578);
		System.out.println(Inventory.getInventory("Milk"));

 */

		User currUser = new User(1,"Joe","Smith","cashier","user1","password1","active");

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

		//System.out.println(r.toString());

	}

}
