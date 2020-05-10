import javax.swing.table.AbstractTableModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InventoryTableModel extends AbstractTableModel {

    private List<Item> items = new ArrayList<>();

    private String[] colNames = {"Item Name", "Item ID", "Price", "On Hand", "Threshold", "Supplier", "On Order"};


    public InventoryTableModel() throws FileNotFoundException {
        setData();
    }

    public void setData() throws FileNotFoundException {
        items.clear();

        String[] itemsArray = Inventory.getTableData();

        for (int i = 0; i < itemsArray.length; i++){
            String row = itemsArray[i];
            row = row.replaceAll("\\r|\\n", "");
            Scanner rowParser = new Scanner(row);
            rowParser.useDelimiter(",");
            items.add(new Item(rowParser.next(), Integer.parseInt(rowParser.next()),Double.parseDouble(rowParser.next()),Integer.parseInt(rowParser.next()), Integer.parseInt(rowParser.next()),rowParser.next(),Integer.parseInt(rowParser.next())));
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
