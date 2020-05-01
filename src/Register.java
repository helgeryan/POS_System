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
		currSale.setId(++saleId);
	}
	
	public boolean addItemToSale( Item item, int quantity) {
		currSale.addItem(item, quantity);
		return true;
	}
	
	public void closeSale() {
		currSale.setDate();
		currSale.setId(saleId);
		currSale.setCashier(currUser);
		sales.add(currSale);
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
	
	public void returnSetofItems( long saleId, Item ... items ) {
		for(Sale sale: sales) {
			if( sale.getId() == saleId) {
				for(Item item: items) {
					sale.removeItem(item);
				}
			}
		}
	}
	
	public Register() {
		id = 0;
		users = new ArrayList<User>();
		salePrice = 0;
		date = new Date();
		sales = new ArrayList<Sale>();
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
		return salePrice;
	}
	public void setSalePrice(double salePrice) {
		this.salePrice = salePrice;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public List<Sale> getSales() {
		return sales;
	}

	public void setSales(List<Sale> sales) {
		this.sales = sales;
	}

	public User getCurrUser() {
		return currUser;
	}

	public void setCurrUser(User currUser) {
		this.currUser = currUser;
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
	
	@Override
	public String toString() {
		return "Register [id=" + id + ", cashiers=" + users + ", salePrice=" + salePrice + ", date=" + date
				+ ", sales=" + sales + "]";
	}

}
