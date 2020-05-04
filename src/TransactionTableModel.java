import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class TransactionTableModel extends AbstractTableModel {

    String[] colName = {"Item ID", "Item Name", "Price"};
    List<Item> itemsInSale;

    public TransactionTableModel(){
        itemsInSale = new ArrayList<>();
    }

    @Override
    public String getColumnName(int column) {
        return colName[column];
    }

    public void clearData(){
        itemsInSale.clear();
    }

    public void addItemToSaleTable(Item item, int qty){
        for (int i = 0; i< qty; i++){
            itemsInSale.add(item);
        }
    }

    public void populateFromSale(Sale currSale){
        itemsInSale = currSale.getItems();
    }


    @Override
    public int getRowCount() {
        return itemsInSale.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int row, int col) {
        Item item = itemsInSale.get(row);

        switch (col){
            case 0: return item.getItemID();
            case 1: return item.getItemName();
            case 2: return item.getPrice();
        }


        return null;
    }
}
