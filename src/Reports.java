import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Formatter;

public class Reports {

	

	public void printSales(int DD, int MM, int YY, int HourStart, int HourEnd, String username)
	{
		
		Sale sale = new Sale();
		System.out.println(sale.toString());
	

	}
	
	public void printSales()
	{
		
		Register reg = new Register();
		System.out.println(reg.getSales());
	

	}
	
	/*public void printSales()
	{
		TransactionTableModel tran = new TransactionTableModel();		
		for (int i = 0; i<tran.getColumnCount();i++)
		{
			System.out.print (tran.getColumnName(i)+ "\t\t" );
			
			
		}
		System.out.println("");
		
		for (int t = 0; t<=tran.getRowCount();t++)
		{
			for (int c = 1; c<tran.getColumnCount();c++)
			{
				System.out.print(tran.getValueAt(t, c)+ "\t\t\t");
			}
			System.out.println("");
			
		}
		
	*/

	

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


