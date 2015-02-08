/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * SupplierPanel.java
 *
 * Created on 02 Agu 11, 17:46:05
 */
package com.retail.ui.master;

import com.retail.main.Main;
import com.retail.model.Supplier;
import com.retail.ui.main.FrameUtama;
import hauw.util.PhoneNumberValidator;
import hauw.widget.TableHeaderRenderer;
import hauw.widget.ViewPortGlass;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
public class SupplierPanel extends javax.swing.JInternalFrame {

    private List<Supplier> listSupplier;
    private Supplier supplier;
    private TableRowSorter sorter;

    /** Creates new form SupplierPanel */
    public SupplierPanel() {
        initComponents();
        refreshTable();
        initVars();
        kondisiAwal();
        initListeners();
    }

    private boolean validateForm() {
        if (textIdSupplier.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "ID Supplier tidak boleh kosong", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (textNamaSupplier.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Nama supplier tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (textAlamat.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Alamat tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (textTelepon.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Telepon tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void clearForm() {
        textAlamat.setText("");
        textCari.setText("");
        textIdSupplier.setText("");
        textNamaSupplier.setText("");
        textTelepon.setText("");
    }

    private void refreshTable() {
        listSupplier = Main.getSupplierService().getSupplier();
        tableSupplier.setModel(new SupplierTableModel(listSupplier));
        for (int i = 0; i < tableSupplier.getColumnCount(); i++) {
            tableSupplier.getColumnModel().getColumn(i).setHeaderRenderer(new TableHeaderRenderer());
        }

    }

    private void initVars() {
        sorter = new TableRowSorter(tableSupplier.getModel());
        tableSupplier.setRowSorter(sorter);
    }

    private void enableForm(boolean status) {
        textIdSupplier.setEnabled(status);
        textNamaSupplier.setEnabled(status);
        textAlamat.setEnabled(status);
        textTelepon.setEnabled(status);
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
        textIdSupplier.setEnabled(false);
    }

    private void loadModelToForm() {
        if (supplier != null) {
            textIdSupplier.setText(supplier.getIdSupplier());
            textNamaSupplier.setText(supplier.getNamaSupplier());
            textAlamat.setText(supplier.getAlamat());
            textTelepon.setText(supplier.getTelepon());
        }
    }

    private Supplier loadFormToModel() {
        Supplier s = new Supplier();
        s.setIdSupplier(textIdSupplier.getText());
        s.setNamaSupplier(textNamaSupplier.getText());
        s.setAlamat(textAlamat.getText());
        s.setTelepon(textTelepon.getText());
        return s;
    }

    private void initListeners() {
        tableSupplier.getSelectionModel().addListSelectionListener(new SupplierSelectionListener());

        toolbarPanel.getButtonTambah().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                kondisiSimpan();
                textIdSupplier.requestFocusInWindow();
            }
        });

        toolbarPanel.getButtonSimpan().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateForm()) {
                    Main.getSupplierService().save(loadFormToModel());
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
                    Main.getSupplierService().update(loadFormToModel());
                    refreshTable();
                    clearForm();
                    kondisiAwal();
                    tableSupplier.clearSelection();
                }
            }
        });

        toolbarPanel.getButtonHapus().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (supplier != null) {
                    Main.getSupplierService().delete(supplier);
                    refreshTable();
                    clearForm();
                    kondisiAwal();
                    tableSupplier.clearSelection();
                }
            }
        });

        toolbarPanel.getButtonBatal().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                kondisiAwal();
                clearForm();
                tableSupplier.clearSelection();
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
        toolbarPanel = new com.retail.ui.toolbar.MasterToolbarPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableSupplier = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        textIdSupplier = new paket.launk.java.controller.OvalTextField();
        textNamaSupplier = new paket.launk.java.controller.OvalTextField();
        textAlamat = new paket.launk.java.controller.OvalTextField();
        textTelepon = new paket.launk.java.controller.OvalTextField();
        jLabel5 = new javax.swing.JLabel();
        textCari = new aerith.swing.AerithTextField();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Supplier");
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

        darkgrayPanel1.setName("darkgrayPanel1"); // NOI18N
        darkgrayPanel1.setLayout(new java.awt.BorderLayout());

        panelGlass1.setName("panelGlass1"); // NOI18N

        toolbarPanel.setName("toolbarPanel"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N
        jScrollPane1.setOpaque(false);
        jScrollPane1.setViewport(new ViewPortGlass());

        tableSupplier.setAutoCreateRowSorter(true);
        tableSupplier.setBackground(new java.awt.Color(255, 255, 255, 0));
        tableSupplier.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tableSupplier.setForeground(new java.awt.Color(255, 255, 255));
        tableSupplier.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tableSupplier.setFillsViewportHeight(true);
        tableSupplier.setGridColor(new java.awt.Color(255, 255, 255));
        tableSupplier.setName("tableSupplier"); // NOI18N
        tableSupplier.setOpaque(false);
        tableSupplier.setSelectionBackground(new java.awt.Color(51, 51, 255));
        tableSupplier.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jScrollPane1.setViewportView(tableSupplier);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14));
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("ID Supplier");
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setFont(new java.awt.Font("Arial", 1, 14));
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Nama");
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel3.setFont(new java.awt.Font("Arial", 1, 14));
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Alamat");
        jLabel3.setName("jLabel3"); // NOI18N

        jLabel4.setFont(new java.awt.Font("Arial", 1, 14));
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Telepon");
        jLabel4.setName("jLabel4"); // NOI18N

        textIdSupplier.setCaretColor(new java.awt.Color(255, 255, 255));
        textIdSupplier.setName("textIdSupplier"); // NOI18N
        textIdSupplier.setNextFocusableComponent(textNamaSupplier);

        textNamaSupplier.setCaretColor(new java.awt.Color(255, 255, 255));
        textNamaSupplier.setName("textNamaSupplier"); // NOI18N
        textNamaSupplier.setNextFocusableComponent(textAlamat);

        textAlamat.setCaretColor(new java.awt.Color(255, 255, 255));
        textAlamat.setName("textAlamat"); // NOI18N
        textAlamat.setNextFocusableComponent(textTelepon);

        textTelepon.setDocument(new PhoneNumberValidator(15, true));
        textTelepon.setCaretColor(new java.awt.Color(255, 255, 255));
        textTelepon.setName("textTelepon"); // NOI18N

        jLabel5.setFont(new java.awt.Font("Arial", 1, 14));
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Cari:");
        jLabel5.setName("jLabel5"); // NOI18N

        textCari.setFont(new java.awt.Font("Tahoma", 0, 14));
        textCari.setName("textCari"); // NOI18N

        javax.swing.GroupLayout panelGlass1Layout = new javax.swing.GroupLayout(panelGlass1);
        panelGlass1.setLayout(panelGlass1Layout);
        panelGlass1Layout.setHorizontalGroup(
            panelGlass1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGlass1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelGlass1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelGlass1Layout.createSequentialGroup()
                        .addComponent(toolbarPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 902, Short.MAX_VALUE)
                        .addContainerGap())
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 912, Short.MAX_VALUE)
                    .addGroup(panelGlass1Layout.createSequentialGroup()
                        .addGroup(panelGlass1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(panelGlass1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textCari, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelGlass1Layout.createSequentialGroup()
                                .addGroup(panelGlass1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(textNamaSupplier, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(textIdSupplier, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE))
                                .addGap(48, 48, 48)
                                .addGroup(panelGlass1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(panelGlass1Layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addGap(18, 18, 18)
                                        .addComponent(textTelepon, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panelGlass1Layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addGap(30, 30, 30)
                                        .addComponent(textAlamat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                        .addContainerGap(277, Short.MAX_VALUE))))
        );

        panelGlass1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {textAlamat, textIdSupplier, textNamaSupplier, textTelepon});

        panelGlass1Layout.setVerticalGroup(
            panelGlass1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGlass1Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(toolbarPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelGlass1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelGlass1Layout.createSequentialGroup()
                        .addGroup(panelGlass1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(textIdSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panelGlass1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(textNamaSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelGlass1Layout.createSequentialGroup()
                        .addGroup(panelGlass1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(textAlamat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panelGlass1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(textTelepon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(29, 29, 29)
                .addGroup(panelGlass1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(textCari, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                .addContainerGap())
        );

        darkgrayPanel1.add(panelGlass1, java.awt.BorderLayout.CENTER);

        getContentPane().add(darkgrayPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here:
        FrameUtama.supplierPanel = null;
        dispose();
    }//GEN-LAST:event_formInternalFrameClosing
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private paket.launk.java.container.DarkgrayPanel darkgrayPanel1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private paket.launk.java.container.PanelGlass panelGlass1;
    private javax.swing.JTable tableSupplier;
    private paket.launk.java.controller.OvalTextField textAlamat;
    private aerith.swing.AerithTextField textCari;
    private paket.launk.java.controller.OvalTextField textIdSupplier;
    private paket.launk.java.controller.OvalTextField textNamaSupplier;
    private paket.launk.java.controller.OvalTextField textTelepon;
    private com.retail.ui.toolbar.MasterToolbarPanel toolbarPanel;
    // End of variables declaration//GEN-END:variables

    private class SupplierTableModel extends AbstractTableModel {

        private List<Supplier> rows;
        private List<String> columns;

        public SupplierTableModel(List<Supplier> rows) {
            this.rows = rows;
            columns = new ArrayList<String>();
            columns.add("ID Supplier");
            columns.add("Nama Supplier");
            columns.add("Alamat");
            columns.add("Telepon");
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
                    return rows.get(rowIndex).getIdSupplier();
                case 1:
                    return rows.get(rowIndex).getNamaSupplier();
                case 2:
                    return rows.get(rowIndex).getAlamat();
                case 3:
                    return rows.get(rowIndex).getTelepon();
                default:
                    return "";
            }
        }
    }

    private class SupplierSelectionListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            int index = tableSupplier.getSelectedRow();
            if (index >= 0) {
                kondisiUbahOrHapus();
                supplier = listSupplier.get(index);
                loadModelToForm();
            }
        }
    }
}
