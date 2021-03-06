public class Item {
    int itemID;
    private String itemName;
    private double price;
    private int threshold;
    private int countOnHand;
    private int countOnOrder;
    private String supplier;

    public Item(String ItemName,int ItemID, double Price, int CountOnHand, int Threshold, String Supplier, int CountOnOrder){
        this.itemID = ItemID;
        this.itemName = ItemName;
        this.countOnHand = CountOnHand;
        this.countOnOrder = CountOnOrder;
        this.price = Price;
        this.threshold = Threshold;
        this.supplier = Supplier;
    }

    public int getItemID() {
        return itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }


    public int getCountOnHand() {
        return countOnHand;
    }

    public void setCountOnHand(int countOnHand) {
        this.countOnHand = countOnHand;
    }

    public int getCountOnOrder() {
        return countOnOrder;
    }

    public void setCountOnOrder(int countOnOrder) {
        this.countOnOrder = countOnOrder;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemName='" + itemName + '\'' +
                ", itemID=" + itemID +
                ", price= " + price +
                ", countOnHand=" + countOnHand +
                ", threshold=" + threshold +
                ", supplier=" + supplier +
                ", countOnOrder=" + countOnOrder +
                '}';
    }
    public String toStringForSaving(){
        return "\n"+ itemName + ","+itemID+","+price+","+countOnHand+","+threshold+","+supplier+","+countOnOrder;
    }
}
