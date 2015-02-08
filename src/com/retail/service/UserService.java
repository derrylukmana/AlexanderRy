/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.retail.service;

import com.retail.dao.UserDao;
import com.retail.model.Role;
import com.retail.model.User;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 *
 * @author Hauw
 */
public class UserService {
    private UserDao userDao;
    private Connection connection;
    
    public void setDataSource(DataSource dataSource) {
        try {
            connection = dataSource.getConnection();
            userDao = new UserDao();
            userDao.setConnection(connection);
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    public void save(User user) {
        try {
            connection.setAutoCommit(false);
            userDao.save(user);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }
    
    public void update(User user) {
        try {
            connection.setAutoCommit(false);
            userDao.update(user);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }
    
    public void delete(User user) {
        try {
            connection.setAutoCommit(false);
            userDao.delete(user);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }
    
    public User getUser(String idUser) {
        try {
            return userDao.getUser(idUser);
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public List<User> getUser() {
        List<User> list = new ArrayList<User>();
        try {
            list = userDao.getUser();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public boolean checkLogin(String userName, String password) {
        try {
            return userDao.checkLogin(userName, password);
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public Role getUserRole(String userName) {
        try {
            return userDao.getUserRole(userName);
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
