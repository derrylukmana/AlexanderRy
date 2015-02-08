/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ProdukPanel.java
 *
 * Created on 01 Agu 11, 4:53:39
 */
package com.retail.ui.master;

import com.retail.main.Main;
import com.retail.model.Produk;
import com.retail.ui.main.FrameUtama;
import com.retail.ui.transaksi.ScanQRBarcode;
import hauw.util.NumberValidator;
import hauw.widget.TableHeaderRenderer;
import hauw.widget.ViewPortGlass;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
public class ProdukPanel extends javax.swing.JInternalFrame {

    private List<Produk> produks;
    private Produk produk;
    private TableRowSorter sorter;
    
    /** Creates new form ProdukPanel */
    public ProdukPanel() {
        initComponents();
        initTable();
        initVars();
        initListeners();
        kondisiAwal();
    }

    private void initVars() {
        sorter = new TableRowSorter(tableProduk.getModel());
        tableProduk.setRowSorter(sorter);
    }
    
    private void initTable() {
        refreshTable();
        tableProduk.setAutoCreateRowSorter(true);
        tableProduk.getSelectionModel().addListSelectionListener(new ProdukSelectionListener());
    }

    private void refreshTable() {
        produks = Main.getProdukService().getProduk();
        tableProduk.setModel(new TableProdukModel(produks));
        for (int i = 0; i < tableProduk.getColumnCount(); i++) {
            tableProduk.getColumnModel().getColumn(i).setHeaderRenderer(new TableHeaderRenderer());
        }
    }

    private void loadModelToForm() {
        textIdProduk.setText(produk.getIdProduk());
        textNamaProduk.setText(produk.getNamaProduk());
        textStockProdukS.setText(produk.getStockS()+ "");
        textStockProdukM.setText(produk.getStockM().toString());
        textStockProdukL.setText(produk.getStockL().toString());
        textStockProdukXL.setText(produk.getStockXL().toString());
        textStockProdukStockTotal.setText(produk.getStockTotal().toString());
        textHargaPokok.setValue(produk.getHargaPokok());
        textHargaJual.setValue(produk.getHargaJual());
    }

    private Produk loadFormToModel() {
        String idProduk = textIdProduk.getText();
        String namaProduk = textNamaProduk.getText();
        Integer stockS = Integer.parseInt(textStockProdukS.getText());
        Integer stockM = Integer.parseInt(textStockProdukM.getText());
        Integer stockL = Integer.parseInt(textStockProdukL.getText());
        Integer stockXL = Integer.parseInt(textStockProdukXL.getText());
        Integer stockTotal = stockS + stockM + stockL + stockXL ;
        BigDecimal hargaPokok = new BigDecimal(new Double(textHargaPokok.getValue().toString()));
        BigDecimal hargaJual = new BigDecimal(new Double(textHargaJual.getValue().toString()));
        Produk p = new Produk();
        p.setIdProduk(idProduk);
        p.setNamaProduk(namaProduk);
        p.setStockTotal(stockTotal);
        p.setStockS(stockS);
        p.setStockM(stockM);
        p.setStockL(stockL);
        p.setStockXL(stockXL);
        p.setHargaJual(hargaJual);
        p.setHargaPokok(hargaPokok);
        return p;
    }

    private boolean validateForm() {
        if (textIdProduk.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "ID Produk tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (textNamaProduk.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Nama produk tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (textStockProdukS.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Stock produk size S tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (textStockProdukM.getText().trim().equals("")){
            JOptionPane.showMessageDialog(this, "Stock produk size M tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (textStockProdukL.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Stock produk size L tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (textStockProdukXL.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Stock produk size XL tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;    
        } else if (textHargaPokok.getValue() == null) {
            JOptionPane.showMessageDialog(this, "Harga pokok tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (textHargaJual.getValue() == null) {
            JOptionPane.showMessageDialog(this, "Harga jual tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else {
            return true;
        }
    }

    private void clearForm() {
        textIdProduk.setText("");
        textNamaProduk.setText("");
        textStockProdukS.setText("");
        textStockProdukM.setText("");
        textStockProdukL.setText("");
        textStockProdukXL.setText("");
        textStockProdukStockTotal.setText("");
        textHargaJual.setValue(null);
        textHargaPokok.setValue(null);
        textCari.setText("");
    }

    private void enableForm(boolean status) {
        textIdProduk.setEnabled(status);
        textNamaProduk.setEnabled(status);
        textStockProdukS.setEnabled(status);
        textStockProdukM.setEnabled(status);
        textStockProdukL.setEnabled(status);
        textStockProdukXL.setEnabled(status);
        textHargaPokok.setEnabled(status);
        textHargaJual.setEnabled(status);
        tombolPindai.setEnabled(status);
    }

    private void kondisiAwal() {
        toolbarPanel.kondisiAwal();
        clearForm();
        enableForm(false);
        tableProduk.getSelectionModel().clearSelection();
    }

    private void kondisiSimpan() {
        toolbarPanel.kondisiSimpan();
        enableForm(true);
    }

    private void kondisiUbahOrHapus() {
        toolbarPanel.kondisiUbahOrHapus();
        enableForm(true);
        textIdProduk.setEnabled(false);
    }

    private void initListeners() {
        toolbarPanel.getButtonTambah().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                kondisiSimpan();
                textIdProduk.requestFocusInWindow();
            }
        });

        toolbarPanel.getButtonSimpan().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateForm()) {
                    Main.getProdukService().save(loadFormToModel());
                    refreshTable();
                    kondisiAwal();
                }
            }
        });

        toolbarPanel.getButtonUbah().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateForm()) {
                    Main.getProdukService().update(loadFormToModel());
                    refreshTable();
                    kondisiAwal();
                }
            }
        });

        toolbarPanel.getButtonHapus().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (produk != null) {
                    Main.getProdukService().delete(produk);
                    produk = null;
                    refreshTable();
                    kondisiAwal();
                }
            }
        });

        toolbarPanel.getButtonBatal().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                kondisiAwal();
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

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        darkgrayPanel1 = new paket.launk.java.container.DarkgrayPanel();
        panelGlass1 = new paket.launk.java.container.PanelGlass();
        textStockProdukS = new paket.launk.java.controller.OvalTextField();
        textNamaProduk = new paket.launk.java.controller.OvalTextField();
        textIdProduk = new paket.launk.java.controller.OvalTextField();
        textHargaPokok = new paket.launk.java.controller.FormattedField();
        textHargaJual = new paket.launk.java.controller.FormattedField();
        jLabel1 = new javax.swing.JLabel();
        toolbarPanel = new com.retail.ui.toolbar.MasterToolbarPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableProduk = new javax.swing.JTable();
        textCari = new aerith.swing.AerithTextField();
        jLabel6 = new javax.swing.JLabel();
        tombolPindai = new paket.launk.java.controller.ButtonGlass();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        textStockProdukM = new paket.launk.java.controller.OvalTextField();
        jLabel9 = new javax.swing.JLabel();
        textStockProdukL = new paket.launk.java.controller.OvalTextField();
        jLabel10 = new javax.swing.JLabel();
        textStockProdukXL = new paket.launk.java.controller.OvalTextField();
        textStockProdukStockTotal = new paket.launk.java.controller.OvalTextField();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Produk");
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

        textStockProdukS.setDocument(new NumberValidator(4, true));
        textStockProdukS.setCaretColor(new java.awt.Color(255, 255, 255));
        textStockProdukS.setNextFocusableComponent(textHargaPokok);

        textNamaProduk.setCaretColor(new java.awt.Color(255, 255, 255));
        textNamaProduk.setNextFocusableComponent(textStockProdukS);

        textIdProduk.setCaretColor(new java.awt.Color(255, 255, 255));
        textIdProduk.setNextFocusableComponent(textNamaProduk);
        textIdProduk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                textIdProdukMouseClicked(evt);
            }
        });
        textIdProduk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textIdProdukActionPerformed(evt);
            }
        });

        textHargaPokok.setCaretColor(new java.awt.Color(255, 255, 255));
        textHargaPokok.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        textHargaPokok.setNextFocusableComponent(textHargaJual);

        textHargaJual.setCaretColor(new java.awt.Color(255, 255, 255));
        textHargaJual.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        textHargaJual.setNextFocusableComponent(toolbarPanel);
        textHargaJual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textHargaJualActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("ID Produk");

        jLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Nama");

        jLabel3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("S");

        jLabel4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Harga Pokok");

        jLabel5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Harga Jual");

        jScrollPane1.setOpaque(false);
        jScrollPane1.setViewport(new ViewPortGlass());

        tableProduk.setBackground(new java.awt.Color(255, 255, 255, 0));
        tableProduk.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tableProduk.setForeground(new java.awt.Color(255, 255, 255));
        tableProduk.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tableProduk.setFillsViewportHeight(true);
        tableProduk.setGridColor(new java.awt.Color(255, 255, 255));
        tableProduk.setOpaque(false);
        tableProduk.setSelectionBackground(new java.awt.Color(51, 51, 255));
        tableProduk.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jScrollPane1.setViewportView(tableProduk);

        textCari.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        textCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textCariActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Cari:");

        tombolPindai.setText("Pindai");
        tombolPindai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tombolPindaiActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Stock :");

        jLabel8.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("M");

        textStockProdukM.setDocument(new NumberValidator(4, true));
        textStockProdukM.setCaretColor(new java.awt.Color(255, 255, 255));
        textStockProdukM.setNextFocusableComponent(textHargaPokok);

        jLabel9.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("L");

        textStockProdukL.setDocument(new NumberValidator(4, true));
        textStockProdukL.setCaretColor(new java.awt.Color(255, 255, 255));
        textStockProdukL.setNextFocusableComponent(textHargaPokok);

        jLabel10.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("XL");

        textStockProdukXL.setDocument(new NumberValidator(4, true));
        textStockProdukXL.setCaretColor(new java.awt.Color(255, 255, 255));
        textStockProdukXL.setNextFocusableComponent(textHargaPokok);
        textStockProdukXL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textStockProdukXLActionPerformed(evt);
            }
        });

        textStockProdukStockTotal.setDocument(new NumberValidator(4, true));
        textStockProdukStockTotal.setCaretColor(new java.awt.Color(255, 255, 255));
        textStockProdukStockTotal.setEnabled(false);
        textStockProdukStockTotal.setNextFocusableComponent(textHargaPokok);

        org.jdesktop.layout.GroupLayout panelGlass1Layout = new org.jdesktop.layout.GroupLayout(panelGlass1);
        panelGlass1.setLayout(panelGlass1Layout);
        panelGlass1Layout.setHorizontalGroup(
            panelGlass1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelGlass1Layout.createSequentialGroup()
                .addContainerGap()
                .add(panelGlass1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jScrollPane1)
                    .add(panelGlass1Layout.createSequentialGroup()
                        .add(panelGlass1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, toolbarPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(panelGlass1Layout.createSequentialGroup()
                                .add(panelGlass1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                                    .add(jLabel6, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .add(jLabel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                .add(10, 10, 10)
                                .add(panelGlass1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(panelGlass1Layout.createSequentialGroup()
                                        .add(textStockProdukS, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 51, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .add(18, 18, 18)
                                        .add(jLabel8, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 19, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .add(12, 12, 12)
                                        .add(textStockProdukM, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 51, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .add(18, 18, 18)
                                        .add(jLabel9, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 16, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                        .add(textStockProdukL, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 51, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .add(18, 18, 18)
                                        .add(jLabel10)
                                        .add(18, 18, 18)
                                        .add(textStockProdukXL, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 51, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                    .add(panelGlass1Layout.createSequentialGroup()
                                        .add(textCari, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 244, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .add(0, 0, Short.MAX_VALUE))))
                            .add(panelGlass1Layout.createSequentialGroup()
                                .add(panelGlass1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(jLabel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .add(jLabel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .add(jLabel7))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(panelGlass1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(panelGlass1Layout.createSequentialGroup()
                                        .add(panelGlass1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                            .add(panelGlass1Layout.createSequentialGroup()
                                                .add(textIdProduk, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 131, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                                .add(18, 18, 18)
                                                .add(tombolPindai, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                            .add(textNamaProduk, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 318, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                        .add(10, 10, 10)
                                        .add(panelGlass1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                            .add(panelGlass1Layout.createSequentialGroup()
                                                .add(jLabel4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 108, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                .add(textHargaPokok, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 120, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                            .add(panelGlass1Layout.createSequentialGroup()
                                                .add(jLabel5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 96, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                                .add(18, 18, 18)
                                                .add(textHargaJual, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 120, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                                    .add(textStockProdukStockTotal, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 88, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                .add(0, 111, Short.MAX_VALUE)))
                        .addContainerGap())))
        );

        panelGlass1Layout.linkSize(new java.awt.Component[] {jLabel1, jLabel2, jLabel6}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        panelGlass1Layout.setVerticalGroup(
            panelGlass1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelGlass1Layout.createSequentialGroup()
                .addContainerGap()
                .add(toolbarPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(panelGlass1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(textIdProduk, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel4)
                    .add(textHargaPokok, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(tombolPindai, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(18, 18, 18)
                .add(panelGlass1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel2)
                    .add(textNamaProduk, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel5)
                    .add(textHargaJual, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(panelGlass1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(textStockProdukStockTotal, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel7))
                .add(12, 12, 12)
                .add(panelGlass1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(textStockProdukS, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel3)
                    .add(textStockProdukM, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel8)
                    .add(textStockProdukL, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel9)
                    .add(textStockProdukXL, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel10))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(panelGlass1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel6)
                    .add(textCari, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelGlass1Layout.linkSize(new java.awt.Component[] {jLabel1, jLabel2, jLabel3, jLabel4, jLabel5, textHargaJual, textHargaPokok, textIdProduk, textNamaProduk, textStockProdukS}, org.jdesktop.layout.GroupLayout.VERTICAL);

        darkgrayPanel1.add(panelGlass1, java.awt.BorderLayout.CENTER);

        getContentPane().add(darkgrayPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here:
        FrameUtama.produkPanel = null;
        dispose();
    }//GEN-LAST:event_formInternalFrameClosing

    private void textIdProdukMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_textIdProdukMouseClicked
        // TODO add your handling code here:
//        if(textIdProduk.getText().equals("")){
//            String memberId = new ScanQRBarcode().getCodeQRBarcode();
//            if (memberId != null) {
//                textIdProduk.setText(memberId);
//            }else{
//                JOptionPane.showMessageDialog(null, "Kode Kosong");
//            }
//        }
        
    }//GEN-LAST:event_textIdProdukMouseClicked

    private void textIdProdukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textIdProdukActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textIdProdukActionPerformed

    private void tombolPindaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tombolPindaiActionPerformed
        // TODO add your handling code here:
//        if(textIdProduk.getText().equals("")){
            String memberId = new ScanQRBarcode().getCodeQRBarcode();
            if (memberId != null) {
                textIdProduk.setText(memberId);
            }else{
                JOptionPane.showMessageDialog(null, "Kode Kosong");
            }
//        }
    }//GEN-LAST:event_tombolPindaiActionPerformed

    private void textStockProdukXLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textStockProdukXLActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textStockProdukXLActionPerformed

    private void textHargaJualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textHargaJualActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textHargaJualActionPerformed

    private void textCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textCariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textCariActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private paket.launk.java.container.DarkgrayPanel darkgrayPanel1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
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
    private javax.swing.JTable tableProduk;
    private aerith.swing.AerithTextField textCari;
    private paket.launk.java.controller.FormattedField textHargaJual;
    private paket.launk.java.controller.FormattedField textHargaPokok;
    private paket.launk.java.controller.OvalTextField textIdProduk;
    private paket.launk.java.controller.OvalTextField textNamaProduk;
    private paket.launk.java.controller.OvalTextField textStockProdukL;
    private paket.launk.java.controller.OvalTextField textStockProdukM;
    private paket.launk.java.controller.OvalTextField textStockProdukS;
    private paket.launk.java.controller.OvalTextField textStockProdukStockTotal;
    private paket.launk.java.controller.OvalTextField textStockProdukXL;
    private paket.launk.java.controller.ButtonGlass tombolPindai;
    private com.retail.ui.toolbar.MasterToolbarPanel toolbarPanel;
    // End of variables declaration//GEN-END:variables

    private class TableProdukModel extends AbstractTableModel {

        private List<Produk> rows;
        private List<String> columns;

        public TableProdukModel(List<Produk> list) {
            rows = list;
            columns = new ArrayList<String>();
            columns.add("ID Produk");
            columns.add("Nama Produk");
            columns.add("Harga Pokok");
            columns.add("Harga Jual");
            columns.add("Stock");
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
                    return rows.get(rowIndex).getIdProduk();
                case 1:
                    return rows.get(rowIndex).getNamaProduk();
                case 2:
                    return rows.get(rowIndex).getHargaPokok();
                case 3:
                    return rows.get(rowIndex).getHargaJual();
                case 4:
                    return rows.get(rowIndex).getStockTotal();
                case 5:
                    return rows.get(rowIndex).getStockS();
                case 6:
                    return rows.get(rowIndex).getStockM();
                case 7:
                    return rows.get(rowIndex).getStockL();
                case 8:
                    return rows.get(rowIndex).getStockXL();
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
                case 3:
                    return BigDecimal.class;
                case 4:
                    return Integer.class;
                default:
                    return String.class;
            }
        }
    }

    private class ProdukSelectionListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            int index = tableProduk.getSelectedRow();
            if (index >= 0) {
                produk = produks.get(index);
                loadModelToForm();
                kondisiUbahOrHapus();
            }
        }
    }
}
