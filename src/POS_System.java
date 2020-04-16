import java.util.List;

public class POS_System {
	private List<Register> registers;
	private List<Register> users;
	private String companyName;
	private Inventory inventory;
	public List<Register> getRegisters() {
		return registers;
	}
	public void setRegisters(List<Register> registers) {
		this.registers = registers;
	}
	public List<Register> getUsers() {
		return users;
	}
	public void setUsers(List<Register> users) {
		this.users = users;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Inventory getInventory() {
		return inventory;
	}
	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}
}
