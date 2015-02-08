/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.retail.service;

import com.retail.dao.SupplierDao;
import com.retail.model.Supplier;
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
public class SupplierService {
    private Connection connection;
    private SupplierDao supplierDao;
    
    public void setDataSource(DataSource dataSource) {
        try {
            connection = dataSource.getConnection();
            supplierDao = new SupplierDao();
            supplierDao.setConnection(connection);
        } catch (SQLException ex) {
            Logger.getLogger(SupplierService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void save(Supplier supplier) {
        try {
            connection.setAutoCommit(false);
            supplierDao.save(supplier);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(SupplierService.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }
    
    public void update(Supplier supplier) {
        try {
            connection.setAutoCommit(false);
            supplierDao.update(supplier);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(SupplierService.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }
    
    public void delete(Supplier supplier) {
        try {
            connection.setAutoCommit(false);
            supplierDao.delete(supplier);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(SupplierService.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }
    
    public Supplier getSupplier(String idSupplier) {
        try {
            return supplierDao.getSupplier(idSupplier);
        } catch (SQLException ex) {
            Logger.getLogger(SupplierService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public Supplier getSupplierByName(String namaSupplier) {
        try {
            return supplierDao.getSupplierByName(namaSupplier);
        } catch (SQLException ex) {
            Logger.getLogger(SupplierService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public List<Supplier> getSupplier() {
        List<Supplier> list = new ArrayList<Supplier>();
        try {
            list = supplierDao.getSupplier();
        } catch (SQLException ex) {
            Logger.getLogger(SupplierService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}
