/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.retail.service;

import com.retail.main.Main;
import com.retail.model.PenjualanReport;
import com.retail.model.Produk;
import com.retail.dao.ReportDao;
import com.retail.model.KasirReport;
import com.retail.model.PembelianReport;
import com.retail.model.Supplier;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Hauw
 */
public class ReportService {

    private ReportDao reportDao;
    private Connection connection;

    public void setDataSource(DataSource dataSource) {
        try {
            connection = dataSource.getConnection();
            reportDao = new ReportDao();
            reportDao.setConnection(connection);
        } catch (SQLException ex) {
            Logger.getLogger(ReportService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public JasperPrint getReportProduk() {
        try {
            List<Produk> list = Main.getProdukService().getProduk();
            JRDataSource dataSource = new JRBeanCollectionDataSource(list);
            Map<Object, Object> parameters = new HashMap<Object, Object>();
            parameters.put(JRParameter.REPORT_DATA_SOURCE, dataSource);
            JasperPrint print = JasperFillManager.fillReport(getClass().getResourceAsStream("/reports/ProdukReport.jasper"), parameters);
            return print;
        } catch (JRException ex) {
            Logger.getLogger(ReportService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public JasperPrint getReportSupplier() {
        try {
            List<Supplier> list = Main.getSupplierService().getSupplier();
            JRDataSource dataSource = new JRBeanCollectionDataSource(list);
            Map<Object, Object> parameters = new HashMap<Object, Object>();
            parameters.put(JRParameter.REPORT_DATA_SOURCE, dataSource);
            JasperPrint print = JasperFillManager.fillReport(getClass().getResourceAsStream("/reports/SupplierReport.jasper"), parameters);
            return print;
        } catch (JRException ex) {
            Logger.getLogger(ReportService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public JasperPrint getReportPenjualan(Date tanggalMulai, Date tanggalSelesai) {
        try {
            List<PenjualanReport> list = reportDao.getPenjualanReport(tanggalMulai, tanggalSelesai);
            JRDataSource dataSource = new JRBeanCollectionDataSource(list);
            Map<Object, Object> parameters = new HashMap<Object, Object>();
            parameters.put(JRParameter.REPORT_DATA_SOURCE, dataSource);
            parameters.put("tanggalMulai", tanggalMulai);
            parameters.put("tanggalSelesai", tanggalSelesai);
            JasperPrint print = JasperFillManager.fillReport(getClass().getResourceAsStream("/reports/PenjualanReport.jasper"), parameters);
            return print;
        } catch (JRException ex) {
            Logger.getLogger(ReportService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ReportService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public JasperPrint getReportPembelian(Date tanggalMulai, Date tanggalSelesai) {
        try {
            List<PembelianReport> list = reportDao.getPembelianReport(tanggalMulai, tanggalSelesai);
            JRDataSource dataSource = new JRBeanCollectionDataSource(list);
            Map<Object, Object> parameters = new HashMap<Object, Object>();
            parameters.put(JRParameter.REPORT_DATA_SOURCE, dataSource);
            parameters.put("tanggalMulai", tanggalMulai);
            parameters.put("tanggalSelesai", tanggalSelesai);
            JasperPrint print = JasperFillManager.fillReport(getClass().getResourceAsStream("/reports/PembelianReport.jasper"), parameters);
            return print;
        } catch (JRException ex) {
            Logger.getLogger(ReportService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ReportService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public JasperPrint getReportKasir() {
        try {
            List<KasirReport> list = reportDao.getKasirReport();
            JRDataSource dataSource = new JRBeanCollectionDataSource(list);
            Map<Object, Object> parameters = new HashMap<Object, Object>();
            parameters.put(JRParameter.REPORT_DATA_SOURCE, dataSource);
            JasperPrint print = JasperFillManager.fillReport(getClass().getResourceAsStream("/reports/KasirReport.jasper"), parameters);
            return print;
        } catch (JRException ex) {
            Logger.getLogger(ReportService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ReportService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public JasperPrint getNotaPenjualanReport(String idPenjualan) {
        try {
            Map<Object, Object> parameters = new HashMap<Object, Object>();
            parameters.put("id_penjualan", idPenjualan);
            JasperPrint print = JasperFillManager.fillReport(getClass().getResourceAsStream("/reports/NotaPenjualanReport.jasper"), parameters, connection);
            return print;
        } catch (JRException ex) {
            Logger.getLogger(ReportService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public JasperPrint getNotaPembelianReport(String idPembelian) {
        try {
            Map<Object, Object> parameters = new HashMap<Object, Object>();
            parameters.put("id_pembelian", idPembelian);
            JasperPrint print = JasperFillManager.fillReport(getClass().getResourceAsStream("/reports/NotaPembelianReport.jasper"), parameters, connection);
            return print;
        } catch (JRException ex) {
            Logger.getLogger(ReportService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
