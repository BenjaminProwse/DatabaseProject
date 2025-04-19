import java.io.IOException;
import java.net.ServerSocket;
import java.sql.*;

public class Validator
{
    public static boolean validateCredentials(String username, String password)
    {
        Connection con = null;
        try
        {
            Class.forName("org.sqlite.JDBC");

            con = DriverManager.getConnection("jdbc:sqlite:IceCreamShop.db");

            Statement stmt = con.createStatement();

            String findUser = "SELECT COUNT(*) from LoginInfo WHERE USERNAME = '"+username+"' and password = '"+password+"';";
            ResultSet rs = stmt.executeQuery(findUser);

            if (rs.getInt(1) == 1)
            {
                return true;
            }
            return false;



        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
        catch (ClassNotFoundException e)
        {
            System.out.println(e);
            return false;
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

    public static boolean validAccess(int ID, String line)
    {
        Connection con = null;
        try
        {
            Class.forName("org.sqlite.JDBC");

            con = DriverManager.getConnection("jdbc:sqlite:IceCreamShop.db");

            Statement stmt = con.createStatement();

            String getIDQuery = "Select access from LoginInfo where ID = "+ID+";";
            ResultSet rs = stmt.executeQuery(getIDQuery);

            int accessLevel = 0;
            if (rs.next())
            {
                accessLevel = rs.getInt(1);
            }

            String getCommandAccessQuery = "Select access from commandList where command = '"+line+"';";
            ResultSet rs2 = stmt.executeQuery(getCommandAccessQuery);

            int commandLevel = 0;
            if (rs2.next())
            {
                commandLevel = rs2.getInt(1);
            }

            if ((accessLevel != 0)&&(commandLevel != 0))
            {
                //Access levels retreived
                if (accessLevel <= commandLevel)
                {
                    return true;
                }
                return false;
            }
            return false;

        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
        catch (ClassNotFoundException e)
        {
            System.out.println(e);
            return false;
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

    public static int getID(String username, String password)
    {
        Connection con = null;
        try (ServerSocket serverSocket = new ServerSocket(8080))
        {
            Class.forName("org.sqlite.JDBC");

            con = DriverManager.getConnection("jdbc:sqlite:IceCreamShop.db");

            Statement stmt = con.createStatement();

            String findUser = "SELECT ID from LoginInfo WHERE USERNAME = '"+username+"' and password = '"+password+"';";
            ResultSet rs = stmt.executeQuery(findUser);

            if (rs.next())
            {
                return rs.getInt("ID");
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
        catch (ClassNotFoundException e)
        {
            System.out.println(e);
        }
        catch (IOException e)
        {
            System.out.println(e);
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
        return 0;
    }

    public static int getIDAccess(int ID)
    {
        Connection con = null;
        try (ServerSocket serverSocket = new ServerSocket(8080))
        {
            Class.forName("org.sqlite.JDBC");

            con = DriverManager.getConnection("jdbc:sqlite:IceCreamShop.db");

            Statement stmt = con.createStatement();

            String findAccess = "SELECT access from LoginInfo WHERE ID = "+ID+";";
            ResultSet rs = stmt.executeQuery(findAccess);

            if (rs.next())
            {
                return rs.getInt("access");
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
        catch (ClassNotFoundException e)
        {
            System.out.println(e);
        }
        catch (IOException e)
        {
            System.out.println(e);
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
        return 0;
    }

}
