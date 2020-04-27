public class Item {
	int itemID;
	private String itemName;
	private String itemDesc;
	private double price;
	private int threshold;
	private int countOnHand;
	private int countOnOrder;
	private String supplier;

	public Item(int ItemID, String ItemName, String ItemDesc, int CountOnHand, int CountOnOrder, int Threshold, double Price, String Supplier){
		this.itemID = ItemID;
		this.itemName = ItemName;
		this.itemDesc = ItemDesc;
		this.countOnHand = CountOnHand;
		this.countOnOrder = CountOnOrder;
		this.price = Price;
		this.threshold = Threshold;
		this.supplier = Supplier;
	}



	public String getName() {
		return itemName;
	}
	public void setName(String ItemName) {
		this.itemName = ItemName;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getThreshold() {
		return threshold;
	}
	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public int getItemID() {
		return itemID;
	}

	public void setItemID(int itemID) {
		this.itemID = itemID;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public int getCountOnHand() {
		return countOnHand;
	}

	public void setCountOnHand(int countOnHand) {
		this.countOnHand = countOnHand;
	}

	public int getCountOnOrder() {
		return countOnOrder;
	}

	public void setCountOnOrder(int countOnOrder) {
		this.countOnOrder = countOnOrder;
	}

	public String saleToString() {
		return "Item [itemName=" + itemName + ", price=" + price + "]";
	}

	@Override
	public String toString() {
		return "Item{" +
				"itemID=" + itemID +
				", itemName='" + itemName + '\'' +
				", itemDesc='" + itemDesc + '\'' +
				", price=" + price +
				", threshold=" + threshold +
				", countOnHand=" + countOnHand +
				", countOnOrder=" + countOnOrder +
				", supplier='" + supplier + '\'' +
				'}';
	}
}

