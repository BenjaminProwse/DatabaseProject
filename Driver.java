import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Driver
{
    private static boolean menuMode = false;
    private static String username;
    private static String password;
    private static int ID;

    public static void main(String[] args) throws InvalidAccessException
    {
        Scanner input = new Scanner(System.in);

        // This is for presending username and password bc testing took forever
        System.out.print("args (y/n): ");
        if (input.nextLine().equalsIgnoreCase("y"))
        {
            if ((args[0] != null) && (args[1] != null))
            {
                username = args[0];
                password = args[1];
            }
        }
        else
        {
            System.out.print("Username: ");
            username = input.nextLine();
            System.out.print("Password: ");
            password = input.nextLine();
        }

        try {


            if (Validator.validateCredentials(username, password) == true)
            {
                ID = Validator.getID(username, password);
                String line = "";
                System.out.println("Login Successful");
                do {
                    if (menuMode == true)
                    {
                        getMenu();
                    }
                    else
                    {
                        System.out.print("> ");
                        line = input.nextLine();

                        Scanner commandScan = new Scanner(line);
                        String command = commandScan.next();

                        if (command.equals("!QUIT")) {
                            System.out.println("Terminating Console");
                            System.exit(0);
                        }

                        if (CommandParse.isCommand(line) == true) {
                            //COMMAND IS VALID
                            if (Validator.validAccess(Validator.getID(username, password), new Scanner(line).next())) {
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
                            }
                            else
                            {
                                throw new InvalidAccessException("Invalid Access");
                            }
                        }
                        else
                        {
                            throw new InvalidArguementsException("'" + line + "' is an Invalid command");
                        }
                    }
                } while (!line.equals("!QUIT"));
            }
            else
            {
                throw new InvalidAccessException("Invalid Access");
            }
        }
        catch (InvalidAccessException e)
        {
            System.out.println("Invalid Access");
            e.printStackTrace();
        }

    }

    public static void getMenu()
    {
        Scanner input = new Scanner(System.in);

        System.out.println("--- Menu ---");
        System.out.println("1. Create a new user");
        System.out.println("2. Delete a user");
        System.out.println("3. Show all users");
        System.out.println("4. Show Stock");
        System.out.println("5. Exit Menu");
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
                break;
            case 5:
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

        CommandRunner.addFlavor(name, restrictions, calories);
    }

    private static void addInvoice()
    {
        Scanner input = new Scanner(System.in);
        System.out.println("--- Add Invoice ---");
        System.out.print("SubTotal: ");
        double subTotal = input.nextDouble();
        System.out.print("Total: ");
        double total = input.nextDouble();
        System.out.print("Amount Due: ");
        double amount = input.nextDouble();
        System.out.print("Amount Given: ");
        double giveAmount = input.nextDouble();
        System.out.print("Change: ");
        double change = input.nextDouble();
        System.out.print("Date (DD//MM//YY): ");
        String date = input.nextLine();

        CommandRunner.addInvoice(subTotal, total, amount, giveAmount, change, date);
    }

    private static void removeInvoice()
    {
        Scanner input = new Scanner(System.in);
        System.out.println("--- Remove Invoice ---");
        System.out.print("Invoice Number: ");
        int id = input.nextInt();

        CommandRunner.removeInvoice(id);
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
        System.out.print("Shipment Number: ");
        int id = input.nextInt();
        System.out.print("Shipment Stage: ");
        String stage = input.nextLine();

        CommandRunner.updateShipment(id, stage);

        System.out.println("-> Shipment Updated");
        if (stage.equals("ARRIVED"))
            System.out.println("-> Stock Updated");
    }

    private static void showShipments(boolean isAll)
    {
        if (isAll)
            CommandRunner.showShipments();
        else
            CommandRunner.showShipmentsAll();
    }

    private static void showShipment()
    {
        Scanner input = new Scanner(System.in);
        System.out.println("--- Show Shipment ---");
        System.out.print("Shipment ID: ");
        int id = input.nextInt();
        System.out.println(CommandRunner.showShipment(id));
    }
}
