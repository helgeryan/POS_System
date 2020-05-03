import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainFrame extends JFrame {

    POS_System pos_system;
    private MainPanel mainPanel;
    private RegisterInfoPanel registerInfoPanel;
    private Register register;
    private SalesPanel salesPanel;
    private List<Item> items;

    public MainFrame(POS_System POS_System, Register Register) throws FileNotFoundException {
        super("Point of Sale System");

        pos_system = new POS_System();
        pos_system = POS_System;

        register = new Register();
        register = Register;

        salesPanel = new SalesPanel();

        setLayout(new BorderLayout());

        mainPanel = new MainPanel();

        registerInfoPanel = new RegisterInfoPanel();


        add(mainPanel, BorderLayout.SOUTH);
        add(salesPanel, BorderLayout.CENTER);
        add(registerInfoPanel, BorderLayout.NORTH);

        //add(userPanel, BorderLayout.CENTER);


        setSize(1000, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    class RegisterInfoPanel extends JPanel {

        LocalDate date = LocalDate.now();
        //LocalTime time = LocalTime.now();

        List<Sale> sales = new ArrayList<>();
        long nextSalesID = 1;

        private JLabel registerIDLabel;
        private JLabel transIDLabel;

        private JLabel cashierLabel;

        private JLabel dateLabel;

        private JLabel timeLabel;


        Border blackline = BorderFactory.createLineBorder(Color.black);
        String spaces = "           ";


        public RegisterInfoPanel(){

            sales = register.getSales();

            for(int i = 0; i < sales.size(); i++){
                nextSalesID = sales.get(i).getId() + 1;
            }

            registerIDLabel = new JLabel("Register ID: "  ); //+ register.getId() +
            registerIDLabel.setBorder(blackline);

            transIDLabel = new JLabel(spaces + "Tran ID: " + nextSalesID + spaces);
            transIDLabel.setBorder(blackline);

            cashierLabel = new JLabel(spaces + "Cashier : "); //+ register.getCurrUser().getUsername() + spaces
            cashierLabel.setBorder(blackline);

            dateLabel = new JLabel(spaces + date + spaces);
            dateLabel.setBorder(blackline);

            timeLabel = new JLabel(spaces + "Time" + spaces);
            timeLabel.setBorder(blackline);

            setVisible(true);

            layoutSalesComponents();
        }

        public void layoutSalesComponents(){
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
            add(timeLabel, gc);
        }
    }

    class MainPanel extends JPanel {

        private JButton invBtn;
        private JButton userBtn;
        private JButton reportingbtn;
        public MainPanel(){

            Dimension dim = getPreferredSize();
            dim.width =250;
            dim.height = 50;
            setPreferredSize(dim);

            invBtn = new JButton("Inventory");
            userBtn = new JButton("User Management");
            reportingbtn = new JButton("Reporting");
            setLayout(new GridBagLayout());

            GridBagConstraints gc = new GridBagConstraints();

            gc.gridy = 0;
            gc.weightx = 1;
            gc.weighty = .5;


            gc.gridx = 1;
            add(invBtn);

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
            add(userBtn);

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
            add(reportingbtn);
        }
    }

    class SalesPanel extends JPanel{
        private SalesBtnPanel salesBtnPanel;
        private SalesTablePanel salesTablePanel;
        private ArrayList<Item> items;
        private TransactionTableModel tableModel;
        private int selectedRow;
        public SalesPanel(){
            salesBtnPanel = new SalesBtnPanel();
            salesTablePanel = new SalesTablePanel();
            items = new ArrayList<>();
            populateItems();

            setLayout(new BorderLayout());
            add(salesTablePanel, BorderLayout.CENTER);
            add(salesBtnPanel, BorderLayout.EAST);

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
                    }
                });

                setLayout(new BorderLayout());
                setSize(700,600);
                add(new JScrollPane(table), BorderLayout.CENTER);
            }

            public void refresh() {
                tableModel.fireTableDataChanged();
            }
        }

        class SalesBtnPanel extends JPanel{
            private JButton newTransactionBtn;
            private JButton returnTransactionBtn;
            private JButton removeItemBtn;
            private JButton processPaymentBtn;
            private JButton endTransactionBtn;

            private JLabel addItemLabel;
            private JTextField addItemField;
            private JLabel qtyLabel;
            private JTextField qtyField;
            private JButton addItemBtn;

            private JLabel subtotalLabel;
            private JTextField subtotalField;
            private JLabel taxLabel;
            private JTextField taxField;
            private JLabel totalLabel;
            private JTextField totalField;
            private JLabel amountDueLabel;
            private JTextField amountDueField;
            private double subTotal;
            private double saleTotal;
            private double tax;
            private double amountDue;

            public SalesBtnPanel(){
                newTransactionBtn = new JButton("New Transaction");
                newTransactionBtn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent actionEvent) {
                        register.newSale();
                    }
                });

                returnTransactionBtn = new JButton("Return Transaction");


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
                processPaymentBtn = new JButton("Process Payment");
                endTransactionBtn = new JButton("End Transaction");

                subtotalLabel = new JLabel("SubTotal: ");
                subtotalField = new JTextField(5);
                subtotalField.setEditable(false);

                taxLabel = new JLabel("Tax: ");
                taxField = new JTextField(5);
                taxField.setEditable(false);

                totalLabel = new JLabel("Total: ");
                totalField = new JTextField(5);
                totalField.setEditable(false);

                amountDueLabel = new JLabel("Amount still due: ");
                amountDueField = new JTextField(5);
                amountDueField.setEditable(false);


                setSize(200,700);
                setVisible(true);
                setLayout();
            }

            public void refreshTotals(){
                DecimalFormat df = new DecimalFormat("###.##");

                subTotal = register.getSalePrice();
                subTotal = Double.parseDouble(df.format(subTotal));
                saleTotal = subTotal * 1.075;
                saleTotal = Double.parseDouble(df.format(saleTotal));
                tax = saleTotal - subTotal;
                tax = Double.parseDouble(df.format(tax));
                amountDue = saleTotal;

                subtotalField.setText(String.valueOf(subTotal));
                taxField.setText(String.valueOf(tax));
                totalField.setText(String.valueOf(saleTotal));
                register.setSalePrice(saleTotal);

                amountDueField.setText(String.valueOf(amountDue));
            }

            void setLayout(){
                setLayout(new GridBagLayout());
                GridBagConstraints gc = new GridBagConstraints();

                /////// Side Column //////

                gc.weighty = .02;
                gc.gridwidth = 2;
                gc.gridx = 1;
                gc.gridy = 1;
                gc.anchor = GridBagConstraints.CENTER;
                add(newTransactionBtn, gc);

                gc.gridx = 1;
                gc.gridy =2;
                gc.anchor = GridBagConstraints.CENTER;
                add(returnTransactionBtn, gc);

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
                gc.anchor = GridBagConstraints.LINE_START;
                add(addItemBtn, gc);

                gc.gridx = 1;
                gc.gridy = 7;
                gc.anchor = GridBagConstraints.LINE_START;
                add(removeItemBtn, gc);

                gc.weighty = .1;
                gc.gridx = 1;
                gc.gridy = 8;
                add(new JLabel(""), gc);

                gc.weighty = .02;
                gc.gridx = 1;
                gc.gridy = 9;
                gc.anchor = GridBagConstraints.LINE_START;
                add(processPaymentBtn, gc);

                gc.gridx = 1;
                gc.gridy = 10;
                gc.anchor = GridBagConstraints.LINE_START;
                add(endTransactionBtn, gc);

                ///// Totals section /////

                gc.weighty = .02;
                gc.gridx = 1;
                gc.gridwidth = 1;
                gc.gridy = 11;
                gc.anchor = GridBagConstraints.LINE_END;
                add(subtotalLabel, gc);

                gc.gridx = 1;
                gc.gridy = 12;
                gc.anchor = GridBagConstraints.LINE_END;
                add(taxLabel, gc);

                gc.gridx = 1;
                gc.gridy = 13;
                gc.anchor = GridBagConstraints.LINE_END;
                add(totalLabel, gc);

                gc.gridx = 1;
                gc.gridy = 14;
                gc.anchor = GridBagConstraints.LINE_END;
                add(amountDueLabel, gc);

                gc.weighty = .02;
                gc.gridx = 2;
                gc.gridy = 11;
                gc.anchor = GridBagConstraints.LINE_START;
                add(subtotalField, gc);

                gc.gridx = 2;
                gc.gridy = 12;
                gc.anchor = GridBagConstraints.LINE_START;
                add(taxField, gc);

                gc.gridx = 2;
                gc.gridy = 13;
                gc.anchor = GridBagConstraints.LINE_START;
                add(totalField, gc);

                gc.gridx = 2;
                gc.gridy = 14;
                gc.anchor = GridBagConstraints.LINE_START;
                add(amountDueField, gc);
            }
        }
    }





    class InventoryDialog extends JDialog {

        private InventoryTablePanel inventoryTablePanel;
        private AdjInvPanel adjInvPanel;
        InventoryTableModel tableModel;

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

                setLayout(new BorderLayout());

                add(new JScrollPane(table), BorderLayout.CENTER);
            }

            public void refresh() throws FileNotFoundException {
                tableModel.setData();
                tableModel.fireTableDataChanged();
            }

        }

        class AdjInvPanel extends JPanel {
            private JButton addNewItemBtn;
            private JButton deleteItemBtn;
            private JButton updateQuantityBtn;

            public AdjInvPanel(){
                Dimension dim = getPreferredSize();
                dim.width = 150;
                dim.height = 100;
                setPreferredSize(dim);
                addNewItemBtn = new JButton("Add New Item");
                deleteItemBtn = new JButton("Delete Item");

                addNewItemBtn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent actionEvent) {
                        try {
                            NewItemDialog newItemDialog = new NewItemDialog();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });

                layoutAdjInvComponents();
            }

            public void layoutAdjInvComponents() {

                setLayout(new GridBagLayout());
                GridBagConstraints gc = new GridBagConstraints();

                gc.weightx = 1;
                gc.weighty = .02;
                gc.gridy = 0;

                ////// First Row ///////
                gc.gridy ++;

                gc.gridx = 1;
                gc.anchor = GridBagConstraints.LINE_START;
                add(addNewItemBtn, gc);

                gc.gridy ++;

                gc.gridx = 1;
                gc.anchor = GridBagConstraints.LINE_START;
                add(deleteItemBtn, gc);

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

                public void layoutNewItemComponents() {



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

            public void refresh() throws FileNotFoundException {
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

            public void layoutAdjUserComponents() {

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

                public void layoutNewUserComponents() {

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

                public void layoutChgPwdComponents(){
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
    class ReportingDialog extends JDialog{

    }

}

