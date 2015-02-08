/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * FrameUtama.java
 *
 * Created on 01 Agu 11, 4:54:40
 */
package com.retail.ui.main;

import com.retail.model.Role;
import com.retail.model.User;
import com.retail.ui.about.AboutDialog;
import com.retail.ui.master.ProdukPanel;
import com.retail.ui.master.SupplierPanel;
import com.retail.ui.master.MemberPanel;
import com.retail.ui.master.UserPanel;
import com.retail.ui.report.KasirReportPanel;
import com.retail.ui.report.PembelianReportPanel;
import com.retail.ui.report.PenjualanReportPanel;
import com.retail.ui.report.ProdukReportPanel;
import com.retail.ui.report.SupplierReportPanel;
import com.retail.ui.security.LoginPanel;
import com.retail.ui.transaksi.PembelianPanel;
import com.retail.ui.transaksi.PenjualanPanel;
import java.beans.PropertyVetoException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Hauw
 */
public class FrameUtama extends javax.swing.JFrame {

    public static ProdukPanel produkPanel;
    public static PenjualanPanel penjualanPanel;
    public static ProdukReportPanel produkReportPanel;
    public static PenjualanReportPanel penjualanReportPanel;
    public static SupplierPanel supplierPanel;
    public static MemberPanel memberPanel;
    public static SupplierReportPanel supplierReportPanel;
    public static PembelianPanel pembelianPanel;
    public static PembelianReportPanel pembelianReportPanel;
    public static UserPanel userPanel;
    public static KasirReportPanel kasirReportPanel;
    KoneksiDb konek; //deklarasi kelas
    ResultSet rs;

    /**
     * Creates new form FrameUtama
     */
    public FrameUtama() {
        initComponents();
        desktopPane.removeAll();
        setExtendedState(MAXIMIZED_BOTH);
        konek = new KoneksiDb();
        konek.koneksi();

    }

    public String getActiveIdUser() {
        String username = labelUser.getText().substring(labelUser.getText().lastIndexOf(' ') + 1, labelUser.getText().length());
        return username;
    }

    public void showLoginPanel() {
        startJam();
        User user = new LoginPanel().login();
        constructMenu(user);

    }

    private void isitabelUT() {
        DefaultTableModel isitabel = new DefaultTableModel();

        SimpleDateFormat sdf = new SimpleDateFormat("dd MM");
        Date date = new Date();
        String tanggal = sdf.format(date);

        isitabel.addColumn("ID Member");
        isitabel.addColumn("Nama");
        isitabel.addColumn("Tgl Lahir");
        isitabel.addColumn("Telepon");
        try {
            konek.st = konek.conn.createStatement();
            rs = konek.st.executeQuery("SELECT * FROM t_member where DATE_FORMAT(tgl_lahir,'%d %m')='" + tanggal + "'");
            while (rs.next()) {
                isitabel.addRow(new Object[]{rs.getString("id_member"), rs.getString("nama_lengkap"), rs.getString("tgl_lahir"), rs.getString("telepon")});
            }
            jTableUT.setModel(isitabel);
        } catch (Exception e) {
        }

    }

    private void isitabelTT() {
        DefaultTableModel itabel = new DefaultTableModel();
        itabel.addColumn("ID Member");
        itabel.addColumn("Nama");
        itabel.addColumn("Total Transaksi");

        try {
            konek.st = konek.conn.createStatement();
            rs = konek.st.executeQuery("SELECT t_member.id_member, t_member.nama_lengkap, sum(t_penjualan.total)"
                    + "FROM t_member inner join t_penjualan on t_member.id_member=t_penjualan.id_member group by t_penjualan.id_member");
            while (rs.next()) {
                itabel.addRow(new Object[]{rs.getString("t_member.id_member"), rs.getString("t_member.nama_lengkap"), rs.getString("sum(t_penjualan.total)")});
            }
            jTableTT.setModel(itabel);
        } catch (Exception e) {
        }

    }

     private void isiHT() {
        DefaultTableModel itabel = new DefaultTableModel();
        itabel.addColumn("ID Member");
        itabel.addColumn("Nama Member");
        itabel.addColumn("No. Nota");
        itabel.addColumn("Tanggal");
        itabel.addColumn("Nama Produk");
        itabel.addColumn("Qty");
        itabel.addColumn("Harga");
        itabel.addColumn("Jumlah");

        try {
            konek.st = konek.conn.createStatement();
            rs = konek.st.executeQuery("select t_penjualan.id_member, t_member.nama_lengkap, t_penjualan.id_penjualan, t_penjualan.tanggal, t_produk.nama as nama_produk, "
                    + "t_detail_penjualan.kuantitas, t_detail_penjualan.harga, t_detail_penjualan.sub_total  "
                    + " from t_penjualan inner join t_detail_penjualan on t_penjualan.id_penjualan=t_detail_penjualan.id_penjualan "
                    + "inner join t_member on t_penjualan.id_member=t_member.id_member inner join t_produk on t_detail_penjualan.id_produk=t_produk.id_produk ");
            while (rs.next()) {
                itabel.addRow(new Object[]{rs.getString("t_penjualan.id_member"),rs.getString("t_member.nama_lengkap"),rs.getString("t_penjualan.id_penjualan"), rs.getString("t_penjualan.tanggal"), rs.getString("nama_produk"), rs.getString("t_detail_penjualan.kuantitas"), rs.getString("t_detail_penjualan.harga"), rs.getString("t_detail_penjualan.sub_total")});
            }
            jTableHT.setModel(itabel);
        } catch (Exception e) {
        }

    }
    
    private void filterNama() {
        DefaultTableModel itabel = new DefaultTableModel();
        itabel.addColumn("ID Member");
        itabel.addColumn("Nama");
        itabel.addColumn("Total Transaksi");

        try {
            konek.st = konek.conn.createStatement();
            rs = konek.st.executeQuery("SELECT t_member.id_member, t_member.nama_lengkap, sum(t_penjualan.total)"
                    + "FROM t_member inner join t_penjualan on t_member.id_member=t_penjualan.id_member where t_member.nama_lengkap like '%"
                    + textNama.getText() + "%' group by t_penjualan.id_member");
            while (rs.next()) {
                itabel.addRow(new Object[]{rs.getString("t_member.id_member"), rs.getString("t_member.nama_lengkap"), rs.getString("sum(t_penjualan.total)")});
            }
            jTableHT.setModel(itabel);
        } catch (Exception e) {
        }

    }

    private void filterTrans(String jumlah) {
        DefaultTableModel itabel = new DefaultTableModel();
        itabel.addColumn("ID Member");
        itabel.addColumn("Nama");
        itabel.addColumn("Total Transaksi");

        try {
            konek.st = konek.conn.createStatement();
            rs = konek.st.executeQuery("SELECT t_member.id_member, t_member.nama_lengkap, sum(t_penjualan.total) as Total"
                    + " FROM t_member inner join t_penjualan on t_member.id_member=t_penjualan.id_member where Total>=" + jumlah + " group by t_penjualan.id_member");
            while (rs.next()) {
                itabel.addRow(new Object[]{rs.getString("t_member.id_member"), rs.getString("t_member.nama_lengkap"), rs.getString("Total")});
            }
            jTableTT.setModel(itabel);
        } catch (Exception e) {
        }

    }

    private void filterHis(String id_member) {
        DefaultTableModel itabel = new DefaultTableModel();
        itabel.addColumn("ID Member");
        itabel.addColumn("Nama Member");
        itabel.addColumn("No. Nota");
        itabel.addColumn("Tanggal");
        itabel.addColumn("Nama Produk");
        itabel.addColumn("Qty");
        itabel.addColumn("Harga");
        itabel.addColumn("Jumlah");

        try {
            konek.st = konek.conn.createStatement();
            rs = konek.st.executeQuery("select t_penjualan.id_member, t_member.nama_lengkap, t_penjualan.id_penjualan, t_penjualan.tanggal, t_produk.nama as nama_produk, "
                    + "t_detail_penjualan.kuantitas, t_detail_penjualan.harga, t_detail_penjualan.sub_total  "
                    + " from t_penjualan inner join t_detail_penjualan on t_penjualan.id_penjualan=t_detail_penjualan.id_penjualan "
                    + "inner join t_member on t_penjualan.id_member=t_member.id_member inner join t_produk on t_detail_penjualan.id_produk=t_produk.id_produk "
                    + " where t_penjualan.id_member='" + id_member + "'");
            while (rs.next()) {
                itabel.addRow(new Object[]{rs.getString("t_penjualan.id_member"),rs.getString("t_member.nama_lengkap"),rs.getString("t_penjualan.id_penjualan"), rs.getString("t_penjualan.tanggal"), rs.getString("nama_produk"), rs.getString("t_detail_penjualan.kuantitas"), rs.getString("t_detail_penjualan.harga"), rs.getString("t_detail_penjualan.sub_total")});
            }
            jTableHT.setModel(itabel);
        } catch (Exception e) {
        }

    }

    private void constructMenu(User user) {
        if (user.getRole() == Role.KASIR) {
            menuBar.add(menuTransaksi);
            desktopPane.add(jLabel1);
            desktopPane.add(jLabel2);
            desktopPane.add(jLabel3);
            desktopPane.add(jLabel4);
            desktopPane.add(buttonGlass1);
            desktopPane.add(buttonGlass2);
            desktopPane.add(buttonGlass3);
            desktopPane.add(buttonGlass4);
            desktopPane.add(buttonGlass5);
            desktopPane.add(jScrollPane1);
            desktopPane.add(jScrollPane2);
            desktopPane.add(jScrollPane3);
//            desktopPane.add(jTableHT);
//            desktopPane.add(jTableTT);
//            desktopPane.add(jTableUT);
            desktopPane.add(textJumlah);
            desktopPane.add(textNama);
            desktopPane.add(textNama1);
            
            
        } else if (user.getRole() == Role.ADMIN) {
            menuBar.add(menuMaster);
            menuBar.add(menuTransaksi);
            menuBar.add(menuLaporan);
            desktopPane.add(jLabel1);
            desktopPane.add(jLabel2);
            desktopPane.add(jLabel3);
            desktopPane.add(jLabel4);
            desktopPane.add(buttonGlass1);
            desktopPane.add(buttonGlass2);
            desktopPane.add(buttonGlass3);
            desktopPane.add(buttonGlass4);
            desktopPane.add(buttonGlass5);
            desktopPane.add(jScrollPane1);
            desktopPane.add(jScrollPane2);
            desktopPane.add(jScrollPane3);
//            desktopPane.add(jTableHT);
//            desktopPane.add(jTableTT);
//            desktopPane.add(jTableUT);
            desktopPane.add(textJumlah);
            desktopPane.add(textNama);
            desktopPane.add(textNama1);
            
            
        }
        menuBar.add(menuHelp);
        labelRole.setText("Role: " + user.getRole());
        labelUser.setText("User: " + user.getIdUser());
        menuBar.updateUI();

        isitabelUT();
        isitabelTT();
        isiHT();
    }

    private void startJam() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    labelJam.setText(new SimpleDateFormat("EEEE, dd-MM-yyyy HH:mm:ss  ").format(new Date()));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(FrameUtama.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }).start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menuTransaksi = new javax.swing.JMenu();
        menuTransaksiPenjualan = new javax.swing.JMenuItem();
        menuTransaksiPembelian = new javax.swing.JMenuItem();
        menuMaster = new javax.swing.JMenu();
        menuMasterProduk = new javax.swing.JMenuItem();
        menuMasterSupplier = new javax.swing.JMenuItem();
        menuMasterMember = new javax.swing.JMenuItem();
        menuMasterUser = new javax.swing.JMenuItem();
        menuLaporan = new javax.swing.JMenu();
        menuLaporanProduk = new javax.swing.JMenuItem();
        menuLaporanSupplier = new javax.swing.JMenuItem();
        menuLaporanPenjualan = new javax.swing.JMenuItem();
        menuLaporanPembelian = new javax.swing.JMenuItem();
        menuLaporanKasir = new javax.swing.JMenuItem();
        menuHelp = new javax.swing.JMenu();
        menuHelpAbout = new javax.swing.JMenuItem();
        aerithContentPanel1 = new aerith.swing.AerithContentPanel();
        desktopPane = new javax.swing.JDesktopPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableUT = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableHT = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        textJumlah = new paket.launk.java.controller.OvalTextField();
        textNama = new paket.launk.java.controller.OvalTextField();
        buttonGlass1 = new paket.launk.java.controller.ButtonGlass();
        buttonGlass2 = new paket.launk.java.controller.ButtonGlass();
        buttonGlass3 = new paket.launk.java.controller.ButtonGlass();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableTT = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        textNama1 = new paket.launk.java.controller.OvalTextField();
        buttonGlass4 = new paket.launk.java.controller.ButtonGlass();
        buttonGlass5 = new paket.launk.java.controller.ButtonGlass();
        panelFooter = new aerith.swing.AerithFooter();
        labelRole = new javax.swing.JLabel();
        labelUser = new javax.swing.JLabel();
        labelJam = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        menuLogout = new javax.swing.JMenuItem();
        menuKeluar = new javax.swing.JMenuItem();

        menuTransaksi.setText("Transaksi");

        menuTransaksiPenjualan.setText("Penjualan");
        menuTransaksiPenjualan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuTransaksiPenjualanActionPerformed(evt);
            }
        });
        menuTransaksi.add(menuTransaksiPenjualan);

        menuTransaksiPembelian.setText("Pembelian");
        menuTransaksiPembelian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuTransaksiPembelianActionPerformed(evt);
            }
        });
        menuTransaksi.add(menuTransaksiPembelian);

        menuMaster.setText("Master");

        menuMasterProduk.setText("Produk");
        menuMasterProduk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuMasterProdukActionPerformed(evt);
            }
        });
        menuMaster.add(menuMasterProduk);

        menuMasterSupplier.setText("Supplier");
        menuMasterSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuMasterSupplierActionPerformed(evt);
            }
        });
        menuMaster.add(menuMasterSupplier);

        menuMasterMember.setText("Member");
        menuMasterMember.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuMasterMemberActionPerformed(evt);
            }
        });
        menuMaster.add(menuMasterMember);

        menuMasterUser.setText("User");
        menuMasterUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuMasterUserActionPerformed(evt);
            }
        });
        menuMaster.add(menuMasterUser);

        menuLaporan.setText("Laporan");

        menuLaporanProduk.setText("Produk");
        menuLaporanProduk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuLaporanProdukActionPerformed(evt);
            }
        });
        menuLaporan.add(menuLaporanProduk);

        menuLaporanSupplier.setText("Supplier");
        menuLaporanSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuLaporanSupplierActionPerformed(evt);
            }
        });
        menuLaporan.add(menuLaporanSupplier);

        menuLaporanPenjualan.setText("Penjualan");
        menuLaporanPenjualan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuLaporanPenjualanActionPerformed(evt);
            }
        });
        menuLaporan.add(menuLaporanPenjualan);

        menuLaporanPembelian.setText("Pembelian");
        menuLaporanPembelian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuLaporanPembelianActionPerformed(evt);
            }
        });
        menuLaporan.add(menuLaporanPembelian);

        menuLaporanKasir.setText("Kasir");
        menuLaporanKasir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuLaporanKasirActionPerformed(evt);
            }
        });
        menuLaporan.add(menuLaporanKasir);

        menuHelp.setText("Bantuan");

        menuHelpAbout.setText("Tentang Aplikasi");
        menuHelpAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuHelpAboutActionPerformed(evt);
            }
        });
        menuHelp.add(menuHelpAbout);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("AlexanderRy Point Of Sales");

        aerithContentPanel1.setLayout(new java.awt.BorderLayout());

        desktopPane.setOpaque(false);

        jTableUT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jTableUT.setOpaque(false);
        jScrollPane1.setViewportView(jTableUT);

        jScrollPane1.setBounds(0, 30, 390, 370);
        desktopPane.add(jScrollPane1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("History  Transaksi Member:");
        jLabel1.setBounds(750, 10, 250, 17);
        desktopPane.add(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jTableHT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(jTableHT);

        jScrollPane2.setBounds(750, 30, 590, 370);
        desktopPane.add(jScrollPane2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Member yang berulang tahun hari ini :");
        jLabel2.setBounds(0, 10, 250, 17);
        desktopPane.add(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Filter :");
        jLabel3.setBounds(380, 410, 50, 17);
        desktopPane.add(jLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);

        textJumlah.setText("Jumlah Transaksi");
        textJumlah.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                textJumlahMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                textJumlahMouseExited(evt);
            }
        });
        textJumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textJumlahActionPerformed(evt);
            }
        });
        textJumlah.setBounds(430, 450, 170, 24);
        desktopPane.add(textJumlah, javax.swing.JLayeredPane.DEFAULT_LAYER);

        textNama.setText("Nama Member");
        textNama.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                textNamaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                textNamaMouseExited(evt);
            }
        });
        textNama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textNamaActionPerformed(evt);
            }
        });
        textNama.setBounds(430, 410, 170, 24);
        desktopPane.add(textNama, javax.swing.JLayeredPane.DEFAULT_LAYER);

        buttonGlass1.setText("Reset");
        buttonGlass1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonGlass1ActionPerformed(evt);
            }
        });
        buttonGlass1.setBounds(1260, 410, 80, 30);
        desktopPane.add(buttonGlass1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        buttonGlass2.setText("Filter");
        buttonGlass2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonGlass2ActionPerformed(evt);
            }
        });
        buttonGlass2.setBounds(600, 450, 70, 30);
        desktopPane.add(buttonGlass2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        buttonGlass3.setText("Filter");
        buttonGlass3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonGlass3ActionPerformed(evt);
            }
        });
        buttonGlass3.setBounds(600, 410, 70, 30);
        desktopPane.add(buttonGlass3, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jTableTT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(jTableTT);

        jScrollPane3.setBounds(400, 30, 340, 370);
        desktopPane.add(jScrollPane3, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Jumlah  Transaksi Member:");
        jLabel4.setBounds(400, 10, 250, 17);
        desktopPane.add(jLabel4, javax.swing.JLayeredPane.DEFAULT_LAYER);

        textNama1.setText("Nama Member");
        textNama1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                textNama1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                textNama1MouseExited(evt);
            }
        });
        textNama1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textNama1ActionPerformed(evt);
            }
        });
        textNama1.setBounds(950, 410, 230, 24);
        desktopPane.add(textNama1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        buttonGlass4.setText("Filter");
        buttonGlass4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonGlass4ActionPerformed(evt);
            }
        });
        buttonGlass4.setBounds(1190, 410, 70, 30);
        desktopPane.add(buttonGlass4, javax.swing.JLayeredPane.DEFAULT_LAYER);

        buttonGlass5.setText("Reset");
        buttonGlass5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonGlass5ActionPerformed(evt);
            }
        });
        buttonGlass5.setBounds(670, 410, 80, 70);
        desktopPane.add(buttonGlass5, javax.swing.JLayeredPane.DEFAULT_LAYER);

        aerithContentPanel1.add(desktopPane, java.awt.BorderLayout.CENTER);

        labelRole.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        labelRole.setForeground(new java.awt.Color(255, 255, 255));
        labelRole.setText("Role:");

        labelUser.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        labelUser.setForeground(new java.awt.Color(255, 255, 255));
        labelUser.setText("User:");

        labelJam.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        labelJam.setForeground(new java.awt.Color(255, 255, 255));
        labelJam.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        org.jdesktop.layout.GroupLayout panelFooterLayout = new org.jdesktop.layout.GroupLayout(panelFooter);
        panelFooter.setLayout(panelFooterLayout);
        panelFooterLayout.setHorizontalGroup(
            panelFooterLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelFooterLayout.createSequentialGroup()
                .add(labelRole, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 170, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(20, 20, 20)
                .add(labelUser, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 180, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(220, 220, 220)
                .add(labelJam, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 772, Short.MAX_VALUE))
        );
        panelFooterLayout.setVerticalGroup(
            panelFooterLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(labelRole, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 18, Short.MAX_VALUE)
            .add(labelUser, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 18, Short.MAX_VALUE)
            .add(labelJam, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 18, Short.MAX_VALUE)
        );

        aerithContentPanel1.add(panelFooter, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(aerithContentPanel1, java.awt.BorderLayout.CENTER);

        menuFile.setText("File");

        menuLogout.setText("Logout");
        menuLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuLogoutActionPerformed(evt);
            }
        });
        menuFile.add(menuLogout);

        menuKeluar.setText("Keluar");
        menuKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuKeluarActionPerformed(evt);
            }
        });
        menuFile.add(menuKeluar);

        menuBar.add(menuFile);

        setJMenuBar(menuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuMasterProdukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuMasterProdukActionPerformed
        try {
            // TODO add your handling code here:
            if (produkPanel == null) {
                produkPanel = new ProdukPanel();
                desktopPane.add(produkPanel);
            } else {
                produkPanel.toFront();
            }
            produkPanel.setSize(desktopPane.getSize());
            produkPanel.setSelected(true);
            produkPanel.setVisible(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(FrameUtama.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_menuMasterProdukActionPerformed

    private void menuTransaksiPenjualanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuTransaksiPenjualanActionPerformed
        try {
            // TODO add your handling code here:
            if (penjualanPanel == null) {
                penjualanPanel = new PenjualanPanel();
                desktopPane.add(penjualanPanel);
            } else {
                penjualanPanel.toFront();
            }
            penjualanPanel.setSize(desktopPane.getSize());
            penjualanPanel.setSelected(true);
            penjualanPanel.setVisible(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(FrameUtama.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_menuTransaksiPenjualanActionPerformed

    private void menuLaporanProdukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuLaporanProdukActionPerformed
        try {
            // TODO add your handling code here:
            if (produkReportPanel == null) {
                produkReportPanel = new ProdukReportPanel();
                desktopPane.add(produkReportPanel);
            } else {
                produkReportPanel.toFront();
            }
            produkReportPanel.setSize(desktopPane.getSize());
            produkReportPanel.setSelected(true);
            produkReportPanel.setVisible(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(FrameUtama.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_menuLaporanProdukActionPerformed

    private void menuLaporanPenjualanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuLaporanPenjualanActionPerformed
        try {
            // TODO add your handling code here:
            if (penjualanReportPanel == null) {
                penjualanReportPanel = new PenjualanReportPanel();
                desktopPane.add(penjualanReportPanel);
            } else {
                penjualanReportPanel.toFront();
            }
            penjualanReportPanel.setSize(desktopPane.getSize());
            penjualanReportPanel.setSelected(true);
            penjualanReportPanel.setVisible(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(FrameUtama.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_menuLaporanPenjualanActionPerformed

    private void menuMasterSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuMasterSupplierActionPerformed
        try {
            // TODO add your handling code here:
            if (supplierPanel == null) {
                supplierPanel = new SupplierPanel();
                desktopPane.add(supplierPanel);
            } else {
                supplierPanel.toFront();
            }
            supplierPanel.setSize(desktopPane.getSize());
            supplierPanel.setSelected(true);
            supplierPanel.setVisible(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(FrameUtama.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_menuMasterSupplierActionPerformed

    private void menuMasterMemberActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            // TODO add your handling code here:
            if (memberPanel == null) {
                memberPanel = new MemberPanel();
                desktopPane.add(memberPanel);
            } else {
                memberPanel.toFront();
            }
            memberPanel.setSize(desktopPane.getSize());
            memberPanel.setSelected(true);
            memberPanel.setVisible(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(FrameUtama.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void menuLaporanSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuLaporanSupplierActionPerformed
        try {
            // TODO add your handling code here:
            if (supplierReportPanel == null) {
                supplierReportPanel = new SupplierReportPanel();
                desktopPane.add(supplierReportPanel);
            } else {
                supplierReportPanel.toFront();
            }
            supplierReportPanel.setSize(desktopPane.getSize());
            supplierReportPanel.setSelected(true);
            supplierReportPanel.setVisible(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(FrameUtama.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_menuLaporanSupplierActionPerformed

    private void menuTransaksiPembelianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuTransaksiPembelianActionPerformed
        try {
            // TODO add your handling code here:
            if (pembelianPanel == null) {
                pembelianPanel = new PembelianPanel();
                desktopPane.add(pembelianPanel);
            } else {
                pembelianPanel.toFront();
            }
            pembelianPanel.setSize(desktopPane.getSize());
            pembelianPanel.setSelected(true);
            pembelianPanel.setVisible(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(FrameUtama.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_menuTransaksiPembelianActionPerformed

    private void menuLaporanPembelianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuLaporanPembelianActionPerformed
        try {
            // TODO add your handling code here:
            if (pembelianReportPanel == null) {
                pembelianReportPanel = new PembelianReportPanel();
                desktopPane.add(pembelianReportPanel);
            } else {
                pembelianReportPanel.toFront();
            }
            pembelianReportPanel.setSize(desktopPane.getSize());
            pembelianReportPanel.setSelected(true);
            pembelianReportPanel.setVisible(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(FrameUtama.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_menuLaporanPembelianActionPerformed

    private void menuKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuKeluarActionPerformed
        // TODO add your handling code here:
        System.exit(1);
    }//GEN-LAST:event_menuKeluarActionPerformed

    private void menuLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuLogoutActionPerformed
        // TODO add your handling code here:
        produkPanel = null;
        penjualanPanel = null;
        produkReportPanel = null;
        penjualanReportPanel = null;
        supplierPanel = null;
        memberPanel = null;
        supplierReportPanel = null;
        pembelianPanel = null;
        pembelianReportPanel = null;
        userPanel = null;
        desktopPane.removeAll();
        desktopPane.updateUI();
        menuBar.removeAll();
        menuBar.add(menuFile);
        menuBar.updateUI();
        labelRole.setText("Role:");
        labelUser.setText("User:");
        showLoginPanel();
    }//GEN-LAST:event_menuLogoutActionPerformed

    private void menuMasterUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuMasterUserActionPerformed
        try {
            // TODO add your handling code here:
            if (userPanel == null) {
                userPanel = new UserPanel();
                desktopPane.add(userPanel);
            } else {
                userPanel.toFront();
            }
            userPanel.setSize(desktopPane.getSize());
            userPanel.setSelected(true);
            userPanel.setVisible(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(FrameUtama.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_menuMasterUserActionPerformed

    private void menuLaporanKasirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuLaporanKasirActionPerformed
        try {
            // TODO add your handling code here:
            if (kasirReportPanel == null) {
                kasirReportPanel = new KasirReportPanel();
                desktopPane.add(kasirReportPanel);
            } else {
                kasirReportPanel.toFront();
            }
            kasirReportPanel.setSize(desktopPane.getSize());
            kasirReportPanel.setSelected(true);
            kasirReportPanel.setVisible(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(FrameUtama.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_menuLaporanKasirActionPerformed

    private void menuHelpAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuHelpAboutActionPerformed
        // TODO add your handling code here:
        new AboutDialog().setVisible(true);
    }//GEN-LAST:event_menuHelpAboutActionPerformed

    private void textJumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textJumlahActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textJumlahActionPerformed

    private void textNamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textNamaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textNamaActionPerformed

    private void textNamaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_textNamaMouseEntered
        // TODO add your handling code here:
        if ("Nama Member".equals(textNama.getText())) {
            textNama.setText("");
        }
    }//GEN-LAST:event_textNamaMouseEntered

    private void buttonGlass1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonGlass1ActionPerformed
        // TODO add your handling code here:
        isiHT();
    }//GEN-LAST:event_buttonGlass1ActionPerformed

    private void textJumlahMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_textJumlahMouseEntered
        // TODO add your handling code here:
        if ("Jumlah Transaksi".equals(textJumlah.getText())) {
            textJumlah.setText("0");
        }

    }//GEN-LAST:event_textJumlahMouseEntered

    private void buttonGlass2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonGlass2ActionPerformed
        // TODO add your handling code here:
        filterTrans(textJumlah.getText());
    }//GEN-LAST:event_buttonGlass2ActionPerformed

    private void buttonGlass3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonGlass3ActionPerformed
        // TODO add your handling code here:
        filterNama();
    }//GEN-LAST:event_buttonGlass3ActionPerformed

    private void textNama1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_textNama1MouseEntered
        // TODO add your handling code here:
        if ("Nama Member".equals(textNama1.getText())) {
            textNama1.setText("");
        }
    }//GEN-LAST:event_textNama1MouseEntered

    private void textNama1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textNama1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textNama1ActionPerformed

    private void buttonGlass4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonGlass4ActionPerformed
        // TODO add your handling code here:
        filterHis(textNama1.getText());
    }//GEN-LAST:event_buttonGlass4ActionPerformed

    private void buttonGlass5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonGlass5ActionPerformed
        // TODO add your handling code here:
        isitabelTT();
        textJumlah.setText("Jumlah Transaksi");
        textNama.setText("Nama Lengkap");
    }//GEN-LAST:event_buttonGlass5ActionPerformed

    private void textNama1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_textNama1MouseExited
        // TODO add your handling code here:
        if ("".equals(textNama1.getText())) {
            textNama1.setText("Nama Member");
        }
    }//GEN-LAST:event_textNama1MouseExited

    private void textNamaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_textNamaMouseExited
        // TODO add your handling code here:
         if ("".equals(textNama.getText())) {
            textNama.setText("Nama Member");
        }
    }//GEN-LAST:event_textNamaMouseExited

    private void textJumlahMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_textJumlahMouseExited
        // TODO add your handling code here:
         if ("0".equals(textJumlah.getText())) {
            textJumlah.setText("Jumlah Transaksi");
        }
    }//GEN-LAST:event_textJumlahMouseExited

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private aerith.swing.AerithContentPanel aerithContentPanel1;
    private paket.launk.java.controller.ButtonGlass buttonGlass1;
    private paket.launk.java.controller.ButtonGlass buttonGlass2;
    private paket.launk.java.controller.ButtonGlass buttonGlass3;
    private paket.launk.java.controller.ButtonGlass buttonGlass4;
    private paket.launk.java.controller.ButtonGlass buttonGlass5;
    private javax.swing.JDesktopPane desktopPane;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTableHT;
    private javax.swing.JTable jTableTT;
    private javax.swing.JTable jTableUT;
    private javax.swing.JLabel labelJam;
    private javax.swing.JLabel labelRole;
    private javax.swing.JLabel labelUser;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuFile;
    private javax.swing.JMenu menuHelp;
    private javax.swing.JMenuItem menuHelpAbout;
    private javax.swing.JMenuItem menuKeluar;
    private javax.swing.JMenu menuLaporan;
    private javax.swing.JMenuItem menuLaporanKasir;
    private javax.swing.JMenuItem menuLaporanPembelian;
    private javax.swing.JMenuItem menuLaporanPenjualan;
    private javax.swing.JMenuItem menuLaporanProduk;
    private javax.swing.JMenuItem menuLaporanSupplier;
    private javax.swing.JMenuItem menuLogout;
    private javax.swing.JMenu menuMaster;
    private javax.swing.JMenuItem menuMasterMember;
    private javax.swing.JMenuItem menuMasterProduk;
    private javax.swing.JMenuItem menuMasterSupplier;
    private javax.swing.JMenuItem menuMasterUser;
    private javax.swing.JMenu menuTransaksi;
    private javax.swing.JMenuItem menuTransaksiPembelian;
    private javax.swing.JMenuItem menuTransaksiPenjualan;
    private aerith.swing.AerithFooter panelFooter;
    private paket.launk.java.controller.OvalTextField textJumlah;
    private paket.launk.java.controller.OvalTextField textNama;
    private paket.launk.java.controller.OvalTextField textNama1;
    // End of variables declaration//GEN-END:variables
}
