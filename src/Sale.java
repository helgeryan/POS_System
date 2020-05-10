import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Sale {
	private List<Item> items;
	private double salePrice;
	private Date date;
	private long id = 0;
	private String cashier;
	private int registerId;
	private String comment = "Items returned from this transaction: ";

	public String getCashier() {
		return cashier;
	}

	public int getRegisterId() {
		return registerId;
	}

	public void setRegisterId(int registerId) {
		this.registerId = registerId;
	}

	public void setComments(String Comment){
		this.comment = comment + "\n" +Comment;
	}
	public String getComment(){
		return this.comment;
	}

	public void setCashier(User cashier) {
		this.cashier = cashier.getUsername();
	}

	public Sale() {
		items = new ArrayList<>();
		salePrice = 0.0;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public double getSalePrice() {
		return salePrice;

	}

	public void setSalePrice(double salePrice) {
		this.salePrice = salePrice;
	}

	public void addItem(Item item, int quantity) {
		for (int i = 0; i < quantity; ++i) {
			salePrice += item.getPrice();
			items.add(item);
		}
	}

	public Date getDate() {
		return date;
	}

	public void setDate() {
		this.date = new Date();
	}

	public void removeItem(int LineItemId) {
		salePrice -= items.get(LineItemId).getPrice();
		items.remove(LineItemId);
	}

	public void printSale() {
		for( int i = 0; i < items.size(); ++i) {
			//System.out.println( items.get(i).saleToString());
		}
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		String retString = "\nSale at Date/Time " + date + "\n";
		retString += "Register: " + registerId + "\n";
		retString += "Sale ID: " + id + "\n";
		retString += "Cashier: " + cashier + "\n";

		for (Item item: items) {
			retString += item.getItemName() + " $" + item.getPrice() + "\n";
		}
		retString += "Total price: $" + (int)(this.getSalePrice()*100)/100.0 + "\n";
		retString += comment +"\n";
		return retString ;
	}

}