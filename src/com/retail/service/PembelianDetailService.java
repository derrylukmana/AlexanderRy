/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.retail.service;

import com.retail.dao.PembelianDetailDao;
import com.retail.model.PembelianDetail;
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
public class PembelianDetailService {
    private Connection connection;
    private PembelianDetailDao pembelianDetailDao;
    
    public void setDataSource(DataSource dataSource) {
        try {
            connection = dataSource.getConnection();
            pembelianDetailDao = new PembelianDetailDao();
            pembelianDetailDao.setConnection(connection);
        } catch (SQLException ex) {
            Logger.getLogger(PembelianService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void save(PembelianDetail pembelianDetail) {
        try {
            connection.setAutoCommit(false);
            pembelianDetailDao.save(pembelianDetail);
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
    
    public void update(PembelianDetail pembelianDetail) {
        try {
            connection.setAutoCommit(false);
            pembelianDetailDao.update(pembelianDetail);
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
    
    public void delete(PembelianDetail pembelianDetail) {
        try {
            connection.setAutoCommit(false);
            pembelianDetailDao.delete(pembelianDetail);
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
    
    public PembelianDetail getPembelian(String idPembelianDetail) {
        try {
            return pembelianDetailDao.getPembelianDetail(idPembelianDetail);
        } catch (SQLException ex) {
            Logger.getLogger(PembelianService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public List<PembelianDetail> getPembelian() {
        List<PembelianDetail> list = new ArrayList<PembelianDetail>();
        try {
            list = pembelianDetailDao.getPembelianDetail();
        } catch (SQLException ex) {
            Logger.getLogger(PembelianService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }    
}
