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
public class Produk {
    private String idProduk;
    private BigDecimal hargaJual;
    private BigDecimal hargaPokok;
    private String namaProduk;    
    private Integer stockTotal;
    private Integer stockS;
    private Integer stockM;
    private Integer stockL;
    private Integer stockXL;

    public Integer getStockTotal() {
        return stockTotal;
    }

    public void setStockTotal(Integer stockTotal) {
        this.stockTotal = stockTotal;
    }

    public Integer getStockS() {
        return stockS;
    }

    public void setStockS(Integer stockS) {
        this.stockS = stockS;
    }

    public Integer getStockM() {
        return stockM;
    }

    public void setStockM(Integer stockM) {
        this.stockM = stockM;
    }

    public Integer getStockL() {
        return stockL;
    }

    public void setStockL(Integer stockL) {
        this.stockL = stockL;
    }

    public Integer getStockXL() {
        return stockXL;
    }

    public void setStockXL(Integer stockXL) {
        this.stockXL = stockXL;
    }

    public BigDecimal getHargaJual() {
        return hargaJual;
    }

    public void setHargaJual(BigDecimal hargaJual) {
        this.hargaJual = hargaJual;
    }

    public BigDecimal getHargaPokok() {
        return hargaPokok;
    }

    public void setHargaPokok(BigDecimal hargaPokok) {
        this.hargaPokok = hargaPokok;
    }

    public String getIdProduk() {
        return idProduk;
    }

    public void setIdProduk(String idProduk) {
        this.idProduk = idProduk;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public void setNamaProduk(String namaProduk) {
        this.namaProduk = namaProduk;
    }


}
