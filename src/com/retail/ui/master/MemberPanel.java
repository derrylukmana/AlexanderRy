/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MemberPanel.java
 *
 * Created on 02 Agu 11, 17:46:05
 */
package com.retail.ui.master;

import com.retail.main.Main;
import com.retail.model.Member;
import com.retail.ui.main.FrameUtama;
import com.retail.ui.transaksi.ScanQRBarcode;
import hauw.util.PhoneNumberValidator;
import hauw.widget.TableHeaderRenderer;
import hauw.widget.ViewPortGlass;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.PatternSyntaxException;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Hauw
 */
public class MemberPanel extends javax.swing.JInternalFrame {

    private List<Member> listMember;
    private Member member;
    private TableRowSorter sorter;

    /**
     * Creates new form MemberPanel
     */
    public MemberPanel() {
        initComponents();
        refreshTable();
        initVars();
        kondisiAwal();
        initListeners();
    }

    private boolean validateForm() {
        if (textIdMember.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "ID Member tidak boleh kosong", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (textNamaMember.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Nama member tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (textAlamat.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Alamat tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (textTelepon.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Telepon tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (textNamaPanggil.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Nama Panggilan tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (textPekerjaan.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Pekerjaan tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (textPerusahaan.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Perusahaan tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (textJabatan.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Jabatan tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (textFav.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Parfum Favorite tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (textTesti.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Testimoni tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void clearForm() {
        textAlamat.setText("");
        textCari.setText("");
        textIdMember.setText("");
        textNamaMember.setText("");
        textTelepon.setText("");
        textFav.setText("");
        textJabatan.setText("");
        textNamaPanggil.setText("");
        textPekerjaan.setText("");
        textPerusahaan.setText("");
        textTesti.setText("");
        textTwitBB.setText("");
    }

    private void refreshTable() {
        listMember = Main.getMemberService().getMember();
        tableMember.setModel(new MemberTableModel(listMember));
        for (int i = 0; i < tableMember.getColumnCount(); i++) {
            tableMember.getColumnModel().getColumn(i).setHeaderRenderer(new TableHeaderRenderer());
        }

    }

    private void initVars() {
        sorter = new TableRowSorter(tableMember.getModel());
        tableMember.setRowSorter(sorter);
    }

    private void enableForm(boolean status) {
        textIdMember.setEnabled(status);
        textNamaMember.setEnabled(status);
        textAlamat.setEnabled(status);
        textTelepon.setEnabled(status);
        textFav.setEnabled(status);
        textJabatan.setEnabled(status);
        textNamaPanggil.setEnabled(status);
        textPekerjaan.setEnabled(status);
        textPerusahaan.setEnabled(status);
        textTesti.setEnabled(status);
        textTglLahir.setEnabled(status);
        textTwitBB.setEnabled(status);
        tombolPindai.setEnabled(status);
    }

    private void kondisiAwal() {
        toolbarPanel.kondisiAwal();
        enableForm(false);
    }

    private void kondisiSimpan() {
        toolbarPanel.kondisiSimpan();
        enableForm(true);
    }

    private void kondisiUbahOrHapus() {
        toolbarPanel.kondisiUbahOrHapus();
        enableForm(true);
        textIdMember.setEnabled(false);
    }

    private void loadModelToForm() {

        if (member != null) {
            textIdMember.setText(member.getIdMember());
            textNamaMember.setText(member.getNamaLengkap());
            textAlamat.setText(member.getAlamat());
            textTelepon.setText(member.getTelepon());
            textFav.setText(member.getFavorite());
            textJabatan.setText(member.getJabatan());
            textNamaPanggil.setText(member.getNamaPanggilan());
            textPekerjaan.setText(member.getPekerjaan());
            textPerusahaan.setText(member.getPerusahaan());
            textTesti.setText(member.getTestimoni());
            textTglLahir.setDate(member.getTglLahir());
            textTwitBB.setText(member.getTwit_pinbb());
        }
    }

    private void loadMember(String idMember){
        Member member = Main.getMemberService().getMember(idMember);
        if(member!= null){
            textIdMember.setText(member.getIdMember());
            textNamaMember.setText(member.getNamaLengkap());
            textAlamat.setText(member.getAlamat());
            textTelepon.setText(member.getTelepon());
            textFav.setText(member.getFavorite());
            textJabatan.setText(member.getJabatan());
            textNamaPanggil.setText(member.getNamaPanggilan());
            textPekerjaan.setText(member.getPekerjaan());
            textPerusahaan.setText(member.getPerusahaan());
            textTesti.setText(member.getTestimoni());
            textTglLahir.setDate(member.getTglLahir());
            textTwitBB.setText(member.getTwit_pinbb());
            kondisiUbahOrHapus();
        } else {
            JOptionPane.showMessageDialog(this, "Member belum terdaftar!", "Info", JOptionPane.INFORMATION_MESSAGE);
            kondisiSimpan();
            clearForm();
            
            
        }
    }
    
    private Member loadFormToModel() {
        


        Member m = new Member();
        m.setIdMember(textIdMember.getText());
        m.setNamaLengkap(textNamaMember.getText());
        m.setNamaPanggilan(textNamaPanggil.getText());
        m.setTglLahir(new Date(textTglLahir.getDate().getTime()));
        m.setAlamat(textAlamat.getText());
        m.setTelepon(textTelepon.getText());
        m.setPekerjaan(textPekerjaan.getText());
        m.setPerusahaan(textPerusahaan.getText());
        m.setJabatan(textJabatan.getText());
        m.setFavorite(textFav.getText());
        m.setTwit_pinbb(textTwitBB.getText());
        m.setTestimoni(textTesti.getText());
        return m;
    }

    private void initListeners() {
        tableMember.getSelectionModel().addListSelectionListener(new MemberSelectionListener());

        toolbarPanel.getButtonTambah().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                kondisiSimpan();
                textIdMember.requestFocusInWindow();
            }
        });

        toolbarPanel.getButtonSimpan().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (validateForm()) {
                    Main.getMemberService().save(loadFormToModel());
                    refreshTable();
                    clearForm();
                    kondisiAwal();

                }
            }
        });

        toolbarPanel.getButtonUbah().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateForm()) {
                    Main.getMemberService().update(loadFormToModel());
                    refreshTable();
                    clearForm();
                    kondisiAwal();
                    tableMember.clearSelection();
                }
            }
        });

        toolbarPanel.getButtonHapus().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (member != null) {
                    Main.getMemberService().delete(member);
                    refreshTable();
                    clearForm();
                    kondisiAwal();
                    tableMember.clearSelection();
                }
            }
        });

        toolbarPanel.getButtonBatal().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                kondisiAwal();
                clearForm();
                tableMember.clearSelection();
            }
        });

        textCari.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                String text = textCari.getText();
                if (text == null) {
                    sorter.setRowFilter(null);
                } else {
                    char[] charArray = text.toCharArray();
                    String[] stringArray = new String[charArray.length];

                    for (int i = 0; i < stringArray.length; i++) {
                        stringArray[i] = "[" + Character.toUpperCase(charArray[i])
                                + Character.toLowerCase(charArray[i]) + "]";
                    }

                    String regex = "";
                    for (String string : stringArray) {
                        regex += string;
                    }

                    try {
                        sorter.setRowFilter(RowFilter.regexFilter(regex, 0));
                    } catch (PatternSyntaxException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        darkgrayPanel1 = new paket.launk.java.container.DarkgrayPanel();
        panelGlass1 = new paket.launk.java.container.PanelGlass();
        toolbarPanel = new com.retail.ui.toolbar.MasterToolbarPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableMember = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        textIdMember = new paket.launk.java.controller.OvalTextField();
        textNamaMember = new paket.launk.java.controller.OvalTextField();
        textAlamat = new paket.launk.java.controller.OvalTextField();
        textTelepon = new paket.launk.java.controller.OvalTextField();
        jLabel5 = new javax.swing.JLabel();
        textCari = new aerith.swing.AerithTextField();
        jLabel7 = new javax.swing.JLabel();
        textPekerjaan = new paket.launk.java.controller.OvalTextField();
        jLabel6 = new javax.swing.JLabel();
        textNamaPanggil = new paket.launk.java.controller.OvalTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        textPerusahaan = new paket.launk.java.controller.OvalTextField();
        jLabel10 = new javax.swing.JLabel();
        textJabatan = new paket.launk.java.controller.OvalTextField();
        textTwitBB = new paket.launk.java.controller.OvalTextField();
        jLabel11 = new javax.swing.JLabel();
        textFav = new paket.launk.java.controller.OvalTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        textTesti = new paket.launk.java.controller.OvalTextField();
        textTglLahir = new com.toedter.calendar.JDateChooser();
        tombolPindai = new paket.launk.java.controller.ButtonGlass();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Member");
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosing(evt);
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        darkgrayPanel1.setName("darkgrayPanel1"); // NOI18N
        darkgrayPanel1.setLayout(new java.awt.BorderLayout());

        panelGlass1.setName("panelGlass1"); // NOI18N

        toolbarPanel.setName("toolbarPanel"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N
        jScrollPane1.setOpaque(false);
        jScrollPane1.setViewport(new ViewPortGlass());

        tableMember.setAutoCreateRowSorter(true);
        tableMember.setBackground(new java.awt.Color(255, 255, 255, 0));
        tableMember.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tableMember.setForeground(new java.awt.Color(255, 255, 255));
        tableMember.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tableMember.setFillsViewportHeight(true);
        tableMember.setGridColor(new java.awt.Color(255, 255, 255));
        tableMember.setName("tableMember"); // NOI18N
        tableMember.setOpaque(false);
        tableMember.setSelectionBackground(new java.awt.Color(51, 51, 255));
        tableMember.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jScrollPane1.setViewportView(tableMember);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("ID Member");
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Nama Lengkap");
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Alamat");
        jLabel3.setName("jLabel3"); // NOI18N

        jLabel4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Telepon");
        jLabel4.setName("jLabel4"); // NOI18N

        textIdMember.setCaretColor(new java.awt.Color(255, 255, 255));
        textIdMember.setName("textIdMember"); // NOI18N
        textIdMember.setNextFocusableComponent(textNamaMember);
        textIdMember.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textIdMemberActionPerformed(evt);
            }
        });

        textNamaMember.setCaretColor(new java.awt.Color(255, 255, 255));
        textNamaMember.setName("textNamaMember"); // NOI18N
        textNamaMember.setNextFocusableComponent(textNamaPanggil);

        textAlamat.setCaretColor(new java.awt.Color(255, 255, 255));
        textAlamat.setName("textAlamat"); // NOI18N
        textAlamat.setNextFocusableComponent(textTelepon);

        textTelepon.setDocument(new PhoneNumberValidator(15, true));
        textTelepon.setCaretColor(new java.awt.Color(255, 255, 255));
        textTelepon.setName("textTelepon"); // NOI18N
        textTelepon.setNextFocusableComponent(textPekerjaan);

        jLabel5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Cari:");
        jLabel5.setName("jLabel5"); // NOI18N

        textCari.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        textCari.setName("textCari"); // NOI18N

        jLabel7.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Pekerjaan");
        jLabel7.setName("jLabel7"); // NOI18N

        textPekerjaan.setCaretColor(new java.awt.Color(255, 255, 255));
        textPekerjaan.setName("textPekerjaan"); // NOI18N
        textPekerjaan.setNextFocusableComponent(textPerusahaan);

        jLabel6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Nama Panggilan");
        jLabel6.setName("jLabel6"); // NOI18N

        textNamaPanggil.setCaretColor(new java.awt.Color(255, 255, 255));
        textNamaPanggil.setName("textNamaPanggil"); // NOI18N
        textNamaPanggil.setNextFocusableComponent(textTglLahir);

        jLabel8.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Tanggal Lahir");
        jLabel8.setName("jLabel8"); // NOI18N

        jLabel9.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Perusahaan");
        jLabel9.setName("jLabel9"); // NOI18N

        textPerusahaan.setCaretColor(new java.awt.Color(255, 255, 255));
        textPerusahaan.setName("textPerusahaan"); // NOI18N
        textPerusahaan.setNextFocusableComponent(textJabatan);

        jLabel10.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Jabatan");
        jLabel10.setName("jLabel10"); // NOI18N

        textJabatan.setCaretColor(new java.awt.Color(255, 255, 255));
        textJabatan.setName("textJabatan"); // NOI18N
        textJabatan.setNextFocusableComponent(textTwitBB);

        textTwitBB.setCaretColor(new java.awt.Color(255, 255, 255));
        textTwitBB.setName("textTwitBB"); // NOI18N
        textTwitBB.setNextFocusableComponent(textFav);

        jLabel11.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Twitter/Pin BB");
        jLabel11.setName("jLabel11"); // NOI18N

        textFav.setCaretColor(new java.awt.Color(255, 255, 255));
        textFav.setName("textFav"); // NOI18N
        textFav.setNextFocusableComponent(textTesti);

        jLabel12.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Parfum Fav.");
        jLabel12.setName("jLabel12"); // NOI18N

        jLabel13.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Testimoni");
        jLabel13.setName("jLabel13"); // NOI18N

        textTesti.setCaretColor(new java.awt.Color(255, 255, 255));
        textTesti.setName("textTesti"); // NOI18N
        textTesti.setNextFocusableComponent(textAlamat);

        textTglLahir.setDateFormatString("dd-MMMM-yyyy");
        textTglLahir.setName("textTglLahir"); // NOI18N
        textTglLahir.setNextFocusableComponent(textAlamat);

        tombolPindai.setText("Pindai");
        tombolPindai.setName("tombolPindai"); // NOI18N
        tombolPindai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tombolPindaiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelGlass1Layout = new javax.swing.GroupLayout(panelGlass1);
        panelGlass1.setLayout(panelGlass1Layout);
        panelGlass1Layout.setHorizontalGroup(
            panelGlass1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGlass1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelGlass1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(panelGlass1Layout.createSequentialGroup()
                        .addGroup(panelGlass1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelGlass1Layout.createSequentialGroup()
                                .addGroup(panelGlass1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2))
                                .addGap(27, 27, 27)
                                .addGroup(panelGlass1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(textNamaMember, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(panelGlass1Layout.createSequentialGroup()
                                        .addComponent(textIdMember, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(tombolPindai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 13, Short.MAX_VALUE))))
                            .addComponent(textTesti, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelGlass1Layout.createSequentialGroup()
                                .addGroup(panelGlass1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelGlass1Layout.createSequentialGroup()
                                        .addGroup(panelGlass1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel6)
                                            .addComponent(jLabel8)
                                            .addComponent(jLabel13))
                                        .addGap(18, 18, 18)
                                        .addGroup(panelGlass1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(textNamaPanggil, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(textTglLahir, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelGlass1Layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(textCari, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 106, Short.MAX_VALUE)))
                        .addGroup(panelGlass1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelGlass1Layout.createSequentialGroup()
                                .addGroup(panelGlass1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(panelGlass1Layout.createSequentialGroup()
                                        .addGap(76, 76, 76)
                                        .addComponent(textAlamat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(panelGlass1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel3)
                                        .addGroup(panelGlass1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelGlass1Layout.createSequentialGroup()
                                                .addComponent(jLabel4)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(textTelepon, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(textPekerjaan, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelGlass1Layout.createSequentialGroup()
                                                .addComponent(jLabel9)
                                                .addGap(18, 18, 18)
                                                .addComponent(textPerusahaan, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addGap(18, 18, 18)
                                .addGroup(panelGlass1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(panelGlass1Layout.createSequentialGroup()
                                        .addComponent(jLabel10)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(textJabatan, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panelGlass1Layout.createSequentialGroup()
                                        .addGroup(panelGlass1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel11)
                                            .addComponent(jLabel12))
                                        .addGap(18, 18, 18)
                                        .addGroup(panelGlass1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(textFav, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(textTwitBB, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(panelGlass1Layout.createSequentialGroup()
                                .addComponent(toolbarPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)))))
                .addContainerGap())
        );

        panelGlass1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {textAlamat, textNamaMember, textTelepon});

        panelGlass1Layout.setVerticalGroup(
            panelGlass1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGlass1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelGlass1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelGlass1Layout.createSequentialGroup()
                        .addGroup(panelGlass1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(textIdMember, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tombolPindai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelGlass1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(textNamaMember, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelGlass1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(textNamaPanggil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelGlass1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(textTglLahir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel13))
                    .addGroup(panelGlass1Layout.createSequentialGroup()
                        .addGroup(panelGlass1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(textAlamat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel10)
                            .addComponent(textJabatan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelGlass1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(textTelepon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)
                            .addComponent(textTwitBB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelGlass1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(textPekerjaan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel12)
                            .addComponent(textFav, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(panelGlass1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(textPerusahaan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(2, 2, 2)
                .addGroup(panelGlass1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelGlass1Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(toolbarPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelGlass1Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(textTesti, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addGroup(panelGlass1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(textCari, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5))
                        .addGap(47, 47, 47)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)
                .addContainerGap())
        );

        darkgrayPanel1.add(panelGlass1, java.awt.BorderLayout.CENTER);

        getContentPane().add(darkgrayPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here:
        FrameUtama.memberPanel = null;
        dispose();
    }//GEN-LAST:event_formInternalFrameClosing

    private void textIdMemberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textIdMemberActionPerformed
        // TODO add your handling code here:
        loadMember(textIdMember.getText());
    }//GEN-LAST:event_textIdMemberActionPerformed

    private void tombolPindaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tombolPindaiActionPerformed
        // TODO add your handling code here:
        String memberId = new ScanQRBarcode().getCodeQRBarcode();
        if (memberId != null) {
            loadMember(memberId);
            textIdMember.setText(memberId);
        }else{
            JOptionPane.showMessageDialog(null, "Kode Kosong");
        }
    }//GEN-LAST:event_tombolPindaiActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private paket.launk.java.container.DarkgrayPanel darkgrayPanel1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private paket.launk.java.container.PanelGlass panelGlass1;
    private javax.swing.JTable tableMember;
    private paket.launk.java.controller.OvalTextField textAlamat;
    private aerith.swing.AerithTextField textCari;
    private paket.launk.java.controller.OvalTextField textFav;
    private paket.launk.java.controller.OvalTextField textIdMember;
    private paket.launk.java.controller.OvalTextField textJabatan;
    private paket.launk.java.controller.OvalTextField textNamaMember;
    private paket.launk.java.controller.OvalTextField textNamaPanggil;
    private paket.launk.java.controller.OvalTextField textPekerjaan;
    private paket.launk.java.controller.OvalTextField textPerusahaan;
    private paket.launk.java.controller.OvalTextField textTelepon;
    private paket.launk.java.controller.OvalTextField textTesti;
    private com.toedter.calendar.JDateChooser textTglLahir;
    private paket.launk.java.controller.OvalTextField textTwitBB;
    private paket.launk.java.controller.ButtonGlass tombolPindai;
    private com.retail.ui.toolbar.MasterToolbarPanel toolbarPanel;
    // End of variables declaration//GEN-END:variables

    private class MemberTableModel extends AbstractTableModel {

        private List<Member> rows;
        private List<String> columns;

        public MemberTableModel(List<Member> rows) {
            this.rows = rows;
            columns = new ArrayList<String>();
            columns.add("ID Member");
            columns.add("Nama Lengkap");
            columns.add("Nama Panggilan");
            columns.add("Tanggal Lahir");
            columns.add("Alamat");
            columns.add("Telepon");
            columns.add("Pekerjaan");
            columns.add("Perusahaan");
            columns.add("Jabatan");
            columns.add("Parfum Favorite");
            columns.add("Twitter/Pin BB");
            columns.add("Testimoni");

        }

        @Override
        public String getColumnName(int column) {
            return columns.get(column);
        }

        @Override
        public int getRowCount() {
            return rows.size();
        }

        @Override
        public int getColumnCount() {
            return columns.size();
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return rows.get(rowIndex).getIdMember();
                case 1:
                    return rows.get(rowIndex).getNamaLengkap();
                case 2:
                    return rows.get(rowIndex).getNamaPanggilan();
                case 3:
                    return rows.get(rowIndex).getTglLahir();
                case 4:
                    return rows.get(rowIndex).getAlamat();
                case 5:
                    return rows.get(rowIndex).getTelepon();
                case 6:
                    return rows.get(rowIndex).getPekerjaan();
                case 7:
                    return rows.get(rowIndex).getPerusahaan();
                case 8:
                    return rows.get(rowIndex).getJabatan();
                case 9:
                    return rows.get(rowIndex).getFavorite();
                case 10:
                    return rows.get(rowIndex).getTwit_pinbb();
                case 11:
                    return rows.get(rowIndex).getTestimoni();
                default:
                    return "";
            }
        }
    }

    private class MemberSelectionListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            int index = tableMember.getSelectedRow();
            if (index >= 0) {
                kondisiUbahOrHapus();
                member = listMember.get(index);
                loadModelToForm();
            }
        }
    }
}
