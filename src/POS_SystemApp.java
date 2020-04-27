import javax.swing.*;

public class POS_SystemApp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Hello!");
		//User u = new User();
		Register r = new Register();

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new LoginFrame();
			}
		});
		
		System.out.println(r.toString());

	}

}
