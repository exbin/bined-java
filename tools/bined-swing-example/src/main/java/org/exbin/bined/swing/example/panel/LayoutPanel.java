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

import javax.annotation.Nonnull;
import org.exbin.bined.capability.RowWrappingCapable.RowWrappingMode;
import org.exbin.bined.swing.basic.CodeArea;

/**
 * Hexadecimal editor example panel.
 *
 * @version 0.2.0 2018/10/10
 * @author ExBin Project (https://exbin.org)
 */
public class LayoutPanel extends javax.swing.JPanel {

    private final CodeArea codeArea;

    public LayoutPanel(@Nonnull CodeArea codeArea) {
        this.codeArea = codeArea;

        initComponents();

        rowWrappingModeCheckBox.setSelected(codeArea.getRowWrapping() == RowWrappingMode.WRAPPING);
        lineNumbersLengthSpinner.setValue(codeArea.getRowPositionNumberLength());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bytesPerLineLabel = new javax.swing.JLabel();
        bytesPerLineSpinner = new javax.swing.JSpinner();
        rowWrappingModeCheckBox = new javax.swing.JCheckBox();
        lineNumbersLengthSpinner = new javax.swing.JSpinner();
        lineNumbersLengthLabel = new javax.swing.JLabel();

        bytesPerLineLabel.setText("Bytes Per Line");

        bytesPerLineSpinner.setModel(new javax.swing.SpinnerNumberModel(16, 0, null, 1));
        bytesPerLineSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                bytesPerLineSpinnerStateChanged(evt);
            }
        });

        rowWrappingModeCheckBox.setText("Wrap Line Mode");
        rowWrappingModeCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                rowWrappingModeCheckBoxItemStateChanged(evt);
            }
        });

        lineNumbersLengthSpinner.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));
        lineNumbersLengthSpinner.setValue(8);
        lineNumbersLengthSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                lineNumbersLengthSpinnerStateChanged(evt);
            }
        });

        lineNumbersLengthLabel.setText("Line Numbers Length");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(bytesPerLineLabel)
                            .addComponent(rowWrappingModeCheckBox)
                            .addComponent(lineNumbersLengthLabel))
                        .addGap(0, 243, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lineNumbersLengthSpinner, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(bytesPerLineSpinner, javax.swing.GroupLayout.Alignment.LEADING))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rowWrappingModeCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bytesPerLineLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bytesPerLineSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lineNumbersLengthLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lineNumbersLengthSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void bytesPerLineSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_bytesPerLineSpinnerStateChanged
        int value = (Integer) bytesPerLineSpinner.getValue();
        codeArea.setMaxBytesPerLine(value);
    }//GEN-LAST:event_bytesPerLineSpinnerStateChanged

    private void lineNumbersLengthSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_lineNumbersLengthSpinnerStateChanged
        codeArea.setRowPositionNumberLength((Integer) lineNumbersLengthSpinner.getValue());
    }//GEN-LAST:event_lineNumbersLengthSpinnerStateChanged

    private void rowWrappingModeCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_rowWrappingModeCheckBoxItemStateChanged
        codeArea.setRowWrapping(rowWrappingModeCheckBox.isSelected() ? RowWrappingMode.WRAPPING : RowWrappingMode.NO_WRAPPING);
    }//GEN-LAST:event_rowWrappingModeCheckBoxItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bytesPerLineLabel;
    private javax.swing.JSpinner bytesPerLineSpinner;
    private javax.swing.JLabel lineNumbersLengthLabel;
    private javax.swing.JSpinner lineNumbersLengthSpinner;
    private javax.swing.JCheckBox rowWrappingModeCheckBox;
    // End of variables declaration//GEN-END:variables
}
