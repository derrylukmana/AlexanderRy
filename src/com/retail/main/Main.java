/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.retail.main;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.retail.service.MemberService;
import com.retail.service.PembelianService;
import com.retail.service.PenjualanDetailService;
import com.retail.service.PenjualanService;
import com.retail.service.ProdukService;
import com.retail.service.ReportService;
import com.retail.service.SupplierService;
import com.retail.service.TransaksiPembelianService;
import com.retail.service.TransaksiPenjualanService;
import com.retail.service.UserService;
import com.retail.ui.main.FrameUtama;
import javax.swing.SwingUtilities;

/**
 *
 * @author Hauw
 */
public class Main {
    private static FrameUtama frameUtama;
    private static ProdukService produkService;
    private static PenjualanService penjualanService;
    private static PenjualanDetailService penjualanDetailService;
    private static TransaksiPenjualanService transaksiPenjualanService;
    private static ReportService reportService;
    private static SupplierService supplierService;
    private static MemberService memberService;
    private static PembelianService pembelianService;
    private static TransaksiPembelianService transaksiPembelianService;
    private static UserService userService;

    public static UserService getUserService() {
        return userService;
    }
    
    public static TransaksiPembelianService getTransaksiPembelianService() {
        return transaksiPembelianService;
    }
    
    public static PembelianService getPembelianService() {
        return pembelianService;
    }
    
    public static SupplierService getSupplierService() {
        return supplierService;
    }
    
    public static MemberService getMemberService() {
        return memberService;
    }

    public static ReportService getReportService() {
        return reportService;
    }
    
    public static FrameUtama getFrameUtama() {
        return frameUtama;
    }

    public static TransaksiPenjualanService getTransaksiPenjualanService() {
        return transaksiPenjualanService;
    }
    
    public static ProdukService getProdukService() {
        return produkService;
    }

    public static PenjualanDetailService getPenjualanDetailService() {
        return penjualanDetailService;
    }

    public static PenjualanService getPenjualanService() {
        return penjualanService;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                MysqlDataSource dataSource = new MysqlDataSource();
                dataSource.setDatabaseName("alexanderry");
                dataSource.setServerName("localhost");
                dataSource.setPort(3306);
                dataSource.setUser("root");
                dataSource.setPassword("");
                
                produkService = new ProdukService();
                produkService.setDataSource(dataSource);
                
                penjualanService = new PenjualanService();
                penjualanService.setDataSource(dataSource);
                
                penjualanDetailService = new PenjualanDetailService();
                penjualanDetailService.setDataSource(dataSource);
                
                transaksiPenjualanService = new TransaksiPenjualanService();
                transaksiPenjualanService.setDataSource(dataSource);
                
                reportService = new ReportService();
                reportService.setDataSource(dataSource);
                
                supplierService = new SupplierService();
                supplierService.setDataSource(dataSource);

                memberService = new MemberService();
                memberService.setDataSource(dataSource);
                
                pembelianService = new PembelianService();
                pembelianService.setDataSource(dataSource);
                
                transaksiPembelianService = new TransaksiPembelianService();
                transaksiPembelianService.setDataSource(dataSource);
    
                userService = new UserService();
                userService.setDataSource(dataSource);
                
                frameUtama = new FrameUtama();
                frameUtama.setVisible(true);
                frameUtama.showLoginPanel();                
            }
        });
    }
}
