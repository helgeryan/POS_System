import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Sale {
	private List<Item> items;
	private double salePrice;
	private Date date;
	private long id = 1;
	private User cashier;

	public User getCashier() {
		return cashier;
	}

	public void setCashier(User cashier) {
		this.cashier = cashier;
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

	public void addItem(String name, int quantity) {
		String[] inventory = Inventory.getItem(name);
		String[] itemData = inventory[0].split(",");
		System.out.println(itemData[0]);
		Item item;
		int id;
		double price;
		int countOnHand;
		int threshold;
		String desc;
		int onOrder;
		for (int i = 0; i < quantity; ++i) {
			name= itemData[0];
			id = Integer.parseInt(itemData[1]);
			price = Double.parseDouble(itemData[2]);
			countOnHand = Integer.parseInt(itemData[3]);
			threshold = Integer.parseInt(itemData[4]);
			desc = itemData[5];
			onOrder = Integer.parseInt(itemData[6]);
			item = new Item(name,id, price, countOnHand, threshold, desc, onOrder);
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

	public Item removeItem(long id) {
		for(int i = 0; i < items.size(); ++i ) {
			if(id == items.get(i).getItemID()) {
				salePrice -= items.get(i).getPrice();
				Item retVal = items.remove(i);
				return retVal;
			}
		}
		return null;
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
		String retString = "Sale at Date/Time " + date + "\n";
		retString += "Sale ID: " + id + "\n";
		retString += "Cashier: " + cashier.getUsername() + "\n";

		for (Item item: items) {
			retString += item.getItemName() + " $" + item.getPrice() + "\n";
		}
		retString += "Total price: $" + (int)(this.getSalePrice()*100)/100.0;
		return retString ;
	}
}