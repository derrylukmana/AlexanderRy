/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PenjualanPanel.java
 *
 * Created on 01 Agu 11, 8:22:40
 */
package com.retail.ui.transaksi;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;

import java.awt.image.BufferedImage;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import java.awt.Dimension;

import com.retail.main.Main;
import com.retail.model.Member;
import com.retail.model.Penjualan;
import com.retail.model.PenjualanDetail;
import com.retail.model.Produk;
import com.retail.ui.main.FrameUtama;


import com.retail.ui.report.NotaPenjualanReportPanel;
import hauw.util.NumberValidator;
import hauw.widget.TableHeaderRenderer;
import hauw.widget.ViewPortGlass;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;


import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.text.BadLocationException;

/**
 *
 * @author Hauw
 */
public class PenjualanPanel extends javax.swing.JInternalFrame implements Runnable, ThreadFactory {

    private List<PenjualanDetail> listDetailPenjualan = new ArrayList<PenjualanDetail>();
    private PenjualanDetail penjualan;
    private String nomorPenjualan = null;
    private static final long serialVersionUID = 6441489157408381878L;
    private Executor executor = Executors.newSingleThreadExecutor(this);
    private Dimension size;
    public Webcam webcam = null;
    private static WebcamPanel panel = null;
    public String codeQRBarcode = null;
    final static boolean shouldFill = true;
    final static boolean shouldWeightX = true;
    final static boolean RIGHT_TO_LEFT = false;
    //private volatile boolean running = true;

    public String getNomorPenjualan() {
        return nomorPenjualan;
    }

    public void setNomorPenjualan(String nomorPenjualan) {
        this.nomorPenjualan = nomorPenjualan;
    }

    /**
     * Creates new form PenjualanPanel
     */
    public PenjualanPanel() {
        size = WebcamResolution.QVGA.getSize();
        initComponents();
        initTable();
        initListeners();
        kondisiAwal();
        
        
        executor.execute(this);
        
    }
    
    
    
    private void kondisiAwal() {
        transaksiToolbarPanel.kondisiAwal();
        keranjangPanel.enableForm(false);
        enableForm(false);
        clearTable();
        clearForm();
    }

    private void kondisiSimpan() {
        transaksiToolbarPanel.kondisiSimpan();
        keranjangPanel.kondisiAwal();
        enableForm(true);
    }

    private void clearTable() {
        listDetailPenjualan = new ArrayList<PenjualanDetail>();
        refreshTable();
    }

    private void initTable() {
        refreshTable();
        tablePenjualan.getSelectionModel().addListSelectionListener(new PenjualanDetailSelectionListener());
    }

    private void refreshTable() {
        tablePenjualan.setModel(new TablePenjualanModel(listDetailPenjualan));
        for (int i = 0; i < tablePenjualan.getColumnCount(); i++) {
            tablePenjualan.getColumnModel().getColumn(i).setHeaderRenderer(new TableHeaderRenderer());
        }
    }

    private BigDecimal getTotalHarga() {
        BigDecimal total = new BigDecimal(0);
        for (PenjualanDetail penjualanDetail : listDetailPenjualan) {
            total = total.add(penjualanDetail.getSubTotal());
        }
        return total;
    }

    private void refreshTotalLabel() {
        NumberFormat formatter = NumberFormat.getInstance();
        labelTotal.setText(formatter.format(getTotalHarga().setScale(0, RoundingMode.HALF_EVEN)));
    }

    private boolean validateForm() {
        if (textIdProduk.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "ID Produk tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (textKuantitas.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Kuantitas tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (textIdMember.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "ID Member tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean productExists() {
        for (PenjualanDetail penjualanDetail : listDetailPenjualan) {
            if (penjualanDetail.getProduk().getIdProduk().equalsIgnoreCase(textIdProduk.getText())) {
                return true;
            }
        }
        return false;
    }

    private boolean stockAvailable(String idProduk, Integer kuantitas, String size) {
        Produk p = Main.getProdukService().getProduk(idProduk);
        Integer stockGudang = Main.getProdukService().getStockSize(idProduk, size);
        if (stockGudang < kuantitas) {
            return false;
        }
        return true;
    }

    private boolean addDetailPenjualan() {
        PenjualanDetail detailPenjualan = new PenjualanDetail();
        detailPenjualan.setIdPenjualan(textIdPenjualan.getText());
        detailPenjualan.setIdDetailPenjualan(textIdPenjualan.getText() + (listDetailPenjualan.size() + 1));
        detailPenjualan.setProduk(Main.getProdukService().getProduk(textIdProduk.getText()));
        detailPenjualan.setSize(comboSize.getSelectedItem().toString());
        detailPenjualan.setKuantitas(Integer.parseInt(textKuantitas.getText()));
        detailPenjualan.setHarga(new BigDecimal(new Double(textHarga.getValue().toString())));
        detailPenjualan.setSubTotal(new BigDecimal(new Double(textSubTotal.getValue().toString())));
        if (stockAvailable(detailPenjualan.getProduk().getIdProduk(), detailPenjualan.getKuantitas(), detailPenjualan.getSize())) {
            if (!productExists()) {
                listDetailPenjualan.add(detailPenjualan);
            } else {
                int index = 0;
                for (PenjualanDetail penjualanDetail : listDetailPenjualan) {
                    if (penjualanDetail.getProduk().getIdProduk().equalsIgnoreCase(textIdProduk.getText())) {
                        break;
                    }
                    index++;
                }
                detailPenjualan.setKuantitas(listDetailPenjualan.get(index).getKuantitas() + detailPenjualan.getKuantitas());
                detailPenjualan.setSubTotal(listDetailPenjualan.get(index).getSubTotal().add(detailPenjualan.getSubTotal()));
                listDetailPenjualan.set(index, detailPenjualan);
            }
            refreshTable();
            return true;
        } else {
            JOptionPane.showMessageDialog(this, "Maaf, stock masih kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private void loadMember(String idMember) {
        Member member = Main.getMemberService().getMember(idMember);
        if (member != null) {
            textIdMember.setText(member.getIdMember());
            textNamaMember.setText(member.getNamaLengkap());
        } else {
            JOptionPane.showMessageDialog(this, "Member tidak terdaftar!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadProduk(String idProduk) {
        Produk produk = Main.getProdukService().getProduk(idProduk);
        if (produk != null) {
            textIdProduk.setText(produk.getIdProduk());
            textNamaProduk.setText(produk.getNamaProduk());
            textHarga.setValue(produk.getHargaJual());
            textSubTotal.setValue(produk.getHargaJual());
            textStockSize.setText(produk.getStockS().toString());
            textKuantitas.setText("1");
        } else {
            JOptionPane.showMessageDialog(this, "Produk tidak tersedia!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadModelToForm() {
        textIdProduk.setText(penjualan.getProduk().getIdProduk());
        textNamaProduk.setText(penjualan.getProduk().getNamaProduk());
        textHarga.setValue(penjualan.getHarga());
        textSubTotal.setValue(penjualan.getSubTotal());
        textKuantitas.setText(penjualan.getKuantitas() + "");
    }

    private void setSubTotal() {
        if (textHarga.getValue() != null) {
            Integer kuantitas = Integer.parseInt(textKuantitas.getText());
            Double harga = new Double(textHarga.getValue().toString());
            textSubTotal.setValue(new BigDecimal(kuantitas * harga));
        }
    }
    
    private void setDiskon(){
        NumberFormat formatter = NumberFormat.getInstance();
        //if(!"0".equals(labelTotal.getText())){
            Double diskon = new Double(textDiskon.getText());
            Double total = Double.parseDouble(getTotalHarga().toString());
            BigDecimal grandTotal;
            grandTotal = new BigDecimal(total-(total * (diskon/100)));
            labelTotal1.setText(formatter.format(grandTotal.setScale(0, RoundingMode.HALF_EVEN)));
            
        //}
    }

    private void enableForm(boolean status) {
        textIdProduk.setEnabled(status);
        buttonCariProduk.setEnabled(status);
        textKuantitas.setEnabled(status);
        buttonHitung.setEnabled(status);
        jdcTanggal.setEnabled(status);
        textIdMember.setEnabled(status);
        jdcTanggal.setEnabled(status);
        textStockSize.setEnabled(status);
        comboSize.setEnabled(status);
        //running=status;
    }

    private void kondisiAwalKeranjang() {
        keranjangPanel.kondisiAwal();
        clearForm();
        enableForm(true);
    }

    private void kondisiUbahOrHapusKeranjang() {
        keranjangPanel.kondisiUbahOrHapus();
        enableForm(true);
        textIdProduk.setEnabled(false);
        buttonCariProduk.setEnabled(false);
    }

    private void clearForm() {
        textIdProduk.setText("");
        textNamaProduk.setText("");
        textKuantitas.setText("");
        textHarga.setValue(null);
        textSubTotal.setValue(null);
        labelTotal.setText("0");
        labelTotal1.setText("0");
        textStockSize.setText("0");
        comboSize.setSelectedIndex(0);
        //textIdMember.setText("");
        //textNamaMember.setText("");
    }

    private void initListeners() {

        keranjangPanel.getButtonTambahKeranjang().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateForm()) {
                    if (addDetailPenjualan()) {
                        clearForm();
                        refreshTotalLabel();
                        setDiskon();
                    }
                }
            }
        });

        keranjangPanel.getButtonEditKeranjang().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = tablePenjualan.getSelectedRow();
                Integer kuantitas = Integer.parseInt(textKuantitas.getText());
                PenjualanDetail penjualanDetail = listDetailPenjualan.get(index);
                if (stockAvailable(penjualanDetail.getProduk().getIdProduk(), kuantitas, comboSize.getSelectedItem().toString())) {
                    penjualanDetail.setKuantitas(Integer.parseInt(textKuantitas.getText()));
                    penjualanDetail.setSubTotal(new BigDecimal(new Double(textSubTotal.getValue().toString())));
                    listDetailPenjualan.set(index, penjualanDetail);
                    kondisiAwalKeranjang();
                    refreshTable();
                    refreshTotalLabel();
                    setDiskon();
                } else {
                    JOptionPane.showMessageDialog(PenjualanPanel.this, "Maaf, stock masih kosong!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        keranjangPanel.getButtonHapusKeranjang().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (penjualan != null) {
                    listDetailPenjualan.remove(penjualan);
                    kondisiAwalKeranjang();
                    refreshTable();
                    refreshTotalLabel();
                    setDiskon();
                }
            }
        });
        keranjangPanel.getButtonBatal().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
                tablePenjualan.getSelectionModel().clearSelection();
                kondisiAwalKeranjang();
            }
        });

        textKuantitas.getDocument().addDocumentListener(new DocumentListener() {
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
                try {
                    String text = e.getDocument().getText(0, e.getDocument().getLength());
                    if (!text.trim().equals("")) {
                        setSubTotal();
                    }
                } catch (BadLocationException ex) {
                    Logger.getLogger(PenjualanPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        transaksiToolbarPanel.getButtonTambah().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                kondisiSimpan();
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyhhmmss");
                textIdPenjualan.setText(sdf.format(date.getTime()));
                //setNomorPenjualan(textIdPenjualan.getText().toString());
            }
        });

        transaksiToolbarPanel.getButtonSimpan().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listDetailPenjualan.isEmpty()) {
                    JOptionPane.showMessageDialog(PenjualanPanel.this, "Tidak ada barang di dalam keranjang", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    Penjualan p = new Penjualan();
                    p.setIdPenjualan(textIdPenjualan.getText());
                    //p.setTanggal(Calendar.getInstance().getTime());
                    p.setTanggal(new Date(jdcTanggal.getDate().getTime()));
                    p.setTotal(getTotalHarga());
                    p.setDiskon(BigDecimal.valueOf(Double.parseDouble(textDiskon.getText())));
                    p.setIdUser(Main.getFrameUtama().getActiveIdUser());
                    p.setIdMember(textIdMember.getText());
                    Main.getTransaksiPenjualanService().save(p, listDetailPenjualan);
                    transaksiToolbarPanel.kondisiCetak();
                    textIdMember.setText("");
                    textNamaMember.setText("");
                    
                }
            }
        });

        transaksiToolbarPanel.getButtonBatal().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                kondisiAwal();
                textIdPenjualan.setText("");
            }
        });

        transaksiToolbarPanel.getButtonCetak().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new NotaPenjualanReportPanel(textIdPenjualan.getText());
                kondisiAwal();
                textIdPenjualan.setText("");
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
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        buttonGroup5 = new javax.swing.ButtonGroup();
        buttonGroup6 = new javax.swing.ButtonGroup();
        darkgrayPanel1 = new paket.launk.java.container.DarkgrayPanel();
        panelGlass1 = new paket.launk.java.container.PanelGlass();
        aerithLabel1 = new aerith.swing.AerithLabel();
        textIdPenjualan = new aerith.swing.AerithTextField();
        aerithLabel2 = new aerith.swing.AerithLabel();
        aerithLabel3 = new aerith.swing.AerithLabel();
        jdcTanggal = new com.toedter.calendar.JDateChooser();
        aerithLabel4 = new aerith.swing.AerithLabel();
        textHarga = new paket.launk.java.controller.FormattedField();
        textIdProduk = new paket.launk.java.controller.OvalTextField();
        aerithLabel5 = new aerith.swing.AerithLabel();
        textKuantitas = new paket.launk.java.controller.OvalTextField();
        aerithLabel6 = new aerith.swing.AerithLabel();
        textSubTotal = new paket.launk.java.controller.FormattedField();
        buttonCariProduk = new paket.launk.java.controller.MenuButton();
        aerithLabel7 = new aerith.swing.AerithLabel();
        labelTotal = new aerith.swing.AerithLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablePenjualan = new javax.swing.JTable();
        keranjangPanel = new com.retail.ui.toolbar.KeranjangPanel();
        buttonHitung = new paket.launk.java.controller.MenuButton();
        transaksiToolbarPanel = new com.retail.ui.toolbar.TransaksiToolbarPanel();
        aerithLabel8 = new aerith.swing.AerithLabel();
        textIdMember = new paket.launk.java.controller.OvalTextField();
        textNamaMember = new paket.launk.java.controller.OvalTextField();
        textStockSize = new paket.launk.java.controller.OvalTextField();
        buttonPindai = new paket.launk.java.controller.ButtonGlass();
        aerithLabel10 = new aerith.swing.AerithLabel();
        aerithLabel11 = new aerith.swing.AerithLabel();
        textDiskon = new paket.launk.java.controller.OvalTextField();
        buttonDiskon = new paket.launk.java.controller.ButtonGlass();
        aerithLabel12 = new aerith.swing.AerithLabel();
        labelTotal1 = new aerith.swing.AerithLabel();
        comboSize = new javax.swing.JComboBox();
        aerithLabel13 = new aerith.swing.AerithLabel();
        aerithLabel14 = new aerith.swing.AerithLabel();
        aerithLabel15 = new aerith.swing.AerithLabel();
        textNamaProduk = new paket.launk.java.controller.OvalTextField();

        setBackground(new java.awt.Color(51, 51, 51));
        setClosable(true);
        setForeground(new java.awt.Color(0, 0, 0));
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Penjualan");
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosing(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });
        getContentPane().setLayout(new java.awt.GridBagLayout());

        darkgrayPanel1.setLayout(new java.awt.BorderLayout());

        panelGlass1.setLayout(new java.awt.GridBagLayout());

        aerithLabel1.setText("No. Nota");
        aerithLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 28;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 20, 0, 0);
        panelGlass1.add(aerithLabel1, gridBagConstraints);

        textIdPenjualan.setEnabled(false);
        textIdPenjualan.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.ipadx = 196;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 4, 0, 0);
        panelGlass1.add(textIdPenjualan, gridBagConstraints);

        aerithLabel2.setText("Tanggal");
        aerithLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipady = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 5, 0, 0);
        panelGlass1.add(aerithLabel2, gridBagConstraints);

        aerithLabel3.setText("ID Produk");
        aerithLabel3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.ipadx = 19;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 20, 0, 0);
        panelGlass1.add(aerithLabel3, gridBagConstraints);

        jdcTanggal.setDate(new java.util.Date());
        jdcTanggal.setDateFormatString("dd MMMM yyyy"); // NOI18N
        jdcTanggal.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.ipadx = 152;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(7, 18, 0, 0);
        panelGlass1.add(jdcTanggal, gridBagConstraints);

        aerithLabel4.setText("Harga");
        aerithLabel4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 46;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 20, 0, 0);
        panelGlass1.add(aerithLabel4, gridBagConstraints);

        textHarga.setCaretColor(new java.awt.Color(255, 255, 255));
        textHarga.setEnabled(false);
        textHarga.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        textHarga.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 184;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 4, 0, 0);
        panelGlass1.add(textHarga, gridBagConstraints);

        textIdProduk.setCaretColor(new java.awt.Color(255, 255, 255));
        textIdProduk.setNextFocusableComponent(textKuantitas);
        textIdProduk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textIdProdukActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.ipadx = 184;
        gridBagConstraints.ipady = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 4, 0, 0);
        panelGlass1.add(textIdProduk, gridBagConstraints);

        aerithLabel5.setText("Kuantitas");
        aerithLabel5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 21;
        gridBagConstraints.ipady = 14;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 20, 0, 0);
        panelGlass1.add(aerithLabel5, gridBagConstraints);

        textKuantitas.setDocument(new NumberValidator(3, true));
        textKuantitas.setCaretColor(new java.awt.Color(255, 255, 255));
        textKuantitas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textKuantitasActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 184;
        gridBagConstraints.ipady = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(9, 4, 0, 0);
        panelGlass1.add(textKuantitas, gridBagConstraints);

        aerithLabel6.setText("Sub Total");
        aerithLabel6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.gridheight = 6;
        gridBagConstraints.ipadx = 22;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 20, 0, 0);
        panelGlass1.add(aerithLabel6, gridBagConstraints);

        textSubTotal.setCaretColor(new java.awt.Color(255, 255, 255));
        textSubTotal.setEnabled(false);
        textSubTotal.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        textSubTotal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.ipadx = 184;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 4, 0, 0);
        panelGlass1.add(textSubTotal, gridBagConstraints);

        buttonCariProduk.setText("...");
        buttonCariProduk.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        buttonCariProduk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCariProdukActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(19, 18, 0, 0);
        panelGlass1.add(buttonCariProduk, gridBagConstraints);

        aerithLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        aerithLabel7.setText("Total  Netto: Rp. ");
        aerithLabel7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 19;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 0);
        panelGlass1.add(aerithLabel7, gridBagConstraints);

        labelTotal.setText("0");
        labelTotal.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        labelTotal.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.ipadx = 154;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelGlass1.add(labelTotal, gridBagConstraints);

        jScrollPane1.setOpaque(false);
        jScrollPane1.setViewport(new ViewPortGlass());

        tablePenjualan.setBackground(new java.awt.Color(255, 255, 255, 0));
        tablePenjualan.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tablePenjualan.setForeground(new java.awt.Color(255, 255, 255));
        tablePenjualan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tablePenjualan.setGridColor(new java.awt.Color(255, 255, 255));
        tablePenjualan.setOpaque(false);
        tablePenjualan.setSelectionBackground(new java.awt.Color(51, 52, 255));
        tablePenjualan.setSelectionForeground(new java.awt.Color(0, 0, 0));
        tablePenjualan.setShowHorizontalLines(false);
        tablePenjualan.setShowVerticalLines(false);
        tablePenjualan.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tablePenjualan);
        webcam = Webcam.getWebcams().get(0);
        webcam.setViewSize(size);
        panel = new WebcamPanel(webcam);
        gridBagConstraints.gridx = 20;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 16;
        //gridBagConstraints.gridwidth = 40;
        gridBagConstraints.ipadx = 400;
        gridBagConstraints.weighty = 1.0;
        //gridBagConstraints.weightx = 1.0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_END;
        panelGlass1.add(panel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 21;
        gridBagConstraints.gridwidth = 17;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 850;
        gridBagConstraints.ipady = 197;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(17, 10, 0, 0);
        panelGlass1.add(jScrollPane1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 10;
        gridBagConstraints.gridheight = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 1, 0, 0);
        panelGlass1.add(keranjangPanel, gridBagConstraints);

        buttonHitung.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/calculator.png"))); // NOI18N
        buttonHitung.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        buttonHitung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonHitungActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 16;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.ipadx = 26;
        gridBagConstraints.ipady = 40;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 6, 0, 0);
        panelGlass1.add(buttonHitung, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 60;
        gridBagConstraints.ipadx = 465;
        gridBagConstraints.ipady = -9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 10, 0, 10);
        panelGlass1.add(transaksiToolbarPanel, gridBagConstraints);

        aerithLabel8.setText("ID Member");
        aerithLabel8.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.ipadx = 11;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(7, 5, 0, 0);
        panelGlass1.add(aerithLabel8, gridBagConstraints);

        textIdMember.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                textIdMemberMouseClicked(evt);
            }
        });
        textIdMember.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textIdMemberActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 79;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(9, 4, 0, 0);
        panelGlass1.add(textIdMember, gridBagConstraints);

        textNamaMember.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.ipadx = 145;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 4, 0, 0);
        panelGlass1.add(textNamaMember, gridBagConstraints);

        textStockSize.setCaretColor(new java.awt.Color(255, 255, 255));
        textStockSize.setEnabled(false);
        textStockSize.setFocusable(false);
        textStockSize.setNextFocusableComponent(textKuantitas);
        textStockSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textStockSizeActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 133;
        panelGlass1.add(textStockSize, gridBagConstraints);

        buttonPindai.setText("...");
        buttonPindai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonPindaiActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 11;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 6, 0, 0);
        panelGlass1.add(buttonPindai, gridBagConstraints);

        aerithLabel10.setText("Diskon (%)");
        aerithLabel10.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 16;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.ipadx = 15;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(7, 18, 0, 0);
        panelGlass1.add(aerithLabel10, gridBagConstraints);

        aerithLabel11.setText("Member");
        aerithLabel11.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 29;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 4, 0, 0);
        panelGlass1.add(aerithLabel11, gridBagConstraints);

        textDiskon.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 16;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 54;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 18, 0, 0);
        panelGlass1.add(textDiskon, gridBagConstraints);

        buttonDiskon.setText("Hitung");
        buttonDiskon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonDiskonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 16;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.ipadx = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(7, 18, 0, 0);
        panelGlass1.add(buttonDiskon, gridBagConstraints);

        aerithLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        aerithLabel12.setText("Total: Rp. ");
        aerithLabel12.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        panelGlass1.add(aerithLabel12, gridBagConstraints);

        labelTotal1.setText("0");
        labelTotal1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        labelTotal1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 19;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.ipadx = 174;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelGlass1.add(labelTotal1, gridBagConstraints);

        comboSize.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "S", "M", "L", "XL" }));
        comboSize.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboSizeItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 4, 0, 0);
        panelGlass1.add(comboSize, gridBagConstraints);

        aerithLabel13.setText("Produk");
        aerithLabel13.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 37;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 20, 0, 0);
        panelGlass1.add(aerithLabel13, gridBagConstraints);

        aerithLabel14.setText("Size");
        aerithLabel14.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 37;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 20, 0, 0);
        panelGlass1.add(aerithLabel14, gridBagConstraints);

        aerithLabel15.setText("Size");
        aerithLabel15.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 37;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 20, 0, 0);
        panelGlass1.add(aerithLabel15, gridBagConstraints);

        textNamaProduk.setCaretColor(new java.awt.Color(255, 255, 255));
        textNamaProduk.setEnabled(false);
        textNamaProduk.setNextFocusableComponent(textKuantitas);
        textNamaProduk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textNamaProdukActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 184;
        gridBagConstraints.ipady = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 4, 0, 0);
        panelGlass1.add(textNamaProduk, gridBagConstraints);

        darkgrayPanel1.add(panelGlass1, java.awt.BorderLayout.CENTER);

        getContentPane().add(darkgrayPanel1, new java.awt.GridBagConstraints());

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here:
        FrameUtama.penjualanPanel = null;
        //running = false;
        webcam.close();
        
        dispose();
        
    }//GEN-LAST:event_formInternalFrameClosing

    private void textIdProdukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textIdProdukActionPerformed
        // TODO add your handling code here:
        loadProduk(textIdProduk.getText());
    }//GEN-LAST:event_textIdProdukActionPerformed

    private void textKuantitasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textKuantitasActionPerformed
        // TODO add your handling code here:
        setSubTotal();
    }//GEN-LAST:event_textKuantitasActionPerformed

    private void buttonCariProdukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCariProdukActionPerformed
        // TODO add your handling code here:
//        String prodId = new ScanQRBarcode().getCodeQRBarcode();
//        if (prodId != null) {
//            loadProduk(prodId);
//        } else {
//            JOptionPane.showMessageDialog(null, "Kode Kosong");
//        }
        Produk produk = new SearchProdukPanel().getProduk();
        if (produk != null) {
            loadProduk(produk.getIdProduk());
        }
    }//GEN-LAST:event_buttonCariProdukActionPerformed

    private void buttonHitungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonHitungActionPerformed
        // TODO add your handling code here:
        new CalculatorPanel(getTotalHarga()).setVisible(true);
    }//GEN-LAST:event_buttonHitungActionPerformed

    private void textIdMemberMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_textIdMemberMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_textIdMemberMouseClicked

    private void textIdMemberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textIdMemberActionPerformed
        // TODO add your handling code here:
        loadMember(textIdMember.getText());
    }//GEN-LAST:event_textIdMemberActionPerformed

    private void textStockSizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textStockSizeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textStockSizeActionPerformed

    private void buttonPindaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonPindaiActionPerformed
        // TODO add your handling code here:
//        String memberId = new ScanQRBarcode().getCodeQRBarcode();
//        if (memberId != null) {
//            loadMember(memberId);
//        } else {
//            JOptionPane.showMessageDialog(null, "Kode Kosong");
//        }
        Member member = new SearchMemberPanel().getMember();
        if (member != null) {
            loadMember(member.getIdMember());
        }
    }//GEN-LAST:event_buttonPindaiActionPerformed

    private void buttonDiskonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonDiskonActionPerformed
        // TODO add your handling code here:
        setDiskon();
        
        
    }//GEN-LAST:event_buttonDiskonActionPerformed

    private void textNamaProdukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textNamaProdukActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textNamaProdukActionPerformed

    private void comboSizeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboSizeItemStateChanged
        // TODO add your handling code here:
        textStockSize.setText(Main.getProdukService().getStockSize(textIdProduk.getText(), comboSize.getSelectedItem().toString()).toString());
        
    }//GEN-LAST:event_comboSizeItemStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private aerith.swing.AerithLabel aerithLabel1;
    private aerith.swing.AerithLabel aerithLabel10;
    private aerith.swing.AerithLabel aerithLabel11;
    private aerith.swing.AerithLabel aerithLabel12;
    private aerith.swing.AerithLabel aerithLabel13;
    private aerith.swing.AerithLabel aerithLabel14;
    private aerith.swing.AerithLabel aerithLabel15;
    private aerith.swing.AerithLabel aerithLabel2;
    private aerith.swing.AerithLabel aerithLabel3;
    private aerith.swing.AerithLabel aerithLabel4;
    private aerith.swing.AerithLabel aerithLabel5;
    private aerith.swing.AerithLabel aerithLabel6;
    private aerith.swing.AerithLabel aerithLabel7;
    private aerith.swing.AerithLabel aerithLabel8;
    private paket.launk.java.controller.MenuButton buttonCariProduk;
    private paket.launk.java.controller.ButtonGlass buttonDiskon;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.ButtonGroup buttonGroup5;
    private javax.swing.ButtonGroup buttonGroup6;
    private paket.launk.java.controller.MenuButton buttonHitung;
    private paket.launk.java.controller.ButtonGlass buttonPindai;
    private javax.swing.JComboBox comboSize;
    private paket.launk.java.container.DarkgrayPanel darkgrayPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private com.toedter.calendar.JDateChooser jdcTanggal;
    private com.retail.ui.toolbar.KeranjangPanel keranjangPanel;
    private aerith.swing.AerithLabel labelTotal;
    private aerith.swing.AerithLabel labelTotal1;
    private paket.launk.java.container.PanelGlass panelGlass1;
    private javax.swing.JTable tablePenjualan;
    private paket.launk.java.controller.OvalTextField textDiskon;
    private paket.launk.java.controller.FormattedField textHarga;
    private paket.launk.java.controller.OvalTextField textIdMember;
    private aerith.swing.AerithTextField textIdPenjualan;
    public paket.launk.java.controller.OvalTextField textIdProduk;
    private paket.launk.java.controller.OvalTextField textKuantitas;
    private paket.launk.java.controller.OvalTextField textNamaMember;
    public paket.launk.java.controller.OvalTextField textNamaProduk;
    public paket.launk.java.controller.OvalTextField textStockSize;
    private paket.launk.java.controller.FormattedField textSubTotal;
    private com.retail.ui.toolbar.TransaksiToolbarPanel transaksiToolbarPanel;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run() {
        do {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Result result = null;
            BufferedImage image = null;
            

            if (webcam.isOpen()) {

                if ((image = webcam.getImage()) == null) {
                    continue;
                }

                LuminanceSource source = new BufferedImageLuminanceSource(image);
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

                try {
                    result = new MultiFormatReader().decode(bitmap);
                } catch (NotFoundException e) {
                    // fall thru, it means there is no QR code in image
                }
            }

            if (result != null) {
                if((textIdProduk.hasFocus()==true)&&(textIdProduk.getText().trim().equals("")))
                {
                    textIdProduk.setText(result.getText());
                    loadProduk(textIdProduk.getText());
                }
                if((textIdMember.hasFocus()==true)&&(textIdMember.getText().trim().equals(""))){
                    textIdMember.setText(result.getText());
                    loadMember(textIdMember.getText());                    
                }
                
            }

        } while (true);
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r, "example-runner");
        t.setDaemon(true);
        return t;
    }

    

    private class TablePenjualanModel extends AbstractTableModel {

        private List<PenjualanDetail> rows;
        private List<String> columns;

        public TablePenjualanModel(List<PenjualanDetail> rows) {
            this.rows = rows;
            columns = new ArrayList<String>();
            columns.add("ID Produk");
            columns.add("Nama Produk");
            columns.add("Kuantitas");
            columns.add("Harga");
            columns.add("Sub Total");
        }

        @Override
        public int getRowCount() {
            return rows.size();
        }

        @Override
        public String getColumnName(int column) {
            return columns.get(column);
        }

        @Override
        public int getColumnCount() {
            return columns.size();
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return rows.get(rowIndex).getProduk().getIdProduk();
                case 1:
                    return rows.get(rowIndex).getProduk().getNamaProduk();
                case 2:
                    return rows.get(rowIndex).getKuantitas();
                case 3:
                    return rows.get(rowIndex).getHarga();
                case 4:
                    return rows.get(rowIndex).getSubTotal();
                default:
                    return "";
            }
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
                case 0:
                case 1:
                    return String.class;
                case 2:
                    return Integer.class;
                case 3:
                case 4:
                    return BigDecimal.class;
                default:
                    return String.class;
            }
        }
    }

    private class PenjualanDetailSelectionListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            int index = tablePenjualan.getSelectedRow();
            if (index >= 0) {
                penjualan = listDetailPenjualan.get(index);
                loadModelToForm();
                kondisiUbahOrHapusKeranjang();
            }
        }
    }
}
