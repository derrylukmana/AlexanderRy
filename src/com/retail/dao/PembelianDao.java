/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.retail.dao;

import com.retail.main.Main;
import com.retail.model.Pembelian;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Hauw
 */
public class PembelianDao {

    private Connection connection;
    private PreparedStatement insertStatement;
    private PreparedStatement updateStatement;
    private PreparedStatement deleteStatement;
    private PreparedStatement getAllStatement;
    private PreparedStatement getByIdStatement;
    private final String insertQuery = "insert into t_pembelian values(?, ?, ?, ?, ?)";
    private final String updateQuery = "update t_pembelian set tanggal=?, total=?, id_supplier=?, id_user=? where id_pembelian=?";
    private final String deleteQuery = "delete from t_pembelian where id_pembelian=?";
    private final String getAllQuery = "select * from t_pembelian";
    private final String getByIdQuery = "select * from t_pembelian where id_pembelian=?";

    public void setConnection(Connection c) throws SQLException {
        connection = c;
        insertStatement = connection.prepareStatement(insertQuery);
        updateStatement = connection.prepareStatement(updateQuery);
        deleteStatement = connection.prepareStatement(deleteQuery);
        getAllStatement = connection.prepareStatement(getAllQuery);
        getByIdStatement = connection.prepareStatement(getByIdQuery);
    }

    public void save(Pembelian pembelian) throws SQLException {
        insertStatement.setString(1, pembelian.getIdPembelian());
        insertStatement.setTimestamp(2, new Timestamp(pembelian.getTanggal().getTime()));
        insertStatement.setString(3, pembelian.getSupplier().getIdSupplier());
        insertStatement.setBigDecimal(4, pembelian.getTotal());
        insertStatement.setString(5, pembelian.getIdUser());
        insertStatement.executeUpdate();
    }

    public void update(Pembelian pembelian) throws SQLException {
        updateStatement.setTimestamp(1, new Timestamp(pembelian.getTanggal().getTime()));
        updateStatement.setBigDecimal(2, pembelian.getTotal());
        updateStatement.setString(3, pembelian.getSupplier().getIdSupplier());
        updateStatement.setString(4, pembelian.getIdUser());
        updateStatement.setString(5, pembelian.getIdPembelian());
        updateStatement.executeUpdate();
    }

    public void delete(Pembelian pembelian) throws SQLException {
        deleteStatement.setString(1, pembelian.getIdPembelian());
        deleteStatement.executeUpdate();
    }

    public Pembelian getPembelian(String idPembelian) throws SQLException {
        getByIdStatement.setString(1, idPembelian);
        ResultSet rs = getByIdStatement.executeQuery();
        if(rs.next()) {
            Pembelian pembelian = new Pembelian();
            pembelian.setIdPembelian(rs.getString("id_pembelian"));
            pembelian.setTanggal(new Date(rs.getTimestamp("tanggal").getTime()));
            pembelian.setTotal(rs.getBigDecimal("total"));
            pembelian.setSupplier(Main.getSupplierService().getSupplier(rs.getString("id_supplier")));
            pembelian.setIdUser(rs.getString("id_user"));
            return pembelian;
        }
        return null;
    }

    public List<Pembelian> getPembelian() throws SQLException {
        List<Pembelian> list = new ArrayList<Pembelian>();
        ResultSet rs = getAllStatement.executeQuery();
        while(rs.next()) {
            Pembelian pembelian = new Pembelian();
            pembelian.setIdPembelian(rs.getString("id_pembelian"));
            pembelian.setTanggal(new Date(rs.getTimestamp("tanggal").getTime()));
            pembelian.setTotal(rs.getBigDecimal("total"));
            pembelian.setSupplier(Main.getSupplierService().getSupplier(rs.getString("id_supplier")));            
            pembelian.setIdUser(rs.getString("id_user"));
            list.add(pembelian);
        }
        return list;
    }
}
