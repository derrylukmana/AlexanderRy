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
public class PembelianDetail {
    private String idDetailPembelian;
    private Produk produk;
    private Integer kuantitas;
    private BigDecimal harga;
    private BigDecimal subTotal;
    private Pembelian pembelian;

    public BigDecimal getHarga() {
        return harga;
    }

    public void setHarga(BigDecimal harga) {
        this.harga = harga;
    }

    public String getIdDetailPembelian() {
        return idDetailPembelian;
    }

    public void setIdDetailPembelian(String idDetailPembelian) {
        this.idDetailPembelian = idDetailPembelian;
    }

    public Integer getKuantitas() {
        return kuantitas;
    }

    public void setKuantitas(Integer kuantitas) {
        this.kuantitas = kuantitas;
    }

    public Pembelian getPembelian() {
        return pembelian;
    }

    public void setPembelian(Pembelian pembelian) {
        this.pembelian = pembelian;
    }

    public Produk getProduk() {
        return produk;
    }

    public void setProduk(Produk produk) {
        this.produk = produk;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }
    
}
