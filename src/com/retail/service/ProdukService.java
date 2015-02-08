/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.retail.service;

import com.retail.dao.ProdukDao;
import com.retail.model.Produk;
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
public class ProdukService {
    private Connection connection;
    private ProdukDao produkDao;
    
    public void setDataSource(DataSource dataSource) {
        try {
            connection = dataSource.getConnection();
            produkDao = new ProdukDao();
            produkDao.setConnection(connection);
        } catch (SQLException ex) {
            Logger.getLogger(ProdukService.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    public void save(Produk produk) {
        try {
            connection.setAutoCommit(false);
            produkDao.save(produk);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(ProdukService.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }        
    }
    
    public void update(Produk produk) {
        try {
            connection.setAutoCommit(false);
            produkDao.update(produk);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(ProdukService.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }
    
    public void delete(Produk produk) {
        try {
            connection.setAutoCommit(false);
            produkDao.delete(produk);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(ProdukService.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }        
    }
    
    public Produk getProduk(String idProduk) {
        try {
            return produkDao.getProduk(idProduk);
        } catch (SQLException ex) {
            Logger.getLogger(ProdukService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public List<Produk> getProduk() {
        List<Produk> list = new ArrayList<Produk>();
        try {
            list = produkDao.getProduk();
        } catch (SQLException ex) {
            Logger.getLogger(ProdukService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public Integer getStockSize(String idProduk, String size) {
        try {
            Produk produk = produkDao.getProduk(idProduk);
            Integer stock = 0;
            if(size.equals("S")){
                return produk.getStockS();
            } else if(size.equals("M")){
                return produk.getStockM();
            } else if(size.equals("L")){
                return produk.getStockL();
            } else if(size.equals("XL")){
                return produk.getStockXL();
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ProdukService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
}
