/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.retail.service;

import com.retail.dao.PembelianDao;
import com.retail.model.Pembelian;
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
public class PembelianService {
    private Connection connection;
    private PembelianDao pembelianDao;
    
    public void setDataSource(DataSource dataSource) {
        try {
            connection = dataSource.getConnection();
            pembelianDao = new PembelianDao();
            pembelianDao.setConnection(connection);
        } catch (SQLException ex) {
            Logger.getLogger(PembelianService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void save(Pembelian pembelian) {
        try {
            connection.setAutoCommit(false);
            pembelianDao.save(pembelian);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(PembelianService.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }        
    }
    
    public void update(Pembelian pembelian) {
        try {
            connection.setAutoCommit(false);
            pembelianDao.update(pembelian);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(PembelianService.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }        
    }
    
    public void delete(Pembelian pembelian) {
        try {
            connection.setAutoCommit(false);
            pembelianDao.delete(pembelian);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(PembelianService.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }
    
    public Pembelian getPembelian(String idPembelian) {
        try {
            return pembelianDao.getPembelian(idPembelian);
        } catch (SQLException ex) {
            Logger.getLogger(PembelianService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public List<Pembelian> getPembelian() {
        List<Pembelian> list = new ArrayList<Pembelian>();
        try {
            list = pembelianDao.getPembelian();
        } catch (SQLException ex) {
            Logger.getLogger(PembelianService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}
