/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.retail.dao;

import com.retail.model.Member;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hauw
 */
public class MemberDao {

    private Connection connection;
    private PreparedStatement insertStatement;
    private PreparedStatement updateStatement;
    private PreparedStatement deleteStatement;
    private PreparedStatement getByIdStatement;
    private PreparedStatement getAllStatement;
    private PreparedStatement getByNameStatement;
    private final String insertQuery = "insert into t_member values(?,?,?,?,?,?,?,?,?,?,?,?)";
    private final String updateQuery = "update t_member set nama_lengkap=?, nama_panggilan=?, tgl_lahir=?, alamat=?, telepon=?, pekerjaan=?, perusahaan=?, jabatan=?, favorite=?, twit_pinbb=?, testimoni=?  where id_member=?";
    private final String deleteQuery = "delete from t_member where id_member=?";
    private final String getByIdQuery = "select * from t_member where id_member=?";
    private final String getAllQuery = "select * from t_member";
    private final String getByNameQuery = "select * from t_member where nama_lengkap=?";

    public void setConnection(Connection c) throws SQLException {
        connection = c;
        insertStatement = connection.prepareStatement(insertQuery);
        updateStatement = connection.prepareStatement(updateQuery);
        deleteStatement = connection.prepareStatement(deleteQuery);
        getByIdStatement = connection.prepareStatement(getByIdQuery);
        getAllStatement = connection.prepareStatement(getAllQuery);
        getByNameStatement = connection.prepareStatement(getByNameQuery);
    }

    public void save(Member member) throws SQLException {
        insertStatement.setString(1, member.getIdMember());
        insertStatement.setString(2, member.getNamaLengkap());
        insertStatement.setString(3, member.getNamaPanggilan());
        insertStatement.setTimestamp(4, new Timestamp(member.getTglLahir().getTime()));
        insertStatement.setString(5, member.getAlamat());
        insertStatement.setString(6, member.getTelepon());
        insertStatement.setString(7, member.getPekerjaan());
        insertStatement.setString(8, member.getPerusahaan());
        insertStatement.setString(9, member.getJabatan());
        insertStatement.setString(10, member.getFavorite());
        insertStatement.setString(11, member.getTwit_pinbb());
        insertStatement.setString(12, member.getTestimoni());
        insertStatement.executeUpdate();
    }

    public void update(Member member) throws SQLException {
        updateStatement.setString(1, member.getNamaLengkap());
        updateStatement.setString(2, member.getNamaPanggilan());
        updateStatement.setTimestamp(3, new Timestamp(member.getTglLahir().getTime()));
        updateStatement.setString(4, member.getAlamat());
        updateStatement.setString(5, member.getTelepon());
        updateStatement.setString(6, member.getPekerjaan());
        updateStatement.setString(7, member.getPerusahaan());
        updateStatement.setString(8, member.getJabatan());
        updateStatement.setString(9, member.getFavorite());
        updateStatement.setString(10, member.getTwit_pinbb());
        updateStatement.setString(11, member.getTestimoni());
        updateStatement.setString(12, member.getIdMember());
        updateStatement.executeUpdate();
    }

    public void delete(Member member) throws SQLException {
        deleteStatement.setString(1, member.getIdMember());
        deleteStatement.executeUpdate();
    }

    public Member getMember(String idMember) throws SQLException {
        getByIdStatement.setString(1, idMember);
        ResultSet rs = getByIdStatement.executeQuery();
        if (rs.next()) {
            Member member = new Member();
            member.setIdMember(rs.getString("id_member"));
            member.setNamaLengkap(rs.getString("nama_lengkap"));
            member.setNamaPanggilan(rs.getString("nama_panggilan"));
            member.setTglLahir(rs.getDate("tgl_lahir"));
            member.setAlamat(rs.getString("alamat"));
            member.setTelepon(rs.getString("telepon"));
            member.setPekerjaan(rs.getString("pekerjaan"));
            member.setPerusahaan(rs.getString("perusahaan"));
            member.setJabatan(rs.getString("jabatan"));
            member.setFavorite(rs.getString("favorite"));
            member.setTwit_pinbb(rs.getString("twit_pinbb"));
            member.setTestimoni(rs.getString("testimoni"));
            return member;
        }
        return null;
    }

    public Member getMemberByName(String namaMember) throws SQLException {
        getByNameStatement.setString(1, namaMember);
        ResultSet rs = getByNameStatement.executeQuery();
        if (rs.next()) {
            Member member = new Member();
            member.setIdMember(rs.getString("id_member"));
            member.setNamaLengkap(rs.getString("nama_lengkap"));
            member.setNamaPanggilan(rs.getString("nama_panggilan"));
            member.setTglLahir(rs.getDate("tgl_lahir"));
            member.setAlamat(rs.getString("alamat"));
            member.setTelepon(rs.getString("telepon"));
            member.setPekerjaan(rs.getString("pekerjaan"));
            member.setPerusahaan(rs.getString("perusahaan"));
            member.setJabatan(rs.getString("jabatan"));
            member.setFavorite(rs.getString("favorite"));
            member.setTwit_pinbb(rs.getString("twit_pinbb"));
            member.setTestimoni(rs.getString("testimoni"));
            return member;
        }
        return null;
    }

    public List<Member> getMember() throws SQLException {
        ResultSet rs = getAllStatement.executeQuery();
        List<Member> list = new ArrayList<Member>();
        while (rs.next()) {
            Member member = new Member();
            member.setIdMember(rs.getString("id_member"));
            member.setNamaLengkap(rs.getString("nama_lengkap"));
            member.setNamaPanggilan(rs.getString("nama_panggilan"));
            member.setTglLahir(rs.getDate("tgl_lahir"));
            member.setAlamat(rs.getString("alamat"));
            member.setTelepon(rs.getString("telepon"));
            member.setPekerjaan(rs.getString("pekerjaan"));
            member.setPerusahaan(rs.getString("perusahaan"));
            member.setJabatan(rs.getString("jabatan"));
            member.setFavorite(rs.getString("favorite"));
            member.setTwit_pinbb(rs.getString("twit_pinbb"));
            member.setTestimoni(rs.getString("testimoni"));
            list.add(member);
        }
        return list;
    }
}
