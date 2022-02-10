
package database.dao;

import database.ConnectionManager;
import database.entity.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 *
 * @author Muhammad
 */
public class UserDaoImpl implements UserDao{
    Connection con;

    @Override
    public boolean regist(User user) {
        System.out.println("in regist method");
        boolean added = false;
        try {
            con = ConnectionManager.getConnection();
            System.out.println("Connected");
            PreparedStatement statement = con.prepareStatement("INSERT INTO USERS VALUES (?,?)");
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.executeUpdate();
            added = true;
            statement.close();
            con.close();
        } catch (SQLException ex) {
            added = false;
            ex.printStackTrace();
            
        }
        return added;
    }

    @Override
    public boolean login(User user) {
        System.out.println("in login method");

        boolean login = false;
        try {
            con = ConnectionManager.getConnection();
            Statement stmt = con.createStatement();
            String query = new String("SELECT * FROM USERS");
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                String name = rs.getString(1);
                String pass = rs.getString(2);
                if(name.equals(user.getUsername()) && pass.equals(user.getPassword())){
                    login = true;
                    break;
                }
            }
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return login;
    }
    
}
