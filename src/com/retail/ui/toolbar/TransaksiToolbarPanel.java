/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MasterToolbarPanel.java
 *
 * Created on 31 Jul 11, 4:45:55
 */
package com.retail.ui.toolbar;

import paket.launk.java.controller.ButtonGlass;

/**
 *
 * @author Hauw
 */
public class TransaksiToolbarPanel extends javax.swing.JPanel {

    /** Creates new form MasterToolbarPanel */
    public TransaksiToolbarPanel() {
        initComponents();
        kondisiAwal();
    }

    public ButtonGlass getButtonCetak() {
        return buttonCetak;
    }

    public ButtonGlass getButtonBatal() {
        return buttonBatal;
    }

    public ButtonGlass getButtonSimpan() {
        return buttonSimpan;
    }

    public ButtonGlass getButtonTambah() {
        return buttonTambah;
    }

    public void kondisiAwal() {
        buttonTambah.setEnabled(true);
        buttonSimpan.setEnabled(false);
        buttonBatal.setEnabled(false);
        buttonCetak.setEnabled(false);
    }
    
    public void kondisiSimpan() {
        buttonTambah.setEnabled(false);
        buttonSimpan.setEnabled(true);
        buttonBatal.setEnabled(true);
        buttonCetak.setEnabled(false);
    }
    
    public void kondisiCetak() {
        buttonTambah.setEnabled(false);
        buttonSimpan.setEnabled(false);
        buttonBatal.setEnabled(true);
        buttonCetak.setEnabled(true);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonBatal = new paket.launk.java.controller.ButtonGlass();
        buttonSimpan = new paket.launk.java.controller.ButtonGlass();
        buttonTambah = new paket.launk.java.controller.ButtonGlass();
        buttonCetak = new paket.launk.java.controller.ButtonGlass();

        setOpaque(false);

        buttonBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cancel.png"))); // NOI18N
        buttonBatal.setText("Batal");

        buttonSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/save.png"))); // NOI18N
        buttonSimpan.setText("Simpan");

        buttonTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add.png"))); // NOI18N
        buttonTambah.setText("Tambah");

        buttonCetak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/print.png"))); // NOI18N
        buttonCetak.setText("Cetak");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(buttonTambah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buttonSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buttonBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buttonCetak, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {buttonBatal, buttonSimpan, buttonTambah});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonTambah, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                    .addComponent(buttonSimpan, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                    .addComponent(buttonBatal, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                    .addComponent(buttonCetak, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {buttonBatal, buttonSimpan, buttonTambah});

    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private paket.launk.java.controller.ButtonGlass buttonBatal;
    private paket.launk.java.controller.ButtonGlass buttonCetak;
    private paket.launk.java.controller.ButtonGlass buttonSimpan;
    private paket.launk.java.controller.ButtonGlass buttonTambah;
    // End of variables declaration//GEN-END:variables
}
