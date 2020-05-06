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
		currSale.setCashier(currUser);
	}

	public User getCurrUser() {
		return currUser;
	}

	public void setCurrUser(User currUser) {
		this.currUser = currUser;
	}

	public boolean addItemToSale(Item item, int quantity) {
		currSale.addItem(item, quantity);
		return true;
	}

	public void closeSale() {
		currSale.setDate();
		currSale.setId(saleId);
		sales.add(currSale);
	}

	public void makeReturnComment(long saleId, String comment){
		for(Sale sale: sales) {
			if( sale.getId() == saleId) {
				sale.setComments(comment);
			}
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

	public void removeItemFromSale(int itemID){
		currSale.removeItem(itemID);
	}

	public void returnSingleItem(long saleId, int lineItemid) {
		for(Sale sale: sales) {
			if( sale.getId() == saleId) {
				sale.removeItem(lineItemid);
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
		if(getCurrSale() != null){
			return currSale.getId();
		}
		else return 0;
	}

	public void setSaleId(long saleId) {
		this.currSale.setId(saleId);
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