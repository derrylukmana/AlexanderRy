/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.retail.dao;

import com.retail.model.Penjualan;
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
public class PenjualanDao {

    private Connection connection;
    private PreparedStatement insertStatement;
    private PreparedStatement updateStatement;
    private PreparedStatement deleteStatement;
    private PreparedStatement getByIdStatement;
    private PreparedStatement getByIdMemberStatement;
    private PreparedStatement getAllStatement;
    private final String insertQuery = "insert into t_penjualan values(?,?,?,?,?,?)";
    private final String updateQuery = "update t_penjualan set tanggal=?, total=?, diskon=?, id_user=?, id_member=? where id_penjualan=?";
    private final String deleteQuery = "delete from t_penjualan where id_penjualan=?";
    private final String getByIdQuery = "select * from t_penjualan where id_penjualan=?";
    private final String getByIdMemberQuery = "select * from t_penjualan where id_member=?";
    private final String getAllQuery = "select * from t_penjualan";

    public void setConnection(Connection c) throws SQLException {
        connection = c;
        insertStatement = connection.prepareStatement(insertQuery);
        updateStatement = connection.prepareStatement(updateQuery);
        deleteStatement = connection.prepareStatement(deleteQuery);
        getByIdStatement = connection.prepareStatement(getByIdQuery);
        getAllStatement = connection.prepareStatement(getAllQuery);
        getByIdMemberStatement = connection.prepareStatement(getByIdMemberQuery);
    }

    public void save(Penjualan penjualan) throws SQLException {
        insertStatement.setString(1, penjualan.getIdPenjualan());
        insertStatement.setTimestamp(2, new Timestamp(penjualan.getTanggal().getTime()));
        insertStatement.setDouble(3, penjualan.getTotal().doubleValue());
        insertStatement.setDouble(6, penjualan.getDiskon().doubleValue());
        insertStatement.setString(4, penjualan.getIdUser());
        insertStatement.setString(5, penjualan.getIdMember());
        insertStatement.executeUpdate();
    }

    public void update(Penjualan penjualan) throws SQLException {
        updateStatement.setTimestamp(1, new Timestamp(penjualan.getTanggal().getTime()));
        updateStatement.setBigDecimal(2, penjualan.getTotal());
        updateStatement.setBigDecimal(6, penjualan.getDiskon());
        updateStatement.setString(3, penjualan.getIdUser());
        updateStatement.setString(4, penjualan.getIdMember());
        updateStatement.setString(5, penjualan.getIdPenjualan());
        updateStatement.executeUpdate();
    }

    public void delete(Penjualan penjualan) throws SQLException {
        deleteStatement.setString(1, penjualan.getIdPenjualan());
        deleteStatement.executeUpdate();
    }
    
    public Penjualan getPenjualan(String idPenjualan) throws SQLException {
        getByIdStatement.setString(1, idPenjualan);
        ResultSet rs = getByIdStatement.executeQuery();
        if(rs.next()) {
            Penjualan penjualan = new Penjualan();
            penjualan.setIdPenjualan(rs.getString("id_penjualan"));
            penjualan.setTanggal(new Date(rs.getTimestamp("tanggal").getTime()));
            penjualan.setTotal(rs.getBigDecimal("total"));
            penjualan.setTotal(rs.getBigDecimal("diskon"));
            penjualan.setIdUser(rs.getString("id_user"));
            penjualan.setIdMember(rs.getString("id_member"));
            return penjualan;
        }
        return null;
    }
    
    public List<Penjualan> getPenjualan() throws SQLException {
        ResultSet rs = getAllStatement.executeQuery();
        List<Penjualan> list = new ArrayList<Penjualan>();
        while(rs.next()) {
            Penjualan penjualan = new Penjualan();
            penjualan.setIdPenjualan(rs.getString("id_penjualan"));
            penjualan.setTanggal(new Date(rs.getTimestamp("tanggal").getTime()));
            penjualan.setTotal(rs.getBigDecimal("total"));
            penjualan.setTotal(rs.getBigDecimal("diskon"));
            penjualan.setIdUser(rs.getString("id_user"));
            penjualan.setIdMember(rs.getString("id_member"));
            list.add(penjualan);
        }
        return list;
    }
    public List<Penjualan> getPenjualanMember() throws SQLException {
        ResultSet rs = getByIdMemberStatement.executeQuery();
        List<Penjualan> list = new ArrayList<Penjualan>();
        while(rs.next()) {
            Penjualan penjualan = new Penjualan();
            penjualan.setIdPenjualan(rs.getString("id_penjualan"));
            penjualan.setTanggal(new Date(rs.getTimestamp("tanggal").getTime()));
            penjualan.setTotal(rs.getBigDecimal("total"));
            penjualan.setTotal(rs.getBigDecimal("diskon"));
            penjualan.setIdUser(rs.getString("id_user"));
            list.add(penjualan);
        }
        return list;
    }
}
