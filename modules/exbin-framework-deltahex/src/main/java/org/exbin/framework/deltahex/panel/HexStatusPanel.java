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
package org.exbin.framework.deltahex.panel;

import org.exbin.framework.deltahex.HexPositionStatusApi;
import org.exbin.framework.editor.text.TextEncodingStatusApi;

/**
 * Hexadecimal editor status panel.
 *
 * @version 0.1.0 2016/04/22
 * @author ExBin Project (http://exbin.org)
 */
public class HexStatusPanel extends javax.swing.JPanel implements HexPositionStatusApi, TextEncodingStatusApi {

    public HexStatusPanel() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        positionLabel = new javax.swing.JLabel();
        positionTextField = new javax.swing.JTextField();
        selectionLabel = new javax.swing.JLabel();
        selectionStartPositionTextField = new javax.swing.JTextField();
        dashLabel = new javax.swing.JLabel();
        selectionEndPositionTextField = new javax.swing.JTextField();
        encodingLabel = new javax.swing.JLabel();
        documentEncodingTextField = new javax.swing.JTextField();

        setName("Form"); // NOI18N

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/exbin/framework/deltahex/panel/resources/HexStatusPanel"); // NOI18N
        positionLabel.setText(bundle.getString("HexStatusPanel.positionLabel.text")); // NOI18N
        positionLabel.setName("positionLabel"); // NOI18N

        positionTextField.setEditable(false);
        positionTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        positionTextField.setText("0"); // NOI18N
        positionTextField.setToolTipText(bundle.getString("HexStatusPanel.positionTextField.toolTipText_1")); // NOI18N
        positionTextField.setName("positionTextField"); // NOI18N

        selectionLabel.setText(bundle.getString("HexStatusPanel.selectionLabel.text_1")); // NOI18N
        selectionLabel.setName("selectionLabel"); // NOI18N

        selectionStartPositionTextField.setEditable(false);
        selectionStartPositionTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        selectionStartPositionTextField.setToolTipText(bundle.getString("HexStatusPanel.selectionStartPositionTextField.toolTipText_1")); // NOI18N
        selectionStartPositionTextField.setName("selectionStartPositionTextField"); // NOI18N

        dashLabel.setText(bundle.getString("HexStatusPanel.dashLabel.text_1")); // NOI18N
        dashLabel.setName("dashLabel"); // NOI18N

        selectionEndPositionTextField.setEditable(false);
        selectionEndPositionTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        selectionEndPositionTextField.setToolTipText(bundle.getString("HexStatusPanel.selectionEndPositionTextField.toolTipText")); // NOI18N
        selectionEndPositionTextField.setName("selectionEndPositionTextField"); // NOI18N

        encodingLabel.setText(bundle.getString("HexStatusPanel.encodingLabel.text")); // NOI18N
        encodingLabel.setName("encodingLabel"); // NOI18N

        documentEncodingTextField.setEditable(false);
        documentEncodingTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        documentEncodingTextField.setText("UTF-8"); // NOI18N
        documentEncodingTextField.setToolTipText(bundle.getString("HexStatusPanel.documentEncodingTextField.toolTipText")); // NOI18N
        documentEncodingTextField.setName("documentEncodingTextField"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(positionLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(positionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(selectionLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(selectionStartPositionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dashLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(selectionEndPositionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                .addComponent(encodingLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(documentEncodingTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(documentEncodingTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(encodingLabel)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(selectionLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(positionTextField)
                    .addComponent(selectionStartPositionTextField)
                    .addComponent(dashLabel)
                    .addComponent(selectionEndPositionTextField)
                    .addComponent(positionLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel dashLabel;
    private javax.swing.JTextField documentEncodingTextField;
    private javax.swing.JLabel encodingLabel;
    private javax.swing.JLabel positionLabel;
    private javax.swing.JTextField positionTextField;
    private javax.swing.JTextField selectionEndPositionTextField;
    private javax.swing.JLabel selectionLabel;
    private javax.swing.JTextField selectionStartPositionTextField;
    // End of variables declaration//GEN-END:variables

    @Override
    public void setCursorPosition(String cursorPosition) {
        positionTextField.setText(cursorPosition);
    }

    @Override
    public void setSelectionPosition(String startPosition, String endPosition) {
        selectionStartPositionTextField.setText(startPosition);
        selectionEndPositionTextField.setText(endPosition);
    }

    @Override
    public void setEncoding(String encodingName) {
        documentEncodingTextField.setText(encodingName);
    }
}
