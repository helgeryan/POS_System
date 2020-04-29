import java.io.FileNotFoundException;

public class TestMain {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		POS_System p = new POS_System();
		p.getInventory().getInventoryList();
		p.getRegisters().get(0).newSale();
		p.getRegisters().get(0).addItemToSale(new Item(1,"Book", "read it",3,5,0, 5.99, "barnesandnobles"), 2);
		p.getRegisters().get(0).addItemToSale(new Item(1,"Book", "read it",3,5,0, 5.99, "barnesandnobles"), 2);
		p.getRegisters().get(0).addItemToSale(new Item(1,"Book", "read it",3,5,0, 5.99, "barnesandnobles"), 2);
		p.getRegisters().get(0).addItemToSale(new Item(1,"Book", "read it",3,5,0, 5.99, "barnesandnobles"), 2);
		p.getRegisters().get(0).closeSale();
		
		p.getRegisters().get(0).newSale();
		p.getRegisters().get(0).addItemToSale(new Item(1,"Apple", "eat it",3,5,0, .99, "cub foods"), 3);
		p.getRegisters().get(0).addItemToSale(new Item(1,"Apple", "eat it",3,5,0, .99, "cub foods"), 3);
		p.getRegisters().get(0).addItemToSale(new Item(1,"Apple", "eat it",3,5,0, .99, "cub foods"), 3);
		p.getRegisters().get(0).addItemToSale(new Item(1,"Apple", "eat it",3,5,0, .99, "cub foods"), 3);
		p.getRegisters().get(0).closeSale();
		p.getRegisters().get(0).printSales();
		p.getRegisters().get(0).returnEntireSale(1);
		
		Item return1 = new Item(1,"Apple", "eat it",3,5,0, .99, "cub foods");

		p.getRegisters().get(0).returnSetofItems(2, return1, return1);

		p.getRegisters().get(0).printSales();
		System.out.println("Done");
	}

}
