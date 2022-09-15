package example.dao.impl;

import example.dao.UserDao;

/**
 * A implementation for interface "UserDao".
 */
public class UserDaoImpl implements UserDao {

    // Singleton pattern code
    private static UserDaoImpl instance;

    public static UserDaoImpl getInstance() {
        if (instance == null) {
            instance = new UserDaoImpl();
        }
        return instance;
    }

    public boolean addUser(String name,String password) {
        // JDBC insert operation...
        return true;
    }
}
