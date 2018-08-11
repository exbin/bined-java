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
import org.exbin.bined.BasicCodeAreaSection;
import org.exbin.bined.CaretPosition;
import org.exbin.bined.CodeAreaCaretPosition;
import org.exbin.bined.ScrollBarVisibility;
import org.exbin.bined.basic.CodeAreaScrollPosition;
import org.exbin.bined.basic.HorizontalScrollUnit;
import org.exbin.bined.basic.VerticalScrollUnit;
import org.exbin.bined.capability.CaretCapable;
import org.exbin.bined.capability.ScrollingCapable;
import org.exbin.bined.swing.basic.CodeArea;

/**
 * Hexadecimal editor example panel.
 *
 * @version 0.2.0 2018/07/01
 * @author ExBin Project (https://exbin.org)
 */
public class ScrollingPanel extends javax.swing.JPanel {

    private final CodeArea codeArea;

    public ScrollingPanel(@Nonnull final CodeArea codeArea) {
        this.codeArea = codeArea;

        initComponents();

        verticalScrollBarVisibilityComboBox.setSelectedIndex(((ScrollingCapable) codeArea).getVerticalScrollBarVisibility().ordinal());
        verticalScrollModeComboBox.setSelectedIndex(((ScrollingCapable) codeArea).getVerticalScrollUnit().ordinal());
        horizontalScrollBarVisibilityComboBox.setSelectedIndex(((ScrollingCapable) codeArea).getHorizontalScrollBarVisibility().ordinal());
        horizontalScrollModeComboBox.setSelectedIndex(((ScrollingCapable) codeArea).getHorizontalScrollUnit().ordinal());

        ((ScrollingCapable) codeArea).addScrollingListener(() -> {
            CodeAreaScrollPosition scrollPosition = ((ScrollingCapable) codeArea).getScrollPosition();
            verticalPositionTextField.setText(scrollPosition.getRowPosition() + ":" + scrollPosition.getRowOffset());
            horizontalPositionTextField.setText(scrollPosition.getCharPosition() + ":" + scrollPosition.getCharOffset());
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

        horizontalPanel = new javax.swing.JPanel();
        horizontalScrollBarVisibilityLabel = new javax.swing.JLabel();
        horizontalScrollBarVisibilityComboBox = new javax.swing.JComboBox<>();
        horizontalScrollModeLabel = new javax.swing.JLabel();
        horizontalScrollModeComboBox = new javax.swing.JComboBox<>();
        horizontalPositionLabel = new javax.swing.JLabel();
        horizontalPositionTextField = new javax.swing.JTextField();
        verticalPanel = new javax.swing.JPanel();
        verticalScrollBarVisibilityModeLabel = new javax.swing.JLabel();
        verticalScrollBarVisibilityComboBox = new javax.swing.JComboBox<>();
        verticalScrollModeLabel = new javax.swing.JLabel();
        verticalScrollModeComboBox = new javax.swing.JComboBox<>();
        verticalPositionLabel = new javax.swing.JLabel();
        verticalPositionTextField = new javax.swing.JTextField();
        horizontalByteShiftLabel = new javax.swing.JLabel();
        horizontalByteShiftTextField = new javax.swing.JTextField();
        testButton = new javax.swing.JButton();

        horizontalPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Horizontal"));

        horizontalScrollBarVisibilityLabel.setText("Horizontal Scrollbar");

        horizontalScrollBarVisibilityComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NEVER", "IF_NEEDED", "ALWAYS" }));
        horizontalScrollBarVisibilityComboBox.setSelectedIndex(1);
        horizontalScrollBarVisibilityComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                horizontalScrollBarVisibilityComboBoxActionPerformed(evt);
            }
        });

        horizontalScrollModeLabel.setText("Horizontal Scroll Mode");

        horizontalScrollModeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "PER_CHAR", "PIXEL" }));
        horizontalScrollModeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                horizontalScrollModeComboBoxActionPerformed(evt);
            }
        });

        horizontalPositionLabel.setText("Horizontal Scroll Position");

        horizontalPositionTextField.setEditable(false);
        horizontalPositionTextField.setText("0:0");

        javax.swing.GroupLayout horizontalPanelLayout = new javax.swing.GroupLayout(horizontalPanel);
        horizontalPanel.setLayout(horizontalPanelLayout);
        horizontalPanelLayout.setHorizontalGroup(
            horizontalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(horizontalPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(horizontalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(horizontalScrollModeComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(horizontalScrollBarVisibilityComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(horizontalPanelLayout.createSequentialGroup()
                        .addGroup(horizontalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(horizontalScrollBarVisibilityLabel)
                            .addComponent(horizontalScrollModeLabel)
                            .addComponent(horizontalPositionLabel))
                        .addGap(0, 187, Short.MAX_VALUE))
                    .addComponent(horizontalPositionTextField))
                .addContainerGap())
        );
        horizontalPanelLayout.setVerticalGroup(
            horizontalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(horizontalPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(horizontalScrollBarVisibilityLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(horizontalScrollBarVisibilityComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(horizontalScrollModeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(horizontalScrollModeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(horizontalPositionLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(horizontalPositionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        verticalPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Vertical"));

        verticalScrollBarVisibilityModeLabel.setText("Vertical Scrollbar");

        verticalScrollBarVisibilityComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NEVER", "IF_NEEDED", "ALWAYS" }));
        verticalScrollBarVisibilityComboBox.setSelectedIndex(1);
        verticalScrollBarVisibilityComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verticalScrollBarVisibilityComboBoxActionPerformed(evt);
            }
        });

        verticalScrollModeLabel.setText("Vertical Scroll Mode");

        verticalScrollModeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "PER_LINE", "PIXEL" }));
        verticalScrollModeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verticalScrollModeComboBoxActionPerformed(evt);
            }
        });

        verticalPositionLabel.setText("Vertical Scroll Position");

        verticalPositionTextField.setEditable(false);
        verticalPositionTextField.setText("0:0");

        javax.swing.GroupLayout verticalPanelLayout = new javax.swing.GroupLayout(verticalPanel);
        verticalPanel.setLayout(verticalPanelLayout);
        verticalPanelLayout.setHorizontalGroup(
            verticalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(verticalPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(verticalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(verticalScrollBarVisibilityComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(verticalScrollModeComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(verticalPositionTextField)
                    .addGroup(verticalPanelLayout.createSequentialGroup()
                        .addGroup(verticalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(verticalScrollBarVisibilityModeLabel)
                            .addComponent(verticalScrollModeLabel)
                            .addComponent(verticalPositionLabel))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        verticalPanelLayout.setVerticalGroup(
            verticalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(verticalPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(verticalScrollBarVisibilityModeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(verticalScrollBarVisibilityComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(verticalScrollModeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(verticalScrollModeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(verticalPositionLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(verticalPositionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        horizontalByteShiftLabel.setText("Horizontal Byte Shift");

        horizontalByteShiftTextField.setEditable(false);
        horizontalByteShiftTextField.setText("0");

        testButton.setText("Test");
        testButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(horizontalByteShiftTextField)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(horizontalByteShiftLabel)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(testButton))
                    .addComponent(horizontalPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(verticalPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(testButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(verticalPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(horizontalPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(horizontalByteShiftLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(horizontalByteShiftTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(67, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void horizontalScrollBarVisibilityComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_horizontalScrollBarVisibilityComboBoxActionPerformed
        ((ScrollingCapable) codeArea).setHorizontalScrollBarVisibility(ScrollBarVisibility.values()[horizontalScrollBarVisibilityComboBox.getSelectedIndex()]);
    }//GEN-LAST:event_horizontalScrollBarVisibilityComboBoxActionPerformed

    private void horizontalScrollModeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_horizontalScrollModeComboBoxActionPerformed
        ((ScrollingCapable) codeArea).setHorizontalScrollUnit(HorizontalScrollUnit.values()[horizontalScrollModeComboBox.getSelectedIndex()]);
    }//GEN-LAST:event_horizontalScrollModeComboBoxActionPerformed

    private void verticalScrollBarVisibilityComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verticalScrollBarVisibilityComboBoxActionPerformed
        ((ScrollingCapable) codeArea).setVerticalScrollBarVisibility(ScrollBarVisibility.values()[verticalScrollBarVisibilityComboBox.getSelectedIndex()]);
    }//GEN-LAST:event_verticalScrollBarVisibilityComboBoxActionPerformed

    private void verticalScrollModeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verticalScrollModeComboBoxActionPerformed
        ((ScrollingCapable) codeArea).setVerticalScrollUnit(VerticalScrollUnit.values()[verticalScrollModeComboBox.getSelectedIndex()]);
    }//GEN-LAST:event_verticalScrollModeComboBoxActionPerformed

    private void testButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_testButtonActionPerformed
        CaretPosition caretPosition = new CodeAreaCaretPosition(1000, 0, BasicCodeAreaSection.TEXT_PREVIEW.getSection());
        ((CaretCapable) codeArea).getCaret().setCaretPosition(caretPosition);
        ((ScrollingCapable) codeArea).centerOnCursor();
        codeArea.requestFocus();
    }//GEN-LAST:event_testButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel horizontalByteShiftLabel;
    private javax.swing.JTextField horizontalByteShiftTextField;
    private javax.swing.JPanel horizontalPanel;
    private javax.swing.JLabel horizontalPositionLabel;
    private javax.swing.JTextField horizontalPositionTextField;
    private javax.swing.JComboBox<String> horizontalScrollBarVisibilityComboBox;
    private javax.swing.JLabel horizontalScrollBarVisibilityLabel;
    private javax.swing.JComboBox<String> horizontalScrollModeComboBox;
    private javax.swing.JLabel horizontalScrollModeLabel;
    private javax.swing.JButton testButton;
    private javax.swing.JPanel verticalPanel;
    private javax.swing.JLabel verticalPositionLabel;
    private javax.swing.JTextField verticalPositionTextField;
    private javax.swing.JComboBox<String> verticalScrollBarVisibilityComboBox;
    private javax.swing.JLabel verticalScrollBarVisibilityModeLabel;
    private javax.swing.JComboBox<String> verticalScrollModeComboBox;
    private javax.swing.JLabel verticalScrollModeLabel;
    // End of variables declaration//GEN-END:variables
}
