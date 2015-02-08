/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.retail.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Karin
 */
public class BillReport {
    private String idPenjualan;
    private Date tanggal;
    
    private BigDecimal total;
    //private BigDecimal diskon;
    private String idMember;
    private String namaLengkap;
    //private String idDetailPenjualan;
    private String idProduk;
    private String namaProduk;
    private Integer kuantitas;
    private BigDecimal harga;
    private BigDecimal subTotal;

    public String getIdPenjualan() {
        return idPenjualan;
    }

    public void setIdPenjualan(String idPenjualan) {
        this.idPenjualan = idPenjualan;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

//    public BigDecimal getJumlah() {
//        return jumlah;
//    }
//
//    public void setJumlah(BigDecimal jumlah) {
//        this.jumlah = jumlah;
//    }

//    public BigDecimal getDiskon() {
//        return diskon;
//    }
//
//    public void setDiskon(BigDecimal diskon) {
//        this.diskon = diskon;
//    }
//
//    public String getIdUser() {
//        return idUser;
//    }
//
//    public void setIdUser(String idUser) {
//        this.idUser = idUser;
//    }

    public String getIdMember() {
        return idMember;
    }

    public void setIdMember(String idMember) {
        this.idMember = idMember;
    }

//    public String getIdDetailPenjualan() {
//        return idDetailPenjualan;
//    }
//
//    public void setIdDetailPenjualan(String idDetailPenjualan) {
//        this.idDetailPenjualan = idDetailPenjualan;
//    }

    public String getIdProduk() {
        return idProduk;
    }

    public void setIdProduk(String idProduk) {
        this.idProduk = idProduk;
    }

    

    public Integer getKuantitas() {
        return kuantitas;
    }

    public void setKuantitas(Integer kuantitas) {
        this.kuantitas = kuantitas;
    }

    public BigDecimal getHarga() {
        return harga;
    }

    public void setHarga(BigDecimal harga) {
        this.harga = harga;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public void setNamaProduk(String namaProduk) {
        this.namaProduk = namaProduk;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }
    
    
}
