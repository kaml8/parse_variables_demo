package example.service;

import example.dao.UserDao;
import example.dao.impl.UserDaoImpl;

import java.util.*;
import java.io.*;

/**
 * The project is a classic Web Project with register function.
 * Now I want to analysis the project to find potential relations between classes through Qdox.
 * seeing @test
 */
public class RegisterService {

    String path = "/register";

    /**
     * register method
     *
     * @param name     date received from controller
     * @param password date received from controller
     * @return result
     */
    public static boolean register(String name, String password) {

        UserDao userDao = UserDaoImpl.getInstance();
        boolean flag = false;

        if (userDao.addUser(name, password)) {
            flag = true;
        }
        // The return statement can be simpified as "return userDao.addUser(name, password)"
        // However, source code won't operate this optimization as text.
        return flag;
    }
}
