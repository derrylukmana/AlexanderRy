/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.retail.dao;

import com.retail.main.Main;
import com.retail.model.PenjualanDetail;
import java.math.BigDecimal;
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
public class PenjualanDetailDao {

    private Connection connection;
    private PreparedStatement insertStatement;
    private PreparedStatement updateStatement;
    private PreparedStatement deleteStatement;
    private PreparedStatement getByIdStatement;
    private PreparedStatement getAllStatement;
    private final String insertQuery = "insert into t_detail_penjualan values(?,?,?,?,?,?,?)";
    private final String updateQuery = "update t_detail_penjualan set id_produk=?, kuantitas=?,"
            + " harga=?, sub_total=?, id_penjualan=?, size=? where id_detail_penjualan=?";
    private final String deleteQuery = "delete from t_penjualan where id_detail_penjualan=?";
    private final String getByIdQuery = "select * from t_penjualan where id_detail_penjualan=?";
    private final String getAllQuery = "select * from t_detail_penjualan";

    public void setConnection(Connection c) throws SQLException {
        connection = c;
        insertStatement = connection.prepareStatement(insertQuery);
        updateStatement = connection.prepareStatement(updateQuery);
        deleteStatement = connection.prepareStatement(deleteQuery);
        getByIdStatement = connection.prepareStatement(getByIdQuery);
        getAllStatement = connection.prepareStatement(getAllQuery);
    }

    public void save(PenjualanDetail penjualanDetail) throws SQLException {
        insertStatement.setString(1, penjualanDetail.getIdDetailPenjualan());
        insertStatement.setString(2, penjualanDetail.getProduk().getIdProduk());
        insertStatement.setInt(3, penjualanDetail.getKuantitas());
        insertStatement.setBigDecimal(4, penjualanDetail.getHarga());
        insertStatement.setBigDecimal(5, penjualanDetail.getSubTotal());
        insertStatement.setString(6, penjualanDetail.getIdPenjualan());
        insertStatement.setString(7, penjualanDetail.getSize());
        insertStatement.executeUpdate();
        System.out.println(insertQuery);
    }

    public void update(PenjualanDetail penjualanDetail) throws SQLException {
        updateStatement.setString(1, penjualanDetail.getProduk().getIdProduk());
        updateStatement.setInt(2, penjualanDetail.getKuantitas());
        updateStatement.setBigDecimal(3, penjualanDetail.getHarga());
        updateStatement.setBigDecimal(4, penjualanDetail.getSubTotal());
        updateStatement.setString(5, penjualanDetail.getIdPenjualan());
        updateStatement.setString(7, penjualanDetail.getIdDetailPenjualan());
        updateStatement.setString(6, penjualanDetail.getSize());
        updateStatement.executeUpdate();
    }

    public void delete(PenjualanDetail penjualanDetail) throws SQLException {
        deleteStatement.setString(1, penjualanDetail.getIdDetailPenjualan());
        deleteStatement.executeUpdate();
    }

    public PenjualanDetail getPenjualanDetail(String idDetailPenjualan) throws SQLException {
        getByIdStatement.setString(1, idDetailPenjualan);
        ResultSet rs = getByIdStatement.executeQuery();
        if (rs.next()) {
            PenjualanDetail penjualanDetail = new PenjualanDetail();
            penjualanDetail.setIdDetailPenjualan(rs.getString("id_detail_penjualan"));
            penjualanDetail.setProduk(Main.getProdukService().getProduk(rs.getString("id_produk")));
            penjualanDetail.setKuantitas(rs.getInt("kuantitas"));
            penjualanDetail.setHarga(rs.getBigDecimal("harga"));
            penjualanDetail.setSubTotal(rs.getBigDecimal("sub_total"));
            penjualanDetail.setIdPenjualan(rs.getString("id_penjualan"));
            penjualanDetail.setSize(rs.getString("size"));
        }
        return null;
    }

    public List<PenjualanDetail> getPenjualanDetail() throws SQLException {
        ResultSet rs = getAllStatement.executeQuery();
        List<PenjualanDetail> list = new ArrayList<PenjualanDetail>();
        while (rs.next()) {
            PenjualanDetail penjualanDetail = new PenjualanDetail();
            penjualanDetail.setIdDetailPenjualan(rs.getString("id_detail_penjualan"));
            penjualanDetail.setProduk(Main.getProdukService().getProduk(rs.getString("id_produk")));
            penjualanDetail.setKuantitas(rs.getInt("kuantitas"));
            penjualanDetail.setHarga(rs.getBigDecimal("harga"));
            penjualanDetail.setSubTotal(rs.getBigDecimal("sub_total"));
            penjualanDetail.setIdPenjualan(rs.getString("id_penjualan"));
            penjualanDetail.setSize(rs.getString("size"));
            list.add(penjualanDetail);
        }
        return list;
    }
}
