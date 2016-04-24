/*
 * Copyright (C) ExBin Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.exbin.framework.deltahex.dialog;

import javax.swing.JOptionPane;
import org.exbin.framework.gui.utils.ActionUtils;
import org.exbin.framework.gui.utils.WindowUtils;

/**
 * Find text dialog.
 *
 * @version 0.1.0 2016/04/03
 * @author ExBin Project (http://exbin.org)
 */
public class FindHexDialog extends javax.swing.JDialog {

    private int dialogOption = JOptionPane.CLOSED_OPTION;
    private final java.util.ResourceBundle bundle = ActionUtils.getResourceBundleByClass(FindHexDialog.class);

    public FindHexDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
    }

    private void init() {
        WindowUtils.initWindow(this);
        WindowUtils.addHeaderPanel(this, bundle.getString("header.title"), bundle.getString("header.description"), bundle.getString("header.icon"));
        WindowUtils.assignGlobalKeyListener(this, findButton, cancelButton);
        pack();
    }

    public boolean getSearchFromStart() {
        return !searchFromCursorCheckBox.isSelected();
    }

    public void setSelected() {
        textToFindjTextField.requestFocusInWindow();
        textToFindjTextField.selectAll();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        findPanel = new javax.swing.JPanel();
        textToFindLabel = new javax.swing.JLabel();
        textToFindjTextField = new javax.swing.JTextField();
        searchFromCursorCheckBox = new javax.swing.JCheckBox();
        matchCaseCheckBox = new javax.swing.JCheckBox();
        replacePanel = new javax.swing.JPanel();
        performReplaceCheckBox = new javax.swing.JCheckBox();
        textToReplaceLabel = new javax.swing.JLabel();
        textToReplaceTextField = new javax.swing.JTextField();
        replaceAllMatchesCheckBox = new javax.swing.JCheckBox();
        controlPanel = new javax.swing.JPanel();
        findButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/exbin/framework/deltahex/dialog/resources/FindHexDialog"); // NOI18N
        setTitle(bundle.getString("Form.title")); // NOI18N
        setModal(true);
        setName("Form"); // NOI18N

        mainPanel.setName("mainPanel"); // NOI18N

        findPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("findPanel.border.title"))); // NOI18N
        findPanel.setName("findPanel"); // NOI18N

        textToFindLabel.setText(bundle.getString("textToFindLabel.text")); // NOI18N
        textToFindLabel.setName("textToFindLabel"); // NOI18N

        textToFindjTextField.setName("textToFindjTextField"); // NOI18N

        searchFromCursorCheckBox.setSelected(true);
        searchFromCursorCheckBox.setText(bundle.getString("searchFromCursorCheckBox.text")); // NOI18N
        searchFromCursorCheckBox.setName("searchFromCursorCheckBox"); // NOI18N

        matchCaseCheckBox.setText(bundle.getString("matchCaseCheckBox.text")); // NOI18N
        matchCaseCheckBox.setEnabled(false);
        matchCaseCheckBox.setName("matchCaseCheckBox"); // NOI18N

        javax.swing.GroupLayout findPanelLayout = new javax.swing.GroupLayout(findPanel);
        findPanel.setLayout(findPanelLayout);
        findPanelLayout.setHorizontalGroup(
            findPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(findPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(findPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(matchCaseCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, 417, Short.MAX_VALUE)
                    .addComponent(searchFromCursorCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, 417, Short.MAX_VALUE)
                    .addComponent(textToFindLabel)
                    .addComponent(textToFindjTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 417, Short.MAX_VALUE))
                .addContainerGap())
        );
        findPanelLayout.setVerticalGroup(
            findPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(findPanelLayout.createSequentialGroup()
                .addComponent(textToFindLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textToFindjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchFromCursorCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(matchCaseCheckBox)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        replacePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("replacePanel.border.title"))); // NOI18N
        replacePanel.setName("replacePanel"); // NOI18N

        performReplaceCheckBox.setText(bundle.getString("performReplaceCheckBox.text")); // NOI18N
        performReplaceCheckBox.setName("performReplaceCheckBox"); // NOI18N
        performReplaceCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                performReplaceCheckBoxActionPerformed(evt);
            }
        });

        textToReplaceLabel.setText(bundle.getString("textToReplaceLabel.text")); // NOI18N
        textToReplaceLabel.setName("textToReplaceLabel"); // NOI18N

        textToReplaceTextField.setEnabled(false);
        textToReplaceTextField.setName("textToReplaceTextField"); // NOI18N

        replaceAllMatchesCheckBox.setText(bundle.getString("replaceAllMatchesCheckBox.text")); // NOI18N
        replaceAllMatchesCheckBox.setEnabled(false);
        replaceAllMatchesCheckBox.setName("replaceAllMatchesCheckBox"); // NOI18N

        javax.swing.GroupLayout replacePanelLayout = new javax.swing.GroupLayout(replacePanel);
        replacePanel.setLayout(replacePanelLayout);
        replacePanelLayout.setHorizontalGroup(
            replacePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(replacePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(replacePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(performReplaceCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, 417, Short.MAX_VALUE)
                    .addComponent(textToReplaceLabel)
                    .addComponent(textToReplaceTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 417, Short.MAX_VALUE)
                    .addComponent(replaceAllMatchesCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, 417, Short.MAX_VALUE))
                .addContainerGap())
        );
        replacePanelLayout.setVerticalGroup(
            replacePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(replacePanelLayout.createSequentialGroup()
                .addComponent(performReplaceCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textToReplaceLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textToReplaceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(replaceAllMatchesCheckBox)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 475, Short.MAX_VALUE)
            .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(mainPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(findPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(replacePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap()))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 293, Short.MAX_VALUE)
            .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(mainPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(findPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(replacePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        getContentPane().add(mainPanel, java.awt.BorderLayout.CENTER);

        controlPanel.setName("controlPanel"); // NOI18N

        findButton.setText(bundle.getString("findButton.text")); // NOI18N
        findButton.setName("findButton"); // NOI18N
        findButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                findButtonActionPerformed(evt);
            }
        });

        cancelButton.setText(bundle.getString("cancelButton.text")); // NOI18N
        cancelButton.setName("cancelButton"); // NOI18N
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout controlPanelLayout = new javax.swing.GroupLayout(controlPanel);
        controlPanel.setLayout(controlPanelLayout);
        controlPanelLayout.setHorizontalGroup(
            controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, controlPanelLayout.createSequentialGroup()
                .addContainerGap(324, Short.MAX_VALUE)
                .addComponent(findButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cancelButton)
                .addContainerGap())
        );
        controlPanelLayout.setVerticalGroup(
            controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, controlPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton)
                    .addComponent(findButton))
                .addContainerGap())
        );

        getContentPane().add(controlPanel, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        dialogOption = JOptionPane.CANCEL_OPTION;
        WindowUtils.closeWindow(this);
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void findButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_findButtonActionPerformed
        dialogOption = JOptionPane.OK_OPTION;
        WindowUtils.closeWindow(this);
    }//GEN-LAST:event_findButtonActionPerformed

    private void performReplaceCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_performReplaceCheckBoxActionPerformed
        textToReplaceTextField.setEnabled(performReplaceCheckBox.isSelected());
        textToReplaceLabel.setEnabled(performReplaceCheckBox.isSelected());
    }//GEN-LAST:event_performReplaceCheckBoxActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        WindowUtils.invokeWindow(new FindHexDialog(new javax.swing.JFrame(), true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JPanel controlPanel;
    private javax.swing.JButton findButton;
    private javax.swing.JPanel findPanel;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JCheckBox matchCaseCheckBox;
    private javax.swing.JCheckBox performReplaceCheckBox;
    private javax.swing.JCheckBox replaceAllMatchesCheckBox;
    private javax.swing.JPanel replacePanel;
    private javax.swing.JCheckBox searchFromCursorCheckBox;
    private javax.swing.JLabel textToFindLabel;
    private javax.swing.JTextField textToFindjTextField;
    private javax.swing.JLabel textToReplaceLabel;
    private javax.swing.JTextField textToReplaceTextField;
    // End of variables declaration//GEN-END:variables

    public int getDialogOption() {
        return dialogOption;
    }

    public String getFindText() {
        return textToFindjTextField.getText();
    }

    public boolean getShallReplace() {
        return performReplaceCheckBox.isSelected();
    }

    public void setShallReplace(boolean shallReplace) {
        performReplaceCheckBox.setSelected(shallReplace);
        performReplaceCheckBoxActionPerformed(null);
    }

    public String getReplaceText() {
        return textToReplaceTextField.getText();
    }
}