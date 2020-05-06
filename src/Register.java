import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Register {
	private int id;
	private List<User> users;
	private double salePrice;
	private Date date;
	private List<Sale> sales;
	private User currUser;
	private Sale currSale;
	private long saleId = 0;

	public void newSale() {
		currSale = new Sale();
		currSale.setCashier(currUser);
		currSale.setId(++saleId);
	}

	public User getCurrUser() {
		return currUser;
	}

	public void setCurrUser(User currUser) {
		this.currUser = currUser;
	}

	public boolean addItemToSale(String item, int quantity) {
		currSale.addItem(item, quantity);
		return true;
	}

	public void closeSale() {
		currSale.setDate();
		currSale.setId(saleId);
		int oldInventoryCount = 0;
		int newInventoryCount = 0; 
		String name;
		try {
			for(Item item: currSale.getItems() ) {
				name = item.getItemName();
				oldInventoryCount = Inventory.getInventory(name);
				Inventory.updateItemCountInFile(name, oldInventoryCount);
				newInventoryCount = oldInventoryCount - 1;
				Inventory.setInventory(item.getItemName(), newInventoryCount);
			}
			sales.add(currSale);
		}
		catch(IOException e) {
			System.out.println("ERROR");
		}
		
	}

	public boolean returnEntireSale(long saleId) {
		for(Sale sale: sales) { 
			if( sale.getId() == saleId) {
				sales.remove(sale);
				return true;
			}
		}
		return false;
	}

	public void returnSetofItems( long saleId, long ... ids ) {
		Item item;
		String name;
		int oldInventoryCount;
		int newInventoryCount;
		for(Sale sale: sales) {
			if( sale.getId() == saleId) {
				try {
					for(long id: ids) {
						item = sale.removeItem(id);
						name = item.getItemName();
						oldInventoryCount = Inventory.getInventory(name);
						Inventory.updateItemCountInFile(name, oldInventoryCount);
						newInventoryCount = oldInventoryCount + 1;
						Inventory.setInventory(item.getItemName(), newInventoryCount);
					}
				}
				catch(IOException e) {
					System.out.println("ERROR");
				}
			}
		}
	}

	public Register() {
		id = 0;
		users = new ArrayList<User>();
		salePrice = 0;
		date = new Date();
		sales = new ArrayList<>();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<User> getUsers() {
		return users;
	}
	public void setCashiers(List<User> users) {
		this.users = users;
	}
	public double getSalePrice() {
		return currSale.getSalePrice();
	}
	public void setSalePrice(double salePrice) {
		this.salePrice = salePrice;
		currSale.setSalePrice(salePrice);
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	public void setSales(List<Sale> sales) {
		this.sales = sales;
	}

	public Sale getCurrSale() {
		return currSale;
	}

	public void setCurrSale(Sale currSale) {
		this.currSale = currSale;
	}

	public long getSaleId() {
		return saleId;
	}

	public void setSaleId(long saleId) {
		this.saleId = saleId;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public void printSales() {
		for(Sale sale: sales) {
			System.out.println(sale + "\n");
		}
	}

	public List<Sale> getSales() {
		return sales;
	}
	
	public Sale getSale(long id) {
		for (Sale sale: sales) {
			if(sale.getId() == id) {
				return sale;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return "Register [id=" + id + ", cashiers=" + users + ", salePrice=" + salePrice + ", date=" + date
				+ ", sales=" + sales + "]";
	}

}