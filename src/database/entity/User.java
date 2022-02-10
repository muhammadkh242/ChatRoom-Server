
package database.entity;

/**
 *
 * @author Muhammad
 */
public class User {
    private String username;
    private String password;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("User : " + this.username + "\n");
        sb.append("Password : "+ this.password + "\n");
        
        return sb.toString();
    }
    
    
}
