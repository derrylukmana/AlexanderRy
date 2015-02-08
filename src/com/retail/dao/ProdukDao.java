/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.retail.dao;

import com.retail.model.Produk;
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
public class ProdukDao {

    private Connection connection;
    private PreparedStatement insertStatement;
    private PreparedStatement updateStatement;
    private PreparedStatement deleteStatement;
    private PreparedStatement getByIdStatement;
    private PreparedStatement getAllStatement;
    private final String insertQuery = "insert into t_produk values(?,?,?,?,?,?,?,?,?)";
    private final String updateQuery = "update t_produk set harga_jual=?, harga_pokok=?, nama=?, stock_total=?, stock_s=?, stock_m=?, stock_l=?, stock_xl=? where id_produk=?";
    private final String deleteQuery = "delete from t_produk where id_produk=?";
    private final String getByIdQuery = "select * from t_produk where id_produk=?";
    private final String getAllQuery = "select * from t_produk";

    public void setConnection(Connection c) throws SQLException {
        connection = c;
        insertStatement = connection.prepareStatement(insertQuery);
        updateStatement = connection.prepareStatement(updateQuery);
        deleteStatement = connection.prepareStatement(deleteQuery);
        getByIdStatement = connection.prepareStatement(getByIdQuery);
        getAllStatement = connection.prepareStatement(getAllQuery);
    }

    public void save(Produk produk) throws SQLException {
        insertStatement.setString(1, produk.getIdProduk());
        insertStatement.setBigDecimal(2, produk.getHargaJual());
        insertStatement.setBigDecimal(3, produk.getHargaPokok());
        insertStatement.setString(4, produk.getNamaProduk());
        insertStatement.setInt(5, produk.getStockTotal());
        insertStatement.setInt(6, produk.getStockS());
        insertStatement.setInt(7, produk.getStockM());
        insertStatement.setInt(8, produk.getStockL());
        insertStatement.setInt(9, produk.getStockXL());
        insertStatement.executeUpdate();
    }

    public void update(Produk produk) throws SQLException {
        updateStatement.setDouble(1, produk.getHargaJual().doubleValue());
        updateStatement.setDouble(2, produk.getHargaPokok().doubleValue());
        updateStatement.setString(3, produk.getNamaProduk());
        updateStatement.setInt(4, produk.getStockTotal());
        updateStatement.setInt(5, produk.getStockS());
        updateStatement.setInt(6, produk.getStockM());
        updateStatement.setInt(7, produk.getStockL());
        updateStatement.setInt(8, produk.getStockXL());
        updateStatement.setString(9, produk.getIdProduk());
        updateStatement.executeUpdate();
    }

    public void delete(Produk produk) throws SQLException {
        deleteStatement.setString(1, produk.getIdProduk());
        deleteStatement.executeUpdate();
    }

    public Produk getProduk(String idProduk) throws SQLException {
        getByIdStatement.setString(1, idProduk);
        ResultSet rs = getByIdStatement.executeQuery();
        if (rs.next()) {
            Produk produk = new Produk();
            produk.setIdProduk(rs.getString("id_produk"));
            produk.setHargaJual(rs.getBigDecimal("harga_jual"));
            produk.setHargaPokok(rs.getBigDecimal("harga_pokok"));
            produk.setNamaProduk(rs.getString("nama"));
            produk.setStockTotal(rs.getInt("stock_total"));
            produk.setStockS(rs.getInt("stock_s"));
            produk.setStockM(rs.getInt("stock_m"));
            produk.setStockL(rs.getInt("stock_l"));
            produk.setStockXL(rs.getInt("stock_xl"));
            return produk;
        }
        return null;
    }

    public List<Produk> getProduk() throws SQLException {
        List<Produk> list = new ArrayList<Produk>();
        ResultSet rs = getAllStatement.executeQuery();
        while (rs.next()) {
            Produk produk = new Produk();
            produk.setIdProduk(rs.getString("id_produk"));
            produk.setHargaJual(rs.getBigDecimal("harga_jual"));
            produk.setHargaPokok(rs.getBigDecimal("harga_pokok"));
            produk.setNamaProduk(rs.getString("nama"));
            produk.setStockTotal(rs.getInt("stock_total"));
            produk.setStockS(rs.getInt("stock_s"));
            produk.setStockM(rs.getInt("stock_m"));
            produk.setStockL(rs.getInt("stock_l"));
            produk.setStockXL(rs.getInt("stock_xl"));
            list.add(produk);
        }
        return list;
    }
}
