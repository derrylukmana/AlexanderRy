/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * SupplierReportPanel.java
 *
 * Created on 03 Agu 11, 3:57:58
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
public class SupplierReportPanel extends javax.swing.JInternalFrame {

    /** Creates new form SupplierReportPanel */
    public SupplierReportPanel() {
        initComponents();
        showReport();
    }
    
    private void showReport() {
        reportPanel.removeAll();
        JasperPrint print = Main.getReportService().getReportSupplier();
        JRViewer viewer = new JRViewer(print);
        viewer.setSize(reportPanel.getSize());
        reportPanel.add(viewer, BorderLayout.CENTER);
        reportPanel.updateUI();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        reportPanel = new aerith.swing.AerithContentPanel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Laporan Supplier");
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

        reportPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Laporan", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(255, 255, 255))); // NOI18N
        reportPanel.setLayout(new java.awt.BorderLayout());
        getContentPane().add(reportPanel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here:
        FrameUtama.supplierReportPanel = null;
        dispose();
    }//GEN-LAST:event_formInternalFrameClosing


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private aerith.swing.AerithContentPanel reportPanel;
    // End of variables declaration//GEN-END:variables

}
