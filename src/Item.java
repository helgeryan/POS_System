public class Item {
	private String name;
	private double price;
	private int threshold;
	private int quantity;
	private String supplier;
	private int pendingOrders;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public int getPendingOrders() {
		return pendingOrders;
	}
	public void setPendingOrders(int pendingOrders) {
		this.pendingOrders = pendingOrders;
	}
	
}

