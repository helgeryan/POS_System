import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner; 

public class Inventory {
	//private List<Item> items;

	
	//Variables:
	private String item; //Name of product 
	private double price; // cost of one unit in $
	private int inventory; // Number of units in stock
	private int threshold; //When automatic order is placed
	private String supplier; //vendor
	
	
	//Methods:
	public String[] getItem(String item, String fileName) { 
		ArrayList<String> records = new ArrayList<String>(); //will be able to change size
		
		boolean found = false;
		String name = ""; 
		String price = ""; 
		String inventory = "";
		String threshold = "";
		String supplier = "";
		String record = "";
		
		
		try{
			Scanner x;
			x = new Scanner(new File(fileName));
			x.useDelimiter("[,\n]");
			
			while(x.hasNext()){
				name = x.next();
				if(name.contentEquals(item)) {
					price = x.next();
					inventory = x.next();
					threshold = x.next();
					supplier = x.next();
					record = name + "," + price + "," + inventory + "," + threshold + "," + supplier ;
					records.add(record);
					found = true;
				}
				else {
					x.next();
					x.next();
					
				}
			}
			
			if(!found) {
				System.out.println("NOT FOUND");
			}
			
			
		}
		catch(Exception e) {
			//System.out.println("ERROR");
		}
		
		String[] recordsArray = new String[records.size()];
		records.toArray(recordsArray);
		return recordsArray;
		
		
	}
	//will need to change to a list/array for item inputs(item,price,inventory,threshold, supplier) 
	public static void editRecord(String filepath, String editItem, String newItem, String newPrice, String newInventory, String newThreshold,  String newSupplier) {
		
		
		String tempFile = "temp.txt";
		
		File oldFile = new File(filepath);
		File newFile = new File(tempFile);
		
		String item = ""; String price = ""; String inventory = ""; String threshold = ""; String supplier = "";
		
		try {
			FileWriter fw = new FileWriter(tempFile, true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
			
			x = new Scanner(new File(filepath));
			x.useDelimiter("[,\n]");
			
			while(x.hasNext()){
				
				item = x.next();
				price = x.next();
				inventory = x.next();
				threshold = x.next();
				supplier = x.next();
				
				if(item.equals(editItem)) {
					
					pw.println(newItem + "," + newPrice + "," + newInventory + "," + newThreshold + "," + newSupplier);
				}
				
				else {
					
					pw.println(item + "," + price + "," + inventory + "," + threshold + "," + supplier );
				}
			}
			x.close();
			pw.flush();
			pw.close();
			oldFile.delete();
			File dump = new File(filepath);
			newFile.renameTo(dump);
		}
		catch( Exception e) {
			System.out.println("Error");
			
		}
		
		
		
		
	}
	

	@Override
	public String toString() {
		return "Inventory []";
	}
}
