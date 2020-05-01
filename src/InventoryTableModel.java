import javax.swing.table.AbstractTableModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InventoryTableModel extends AbstractTableModel {

    private List<Item> items = new ArrayList<>();

    private String[] colNames = {"Item Name", "Item ID", "Price", "Count on hand", "Threshold", "Supplier", "On Order"};
    String[] tableData;

    public InventoryTableModel() throws FileNotFoundException {
        tableData = Inventory.getTableData();

        setData();
    }

    public void setData() throws FileNotFoundException {
        Scanner row = new Scanner(new File("src/inventory.txt"));
        String rowParse = row.nextLine();
        for(int i = 0; i < tableData.length; i++){
            rowParse = row.nextLine();
            Scanner sc = new Scanner(String.valueOf(rowParse));
            sc.useDelimiter(",");

            Item itemToAdd = new Item(sc.next(), Integer.parseInt(sc.next()), Double.parseDouble(sc.next()), Integer.parseInt(sc.next()), Integer.parseInt(sc.next()), sc.next(), Integer.parseInt(sc.next()));
            items.add(itemToAdd);
        }
    }

    @Override
    public int getRowCount() {
        return items.size();
    }

    @Override
    public int getColumnCount() {
        return 7;
    }

    @Override
    public String getColumnName(int column) {
        return colNames[column];
    }

    @Override
    public Object getValueAt(int row, int col) {

        Item item = items.get(row);

        switch (col){
            case 0:
                return item.getItemName();
            case 1:
                return item.getItemID();
            case 2:
                return item.getPrice();
            case 3:
                return item.getCountOnHand();
            case 4:
                return item.getThreshold();
            case 5:
                return item.getSupplier();
            case 6:
                return item.getCountOnOrder();
        }
        return null;
    }
}
