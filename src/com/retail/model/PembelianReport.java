/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.retail.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Hauw
 */
public class PembelianReport {
    private String namaSupplier;
    private Date tanggal;
    private String namaProduk;
    private Integer kuantitas;
    private BigDecimal subTotal;

    public Integer getKuantitas() {
        return kuantitas;
    }

    public void setKuantitas(Integer kuantitas) {
        this.kuantitas = kuantitas;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public void setNamaProduk(String namaProduk) {
        this.namaProduk = namaProduk;
    }

    public String getNamaSupplier() {
        return namaSupplier;
    }

    public void setNamaSupplier(String namaSupplier) {
        this.namaSupplier = namaSupplier;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }
    
}
