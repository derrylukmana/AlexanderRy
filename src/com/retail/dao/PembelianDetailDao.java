/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.retail.dao;

import com.retail.main.Main;
import com.retail.model.PembelianDetail;
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
public class PembelianDetailDao {

    private Connection connection;
    private PreparedStatement insertStatement;
    private PreparedStatement updateStatement;
    private PreparedStatement deleteStatement;
    private PreparedStatement getAllStatement;
    private PreparedStatement getByIdStatement;
    private final String insertQuery = "insert into t_detail_pembelian values(?, ?, ?, ?, ?, ?)";
    private final String updateQuery = "update t_pembelian set id_produk=?, kuantitas=?, harga=?, "
            + "sub_total=?, id_pembelian=? where id_detail_pembelian=?";
    private final String deleteQuery = "delete from t_pembelian where id_detail_pembelian=?";
    private final String getAllQuery = "select * from t_pembelian";
    private final String getByIdQuery = "select * from t_pembelian where id_detail_pembelian=?";

    public void setConnection(Connection c) throws SQLException {
        connection = c;
        insertStatement = connection.prepareStatement(insertQuery);
        updateStatement = connection.prepareStatement(updateQuery);
        deleteStatement = connection.prepareStatement(deleteQuery);
        getAllStatement = connection.prepareStatement(getAllQuery);
        getByIdStatement = connection.prepareStatement(getByIdQuery);
    }

    public void save(PembelianDetail pembelianDetail) throws SQLException {
        insertStatement.setString(1, pembelianDetail.getIdDetailPembelian());
        insertStatement.setString(2, pembelianDetail.getProduk().getIdProduk());
        insertStatement.setInt(3, pembelianDetail.getKuantitas());
        insertStatement.setBigDecimal(4, pembelianDetail.getHarga());
        insertStatement.setBigDecimal(5, pembelianDetail.getSubTotal());
        insertStatement.setString(6, pembelianDetail.getPembelian().getIdPembelian());
        insertStatement.executeUpdate();
    }

    public void update(PembelianDetail pembelianDetail) throws SQLException {        
        updateStatement.setString(1, pembelianDetail.getProduk().getIdProduk());
        updateStatement.setInt(2, pembelianDetail.getKuantitas());
        updateStatement.setBigDecimal(3, pembelianDetail.getHarga());
        updateStatement.setBigDecimal(4, pembelianDetail.getSubTotal());
        updateStatement.setString(5, pembelianDetail.getPembelian().getIdPembelian());
        updateStatement.setString(6, pembelianDetail.getIdDetailPembelian());
        updateStatement.executeUpdate();
    }

    public void delete(PembelianDetail pembelianDetail) throws SQLException {
        deleteStatement.setString(1, pembelianDetail.getIdDetailPembelian());
        deleteStatement.executeUpdate();
    }

    public PembelianDetail getPembelianDetail(String idPembelianDetail) throws SQLException {
        getByIdStatement.setString(1, idPembelianDetail);
        ResultSet rs = getByIdStatement.executeQuery();
        if (rs.next()) {
            PembelianDetail pembelianDetail = new PembelianDetail();
            pembelianDetail.setIdDetailPembelian(rs.getString("id_detail_pembelian"));
            pembelianDetail.setProduk(Main.getProdukService().getProduk(rs.getString("id_produk")));
            pembelianDetail.setKuantitas(rs.getInt("kuantitas"));
            pembelianDetail.setHarga(rs.getBigDecimal("harga"));
            pembelianDetail.setSubTotal(rs.getBigDecimal("sub_total"));
            pembelianDetail.setPembelian(Main.getPembelianService().getPembelian(rs.getString("id_pembelian")));
            return pembelianDetail;
        }
        return null;
    }

    public List<PembelianDetail> getPembelianDetail() throws SQLException {
        List<PembelianDetail> list = new ArrayList<PembelianDetail>();
        ResultSet rs = getAllStatement.executeQuery();
        while (rs.next()) {
            PembelianDetail pembelianDetail = new PembelianDetail();
            pembelianDetail.setIdDetailPembelian(rs.getString("id_detail_pembelian"));
            pembelianDetail.setProduk(Main.getProdukService().getProduk(rs.getString("id_produk")));
            pembelianDetail.setKuantitas(rs.getInt("kuantitas"));
            pembelianDetail.setHarga(rs.getBigDecimal("harga"));
            pembelianDetail.setSubTotal(rs.getBigDecimal("sub_total"));
            pembelianDetail.setPembelian(Main.getPembelianService().getPembelian(rs.getString("id_pembelian")));
            list.add(pembelianDetail);
        }
        return list;
    }
}
