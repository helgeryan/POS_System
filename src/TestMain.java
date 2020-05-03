import java.io.FileNotFoundException;

public class TestMain {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		POS_System p = new POS_System();
		p.getInventory().getInventoryList();
		p.getRegisters().get(0).setCurrUser(new User(1, "Ryan", "Helgeson", "cashier", "user1", "password1"));
		p.getRegisters().get(0).newSale();
		p.getRegisters().get(0).addItemToSale(new Item("Book",1, 5.99,3,5,"barnesandnobles", 0 ), 2);
		p.getRegisters().get(0).addItemToSale(new Item("Book",1, 5.99,3,5,"barnesandnobles", 0 ), 2);
		p.getRegisters().get(0).addItemToSale(new Item("Book",1, 5.99,3,5,"barnesandnobles", 0 ), 2);
		p.getRegisters().get(0).addItemToSale(new Item("Book",1, 5.99,3,5,"barnesandnobles", 0 ), 2);
		p.getRegisters().get(0).closeSale();
		
		p.getRegisters().get(0).newSale();
		p.getRegisters().get(0).addItemToSale(new Item("Book",1, 5.99,3,5,"barnesandnobles", 0 ), 3);
		p.getRegisters().get(0).addItemToSale(new Item("Book",1, 5.99,3,5,"barnesandnobles", 0 ), 3);
		p.getRegisters().get(0).addItemToSale(new Item("Book",1, 5.99,3,5,"barnesandnobles", 0 ), 3);
		p.getRegisters().get(0).addItemToSale(new Item("Book",1, 5.99,3,5,"barnesandnobles", 0 ), 3);
		p.getRegisters().get(0).closeSale();
		p.getRegisters().get(0).printSales();
		p.getRegisters().get(0).returnEntireSale(1);
		
		Item return1 = new Item("Book",1, 5.99,3,5,"barnesandnobles", 0 );

		p.getRegisters().get(0).returnSetofItems(2, return1);

		p.getRegisters().get(0).printSales();
		System.out.println("Done");
	}
}
