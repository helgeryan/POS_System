import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner; 
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;


public class Inventory {
	
	//Variables:
	private static Scanner x;//for writing to file
	private String item; //Name of product 
	private double price; // cost of one unit in $
	private int inventory; // Number of units in stock
	private int threshold; 
	private String supplier;
	//file = "src/inventory.txt"; inventory file path
	//file = "src/temp.txt" ...need to create
	
	
	//note all static methods
	//Methods:
	//Gets all info for item
	public static String[] getItem(String item) {// 
		String fileName = "src/inventory.txt";
		ArrayList<String> records = new ArrayList<String>(); //will be able to change size
		boolean found = false;
		String name = ""; 
		String price = ""; 
		String inventory = "";
		String threshold = "";
		String supplier = "";
		String onOrder = "";
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
					onOrder = x.next();
					record = name + "," + price + "," + inventory + "," + threshold + "," + supplier + "," + onOrder;
					records.add(record);
					found = true;
					x.close();
				}
				else {
				}
			}
			if(!found) {
				System.out.println("NOT FOUND");
				x.close();
			}
			
		}
		catch(Exception e) {
			//System.out.println("ERROR");
		}
	
		String[] recordsArray = new String[records.size()];
		records.toArray(recordsArray);
		return recordsArray;
	}

	//edit all info for item
	public static void editRecord( String editItem, String newItem, Double newPriceD, int newInventoryI, int newThresholdI,  String newSupplier, int newOnOrderI) {
		String fileName = "src/inventory.txt";//real file
		String tempFile = "temp.txt"; //file for rewriting
		String newPrice = String.valueOf(newPriceD);
		String newInventory = Integer.toString(newInventoryI);
		String newThreshold = Integer.toString(newThresholdI);
		String newOnOrder = Integer.toString(newOnOrderI);
		File oldFile = new File(fileName);
		File newFile = new File(tempFile);
		String item = ""; String price = ""; String inventory = ""; String threshold = ""; String supplier = "";  String onOrder = "";
		
		try {
			FileWriter fw = new FileWriter(tempFile, true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
			x = new Scanner(new File(fileName));
			x.useDelimiter("[,\n]");
			
			while(x.hasNext()){
				item = x.next();
				price = x.next();
				inventory = x.next();
				threshold = x.next();
				supplier = x.next();
				onOrder = x.next();
				if(item.equals(editItem)) {
					pw.print(newItem + "," + newPrice + "," + newInventory + "," + newThreshold + "," + newSupplier + "," + newOnOrder + "\n");
				}
				else {
					pw.print(item + "," + price + "," + inventory + "," + threshold + "," + supplier + "," + onOrder + "\n");
				}
			}
			x.close();
			pw.flush();
			pw.close();
			oldFile.delete();
			File dump = new File(fileName);
			newFile.renameTo(dump);
		}
		catch( Exception e) {
			System.out.println("Error");
		}
	}
	
	//Dont need maybe
	//public String getItem() {
		//return item;
	//}
	
	
	//gets price 
	public static double getPrice(String item) {// 
		String fileName = "src/inventory.txt";
	ArrayList<String> records = new ArrayList<String>(); //will be able to change size
	boolean found = false;
	String name = ""; 
	String price = ""; 
	String inventory = "";
	String threshold = "";
	String supplier = "";
	String onOrder = "";
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
				onOrder = x.next();
				record = name + "," + price + "," + inventory + "," + threshold + "," + supplier + "," + onOrder;
				records.add(record);
				found = true;
				x.close();
			}
			else {
			}
		}
		if(!found) {
			System.out.println("NOT FOUND");
			x.close();
		}
		
	}
	catch(Exception e) {
		//System.out.println("ERROR");
	}

	String[] recordsArray = new String[records.size()];
	records.toArray(recordsArray);
	String[] data = new String[100];
	data = recordsArray;
	String info;
	info = data[0];
	List<String> list = Arrays.asList(info.split(","));
	double priceT = Double.parseDouble(list.get(1));
	return priceT;
}
	
	//sets price
	public static void setPrice(String editItem, double newPriceD) {
		String fileName = "src/inventory.txt";
		String tempFile = "temp.txt";
		String newPrice = String.valueOf(newPriceD);
		File oldFile = new File(fileName);
		File newFile = new File(tempFile);
		String item = ""; String price = ""; String inventory = ""; String threshold = ""; String supplier = ""; String onOrder = ""; 
		
		try {
			FileWriter fw = new FileWriter(tempFile, true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
			x = new Scanner(new File(fileName));
			x.useDelimiter("[,\n]");
			
			while(x.hasNext()){
				item = x.next();
				price = x.next();
				inventory = x.next();
				threshold = x.next();
				supplier = x.next();
				onOrder = x.next();
				if(item.equals(editItem)) {
					pw.print(item + "," + newPrice + "," + inventory + "," + threshold + "," + supplier + "," + onOrder + "\n");
				}
				else {
					pw.print(item + "," + price + "," + inventory + "," + threshold + "," + supplier + "," + onOrder + "\n");
				}
			}
			x.close();
			pw.flush();
			pw.close();
			oldFile.delete();
			File dump = new File(fileName);
			newFile.renameTo(dump);
		}
		catch( Exception e) {
			System.out.println("Error no price");
			x.close();
		}
	}
	
	
	//gets inventory count
	public static int getInventory(String item) {// 
		String fileName = "src/inventory.txt";
		ArrayList<String> records = new ArrayList<String>(); //will be able to change size
		boolean found = false;
		String name = ""; 
		String price = ""; 
		String inventory = "";
		String threshold = "";
		String supplier = "";
		String onOrder = "";
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
					record = name + "," + price + "," + inventory + "," + threshold + "," + supplier + "," + onOrder;
					records.add(record);
					found = true;
					x.close();
				}
				else {
				}
			}
			if(!found) {
				System.out.println("NOT FOUND");
				x.close();
			}
			
		}
		catch(Exception e) {
			//System.out.println("ERROR");
		}
		String[] recordsArray = new String[records.size()];
		records.toArray(recordsArray);
		String[] data = new String[100];
		data = recordsArray;
		String info;
		info = data[0];
		List<String> list = Arrays.asList(info.split(","));
		int inventoryT = Integer.parseInt(list.get(2));
		return inventoryT;
	}
	
	//sets inventory count
	public static void setInventory(String editItem, int newInventoryI) {
		String fileName = "src/inventory.txt";
		String tempFile = "temp.txt";
		String newInventory = Integer.toString(newInventoryI);
		File oldFile = new File(fileName);
		File newFile = new File(tempFile);
		String item = ""; String price = ""; String inventory = ""; String threshold = ""; String supplier = ""; String onOrder = ""; 
		
		try {
			FileWriter fw = new FileWriter(tempFile, true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
			x = new Scanner(new File(fileName));
			x.useDelimiter("[,\n]");
			
			while(x.hasNext()){
				item = x.next();
				price = x.next();
				inventory = x.next();
				threshold = x.next();
				supplier = x.next();
				onOrder = x.next();
				if(item.equals(editItem)) {
					pw.print(item + "," + price + "," + newInventory + "," + threshold + "," + supplier + "," + onOrder + "\n");
				}
				else {
					pw.print(item + "," + price + "," + inventory + "," + threshold + "," + supplier + "," + onOrder + "\n");
				}
			}
			x.close();
			pw.flush();
			pw.close();
			oldFile.delete();
			File dump = new File(fileName);
			newFile.renameTo(dump);
		}
		catch( Exception e) {
			System.out.println("Error");
		}
	}
	
	
	//gets threshold for auto order
	public static int getThreshold(String item) {// 
		String fileName = "src/inventory.txt";
		ArrayList<String> records = new ArrayList<String>(); //will be able to change size
		boolean found = false;
		String name = ""; 
		String price = ""; 
		String inventory = "";
		String threshold = "";
		String supplier = "";
		String onOrder = "";
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
					onOrder = x.next();
					record = name + "," + price + "," + inventory + "," + threshold + "," + supplier + "," + onOrder;
					records.add(record);
					found = true;
					x.close();
				}
				else {
				}
			}
			if(!found) {
				System.out.println("NOT FOUND");
				x.close();
			}
			
		}
		catch(Exception e) {
			//System.out.println("ERROR");
		}

		String[] recordsArray = new String[records.size()];
		records.toArray(recordsArray);
		String[] data = new String[100];
		data = recordsArray;
		String info;
		info = data[0];
		List<String> list = Arrays.asList(info.split(","));
		int thresholdT = Integer.parseInt(list.get(3));
		return thresholdT;
	}
	
	//set threshold for auto orders
	public static void setThreshold( String editItem, int newThresholdI) {
		String fileName = "src/inventory.txt";
		String tempFile = "temp.txt";
		String newThreshold = Integer.toString(newThresholdI);
		File oldFile = new File(fileName);
		File newFile = new File(tempFile);
		String item = ""; String price = ""; String inventory = ""; String threshold = ""; String supplier = ""; String onOrder = ""; 
		
		try {
			FileWriter fw = new FileWriter(tempFile, true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
			x = new Scanner(new File(fileName));
			x.useDelimiter("[,\n]");
			
			while(x.hasNext()){
				item = x.next();
				price = x.next();
				inventory = x.next();
				threshold = x.next();
				supplier = x.next();
				onOrder = x.next();
				if(item.equals(editItem)) {
					pw.print(item + "," + price + "," + inventory + "," + newThreshold + "," + supplier + "," + onOrder + "\n");
				}
				else {
					pw.print(item + "," + price + "," + inventory + "," + threshold + "," + supplier + "," + onOrder + "\n");
				}
			}
			x.close();
			pw.flush();
			pw.close();
			oldFile.delete();
			File dump = new File(fileName);
			newFile.renameTo(dump);
		}
		catch( Exception e) {
			System.out.println("Error");
		}
	}
	
	//gets company that supply item
	public static String getSupplier(String item) {// 
		String fileName = "src/inventory.txt";
		ArrayList<String> records = new ArrayList<String>(); //will be able to change size
		boolean found = false;
		String name = ""; 
		String price = ""; 
		String inventory = "";
		String threshold = "";
		String supplier = "";
		String onOrder = "";
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
					record = name + "," + price + "," + inventory + "," + threshold + "," + supplier + "," + onOrder;
					records.add(record);
					found = true;
					x.close();
				}
				else {
				}
			}
			if(!found) {
				System.out.println("NOT FOUND");
				x.close();
			}
			
		}
		catch(Exception e) {
			//System.out.println("ERROR");
		}

		String[] recordsArray = new String[records.size()];
		records.toArray(recordsArray);
		String[] data = new String[100];
		data = recordsArray;
		String info;
		info = data[0];
		List<String> list = Arrays.asList(info.split(","));
		String supplierT = list.get(4);
		return supplierT;
	}
	
	//sets who is our vender for item
	public static void setSupplier(String editItem, String newSupplier) {
		String fileName = "src/inventory.txt";
		String tempFile = "temp.txt";
		File oldFile = new File(fileName);
		File newFile = new File(tempFile);
		String item = ""; String price = ""; String inventory = ""; String threshold = ""; String supplier = ""; String onOrder = ""; 
		
		try {
			FileWriter fw = new FileWriter(tempFile, true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
			x = new Scanner(new File(fileName));
			x.useDelimiter("[,\n]");
			
			while(x.hasNext()){
				item = x.next();
				price = x.next();
				inventory = x.next();
				threshold = x.next();
				supplier = x.next();
				onOrder = x.next();
				if(item.equals(editItem)) {
					pw.print(item + "," + price + "," + inventory + "," + threshold + "," + newSupplier + "," + onOrder + "\n");
				}
				else {
					pw.print(item + "," + price + "," + inventory + "," + threshold + "," + supplier + "," + onOrder + "\n");
				}
			}
			x.close();
			pw.flush();
			pw.close();
			oldFile.delete();
			File dump = new File(fileName);
			newFile.renameTo(dump);
		}
		catch( Exception e) {
			System.out.println("Error");
		}
	}
	
	//////////////////
	
	//gets number of onOrder for item
		public static int getOnOrder(String item) {// 
			String fileName = "src/inventory.txt";
			ArrayList<String> records = new ArrayList<String>(); //will be able to change size
			boolean found = false;
			String name = ""; 
			String price = ""; 
			String inventory = "";
			String threshold = "";
			String supplier = "";
			String onOrder = "";
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
						onOrder = x.next();
						record = name + "," + price + "," + inventory + "," + threshold + "," + supplier + "," + onOrder;
						records.add(record);
						found = true;
						x.close();
					}
					else {
					}
				}
				if(!found) {
					System.out.println("NOT FOUND");
					x.close();
				}
				
			}
			catch(Exception e) {
				//System.out.println("ERROR");
			}

			String[] recordsArray = new String[records.size()];
			records.toArray(recordsArray);
			String[] data = new String[100];
			data = recordsArray;
			String info;
			info = data[0];
			List<String> list = Arrays.asList(info.split(","));
			
			int onOrderT = Integer.parseInt(list.get(5));
			return onOrderT;
		}
	

		//sets number of onOrder for item
		public static void setOnOrder(String editItem, int newOnOrderI) {
			String fileName = "src/inventory.txt";
			String tempFile = "temp.txt";
			String newOnOrder = Integer.toString(newOnOrderI);
			File oldFile = new File(fileName);
			File newFile = new File(tempFile);
			String item = ""; String price = ""; String inventory = ""; String threshold = ""; String supplier = ""; String onOrder = ""; 
			
			try {
				FileWriter fw = new FileWriter(tempFile, true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter pw = new PrintWriter(bw);
				x = new Scanner(new File(fileName));
				x.useDelimiter("[,\n]");
				
				while(x.hasNext()){
					item = x.next();
					price = x.next();
					inventory = x.next();
					threshold = x.next();
					supplier = x.next();
					onOrder = x.next();
					if(item.equals(editItem)) {
						pw.print(item + "," + price + "," + inventory + "," + threshold + "," + supplier + "," + newOnOrder + "\n");
					}
					else {
						pw.print(item + "," + price + "," + inventory + "," + threshold + "," + supplier + "," + onOrder + "\n");
					}
				}
				x.close();
				pw.flush();
				pw.close();
				oldFile.delete();
				File dump = new File(fileName);
				newFile.renameTo(dump);
			}
			catch( Exception e) {
				System.out.println("Error");
			}
		}
	
	
	
	
	
	//appends items to the end of the file
	public static void appendStrToFile(String str) { 
		String fileName = "src/inventory.txt";
		
		try { 
			BufferedWriter out = new BufferedWriter( new FileWriter(fileName, true)); 
		 	//out.write("\n");
		 	out.write(str); 
		 	out.close(); 
		 } 
		catch (IOException e) { 
		 	System.out.println("exception occoured" + e); 
		 } 
	 } 
	
	
	
	//Not sure it this works
	//toString
	@Override
	public String toString() {
		return "InventoryManager [item=" + item + ", price=" + price + ", inventory=" + inventory + ", threshold="
				+ threshold + ", supplier=" + supplier + "]";
	}



}
