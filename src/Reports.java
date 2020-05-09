import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Reports  {



    public void printSales(POS_System p, String path) throws FileNotFoundException //this one prints all sales ever recorded across all registers
    {
        ArrayList<String> list = new ArrayList<String>();
        POS_System pos = new POS_System();

        pos = p;

        File ci = new File(path);
        FileWriter fw = null;
        try {
            fw = new FileWriter(ci);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try {
            fw = new FileWriter(ci, true);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        PrintWriter pw = new PrintWriter (fw);


        for(int i = 0;i<pos.getRegisters().size();i++)
        {
            pw.print(pos.getRegisters().get(i).getSales());
        }
        pw.println("");
        pw.close();
    }



    public void printSales(POS_System p, String MM_dd_yyyy, String path) throws FileNotFoundException //this prints all sales across all registers for a specific day
            , ParseException
    {

        POS_System pos = new POS_System();
        String day = MM_dd_yyyy;
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        Date strDate = dateFormat.parse(day);//this puts the date passed into the method in the right format and makes it a date object

        pos = p;

        File ci = new File(path);
        FileWriter fw = null;
        try {
            fw = new FileWriter(ci);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try {
            fw = new FileWriter(ci, true);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        PrintWriter pw = new PrintWriter (fw);


        // String format = "%-25s";

        //pw.println("Sales Report as of: "+Calendar.getInstance().getTime());
        // pw.println("");
        //pw.println("");


        for(int i = 0;i<pos.getRegisters().size();i++)
        {
            String regDate = dateFormat.format(pos.getRegisters().get(i).getDate());//formats the register date
            Date regDateForCompare = dateFormat.parse(regDate);//after formatting this turns the regsiter date back into a date object for comparing

            if(strDate.compareTo(regDateForCompare) == 0)
            {

                pw.print(pos.getRegisters().get(i).getSales());
            }

        }
        pw.println("");
        pw.close();
    }


    public void printSales(POS_System p, String MM_dd_yyyy, List<String> usernames, String path) throws FileNotFoundException //this prints all sales across all registers for a specific day and a specific user
            , ParseException
    {
        List<Sale> list = new ArrayList<Sale>();

        POS_System pos = new POS_System();
        String day = MM_dd_yyyy;
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        Date strDate = dateFormat.parse(day);//this puts the date passed into the method in the right format and makes it a date object
        List<String> cashiers = usernames;

        pos = p;

        File ci = new File(path);
        FileWriter fw = null;
        try {
            fw = new FileWriter(ci);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try {
            fw = new FileWriter(ci, true);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        PrintWriter pw = new PrintWriter (fw);


        // String format = "%-25s";

        //pw.println("Sales Report as of: "+Calendar.getInstance().getTime());
        // pw.println("");
        //pw.println("");


        for(int i = 0;i<pos.getRegisters().size();i++)
        {
            String regDate = dateFormat.format(pos.getRegisters().get(i).getDate());//formats the register date
            Date regDateForCompare = dateFormat.parse(regDate);//after formatting this turns the register date back into a date object for comparing
            String user = pos.getRegisters().get(i).getCurrUser().getUsername();//turns register user into string


            list = pos.getRegisters().get(i).getSales();//gets sales for the first register and put it in my array

            for(int c = 0; c < list.size(); c++)
            {
                Sale saleItem = list.get(c);//gets the sale item in the list
                String stringsaleDate = dateFormat.format(saleItem.getDate());//puts the sale date format in the format i need
                Date saleDate = dateFormat.parse(stringsaleDate);//puts the stringsaledate into a date object so i can compare
                String saleItemCashier = saleItem.getCashier();


                boolean compareResult = cashiers.contains(saleItemCashier);
                //int compareResult = saleItemCashier.compareTo(cashier);//compares username passed in (cashier) with saleItem cashier username


                if(saleDate.compareTo(regDateForCompare) == 0 && compareResult )//
                {
                    pw.println(saleItem.toString());
                }

            }

        }
        pw.println("");
        pw.close();
    }







    public void printInventoryBelowThreshold(String path)
    {
        File ci = new File(path);
        FileWriter fw = null;
        try {
            fw = new FileWriter(ci);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try {
            fw = new FileWriter(ci, true);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        PrintWriter pw = new PrintWriter (fw);

        InventoryTableModel tm = null;
        try {
            tm = new InventoryTableModel();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String format = "%-15s";

        pw.println("Inventory Report for items below threshold as of "+ Calendar.getInstance().getTime());
        pw.println("");
        pw.println("");

        for (int i = 0; i<tm.getColumnCount();i++)
        {
            pw.printf (format, tm.getColumnName(i));
        }

        pw.println("");

        for (int t = 0; t<tm.getRowCount();t++)
        {
            int count = Integer.parseInt(String.valueOf(tm.getValueAt(t, 3)));
            int threshold = Integer.parseInt(String.valueOf(tm.getValueAt(t, 4)));
            if(count < threshold){
                for (int c = 0; c<tm.getColumnCount();c++)
                {
                    pw.printf(format, tm.getValueAt(t, c));
                }
                pw.print("\n");
            }
        }
        pw.close();
    }

    public void printCurrentInventory(String path)
    {
        File ci = new File(path);
        FileWriter fw = null;
        try {
            fw = new FileWriter(ci);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try {
            fw = new FileWriter(ci, true);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        PrintWriter pw = new PrintWriter (fw);

        InventoryTableModel tm = null;
        try {
            tm = new InventoryTableModel();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String format = "%-25s";

        pw.println("Inventory Report as of "+ Calendar.getInstance().getTime());
        pw.println("");
        pw.println("");

        for (int i = 0; i<tm.getColumnCount();i++)
        {
            pw.printf (format, tm.getColumnName(i));



        }

        pw.println("");

        for (int t = 0; t<tm.getRowCount();t++)
        {
            for (int c = 0; c<tm.getColumnCount();c++)
            {
                pw.printf(format, tm.getValueAt(t, c));

            }
            pw.println("");

        }

        pw.close();




    }


    public void printSalesForUser(POS_System p, List<String> usernames, String path) throws FileNotFoundException //this prints all sales across all registers for a specific user
            , ParseException
    {
        List<Sale> list = new ArrayList<Sale>();

        POS_System pos = new POS_System();

        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");

        List<String> cashiers = usernames;

        pos = p;

        File ci = new File(path);
        FileWriter fw = null;
        try {
            fw = new FileWriter(ci);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try {
            fw = new FileWriter(ci, true);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        PrintWriter pw = new PrintWriter (fw);


        // String format = "%-25s";

        //pw.println("Sales Report as of: "+Calendar.getInstance().getTime());
        // pw.println("");
        //pw.println("");


        for(int i = 0;i<pos.getRegisters().size();i++)
        {
            String regDate = dateFormat.format(pos.getRegisters().get(i).getDate());//formats the register date
            Date regDateForCompare = dateFormat.parse(regDate);//after formatting this turns the register date back into a date object for comparing
            String user = pos.getRegisters().get(i).getCurrUser().getUsername();//turns register user into string


            list = pos.getRegisters().get(i).getSales();//gets sales for the first register and put it in my array

            for(int c = 0; c < list.size(); c++)
            {
                Sale saleItem = list.get(c);//gets the sale item in the list

                String saleItemCashier = saleItem.getCashier();
                boolean compareResult = cashiers.contains(saleItemCashier);//compares username passed in (cashier) with saleItem cashier username


                if(compareResult)//
                {
                    pw.println(saleItem.toString());
                }

            }

        }
        pw.println("");
        pw.close();
    }



}