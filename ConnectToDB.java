import java.sql.*;
public class ConnectToDB
{
    public Connection getConnectionToDB()
    {
        Connection con=null;
        try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); //registering JDBC Driver
            String connectionUrl = "jdbc:sqlserver://LAPTOP-98F8GH5R\\MYDATABASE;" + "databaseName=kingdom_knn;user=sa;password=123;";  
            con = DriverManager.getConnection(connectionUrl); //Open a connection
        }
        catch(SQLException sqlException) 
        {
            sqlException.printStackTrace();
        }
        catch(Exception ex)
        {
            System.out.println(ex);
        }
        return con;
    }
}
