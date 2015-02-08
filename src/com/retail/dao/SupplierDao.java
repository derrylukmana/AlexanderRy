/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.retail.dao;

import com.retail.model.Supplier;
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
public class SupplierDao {

    private Connection connection;
    private PreparedStatement insertStatement;
    private PreparedStatement updateStatement;
    private PreparedStatement deleteStatement;
    private PreparedStatement getByIdStatement;
    private PreparedStatement getAllStatement;
    private PreparedStatement getByNameStatement;
    private final String insertQuery = "insert into t_supplier values(?,?,?,?)";
    private final String updateQuery = "update t_supplier set nama_supplier=?, alamat=?, telepon=? where id_supplier=?";
    private final String deleteQuery = "delete from t_supplier where id_supplier=?";
    private final String getByIdQuery = "select * from t_supplier where id_supplier=?";
    private final String getAllQuery = "select * from t_supplier";
    private final String getByNameQuery = "select * from t_supplier where nama_supplier=?";

    public void setConnection(Connection c) throws SQLException {
        connection = c;
        insertStatement = connection.prepareStatement(insertQuery);
        updateStatement = connection.prepareStatement(updateQuery);
        deleteStatement = connection.prepareStatement(deleteQuery);
        getByIdStatement = connection.prepareStatement(getByIdQuery);
        getAllStatement = connection.prepareStatement(getAllQuery);
        getByNameStatement = connection.prepareStatement(getByNameQuery);
    }

    public void save(Supplier supplier) throws SQLException {
        insertStatement.setString(1, supplier.getIdSupplier());
        insertStatement.setString(2, supplier.getNamaSupplier());
        insertStatement.setString(3, supplier.getAlamat());
        insertStatement.setString(4, supplier.getTelepon());
        insertStatement.executeUpdate();
    }

    public void update(Supplier supplier) throws SQLException {
        updateStatement.setString(1, supplier.getNamaSupplier());
        updateStatement.setString(2, supplier.getAlamat());
        updateStatement.setString(3, supplier.getTelepon());
        updateStatement.setString(4, supplier.getIdSupplier());
        updateStatement.executeUpdate();
    }

    public void delete(Supplier supplier) throws SQLException {
        deleteStatement.setString(1, supplier.getIdSupplier());
        deleteStatement.executeUpdate();
    }

    public Supplier getSupplier(String idSupplier) throws SQLException {
        getByIdStatement.setString(1, idSupplier);
        ResultSet rs = getByIdStatement.executeQuery();
        if (rs.next()) {
            Supplier supplier = new Supplier();
            supplier.setIdSupplier(rs.getString("id_supplier"));
            supplier.setNamaSupplier(rs.getString("nama_supplier"));
            supplier.setAlamat(rs.getString("alamat"));
            supplier.setTelepon(rs.getString("telepon"));
            return supplier;
        }
        return null;
    }
    
    public Supplier getSupplierByName(String namaSupplier) throws SQLException {
        getByNameStatement.setString(1, namaSupplier);
        ResultSet rs = getByNameStatement.executeQuery();
        if (rs.next()) {
            Supplier supplier = new Supplier();
            supplier.setIdSupplier(rs.getString("id_supplier"));
            supplier.setNamaSupplier(rs.getString("nama_supplier"));
            supplier.setAlamat(rs.getString("alamat"));
            supplier.setTelepon(rs.getString("telepon"));
            return supplier;
        }
        return null;    
    }

    public List<Supplier> getSupplier() throws SQLException {
        ResultSet rs = getAllStatement.executeQuery();
        List<Supplier> list = new ArrayList<Supplier>();
        while (rs.next()) {
            Supplier supplier = new Supplier();
            supplier.setIdSupplier(rs.getString("id_supplier"));
            supplier.setNamaSupplier(rs.getString("nama_supplier"));
            supplier.setAlamat(rs.getString("alamat"));
            supplier.setTelepon(rs.getString("telepon"));
            list.add(supplier);
        }
        return list;
    }
}
