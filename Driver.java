import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Driver
{
    private static boolean menuMode = false;
    private static String username;
    private static String password;
    private static int ID = -1;
    private static String line = "";

    public static void main(String[] args) throws InvalidAccessException
    {
        // Main input scanner
        Scanner input = new Scanner(System.in);
    
        // Requesting + Verifying credentials
        do
        {
            System.out.print("Username: ");
            username = input.nextLine();
            System.out.print("Password: ");
            password = input.nextLine();
    
            // Verifying credentials
            try {
                if (Validator.validateCredentials(username, password) == true)
                {
                    System.out.println("Login Successful");
                    ID = Validator.getID(username, password);
                }
                else
                {
                    throw new InvalidAccessException("Invalid Access");
                }
            }
            catch (InvalidAccessException e)
            {
                System.out.println("Incorrect username or password");
            }
        } while (ID == -1);

        
        // Main program loop
        do {
            try
            {
                if (menuMode == true)
                {
                    getMenu();
                }
                else
                {
                    System.out.print("> ");
                    line = input.nextLine();

                    Scanner commandScan = new Scanner(line);
                    String command = commandScan.next().toUpperCase();

                    if (command.equals("!QUIT")) {
                        System.out.println("Terminating Console");
                        System.exit(0);
                    }

                    if (CommandParse.isCommand(command) == true) {
                        //COMMAND IS VALID
                        if (Validator.validAccess(Validator.getID(username, password), command)) {
                            //ACCESS IS VALID
                            //RUN THE COMMAND
                            if (command.equals("!HELP")) {
                                System.out.println(CommandRunner.help());
                            }

                            else if (command.equals("!ADD_USER")) {
                                if (Validator.validAccess(ID,"!ADD_USER") == true)
                                {
                                    addNewUser();
                                    System.out.println("-> User added");
                                }
                                else
                                    throw new InvalidAccessException("Invalid Access");
                            }

                            else if (command.equals("!REMOVE_USER")) {
                                if (Validator.validAccess(ID,"!REMOVE_USER") == true)
                                {
                                    removeUser();
                                    System.out.println("-> User deleted");
                                }
                                else
                                    throw new InvalidAccessException("Invalid Access");

                            }

                            else if (command.equals("!SHOW_USERS")) {
                                if (Validator.validAccess(ID,"!SHOW_USERS") == true)
                                {
                                    System.out.println(CommandRunner.showUsers(ID));
                                }
                                else
                                    throw new InvalidAccessException("Invalid Access");
                            }

                            else if (command.equals("!MENU")) {
                                getMenu();
                            }

                            else if (command.equals("!MODE")) {
                                if (commandScan.hasNext() == true)
                                {
                                    if (commandScan.nextInt() == 1)
                                    {
                                        System.out.println("-> Terminal Mode activated");
                                        menuMode = false;
                                    }
                                    else
                                    {
                                        System.out.println("-> Menu Mode activated");
                                        menuMode = true;
                                    }
                                }
                                else
                                {
                                    menuMode = !menuMode;
                                }

                                //FLAVORS AREA
                            }
                            else if (command.equals("!SHOW_FLAVOURS"))
                            {
                                System.out.println(CommandRunner.showFlavours());
                            }
                            else if (command.equals("!ADD_FLAVOUR"))
                            {
                                if (Validator.validAccess(ID,"!ADD_FLAVOUR") == true)
                                {
                                    addFlavor();
                                    System.out.println("-> Flavour added");
                                }
                                else
                                    throw new InvalidAccessException("Invalid Access");
                            }
                            else if (command.equals("!REMOVE_FLAVOUR"))
                            {
                                if (Validator.validAccess(ID,"!REMOVE_FLAVOUR") == true)
                                {
                                    removeFlavor();
                                    System.out.println("-> Flavour deleted");
                                }
                                else
                                    throw new InvalidAccessException("Invalid Access");
                            }

                            else if (command.equals("!SHOW_INVOICES"))
                            {
                                if (Validator.validAccess(ID,"!SHOW_INVOICES") == true)
                                {
                                    System.out.println(CommandRunner.showInvoices());
                                }
                                else
                                    throw new InvalidAccessException("Invalid Access");
                            }

                            else if (command.equals("!ADD_INVOICE"))
                            {
                                if (Validator.validAccess(ID,"!ADD_INVOICE") == true)
                                {
                                    addInvoice();
                                    System.out.println("-> Invoice added");
                                }
                                else
                                    throw new InvalidAccessException("Invalid Access");
                            }

                            else if (command.equals("!REMOVE_INVOICE"))
                            {
                                if (Validator.validAccess(ID,"!REMOVE_INVOICE") == true)
                                {
                                    removeInvoice();
                                    System.out.println("-> Invoice deleted");
                                }
                                else
                                    throw new InvalidAccessException("Invalid Access");
                            }

                            else if (command.equals("!SHOW_SHIPMENTS"))
                            {
                                if (Validator.validAccess(ID,"!SHOW_SHIPMENTS") == true)
                                {
                                    System.out.println(CommandRunner.showShipments());
                                }
                                else
                                    throw new InvalidAccessException("Invalid Access");
                            }

                            else if (command.equals("!SHOW_SHIPMENT"))
                            {
                                if (Validator.validAccess(ID,"!SHOW_SHIPMENT") == true)
                                    showShipment();
                                else
                                    throw new InvalidAccessException("Invalid Access");
                            }
                            
                            else if (command.equals("!SHIPMENT_STAGE_UPDATE"))
                            {
                                if (Validator.validAccess(ID,"!SHIPMENT_STAGE_UPDATE") == true)
                                    showShipmentStage();
                                else
                                    throw new InvalidAccessException("Invalid Access");
                            }
                            
                            else if (command.equals("!SHIPMENT_STAGE_SHOW"))
                            {
                                if (Validator.validAccess(ID,"!SHIPMENT_STAGE_SHOW") == true)
                                    updateShipmentStage();
                                else
                                    throw new InvalidAccessException("Invalid Access");
                            }

                            else if (command.equals("!ADD_ORDER"))
                            {
                                if (Validator.validAccess(ID,"!ADD_ORDER") == true)
                                {
                                    makeOrder();
                                }
                                else
                                    throw new InvalidAccessException("Invalid Access");
                            }

                            else if (command.equals("!CANCEL_ORDER"))
                            {
                                if (Validator.validAccess(ID,"!CANCEL_ORDER") == true)
                                {
                                    cancelOrder();
                                }
                                else
                                    throw new InvalidAccessException("Invalid Access");
                            }

                            else if (command.equals("!SHOW_ORDER"))
                            {
                                if (Validator.validAccess(ID,"!SHOW_ORDER"))
                                {
                                    showOrder();
                                }
                                else
                                    throw new InvalidAccessException("Invalid Access");
                            }

                            else if (command.equals("!SHOW_ORDERS"))
                            {
                                if (Validator.validAccess(ID,"!SHOW_ORDERS"))
                                    System.out.println(CommandRunner.showOrders());
                                else
                                    throw new InvalidAccessException("Invalid Access");
                            }

                            else if (command.equals("!SHOW_ORDERS_ALL"))
                            {
                                if (Validator.validAccess(ID,"!SHOW_ORDERS_ALL"))
                                    System.out.println(CommandRunner.showOrdersAll());
                                else
                                    throw new InvalidAccessException("Invalid Access");
                            }
                            else if (command.equals("!SHOW_INVOICES_BY_EMPLOYEE"))
                            {
                                if (Validator.validAccess(ID,"!SHOW_INVOICES_BY_EMPLOYEE"))
                                {
                                    showInvoicesByEmployee();
                                }
                                else
                                    throw new InvalidAccessException("Invalid Access");
                            }
                            else if (command.equals("!SHOW_INVOICE_CONTENTS"))
                            {
                                if (Validator.validAccess(ID,"!SHOW_INVOICE_CONTENTS"))
                                {
                                    showInvoiceContents();
                                }
                                else
                                    throw new InvalidAccessException("Invalid Access");
                            }

                            else if (command.equals("!SHOW_STOCK"))
                            {
                                if (Validator.validAccess(ID,"!SHOW_STOCK"))
                                    System.out.println(CommandRunner.showStock());
                                else
                                    throw new InvalidAccessException("Invalid Access");
                            }

                            else if (command.equals("!SHOW_STOCK_ALL"))
                            {
                                if (Validator.validAccess(ID,"!SHOW_STOCK_ALL"))
                                    System.out.println(CommandRunner.showStockAll());
                                else
                                    throw new InvalidAccessException("Invalid Access");
                            }

                            else if (command.equals("!ADD_STAFF"))
                            {
                                if (Validator.validAccess(ID,"!ADD_STAFF"))
                                    addStaff();
                                else
                                    throw new InvalidAccessException("Invalid Access");
                            }
                            else if (command.equals("!REMOVE_STAFF"))
                            {
                                if (Validator.validAccess(ID,"!REMOVE_STAFF"))
                                    removeStaff();
                                else
                                    throw new InvalidAccessException("Invalid Access");
                            }
                            else if (command.equals("!SHOW_STAFF"))
                            {
                                if (Validator.validAccess(ID,"!SHOW_STAFF"))
                                    System.out.println(CommandRunner.showStaff());
                                else
                                    throw new InvalidAccessException("Invalid Access");
                            }
                        }
                        else if (command.equals("!SHOW_STOCK"))
                        {
                            if (Validator.validAccess(ID,"!SHOW_STOCK"))
                                System.out.println(CommandRunner.showStock());
                            else
                                throw new InvalidAccessException("Invalid Access");
                        }
                        else if (command.equals("!SHOW_STOCK_ALL"))
                        {
                            if (Validator.validAccess(ID,"!SHOW_STOCK_ALL"))
                                System.out.println(CommandRunner.showStockAll());
                            else
                                throw new InvalidAccessException("Invalid Access");
                        }
                        else
                        {
                            throw new InvalidAccessException("Invalid Access");
                        }
                    }
                    else
                    {
                        throw new InvalidArguementsException(line);
                    }
                }
            }
            catch (InvalidArguementsException e)
            {
                System.out.println("'" + e.getMessage() + "' is an Invalid command");
            }
            catch (InvalidAccessException e)
            {
                System.out.println("Access denied.");
            }
            catch (Exception e)
            {
                
            }
        } while (!line.equals("!QUIT"));
    }

    public static void getMenu()
    {
        Scanner input = new Scanner(System.in);

        System.out.println("--- Menu ---");
        System.out.println("-- User Management:");
        System.out.println("1. Add a new user");
        System.out.println("2. Remove a user");
        System.out.println("3. List all users");
        System.out.println("-- Stock Management:");
        System.out.println("4. List stock");
        System.out.println("5. List flavours");
        System.out.println("6. Add a new flavour");
        System.out.println("7. Remove a flavour");
        System.out.println("-- Employee Performance Management:");
        System.out.println("8. Log a new invoice");
        System.out.println("9. Browse all invoices");
        System.out.println("10. View invoices logged by a specific employee");
        System.out.println("11. View contents of a particular invoice");
        System.out.println("-- Logistics Management:");
        System.out.println("12. View all active orders");
        System.out.println("13. View all orders");
        System.out.println("14. View details on a specific order");
        System.out.println("15. Cancel an active order");
        System.out.println("16. View all shipments en route");
        System.out.println("17. View details on a particular shipment");
        System.out.println("18. Update a status of a shipment");
        System.out.println("0. Exit Menu");
        System.out.print(": ");

        int response = input.nextInt();

        switch (response)
        {
            case 1:
                if (Validator.validAccess(ID,"!ADD_USER") == true)
                {
                    addNewUser();
                    System.out.println("-> User added");
                }
                else
                    throw new InvalidAccessException("Invalid Access");
                break;
            case 2:
                if (Validator.validAccess(ID,"!REMOVE_USER") == true)
                {
                    removeUser();
                    System.out.println("-> User deleted");
                }
                else
                    throw new InvalidAccessException("Invalid Access");
                break;
            case 3:
                if (Validator.validAccess(ID,"!SHOW_USERS") == true)
                {
                    System.out.println(CommandRunner.showUsers(ID));
                }
                else
                    throw new InvalidAccessException("Invalid Access");
                break;
            case 4:
                if (Validator.validAccess(ID,"!SHOW_STOCK"))
                    System.out.println(CommandRunner.showStock());
                else
                    throw new InvalidAccessException("Invalid Access");
                break;
            case 5:
                System.out.println(CommandRunner.showFlavours());
                break;
            case 6:
                if (Validator.validAccess(ID,"!ADD_FLAVOUR") == true)
                {
                    addFlavor();
                    System.out.println("-> Flavour added");
                }
                else
                    throw new InvalidAccessException("Invalid Access");
                break;
            case 7:
                if (Validator.validAccess(ID,"!REMOVE_FLAVOUR") == true)
                {
                    removeFlavor();
                    System.out.println("-> Flavour deleted");
                }
                else
                    throw new InvalidAccessException("Invalid Access");
                break;
            case 8:
                if (Validator.validAccess(ID,"!ADD_INVOICE") == true)
                {
                    addInvoice();
                    System.out.println("-> Invoice added");
                }
                else
                    throw new InvalidAccessException("Invalid Access");
                break;
            case 9:
                if (Validator.validAccess(ID,"!SHOW_INVOICES") == true)
                {
                    System.out.println(CommandRunner.showInvoices());
                }
                else
                    throw new InvalidAccessException("Invalid Access");
                break;
            case 10:
                if (Validator.validAccess(ID,"!SHOW_INVOICES_BY_EMPLOYEE"))
                {
                    showInvoicesByEmployee();
                }
                else
                    throw new InvalidAccessException("Invalid Access");
                break;
            case 11:
                if (Validator.validAccess(ID,"!SHOW_INVOICE_CONTENTS"))
                {
                    showInvoiceContents();
                }
                else
                    throw new InvalidAccessException("Invalid Access");
                break;
            case 12:
                if (Validator.validAccess(ID,"!SHOW_ORDERS"))
                    System.out.println(CommandRunner.showOrders());
                else
                    throw new InvalidAccessException("Invalid Access");
                break;
            case 13:
                if (Validator.validAccess(ID,"!SHOW_ORDERS_ALL"))
                    System.out.println(CommandRunner.showOrdersAll());
                else
                    throw new InvalidAccessException("Invalid Access");
                break;
            case 14:
                if (Validator.validAccess(ID,"!SHOW_ORDER"))
                {
                    showOrder();
                }
                else
                    throw new InvalidAccessException("Invalid Access");
                break;
            case 15:
                if (Validator.validAccess(ID,"!CANCEL_ORDER") == true)
                {
                    cancelOrder();
                }
                else
                    throw new InvalidAccessException("Invalid Access");
                break;
            case 16:
                if (Validator.validAccess(ID,"!SHOW_SHIPMENTS") == true)
                {
                    System.out.println(CommandRunner.showShipments());
                }
                else
                    throw new InvalidAccessException("Invalid Access");
                break;
            case 17:
                if (Validator.validAccess(ID,"!SHOW_SHIPMENT") == true)
                    showShipment();
                else
                    throw new InvalidAccessException("Invalid Access");
                break;
            case 18:
                if (Validator.validAccess(ID,"!SHIPMENT_STAGE_UPDATE") == true)
                    showShipmentStage();
                else
                    throw new InvalidAccessException("Invalid Access");
                break;
            case 0:
                menuMode = false;
                break;
            default:
                System.out.println("Invalid Response");
        }
    }

    // ANY METHOD THAT REQUIRES INPUT SHOULD BE IN THE DRIVER
    private static void addNewUser()
    {
        Scanner input = new Scanner(System.in);
        System.out.println("--- New User ---");
        System.out.print("Name: ");
        String name = input.nextLine();
        System.out.print("Username: ");
        String username = input.nextLine();
        System.out.print("Password: ");
        String password = input.nextLine();
        System.out.print("Access: ");
        int access = input.nextInt();
        CommandRunner.addUser(name, username, password, access);
    }

    private static void removeUser()
    {
        Scanner input = new Scanner(System.in);
        System.out.println("--- Remove User ---");
        System.out.println("1. Username");
        System.out.print("2. ID \n: ");
        int response = input.nextInt();
        switch (response)
        {
            case 1:
                System.out.print("Username: ");
                String username = input.nextLine();
                CommandRunner.removeUser(username);
                break;
            case 2:
                System.out.print("ID: ");
                int id = input.nextInt();
                CommandRunner.removeUser(id);
                break;
            default:
                System.out.println("Invalid Option");
                break;
        }
    }

    private static void removeFlavor()
    {
        Scanner input = new Scanner(System.in);
        System.out.println("--- Add Flavor ---");
        System.out.println("1. Product Number");
        System.out.print("2. Name \n: ");

        int response = input.nextInt();

        switch (response)
        {
            case 1:
                System.out.print("Product: ");
                int productNum = input.nextInt();
                CommandRunner.removeFlavor(productNum);
                break;
            case 2:
                System.out.print("Name: ");
                String name = input.nextLine();
                CommandRunner.removeFlavour(name);
                break;
            default:
                System.out.println("Invalid Option");
                break;
        }
    }

    private static void addFlavor()
    {
        Scanner input = new Scanner(System.in);
        System.out.println("--- Add Flavor ---");
        System.out.print("Name: ");
        String name = input.nextLine();
        System.out.print("Restrictions: ");
        String restrictions = input.nextLine();
        System.out.print("Calories: ");
        int calories = input.nextInt();
        System.out.print("Price per Scope: ");
        double pricePerScope = input.nextDouble();

        CommandRunner.addFlavor(name, restrictions, calories,pricePerScope);
    }

    private static void addInvoice()
    {
        Scanner input = new Scanner(System.in);
        System.out.println("--- Add Invoice ---");
        System.out.print("Employee ID: ");
        int emp_id = input.nextInt();

        System.out.print("How many cones: ");
        int cones = input.nextInt();

        ArrayList <Integer> prod_nos = new ArrayList<>();
        ArrayList <Integer> scopes = new ArrayList<>();


        for (int i = 0; i < cones; i++)
        {
            System.out.print("Flavor ID: ");
            int prod_no = input.nextInt();

            System.out.print("Scopes: ");
            int scope = input.nextInt();

            prod_nos.add(prod_no);
            scopes.add(scope);
        }

        double subTotal = 0;
        double total = 0;
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
                for (int i = 0; i < prod_nos.size(); i++)
                {
                    if (prod_nos.get(i) == rs.getInt("Product_No"))
                    {
                        double price = rs.getDouble("Price");
                        subTotal = subTotal + price * scopes.get(i);
                    }
                }
            }
            System.out.println("SubTotal: "+subTotal);
            total = Math.round(subTotal * 1.15);
            System.out.println("Total Price: "+total);

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
        }

        System.out.print("Amount Given: ");
        double giveAmount = input.nextDouble();
        input.nextLine();

        double change = total - giveAmount;
        System.out.println("Change: "+change);

        LocalDateTime today = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy");

        String date = formatter.format(today);

        System.out.println("Date (DD//MM//YY): "+date);

        CommandRunner.addInvoice(subTotal, total, giveAmount, change, date, emp_id, prod_nos,scopes);
    }

    private static void removeInvoice()
    {
        Scanner input = new Scanner(System.in);
        System.out.println("--- Remove Invoice ---");
        System.out.print("Invoice Number: ");
        int id = input.nextInt();

        CommandRunner.removeInvoice(id);
    }

    private static void showInvoicesByEmployee()
    {
        System.out.println("--- Show Invoice By Employee ---");
        Scanner input = new Scanner(System.in);
        System.out.print("Enter employee ID: ");
        int empID = input.nextInt();
        System.out.println(CommandRunner.showInvoiceByID(empID));
        input.nextLine();
    }

    private static void showInvoiceContents()
    {
        System.out.println("--- Show Invoice Contents ---");
        Scanner input = new Scanner(System.in);
        System.out.print("Enter invoice ID: ");
        int id = input.nextInt();
        System.out.println(CommandRunner.showInvoiceContents(id));
    }

    //NOTE: THERE IS NO REMOVE ORDER. SET THE STAGE TO 'CANCELED' AND LEAVE DATE AT NULL
    private static void makeOrder()
    {
        Scanner input = new Scanner(System.in);
        System.out.println("--- Add Order ---");

        char response;
        ArrayList <Integer> productNum = new ArrayList<>();
        ArrayList <Double> amount = new ArrayList<>();
        do
        {
            System.out.print("Product Number: ");
            productNum.add(input.nextInt());
            System.out.print("Amount (KG): ");
            amount.add(input.nextDouble());
            System.out.print("Next? Y/N: ");
            response = input.next().charAt(0);
        } while (response == 'Y' || response == 'y');

        //Return an int for the add to order thing
        int orderID = CommandRunner.makeOrder(productNum.get(0), amount.get(0));
        for (int i = 1; i < productNum.size(); i++)
        {
            CommandRunner.addToOrder(productNum.get(i),amount.get(i),orderID);
        }
        System.out.println("-> Order Placed");
        System.out.println("-> Order ID: " + orderID);
        System.out.println("-> Shipment will arrive in 5 days");
    }

    private static void cancelOrder()
    {
        Scanner input = new Scanner(System.in);
        System.out.println("--- Cancel Order ---");
        System.out.print("Order ID: ");
        int id = input.nextInt();
        CommandRunner.updateShipment(id,"CANCELLED");
        System.out.println("-> Order Cancelled");
    }

    private static void showOrder()
    {
        Scanner input = new Scanner(System.in);
        System.out.println("--- Show Order ---");
        System.out.print("Order ID: ");
        int id = input.nextInt();
        System.out.println(CommandRunner.showOrder(id));
    }

    private static void updateShipmentStage()
    {
        Scanner input = new Scanner(System.in);
        System.out.println("--- Update Shipment Stage ---");
        int id = -1;
        do
        {
            try
            {
                System.out.print("Shipment Number: ");
                id = input.nextInt();
            }
            catch (Exception e)
            {
                System.out.println("Invalid input!");
                id = -1;
            }
        } while (id == -1);
        System.out.print("Shipment Stage: ");
        String stage = input.nextLine();

        CommandRunner.updateShipment(id, stage);

        System.out.println("-> Shipment Updated");
        if (stage.equals("ARRIVED"))
            System.out.println("-> Stock Updated");
    }

    public static String showShipmentStage()
    {
        Scanner input = new Scanner(System.in);
        System.out.println("--- Request Shipment Stage ---");
        int trackingID = -1;
        do
        {
            try
            {
                System.out.print("Shipment Number: ");
                trackingID = input.nextInt();
            }
            catch (Exception e)
            {
                System.out.println("Invalid input!");
                trackingID = -1;
            }
        } while (trackingID == -1);
        
        String line = "";
        Connection con = null;
        try
        {
            Class.forName("org.sqlite.JDBC");

            con = DriverManager.getConnection("jdbc:sqlite:IceCreamShop.db");

            Statement stmt = con.createStatement();

            String command = String.format("SELECT Status FROM Shipment WHERE Tracking_ID = %d;", trackingID);
            ResultSet rs = stmt.executeQuery(command);

            if (rs.next())
            {
                line = line
                        + String.format("%07d", rs.getInt("Tracking_ID"))
                        + String.format(" | %s", rs.getString("Status"))
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
    
    private static void showShipment()
    {
        Scanner input = new Scanner(System.in);
        System.out.println("--- Show Shipment ---");
        System.out.print("Shipment ID: ");
        int id = input.nextInt();
        System.out.println(CommandRunner.showShipment(id));
    }

    private static void addStaff()
    {
        Scanner input = new Scanner(System.in);
        System.out.println("--- Add Staff ---");
        System.out.print("Staff Name: ");
        String name = input.nextLine();
        System.out.print("Staff Phone: ");
        String phone = input.nextLine();
        System.out.print("Staff Wage: ");
        double wage = input.nextDouble();
        input.nextLine();
        System.out.print("Staff Position: ");
        String position = input.nextLine();
        System.out.print("Staff Start Date: ");
        String startDate = input.nextLine();

        CommandRunner.addStaff(name, phone, wage, position, startDate);
        System.out.println("-> Staff Added");
    }

    private static void removeStaff()
    {
        Scanner input = new Scanner(System.in);
        System.out.println("--- Remove Staff ---");
        System.out.print("Staff ID: ");
        int id = input.nextInt();

        CommandRunner.removeStaff(id);
        System.out.println("-> Staff Removed");
    }
}
