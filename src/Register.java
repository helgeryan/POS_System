import java.util.Date;
import java.util.List;

public class Register {
	private int id;	
	private List<Cashier> cashiers;
	private double salePrice;
	private Date date;
	private List<Sale> sales;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<Cashier> getCashiers() {
		return cashiers;
	}
	public void setCashiers(List<Cashier> cashiers) {
		this.cashiers = cashiers;
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

}
