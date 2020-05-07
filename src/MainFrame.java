import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

public class MainFrame extends JFrame {

    POS_System pos_system;
    private MainPanel mainPanel;
    private RegisterInfoPanel registerInfoPanel;
    private Register register;
    private SalesPanel salesPanel;
    private User currUser;
    private String transactionType = "Inactive";

    public MainFrame(POS_System POS_System, Register Register, User CurrUser) throws FileNotFoundException {
        super("Point of Sale System");

        pos_system = new POS_System();
        pos_system = POS_System;

        register = new Register();
        register = Register;

        currUser = CurrUser;
        register.setCurrUser(currUser);

        salesPanel = new SalesPanel();

        setLayout(new BorderLayout());

        mainPanel = new MainPanel();

        registerInfoPanel = new RegisterInfoPanel();

        add(mainPanel, BorderLayout.SOUTH);
        add(salesPanel, BorderLayout.CENTER);
        add(registerInfoPanel, BorderLayout.NORTH);

        setSize(1000, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    class RegisterInfoPanel extends JPanel {

        LocalDate date = LocalDate.now();
        //LocalTime time = LocalTime.now();

        List<Sale> sales = new ArrayList<>();
        long nextSalesID;

        private JLabel registerIDLabel;
        private JLabel transIDLabel;

        private JLabel cashierLabel;

        private JLabel dateLabel;

        private JLabel transactionTypeLabel;


        Border blackline = BorderFactory.createLineBorder(Color.black);
        String spaces = "       ";


        public RegisterInfoPanel(){

            sales = register.getSales();

            for(int i = 0; i < sales.size(); ++i){
                nextSalesID = i;
            }

            registerIDLabel = new JLabel(spaces + "Register ID:" + register.getId() + spaces );
            registerIDLabel.setBorder(blackline);

            transIDLabel = new JLabel(spaces + "Transaction ID: " + register.getSaleId() + spaces);
            transIDLabel.setBorder(blackline);

            cashierLabel = new JLabel(spaces + "Cashier: "+ register.getCurrUser().getUsername()+ spaces);
            cashierLabel.setBorder(blackline);

            dateLabel = new JLabel(spaces + date + spaces);
            dateLabel.setBorder(blackline);

            transactionTypeLabel = new JLabel(spaces + transactionType + spaces);
            transactionTypeLabel.setBorder(blackline);

            setVisible(true);

            layoutSalesComponents();
        }

        private void refreshRegisterInfoPanel(){

            if(transactionType.equals("Sale")){
                transIDLabel.setText(spaces + "Transaction ID: " + register.getSaleId() + spaces);
            }
            else if(transactionType.equals("Return")){
                transIDLabel.setText(spaces +"Transaction ID: " + salesPanel.salesBtnPanel.saleToBeRecalled + spaces);
            }
            else{
                transIDLabel.setText(spaces +"Transaction ID: Inactive"  + spaces);
            }
            registerIDLabel.setText(spaces + "Register ID:" + register.getId() + spaces);
            cashierLabel.setText(spaces + "Cashier: "+ register.getCurrUser().getUsername()+ spaces);
            dateLabel.setText(spaces + date + spaces);
            transactionTypeLabel.setText(spaces + transactionType + spaces);
        }

        private void layoutSalesComponents(){
            setLayout(new GridBagLayout());
            GridBagConstraints gc = new GridBagConstraints();

            gc.weightx = 1;
            gc.weighty = .1;
            gc.gridy = 0;

            ////// Top Row ///////

            gc.gridx = 0;
            gc.anchor = GridBagConstraints.CENTER;
            add(registerIDLabel, gc);

            gc.gridx = 1;
            gc.anchor = GridBagConstraints.CENTER;
            add(transIDLabel, gc);

            gc.gridx = 2;
            gc.anchor = GridBagConstraints.CENTER;
            add(cashierLabel, gc);

            gc.gridx = 3;
            gc.anchor = GridBagConstraints.CENTER;
            add(dateLabel, gc);

            gc.gridx = 4;
            gc.gridwidth = 2;
            gc.anchor = GridBagConstraints.CENTER;
            add(transactionTypeLabel, gc);
        }
    }

    class MainPanel extends JPanel {

        private JButton invBtn;
        private JButton userBtn;
        private JButton returnsBtn;
        private JButton reportingbtn;
        public MainPanel(){

            Dimension dim = getPreferredSize();
            dim.width =250;
            dim.height = 50;
            setPreferredSize(dim);

            invBtn = new JButton("Inventory");
            userBtn = new JButton("User Management");
            returnsBtn = new JButton("Returns");
            reportingbtn = new JButton("Reporting");
            setLayout(new GridBagLayout());

            GridBagConstraints gc = new GridBagConstraints();

            gc.gridy = 0;
            gc.weightx = 1;
            gc.weighty = .5;


            gc.gridx = 1;
            add(invBtn, gc);

            invBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent actionEvent) {
                    InventoryDialog inventoryDialog = null;
                    try {
                        inventoryDialog = new InventoryDialog();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    inventoryDialog.setVisible(true);
                }
            });

            gc.gridx = 2;
            add(userBtn, gc);

            userBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent actionEvent) {
                    try {
                        UserDialog userDialog = new UserDialog();
                        userDialog.setVisible(true);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });

            gc.gridx = 3;
            add(returnsBtn, gc);

            returnsBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent actionEvent) {

                    if(transactionType.equals("Sale")){
                        JOptionPane.showMessageDialog(MainFrame.this, "Please finish the current transaction before starting a return.", "Return Errorr", JOptionPane.OK_OPTION);
                    }
                    else{
                        ReturnsDialog returnsDialog = new ReturnsDialog();
                        returnsDialog.setVisible(true);
                    }

                }
            });

            gc.gridx = 4;
            add(reportingbtn, gc);
        }
    }

    class SalesPanel extends JPanel{
        private SalesBtnPanel salesBtnPanel;
        private SalesTablePanel salesTablePanel;
        private ArrayList<Item> items;
        private TransactionTableModel tableModel;
        private int selectedRow;
        private int selectedLineItem;
        private String selectedItemName;
        private double selectedItemPrice;
        private long selectedItemID;

        public SalesPanel(){
            salesBtnPanel = new SalesBtnPanel();
            salesTablePanel = new SalesTablePanel();
            items = new ArrayList<>();
            populateItems();

            setLayout(new BorderLayout());
            add(salesTablePanel, BorderLayout.CENTER);
            add(salesBtnPanel, BorderLayout.EAST);

            salesBtnPanel.toggleControls();

            setSize(950,700);
            setVisible(true);
        }

        private void populateItems(){
            String[] itemsArray = Inventory.getTableData();

            for (int i = 0; i < itemsArray.length; i++){
                String row = itemsArray[i];
                row = row.replaceAll("\\r|\\n", "");
                Scanner rowParser = new Scanner(row);
                rowParser.useDelimiter(",");
                items.add(new Item(rowParser.next(), Integer.parseInt(rowParser.next()),Double.valueOf(rowParser.next()),Integer.parseInt(rowParser.next()), Integer.parseInt(rowParser.next()),rowParser.next(),Integer.parseInt(rowParser.next())));

            }
        }

        class SalesTablePanel extends JPanel{
            private JTable table;

            public SalesTablePanel(){
                tableModel = new TransactionTableModel();
                table = new JTable(tableModel);

                table.addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent e) {
                        selectedRow = table.rowAtPoint(e.getPoint());
                        System.out.println(selectedRow);
                        selectedLineItem = (int) tableModel.getValueAt(selectedRow, 0);
                        selectedItemName = (String) tableModel.getValueAt(selectedRow, 2);
                        selectedItemPrice = (double) tableModel.getValueAt(selectedRow, 3);
                        selectedItemID = Long.parseLong(String.valueOf(tableModel.getValueAt(selectedRow, 1)));
                    }
                });

                setLayout(new BorderLayout());
                setSize(700,600);
                add(new JScrollPane(table), BorderLayout.CENTER);
            }

            private void refresh() {
                tableModel.fireTableDataChanged();
            }
            private void clear(){
                tableModel.clearData();
                refresh();
            }

            private void addItemsForReturn(Sale currSale){
                tableModel.clearData();
                tableModel.populateFromSale(currSale);
                refresh();
            }

            private void populateTable(Sale currSale){
                tableModel.clearData();
                tableModel.populateFromSale(currSale);
                refresh();
            }

            private void removeItemFromTable(int LineItemID){
                tableModel.removeItemFromTable(LineItemID);
                refresh();
            }
        }

        class SalesBtnPanel extends JPanel{
            private JButton newSaleBtn;
            private JButton removeItemBtn;
            private JButton processPaymentBtn;
            private JButton endTransactionBtn;

            private JLabel addItemLabel;
            private JTextField addItemField;
            private JLabel qtyLabel;
            private JTextField qtyField;
            private JButton addItemBtn;

            private JLabel totalLabel;
            private JTextField totalField;
            private JLabel amountDueLabel;
            private JTextField amountDueField;
            private double saleTotal;
            private double amountDue;
            private long saleToBeRecalled;


            public SalesBtnPanel(){
                newSaleBtn = new JButton("New Sale");
                newSaleBtn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent actionEvent) {
                        register.newSale();
                        transactionType = "Sale";
                        toggleControls();
                        addItemBtn.setEnabled(true);
                        addItemField.setEnabled(true);
                        qtyField.setEnabled(true);
                        registerInfoPanel.refreshRegisterInfoPanel();
                        salesTablePanel.clear();
                        System.out.println(register.getSaleId());
                        amountDue = 0;
                    }
                });


                addItemLabel = new JLabel("Item ID: ");
                addItemField = new JTextField(10);


                qtyLabel = new JLabel("QTY: ");
                qtyField = new JTextField(10);

                addItemBtn = new JButton("Add Item");
                addItemBtn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent actionEvent) {
                        int itemId = Integer.parseInt(addItemField.getText()) ;
                        int qty = Integer.parseInt(qtyField.getText());
                        Item item;
                        for(Item Item: items){
                            if(itemId == Item.getItemID()){
                                item = Item;
                                register.addItemToSale(item, qty);
                                tableModel.addItemToSaleTable(item, qty);
                                salesTablePanel.refresh();
                                refreshTotals();
                                addItemField.setText(null);
                                qtyField.setText(null);
                            }
                        }

                    }
                });

                removeItemBtn = new JButton("Remove Item");

                removeItemBtn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent actionEvent) {

                        register.removeItemFromSale(selectedLineItem);
                        salesTablePanel.removeItemFromTable(selectedLineItem);
                        salesTablePanel.refresh();
                        System.out.println(register.getCurrSale());
                        register.setSalePrice(register.getCurrSale().getSalePrice());
                        refreshTotals();


                    }
                });

                processPaymentBtn = new JButton("Process Payment");

                processPaymentBtn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent actionEvent) {
                        double amountPaid = Double.parseDouble(JOptionPane.showInputDialog(MainFrame.this,
                                "Enter payment amount","Make Payment", JOptionPane.INFORMATION_MESSAGE));
                        processPayment(amountPaid);
                    }
                });

                endTransactionBtn = new JButton("End Transaction");

                endTransactionBtn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent actionEvent) {
                        terminateTransaction();
                    }
                });

                totalLabel = new JLabel("Total: ");
                totalField = new JTextField(5);
                totalField.setEditable(false);

                amountDueLabel = new JLabel("Amount due: ");
                amountDueField = new JTextField(5);
                amountDueField.setEditable(false);



                setSize(200,700);
                setVisible(true);
                setLayout();
            }

            private void terminateTransaction(){

                if(amountDue == 0){
                    try {
                        adjustInventory();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    register.closeSale();
                    amountDue = 0;
                    System.out.println(register.getSale(register.getSaleId()).toString());
                }
                else if(amountDue<0){
                    JOptionPane.showMessageDialog(MainFrame.this, "You owe the customer $" + Math.abs(amountDue) + " back in change.", "Change Due", JOptionPane.INFORMATION_MESSAGE);
                    register.closeSale();
                    amountDue = 0;
                }
                else if(amountDue > 0){
                    JOptionPane.showMessageDialog(MainFrame.this, "Customer must pay remaining balance before closing the sale.", "Insufficient Payment", JOptionPane.ERROR_MESSAGE);
                }

                System.out.println(register.toString());
                salesTablePanel.clear();
                totalField.setText(null);
                amountDueField.setText(null);
                transactionType = "Inactive";
                toggleControls();
                registerInfoPanel.refreshRegisterInfoPanel();
            }



            private void adjustInventory() throws IOException {
                List<Item> items = register.getCurrSale().getItems();
                Hashtable<String, Integer> oItemHash = new Hashtable<>();
                Enumeration itemNames;

                for (Item item: items) {
                    if (oItemHash.containsKey(item.getItemName())){
                        int count = oItemHash.get(item.getItemName());
                        oItemHash.put(item.getItemName(), ++count);
                    }
                    else{
                        oItemHash.put(item.getItemName(),1);
                    }
                }

                itemNames = oItemHash.keys();

                while(itemNames.hasMoreElements()){
                    String key = (String) itemNames.nextElement();
                    int newCount = (Inventory.getInventory(key) - oItemHash.get(key));

                    System.out.println(key + ", " + newCount);
                    Inventory.updateItemCountInFile(key, newCount);
                }
            }

            private void processPayment(double amountPaid){
                DecimalFormat df = new DecimalFormat("###.##");

                amountDue = Double.parseDouble(df.format(amountDue - amountPaid));

                amountDueField.setText(String.valueOf(amountDue));
            }

            private void refreshTotals(){
                DecimalFormat df = new DecimalFormat("###.##");
                saleTotal = Double.parseDouble(df.format(register.getSalePrice()));
                if(transactionType.equals("Sale")){
                    amountDue = saleTotal;
                }
                totalField.setText(String.valueOf(saleTotal));
                amountDueField.setText(String.valueOf(amountDue));
            }

            private void toggleControls(){
                if(transactionType.equals("Inactive")){
                    addItemField.setEnabled(false);
                    qtyField.setEnabled(false);
                    addItemBtn.setEnabled(false);
                    removeItemBtn.setEnabled(false);
                    processPaymentBtn.setEnabled(false);
                    endTransactionBtn.setEnabled(false);
                }
                else if(transactionType.equals("Sale")){
                    addItemField.setEnabled(true);
                    qtyField.setEnabled(true);
                    addItemBtn.setEnabled(true);
                    removeItemBtn.setEnabled(true);
                    processPaymentBtn.setEnabled(true);
                    endTransactionBtn.setEnabled(true);
                }

            }

            private void setLayout(){
                setLayout(new GridBagLayout());
                GridBagConstraints gc = new GridBagConstraints();

                /////// Side Column //////

                gc.weighty = .02;
                gc.gridwidth = 2;
                gc.gridx = 1;
                gc.gridy = 1;
                gc.anchor = GridBagConstraints.CENTER;
                add(newSaleBtn, gc);


                gc.weighty = .1;
                gc.gridx = 1;
                gc.gridy = 3;
                add(new JLabel(""), gc);

                gc.weighty = .01;
                gc.gridwidth = 1;
                gc.gridx = 1;
                gc.gridy = 4;
                gc.anchor = GridBagConstraints.CENTER;
                add(addItemLabel, gc);

                gc.gridx = 2;
                gc.gridy = 4;
                gc.anchor = GridBagConstraints.CENTER;
                add(qtyLabel, gc);

                gc.gridx = 1;
                gc.gridy = 5;
                gc.anchor = GridBagConstraints.CENTER;
                add(addItemField, gc);

                gc.gridx = 2;
                gc.gridy = 5;
                gc.anchor = GridBagConstraints.CENTER;
                add(qtyField, gc);

                gc.gridwidth = 2;
                gc.weighty = .02;
                gc.gridx = 1;
                gc.gridy =6;
                gc.anchor = GridBagConstraints.CENTER;
                add(addItemBtn, gc);

                gc.gridx = 1;
                gc.gridy = 7;
                gc.anchor = GridBagConstraints.CENTER;
                add(removeItemBtn, gc);

                gc.weighty = .1;
                gc.gridx = 1;
                gc.gridy = 8;
                add(new JLabel(""), gc);

                gc.weighty = .02;
                gc.gridx = 1;
                gc.gridy = 9;
                gc.anchor = GridBagConstraints.CENTER;
                add(processPaymentBtn, gc);

                gc.gridx = 1;
                gc.gridy = 10;
                gc.anchor = GridBagConstraints.CENTER;
                add(endTransactionBtn, gc);

                ///// Totals section /////


                gc.gridwidth = 1;
                gc.gridx = 1;
                gc.gridy = 11;
                gc.anchor = GridBagConstraints.LINE_END;
                add(totalLabel, gc);

                gc.gridx = 1;
                gc.gridy = 12;
                gc.anchor = GridBagConstraints.LINE_END;
                add(amountDueLabel, gc);

                gc.gridx = 2;
                gc.gridy = 11;
                gc.anchor = GridBagConstraints.LINE_START;
                add(totalField, gc);

                gc.gridx = 2;
                gc.gridy = 12;
                gc.anchor = GridBagConstraints.LINE_START;
                add(amountDueField, gc);


            }
        }
    }

    class InventoryDialog extends JDialog {

        private InventoryTablePanel inventoryTablePanel;
        private AdjInvPanel adjInvPanel;
        InventoryTableModel tableModel;
        int itemID;
        String itemName;

        public InventoryDialog() throws FileNotFoundException {
            setTitle("Inventory Maintenance");
            inventoryTablePanel = new InventoryTablePanel();
            adjInvPanel = new AdjInvPanel();

            setSize(new Dimension(800,500));
            setLocationRelativeTo(null);

            add(inventoryTablePanel, BorderLayout.WEST);
            add(adjInvPanel, BorderLayout.EAST);
        }

        class InventoryTablePanel extends  JPanel{

            private JTable table;


            public InventoryTablePanel() throws FileNotFoundException {
                tableModel = new InventoryTableModel();
                table = new JTable(tableModel);

                table.addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent e) {
                        int selectedRow = table.rowAtPoint(e.getPoint());
                        itemID = (int) tableModel.getValueAt(selectedRow, 1);
                        itemName = (String) tableModel.getValueAt(selectedRow, 0);
                        System.out.println(itemName + itemID);
                    }
                });

                setLayout(new BorderLayout());

                add(new JScrollPane(table), BorderLayout.CENTER);
            }

            private void refresh() throws FileNotFoundException {
                tableModel.setData();
                tableModel.fireTableDataChanged();
            }

        }

        class AdjInvPanel extends JPanel {
            private JButton addNewItemBtn;
            private JButton updateItemInventoryBtn;

            public AdjInvPanel(){
                Dimension dim = getPreferredSize();
                dim.width = 150;
                dim.height = 100;
                setPreferredSize(dim);
                addNewItemBtn = new JButton("Add New Item");
                //addNewItemBtn.setSize(75,25);
                updateItemInventoryBtn = new JButton("Update Item's Inventory");
                //updateItemInventoryBtn.setSize(75,25);

                addNewItemBtn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent actionEvent) {
                        try {
                            NewItemDialog newItemDialog = new NewItemDialog();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });



                updateItemInventoryBtn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent actionEvent) {
                        int newCount = Integer.parseInt(JOptionPane.showInputDialog("What is the new quantify for " + itemName + "?"));
                        try {
                            Inventory.updateItemCountInFile(itemName, newCount);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            inventoryTablePanel.refresh();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });

                layoutAdjInvComponents();
            }

            private void layoutAdjInvComponents() {

                setLayout(new GridBagLayout());
                GridBagConstraints gc = new GridBagConstraints();

                gc.weightx = 1;
                gc.weighty = .02;
                gc.gridy = 0;

                ////// First Row ///////
                gc.gridy ++;

                gc.gridx = 1;
                gc.anchor = GridBagConstraints.CENTER;
                add(addNewItemBtn, gc);

                gc.gridy ++;
                gc.anchor = GridBagConstraints.CENTER;
                add(updateItemInventoryBtn, gc);

            }
        }


        class NewItemDialog extends JDialog{

            private NewItemPanel newItemPanel = new NewItemPanel();

            NewItemDialog() throws FileNotFoundException {
                setTitle("New Item");
                setSize(500,500);
                setLocationRelativeTo(null);
                setVisible(true);

                //items = inventory.getInventoryList();

                add(newItemPanel);
            }

            private int getNextID() throws FileNotFoundException {
                InventoryTableModel tableModel = new InventoryTableModel();
                int lastRow = tableModel.getRowCount() - 1;

                return ((int) tableModel.getValueAt(lastRow, 1)) + 1;
            }

            private class NewItemPanel extends JPanel {

                private JLabel itemIDLabel;
                private JTextField itemIDField;
                private int nextID = getNextID();

                private JLabel itemNameLabel;
                private JTextField itemNameField;

                private JLabel descLabel;
                private JTextField descField;

                private JLabel countOnHandLabel;
                private JTextField countOnHandField;

                private JLabel thresholdLabel;
                private JTextField thresholdField;

                private JLabel countOnOrderLabel;
                private JTextField countonOrderField;

                private JLabel priceLabel;
                private JTextField priceField;

                private JLabel supplierLabel;
                private JTextField supplierField;

                private JButton saveBtn;

                private NewItemPanel() throws FileNotFoundException {
                    Dimension dim = getPreferredSize();
                    dim.width = 400;
                    dim.height = 350;
                    setPreferredSize(dim);

                    itemIDLabel = new JLabel("Item ID: ");
                    itemIDField = new JTextField(20);

                    itemNameLabel = new JLabel("Item Name: ");
                    itemNameField = new JTextField(20);

                    descLabel = new JLabel("Item Description: ");
                    descField = new JTextField(20);

                    countOnHandLabel = new JLabel("Count on Hand: ");
                    countOnHandField = new JTextField(20);

                    thresholdLabel = new JLabel("Threshold: ");
                    thresholdField = new JTextField(20);

                    countOnOrderLabel = new JLabel("Count on Order: ");
                    countonOrderField = new JTextField(20);

                    priceLabel = new JLabel("Price: ");
                    priceField = new JTextField(20);

                    supplierLabel = new JLabel("Supplier: ");
                    supplierField = new JTextField(20);

                    saveBtn = new JButton("Save Item");

                    saveBtn.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent actionEvent) {
                            String str = "\n" + itemNameField.getText() + "," + itemIDField.getText() + "," + priceField.getText() + ","
                                    + countOnHandField.getText() + "," + thresholdField.getText() + "," + supplierField.getText()
                                    + "," + countonOrderField.getText();
                            Inventory.appendStrToFile(str);
                            try {
                                inventoryTablePanel.refresh();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            dispose();
                        }
                    });

                    layoutNewItemComponents();
                }

                private void layoutNewItemComponents() {



                    setLayout(new GridBagLayout());
                    GridBagConstraints gc = new GridBagConstraints();

                    gc.weightx = 1;
                    gc.weighty = 1;
                    gc.gridy = 0;


                    /////  First Row   //////

                    gc.gridx = 0;
                    gc.fill = GridBagConstraints.NONE;
                    gc.anchor = GridBagConstraints.FIRST_LINE_END;
                    gc.insets = new Insets(0,0,0,5);
                    add(itemNameLabel, gc);

                    gc.gridx = 1;
                    gc.insets = new Insets(0,0,0,0);
                    gc.anchor = GridBagConstraints.FIRST_LINE_START;
                    itemIDField.setText(String.valueOf(nextID));
                    add(itemNameField, gc);

                    /////// Second Row //////////

                    gc.gridy ++;
                    gc.gridx = 0;
                    gc.anchor = GridBagConstraints.FIRST_LINE_END;
                    gc.insets = new Insets(0,0,0,5);
                    add(itemIDLabel, gc);

                    gc.gridx = 1;
                    gc.insets = new Insets(0,0,0,0);
                    gc.anchor = GridBagConstraints.FIRST_LINE_START;
                    add(itemIDField, gc);


                    /////// Third Row //////////

                    gc.gridy ++;
                    gc.gridx = 0;
                    gc.anchor = GridBagConstraints.FIRST_LINE_END;
                    gc.insets = new Insets(0,0,0,5);
                    add(countOnHandLabel, gc);

                    gc.gridx = 1;
                    gc.insets = new Insets(0,0,0,0);
                    gc.anchor = GridBagConstraints.FIRST_LINE_START;
                    add(countOnHandField, gc);

                    /////// Forth Row //////////

                    gc.gridy ++;
                    gc.gridx = 0;
                    gc.anchor = GridBagConstraints.FIRST_LINE_END;
                    gc.insets = new Insets(0,0,0,5);
                    add(thresholdLabel, gc);

                    gc.gridx = 1;
                    gc.insets = new Insets(0,0,0,0);
                    gc.anchor = GridBagConstraints.FIRST_LINE_START;
                    add(thresholdField, gc);

                    /////// Fifth Row //////////

                    gc.gridy ++;
                    gc.gridx = 0;
                    gc.anchor = GridBagConstraints.FIRST_LINE_END;
                    gc.insets = new Insets(0,0,0,5);
                    add(countOnOrderLabel, gc);

                    gc.gridx = 1;
                    gc.insets = new Insets(0,0,0,0);
                    gc.anchor = GridBagConstraints.FIRST_LINE_START;
                    add(countonOrderField, gc);

                    /////// Sixth Row //////////

                    gc.gridy ++;
                    gc.gridx = 0;
                    gc.anchor = GridBagConstraints.FIRST_LINE_END;
                    gc.insets = new Insets(0,0,0,5);
                    add(priceLabel, gc);

                    gc.gridx = 1;
                    gc.insets = new Insets(0,0,0,0);
                    gc.anchor = GridBagConstraints.FIRST_LINE_START;
                    add(priceField, gc);

                    /////// Seventh Row //////////

                    gc.gridy ++;
                    gc.gridx = 0;
                    gc.anchor = GridBagConstraints.FIRST_LINE_END;
                    gc.insets = new Insets(0,0,0,5);
                    add(supplierLabel, gc);

                    gc.gridx = 1;
                    gc.insets = new Insets(0,0,0,0);
                    gc.anchor = GridBagConstraints.FIRST_LINE_START;
                    add(supplierField, gc);

                    //// Eighth Row //////
                    gc.gridy ++;
                    gc.gridx = 0;
                    gc.anchor = GridBagConstraints.CENTER;
                    gc.insets = new Insets(0,0,0,5);
                    gc.gridwidth = 2;

                    add(saveBtn, gc);
                }
            }
        }
    }
    class UserDialog extends JDialog {

        private UserTable userTable = new UserTable();
        private AdjUserPanel adjUserPanel = new AdjUserPanel();
        private JTable table;
        private UserTableModel userTableModel;

        public UserDialog() throws FileNotFoundException {

            setTitle("User Maintenance");
            add(userTable, BorderLayout.WEST);
            add(adjUserPanel, BorderLayout.EAST);

            setLocationRelativeTo(null);
            setSize(750,500);
        }

        class UserTable extends JPanel {

            public UserTable() throws FileNotFoundException {

                userTableModel = new UserTableModel();
                table = new JTable(userTableModel);

                Dimension dim = getPreferredSize();
                dim.width = 500;
                dim.height = 500;
                setPreferredSize(dim);
                add(new JScrollPane(table));
            }

            private void refresh() throws FileNotFoundException {
                userTableModel.setData();
                userTableModel.fireTableDataChanged();
            }
        }

        class AdjUserPanel extends JPanel {

            private JButton changePWBtn;
            private JButton deleteUserBtn;
            private JButton newUserBtn;

            public AdjUserPanel() {
                Dimension dim = getPreferredSize();
                dim.width = 200;
                dim.height = 500;

                setPreferredSize(dim);

                changePWBtn = new JButton("Change Password");
                changePWBtn.setPreferredSize(new Dimension(150,25));
                deleteUserBtn = new JButton("Delete User");
                deleteUserBtn.setPreferredSize(new Dimension(150,25));
                newUserBtn = new JButton("Create New User");
                newUserBtn.setPreferredSize(new Dimension(150,25));

                changePWBtn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent actionEvent) {
                        try {
                            ChangePwdDialog changePwdFrame = new ChangePwdDialog();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });

                newUserBtn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent actionEvent) {
                        try {
                            NewUserDialog newUserDialog = new NewUserDialog();
                            //dispose();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });

                deleteUserBtn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent actionEvent) {
                        //This code will take the user that is selected in the table and remove them them from
                        //the user arraylist; then saveUsersFile()
                    }
                });
                layoutAdjUserComponents();

            }

            private void layoutAdjUserComponents() {

                setLayout(new GridBagLayout());
                GridBagConstraints gc = new GridBagConstraints();

                gc.weightx = 1;
                gc.weighty = .03;
                gc.gridy = 0;

                ////// First Row ///////
                gc.gridy = 0;
                gc.gridx = 1;
                gc.anchor = GridBagConstraints.LINE_START;
                add(changePWBtn, gc);

                ////// Second Row //////

                gc.gridy = 1;
                gc.gridx = 1;
                gc.anchor = GridBagConstraints.LINE_START;
                add(deleteUserBtn, gc);

                ////// Third Row ///////

                gc.gridy = 2;
                gc.gridx = 1;
                gc.anchor = GridBagConstraints.LINE_START;
                add(newUserBtn, gc);
            }
        }

        class NewUserDialog extends JDialog{
            private UserList userList = new UserList();
            private ArrayList<User> users = new ArrayList<>();

            private NewUserPanel newUserPanel = new NewUserPanel();

            NewUserDialog() throws FileNotFoundException {
                setTitle("New User");
                setSize(500, 500);
                setLocationRelativeTo(null);
                setVisible(true);
                users = userList.getUserList();

                add(newUserPanel);
            }

            private int getNextID() throws FileNotFoundException {
                int nextID = 0;
                UserList userList = new UserList();
                ArrayList<User> users = new ArrayList<>();

                users = userList.getUserList();
                for(int i = 0; i <users.size(); i++){
                    nextID = i + 2;
                }
                return nextID;
            }

            public class NewUserPanel extends JPanel{

                private JLabel empIDLabel;
                private JTextField empIDField;
                private int nextID = getNextID();

                private JLabel firstNameLabel;
                private JTextField firstNameField;

                private JLabel lastNameLabel;
                private JTextField lastNameField;

                private JLabel positionLabel;
                private JTextField positionField;

                private JLabel usernameLabel;
                private JTextField usernameField;

                private JButton saveBtn;

                public NewUserPanel() throws FileNotFoundException {
                    Dimension dim = getPreferredSize();
                    dim.width = 400;
                    dim.height = 250;
                    setPreferredSize(dim);

                    empIDLabel = new JLabel("Employee ID: ");
                    empIDField = new JTextField(10);

                    firstNameLabel = new JLabel("First Name: ");
                    firstNameField = new JTextField(10);

                    lastNameLabel = new JLabel("Last Name: ");
                    lastNameField = new JTextField(10);

                    positionLabel = new JLabel("Position: ");
                    positionField = new JTextField(10);

                    usernameLabel = new JLabel("Username: ");
                    usernameField = new JTextField(10);

                    saveBtn = new JButton("Save User");

                    saveBtn.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent actionEvent) {
                            String defaultPassword = "password1";
                            String defaultStatus = "Active";
                            User user = new User(Integer.parseInt(empIDField.getText()), firstNameField.getText(), lastNameField.getText(), positionField.getText(), usernameField.getText(), defaultPassword, defaultStatus);
                            String userString = user.toString();
                            users.add(user);
                            try {
                                userList.saveUsersFile(user);
                                System.out.println(users);
                                dispose();
                                userTable.refresh();
                                //UserDialog userDialog = new UserDialog();
                                //userDialog.setVisible(true);
                            } catch (IOException e) {
                                e.printStackTrace();
                                System.out.println("save failed.");
                            }
                        }
                    });
                    layoutNewUserComponents();
                }

                private void layoutNewUserComponents() {

                    setLayout(new GridBagLayout());
                    GridBagConstraints gc = new GridBagConstraints();

                    gc.weightx = 1;
                    gc.weighty = 1;
                    gc.gridy = 0;


                    /////  First Row   //////

                    gc.gridx = 0;
                    gc.fill = GridBagConstraints.NONE;
                    gc.anchor = GridBagConstraints.FIRST_LINE_END;
                    gc.insets = new Insets(0,0,0,5);
                    add(empIDLabel, gc);

                    gc.gridx = 1;
                    gc.insets = new Insets(0,0,0,0);
                    gc.anchor = GridBagConstraints.FIRST_LINE_START;
                    empIDField.setText(String.valueOf(nextID));
                    add(empIDField, gc);

                    /////// Second Row //////////

                    gc.gridy ++;
                    gc.gridx = 0;
                    gc.anchor = GridBagConstraints.FIRST_LINE_END;
                    gc.insets = new Insets(0,0,0,5);
                    add(firstNameLabel, gc);

                    gc.gridx = 1;
                    gc.insets = new Insets(0,0,0,0);
                    gc.anchor = GridBagConstraints.FIRST_LINE_START;
                    add(firstNameField, gc);

                    /////// Third Row //////////

                    gc.gridy ++;
                    gc.gridx = 0;
                    gc.anchor = GridBagConstraints.FIRST_LINE_END;
                    gc.insets = new Insets(0,0,0,5);
                    add(lastNameLabel, gc);

                    gc.gridx = 1;
                    gc.insets = new Insets(0,0,0,0);
                    gc.anchor = GridBagConstraints.FIRST_LINE_START;
                    add(lastNameField, gc);

                    /////// Forth Row //////////

                    gc.gridy ++;
                    gc.gridx = 0;
                    gc.anchor = GridBagConstraints.FIRST_LINE_END;
                    gc.insets = new Insets(0,0,0,5);
                    add(positionLabel, gc);

                    gc.gridx = 1;
                    gc.insets = new Insets(0,0,0,0);
                    gc.anchor = GridBagConstraints.FIRST_LINE_START;
                    add(positionField, gc);

                    /////// Fifth Row //////////

                    gc.gridy ++;
                    gc.gridx = 0;
                    gc.anchor = GridBagConstraints.FIRST_LINE_END;
                    gc.insets = new Insets(0,0,0,5);
                    add(usernameLabel, gc);

                    gc.gridx = 1;
                    gc.insets = new Insets(0,0,0,0);
                    gc.anchor = GridBagConstraints.FIRST_LINE_START;
                    add(usernameField, gc);

                    //// Sixth Row //////
                    gc.gridy ++;
                    gc.gridx = 0;
                    gc.anchor = GridBagConstraints.FIRST_LINE_END;
                    gc.insets = new Insets(0,0,0,5);
                    add(saveBtn, gc);
                }
            }


        }
        class ChangePwdDialog extends JDialog {

            private UserList userList = new UserList();
            private ArrayList<User> users = new ArrayList<>();
            private ChangePwdPanel changePwdPanel = new ChangePwdPanel();

            public ChangePwdDialog() throws FileNotFoundException {
                setTitle("Change Password");
                setSize(500, 300);

                setLocationRelativeTo(null);
                setVisible(true);

                add(changePwdPanel, BorderLayout.CENTER);

            }

            class ChangePwdPanel extends JPanel {

                private JLabel usernameLabel;
                private JTextField usernameField;
                private JLabel oldPwdLabel;
                private JPasswordField oldPwdField;
                private JLabel newPwdLabel;
                private JPasswordField newPwdField;
                private JLabel confirmNewPwdLabel;
                private JPasswordField confirmNewPwdField;
                private JButton submitBtn;

                public ChangePwdPanel(){
                    Dimension dim = getPreferredSize();
                    dim.width = 500;
                    dim.height = 300;
                    setPreferredSize(dim);

                    users = userList.getUserList();

                    usernameLabel = new JLabel("Username: ");
                    usernameField = new JTextField(10);

                    oldPwdLabel = new JLabel("Old Password: ");
                    oldPwdField = new JPasswordField(10);

                    newPwdLabel = new JLabel("New Password");
                    newPwdField = new JPasswordField(10);

                    confirmNewPwdLabel = new JLabel("Confirm New Password");
                    confirmNewPwdField = new JPasswordField(10);

                    submitBtn = new JButton("Submit");



                    submitBtn.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent actionEvent) {
                            User user = new User(0, null, null, null, usernameField.getText(), String.valueOf(oldPwdField.getPassword()), null);
                            String newPwd;
                            boolean authstatus = false;
                            for (User User : users) {
                                if (User.getUsername().equals(user.getUsername()) && User.getPassword().equals(user.getPassword())) {
                                    authstatus = true;
                                    if (String.valueOf(newPwdField.getPassword()).equals(String.valueOf(confirmNewPwdField.getPassword()))) {
                                        newPwd = String.valueOf(newPwdField.getPassword());
                                        User.setPassword(newPwd);
                                        try {
                                            userList.saveUsersFile(user);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        JFrame success = new JFrame();
                                        JOptionPane.showMessageDialog(success, "Your password was changed successfully.");
                                        dispose();
                                        break;
                                    } else {
                                        JFrame warning = new JFrame();
                                        JOptionPane.showMessageDialog(warning, "The new passwords you entered do not match.");
                                    }
                                }
                            }
                            if (!authstatus) {
                                JFrame warning = new JFrame();
                                JOptionPane.showMessageDialog(warning, "The existing username or password you entered is incorrect.");
                            }
                        }
                    });
                    layoutChgPwdComponents();
                }

                private void layoutChgPwdComponents(){
                    setLayout(new GridBagLayout());
                    GridBagConstraints gc = new GridBagConstraints();

                    gc.weightx = 1;
                    gc.weighty = 1;
                    gc.gridy = 1;

                    /////  First Row   //////

                    gc.gridx = 0;
                    gc.fill = GridBagConstraints.NONE;
                    gc.anchor = GridBagConstraints.FIRST_LINE_END;
                    gc.insets = new Insets(0,0,0,5);
                    add(usernameLabel, gc);

                    gc.gridx = 1;
                    gc.insets = new Insets(0,0,0,0);
                    gc.anchor = GridBagConstraints.FIRST_LINE_START;
                    add(usernameField, gc);

                    /////// Second Row //////////

                    gc.gridy ++;
                    gc.gridx = 0;
                    gc.anchor = GridBagConstraints.FIRST_LINE_END;
                    gc.insets = new Insets(0,0,0,5);
                    add(oldPwdLabel, gc);

                    gc.gridx = 1;
                    gc.insets = new Insets(0,0,0,0);
                    gc.anchor = GridBagConstraints.FIRST_LINE_START;
                    add(oldPwdField, gc);

                    /////// Third Row //////////

                    gc.gridy ++;
                    gc.gridx = 0;
                    gc.anchor = GridBagConstraints.FIRST_LINE_END;
                    gc.insets = new Insets(0,0,0,5);
                    add(newPwdLabel, gc);

                    gc.gridx = 1;
                    gc.insets = new Insets(0,0,0,0);
                    gc.anchor = GridBagConstraints.FIRST_LINE_START;
                    add(newPwdField, gc);

                    /////// Forth Row //////////

                    gc.gridy ++;
                    gc.gridx = 0;
                    gc.anchor = GridBagConstraints.FIRST_LINE_END;
                    gc.insets = new Insets(0,0,0,5);
                    add(confirmNewPwdLabel, gc);

                    gc.gridx = 1;
                    gc.insets = new Insets(0,0,0,0);
                    gc.anchor = GridBagConstraints.FIRST_LINE_START;
                    add(confirmNewPwdField, gc);

                    /////// Fifth Row //////////

                    gc.gridy ++;

                    gc.gridx = 0;
                    gc.gridwidth = 2;
                    gc.insets = new Insets(0,0,0,0);
                    gc.anchor = GridBagConstraints.CENTER;
                    add(submitBtn, gc);
                }
            }
        }
    }

    class ReturnsDialog extends JDialog {

        ReturnsTextPanel returnsTextPanel;
        ReturnsBtnPanel returnsBtnPanel;
        long recalledSaleID;

        private Hashtable<String, Integer> returnedItems = new Hashtable<>();

        public ReturnsDialog(){
            setLayout(new BorderLayout());

            returnsTextPanel = new ReturnsTextPanel();
            returnsBtnPanel = new ReturnsBtnPanel();

            add(returnsBtnPanel, BorderLayout.EAST);
            add(returnsTextPanel, BorderLayout.CENTER);
            setSize(new Dimension(800,500));
            setLocationRelativeTo(null);
        }

        class ReturnsTextPanel extends JPanel{
            JTextPane leftTextPane;
            JTextPane rightTextPane;
            Border blackline = BorderFactory.createLineBorder(Color.black);

            public ReturnsTextPanel(){
                Dimension dim = new Dimension(225, 375);

                leftTextPane = new JTextPane();
                leftTextPane.setBorder(blackline);
                leftTextPane.setEditable(false);
                leftTextPane.setPreferredSize(dim);

                rightTextPane = new JTextPane();
                rightTextPane.setBorder(blackline);
                rightTextPane.setEditable(false);
                rightTextPane.setPreferredSize(dim);

                add(leftTextPane);
                add(rightTextPane);
                setSize(500,400);
            }
        }

        class ReturnsBtnPanel extends JPanel {

            private JButton loadTranscionBtn;
            private JButton returnEntireTransactionBtn;
            private JLabel itemLabel;
            private JTextField itemField;
            private JButton returnSingleItemBtn;
            private JButton termTransactionBtn;
            private JLabel amountDueBackLabel;
            private JTextField amountDueBackField;
            private double amountDueBack;
            private String leftPaneMessage = "";
            private String rightPaneMessage = "Items being returned: \n";
            private String leftPaneHeader;
            private String itemList;

            public ReturnsBtnPanel(){

                loadTranscionBtn = new JButton("Load Transaction");
                loadTranscionBtn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent actionEvent) {
                        boolean saleFound = false;
                        recalledSaleID = Long.parseLong(JOptionPane.showInputDialog(MainFrame.this, "What is the Sale ID of the transaction?", "Get Sale ID", JOptionPane.INFORMATION_MESSAGE));
                        for(Sale sale: register.getSales()){
                            if(sale.getId() == recalledSaleID){
                                saleFound = true;
                                break;
                            }
                        }
                        if (saleFound){
                            refreshLeftTextPane();
                            returnEntireTransactionBtn.setEnabled(true);
                            itemLabel.setEnabled(true);
                            itemField.setEnabled(true);
                            returnSingleItemBtn.setEnabled(true);
                            termTransactionBtn.setEnabled(true);
                            loadTranscionBtn.setEnabled(false);
                        }
                        else{
                            JOptionPane.showMessageDialog(MainFrame.this, "The Sale ID entered was not found. Enter different Sale ID");
                        }


                    }
                });

                returnEntireTransactionBtn = new JButton("Return Entire Sale");
                returnEntireTransactionBtn.setEnabled(false);
                returnEntireTransactionBtn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent actionEvent) {
                        returnAllItems();
                        refreshLeftTextPane();
                        rightPaneMessage = "All items in sale: "+ recalledSaleID +"have been returned.\n";
                        returnsTextPanel.rightTextPane.setText(rightPaneMessage);
                    }
                });

                itemLabel = new JLabel("Line Item Number: ");
                itemLabel.setEnabled(false);
                itemField = new JTextField(10);
                itemField.setEnabled(false);

                returnSingleItemBtn = new JButton("Return single Item");
                returnSingleItemBtn.setEnabled(false);
                returnSingleItemBtn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent actionEvent) {
                        int lineItemNumber = Integer.parseInt(itemField.getText());
                        String itemName = register.getSale(recalledSaleID).getItems().get(lineItemNumber).getItemName();
                        double itemPrice = register.getSale(recalledSaleID).getItems().get(lineItemNumber).getPrice();
                        amountDueBack = amountDueBack + itemPrice;
                        DecimalFormat df = new DecimalFormat("##.##");
                        amountDueBack = Double.parseDouble(df.format(amountDueBack));
                        amountDueBackField.setText("-" + String.valueOf(amountDueBack));

                        if (returnedItems.containsKey(itemName)){
                            int count = returnedItems.get(itemName);
                            returnedItems.put(itemName, ++count);
                        }
                        else{
                            returnedItems.put(itemName,1);
                        }

                        register.returnSingleItem(recalledSaleID, lineItemNumber);
                        register.makeReturnComment(recalledSaleID, itemName + ", -" + itemPrice);
                        rightPaneMessage = rightPaneMessage + "Line Item " + lineItemNumber + ": " + itemName + ", -" + itemPrice + "\n";
                        returnsTextPanel.rightTextPane.setText(rightPaneMessage);
                        refreshLeftTextPane();
                        itemField.setText(null);
                    }
                });

                amountDueBackLabel = new JLabel("Amount due: ");
                amountDueBackField = new JTextField(7);
                amountDueBackField.setEditable(false);

                termTransactionBtn = new JButton("Terminate Transaction");
                termTransactionBtn.setEnabled(false);
                termTransactionBtn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent actionEvent) {
                        JOptionPane.showMessageDialog(MainFrame.this, "You owe the customer $" + amountDueBack + ".");
                        adjustInventoryReturn();
                        dispose();
                    }
                });

                setLayout();

                Dimension dim = getPreferredSize();
                dim.width = 300;
                dim.height = 100;
                setPreferredSize(dim);

            }

            private void setLayout(){
                setLayout(new GridBagLayout());

                GridBagConstraints gc = new GridBagConstraints();

                gc.weightx = 1;
                gc.gridwidth = 3;
                gc.weighty = .5;
                gc.anchor = GridBagConstraints.CENTER;

                gc.gridx = 0;
                gc.gridy = 0;
                add(loadTranscionBtn, gc);

                gc.weighty = .05;
                gc.gridy = 2;
                add(returnEntireTransactionBtn, gc);

                gc.weighty = 1;
                gc.gridwidth = 3;
                gc.gridx = 0;
                gc.anchor = GridBagConstraints.CENTER;
                gc.gridy = 3;
                add(new JLabel(" "), gc);

                gc.weighty = .05;
                gc.gridwidth = 1;
                gc.gridx = 0;
                gc.gridy = 4;
                gc.anchor = GridBagConstraints.FIRST_LINE_END;
                add(itemLabel, gc);

                gc.gridx = 1;
                gc.gridy = 4;
                gc.anchor = GridBagConstraints.FIRST_LINE_START;
                add(itemField, gc);

                gc.weighty = .01;
                gc.gridwidth = 3;
                gc.gridx = 0;
                gc.anchor = GridBagConstraints.CENTER;
                gc.gridy = 5;
                add(returnSingleItemBtn, gc);

                gc.weighty = .5;
                gc.gridwidth = 3;
                gc.gridx = 0;
                gc.anchor = GridBagConstraints.CENTER;
                gc.gridy = 6;
                add(new JLabel(" "), gc);


                gc.weighty = .05;
                gc.gridwidth = 1;
                gc.gridx = 0;
                gc.gridy = 7;
                gc.anchor = GridBagConstraints.FIRST_LINE_END;
                add(amountDueBackLabel, gc);

                gc.gridx = 1;
                gc.gridy = 7;
                gc.anchor = GridBagConstraints.FIRST_LINE_START;
                add(amountDueBackField, gc);

                gc.weighty = .05;
                gc.gridwidth = 3;
                gc.gridx = 0;
                gc.anchor = GridBagConstraints.CENTER;
                gc.gridy = 8;
                add(termTransactionBtn, gc);

                gc.weighty = 1;
                gc.gridwidth = 3;
                gc.gridx = 0;
                gc.anchor = GridBagConstraints.CENTER;
                gc.gridy = 9;
                add(new JLabel(" "), gc);
            }

            private void returnAllItems(){
                List<Item> returnedItemList = register.getSale(recalledSaleID).getItems();
                while(returnedItemList.size()>0){
                    amountDueBack = amountDueBack + returnedItemList.get(0).getPrice();
                    String itemName = returnedItemList.get(0).getItemName();
                    double itemPrice = returnedItemList.get(0).getPrice();
                    if (returnedItems.containsKey(itemName)){
                        int count = returnedItems.get(itemName);
                        returnedItems.put(itemName, ++count);
                    }
                    else{
                        returnedItems.put(itemName,1);
                    }
                    register.returnSingleItem(recalledSaleID, 0);
                    register.makeReturnComment(recalledSaleID, itemName + ", -" + itemPrice);
                    returnAllItems();
                }
                amountDueBackField.setText("-" + amountDueBack);
            }

            private void refreshLeftTextPane(){
                leftPaneHeader = leftPaneHeader + "Sale '" + recalledSaleID + "' Loaded Sucessfully.\n";
                returnsTextPanel.leftTextPane.setText(leftPaneMessage);
                leftPaneHeader = "Sale ID: " + register.getSale(recalledSaleID).getId() +
                        "; Cashier: " + register.getSale(recalledSaleID).getCashier() +
                        "; \nDate: " + register.getSale(recalledSaleID).getDate() + "\n";
                itemList = getItemsInSale();
                leftPaneMessage = leftPaneHeader + itemList + "\n" + register.getSale(recalledSaleID).getComment() + "\n";
                returnsTextPanel.leftTextPane.setText(leftPaneMessage);
            }

            private String getItemsInSale(){
                List<Item> items = register.getSale(recalledSaleID).getItems();
                String itemString = "";
                for(int i = 0; i< items.size(); i++){
                    itemString = itemString + i + ") " + items.get(i).getItemName() + ", Price: " + items.get(i).getPrice() + "\n";
                }
                return itemString;
            }

            private void adjustInventoryReturn() {
                Enumeration itemNames;
                itemNames = returnedItems.keys();

                while(itemNames.hasMoreElements()){
                    String key = (String) itemNames.nextElement();
                    int newCount = (Inventory.getInventory(key) + returnedItems.get(key));

                    try {
                        Inventory.updateItemCountInFile(key, newCount);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

    }

    class ReportingDialog extends JDialog{

    }

}

