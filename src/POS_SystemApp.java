import javax.swing.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class POS_SystemApp {

	public static void main(String[] args) throws FileNotFoundException {

		POS_System pos_system = new POS_System();

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					new LoginFrame(pos_system);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		});


	}

}
