/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.retail.service;

import com.retail.dao.MemberDao;
import com.retail.model.Member;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 *
 * @author Hauw
 */
public class MemberService {
    private Connection connection;
    private MemberDao memberDao;
    
    public void setDataSource(DataSource dataSource) {
        try {
            connection = dataSource.getConnection();
            memberDao = new MemberDao();
            memberDao.setConnection(connection);
        } catch (SQLException ex) {
            Logger.getLogger(MemberService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void save(Member member) {
        try {
            connection.setAutoCommit(false);
            memberDao.save(member);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(MemberService.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }
    
    public void update(Member member) {
        try {
            connection.setAutoCommit(false);
            memberDao.update(member);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(MemberService.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }
    
    public void delete(Member member) {
        try {
            connection.setAutoCommit(false);
            memberDao.delete(member);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(MemberService.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }
    
    public Member getMember(String idMember) {
        try {
            return memberDao.getMember(idMember);
        } catch (SQLException ex) {
            Logger.getLogger(MemberService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public Member getMemberByName(String namaMember) {
        try {
            return memberDao.getMemberByName(namaMember);
        } catch (SQLException ex) {
            Logger.getLogger(MemberService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public List<Member> getMember() {
        List<Member> list = new ArrayList<Member>();
        try {
            list = memberDao.getMember();
        } catch (SQLException ex) {
            Logger.getLogger(MemberService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}
