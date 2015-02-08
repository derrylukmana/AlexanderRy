/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.retail.service;

import com.retail.dao.PenjualanDetailDao;
import com.retail.model.PenjualanDetail;
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
public class PenjualanDetailService {

    private Connection connection;
    private PenjualanDetailDao penjualanDetailDao;

    public void setDataSource(DataSource dataSource) {
        try {
            connection = dataSource.getConnection();
            penjualanDetailDao = new PenjualanDetailDao();
            penjualanDetailDao.setConnection(connection);
        } catch (SQLException ex) {
            Logger.getLogger(PenjualanService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void save(PenjualanDetail penjualanDetail) {
        try {
            connection.setAutoCommit(false);
            penjualanDetailDao.save(penjualanDetail);
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

    public void update(PenjualanDetail penjualanDetail) {
        try {
            connection.setAutoCommit(false);
            penjualanDetailDao.update(penjualanDetail);
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

    public void delete(PenjualanDetail penjualanDetail) {
        try {
            connection.setAutoCommit(false);
            penjualanDetailDao.delete(penjualanDetail);
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

    public PenjualanDetail getDetailPenjualan(String idDetailPenjualan) {
        try {
            return penjualanDetailDao.getPenjualanDetail(idDetailPenjualan);
        } catch (SQLException ex) {
            Logger.getLogger(PenjualanService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<PenjualanDetail> getDetailPenjualan() {
        List<PenjualanDetail> list = new ArrayList<PenjualanDetail>();
        try {
            list = penjualanDetailDao.getPenjualanDetail();
        } catch (SQLException ex) {
            Logger.getLogger(PenjualanService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}
