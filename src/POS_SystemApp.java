import javax.swing.*;
import java.io.FileNotFoundException;

public class POS_SystemApp {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		//User u = new User();

		POS_System pos_system = new POS_System();

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
