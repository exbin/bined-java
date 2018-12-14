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
package org.exbin.bined.swing.example.panel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import org.exbin.bined.BasicCodeAreaSection;
import org.exbin.bined.CaretPosition;
import org.exbin.bined.EditationOperation;
import org.exbin.bined.SelectionRange;
import org.exbin.bined.capability.SelectionCapable;
import org.exbin.bined.swing.basic.CodeArea;
import org.exbin.bined.swing.example.BinEdExampleBasicPanel;
import org.exbin.utils.binary_data.EditableBinaryData;

/**
 * Hexadecimal editor example panel.
 *
 * @version 0.2.0 2018/11/12
 * @author ExBin Project (https://exbin.org)
 */
@ParametersAreNonnullByDefault
public class StatePanel extends javax.swing.JPanel {

    private final CodeArea codeArea;

    public StatePanel(CodeArea codeArea) {
        this.codeArea = codeArea;

        initComponents();

        dataSizeTextField.setText(String.valueOf(codeArea.getDataSize()));

        codeArea.addCaretMovedListener((CaretPosition caretPosition) -> {
            positionTextField.setText(String.valueOf(caretPosition.getDataPosition()));
            codeOffsetTextField.setText(String.valueOf(caretPosition.getCodeOffset()));
            activeSectionComboBox.setSelectedIndex(((BasicCodeAreaSection) caretPosition.getSection()).ordinal());
        });
        ((SelectionCapable) codeArea).addSelectionChangedListener((SelectionRange selection) -> {
            if (selection != null) {
                long first = ((SelectionCapable) codeArea).getSelection().getFirst();
                selectionStartTextField.setText(String.valueOf(first));
                long last = ((SelectionCapable) codeArea).getSelection().getLast();
                selectionEndTextField.setText(String.valueOf(last));
            } else {
                selectionStartTextField.setText("");
                selectionEndTextField.setText("");
            }
        });
        codeArea.addDataChangedListener(() -> {
            dataSizeTextField.setText(String.valueOf(codeArea.getDataSize()));
        });
        codeArea.addEditationModeChangedListener((editationMode, editationOperation) -> {
            activeOperationComboBox.setSelectedIndex(editationOperation.ordinal());
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

        saveDataButton = new javax.swing.JButton();
        positionPanel = new javax.swing.JPanel();
        positionLabel = new javax.swing.JLabel();
        positionTextField = new javax.swing.JTextField();
        codeOffsetLabel = new javax.swing.JLabel();
        codeOffsetTextField = new javax.swing.JTextField();
        activeSectionLabel = new javax.swing.JLabel();
        activeSectionComboBox = new javax.swing.JComboBox<>();
        dataSizeLabel = new javax.swing.JLabel();
        selectionPanel = new javax.swing.JPanel();
        selectionStartLabel = new javax.swing.JLabel();
        selectionStartTextField = new javax.swing.JTextField();
        selectionEndLabel = new javax.swing.JLabel();
        selectionEndTextField = new javax.swing.JTextField();
        dataSizeTextField = new javax.swing.JTextField();
        loadDataButton = new javax.swing.JButton();
        activeOperationLabel = new javax.swing.JLabel();
        activeOperationComboBox = new javax.swing.JComboBox<>();

        saveDataButton.setText("Save...");
        saveDataButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveDataButtonActionPerformed(evt);
            }
        });

        positionPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Position"));

        positionLabel.setText("Data Position");

        positionTextField.setEditable(false);

        codeOffsetLabel.setText("Code Offset Position");

        codeOffsetTextField.setEditable(false);

        activeSectionLabel.setText("Active Section");

        activeSectionComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CODE_MATRIX", "TEXT_PREVIEW" }));
        activeSectionComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                activeSectionComboBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout positionPanelLayout = new javax.swing.GroupLayout(positionPanel);
        positionPanel.setLayout(positionPanelLayout);
        positionPanelLayout.setHorizontalGroup(
            positionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(positionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(positionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(codeOffsetTextField)
                    .addComponent(positionTextField)
                    .addGroup(positionPanelLayout.createSequentialGroup()
                        .addGroup(positionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(positionLabel)
                            .addComponent(codeOffsetLabel)
                            .addComponent(activeSectionLabel))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(activeSectionComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        positionPanelLayout.setVerticalGroup(
            positionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(positionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(positionLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(positionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(codeOffsetLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(codeOffsetTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(activeSectionLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(activeSectionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        dataSizeLabel.setText("Data Size");

        selectionPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Selection"));

        selectionStartLabel.setText("Selection Start");

        selectionStartTextField.setEditable(false);

        selectionEndLabel.setText("Selection End");

        selectionEndTextField.setEditable(false);

        javax.swing.GroupLayout selectionPanelLayout = new javax.swing.GroupLayout(selectionPanel);
        selectionPanel.setLayout(selectionPanelLayout);
        selectionPanelLayout.setHorizontalGroup(
            selectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(selectionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(selectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(selectionEndTextField, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(selectionStartTextField, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, selectionPanelLayout.createSequentialGroup()
                        .addGroup(selectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(selectionEndLabel)
                            .addComponent(selectionStartLabel))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        selectionPanelLayout.setVerticalGroup(
            selectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(selectionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(selectionStartLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(selectionStartTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(selectionEndLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(selectionEndTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        dataSizeTextField.setEditable(false);
        dataSizeTextField.setText("0");

        loadDataButton.setText("Load...");
        loadDataButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadDataButtonActionPerformed(evt);
            }
        });

        activeOperationLabel.setText("Active Editation Operation");

        activeOperationComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "INSERT", "OVERWRITE" }));
        activeOperationComboBox.setSelectedIndex(1);
        activeOperationComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                activeOperationComboBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(selectionPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(positionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dataSizeTextField)
                    .addComponent(activeOperationComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dataSizeLabel)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(loadDataButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(saveDataButton))
                            .addComponent(activeOperationLabel))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(dataSizeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dataSizeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(loadDataButton)
                    .addComponent(saveDataButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(activeOperationLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(activeOperationComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(positionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(selectionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void saveDataButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveDataButtonActionPerformed
        JFileChooser saveFC = new JFileChooser();
        saveFC.removeChoosableFileFilter(saveFC.getAcceptAllFileFilter());
        saveFC.addChoosableFileFilter(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isFile();
            }

            @Override
            public String getDescription() {
                return "All Files (*)";
            }
        });
        if (saveFC.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                File selectedFile = saveFC.getSelectedFile();
                try (FileOutputStream stream = new FileOutputStream(selectedFile)) {
                    codeArea.getContentData().saveToStream(stream);
                }
            } catch (IOException ex) {
                Logger.getLogger(BinEdExampleBasicPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_saveDataButtonActionPerformed

    private void activeSectionComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_activeSectionComboBoxActionPerformed
        codeArea.getCaret().setSection(BasicCodeAreaSection.values()[activeSectionComboBox.getSelectedIndex()]);
    }//GEN-LAST:event_activeSectionComboBoxActionPerformed

    private void loadDataButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadDataButtonActionPerformed
        JFileChooser openFC = new JFileChooser();
        openFC.removeChoosableFileFilter(openFC.getAcceptAllFileFilter());
        openFC.addChoosableFileFilter(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isFile();
            }

            @Override
            public String getDescription() {
                return "All Files (*)";
            }
        });
        if (openFC.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                File selectedFile = openFC.getSelectedFile();
                try (FileInputStream stream = new FileInputStream(selectedFile)) {
                    ((EditableBinaryData) codeArea.getContentData()).loadFromStream(stream);
                    codeArea.notifyDataChanged();
                    //                    codeArea.resetPosition();
                }
            } catch (IOException ex) {
                Logger.getLogger(BinEdExampleBasicPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_loadDataButtonActionPerformed

    private void activeOperationComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_activeOperationComboBoxActionPerformed
        codeArea.setEditationOperation(EditationOperation.values()[activeOperationComboBox.getSelectedIndex()]);
    }//GEN-LAST:event_activeOperationComboBoxActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> activeOperationComboBox;
    private javax.swing.JLabel activeOperationLabel;
    private javax.swing.JComboBox<String> activeSectionComboBox;
    private javax.swing.JLabel activeSectionLabel;
    private javax.swing.JLabel codeOffsetLabel;
    private javax.swing.JTextField codeOffsetTextField;
    private javax.swing.JLabel dataSizeLabel;
    private javax.swing.JTextField dataSizeTextField;
    private javax.swing.JButton loadDataButton;
    private javax.swing.JLabel positionLabel;
    private javax.swing.JPanel positionPanel;
    private javax.swing.JTextField positionTextField;
    private javax.swing.JButton saveDataButton;
    private javax.swing.JLabel selectionEndLabel;
    private javax.swing.JTextField selectionEndTextField;
    private javax.swing.JPanel selectionPanel;
    private javax.swing.JLabel selectionStartLabel;
    private javax.swing.JTextField selectionStartTextField;
    // End of variables declaration//GEN-END:variables
}
