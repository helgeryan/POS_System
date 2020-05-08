import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Formatter;
import java.util.List;

public class Reports  {



    //public void printSales(int DD, int MM, int YY, int HourStart, int HourEnd, String username)
    //{

    //}



    public void printSales(List<Sale> Sales)
    {
        //String user;
        //user = username;

        File ci = new File("SalesReport2.txt");
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


        String format = "%-25s";

        pw.println("Sales Report as of: "+Calendar.getInstance().getTime());
        pw.println("");
        pw.println("");


        for (int i = 0; i< Sales.size(); i++)
        {
            pw.printf(format, Sales.get(i).toString());


        }
        pw.println("");
        pw.close();
    }

    public void printInventoryBelowThreshold()
    {
        File ci = new File("InventoryBelowThreshold.txt");
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

    public void printCurrentInventory()
    {
        File ci = new File("Current Inventory");
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





}
