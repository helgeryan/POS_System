import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Sale {
	private List<Item> items;
	private double salePrice;
	private Date date;
	private long id = 1;
	private User cashier;

	public Sale() {
		items = new ArrayList<Item>();
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
		System.out.println(salePrice);
	}

	public Date getDate() {
		return date;
	}

	public void setDate() {
		this.date = new Date();
	}

	public void addItem(Item item, int quantity) {
		for (int i = 0; i < quantity; ++i) {
			salePrice += item.getPrice();
			items.add(item);
		}
	}

	public void removeItem(Item item) {
		for(int i = 0; i < items.size(); ++i ) {
			if(item.getItemID() == items.get(i).getItemID()) {
				salePrice -= items.get(i).getPrice();
				items.remove(i);
				break;
			}
		}
	}

	public void printSale() {
		for( int i = 0; i < items.size(); ++i) {
			System.out.println( items.get(i).saleToString());
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
		for (Item item: items) {
			retString += item.getItemName() + " $" + item.getPrice() + "\n";
		}
		retString += "Total price: $" + (int)(this.getSalePrice()*100)/100.0;
		return retString ;
	}
}