/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PembelianPanel.java
 *
 * Created on 03 Agu 11, 8:55:56
 */
package com.retail.ui.transaksi;

import com.retail.main.Main;
import com.retail.model.Pembelian;
import com.retail.model.PembelianDetail;
import com.retail.model.Produk;
import com.retail.model.Supplier;
import com.retail.ui.main.FrameUtama;
import com.retail.ui.report.NotaPembelianReportPanel;
import hauw.util.NumberValidator;
import hauw.widget.TableHeaderRenderer;
import hauw.widget.ViewPortGlass;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Hauw
 */
public class PembelianPanel extends javax.swing.JInternalFrame {

    private List<PembelianDetail> listPembelianDetail = new ArrayList<PembelianDetail>();
    private PembelianDetail pembelianDetail;
    private Pembelian pembelian;

    /** Creates new form PembelianPanel */
    public PembelianPanel() {
        initComponents();
        createGUI();
        refreshTable();
        kondisiAwal();
        initListeners();
    }

    private boolean validateForm() {
        if (textKuantitas.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Kuantitas tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (textHargaBeli.getValue() == null) {
            JOptionPane.showMessageDialog(this, "Harga beli tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void clearForm() {
        clearProdukForm();
        textIdPembelian.setText("");
        comboBoxSupplier.setSelectedIndex(0);
    }

    private void clearProdukForm() {
        textIdProduk.setText("");
        textNamaProduk.setText("");
        textKuantitas.setText("");
        textHargaBeli.setValue(null);
        textSubTotal.setValue(null);
        textTotal.setValue(null);
    }

    private void kondisiAwal() {
        toolbarPanel.kondisiAwal();
        keranjangPanel.enableForm(false);
        enableForm(false);
        clearForm();
    }

    private void kondisiSimpan() {
        toolbarPanel.kondisiSimpan();
        keranjangPanel.kondisiAwal();
    }

    private void kondisiUbahOrHapus() {
        keranjangPanel.kondisiUbahOrHapus();
        textKuantitas.setEnabled(true);
        textHargaBeli.setEnabled(true);
        buttonCariProduk.setEnabled(false);
    }

    private void enableForm(boolean status) {
        textHargaBeli.setEnabled(status);
        textIdProduk.setEnabled(status);
        textKuantitas.setEnabled(status);
        textNamaProduk.setEnabled(status);
        buttonCariProduk.setEnabled(status);
        comboBoxSupplier.setEnabled(status);
        jdcTanggal.setEnabled(status);
    }

    private void refreshTotalHarga() {
        textTotal.setValue(getTotalHarga());
    }

    private void loadModelToForm() {
        textIdProduk.setText(pembelianDetail.getProduk().getIdProduk());
        textKuantitas.setText(pembelianDetail.getKuantitas() + "");
        textHargaBeli.setValue(pembelianDetail.getHarga());
        textNamaProduk.setText(pembelianDetail.getProduk().getNamaProduk());
        textSubTotal.setValue(pembelianDetail.getSubTotal());
    }

    private void createGUI() {
        List<Supplier> list = Main.getSupplierService().getSupplier();
        String[] namaSupplier = new String[list.size()];
        for (int i = 0; i < namaSupplier.length; i++) {
            namaSupplier[i] = list.get(i).getNamaSupplier();
        }
        comboBoxSupplier.setModel(new DefaultComboBoxModel(namaSupplier));
    }

    private void refreshTable() {
        tablePembelian.setModel(new PembelianTableModel(listPembelianDetail));
        for (int i = 0; i < tablePembelian.getColumnCount(); i++) {
            tablePembelian.getColumnModel().getColumn(i).setHeaderRenderer(new TableHeaderRenderer());
        }
        tablePembelian.getSelectionModel().addListSelectionListener(new PembelianSelectionListener());
    }

    private PembelianDetail loadFormToModel() {
        pembelian.setIdPembelian(textIdPembelian.getText());
        pembelian.setSupplier(Main.getSupplierService().getSupplierByName(comboBoxSupplier.getSelectedItem().toString()));
        pembelian.setTanggal(new Date());
        pembelian.setIdUser(Main.getFrameUtama().getActiveIdUser());
        BigDecimal harga = new BigDecimal(new Double(textHargaBeli.getValue().toString()));
        Integer kuantitas = Integer.parseInt(textKuantitas.getText());
        PembelianDetail pDetail = new PembelianDetail();
        pDetail.setIdDetailPembelian(textIdPembelian.getText() + (listPembelianDetail.size() + 1));
        pDetail.setHarga(harga);
        pDetail.setKuantitas(kuantitas);
        pDetail.setProduk(Main.getProdukService().getProduk(textIdProduk.getText()));
        pDetail.setSubTotal(harga.multiply(new BigDecimal(kuantitas)));
        pDetail.setPembelian(pembelian);
        return pDetail;
    }
    
    private BigDecimal getTotalHarga() {
        BigDecimal totalHarga = new BigDecimal(0);
        for (PembelianDetail pembelianDetail1 : listPembelianDetail) {
            totalHarga = totalHarga.add(pembelianDetail1.getSubTotal());
        }
        return totalHarga;
    }

    private void initListeners() {
        toolbarPanel.getButtonTambah().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                kondisiSimpan();
                buttonCariProduk.setEnabled(true);
                comboBoxSupplier.setEnabled(true);
                tablePembelian.setEnabled(true);
                textIdPembelian.setText(new Date().getTime() + "");
                pembelian = new Pembelian();
            }
        });

        toolbarPanel.getButtonSimpan().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(listPembelianDetail.isEmpty()) {
                    JOptionPane.showMessageDialog(PembelianPanel.this, "Daftar pembelian tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    pembelian.setTotal(getTotalHarga());
                    Main.getTransaksiPembelianService().save(pembelian, listPembelianDetail);
                    pembelian = null;
                    toolbarPanel.kondisiCetak();
                    enableForm(false);
                    keranjangPanel.enableForm(false);
                    tablePembelian.setEnabled(false);
                    clearProdukForm();
                    tablePembelian.clearSelection();
                }
            }
        });

        toolbarPanel.getButtonBatal().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                kondisiAwal();
                pembelian = null;
                listPembelianDetail.clear();
                refreshTable();
            }
        });

        toolbarPanel.getButtonCetak().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new NotaPembelianReportPanel(textIdPembelian.getText());
                kondisiAwal();
                listPembelianDetail.clear();
                refreshTable();
            }
        });

        keranjangPanel.getButtonTambahKeranjang().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateForm()) {
                    listPembelianDetail.add(loadFormToModel());
                    refreshTable();
                    clearProdukForm();
                    enableForm(false);
                    buttonCariProduk.setEnabled(true);
                    refreshTotalHarga();
                }
            }
        });

        keranjangPanel.getButtonEditKeranjang().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int index = tablePembelian.getSelectedRow();
                listPembelianDetail.set(index, loadFormToModel());
                keranjangPanel.kondisiAwal();
                refreshTable();
                tablePembelian.clearSelection();
                clearProdukForm();
                enableForm(false);
                refreshTotalHarga();
                buttonCariProduk.setEnabled(true);
            }
        });

        keranjangPanel.getButtonHapusKeranjang().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int index = tablePembelian.getSelectedRow();
                listPembelianDetail.remove(index);
                refreshTable();
                tablePembelian.clearSelection();
                clearProdukForm();
                enableForm(false);
                buttonCariProduk.setEnabled(true);
                keranjangPanel.kondisiAwal();
                refreshTotalHarga();
                buttonCariProduk.setEnabled(true);
            }
        });

        keranjangPanel.getButtonBatal().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                keranjangPanel.kondisiAwal();
                tablePembelian.clearSelection();
                clearProdukForm();
                enableForm(false);
                buttonCariProduk.setEnabled(true);
            }
        });

        textKuantitas.addKeyListener(new KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent e) {
                String text = textKuantitas.getText().trim();
                if (!text.equals("")) {
                    Integer kuantitas = Integer.parseInt(text);
                    BigDecimal harga = new BigDecimal(new Double(textHargaBeli.getValue().toString()));
                    BigDecimal total = harga.multiply(new BigDecimal(kuantitas));
                    textSubTotal.setValue(total);
                } else {
                    textSubTotal.setValue(0);
                }
            }
        });

        textHargaBeli.addKeyListener(new KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent e) {
                String text = textHargaBeli.getText().trim();
                if (!text.equals("")) {
                    try {
                        textHargaBeli.commitEdit();
                        Integer kuantitas = Integer.parseInt(textKuantitas.getText());
                        BigDecimal harga = new BigDecimal(new Double(textHargaBeli.getValue().toString()));
                        BigDecimal total = harga.multiply(new BigDecimal(kuantitas));
                        textSubTotal.setValue(total);
                    } catch (ParseException ex) {
                        Logger.getLogger(PembelianPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    textSubTotal.setValue(0);
                }
            }
        });
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        darkgrayPanel1 = new paket.launk.java.container.DarkgrayPanel();
        panelLine1 = new paket.launk.java.container.PanelLine();
        toolbarPanel = new com.retail.ui.toolbar.TransaksiToolbarPanel();
        aerithLabel1 = new aerith.swing.AerithLabel();
        textIdPembelian = new aerith.swing.AerithTextField();
        aerithLabel2 = new aerith.swing.AerithLabel();
        jdcTanggal = new com.toedter.calendar.JDateChooser();
        aerithLabel7 = new aerith.swing.AerithLabel();
        comboBoxSupplier = new aerith.swing.AerithComboBox();
        panelProduk = new paket.launk.java.container.PanelOval();
        textIdProduk = new paket.launk.java.controller.OvalTextField();
        aerithLabel3 = new aerith.swing.AerithLabel();
        aerithLabel4 = new aerith.swing.AerithLabel();
        textNamaProduk = new paket.launk.java.controller.OvalTextField();
        aerithLabel5 = new aerith.swing.AerithLabel();
        aerithLabel6 = new aerith.swing.AerithLabel();
        textKuantitas = new paket.launk.java.controller.OvalTextField();
        textHargaBeli = new paket.launk.java.controller.FormattedField();
        buttonCariProduk = new paket.launk.java.controller.MenuButton();
        keranjangPanel = new com.retail.ui.toolbar.KeranjangPanel();
        aerithLabel8 = new aerith.swing.AerithLabel();
        textSubTotal = new javax.swing.JFormattedTextField();
        aerithLabel9 = new aerith.swing.AerithLabel();
        textTotal = new javax.swing.JFormattedTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablePembelian = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Pembelian");
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

        darkgrayPanel1.setLayout(new java.awt.BorderLayout());

        aerithLabel1.setText("ID Pembelian");
        aerithLabel1.setFont(new java.awt.Font("Arial", 1, 14));

        textIdPembelian.setEnabled(false);
        textIdPembelian.setFont(new java.awt.Font("Arial", 0, 14));

        aerithLabel2.setText("Tanggal");
        aerithLabel2.setFont(new java.awt.Font("Arial", 1, 14));

        jdcTanggal.setDate(new java.util.Date());
        jdcTanggal.setDateFormatString("dd MMMM yyyy"); // NOI18N
        jdcTanggal.setEnabled(false);
        jdcTanggal.setFont(new java.awt.Font("Tahoma", 1, 12));

        aerithLabel7.setText("Supplier");
        aerithLabel7.setFont(new java.awt.Font("Arial", 1, 14));

        comboBoxSupplier.setFont(new java.awt.Font("Arial", 1, 14));

        panelProduk.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1), "Produk", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(255, 255, 255))); // NOI18N

        textIdProduk.setCaretColor(new java.awt.Color(255, 255, 255));

        aerithLabel3.setText("ID Produk");
        aerithLabel3.setFont(new java.awt.Font("Arial", 1, 14));

        aerithLabel4.setText("Nama");
        aerithLabel4.setFont(new java.awt.Font("Arial", 1, 14));

        textNamaProduk.setCaretColor(new java.awt.Color(255, 255, 255));

        aerithLabel5.setText("Harga Beli");
        aerithLabel5.setFont(new java.awt.Font("Arial", 1, 14));

        aerithLabel6.setText("Kuantitas");
        aerithLabel6.setFont(new java.awt.Font("Arial", 1, 14));

        textKuantitas.setDocument(new NumberValidator(5, true));
        textKuantitas.setCaretColor(new java.awt.Color(255, 255, 255));

        textHargaBeli.setCaretColor(new java.awt.Color(255, 255, 255));
        textHargaBeli.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));

        org.jdesktop.layout.GroupLayout panelProdukLayout = new org.jdesktop.layout.GroupLayout(panelProduk);
        panelProduk.setLayout(panelProdukLayout);
        panelProdukLayout.setHorizontalGroup(
            panelProdukLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelProdukLayout.createSequentialGroup()
                .addContainerGap()
                .add(panelProdukLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(aerithLabel5, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(aerithLabel6, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(aerithLabel3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(aerithLabel4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(panelProdukLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, textNamaProduk, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, textKuantitas, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, textHargaBeli, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(textIdProduk, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelProdukLayout.setVerticalGroup(
            panelProdukLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelProdukLayout.createSequentialGroup()
                .addContainerGap()
                .add(panelProdukLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(aerithLabel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 25, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(textIdProduk, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(panelProdukLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(aerithLabel4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 25, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(textNamaProduk, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(panelProdukLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(aerithLabel6, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 25, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(textKuantitas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(panelProdukLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(aerithLabel5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 25, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(textHargaBeli, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        buttonCariProduk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/search.png"))); // NOI18N
        buttonCariProduk.setText("Cari Produk");
        buttonCariProduk.setFont(new java.awt.Font("Tahoma", 1, 12));
        buttonCariProduk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCariProdukActionPerformed(evt);
            }
        });

        aerithLabel8.setText("Sub Total");
        aerithLabel8.setFont(new java.awt.Font("Arial", 1, 18));

        textSubTotal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getCurrencyInstance())));
        textSubTotal.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        textSubTotal.setEnabled(false);
        textSubTotal.setFont(new java.awt.Font("Tahoma", 1, 14));

        aerithLabel9.setText("Total");
        aerithLabel9.setFont(new java.awt.Font("Arial", 1, 18));

        textTotal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getCurrencyInstance())));
        textTotal.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        textTotal.setEnabled(false);
        textTotal.setFont(new java.awt.Font("Tahoma", 1, 14));

        jScrollPane1.setOpaque(false);
        jScrollPane1.setViewport(new ViewPortGlass());

        tablePembelian.setBackground(new java.awt.Color(255, 255, 255, 0));
        tablePembelian.setForeground(new java.awt.Color(255, 255, 255));
        tablePembelian.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tablePembelian.setFillsViewportHeight(true);
        tablePembelian.setGridColor(new java.awt.Color(255, 255, 255));
        tablePembelian.setOpaque(false);
        tablePembelian.setSelectionBackground(new java.awt.Color(51, 51, 255));
        tablePembelian.setSelectionForeground(new java.awt.Color(0, 0, 0));
        tablePembelian.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tablePembelian);

        org.jdesktop.layout.GroupLayout panelLine1Layout = new org.jdesktop.layout.GroupLayout(panelLine1);
        panelLine1.setLayout(panelLine1Layout);
        panelLine1Layout.setHorizontalGroup(
            panelLine1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelLine1Layout.createSequentialGroup()
                .addContainerGap()
                .add(panelLine1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(panelLine1Layout.createSequentialGroup()
                        .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 1073, Short.MAX_VALUE)
                        .addContainerGap())
                    .add(panelLine1Layout.createSequentialGroup()
                        .add(panelLine1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, toolbarPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 1083, Short.MAX_VALUE)
                            .add(panelLine1Layout.createSequentialGroup()
                                .add(aerithLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(18, 18, 18)
                                .add(textIdPembelian, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 188, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(91, 91, 91)
                                .add(aerithLabel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 55, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(18, 18, 18)
                                .add(jdcTanggal, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 200, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(panelLine1Layout.createSequentialGroup()
                                .add(panelLine1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                                    .add(org.jdesktop.layout.GroupLayout.LEADING, panelProduk, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .add(org.jdesktop.layout.GroupLayout.LEADING, panelLine1Layout.createSequentialGroup()
                                        .add(aerithLabel7, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 91, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .add(18, 18, 18)
                                        .add(comboBoxSupplier, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 188, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                                .add(18, 18, 18)
                                .add(panelLine1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(keranjangPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(buttonCariProduk, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                .add(254, 254, 254))
                            .add(panelLine1Layout.createSequentialGroup()
                                .add(aerithLabel8, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(textSubTotal, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 165, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(18, 18, 18)
                                .add(aerithLabel9, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(textTotal, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 165, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                        .add(0, 0, 0))))
        );
        panelLine1Layout.setVerticalGroup(
            panelLine1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelLine1Layout.createSequentialGroup()
                .addContainerGap()
                .add(toolbarPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(panelLine1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jdcTanggal, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 27, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(panelLine1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(aerithLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 27, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(textIdPembelian, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(aerithLabel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 27, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .add(18, 18, 18)
                .add(panelLine1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(aerithLabel7, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(comboBoxSupplier, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 32, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(panelLine1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(panelProduk, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(panelLine1Layout.createSequentialGroup()
                        .add(buttonCariProduk, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 43, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(keranjangPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(panelLine1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(aerithLabel8, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 27, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(textSubTotal)
                    .add(aerithLabel9, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 27, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(textTotal))
                .add(18, 18, 18)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
                .addContainerGap())
        );

        darkgrayPanel1.add(panelLine1, java.awt.BorderLayout.CENTER);

        getContentPane().add(darkgrayPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here:
        FrameUtama.pembelianPanel = null;
        dispose();
    }//GEN-LAST:event_formInternalFrameClosing

    private void buttonCariProdukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCariProdukActionPerformed
        // TODO add your handling code here:
        Produk produk = new SearchProdukPanel().getProduk();
        if (produk != null) {
            textIdProduk.setText(produk.getIdProduk());
            textNamaProduk.setText(produk.getNamaProduk());
            textHargaBeli.setValue(produk.getHargaPokok());
            textHargaBeli.setEnabled(true);
            textKuantitas.setEnabled(true);
            textKuantitas.requestFocusInWindow();
        }
    }//GEN-LAST:event_buttonCariProdukActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private aerith.swing.AerithLabel aerithLabel1;
    private aerith.swing.AerithLabel aerithLabel2;
    private aerith.swing.AerithLabel aerithLabel3;
    private aerith.swing.AerithLabel aerithLabel4;
    private aerith.swing.AerithLabel aerithLabel5;
    private aerith.swing.AerithLabel aerithLabel6;
    private aerith.swing.AerithLabel aerithLabel7;
    private aerith.swing.AerithLabel aerithLabel8;
    private aerith.swing.AerithLabel aerithLabel9;
    private paket.launk.java.controller.MenuButton buttonCariProduk;
    private aerith.swing.AerithComboBox comboBoxSupplier;
    private paket.launk.java.container.DarkgrayPanel darkgrayPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private com.toedter.calendar.JDateChooser jdcTanggal;
    private com.retail.ui.toolbar.KeranjangPanel keranjangPanel;
    private paket.launk.java.container.PanelLine panelLine1;
    private paket.launk.java.container.PanelOval panelProduk;
    private javax.swing.JTable tablePembelian;
    private paket.launk.java.controller.FormattedField textHargaBeli;
    private aerith.swing.AerithTextField textIdPembelian;
    private paket.launk.java.controller.OvalTextField textIdProduk;
    private paket.launk.java.controller.OvalTextField textKuantitas;
    private paket.launk.java.controller.OvalTextField textNamaProduk;
    private javax.swing.JFormattedTextField textSubTotal;
    private javax.swing.JFormattedTextField textTotal;
    private com.retail.ui.toolbar.TransaksiToolbarPanel toolbarPanel;
    // End of variables declaration//GEN-END:variables

    private class PembelianTableModel extends AbstractTableModel {

        private List<PembelianDetail> rows;
        private List<String> columns;

        public PembelianTableModel(List<PembelianDetail> rows) {
            this.rows = rows;
            columns = new ArrayList<String>();
            columns.add("ID Produk");
            columns.add("Nama Produk");
            columns.add("Kuantitas");
            columns.add("Harga Beli");
            columns.add("Sub Total");
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
        public String getColumnName(int column) {
            return columns.get(column);
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

    private class PembelianSelectionListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            int index = tablePembelian.getSelectedRow();
            if (index >= 0) {
                kondisiUbahOrHapus();
                pembelianDetail = listPembelianDetail.get(index);
                loadModelToForm();
            }
        }
    }
}
