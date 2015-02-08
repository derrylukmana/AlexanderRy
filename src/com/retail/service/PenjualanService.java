/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.retail.service;

import com.retail.dao.PenjualanDao;
import com.retail.model.Penjualan;
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
public class PenjualanService {

    private PenjualanDao penjualanDao;
    private Connection connection;

    public void setDataSource(DataSource dataSource) {
        try {
            connection = dataSource.getConnection();
            penjualanDao = new PenjualanDao();
            penjualanDao.setConnection(connection);
        } catch (SQLException ex) {
            Logger.getLogger(PenjualanService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void save(Penjualan penjualan) {
        try {
            connection.setAutoCommit(false);
            penjualanDao.save(penjualan);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(PenjualanService.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }

    public void update(Penjualan penjualan) {
        try {
            connection.setAutoCommit(false);
            penjualanDao.update(penjualan);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(PenjualanService.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }

    public void delete(Penjualan penjualan) {
        try {
            connection.setAutoCommit(false);
            penjualanDao.delete(penjualan);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(PenjualanService.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }

    public Penjualan getPenjualan(String idPenjualan) {
        try {
            return penjualanDao.getPenjualan(idPenjualan);
        } catch (SQLException ex) {
            Logger.getLogger(PenjualanService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<Penjualan> getPenjualan() {
        List<Penjualan> list = new ArrayList<Penjualan>();
        try {
            list = penjualanDao.getPenjualan();
        } catch (SQLException ex) {
            Logger.getLogger(PenjualanService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}
