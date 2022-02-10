
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.derby.jdbc.ClientDriver;
/**
 *
 * @author Muhammad
 */
public abstract class ConnectionManager {
    
    public static Connection getConnection() throws SQLException{
        System.out.println("Entered to connect");
        DriverManager.registerDriver(new ClientDriver());
        Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/ChatDB", "chat", "chat");
        return con;
    }
    
    public static void closeConnection(Connection con){
        try {
            con.close();
        } catch (SQLException ex) {
            System.out.println("Can't Close Connection");
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

