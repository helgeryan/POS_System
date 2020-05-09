import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TestMain {

	public static void main(String[] args) throws FileNotFoundException, ParseException {
		// TODO Auto-generated method stub
		POS_System p = new POS_System();
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
		
		
		
		p.getRegisters().get(0).setCurrUser(new User(1, "Ryan", "Helgeson", "cashier", "user2", "password2"));
		p.getRegisters().get(0).newSale();
		
		p.getRegisters().get(0).addItemToSale(new Item("Tool",1, 5.99,3,5,"barnesandnobles", 0 ), 2);
		p.getRegisters().get(0).addItemToSale(new Item("Thing",1, 5.99,3,5,"barnesandnobles", 0 ), 2);
		p.getRegisters().get(0).addItemToSale(new Item("Book",1, 5.99,3,5,"barnesandnobles", 0 ), 2);
		p.getRegisters().get(0).addItemToSale(new Item("Book",1, 5.99,3,5,"barnesandnobles", 0 ), 2);
		p.getRegisters().get(0).closeSale();
		
		p.getRegisters().get(0).newSale();
		p.getRegisters().get(0).addItemToSale(new Item("This",1, 5.99,3,5,"barnesandnobles", 0 ), 3);
		p.getRegisters().get(0).addItemToSale(new Item("That",1, 5.99,3,5,"barnesandnobles", 0 ), 3);
		p.getRegisters().get(0).addItemToSale(new Item("Book",1, 5.99,3,5,"barnesandnobles", 0 ), 3);
		p.getRegisters().get(0).addItemToSale(new Item("Book",1, 5.99,3,5,"barnesandnobles", 0 ), 3);
		p.getRegisters().get(0).closeSale();
		p.getRegisters().get(0).printSales();
		
		
		
		//return a set of items
		//p.getRegisters().get(0).returnSetofItems(2, 1,1,1,1,1,1);
		
		p.getRegisters().get(0).printSales();
		System.out.println("Done");
		
		System.out.println(p.getRegisters().get(0).getSale(1));
		System.out.println(p.getRegisters().get(0).getSale(2));
		
		Reports r = new Reports();
		r.printCurrentInventory();
		System.out.println();
		
		
		
		r.printSales(p,"05-09-2020");
		
		
		
	
		
		
		
		
				
		
		
		
	}
}
