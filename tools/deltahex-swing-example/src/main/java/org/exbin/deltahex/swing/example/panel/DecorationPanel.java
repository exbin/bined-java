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
package org.exbin.deltahex.swing.example.panel;

import java.awt.Color;
import javax.annotation.Nonnull;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicBorders;
import org.exbin.deltahex.swing.CodeArea;
import org.exbin.deltahex.swing.basic.BasicBackgroundPaintMode;
import org.exbin.deltahex.swing.basic.DefaultCodeAreaWorker;
import org.exbin.deltahex.swing.capability.BackgroundPaintCapable;

/**
 * Hexadecimal editor example panel.
 *
 * @version 0.2.0 2018/03/18
 * @author ExBin Project (http://exbin.org)
 */
public class DecorationPanel extends javax.swing.JPanel {

    private final CodeArea codeArea;
    private final DefaultCodeAreaWorker worker;

    public DecorationPanel(@Nonnull CodeArea codeArea) {
        this.codeArea = codeArea;
        worker = (DefaultCodeAreaWorker) codeArea.getWorker();

        initComponents();

        backgroundModeComboBox.setSelectedIndex(((BackgroundPaintCapable) worker).getBackgroundPaintMode().ordinal());
//        positionCodeTypeComboBox.setSelectedIndex(codeArea.getPositionCodeType().ordinal());
//        lineNumbersBackgroundCheckBox.setSelected(codeArea.isLineNumberBackground());
//        showLineNumbersCheckBox.setSelected(codeArea.isShowLineNumbers());

//        int decorationMode = codeArea.getDecorationMode();
//        decoratorHeaderLineCheckBox.setSelected((decorationMode & CodeArea.DECORATION_HEADER_LINE) > 0);
//        decoratorLineNumLineCheckBox.setSelected((decorationMode & CodeArea.DECORATION_LINENUM_LINE) > 0);
//        decoratorSplitLineCheckBox.setSelected((decorationMode & CodeArea.DECORATION_PREVIEW_LINE) > 0);
//        decoratorBoxCheckBox.setSelected((decorationMode & CodeArea.DECORATION_BOX) > 0);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        hexCharactersModeLabel = new javax.swing.JLabel();
        backgroundModeLabel = new javax.swing.JLabel();
        hexCharactersModeComboBox = new javax.swing.JComboBox<>();
        backgroundModeComboBox = new javax.swing.JComboBox<>();
        positionCodeTypeLabel = new javax.swing.JLabel();
        lineNumbersBackgroundCheckBox = new javax.swing.JCheckBox();
        positionCodeTypeComboBox = new javax.swing.JComboBox<>();
        linesPanel = new javax.swing.JPanel();
        decoratorLineNumLineCheckBox = new javax.swing.JCheckBox();
        decoratorSplitLineCheckBox = new javax.swing.JCheckBox();
        decoratorBoxCheckBox = new javax.swing.JCheckBox();
        decoratorHeaderLineCheckBox = new javax.swing.JCheckBox();
        borderTypeLabel = new javax.swing.JLabel();
        borderTypeComboBox = new javax.swing.JComboBox<>();

        hexCharactersModeLabel.setText("Hex Chars Mode");

        backgroundModeLabel.setText("Background Mode");

        hexCharactersModeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "LOWER", "UPPER" }));
        hexCharactersModeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hexCharactersModeComboBoxActionPerformed(evt);
            }
        });

        backgroundModeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NONE", "PLAIN", "STRIPPED" }));
        backgroundModeComboBox.setSelectedIndex(2);
        backgroundModeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backgroundModeComboBoxActionPerformed(evt);
            }
        });

        positionCodeTypeLabel.setText("Position Code Type");

        lineNumbersBackgroundCheckBox.setText("Include Line Numbers");
        lineNumbersBackgroundCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                lineNumbersBackgroundCheckBoxItemStateChanged(evt);
            }
        });

        positionCodeTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "OCTAL", "DECIMAL", "HEXADECIMAL" }));
        positionCodeTypeComboBox.setSelectedIndex(2);
        positionCodeTypeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                positionCodeTypeComboBoxActionPerformed(evt);
            }
        });

        linesPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Lines"));

        decoratorLineNumLineCheckBox.setText("LineNum Line");
        decoratorLineNumLineCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                decoratorLineNumLineCheckBoxItemStateChanged(evt);
            }
        });

        decoratorSplitLineCheckBox.setText("Split Line");
        decoratorSplitLineCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                decoratorSplitLineCheckBoxItemStateChanged(evt);
            }
        });

        decoratorBoxCheckBox.setText("Area Box");
        decoratorBoxCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                decoratorBoxCheckBoxItemStateChanged(evt);
            }
        });

        decoratorHeaderLineCheckBox.setText("Header Line");
        decoratorHeaderLineCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                decoratorHeaderLineCheckBoxItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout linesPanelLayout = new javax.swing.GroupLayout(linesPanel);
        linesPanel.setLayout(linesPanelLayout);
        linesPanelLayout.setHorizontalGroup(
            linesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(linesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(linesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(decoratorLineNumLineCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(decoratorSplitLineCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(decoratorBoxCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(decoratorHeaderLineCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        linesPanelLayout.setVerticalGroup(
            linesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(linesPanelLayout.createSequentialGroup()
                .addComponent(decoratorHeaderLineCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(decoratorLineNumLineCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(decoratorSplitLineCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(decoratorBoxCheckBox))
        );

        borderTypeLabel.setText("Border Type");

        borderTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NONE", "EMPTY BORDER", "MARGIN BORDER", "BEVEL BORDER - RAISED", "BEVEL BORDER - LOWERED", "ETCHED BORDER - RAISED", "ETCHED BORDER - LOWERED", "LINE BORDER" }));
        borderTypeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                borderTypeComboBoxActionPerformed(evt);
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
                            .addComponent(hexCharactersModeLabel)
                            .addComponent(positionCodeTypeLabel))
                        .addGap(132, 132, 132))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(backgroundModeLabel)
                            .addComponent(borderTypeLabel)
                            .addComponent(lineNumbersBackgroundCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(backgroundModeComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(linesPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(borderTypeComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(hexCharactersModeComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(positionCodeTypeComboBox, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(backgroundModeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(backgroundModeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lineNumbersBackgroundCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(linesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(borderTypeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(borderTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(hexCharactersModeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(hexCharactersModeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(positionCodeTypeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(positionCodeTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void hexCharactersModeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hexCharactersModeComboBoxActionPerformed
        //        codeArea.setHexCharactersCase(HexCharactersCase.values()[hexCharactersModeComboBox.getSelectedIndex()]);
    }//GEN-LAST:event_hexCharactersModeComboBoxActionPerformed

    private void backgroundModeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backgroundModeComboBoxActionPerformed
        ((BackgroundPaintCapable) worker).setBackgroundPaintMode(BasicBackgroundPaintMode.values()[backgroundModeComboBox.getSelectedIndex()]);
    }//GEN-LAST:event_backgroundModeComboBoxActionPerformed

    private void lineNumbersBackgroundCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_lineNumbersBackgroundCheckBoxItemStateChanged
        //        codeArea.setLineNumberBackground(lineNumbersBackgroundCheckBox.isSelected());
    }//GEN-LAST:event_lineNumbersBackgroundCheckBoxItemStateChanged

    private void positionCodeTypeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_positionCodeTypeComboBoxActionPerformed
        //        codeArea.setPositionCodeType(PositionCodeType.values()[positionCodeTypeComboBox.getSelectedIndex()]);
    }//GEN-LAST:event_positionCodeTypeComboBoxActionPerformed

    private void decoratorLineNumLineCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_decoratorLineNumLineCheckBoxItemStateChanged
        //        int decorationMode = codeArea.getDecorationMode();
        //        boolean selected = decoratorLineNumLineCheckBox.isSelected();
        //        if (((decorationMode & CodeArea.DECORATION_LINENUM_LINE) > 0) != selected) {
        //            codeArea.setDecorationMode(decorationMode ^ CodeArea.DECORATION_LINENUM_LINE);
        //        }
    }//GEN-LAST:event_decoratorLineNumLineCheckBoxItemStateChanged

    private void decoratorSplitLineCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_decoratorSplitLineCheckBoxItemStateChanged
        //        int decorationMode = codeArea.getDecorationMode();
        //        boolean selected = decoratorSplitLineCheckBox.isSelected();
        //        if (((decorationMode & CodeArea.DECORATION_PREVIEW_LINE) > 0) != selected) {
        //            codeArea.setDecorationMode(decorationMode ^ CodeArea.DECORATION_PREVIEW_LINE);
        //        }
    }//GEN-LAST:event_decoratorSplitLineCheckBoxItemStateChanged

    private void decoratorBoxCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_decoratorBoxCheckBoxItemStateChanged
        //        int decorationMode = codeArea.getDecorationMode();
        //        boolean selected = decoratorBoxCheckBox.isSelected();
        //        if (((decorationMode & CodeArea.DECORATION_BOX) > 0) != selected) {
        //            codeArea.setDecorationMode(decorationMode ^ CodeArea.DECORATION_BOX);
        //        }
    }//GEN-LAST:event_decoratorBoxCheckBoxItemStateChanged

    private void decoratorHeaderLineCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_decoratorHeaderLineCheckBoxItemStateChanged
        //        int decorationMode = codeArea.getDecorationMode();
        //        boolean selected = decoratorHeaderLineCheckBox.isSelected();
        //        if (((decorationMode & CodeArea.DECORATION_HEADER_LINE) > 0) != selected) {
        //            codeArea.setDecorationMode(decorationMode ^ CodeArea.DECORATION_HEADER_LINE);
        //        }
    }//GEN-LAST:event_decoratorHeaderLineCheckBoxItemStateChanged

    private void borderTypeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_borderTypeComboBoxActionPerformed
        codeArea.setBorder(getBorderByType(borderTypeComboBox.getSelectedIndex()));
    }//GEN-LAST:event_borderTypeComboBoxActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> backgroundModeComboBox;
    private javax.swing.JLabel backgroundModeLabel;
    private javax.swing.JComboBox<String> borderTypeComboBox;
    private javax.swing.JLabel borderTypeLabel;
    private javax.swing.JCheckBox decoratorBoxCheckBox;
    private javax.swing.JCheckBox decoratorHeaderLineCheckBox;
    private javax.swing.JCheckBox decoratorLineNumLineCheckBox;
    private javax.swing.JCheckBox decoratorSplitLineCheckBox;
    private javax.swing.JComboBox<String> hexCharactersModeComboBox;
    private javax.swing.JLabel hexCharactersModeLabel;
    private javax.swing.JCheckBox lineNumbersBackgroundCheckBox;
    private javax.swing.JPanel linesPanel;
    private javax.swing.JComboBox<String> positionCodeTypeComboBox;
    private javax.swing.JLabel positionCodeTypeLabel;
    // End of variables declaration//GEN-END:variables

    private Border getBorderByType(int borderTypeIndex) {
        switch (borderTypeIndex) {
            case 0: {
                return null;
            }
            case 1: {
                return new EmptyBorder(5, 5, 5, 5);
            }
            case 2: {
                return new BasicBorders.MarginBorder();
            }
            case 3: {
                return new BevelBorder(BevelBorder.RAISED);
            }
            case 4: {
                return new BevelBorder(BevelBorder.LOWERED);
            }
            case 5: {
                return new EtchedBorder(EtchedBorder.RAISED);
            }
            case 6: {
                return new EtchedBorder(EtchedBorder.LOWERED);
            }
            case 7: {
                return new LineBorder(Color.BLACK);
            }
        }

        return null;
    }
}