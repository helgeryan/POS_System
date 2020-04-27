import java.util.ArrayList;
import java.util.List;

public class Sale {
	private List<Item> items;

	public Sale() {
		items = new ArrayList<Item>();
	}
	
	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public void addItem(Item item) {
		items.add(item);
	}
	
	public void removeItem(Item item) {
		items.remove(item);
	}
	
	public void printSale() {
		for( int i = 0; i < items.size(); ++i) {
			System.out.println( items.get(i).saleToString());
		}
	}
	
	@Override
	public String toString() {
		return "Sale [items=" + items + "]";
	}
}
