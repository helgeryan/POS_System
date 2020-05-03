public class ItemInSale {
    private String itemName;
    private int itemID;
    private int qtyBought;
    private double price;

    public ItemInSale(int ItemID, String ItemName, int QtyBought, double Price){
        this.itemName = ItemName;
        this.itemID = ItemID;
        this.qtyBought = QtyBought;
        this.price = Price;
    }

    public String getItemName() {
        return itemName;
    }

    public int getItemID() {
        return itemID;
    }

    public int getQtyBought() {
        return qtyBought;
    }

    public double getPrice() {
        return price;
    }
}

