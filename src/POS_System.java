import java.util.List;

public class POS_System {
	private List<Register> registers;
	private List<Register> cashiers;
	private String companyName;
	private Inventory inventory;
	public List<Register> getRegisters() {
		return registers;
	}
	public void setRegisters(List<Register> registers) {
		this.registers = registers;
	}
	public List<Register> getCashiers() {
		return cashiers;
	}
	public void setCashiers(List<Register> cashiers) {
		this.cashiers = cashiers;
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
