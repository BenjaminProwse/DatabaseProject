import java.util.Scanner;

public class CommandParse
{

    public static boolean isCommand(String line)
    {
        Scanner sc = new Scanner(line);
        String command = sc.next();

        if (command.equals("!HELP"))
            return true;
        else if (command.equals("!QUIT"))
            return true;

        else if (command.equals("!RUN"))
            return true;
        else if (command.equals("!SELECT"))
            return true;

        else if (command.equals("!MENU"))
            return true;
        else if (command.equals("!MODE"))
            return true;

        else if (command.equals("!SHOW_STOCK"))
            return true;
        else if (command.equals("!SHOW_STOCK_ALL"))
            return true;

        else if (command.equals("!SHOW_USERS"))
            return true;
        else if (command.equals("!ADD_USER"))
            return true;
        else if (command.equals("!REMOVE_USER"))
            return true;

        else if (command.equals("!SHOW_FLAVOURS"))
            return true;
        else if (command.equals("!ADD_FLAVOUR"))
            return true;
        else if (command.equals("!REMOVE_FLAVOUR"))
            return true;

        else if (command.equals("!SHOW_INVOICES"))
            return true;
        else if (command.equals("!ADD_INVOICE"))
            return true;
        else if (command.equals("!REMOVE_INVOICE"))
            return true;
        else if (command.equals("!SHOW_INVOICES_BY_EMPLOYEE"))
            return true;
        else if (command.equals("!SHOW_INVOICE_CONTENTS"))
            return true;


        else if (command.equals("!ADD_ORDER"))
            return true;
        else if (command.equals("!CANCEL_ORDER"))
            return true;
        else if (command.equals("!SHOW_ORDER"))
            return true;
        else if (command.equals("!SHOW_ORDERS"))
            return true;
        else if (command.equals("!SHOW_ORDERS_ALL"))
            return true;

        else if (command.equals("!SHIPMENT_STAGE_UPDATE"))
            return true;
        else if (command.equals("!SHIPMENT_STAGE_SHOW"))
            return true;
        else if (command.equals("!SHOW_SHIPMENT"))
            return true;
        else if (command.equals("!SHOW_SHIPMENTS"))
            return true;
        else if (command.equals("!SHOW_SHIPMENTS_ALL"))
            return true;
        else if (command.equals("!ERASE_SHIPMENT"))
            return true;
        else if (command.equals("!SHOW_STOCK"))
            return true;
        else if (command.equals("!SHOW_STOCK_ALL"))
            return true;
        else
            return false;
    }
}
