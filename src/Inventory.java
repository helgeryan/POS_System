import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Inventory {

	private ArrayList<Item> items = new ArrayList<>();

	public ArrayList<Item> getInventoryList() throws FileNotFoundException {

		Scanner sc = new Scanner(new File("src/itemList.txt"));;
		int i = 0;

		while (sc.hasNextLine()){
			String row = sc.nextLine();
			Scanner rowParse = new Scanner(row);
			rowParse.useDelimiter(",");
			i++;
			while(rowParse.hasNext()){
				items.add(new Item(i, rowParse.next(), rowParse.next(), Integer.parseInt(rowParse.next()), Integer.parseInt(rowParse.next()), Integer.parseInt(rowParse.next()), Double.parseDouble(rowParse.next()), rowParse.next()));
			}
		}
		return items;
	}

	public void printInventory(){
		for(Item item : items){
			System.out.println(item);
		}
	}

	public void modifyInventory(){
		Scanner sc = new Scanner(System.in);

		System.out.print("What item are you adjusting? ");
		int itemID = sc.nextInt();

		System.out.print("Change in count: ");
		int newCountOnHand = sc.nextInt();

		newCountOnHand = items.get(itemID - 1).getCountOnHand() + newCountOnHand;
		items.get(itemID - 1).setCountOnHand(newCountOnHand);
	}
}
