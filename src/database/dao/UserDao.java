
package database.dao;

import database.entity.User;

/**
 *
 * @author Muhammad
 */
public interface UserDao {
    boolean regist(User user);
    boolean login(User user);
    /*boolean update(User user);
    boolean delete(User user);*/
}
