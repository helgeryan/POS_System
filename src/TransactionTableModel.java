import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class TransactionTableModel extends AbstractTableModel {

    String[] colName = {"Item ID", "Item Name", "QTY", "Price"};
    List<ItemInSale> itemsInSale;

    public TransactionTableModel(){
        itemsInSale = new ArrayList<>();
    }

    @Override
    public String getColumnName(int column) {
        return colName[column];
    }

    public void setData(List<ItemInSale> ItemsInSale){
        this.itemsInSale = ItemsInSale;
    }

    @Override
    public int getRowCount() {
        return itemsInSale.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int row, int col) {
        ItemInSale item = itemsInSale.get(row);

        switch (col){
            case 0: return item.getItemID();
            case 1: return item.getItemName();
            case 2: return item.getQtyBought();
            case 3: return item.getPrice();
        }


        return null;
    }
}
