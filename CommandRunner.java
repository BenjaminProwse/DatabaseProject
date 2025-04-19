import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class CommandRunner
{

    public static String help() {

        String line = "";

        try
        {
            Class.forName("org.sqlite.JDBC");

            Connection con = DriverManager.getConnection("jdbc:sqlite:IceCreamShop.db");

            Statement stmt = con.createStatement();

            String command = "SELECT * FROM commandlist;";
            ResultSet rs = stmt.executeQuery(command);

            while (rs.next())
            {
                line = line
                        + String.format("%-15s",rs.getString("command"))
                        + String.format(" | %s",rs.getInt("access"))
                        + String.format(" | %10s",rs.getString("Descrpition"))
                        + "\n";
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            return line;
        }
    }

    public static void addUser(String name, String username, String password, int access)
    {
        Connection con = null;
        try
        {
            Class.forName("org.sqlite.JDBC");

            con = DriverManager.getConnection("jdbc:sqlite:IceCreamShop.db");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT MAX(ID) FROM logininfo;");
            int id = 0;
            if (rs.next())
            {
                id = rs.getInt(1) + 1;
            }

            String command = String.format("INSERT INTO logininfo values ("+id+",'"+name+"','"+username+"', '"+password+"', "+access+");");
            stmt.executeUpdate(command);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (con != null)

                    con.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public static void removeUser(int ID)
    {
        Connection con = null;
        try
        {
            Class.forName("org.sqlite.JDBC");

            con = DriverManager.getConnection("jdbc:sqlite:IceCreamShop.db");

            Statement stmt = con.createStatement();

            String command = String.format("DELETE FROM logininfo WHERE ID=%s;", ID);
            stmt.executeUpdate(command);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (con != null)

                    con.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public static void removeUser(String username)
    {
        String line = "";
        Scanner input = new Scanner(System.in);
        int ID = 0;

        Connection con = null;

        try
        {
            Class.forName("org.sqlite.JDBC");

            con = DriverManager.getConnection("jdbc:sqlite:IceCreamShop.db");

            Statement stmt = con.createStatement();

            //Get the usernames to confirm
            ResultSet rs = stmt.executeQuery(String.format("SELECT * FROM logininfo where username='%s';",username));

            ArrayList<String> users = new ArrayList<String>();
            ArrayList<Integer> Ids = new ArrayList<Integer>();

            while (rs.next())
            {
                ID = rs.getInt("ID");
                line = line
                        + String.format("%-15s",rs.getInt("ID"))
                        + String.format(" | %-15s",rs.getString("Name"))
                        + String.format(" | %s",rs.getString("username"))
                        + String.format(" | %10s",rs.getString("access"))
                        + "\n";
                users.add(line);
                Ids.add(ID);
            }

            for (int i = 0; i < users.size(); i++)
            {
                System.out.println((i+1)+". "+users.get(i));
            }
            System.out.println(": ");
            int result = input.nextInt();
            ID = Ids.get(result - 1);

            String command = String.format("DELETE FROM logininfo WHERE ID=%s;", ID);
            stmt.executeUpdate(command);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (con != null)

                    con.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public static String showUsers()
    {
        String line = "";
        Connection con = null;
        try
        {
            Class.forName("org.sqlite.JDBC");

            con = DriverManager.getConnection("jdbc:sqlite:IceCreamShop.db");

            Statement stmt = con.createStatement();

            String command = "SELECT * FROM logininfo;";
            ResultSet rs = stmt.executeQuery(command);

            while (rs.next())
            {
                line = line
                        + String.format("%07d",rs.getInt("ID"))
                        + String.format(" | %-15s",rs.getString("Name"))
                        + String.format(" | %-6s ", rs.getString("username"))
                        + String.format(" | %-15s ", rs.getString("password"))
                        + String.format(" | %d",rs.getInt("access"))
                        +"\n";
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (con != null)
                {
                    con.close();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return line;
        }
    }

    public static String showUsers(int ID)
    {
        String line = "";
        Connection con = null;

        int nextID;

        int myAccess;
        int nextAccess;

        try
        {
            Class.forName("org.sqlite.JDBC");

            con = DriverManager.getConnection("jdbc:sqlite:IceCreamShop.db");

            Statement stmt = con.createStatement();

            String command = "SELECT * FROM logininfo;";
            ResultSet rs = stmt.executeQuery(command);

            while (rs.next())
            {
                nextID = rs.getInt("ID");

                if (Validator.getIDAccess(ID) == 1)
                {
                    line = line
                            + String.format("%07d", rs.getInt("ID"))
                            + String.format(" | %-15s", rs.getString("Name"))
                            + String.format(" | %-6s ", rs.getString("username"))
                            + String.format(" | %-15s ", rs.getString("password"))
                            + String.format(" | %d", rs.getInt("access"))
                            + "\n";
                }
                else if ( Validator.getIDAccess(ID) < Validator.getIDAccess(nextID)) {
                    line = line
                            + String.format("%07d", rs.getInt("ID"))
                            + String.format(" | %-15s", rs.getString("Name"))
                            + String.format(" | %-6s ", rs.getString("username"))
                            + String.format(" | %-15s ", rs.getString("password"))
                            + String.format(" | %d", rs.getInt("access"))
                            + "\n";
                }
                else {
                    line = line
                            + String.format("%07d", rs.getInt("ID"))
                            + String.format(" | %-15s", rs.getString("Name"))
                            + String.format(" | %-6s ", rs.getString("username"))
                            + " | ***************"
                            + String.format(" | %d", rs.getInt("access"))
                            + "\n";
                }
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (con != null)
                {
                    con.close();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return line;
        }
    }


    // THIS SECTION IS FOR THE ICE CREAM FLAVOURS

    public static String showFlavours()
    {
        String line = "";
        Connection con = null;

        try
        {
            Class.forName("org.sqlite.JDBC");

            con = DriverManager.getConnection("jdbc:sqlite:IceCreamShop.db");

            Statement stmt = con.createStatement();

            String command = "SELECT * FROM IceCreamFlavor;";
            ResultSet rs = stmt.executeQuery(command);

            while (rs.next())
            {
                line = line
                        + String.format("%07d", rs.getInt("PRODUCT_NO"))
                        + String.format(" | %-15s", rs.getString("Flavor"))
                        + String.format(" | %-6s ", rs.getString("Dietery_Restrictions"))
                        + String.format(" | %d ", rs.getInt("Cals_per_Serv"))
                        + "\n";
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (con != null)
                {
                    con.close();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return line;
        }
    }

    public static void removeFlavour(String Flavor)
    {
        String line = "";
        Scanner input = new Scanner(System.in);
        int productNum = 0;

        Connection con = null;

        try
        {
            Class.forName("org.sqlite.JDBC");

            con = DriverManager.getConnection("jdbc:sqlite:IceCreamShop.db");

            Statement stmt = con.createStatement();

            //Get the usernames to confirm
            ResultSet rs = stmt.executeQuery(String.format("SELECT * FROM IceCreamFlavor where Flavor='%s';",Flavor));

            ArrayList<String> names = new ArrayList<String>();
            ArrayList<Integer> productIDS = new ArrayList<Integer>();

            while (rs.next())
            {
                productNum = rs.getInt("ID");
                line = line
                        + String.format("%-15s",rs.getInt("Product_No"))
                        + String.format(" | %-15s",rs.getString("Flavor"))
                        + "\n";
                names.add(line);
                productIDS.add(productNum);
            }

            for (int i = 0; i < names.size(); i++)
            {
                System.out.println((i+1)+". "+names.get(i));
            }
            System.out.println(": ");
            int result = input.nextInt();
            productNum = productIDS.get(result - 1);

            String command = String.format("DELETE FROM IceCreamFlavor WHERE ID=%s;", productNum);
            stmt.executeUpdate(command);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (con != null)

                    con.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public static void removeFlavor(int productNum)
    {
        Connection con = null;
        try
        {
            Class.forName("org.sqlite.JDBC");

            con = DriverManager.getConnection("jdbc:sqlite:IceCreamShop.db");

            Statement stmt = con.createStatement();

            String command = String.format("DELETE FROM IceCreamFlavor WHERE Product_No=%s;", productNum);
            stmt.executeUpdate(command);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (con != null)

                    con.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public static void addFlavor(String name, String rescrictions, int cals)
    {
        Connection con = null;
        try
        {
            Class.forName("org.sqlite.JDBC");

            con = DriverManager.getConnection("jdbc:sqlite:IceCreamShop.db");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT MAX(Product_No) FROM IceCreamFlavor;");
            int proNum = 0;
            if (rs.next())
            {
                proNum = rs.getInt(1) + 1;
            }

            String command = String.format("INSERT INTO IceCreamFlavor values (%d,'%s','%s',%d);", proNum, name, rescrictions, cals);
            stmt.executeUpdate(command);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (con != null)

                    con.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public static String showInvoices()
    {
        String line = "";
        Connection con = null;

        try
        {
            Class.forName("org.sqlite.JDBC");

            con = DriverManager.getConnection("jdbc:sqlite:IceCreamShop.db");

            Statement stmt = con.createStatement();

            String command = "SELECT * FROM Invoices;";
            ResultSet rs = stmt.executeQuery(command);

            while (rs.next())
            {
                line = line
                        + String.format("%07d", rs.getInt("Invoice_No"))
                        + String.format(" | %-5.2f", rs.getDouble("SubTotal"))
                        + String.format(" | %-5.2f ", rs.getDouble("Total"))
                        + String.format(" | %-5.2f ", rs.getDouble("Amount_Given"))
                        + String.format(" | %-5.2f ", rs.getDouble("Amount_Due"))
                        + String.format(" | %-5.2f ", rs.getDouble("Change"))
                        + String.format(" | %s ", rs.getString("Date_Bought"))
                        + "\n";
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (con != null)
                {
                    con.close();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return line;
        }

    }

    public static void addInvoice(double subTot, double total, double due, double given, double change, String date)
    {
        Connection con = null;
        try
        {
            Class.forName("org.sqlite.JDBC");

            con = DriverManager.getConnection("jdbc:sqlite:IceCreamShop.db");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT MAX(Invoice_No) FROM Invoices;");
            int InvNum = 0;
            if (rs.next())
            {
                InvNum = rs.getInt(1) + 1;
            }

            String command = String.format("INSERT INTO Invoices values (%d,%f,%f,%f,%f,%f,'%s');", InvNum, subTot, total, due, given, change,date);
            stmt.executeUpdate(command);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (con != null)

                    con.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public static void removeInvoice(int InvID)
    {
        Connection con = null;
        try
        {
            Class.forName("org.sqlite.JDBC");

            con = DriverManager.getConnection("jdbc:sqlite:IceCreamShop.db");

            Statement stmt = con.createStatement();

            String command = String.format("DELETE FROM Invoices WHERE Invoice_No=%d;", InvID);
            stmt.executeUpdate(command);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (con != null)

                    con.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public static int makeOrder(int proNum, double amount)
    {
        Connection con = null;
        int orderId = 0;
        try
        {
            Class.forName("org.sqlite.JDBC");

            con = DriverManager.getConnection("jdbc:sqlite:IceCreamShop.db");

            con.setAutoCommit(false);

            Statement stmt = con.createStatement();

            //Make Shipment
            //Get Order ID
            ResultSet rs = stmt.executeQuery("SELECT MAX(tracking_id) FROM Orders;");

            if (rs.next())
            {
                orderId = rs.getInt(1) + 1;
            }

            //Get the dates
            LocalDateTime today = LocalDateTime.now();
            LocalDateTime expected = LocalDateTime.now().plusDays(5);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy");

            String orderDate = formatter.format(today);
            String expectedDate = formatter.format(expected);

            //Create and execute statement
            String command = String.format("INSERT INTO Shipment values (%d,'%s','%s','ON ORDER','%s');", orderId,orderDate,expectedDate,null);
            stmt.executeUpdate(command);

            //Make Order
            command = String.format("INSERT INTO Orders values (%d,%d,%f,'%s');", proNum,orderId,amount,"ON ORDER");
            stmt.executeUpdate(command);

            con.commit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            try
            {
                if (con != null)
                    con.rollback();
            }
            catch (SQLException e1)
            {
                e1.printStackTrace();
            }
        }
        finally
        {
            try
            {
                if (con != null)

                    con.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return orderId;
        }
    }

    public static void addToOrder(int proNum, double amount, int orderId)
    {
        Connection con = null;
        try
        {
            Class.forName("org.sqlite.JDBC");

            con = DriverManager.getConnection("jdbc:sqlite:IceCreamShop.db");

            Statement stmt = con.createStatement();

            //Make Shipment

            //Make Order
            String command = String.format("INSERT INTO Orders values (%d,%d,%f,'%s');", proNum,orderId,amount,"ON ORDER");
            stmt.executeUpdate(command);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (con != null)

                    con.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public static void updateShipment(int trackingID, String stage)
    {
        Connection con = null;
        try
        {
            Class.forName("org.sqlite.JDBC");

            con = DriverManager.getConnection("jdbc:sqlite:IceCreamShop.db");
            con.setAutoCommit(false);

            Statement stmt = con.createStatement();

            String command;
            if (stage.equals("ARRIVED"))
            {
                LocalDateTime today = LocalDateTime.now();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy");

                String todayFormatted = formatter.format(today);
                command = String.format("UPDATE SHIPMENT SET Status = '%s', WHERE TrackingID = %d;", stage, todayFormatted, trackingID);
                stmt.executeUpdate(command);

                command = String.format("UPDATE Orders SET Status = '%s' WHERE TrackingID = %d;", stage, trackingID);
                stmt.executeUpdate(command);

                //Get the product that is being updated
                ResultSet rs = stmt.executeQuery("SELECT product_no,amount FROM Orders WHERE TrackingID = "+trackingID+";");
                int id = 0;
                int amount = 0;
                if (rs.next())
                {
                    id = rs.getInt(1);
                    amount = rs.getInt(2);
                }

                //Update Stock
                command = String.format("INSERT INTO Stock values (%d,%f);",id,amount);
                stmt.executeUpdate(command);

                con.commit();
            }
            else if (stage.equals("CANCELLED"))
            {
                //UPDATE ORDER TABLE
                command = String.format("UPDATE Orders SET status='CANCELLED' WHERE Tracking_ID=%d;", trackingID);
                stmt.executeUpdate(command);

                //UPDATE SHIPMENT TABLE
                command = String.format("UPDATE Shipment SET status='CANCELLED' WHERE Tracking_ID=%d", trackingID);
                stmt.executeUpdate(command);

                con.commit();
            }
            else
            {
                command = String.format("UPDATE SHIPMENT SET Status = '%s' WHERE TrackingID = %d;", stage, trackingID);
                stmt.executeUpdate(command);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (con != null)

                    con.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public static String showShipments()
    {
        String line = "";
        Connection con = null;
        try
        {
            Class.forName("org.sqlite.JDBC");

            con = DriverManager.getConnection("jdbc:sqlite:IceCreamShop.db");

            Statement stmt = con.createStatement();

            String command = String.format("SELECT * FROM Shipment;");
            ResultSet rs = stmt.executeQuery(command);

            while (rs.next())
            {
                if (!(rs.getString("Status").equals("ARRIVED")) || !(rs.getString("Status").equals("CANCELLED")))
                {
                    line = line
                            + String.format("%07d", rs.getInt("Tracking_ID"))
                            + String.format(" | %s", rs.getString("Order_Date"))
                            + String.format(" | %s", rs.getString("Expected_Date"))
                            + String.format(" | %s", rs.getString("Status"))
                            + String.format(" | %s", rs.getString("Date_Received"))
                            + "\n";
                }
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (con != null)
                {
                    con.close();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return line;
        }
    }

    public static String showShipmentsAll()
    {
        String line = "";
        Connection con = null;
        try
        {
            Class.forName("org.sqlite.JDBC");

            con = DriverManager.getConnection("jdbc:sqlite:IceCreamShop.db");

            Statement stmt = con.createStatement();

            String command = String.format("SELECT * FROM Shipment;");
            ResultSet rs = stmt.executeQuery(command);

            while (rs.next())
            {
                line = line
                        + String.format("%07d", rs.getInt("Tracking_ID"))
                        + String.format(" | %s", rs.getString("Order_Date"))
                        + String.format(" | %s", rs.getString("Expected_Date"))
                        + String.format(" | %s", rs.getString("Status"))
                        + String.format(" | %s", rs.getString("Date_Received"))
                        + "\n";
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (con != null)
                {
                    con.close();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return line;
        }
    }

    public static String showShipment(int trackingID)
    {
        String line = "";
        Connection con = null;
        try
        {
            Class.forName("org.sqlite.JDBC");

            con = DriverManager.getConnection("jdbc:sqlite:IceCreamShop.db");

            Statement stmt = con.createStatement();

            String command = String.format("SELECT * FROM Shipment WHERE Tracking_ID = %d;", trackingID);
            ResultSet rs = stmt.executeQuery(command);

            if (rs.next())
            {
                line = line
                        + String.format("%07d", rs.getInt("Tracking_ID"))
                        + String.format(" | %s", rs.getString("Order_Date"))
                        + String.format(" | %s", rs.getString("Expected_Date"))
                        + String.format(" | %s", rs.getString("Status"))
                        + String.format(" | %s", rs.getString("Date_Received"))
                        + "\n";
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (con != null)
                {
                    con.close();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return line;
        }
    }

    public static String showOrder(int trackingID)
    {
        String line = "";
        Connection con = null;
        try
        {
            Class.forName("org.sqlite.JDBC");

            con = DriverManager.getConnection("jdbc:sqlite:IceCreamShop.db");

            Statement stmt = con.createStatement();

            String command = String.format("SELECT * FROM Orders WHERE tracking_ID = %d;", trackingID);
            ResultSet rs = stmt.executeQuery(command);

            line = line + String.format(" --- %07d ---\n", rs.getInt("Tracking_ID"));

            if (rs.next())
            {
                line = line
                        + String.format("%07d", rs.getInt("product_no"))
                        + String.format(" | %f", rs.getDouble("amount"))
                        + String.format(" | %s", rs.getString("status"))
                        + "\n";
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (con != null)
                {
                    con.close();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return line;
        }
    }

    public static String showOrders()
    {
        String line = "";
        Connection con = null;
        try
        {
            Class.forName("org.sqlite.JDBC");

            con = DriverManager.getConnection("jdbc:sqlite:IceCreamShop.db");

            Statement stmt = con.createStatement();

            String command = "SELECT * FROM Orders WHERE status != 'CANCELLED' and status != 'ARRIVED';";
            ResultSet rs = stmt.executeQuery(command);

            int count = 0;

            while (rs.next())
            {
                if (count != rs.getInt("Tracking_ID"))
                {
                    count = rs.getInt("Tracking_ID");
                    line = line + String.format(" --- %07d ---\n", rs.getInt("Tracking_ID"));
                }
                line = line
                        + String.format("%07d", rs.getInt("product_no"))
                        + String.format(" | %f", rs.getDouble("amount"))
                        + String.format(" | %s", rs.getString("status"))
                        + "\n";
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (con != null)
                {
                    con.close();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return line;
        }
    }

    public static String showOrdersAll()
    {
        String line = "";
        Connection con = null;
        try
        {
            Class.forName("org.sqlite.JDBC");

            con = DriverManager.getConnection("jdbc:sqlite:IceCreamShop.db");

            Statement stmt = con.createStatement();

            String command = "SELECT * FROM Orders;";
            ResultSet rs = stmt.executeQuery(command);

            int count = 0;
            while (rs.next())
            {
                if (count != rs.getInt("Tracking_ID"))
                {
                    count = rs.getInt("Tracking_ID");
                    line = line + String.format(" --- %07d ---\n", rs.getInt("Tracking_ID"));
                }
                line = line
                        + String.format("%07d", rs.getInt("product_no"))
                        + String.format(" | %f", rs.getDouble("amount"))
                        + String.format(" | %s", rs.getString("status"))
                        + "\n";
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (con != null)
                {
                    con.close();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return line;
        }
    }

}