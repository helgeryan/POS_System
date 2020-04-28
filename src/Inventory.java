import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner; 
import java.io.BufferedWriter;
import java.io.FileWriter;
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
	
	//file = "src/itemList.txt"
	//file = "src/temp.txt" ...need to create
	
	//note all static methods
	//Methods:
	//Gets all info for item
	public static String[] getItem(String item, String fileName) {// 
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
					record = name + "," + price + "," + inventory + "," + threshold + "," + supplier;
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
					pw.print(newItem + "," + newPrice + "," + newInventory + "," + newThreshold + "," + newSupplier + "\n");
				}
				else {
					pw.print(item + "," + price + "," + inventory + "," + threshold + "," + supplier + "\n");
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
	
	//Dont need maybe
	//public String getItem() {
		//return item;
	//}
	
	
	//gets price 
	public static double getPrice(String item, String fileName) {// 
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
				record = name + "," + price + "," + inventory + "," + threshold + "," + supplier;
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
	public static void setPrice(String filepath, String editItem, String newPrice) {
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
					pw.print(item + "," + newPrice + "," + inventory + "," + threshold + "," + supplier + "\n");
				}
				else {
					pw.print(item + "," + price + "," + inventory + "," + threshold + "," + supplier + "\n");
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
	
	
	//gets inventory count
	public static int getInventory(String item, String fileName) {// 
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
					record = name + "," + price + "," + inventory + "," + threshold + "," + supplier;
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
	public static void setInventory(String filepath, String editItem, String newInventory) {
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
					pw.print(item + "," + price + "," + newInventory + "," + threshold + "," + supplier + "\n");
				}
				else {
					pw.print(item + "," + price + "," + inventory + "," + threshold + "," + supplier + "\n");
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
	
	
	//gets threshold for auto order
	public static int getThreshold(String item, String fileName) {// 
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
					record = name + "," + price + "," + inventory + "," + threshold + "," + supplier;
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
	public static void setThreshold(String filepath, String editItem, String newThreshold) {
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
					pw.print(item + "," + price + "," + inventory + "," + newThreshold + "," + supplier + "\n");
				}
				else {
					pw.print(item + "," + price + "," + inventory + "," + threshold + "," + supplier + "\n");
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
	
	//gets company that supply item
	public static String getSupplier(String item, String fileName) {// 
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
					record = name + "," + price + "," + inventory + "," + threshold + "," + supplier;
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
	public static void setSupplier(String filepath, String editItem, String newSupplier) {
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
					pw.print(item + "," + price + "," + inventory + "," + threshold + "," + newSupplier + "\n");
				}
				else {
					pw.print(item + "," + price + "," + inventory + "," + threshold + "," + supplier + "\n");
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
	
	//toString
	@Override
	public String toString() {
		return "InventoryManager [item=" + item + ", price=" + price + ", inventory=" + inventory + ", threshold="
				+ threshold + ", supplier=" + supplier + "]";
	}



}
