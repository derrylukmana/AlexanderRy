/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.retail.dao;

import com.retail.model.KasirReport;
import com.retail.model.PembelianReport;
import com.retail.model.PenjualanReport;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Hauw
 */
public class ReportDao {
    private Connection connection;
    private PreparedStatement getReportPenjualanStatement;
    private PreparedStatement getReportPembelianStatement;
    private PreparedStatement getReportKasirStatement;
    
    public void setConnection(Connection c) throws SQLException {
        this.connection = c;
        getReportPenjualanStatement = connection.prepareStatement("select t_produk.nama as nama_produk, "
                + "sum(t_detail_penjualan.kuantitas) as kuantitas, sum(t_detail_penjualan.sub_total)"
                + "as sub_total from t_produk, t_detail_penjualan , t_penjualan where t_produk.id_produk = "
                + "t_detail_penjualan.id_produk and t_penjualan.id_penjualan=t_detail_penjualan.id_penjualan "
                + "and t_penjualan.tanggal between ? and ? group by nama_produk");
        
        getReportPembelianStatement = connection.prepareStatement("select t_pembelian.tanggal, t_supplier.nama_supplier, "
                + "t_produk.nama as nama_produk, t_detail_pembelian.kuantitas, t_detail_pembelian.sub_total "
                + "from t_supplier, t_produk, t_detail_pembelian, t_pembelian "
                + "where t_produk.id_produk = t_detail_pembelian.id_produk "
                + "and t_detail_pembelian.id_pembelian = t_pembelian.id_pembelian "
                + "and t_pembelian.id_supplier = t_supplier.id_supplier "
                + "and t_pembelian.tanggal between ? and ? order by nama_supplier");
        
        getReportKasirStatement = connection.prepareStatement("select id_user, nama, alamat, "
                + "telepon from t_user where role='kasir'");
    }
    
    public List<PenjualanReport> getPenjualanReport(Date tanggalAwal, Date tanggalAkhir) throws SQLException {
        List<PenjualanReport> list = new ArrayList<PenjualanReport>();
        String pattern = "yyyy-MM-dd hh:mm:ss";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        getReportPenjualanStatement.setString(1, format.format(tanggalAwal.getTime()));
        getReportPenjualanStatement.setString(2, format.format(tanggalAkhir.getTime()));
        ResultSet rs = getReportPenjualanStatement.executeQuery();
        while(rs.next()) {
            PenjualanReport penjualanReport = new PenjualanReport();
            penjualanReport.setNamaProduk(rs.getString("nama_produk"));
            penjualanReport.setKuantitas(rs.getInt("kuantitas"));
            penjualanReport.setSubTotal(rs.getBigDecimal("sub_total"));
            list.add(penjualanReport);
        }
        return list;
    }
    
    public List<PembelianReport> getPembelianReport(Date tanggalAwal, Date tanggalAkhir) throws SQLException {
        List<PembelianReport> list = new ArrayList<PembelianReport>();
        String pattern = "yyyy-MM-dd hh:mm:ss";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        
        getReportPembelianStatement.setString(1, format.format(tanggalAwal.getTime()));
        getReportPembelianStatement.setString(2, format.format(tanggalAkhir.getTime()));
        ResultSet rs = getReportPembelianStatement.executeQuery();
        while(rs.next()) {
            PembelianReport pembelianReport = new PembelianReport();
            pembelianReport.setNamaProduk(rs.getString("nama_produk"));
            pembelianReport.setNamaSupplier(rs.getString("nama_supplier"));
            pembelianReport.setKuantitas(rs.getInt("kuantitas"));
            pembelianReport.setSubTotal(rs.getBigDecimal("sub_total"));
            pembelianReport.setTanggal(rs.getDate("tanggal"));
            list.add(pembelianReport);
        }
        return list;
    }
    
    public List<KasirReport> getKasirReport() throws SQLException {
        List<KasirReport> list = new ArrayList<KasirReport>();
        ResultSet rs = getReportKasirStatement.executeQuery();
        while(rs.next()) {
            KasirReport kasirReport = new KasirReport();
            kasirReport.setIdKasir(rs.getString("id_user"));
            kasirReport.setNama(rs.getString("nama"));
            kasirReport.setAlamat(rs.getString("alamat"));
            kasirReport.setTelepon(rs.getString("telepon"));
            list.add(kasirReport);
        }
        return list;
    }
}
