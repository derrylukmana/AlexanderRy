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
import com.retail.model.Role;
import com.retail.model.User;
import com.retail.ui.main.FrameUtama;
import hauw.util.PhoneNumberValidator;
import hauw.widget.TableHeaderRenderer;
import hauw.widget.ViewPortGlass;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Hauw
 */
public class UserPanel extends javax.swing.JInternalFrame {

    private List<User> listUser;
    private User user;

    /** Creates new form ProdukPanel */
    public UserPanel() {
        initComponents();
        constructGUI();
        initListeners();
    }

    private boolean validateForm() {
        if (textIdUser.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "ID User tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (textPassword.getPassword().length == 0) {
            JOptionPane.showMessageDialog(this, "Password tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (textNama.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Nama tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
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

    private void enableForm(boolean status) {
        textIdUser.setEnabled(status);
        textPassword.setEnabled(status);
        textNama.setEnabled(status);
        textAlamat.setEnabled(status);
        textTelepon.setEnabled(status);
    }

    private void kondisiAwal() {
        toolbarPanel.kondisiAwal();
        clearForm();
        enableForm(false);
    }

    private void kondisiSimpan() {
        toolbarPanel.kondisiSimpan();
        enableForm(true);
    }

    private void kondisiUbahOrHapus() {
        toolbarPanel.kondisiUbahOrHapus();
        enableForm(true);
        textIdUser.setEnabled(false);
    }

    private void clearForm() {
        textIdUser.setText("");
        textPassword.setText("");
        textNama.setText("");
        textAlamat.setText("");
        textTelepon.setText("");
        comboBoxRole.setSelectedItem(Role.KASIR.toString());
        tableUser.clearSelection();
    }

    private void refreshTable() {
        listUser = Main.getUserService().getUser();
        tableUser.setModel(new UserTableModel(listUser));
        for (int i = 0; i < tableUser.getColumnCount(); i++) {
            tableUser.getColumnModel().getColumn(i).setHeaderRenderer(new TableHeaderRenderer());
        }
    }

    private void constructGUI() {
        String[] roles = new String[Role.values().length];
        for (int i = 0; i < roles.length; i++) {
            roles[i] = Role.values()[i].toString();
        }
        comboBoxRole.setModel(new DefaultComboBoxModel(roles));
        refreshTable();
        tableUser.getSelectionModel().addListSelectionListener(new UserSelectionListener());
        enableForm(false);
    }

    private void loadModelToForm() {
        textIdUser.setText(user.getIdUser());
        textPassword.setText(user.getNama());
        textNama.setText(user.getNama());
        textAlamat.setText(user.getAlamat());
        textTelepon.setText(user.getTelepon());
        comboBoxRole.setSelectedItem(user.getRole().toString());
    }

    private User loadFormToModel() {
        User u = new User();
        u.setIdUser(textIdUser.getText());
        u.setPassword(getUserPassord());
        u.setNama(textNama.getText());
        u.setAlamat(textAlamat.getText());
        u.setRole(getUserRole());
        u.setTelepon(textTelepon.getText());;
        return u;
    }

    private String getUserPassord() {
        char[] passwordChar = textPassword.getPassword();
        String password = "";
        for (char c : passwordChar) {
            password += c;
        }
        return password;
    }

    private Role getUserRole() {
        int index = comboBoxRole.getSelectedIndex();
        switch (index) {
            case 0:
                return Role.KASIR;
            case 1:
                return Role.ADMIN;
            default:
                return null;
        }
    }

    private void initListeners() {
        toolbarPanel.getButtonTambah().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                kondisiSimpan();
                textIdUser.requestFocusInWindow();
            }
        });

        toolbarPanel.getButtonSimpan().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateForm()) {
                    Main.getUserService().save(loadFormToModel());
                    refreshTable();
                    kondisiAwal();
                }
            }
        });

        toolbarPanel.getButtonUbah().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (user.getRole() == Role.ADMIN) {
                    JOptionPane.showMessageDialog(UserPanel.this, "User dengan role admin tidak dapat diubah!!!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (validateForm()) {
                        Main.getUserService().update(loadFormToModel());
                        refreshTable();
                        kondisiAwal();
                    }
                }
            }
        });

        toolbarPanel.getButtonHapus().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (user.getRole() == Role.ADMIN) {
                    JOptionPane.showMessageDialog(UserPanel.this, "User dengan role admin tidak dapat dihapus!!!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    Main.getUserService().delete(user);
                    listUser.remove(user);
                    user = null;
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
        tableUser = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        textPassword = new javax.swing.JPasswordField();
        textIdUser = new aerith.swing.AerithTextField();
        comboBoxRole = new aerith.swing.AerithComboBox();
        textNama = new aerith.swing.AerithTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        textAlamat = new javax.swing.JTextArea();
        textTelepon = new aerith.swing.AerithTextField();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("User");
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

        jScrollPane1.setOpaque(false);
        jScrollPane1.setViewport(new ViewPortGlass());

        tableUser.setAutoCreateRowSorter(true);
        tableUser.setBackground(new java.awt.Color(255, 255, 255, 0));
        tableUser.setForeground(new java.awt.Color(255, 255, 255));
        tableUser.setModel(new javax.swing.table.DefaultTableModel(
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
        tableUser.setFillsViewportHeight(true);
        tableUser.setGridColor(new java.awt.Color(255, 255, 255));
        tableUser.setOpaque(false);
        tableUser.setSelectionBackground(new java.awt.Color(51, 51, 255));
        tableUser.setSelectionForeground(new java.awt.Color(0, 0, 0));
        tableUser.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tableUser);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("ID User");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Password");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Nama");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Alamat");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Role");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Telepon");

        textPassword.setBackground(new java.awt.Color(70, 70, 70));
        textPassword.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        textPassword.setForeground(new java.awt.Color(255, 255, 255));
        textPassword.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(195, 195, 195), 2));
        textPassword.setCaretColor(new java.awt.Color(255, 255, 255));

        textIdUser.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        textIdUser.setPreferredSize(new java.awt.Dimension(8, 25));

        comboBoxRole.setEnabled(false);
        comboBoxRole.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        textNama.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        textAlamat.setBackground(new java.awt.Color(70, 70, 70));
        textAlamat.setColumns(20);
        textAlamat.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        textAlamat.setForeground(new java.awt.Color(255, 255, 255));
        textAlamat.setRows(5);
        textAlamat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(210, 210, 210), 2));
        textAlamat.setCaretColor(new java.awt.Color(255, 255, 255));
        jScrollPane2.setViewportView(textAlamat);

        textTelepon.setDocument(new PhoneNumberValidator(15, true));
        textTelepon.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        org.jdesktop.layout.GroupLayout panelGlass1Layout = new org.jdesktop.layout.GroupLayout(panelGlass1);
        panelGlass1.setLayout(panelGlass1Layout);
        panelGlass1Layout.setHorizontalGroup(
            panelGlass1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelGlass1Layout.createSequentialGroup()
                .addContainerGap()
                .add(panelGlass1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(panelGlass1Layout.createSequentialGroup()
                        .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 553, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(18, 18, 18)
                        .add(panelGlass1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(panelGlass1Layout.createSequentialGroup()
                                .add(jLabel6, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 63, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 18, Short.MAX_VALUE)
                                .add(textTelepon, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 314, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(panelGlass1Layout.createSequentialGroup()
                                .add(jLabel4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 63, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(18, 18, 18)
                                .add(jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 314, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(panelGlass1Layout.createSequentialGroup()
                                .add(jLabel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 63, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 18, Short.MAX_VALUE)
                                .add(textNama, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 314, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(panelGlass1Layout.createSequentialGroup()
                                .add(panelGlass1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(jLabel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 63, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(jLabel5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 63, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                .add(18, 18, 18)
                                .add(panelGlass1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(comboBoxRole, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
                                    .add(textPassword, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE))
                                .add(72, 72, 72))
                            .add(panelGlass1Layout.createSequentialGroup()
                                .add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 63, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(18, 18, 18)
                                .add(textIdUser, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 214, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                        .add(18, 18, 18))
                    .add(toolbarPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 984, Short.MAX_VALUE))
                .addContainerGap())
        );

        panelGlass1Layout.linkSize(new java.awt.Component[] {comboBoxRole, textIdUser, textPassword}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        panelGlass1Layout.linkSize(new java.awt.Component[] {jScrollPane2, textNama, textTelepon}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        panelGlass1Layout.setVerticalGroup(
            panelGlass1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelGlass1Layout.createSequentialGroup()
                .add(panelGlass1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(panelGlass1Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(toolbarPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(18, 18, 18)
                        .add(panelGlass1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
                            .add(panelGlass1Layout.createSequentialGroup()
                                .add(panelGlass1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                    .add(jLabel1)
                                    .add(textIdUser, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 25, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                .add(18, 18, 18)
                                .add(panelGlass1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                    .add(jLabel2)
                                    .add(textPassword, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 28, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                .add(18, 18, 18)
                                .add(panelGlass1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                    .add(jLabel5)
                                    .add(comboBoxRole, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 30, Short.MAX_VALUE))
                                .add(18, 18, 18)
                                .add(panelGlass1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                    .add(jLabel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(textNama, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE))
                                .add(panelGlass1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(panelGlass1Layout.createSequentialGroup()
                                        .add(54, 54, 54)
                                        .add(jLabel4))
                                    .add(panelGlass1Layout.createSequentialGroup()
                                        .add(61, 61, 61)
                                        .add(jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 131, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                                .add(151, 151, 151))))
                    .add(panelGlass1Layout.createSequentialGroup()
                        .add(271, 271, 271)
                        .add(panelGlass1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(textTelepon, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel6))))
                .addContainerGap())
        );

        panelGlass1Layout.linkSize(new java.awt.Component[] {jLabel1, jLabel2, jLabel3, jLabel5}, org.jdesktop.layout.GroupLayout.VERTICAL);

        panelGlass1Layout.linkSize(new java.awt.Component[] {textIdUser, textNama, textPassword, textTelepon}, org.jdesktop.layout.GroupLayout.VERTICAL);

        darkgrayPanel1.add(panelGlass1, java.awt.BorderLayout.CENTER);

        getContentPane().add(darkgrayPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here:
        FrameUtama.userPanel = null;
        dispose();
    }//GEN-LAST:event_formInternalFrameClosing
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private aerith.swing.AerithComboBox comboBoxRole;
    private paket.launk.java.container.DarkgrayPanel darkgrayPanel1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private paket.launk.java.container.PanelGlass panelGlass1;
    private javax.swing.JTable tableUser;
    private javax.swing.JTextArea textAlamat;
    private aerith.swing.AerithTextField textIdUser;
    private aerith.swing.AerithTextField textNama;
    private javax.swing.JPasswordField textPassword;
    private aerith.swing.AerithTextField textTelepon;
    private com.retail.ui.toolbar.MasterToolbarPanel toolbarPanel;
    // End of variables declaration//GEN-END:variables

    private class UserTableModel extends AbstractTableModel {

        private List<User> rows;
        private List<String> columns;

        public UserTableModel(List<User> rows) {
            this.rows = rows;
            columns = new ArrayList<String>();
            columns.add("ID User");
            columns.add("Nama");
            columns.add("Role");
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
                    return rows.get(rowIndex).getIdUser();
                case 1:
                    return rows.get(rowIndex).getNama();
                case 2:
                    return rows.get(rowIndex).getRole();
                case 3:
                    return rows.get(rowIndex).getTelepon();
                default:
                    return "";
            }
        }
    }

    private class UserSelectionListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            int index = tableUser.getSelectedRow();
            if (index >= 0) {
                user = listUser.get(tableUser.convertRowIndexToModel(index));
                loadModelToForm();
                kondisiUbahOrHapus();
            }
        }
    }
}
