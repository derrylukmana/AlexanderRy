/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.retail.service;

import com.retail.dao.PenjualanDao;
import com.retail.dao.PenjualanDetailDao;
import com.retail.dao.ProdukDao;
import com.retail.model.Penjualan;
import com.retail.model.PenjualanDetail;
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
public class TransaksiPenjualanService {
    private Connection connection;
    private PenjualanDao penjualanDao;
    private PenjualanDetailDao penjualanDetailDao;
    private ProdukDao produkDao;
    
    public void setDataSource(DataSource dataSource) {
        try {
            connection = dataSource.getConnection();
            penjualanDao = new PenjualanDao();
            penjualanDao.setConnection(connection);
            penjualanDetailDao = new PenjualanDetailDao();
            penjualanDetailDao.setConnection(connection);
            produkDao = new ProdukDao();
            produkDao.setConnection(connection);
        } catch (SQLException ex) {
            Logger.getLogger(TransaksiPenjualanService.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    public void save(Penjualan penjualan, List<PenjualanDetail> penjualanDetails) {
        try {
            connection.setAutoCommit(false);
            penjualanDao.save(penjualan);
            for (PenjualanDetail penjualanDetail : penjualanDetails) {
                penjualanDetailDao.save(penjualanDetail);
                Produk produk = produkDao.getProduk(penjualanDetail.getProduk().getIdProduk());
                produk.setStockTotal(produk.getStockTotal()- penjualanDetail.getKuantitas());
                produkDao.update(produk);
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException ex) {
            try {                
                connection.rollback();
                JOptionPane.showMessageDialog(null, new Object[] {"Transaksi gagal dilakukan!!!", ex.getMessage()}, "Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex1) {
                Logger.getLogger(TransaksiPenjualanService.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }        
    }
}
