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
import org.exbin.bined.swing.extended.ExtCodeArea;

/**
 * Hexadecimal editor example panel.
 *
 * @version 0.2.0 2018/03/18
 * @author ExBin Project (https://exbin.org)
 */
public class LayoutPanelEx extends javax.swing.JPanel {

    private final ExtCodeArea codeArea;

    public LayoutPanelEx(@Nonnull ExtCodeArea codeArea) {
        this.codeArea = codeArea;

        initComponents();
//        wrapLineModeCheckBox.setSelected(codeArea.isWrapMode());
//        lineLengthSpinner.setValue(codeArea.getLineLength());
//        headerSpaceComboBox.setSelectedIndex(codeArea.getHeaderSpaceType().ordinal());
//        headerSpaceSpinner.setValue(codeArea.getHeaderSpaceSize());
//        lineNumbersSpaceComboBox.setSelectedIndex(codeArea.getLineNumberSpaceType().ordinal());
//        lineNumbersSpaceSpinner.setValue(codeArea.getLineNumberSpaceSize());
//        lineNumbersLengthComboBox.setSelectedIndex(codeArea.getLineNumberType().ordinal());
//        lineNumbersLengthSpinner.setValue(codeArea.getLineNumberSpecifiedLength());
//        byteGroupSizeSpinner.setValue(codeArea.getByteGroupSize());
//        spaceGroupSizeSpinner.setValue(codeArea.getSpaceGroupSize());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lineLengthLabel = new javax.swing.JLabel();
        headerPanel = new javax.swing.JPanel();
        showHeaderCheckBox = new javax.swing.JCheckBox();
        headerSpaceLabel = new javax.swing.JLabel();
        headerSpaceComboBox = new javax.swing.JComboBox<>();
        headerSpaceSpinner = new javax.swing.JSpinner();
        lineLengthSpinner = new javax.swing.JSpinner();
        lineNumbersPanel = new javax.swing.JPanel();
        showLineNumbersCheckBox = new javax.swing.JCheckBox();
        lineNumbersLengthLabel = new javax.swing.JLabel();
        lineNumbersLengthComboBox = new javax.swing.JComboBox<>();
        lineNumbersLengthSpinner = new javax.swing.JSpinner();
        lineNumbersSpaceLabel = new javax.swing.JLabel();
        lineNumbersSpaceComboBox = new javax.swing.JComboBox<>();
        lineNumbersSpaceSpinner = new javax.swing.JSpinner();
        byteGroupSizeLabel = new javax.swing.JLabel();
        byteGroupSizeSpinner = new javax.swing.JSpinner();
        spaceGroupSizeLabel = new javax.swing.JLabel();
        spaceGroupSizeSpinner = new javax.swing.JSpinner();
        wrapLineModeCheckBox = new javax.swing.JCheckBox();

        lineLengthLabel.setText("Bytes Per Line");

        headerPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Header"));

        showHeaderCheckBox.setText("Show Header");
        showHeaderCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                showHeaderCheckBoxItemStateChanged(evt);
            }
        });

        headerSpaceLabel.setText("Header Space");

        headerSpaceComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NONE", "SPECIFIED", "QUARTER_UNIT", "HALF_UNIT", "ONE_UNIT", "ONE_AND_HALF_UNIT", "DOUBLE_UNIT" }));
        headerSpaceComboBox.setSelectedIndex(2);
        headerSpaceComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                headerSpaceComboBoxActionPerformed(evt);
            }
        });

        headerSpaceSpinner.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));
        headerSpaceSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                headerSpaceSpinnerStateChanged(evt);
            }
        });

        javax.swing.GroupLayout headerPanelLayout = new javax.swing.GroupLayout(headerPanel);
        headerPanel.setLayout(headerPanelLayout);
        headerPanelLayout.setHorizontalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(headerPanelLayout.createSequentialGroup()
                        .addComponent(headerSpaceComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(headerSpaceSpinner, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE))
                    .addGroup(headerPanelLayout.createSequentialGroup()
                        .addGroup(headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(showHeaderCheckBox)
                            .addComponent(headerSpaceLabel))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        headerPanelLayout.setVerticalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPanelLayout.createSequentialGroup()
                .addComponent(showHeaderCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(headerSpaceLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(headerSpaceComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(headerSpaceSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lineLengthSpinner.setModel(new javax.swing.SpinnerNumberModel(16, 1, null, 1));
        lineLengthSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                lineLengthSpinnerStateChanged(evt);
            }
        });

        lineNumbersPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Line Numbers"));

        showLineNumbersCheckBox.setText("Show Line Numbers");
        showLineNumbersCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                showLineNumbersCheckBoxItemStateChanged(evt);
            }
        });

        lineNumbersLengthLabel.setText("Line Numbers Length");

        lineNumbersLengthComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AUTO", "SPECIFIED" }));
        lineNumbersLengthComboBox.setSelectedIndex(1);
        lineNumbersLengthComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lineNumbersLengthComboBoxActionPerformed(evt);
            }
        });

        lineNumbersLengthSpinner.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));
        lineNumbersLengthSpinner.setValue(8);
        lineNumbersLengthSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                lineNumbersLengthSpinnerStateChanged(evt);
            }
        });

        lineNumbersSpaceLabel.setText("Line Numbers Space");

        lineNumbersSpaceComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NONE", "SPECIFIED", "QUARTER_UNIT", "HALF_UNIT", "ONE_UNIT", "ONE_AND_HALF_UNIT", "DOUBLE_UNIT" }));
        lineNumbersSpaceComboBox.setSelectedIndex(4);
        lineNumbersSpaceComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lineNumbersSpaceComboBoxActionPerformed(evt);
            }
        });

        lineNumbersSpaceSpinner.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));
        lineNumbersSpaceSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                lineNumbersSpaceSpinnerStateChanged(evt);
            }
        });

        javax.swing.GroupLayout lineNumbersPanelLayout = new javax.swing.GroupLayout(lineNumbersPanel);
        lineNumbersPanel.setLayout(lineNumbersPanelLayout);
        lineNumbersPanelLayout.setHorizontalGroup(
            lineNumbersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lineNumbersPanelLayout.createSequentialGroup()
                .addComponent(showLineNumbersCheckBox)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(lineNumbersPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(lineNumbersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(lineNumbersPanelLayout.createSequentialGroup()
                        .addComponent(lineNumbersLengthComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lineNumbersLengthSpinner))
                    .addGroup(lineNumbersPanelLayout.createSequentialGroup()
                        .addGroup(lineNumbersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lineNumbersLengthLabel)
                            .addComponent(lineNumbersSpaceLabel))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(lineNumbersPanelLayout.createSequentialGroup()
                        .addComponent(lineNumbersSpaceComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lineNumbersSpaceSpinner)))
                .addContainerGap())
        );
        lineNumbersPanelLayout.setVerticalGroup(
            lineNumbersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lineNumbersPanelLayout.createSequentialGroup()
                .addComponent(showLineNumbersCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lineNumbersLengthLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(lineNumbersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lineNumbersLengthComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lineNumbersLengthSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lineNumbersSpaceLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(lineNumbersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lineNumbersSpaceComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lineNumbersSpaceSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        byteGroupSizeLabel.setText("Byte Group Size");

        byteGroupSizeSpinner.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));
        byteGroupSizeSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                byteGroupSizeSpinnerStateChanged(evt);
            }
        });

        spaceGroupSizeLabel.setText("Space Group Size");

        spaceGroupSizeSpinner.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));
        spaceGroupSizeSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spaceGroupSizeSpinnerStateChanged(evt);
            }
        });

        wrapLineModeCheckBox.setText("Wrap Line Mode");
        wrapLineModeCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                wrapLineModeCheckBoxItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lineLengthLabel)
                            .addComponent(wrapLineModeCheckBox))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(spaceGroupSizeSpinner, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(byteGroupSizeSpinner, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(headerPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lineLengthSpinner, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lineNumbersPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(byteGroupSizeLabel, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(spaceGroupSizeLabel, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(wrapLineModeCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lineLengthLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lineLengthSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(headerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lineNumbersPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(byteGroupSizeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(byteGroupSizeSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spaceGroupSizeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spaceGroupSizeSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void showHeaderCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_showHeaderCheckBoxItemStateChanged
        //        codeArea.setShowHeader(showHeaderCheckBox.isSelected());
    }//GEN-LAST:event_showHeaderCheckBoxItemStateChanged

    private void headerSpaceComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_headerSpaceComboBoxActionPerformed
        //        codeArea.setHeaderSpaceType(SpaceType.values()[headerSpaceComboBox.getSelectedIndex()]);
    }//GEN-LAST:event_headerSpaceComboBoxActionPerformed

    private void headerSpaceSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_headerSpaceSpinnerStateChanged
        //        codeArea.setHeaderSpaceSize((Integer) headerSpaceSpinner.getValue());
    }//GEN-LAST:event_headerSpaceSpinnerStateChanged

    private void lineLengthSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_lineLengthSpinnerStateChanged
        //        int value = (Integer) lineLengthSpinner.getValue();
        //        if (value > 0) {
        //            codeArea.setLineLength(value);
        //        }
    }//GEN-LAST:event_lineLengthSpinnerStateChanged

    private void showLineNumbersCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_showLineNumbersCheckBoxItemStateChanged
        //        codeArea.setShowLineNumbers(showLineNumbersCheckBox.isSelected());
    }//GEN-LAST:event_showLineNumbersCheckBoxItemStateChanged

    private void lineNumbersLengthComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lineNumbersLengthComboBoxActionPerformed
        //        codeArea.setLineNumberType(CodeAreaLineNumberLength.LineNumberType.values()[lineNumbersLengthComboBox.getSelectedIndex()]);
    }//GEN-LAST:event_lineNumbersLengthComboBoxActionPerformed

    private void lineNumbersLengthSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_lineNumbersLengthSpinnerStateChanged
        //        codeArea.setLineNumberSpecifiedLength((Integer) lineNumbersLengthSpinner.getValue());
    }//GEN-LAST:event_lineNumbersLengthSpinnerStateChanged

    private void lineNumbersSpaceComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lineNumbersSpaceComboBoxActionPerformed
        //        codeArea.setLineNumberSpaceType(SpaceType.values()[lineNumbersSpaceComboBox.getSelectedIndex()]);
    }//GEN-LAST:event_lineNumbersSpaceComboBoxActionPerformed

    private void lineNumbersSpaceSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_lineNumbersSpaceSpinnerStateChanged
        //        codeArea.setLineNumberSpaceSize((Integer) lineNumbersSpaceSpinner.getValue());
    }//GEN-LAST:event_lineNumbersSpaceSpinnerStateChanged

    private void byteGroupSizeSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_byteGroupSizeSpinnerStateChanged
        //        codeArea.setByteGroupSize((Integer) byteGroupSizeSpinner.getValue());
    }//GEN-LAST:event_byteGroupSizeSpinnerStateChanged

    private void spaceGroupSizeSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spaceGroupSizeSpinnerStateChanged
        //        codeArea.setSpaceGroupSize((Integer) spaceGroupSizeSpinner.getValue());
    }//GEN-LAST:event_spaceGroupSizeSpinnerStateChanged

    private void wrapLineModeCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_wrapLineModeCheckBoxItemStateChanged
        //        codeArea.setWrapMode(wrapLineModeCheckBox.isSelected());
    }//GEN-LAST:event_wrapLineModeCheckBoxItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel byteGroupSizeLabel;
    private javax.swing.JSpinner byteGroupSizeSpinner;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JComboBox<String> headerSpaceComboBox;
    private javax.swing.JLabel headerSpaceLabel;
    private javax.swing.JSpinner headerSpaceSpinner;
    private javax.swing.JLabel lineLengthLabel;
    private javax.swing.JSpinner lineLengthSpinner;
    private javax.swing.JComboBox<String> lineNumbersLengthComboBox;
    private javax.swing.JLabel lineNumbersLengthLabel;
    private javax.swing.JSpinner lineNumbersLengthSpinner;
    private javax.swing.JPanel lineNumbersPanel;
    private javax.swing.JComboBox<String> lineNumbersSpaceComboBox;
    private javax.swing.JLabel lineNumbersSpaceLabel;
    private javax.swing.JSpinner lineNumbersSpaceSpinner;
    private javax.swing.JCheckBox showHeaderCheckBox;
    private javax.swing.JCheckBox showLineNumbersCheckBox;
    private javax.swing.JLabel spaceGroupSizeLabel;
    private javax.swing.JSpinner spaceGroupSizeSpinner;
    private javax.swing.JCheckBox wrapLineModeCheckBox;
    // End of variables declaration//GEN-END:variables
}
