import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Formatter;

public class Reports  {

	

	//public void printSales(int DD, int MM, int YY, int HourStart, int HourEnd, String username)
	//{
		
	//}
	
	
	
	public void printSales()
	{
		//String user;
		//user = username;
		
		File ci = new File("Sales Report2");
		FileWriter fw = null;
		try {
			fw = new FileWriter(ci);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			fw = new FileWriter(ci, true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PrintWriter pw = new PrintWriter (fw);
		
		Register reg = null;
		reg = new Register();
		
		String format = "%-25s";
		
		pw.println("Sales Report as of: "+Calendar.getInstance().getTime());
		pw.println("");
		pw.println("");
		
		
		for (int i = 0; i<reg.getSales().size(); i++)
		{
			pw.printf(format, reg.getSale(i));
			
			
		}
		pw.println("");
		pw.close();
	}
		
	public void printCurrentInventory()
	{	
		File ci = new File("Current Inventory");
		FileWriter fw = null;
		try {
			fw = new FileWriter(ci);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			fw = new FileWriter(ci, true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PrintWriter pw = new PrintWriter (fw);
		
		InventoryTableModel tm = null;
		try {
			tm = new InventoryTableModel();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String format = "%-25s";
		
		pw.println("Inventory Report as of "+ Calendar.getInstance().getTime());
		pw.println("");
		pw.println("");
		
		for (int i = 0; i<tm.getColumnCount();i++)
		{
			pw.printf (format, tm.getColumnName(i));
			
			
			
		}
		
		pw.println("");
		
		for (int t = 0; t<=10;t++)
		{
			for (int c = 0; c<tm.getColumnCount();c++)
			{
				pw.printf(format, tm.getValueAt(t, c));
				
			}
			pw.println("");
			
		}
		
		pw.close();
		
		
		
		
	}
		
	
		
		

}


