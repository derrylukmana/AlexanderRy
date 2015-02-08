/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.retail.model;

import java.math.BigDecimal;

/**
 *
 * @author Hauw
 */
public class PenjualanDetail {
    private String idDetailPenjualan;
    private Produk produk;
    private Integer kuantitas;
    private BigDecimal harga;
    private BigDecimal subTotal;
    private String idPenjualan;
    private String size;

    public BigDecimal getHarga() {
        return harga;
    }

    public void setHarga(BigDecimal harga) {
        this.harga = harga;
    }

    public String getIdDetailPenjualan() {
        return idDetailPenjualan;
    }

    public void setIdDetailPenjualan(String idDetailPenjualan) {
        this.idDetailPenjualan = idDetailPenjualan;
    }

    public String getIdPenjualan() {
        return idPenjualan;
    }

    public void setIdPenjualan(String idPenjualan) {
        this.idPenjualan = idPenjualan;
    }

    public Produk getProduk() {
        return produk;
    }

    public void setProduk(Produk produk) {
        this.produk = produk;
    }

    public Integer getKuantitas() {
        return kuantitas;
    }

    public void setKuantitas(Integer kuantitas) {
        this.kuantitas = kuantitas;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
    
}
