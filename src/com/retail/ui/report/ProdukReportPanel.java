/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ProdukReportPanel.java
 *
 * Created on 02 Agu 11, 7:50:55
 */
package com.retail.ui.report;

import com.retail.main.Main;
import com.retail.ui.main.FrameUtama;
import java.awt.BorderLayout;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;

/**
 *
 * @author Hauw
 */
public class ProdukReportPanel extends javax.swing.JInternalFrame {

    /** Creates new form ProdukReportPanel */
    public ProdukReportPanel() {
        initComponents();
        showReport();
    }

    private void showReport() {
        panelReport.removeAll();
        JasperPrint report = Main.getReportService().getReportProduk();
        JRViewer viewer = new JRViewer(report);
        viewer.setSize(panelReport.getSize());
        panelReport.add(viewer, BorderLayout.CENTER);
        panelReport.updateUI();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        aerithContentPanel1 = new aerith.swing.AerithContentPanel();
        panelReport = new javax.swing.JPanel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Laporan Produk");
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

        aerithContentPanel1.setName("aerithContentPanel1"); // NOI18N
        aerithContentPanel1.setLayout(new java.awt.BorderLayout());

        panelReport.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Laporan", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(255, 255, 255))); // NOI18N
        panelReport.setName("panelReport"); // NOI18N
        panelReport.setOpaque(false);
        panelReport.setLayout(new java.awt.BorderLayout());
        aerithContentPanel1.add(panelReport, java.awt.BorderLayout.CENTER);

        getContentPane().add(aerithContentPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here:
        FrameUtama.produkReportPanel = null;
        dispose();
    }//GEN-LAST:event_formInternalFrameClosing

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private aerith.swing.AerithContentPanel aerithContentPanel1;
    private javax.swing.JPanel panelReport;
    // End of variables declaration//GEN-END:variables
}