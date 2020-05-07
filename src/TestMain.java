import java.io.FileNotFoundException;

public class TestMain {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		POS_System p = new POS_System();
		p.getRegisters().get(0).setCurrUser(new User(1, "Ryan", "Helgeson", "cashier", "user1", "password1", true));
		p.getRegisters().get(0).newSale();
		p.getRegisters().get(0).addItemToSale("Beer", 2);
		p.getRegisters().get(0).addItemToSale("Beer", 2);
		p.getRegisters().get(0).closeSale();
		System.out.println();
//		p.getRegisters().get(0).newSale();
//		p.getRegisters().get(0).addItemToSale(new Item("Beer",1, 5.99,3,5,"barnesandnobles", 0 ), 3);
//		p.getRegisters().get(0).addItemToSale(new Item("Beer",1, 5.99,3,5,"barnesandnobles", 0 ), 3);
//		p.getRegisters().get(0).addItemToSale(new Item("Beer",1, 5.99,3,5,"barnesandnobles", 0 ), 3);
//		p.getRegisters().get(0).addItemToSale(new Item("Beer",1, 5.99,3,5,"barnesandnobles", 0 ), 3);
//		p.getRegisters().get(0).closeSale();
//		p.getRegisters().get(0).printSales();
		//Return entire sale
//		p.getRegisters().get(0).returnEntireSale(1);
		
		//return a set of items
		p.getRegisters().get(0).returnSetofItems(1, 1003,1003);
		
		p.getRegisters().get(0).printSales();
		System.out.println("Done");
		
		System.out.println(p.getRegisters().get(0).getSale(1));
		System.out.println(p.getRegisters().get(0).getSale(2));
	}
}
