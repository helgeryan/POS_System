public class ItemInSale {
    private String itemName;
    private int itemID;
    private int qtyBought;
    private int price;

    public ItemInSale(String ItemName, int ItemID, int QtyBought, int Price){
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

    public int getPrice() {
        return price;
    }
}

