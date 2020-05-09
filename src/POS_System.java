import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class POS_System {
	private List<Register> registers;
	private List<User> users;
	private String companyName;
	private Inventory inventory;

	public POS_System () throws FileNotFoundException {
		registers = new ArrayList<Register>();
		users = new UserList().getUserList();
		companyName = "Group2 Point of Sale System";
		inventory = new Inventory();
	}

	public List<Register> getRegisters() {
		return registers;
	}
	public void setRegisters(List<Register> registers) {
		this.registers = registers;
	}

	public void addRegister(Register register){
		registers.add(register);
	}

	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
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