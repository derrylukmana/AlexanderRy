/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.retail.model;

/**
 *
 * @author Hauw
 */
public class KasirReport {
    private String idKasir;
    private String nama;
    private String alamat;
    private String telepon;

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getIdKasir() {
        return idKasir;
    }

    public void setIdKasir(String idKasir) {
        this.idKasir = idKasir;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }
    
}
