package com.retail.ui.main;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.*;
/**
 *
 * @author Karina
 */
public class KoneksiDb {
public Connection conn;
public Statement st;
public ResultSet rs;
// Fungsi koneksi
public void koneksi()
{
// memangil fungsi konek() untuk melakukan koneksi
konek("localhost","alexanderry","root","");
}
// Program untuk melakukan koneksi
public void konek(String server,String db,String user,String passwd){
    System.out.println("Keterangan");

    //memanggil driver
    try {
        Class.forName("com.mysql.jdbc.Driver");
    } catch (ClassNotFoundException e) {
        System.out.println(
        "Ada kesalahan Driver JDBC tidak berhasil Load");
        return;
    }
        System.out.println("Mysql JDBC Driver berhasil di Load");
        conn = null;
    
    try {
        conn = DriverManager.getConnection("jdbc:mysql://"+server+":3306/"+db,user,passwd);
    } catch (SQLException e) {
        System.out.println("Tidak bisa koneksi ke database");
        return;
        }
        if (conn != null)
        System.out.println("Berhasil Koneksi!");
        else
        System.out.println("Koneksi Gagal........ !");
    }
}