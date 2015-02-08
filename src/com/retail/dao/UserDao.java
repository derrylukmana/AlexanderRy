/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.retail.dao;

import com.retail.model.Role;
import com.retail.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hauw
 */
public class UserDao {
    private Connection connection;
    private PreparedStatement insertStatement;
    private PreparedStatement updateStatement;
    private PreparedStatement deleteStatement;
    private PreparedStatement getByIdStatement;
    private PreparedStatement getAllStatement;
    private PreparedStatement getPasswordStatement;
    private PreparedStatement getRoleStatement;
    private final String insertQuery = "insert into t_user values(?,?,?,?,?,?)";
    private final String updateQuery = "update t_user set password=?, nama=?, alamat=?, telepon=?, role=? where id_user=?";
    private final String deleteQuery = "delete from t_user where id_user=?";
    private final String getByIdQuery = "select * from t_user where id_user = ?";
    private final String getAllQuery = "select * from t_user";
    private final String getPasswordQuery = "select password from t_user where id_user = ?";
    private final String getRoleQuery = "select role from t_user where id_user=?";
    
    public void setConnection(Connection c) throws SQLException {
        connection = c;
        insertStatement = connection.prepareStatement(insertQuery);
        updateStatement = connection.prepareStatement(updateQuery);
        deleteStatement = connection.prepareStatement(deleteQuery);
        getByIdStatement = connection.prepareStatement(getByIdQuery);
        getAllStatement = connection.prepareStatement(getAllQuery);
        getPasswordStatement = connection.prepareStatement(getPasswordQuery);
    }
    
    public void save(User user) throws SQLException {
        insertStatement.setString(1, user.getIdUser());
        insertStatement.setString(2, user.getPassword());
        insertStatement.setString(3, user.getNama());
        insertStatement.setString(4, user.getAlamat());
        insertStatement.setString(5, user.getTelepon());
        insertStatement.setString(6, user.getRole().toString());
        insertStatement.executeUpdate();
    }
    
    public void update(User user) throws SQLException {        
        updateStatement.setString(1, user.getPassword());
        updateStatement.setString(2, user.getNama());
        updateStatement.setString(3, user.getAlamat());
        updateStatement.setString(4, user.getTelepon());
        updateStatement.setString(5, user.getRole().toString());
        updateStatement.setString(6, user.getIdUser());
        updateStatement.executeUpdate();        
    }
    
    public void delete(User user) throws SQLException {
        deleteStatement.setString(1, user.getIdUser());
        deleteStatement.executeUpdate();
    }
    
    public User getUser(String idUser) throws SQLException {
        getByIdStatement.setString(1, idUser);
        ResultSet rs = getByIdStatement.executeQuery();
        if(rs.next()) {
            User user = new User();
            user.setIdUser(rs.getString("id_user"));
            user.setPassword(rs.getString("password"));
            user.setNama(rs.getString("nama"));
            user.setAlamat(rs.getString("alamat"));
            user.setTelepon(rs.getString("telepon"));
            user.setRole(Role.valueOf(rs.getString("role")));
            return user;
        }
        return null;
    }
    
    public List<User> getUser() throws SQLException {
        List<User> list = new ArrayList<User>();
        ResultSet rs = getAllStatement.executeQuery();
        while(rs.next()) {
            User user = new User();
            user.setIdUser(rs.getString("id_user"));
            user.setPassword(rs.getString("password"));
            user.setNama(rs.getString("nama"));
            user.setAlamat(rs.getString("alamat"));
            user.setTelepon(rs.getString("telepon"));
            user.setRole(Role.valueOf(rs.getString("role")));
            list.add(user);
        }
        return list;
    }
    
    public boolean checkLogin(String userName, String password) throws SQLException {
        getPasswordStatement.setString(1, userName);
        ResultSet rs = getPasswordStatement.executeQuery();
        if(rs.next()) {
            if(password.equals(rs.getString("password"))) {
                return true;
            }
        }
        return false;
    }
    
    public Role getUserRole(String userName) throws SQLException {
        getRoleStatement.setString(1, userName);
        ResultSet rs = getRoleStatement.executeQuery();
        if(rs.next()) {
            Role role = Role.valueOf(rs.getString("role"));
            return role;
        }
        return null;
    }
}
