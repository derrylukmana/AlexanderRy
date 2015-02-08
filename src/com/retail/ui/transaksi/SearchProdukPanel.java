/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * SearchProdukPanel.java
 *
 * Created on 02 Agu 11, 2:23:12
 */
package com.retail.ui.transaksi;

import com.retail.main.Main;
import com.retail.model.Produk;
import hauw.widget.TableHeaderRenderer;
import hauw.widget.ViewPortGlass;
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
public class SearchProdukPanel extends javax.swing.JDialog {

    private List<Produk> produks;
    private Produk produk;
    private TableRowSorter sorter;

    /** Creates new form SearchProdukPanel */
    public SearchProdukPanel() {
        super(Main.getFrameUtama(), true);        
        initComponents();
        initListeners();
        refreshTable();
        initVar();
        setLocationRelativeTo(null);        
    }

    private void refreshTable() {
        produks = Main.getProdukService().getProduk();
        tableSearchProduk.setModel(new TableSearchProductModel(produks));
        for (int i = 0; i < tableSearchProduk.getColumnCount(); i++) {
            tableSearchProduk.getColumnModel().getColumn(i).setHeaderRenderer(new TableHeaderRenderer());
        }
    }

    public Produk getProduk() {
        setVisible(true);
        return produk;
    }

    private void initVar() {
        sorter = new TableRowSorter(tableSearchProduk.getModel());
        tableSearchProduk.setRowSorter(sorter);
    }

    private void initListeners() {
        tableSearchProduk.getSelectionModel().addListSelectionListener(new ProdukSelectionListener());
        
        textCariProduk.getDocument().addDocumentListener(new DocumentListener() {

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
                String text = textCariProduk.getText();
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
        panelOval1 = new paket.launk.java.container.PanelOval();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableSearchProduk = new javax.swing.JTable();
        buttonOk = new paket.launk.java.controller.ButtonGlass();
        buttonBatal = new paket.launk.java.controller.ButtonGlass();
        jLabel1 = new javax.swing.JLabel();
        textCariProduk = new aerith.swing.AerithTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        darkgrayPanel1.setName("darkgrayPanel1"); // NOI18N
        darkgrayPanel1.setLayout(new java.awt.BorderLayout());

        panelOval1.setName("panelOval1"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N
        jScrollPane1.setOpaque(false);
        jScrollPane1.setViewport(new ViewPortGlass());

        tableSearchProduk.setBackground(new java.awt.Color(255, 255, 255, 0));
        tableSearchProduk.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tableSearchProduk.setForeground(new java.awt.Color(255, 255, 255));
        tableSearchProduk.setModel(new javax.swing.table.DefaultTableModel(
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
        tableSearchProduk.setFillsViewportHeight(true);
        tableSearchProduk.setGridColor(new java.awt.Color(255, 255, 255));
        tableSearchProduk.setName("tableSearchProduk"); // NOI18N
        tableSearchProduk.setOpaque(false);
        tableSearchProduk.setSelectionBackground(new java.awt.Color(51, 51, 255));
        tableSearchProduk.setSelectionForeground(new java.awt.Color(0, 0, 0));
        tableSearchProduk.setShowHorizontalLines(false);
        tableSearchProduk.setShowVerticalLines(false);
        jScrollPane1.setViewportView(tableSearchProduk);

        buttonOk.setText("OK");
        buttonOk.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        buttonOk.setName("buttonOk"); // NOI18N
        buttonOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonOkActionPerformed(evt);
            }
        });

        buttonBatal.setText("Batal");
        buttonBatal.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        buttonBatal.setName("buttonBatal"); // NOI18N
        buttonBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonBatalActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Cari");
        jLabel1.setName("jLabel1"); // NOI18N

        textCariProduk.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        textCariProduk.setName("textCariProduk"); // NOI18N

        javax.swing.GroupLayout panelOval1Layout = new javax.swing.GroupLayout(panelOval1);
        panelOval1.setLayout(panelOval1Layout);
        panelOval1Layout.setHorizontalGroup(
            panelOval1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOval1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelOval1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 437, Short.MAX_VALUE)
                    .addGroup(panelOval1Layout.createSequentialGroup()
                        .addComponent(buttonOk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(buttonBatal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelOval1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(textCariProduk, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)))
                .addContainerGap())
        );

        panelOval1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {buttonBatal, buttonOk});

        panelOval1Layout.setVerticalGroup(
            panelOval1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelOval1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelOval1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textCariProduk, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelOval1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonOk, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        panelOval1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {buttonBatal, buttonOk});

        panelOval1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel1, textCariProduk});

        darkgrayPanel1.add(panelOval1, java.awt.BorderLayout.CENTER);

        getContentPane().add(darkgrayPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonBatalActionPerformed
        // TODO add your handling code here:
        produk = null;
        dispose();
    }//GEN-LAST:event_buttonBatalActionPerformed

    private void buttonOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonOkActionPerformed
        // TODO add your handling code here:
        if (produk == null) {
            JOptionPane.showMessageDialog(null, "Silahkan pilih salah satu produk!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            dispose();
        }
    }//GEN-LAST:event_buttonOkActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private paket.launk.java.controller.ButtonGlass buttonBatal;
    private paket.launk.java.controller.ButtonGlass buttonOk;
    private paket.launk.java.container.DarkgrayPanel darkgrayPanel1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private paket.launk.java.container.PanelOval panelOval1;
    private javax.swing.JTable tableSearchProduk;
    private aerith.swing.AerithTextField textCariProduk;
    // End of variables declaration//GEN-END:variables

    private class TableSearchProductModel extends AbstractTableModel {

        private List<Produk> rows;
        private List<String> columns;

        public TableSearchProductModel(List<Produk> rows) {
            this.rows = rows;
            columns = new ArrayList<String>();
            columns.add("ID Produk");
            columns.add("Nama Produk");
            columns.add("Harga");
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
        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
                case 0:
                case 1:
                    return String.class;
                case 2:
                    return BigDecimal.class;
                case 3:
                    return Integer.class;
                default:
                    return String.class;
            }
        }

        @Override
        public String getColumnName(int column) {
            return columns.get(column);
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return rows.get(rowIndex).getIdProduk();
                case 1:
                    return rows.get(rowIndex).getNamaProduk();
                case 2:
                    return rows.get(rowIndex).getHargaJual();
                case 3:
                    return rows.get(rowIndex).getStockTotal();
                default:
                    return "";
            }
        }
    }

    private class ProdukSelectionListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            int index = tableSearchProduk.getSelectedRow();
            if (index >= 0) {
                produk = produks.get(index);
            }
        }
    }
}
