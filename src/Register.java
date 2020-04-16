import java.util.Date;
import java.util.List;

public class Register {
	private int id;	
	private List<User> users;
	private double salePrice;
	private Date date;
	private List<Sale> sales;
	
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
	@Override
	public String toString() {
		return "Register [id=" + id + ", cashiers=" + users + ", salePrice=" + salePrice + ", date=" + date
				+ ", sales=" + sales + "]";
	}

}
