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
public class PenjualanReport {
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

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }
    
}
