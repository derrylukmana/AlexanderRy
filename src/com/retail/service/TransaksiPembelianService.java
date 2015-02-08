/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.retail.service;

import com.retail.dao.PembelianDao;
import com.retail.dao.PembelianDetailDao;
import com.retail.dao.ProdukDao;
import com.retail.model.Pembelian;
import com.retail.model.PembelianDetail;
import com.retail.model.Produk;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import javax.swing.JOptionPane;

/**
 *
 * @author Hauw
 */
public class TransaksiPembelianService {
    private Connection connection;
    private PembelianDao pembelianDao;
    private PembelianDetailDao pembelianDetailDao;
    private ProdukDao produkDao;
    
    public void setDataSource(DataSource dataSource) {
        try {
            connection = dataSource.getConnection();
            pembelianDao = new PembelianDao();
            pembelianDao.setConnection(connection);
            pembelianDetailDao = new PembelianDetailDao();
            pembelianDetailDao.setConnection(connection);
            produkDao = new ProdukDao();
            produkDao.setConnection(connection);
        } catch (SQLException ex) {
            Logger.getLogger(TransaksiPembelianService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void save(Pembelian pembelian, List<PembelianDetail> listPembelian) {
        try {
            connection.setAutoCommit(false);
            pembelianDao.save(pembelian);
            for (PembelianDetail pembelianDetail : listPembelian) {
                pembelianDetailDao.save(pembelianDetail);
                Produk produk = produkDao.getProduk(pembelianDetail.getProduk().getIdProduk());
                produk.setStockTotal(produk.getStockTotal()+ pembelianDetail.getKuantitas());
                produkDao.update(produk);
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException ex) {
            try {
                connection.rollback();
                JOptionPane.showMessageDialog(null, new Object[]{"Transaksi gagal dilakukan!", ex.getMessage()}, "Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex1) {
                Logger.getLogger(TransaksiPembelianService.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }
}
