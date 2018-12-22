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
package org.exbin.bined.swing.extended;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import org.exbin.bined.BasicCodeAreaSection;
import org.exbin.bined.BasicCodeAreaZone;
import org.exbin.bined.CaretPosition;
import org.exbin.bined.CodeAreaCaretPosition;
import org.exbin.bined.CodeAreaSection;
import org.exbin.bined.CodeAreaUtils;
import org.exbin.bined.CodeAreaViewMode;
import org.exbin.bined.CodeCharactersCase;
import org.exbin.bined.CodeType;
import org.exbin.bined.EditationOperation;
import org.exbin.bined.PositionCodeType;
import org.exbin.bined.PositionOverflowMode;
import org.exbin.bined.SelectionRange;
import org.exbin.bined.basic.BasicCodeAreaScrolling;
import org.exbin.bined.basic.CodeAreaScrollPosition;
import org.exbin.bined.basic.MovementDirection;
import org.exbin.bined.basic.PositionScrollVisibility;
import org.exbin.bined.basic.ScrollBarVerticalScale;
import org.exbin.bined.basic.ScrollingDirection;
import org.exbin.bined.capability.CaretCapable;
import org.exbin.bined.capability.CharsetCapable;
import org.exbin.bined.capability.CodeCharactersCaseCapable;
import org.exbin.bined.capability.EditationModeCapable;
import org.exbin.bined.capability.RowWrappingCapable;
import org.exbin.bined.capability.ScrollingCapable;
import org.exbin.bined.color.BasicCodeAreaDecorationColorType;
import org.exbin.bined.color.CodeAreaBasicColors;
import org.exbin.bined.swing.basic.color.CodeAreaColorsProfile;
import org.exbin.bined.extended.capability.CodeAreaCaretsProfile;
import org.exbin.bined.extended.capability.PositionCodeTypeCapable;
import org.exbin.bined.extended.capability.ShowUnprintablesCapable;
import org.exbin.bined.extended.color.CodeAreaUnprintablesColorType;
import org.exbin.bined.extended.theme.ExtendedBackgroundPaintMode;
import org.exbin.bined.swing.CodeAreaCore;
import org.exbin.bined.swing.CodeAreaPainter;
import org.exbin.bined.swing.CodeAreaSwingUtils;
import org.exbin.bined.swing.basic.AntialiasingMode;
import org.exbin.bined.swing.basic.BasicCodeAreaMetrics;
import org.exbin.bined.swing.basic.DefaultCodeAreaCaret;
import org.exbin.bined.swing.basic.DefaultCodeAreaMouseListener;
import org.exbin.bined.swing.capability.AntialiasingCapable;
import org.exbin.bined.swing.capability.FontCapable;
import org.exbin.bined.swing.extended.color.ColorsProfileCapableCodeAreaPainter;
import org.exbin.bined.swing.extended.color.ExtendedCodeAreaColorProfile;
import org.exbin.bined.swing.extended.layout.ExtendedCodeAreaLayoutProfile;
import org.exbin.bined.swing.extended.layout.LayoutProfileCapableCodeAreaPainter;
import org.exbin.bined.swing.extended.theme.ExtendedCodeAreaThemeProfile;
import org.exbin.bined.swing.extended.theme.ThemeProfileCapableCodeAreaPainter;
import org.exbin.utils.binary_data.BinaryData;

/**
 * Extended code area component default painter.
 *
 * @version 0.2.0 2018/12/20
 * @author ExBin Project (https://exbin.org)
 */
@ParametersAreNonnullByDefault
public class ExtendedCodeAreaPainter implements CodeAreaPainter, ColorsProfileCapableCodeAreaPainter, LayoutProfileCapableCodeAreaPainter, ThemeProfileCapableCodeAreaPainter {

    @Nonnull
    protected final CodeAreaCore codeArea;
    private volatile boolean initialized = false;
    private volatile boolean adjusting = false;

    private volatile boolean fontChanged = false;
    private volatile boolean layoutChanged = true;
    private volatile boolean caretChanged = true;
    private volatile boolean resetColors = true;

    @Nonnull
    private final JPanel dataView;
    @Nonnull
    private final JScrollPane scrollPanel;

    @Nonnull
    private final BasicCodeAreaMetrics metrics = new BasicCodeAreaMetrics();
    @Nonnull
    private final ExtendedCodeAreaStructure structure = new ExtendedCodeAreaStructure();
    @Nonnull
    private final BasicCodeAreaScrolling scrolling = new BasicCodeAreaScrolling();
    @Nonnull
    private final ExtendedCodeAreaDimensions dimensions = new ExtendedCodeAreaDimensions();
    @Nonnull
    private final ExtendedCodeAreaVisibility visibility = new ExtendedCodeAreaVisibility();
    @Nonnull
    private volatile ScrollingState scrollingState = ScrollingState.NO_SCROLLING;

    @Nonnull
    private ExtendedCodeAreaLayoutProfile layoutProfile = new ExtendedCodeAreaLayoutProfile();
    @Nonnull
    private CodeAreaColorsProfile colorsProfile = new ExtendedCodeAreaColorProfile();
    @Nonnull
    private ExtendedCodeAreaThemeProfile themeProfile = new ExtendedCodeAreaThemeProfile();
    @Nonnull
    private CodeAreaCaretsProfile caretsProfile = new CodeAreaCaretsProfile();

    @Nullable
    private CodeCharactersCase codeCharactersCase;
    @Nullable
    private EditationOperation editationOperation;
    private boolean showMirrorCursor;
    private boolean showUnprintables;
    @Nonnull
    private AntialiasingMode antialiasingMode = AntialiasingMode.AUTO;

    private int rowPositionLength;
    private int minRowPositionLength;
    private int maxRowPositionLength;

    @Nullable
    private Font font;
    @Nonnull
    private Charset charset;

    @Nullable
    private RowDataCache rowDataCache = null;
    @Nullable
    private CursorDataCache cursorDataCache = null;

    @Nullable
    private Charset charMappingCharset = null;
    private final char[] charMapping = new char[256];

    protected Map<Character, Character> unprintableCharactersMapping = null;

    // Debuging counter
//    private long paintCounter = 0;
    public ExtendedCodeAreaPainter(CodeAreaCore codeArea) {
        this.codeArea = codeArea;
        dataView = new JPanel();
        dataView.setBorder(null);
        dataView.setVisible(false);
        dataView.setLayout(null);
        dataView.setOpaque(false);
        dataView.setInheritsPopupMenu(true);
        // Fill whole area, no more suitable method found so far
        dataView.setPreferredSize(new Dimension(0, 0));
        scrollPanel = new JScrollPane();
        scrollPanel.setBorder(null);
        scrollPanel.setIgnoreRepaint(true);
        JScrollBar verticalScrollBar = scrollPanel.getVerticalScrollBar();
        verticalScrollBar.setIgnoreRepaint(true);
        verticalScrollBar.addAdjustmentListener(new VerticalAdjustmentListener());
        JScrollBar horizontalScrollBar = scrollPanel.getHorizontalScrollBar();
        horizontalScrollBar.setIgnoreRepaint(true);
        horizontalScrollBar.addAdjustmentListener(new HorizontalAdjustmentListener());
        codeArea.add(scrollPanel);
        scrollPanel.setOpaque(false);
        scrollPanel.setViewportView(dataView);
        scrollPanel.setInheritsPopupMenu(true);
        scrollPanel.setViewportBorder(null);
        scrollPanel.getViewport().setOpaque(false);

        DefaultCodeAreaMouseListener codeAreaMouseListener = new DefaultCodeAreaMouseListener(codeArea, scrollPanel);
        codeArea.addMouseListener(codeAreaMouseListener);
        codeArea.addMouseMotionListener(codeAreaMouseListener);
        codeArea.addMouseWheelListener(codeAreaMouseListener);
        scrollPanel.addMouseListener(codeAreaMouseListener);
        scrollPanel.addMouseMotionListener(codeAreaMouseListener);
        scrollPanel.addMouseWheelListener(codeAreaMouseListener);
    }

    @Override
    public void reset() {
        resetColors();
        resetFont();
        resetLayout();
        resetCaret();
    }

    @Override
    public void resetColors() {
        resetColors = true;
    }

    @Override
    public void resetFont() {
        fontChanged = true;
        resetLayout();
    }

    @Override
    public void resetLayout() {
        layoutChanged = true;
    }

    @Override
    public void resetCaret() {
        caretChanged = true;
    }

    private void recomputeLayout() {
        rowPositionLength = getRowPositionLength();
        recomputeDimensions();

        int charactersPerPage = dimensions.getCharactersPerPage();
        structure.updateCache(codeArea, charactersPerPage);
        codeCharactersCase = ((CodeCharactersCaseCapable) codeArea).getCodeCharactersCase();
        showUnprintables = ((ShowUnprintablesCapable) codeArea).isShowUnprintables();
        minRowPositionLength = ((RowWrappingCapable) codeArea).getMinRowPositionLength();
        maxRowPositionLength = ((RowWrappingCapable) codeArea).getMaxRowPositionLength();
        antialiasingMode = ((AntialiasingCapable) codeArea).getAntialiasingMode();

        int rowsPerPage = dimensions.getRowsPerPage();
        long rowsPerDocument = structure.getRowsPerDocument();
        int charactersPerRow = structure.getCharactersPerRow();

        if (metrics.isInitialized()) {
            scrolling.updateMaximumScrollPosition(rowsPerDocument, rowsPerPage, charactersPerRow, charactersPerPage, dimensions.getLastCharOffset(), dimensions.getLastRowOffset());
        }

        recomputeScrollState();
        layoutChanged = false;
    }

    private void updateCaret() {
        editationOperation = ((EditationModeCapable) codeArea).getEditationOperation();
        showMirrorCursor = ((CaretCapable) codeArea).isShowMirrorCursor();

        caretChanged = false;
    }

    private void recomputeDimensions() {
        int verticalScrollBarSize = getVerticalScrollBarSize();
        int horizontalScrollBarSize = getHorizontalScrollBarSize();
        Insets insets = codeArea.getInsets();
        int componentWidth = codeArea.getWidth() - insets.left - insets.right;
        int componentHeight = codeArea.getHeight() - insets.top - insets.bottom;
        dimensions.recomputeSizes(metrics, insets.right, insets.top, componentWidth, componentHeight, rowPositionLength, verticalScrollBarSize, horizontalScrollBarSize, layoutProfile);
    }

    public void recomputeCharPositions() {
        visibility.recomputeCharPositions(metrics, structure, dimensions, scrolling.getScrollPosition());
        updateRowDataCache();
    }

    private void updateRowDataCache() {
        if (rowDataCache == null) {
            rowDataCache = new RowDataCache();
        }

        rowDataCache.headerChars = new char[structure.getCharactersPerCodeSection()];
        rowDataCache.rowData = new byte[structure.getBytesPerRow() + metrics.getMaxBytesPerChar() - 1];
        rowDataCache.rowPositionCode = new char[rowPositionLength];
        rowDataCache.rowCharacters = new char[structure.getCharactersPerRow()];
        rowDataCache.unprintables = new byte[(structure.getBytesPerRow() + 7) >> 3];
    }

    public void fontChanged(Graphics g) {
        if (font == null) {
            reset();
        }

        charset = ((CharsetCapable) codeArea).getCharset();
        font = ((FontCapable) codeArea).getCodeFont();
        metrics.recomputeMetrics(g.getFontMetrics(font), charset);

        recomputeDimensions();
        recomputeCharPositions();
        initialized = true;
    }

    public void dataViewScrolled(Graphics g) {
        if (!isInitialized()) {
            return;
        }

        recomputeScrollState();
        if (metrics.getCharacterWidth() > 0) {
            recomputeCharPositions();
            paintComponent(g);
        }
    }

    private void recomputeScrollState() {
        scrolling.setScrollPosition(((ScrollingCapable) codeArea).getScrollPosition());
        int characterWidth = metrics.getCharacterWidth();
        int rowHeight = metrics.getRowHeight();

        if (characterWidth > 0) {
            recomputeCharPositions();
        }

        if (rowHeight > 0 && characterWidth > 0) {
            int documentDataWidth = structure.getCharactersPerRow() * characterWidth;
            long rowsPerData = (structure.getDataSize() / structure.getBytesPerRow()) + 1;
            scrolling.updateCache(codeArea);

            int documentDataHeight;
            if (rowsPerData > Integer.MAX_VALUE / rowHeight) {
                scrolling.setScrollBarVerticalScale(ScrollBarVerticalScale.SCALED);
                documentDataHeight = Integer.MAX_VALUE;
            } else {
                scrolling.setScrollBarVerticalScale(ScrollBarVerticalScale.NORMAL);
                documentDataHeight = (int) (rowsPerData * rowHeight);
            }

            dataView.setPreferredSize(new Dimension(documentDataWidth, documentDataHeight));
        }

        // TODO on resize only
        scrollPanel.setBounds(dimensions.getScrollPanelRectangle());
        scrollPanel.revalidate();
    }

    @Override
    public boolean isInitialized() {
        return initialized;
    }

    @Override
    public void paintComponent(Graphics g) {
        if (!initialized) {
            reset();
        }
        updateCache();
        if (font == null) {
            ((FontCapable) codeArea).setCodeFont(codeArea.getFont());
            fontChanged(g);
        }
        if (rowDataCache == null) {
            return;
        }
        if (scrollingState == ScrollingState.SCROLLING_BY_SCROLLBAR) {
            return;
        }

        if (antialiasingMode != AntialiasingMode.OFF && g instanceof Graphics2D) {
            Object antialiasingHint = antialiasingMode.getAntialiasingHint((Graphics2D) g);
            ((Graphics2D) g).setRenderingHint(
                    RenderingHints.KEY_TEXT_ANTIALIASING,
                    antialiasingHint);
        }
        if (layoutChanged) {
            recomputeLayout();
        }

        paintOutsiteArea(g);
        paintHeader(g);
        paintRowPosition(g);
        paintMainArea(g);
    }

    protected synchronized void updateCache() {
        if (resetColors) {
            resetColors = false;
            colorsProfile.reinitialize();
        }
    }

    public void paintOutsiteArea(Graphics g) {
        int headerAreaHeight = dimensions.getHeaderAreaHeight();
        int rowPositionAreaWidth = dimensions.getRowPositionAreaWidth();
        Rectangle componentRect = dimensions.getComponentRect();
        int characterWidth = metrics.getCharacterWidth();
        g.setColor(colorsProfile.getColor(CodeAreaBasicColors.TEXT_BACKGROUND));
        g.fillRect(componentRect.x, componentRect.y, componentRect.width, headerAreaHeight);

        // Decoration lines
        g.setColor(colorsProfile.getColor(BasicCodeAreaDecorationColorType.LINE));
        if (themeProfile.showHeaderLine()) {
            g.drawLine(componentRect.x, componentRect.y + headerAreaHeight - 1, componentRect.x + rowPositionAreaWidth, componentRect.y + headerAreaHeight - 1);
        }

        if (themeProfile.showRowPositionLine()) {
            int lineX = componentRect.x + rowPositionAreaWidth - (characterWidth / 2);
            if (lineX >= componentRect.x) {
                g.drawLine(lineX, componentRect.y, lineX, componentRect.y + headerAreaHeight);
            }
        }

        if (themeProfile.showBoxLine()) {
            g.drawLine(rowPositionAreaWidth - 1, headerAreaHeight - 1, rowPositionAreaWidth, headerAreaHeight - 1);
        }
    }

    public void paintHeader(Graphics g) {
        if (!dimensions.getLayoutProfile().isShowHeader()) {
            return;
        }

        int charactersPerCodeSection = structure.getCharactersPerCodeSection();
        Rectangle headerArea = dimensions.getHeaderAreaRectangle();
        CodeAreaScrollPosition scrollPosition = scrolling.getScrollPosition();

        Rectangle clipBounds = g.getClipBounds();
        g.setClip(clipBounds != null ? clipBounds.intersection(headerArea) : headerArea);

        int characterWidth = metrics.getCharacterWidth();
        int byteCodeWidth = structure.getPositionCodeType().getMaxDigitsForByte() * characterWidth;
        int rowHeight = metrics.getRowHeight();
        int dataViewX = dimensions.getDataViewX();

        g.setFont(font);
        g.setColor(colorsProfile.getColor(CodeAreaBasicColors.TEXT_BACKGROUND));
        g.fillRect(headerArea.x, headerArea.y, headerArea.width, headerArea.height);

        CodeAreaViewMode viewMode = structure.getViewMode();
        if (viewMode == CodeAreaViewMode.DUAL || viewMode == CodeAreaViewMode.CODE_MATRIX) {
            int headerX = dataViewX - scrollPosition.getCharPosition() * characterWidth - scrollPosition.getCharOffset();
            int headerY = headerArea.y + dimensions.getLayoutProfile().getTopHeaderSpace() + rowHeight - metrics.getSubFontSpace();

            Arrays.fill(rowDataCache.headerChars, ' ');

            g.setColor(colorsProfile.getColor(CodeAreaBasicColors.ALTERNATE_BACKGROUND));
            boolean interleaving = false;
            int lastPos = 0;
            int visibleCodeStart = visibility.getVisibleCodeStart();
            int visibleMatrixCodeEnd = visibility.getVisibleMatrixCodeEnd();
            for (int characterOnRow = visibleCodeStart; characterOnRow < visibleMatrixCodeEnd; characterOnRow++) {
                int codePos = structure.computeFirstCodeCharacterPos(characterOnRow);
                if (codePos == lastPos + 2 && !interleaving) {
                    interleaving = true;
                } else {
                    CodeAreaUtils.longToBaseCode(rowDataCache.headerChars, codePos, characterOnRow, structure.getPositionCodeType().getBase(), 2, true, codeCharactersCase);
                    lastPos = codePos;
                    interleaving = false;

                    if (themeProfile.getBackgroundPaintMode() == ExtendedBackgroundPaintMode.GRIDDED
                            || themeProfile.getBackgroundPaintMode() == ExtendedBackgroundPaintMode.CHESSBOARD) {
                        int bytePosition = structure.computePositionByte(codePos);
                        if ((bytePosition & 1) != 0) {
                            g.fillRect(headerX + codePos * characterWidth, headerArea.y, byteCodeWidth, headerArea.height);
                        }
                    }
                }
            }

            g.setColor(colorsProfile.getColor(CodeAreaBasicColors.TEXT_COLOR));
            int visibleCharStart = visibility.getVisibleCharStart();
            int visibleMatrixCharEnd = visibility.getVisibleMatrixCharEnd();
            int renderOffset = visibleCharStart;
            Color renderColor = null;
            for (int characterOnRow = visibleCharStart; characterOnRow < visibleMatrixCharEnd; characterOnRow++) {
                boolean sequenceBreak = false;

                char currentChar = rowDataCache.headerChars[characterOnRow];
                if (currentChar == ' ' && renderOffset == characterOnRow) {
                    renderOffset++;
                    continue;
                }

                Color color = colorsProfile.getColor(CodeAreaBasicColors.TEXT_COLOR);
                if (!CodeAreaSwingUtils.areSameColors(color, renderColor)) { // || !colorType.equals(renderColorType)
                    sequenceBreak = true;
                }
                if (sequenceBreak) {
                    if (renderOffset < characterOnRow) {
                        drawCenteredChars(g, rowDataCache.headerChars, renderOffset, characterOnRow - renderOffset, characterWidth, headerX + renderOffset * characterWidth, headerY);
                    }

                    if (!CodeAreaSwingUtils.areSameColors(color, renderColor)) {
                        renderColor = color;
                        g.setColor(color);
                    }

                    renderOffset = characterOnRow;
                }
            }

            if (renderOffset < charactersPerCodeSection) {
                drawCenteredChars(g, rowDataCache.headerChars, renderOffset, charactersPerCodeSection - renderOffset, characterWidth, headerX + renderOffset * characterWidth, headerY);
            }
        }

        // Decoration lines
        g.setColor(colorsProfile.getColor(BasicCodeAreaDecorationColorType.LINE));

        if (themeProfile.showHeaderLine() || themeProfile.showBoxLine()) {
            g.drawLine(headerArea.x, headerArea.y + headerArea.height - 1, headerArea.x + headerArea.width, headerArea.y + headerArea.height - 1);
        }
        if (themeProfile.showSplitLine()) {
            int lineX = dataViewX + visibility.getPreviewRelativeX() - scrollPosition.getCharPosition() * characterWidth - scrollPosition.getCharOffset() - characterWidth / 2;
            if (lineX >= dataViewX) {
                g.drawLine(lineX, headerArea.y, lineX, headerArea.y + headerArea.height);
            }
        }

        g.setClip(clipBounds);
    }

    public void paintRowPosition(Graphics g) {
        if (!dimensions.getLayoutProfile().isShowRowPosition()) {
            return;
        }

        int bytesPerRow = structure.getBytesPerRow();
        long dataSize = structure.getDataSize();
        int rowHeight = metrics.getRowHeight();
        int characterWidth = metrics.getCharacterWidth();
        int subFontSpace = metrics.getSubFontSpace();
        int rowsPerRect = dimensions.getRowsPerRect();
        Rectangle rowPosRectangle = dimensions.getRowPositionAreaRectangle();
        Rectangle dataViewRectangle = dimensions.getDataViewRectangle();
        Rectangle clipBounds = g.getClipBounds();
        g.setClip(clipBounds != null ? clipBounds.intersection(rowPosRectangle) : rowPosRectangle);

        g.setFont(font);
        g.setColor(colorsProfile.getColor(CodeAreaBasicColors.TEXT_BACKGROUND));
        g.fillRect(rowPosRectangle.x, rowPosRectangle.y, rowPosRectangle.width, rowPosRectangle.height);

        CodeAreaScrollPosition scrollPosition = scrolling.getScrollPosition();
        if (themeProfile.isPaintRowPosBackground()
                && (themeProfile.getBackgroundPaintMode() == ExtendedBackgroundPaintMode.STRIPED
                || themeProfile.getBackgroundPaintMode() == ExtendedBackgroundPaintMode.GRIDDED
                || themeProfile.getBackgroundPaintMode() == ExtendedBackgroundPaintMode.CHESSBOARD)) {
            long dataPosition = scrollPosition.getRowPosition() * bytesPerRow;
            int stripePositionY = rowPosRectangle.y - scrollPosition.getRowOffset() + ((scrollPosition.getRowPosition() & 1) > 0 ? 0 : rowHeight);
            g.setColor(colorsProfile.getColor(CodeAreaBasicColors.ALTERNATE_BACKGROUND));
            for (int row = 0; row <= rowsPerRect / 2; row++) {
                if (dataPosition > dataSize - bytesPerRow) {
                    break;
                }

                g.fillRect(rowPosRectangle.x, stripePositionY, rowPosRectangle.width, rowHeight);
                stripePositionY += rowHeight * 2;
                dataPosition += bytesPerRow * 2;
            }
        }

        long dataPosition = bytesPerRow * scrollPosition.getRowPosition();
        int positionY = rowPosRectangle.y + rowHeight - subFontSpace - scrollPosition.getRowOffset();
        g.setColor(colorsProfile.getColor(CodeAreaBasicColors.TEXT_COLOR));
        for (int row = 0; row <= rowsPerRect; row++) {
            if (dataPosition > dataSize) {
                break;
            }

            CodeAreaUtils.longToBaseCode(rowDataCache.rowPositionCode, 0, dataPosition < 0 ? 0 : dataPosition, structure.getPositionCodeType().getBase(), rowPositionLength, true, CodeCharactersCase.UPPER);
            drawCenteredChars(g, rowDataCache.rowPositionCode, 0, rowPositionLength, characterWidth, rowPosRectangle.x + dimensions.getLayoutProfile().getLeftRowPositionSpace(), positionY);

            positionY += rowHeight;
            dataPosition += bytesPerRow;
        }

        // Decoration lines
        g.setColor(colorsProfile.getColor(BasicCodeAreaDecorationColorType.LINE));
        if (themeProfile.showRowPositionLine()) {
            int lineX = rowPosRectangle.x + rowPosRectangle.width - (characterWidth / 2);
            if (lineX >= rowPosRectangle.x) {
                g.drawLine(lineX, dataViewRectangle.y, lineX, dataViewRectangle.y + dataViewRectangle.height);
            }
            g.drawLine(dataViewRectangle.x, dataViewRectangle.y - 1, dataViewRectangle.x + dataViewRectangle.width, dataViewRectangle.y - 1);
        }
        if (themeProfile.showBoxLine()) {
            if (rowPosRectangle.width >= 0) {
                g.drawLine(rowPosRectangle.width - 1, dataViewRectangle.y, rowPosRectangle.width - 1, dataViewRectangle.y + dataViewRectangle.height);
            }
        }

        g.setClip(clipBounds);
    }

    @Override
    public void paintMainArea(Graphics g) {
        if (!initialized) {
            reset();
        }
        if (fontChanged) {
            fontChanged(g);
            fontChanged = false;
        }

        Rectangle mainAreaRect = dimensions.getMainAreaRect();
        Rectangle dataViewRectangle = dimensions.getDataViewRectangle();
        CodeAreaScrollPosition scrollPosition = scrolling.getScrollPosition();
        int characterWidth = metrics.getCharacterWidth();
        int previewRelativeX = visibility.getPreviewRelativeX();

        Rectangle clipBounds = g.getClipBounds();
        g.setClip(clipBounds != null ? clipBounds.intersection(mainAreaRect) : mainAreaRect);
        paintBackground(g);

        g.setColor(colorsProfile.getColor(BasicCodeAreaDecorationColorType.LINE));
        int lineX = dataViewRectangle.x + previewRelativeX - scrollPosition.getCharPosition() * characterWidth - scrollPosition.getCharOffset() - characterWidth / 2;
        if (themeProfile.showSplitLine() && lineX >= dataViewRectangle.x) {
            g.drawLine(lineX, dataViewRectangle.y, lineX, dataViewRectangle.y + dataViewRectangle.height);
        }

        paintRows(g);
        g.setClip(clipBounds);
        paintCursor(g);

//        {
//            // Display debugging data
//            int rowHeight = metrics.getRowHeight();
//            int x = mainAreaRect.x + mainAreaRect.width - 220;
//            int y = mainAreaRect.y + mainAreaRect.height - 20;
//            g.setColor(Color.YELLOW);
//            g.fillRect(x, y, 200, 16);
//            g.setColor(Color.BLACK);
//            char[] headerCode = (String.valueOf(scrollPosition.getCharPosition()) + "+" + String.valueOf(scrollPosition.getCharOffset()) + " : " + String.valueOf(scrollPosition.getRowPosition()) + "+" + String.valueOf(scrollPosition.getRowOffset()) + " P: " + String.valueOf(paintCounter)).toCharArray();
//            g.drawChars(headerCode, 0, headerCode.length, x, y + rowHeight);
//        }
//
//        paintCounter++;
    }

    /**
     * Paints main area background.
     *
     * @param g graphics
     */
    public void paintBackground(Graphics g) {
        int bytesPerRow = structure.getBytesPerRow();
        long dataSize = structure.getDataSize();
        int rowHeight = metrics.getRowHeight();
        int characterWidth = metrics.getCharacterWidth();
        int rowsPerRect = dimensions.getRowsPerRect();
        int byteCodeWidth = structure.getPositionCodeType().getMaxDigitsForByte() * characterWidth;
        Rectangle dataViewRect = dimensions.getDataViewRectangle();
        CodeAreaScrollPosition scrollPosition = scrolling.getScrollPosition();

        g.setColor(colorsProfile.getColor(CodeAreaBasicColors.TEXT_BACKGROUND));
        if (themeProfile.getBackgroundPaintMode() != ExtendedBackgroundPaintMode.TRANSPARENT) {
            g.fillRect(dataViewRect.x, dataViewRect.y, dataViewRect.width, dataViewRect.height);
        }

        if (themeProfile.getBackgroundPaintMode() == ExtendedBackgroundPaintMode.STRIPED
                || themeProfile.getBackgroundPaintMode() == ExtendedBackgroundPaintMode.GRIDDED
                || themeProfile.getBackgroundPaintMode() == ExtendedBackgroundPaintMode.CHESSBOARD) {
            g.setColor(colorsProfile.getColor(CodeAreaBasicColors.ALTERNATE_BACKGROUND));
            long dataPosition = scrollPosition.getRowPosition() * bytesPerRow;
            int rowAlternatingOffset = (int) (scrollPosition.getRowPosition() & 1);
            int stripePositionY = dataViewRect.y - scrollPosition.getRowOffset();
            for (int row = 0; row <= rowsPerRect; row++) {
                if (dataPosition > dataSize) {
                    break;
                }

                boolean oddRow = (row & 1) != rowAlternatingOffset;
                if (oddRow
                        && (themeProfile.getBackgroundPaintMode() == ExtendedBackgroundPaintMode.STRIPED
                        || themeProfile.getBackgroundPaintMode() == ExtendedBackgroundPaintMode.GRIDDED)) {
                    g.fillRect(dataViewRect.x, stripePositionY, dataViewRect.width, rowHeight);
                }

                if (themeProfile.getBackgroundPaintMode() == ExtendedBackgroundPaintMode.GRIDDED
                        || themeProfile.getBackgroundPaintMode() == ExtendedBackgroundPaintMode.CHESSBOARD) {
                    int codePos = visibility.getVisibleCodeStart();
                    int codeEnd = visibility.getVisibleCodeEnd();
                    while (codePos < codeEnd) {
                        if ((themeProfile.getBackgroundPaintMode() == ExtendedBackgroundPaintMode.GRIDDED && (codePos & 1) != 0 && !oddRow)
                                || themeProfile.getBackgroundPaintMode() == ExtendedBackgroundPaintMode.CHESSBOARD && (codePos & 1) != ((row + rowAlternatingOffset) & 1)) {
                            int charPos = structure.computeFirstCodeCharacterPos(codePos);
                            int positionX = dataViewRect.x - scrollPosition.getCharOffset() - scrollPosition.getCharPosition() * characterWidth + charPos * characterWidth;
                            g.fillRect(positionX, stripePositionY, byteCodeWidth, rowHeight);
                        }
                        codePos++;
                    }
                }

                stripePositionY += rowHeight;
                dataPosition += bytesPerRow;
            }
        }
    }

    public void paintRows(Graphics g) {
        int bytesPerRow = structure.getBytesPerRow();
        int characterWidth = metrics.getCharacterWidth();
        int rowHeight = metrics.getRowHeight();
        int dataViewX = dimensions.getDataViewX();
        int dataViewY = dimensions.getDataViewY();
        int rowsPerRect = dimensions.getRowsPerRect();
        CodeAreaScrollPosition scrollPosition = scrolling.getScrollPosition();
        long dataPosition = scrollPosition.getRowPosition() * bytesPerRow;
        int rowPositionX = dataViewX - scrollPosition.getCharPosition() * characterWidth - scrollPosition.getCharOffset();
        int rowPositionY = dataViewY - scrollPosition.getRowOffset();

        g.setColor(colorsProfile.getColor(CodeAreaBasicColors.TEXT_COLOR));
        for (int row = 0; row <= rowsPerRect; row++) {
            prepareRowData(dataPosition);
            paintRowBackground(g, dataPosition, rowPositionX, rowPositionY);
            paintRowText(g, dataPosition, rowPositionX, rowPositionY);

            rowPositionY += rowHeight;
            dataPosition += bytesPerRow;
        }
    }

    private void prepareRowData(long dataPosition) {
        int maxBytesPerChar = metrics.getMaxBytesPerChar();
        CodeAreaViewMode viewMode = structure.getViewMode();
        int bytesPerRow = structure.getBytesPerRow();
        long dataSize = structure.getDataSize();
        int previewCharPos = structure.getPreviewCharPos();
        CodeType codeType = structure.getCodeType();

        int rowBytesLimit = bytesPerRow;
        int rowStart = 0;
        if (dataPosition < dataSize) {
            int rowDataSize = bytesPerRow + maxBytesPerChar - 1;
            if (dataPosition + rowDataSize > dataSize) {
                rowDataSize = (int) (dataSize - dataPosition);
            }
            if (dataPosition < 0) {
                rowStart = (int) -dataPosition;
            }
            BinaryData data = codeArea.getContentData();
            if (data == null) {
                throw new IllegalStateException("Missing data on nonzero data size");
            }
            data.copyToArray(dataPosition + rowStart, rowDataCache.rowData, rowStart, rowDataSize - rowStart);
            if (dataPosition + rowBytesLimit > dataSize) {
                rowBytesLimit = (int) (dataSize - dataPosition);
            }
        } else {
            rowBytesLimit = 0;
        }

        if (showUnprintables) {
            Arrays.fill(rowDataCache.unprintables, (byte) 0);

            if (unprintableCharactersMapping == null) {
                buildUnprintableCharactersMapping();
            }
        }

        // Fill codes
        if (viewMode != CodeAreaViewMode.TEXT_PREVIEW) {
            int visibleCodeStart = visibility.getVisibleCodeStart();
            int visibleCodeEnd = visibility.getVisibleCodeEnd();
            char targetChar;
            Character replacement;
            for (int byteOnRow = Math.max(visibleCodeStart, rowStart); byteOnRow < Math.min(visibleCodeEnd, rowBytesLimit); byteOnRow++) {
                byte dataByte = rowDataCache.rowData[byteOnRow];
                if (showUnprintables) {
                    int charDataLength = maxBytesPerChar;
                    if (byteOnRow + charDataLength > rowDataCache.rowData.length) {
                        charDataLength = rowDataCache.rowData.length - byteOnRow;
                    }
                    String displayString = new String(rowDataCache.rowData, byteOnRow, charDataLength, charset);
                    if (!displayString.isEmpty()) {
                        targetChar = displayString.charAt(0);
                        replacement = unprintableCharactersMapping.get(targetChar);
                        if (replacement != null) {
                            rowDataCache.unprintables[byteOnRow >> 3] |= 1 << (byteOnRow & 7);
                        }
                    }
                }

                int byteRowPos = structure.computeFirstCodeCharacterPos(byteOnRow);
                if (byteRowPos > 0) {
                    rowDataCache.rowCharacters[byteRowPos - 1] = ' ';
                }
                CodeAreaUtils.byteToCharsCode(dataByte, codeType, rowDataCache.rowCharacters, byteRowPos, codeCharactersCase);
            }
            if (bytesPerRow > rowBytesLimit) {
                Arrays.fill(rowDataCache.rowCharacters, structure.computeFirstCodeCharacterPos(rowBytesLimit), rowDataCache.rowCharacters.length, ' ');
            }
        }

        if (previewCharPos > 0) {
            rowDataCache.rowCharacters[previewCharPos - 1] = ' ';
        }

        // Fill preview characters
        if (viewMode != CodeAreaViewMode.CODE_MATRIX) {
            int visiblePreviewStart = visibility.getVisiblePreviewStart();
            int visiblePreviewEnd = visibility.getVisiblePreviewEnd();
            Character replacement;
            char targetChar;
            for (int byteOnRow = visiblePreviewStart; byteOnRow < Math.min(visiblePreviewEnd, rowBytesLimit); byteOnRow++) {
                byte dataByte = rowDataCache.rowData[byteOnRow];

                if (maxBytesPerChar > 1) {
                    if (dataPosition + maxBytesPerChar > dataSize) {
                        maxBytesPerChar = (int) (dataSize - dataPosition);
                    }

                    int charDataLength = maxBytesPerChar;
                    if (byteOnRow + charDataLength > rowDataCache.rowData.length) {
                        charDataLength = rowDataCache.rowData.length - byteOnRow;
                    }
                    String displayString = new String(rowDataCache.rowData, byteOnRow, charDataLength, charset);
                    if (!displayString.isEmpty()) {
                        targetChar = displayString.charAt(0);
                        if (showUnprintables) {
                            replacement = unprintableCharactersMapping.get(targetChar);
                            if (replacement != null) {
                                rowDataCache.unprintables[byteOnRow >> 3] |= 1 << (byteOnRow & 7);
                                targetChar = replacement;
                            }

                        }

                        rowDataCache.rowCharacters[previewCharPos + byteOnRow] = targetChar;
                    }
                } else {
                    if (charMappingCharset == null || charMappingCharset != charset) {
                        buildCharMapping(charset);
                    }

                    targetChar = charMapping[dataByte & 0xFF];
                    if (showUnprintables) {
                        if (unprintableCharactersMapping == null) {
                            buildUnprintableCharactersMapping();
                        }
                        replacement = unprintableCharactersMapping.get(targetChar);
                        if (replacement != null) {
                            rowDataCache.unprintables[byteOnRow >> 3] |= 1 << (byteOnRow & 7);
                            targetChar = replacement;
                        }

                        rowDataCache.rowCharacters[previewCharPos + byteOnRow] = targetChar;
                    } else {
                        rowDataCache.rowCharacters[previewCharPos + byteOnRow] = targetChar;
                    }
                }
            }
            if (bytesPerRow > rowBytesLimit) {
                Arrays.fill(rowDataCache.rowCharacters, previewCharPos + rowBytesLimit, previewCharPos + bytesPerRow, ' ');
            }
        }
    }

    /**
     * Paints row background.
     *
     * @param g graphics
     * @param rowDataPosition row data position
     * @param rowPositionX row position X
     * @param rowPositionY row position Y
     */
    public void paintRowBackground(Graphics g, long rowDataPosition, int rowPositionX, int rowPositionY) {
        int previewCharPos = structure.getPreviewCharPos();
        CodeAreaViewMode viewMode = structure.getViewMode();
        int charactersPerRow = structure.getCharactersPerRow();
        int visibleCharStart = visibility.getVisibleCharStart();
        int visibleCharEnd = visibility.getVisibleCharEnd();

        int renderOffset = visibleCharStart;
        Color renderColor = null;
        boolean unprintable;
        for (int charOnRow = visibleCharStart; charOnRow < visibleCharEnd; charOnRow++) {
            CodeAreaSection section;
            int byteOnRow;
            if (charOnRow >= previewCharPos && viewMode != CodeAreaViewMode.CODE_MATRIX) {
                byteOnRow = charOnRow - previewCharPos;
                section = BasicCodeAreaSection.TEXT_PREVIEW;
            } else {
                byteOnRow = structure.computePositionByte(charOnRow);
                section = BasicCodeAreaSection.CODE_MATRIX;
            }
            boolean sequenceBreak = false;

            unprintable = showUnprintables && (rowDataCache.unprintables[byteOnRow >> 3] & (1 << (byteOnRow & 7))) != 0;
            Color color = getPositionBackgroundColor(rowDataPosition, byteOnRow, charOnRow, section, unprintable);
            if (!CodeAreaSwingUtils.areSameColors(color, renderColor)) {
                sequenceBreak = true;
            }
            if (sequenceBreak) {
                if (renderOffset < charOnRow) {
                    if (renderColor != null) {
                        renderBackgroundSequence(g, renderOffset, charOnRow, rowPositionX, rowPositionY);
                    }
                }

                if (!CodeAreaSwingUtils.areSameColors(color, renderColor)) {
                    renderColor = color;
                    if (color != null) {
                        g.setColor(color);
                    }
                }

                renderOffset = charOnRow;
            }
        }

        if (renderOffset < charactersPerRow) {
            if (renderColor != null) {
                renderBackgroundSequence(g, renderOffset, charactersPerRow, rowPositionX, rowPositionY);
            }
        }
    }

    /**
     * Returns background color for particular code.
     *
     * @param rowDataPosition row data position
     * @param byteOnRow byte on current row
     * @param charOnRow character on current row
     * @param section current section
     * @param unprintable flag for unprintable characters
     * @return color or null for default color
     */
    @Nullable
    public Color getPositionBackgroundColor(long rowDataPosition, int byteOnRow, int charOnRow, CodeAreaSection section, boolean unprintable) {
        SelectionRange selectionRange = structure.getSelectionRange();
        int codeLastCharPos = structure.getCodeLastCharPos();
        CodeAreaCaretPosition caretPosition = structure.getCaretPosition();
        boolean inSelection = selectionRange != null && selectionRange.isInSelection(rowDataPosition + byteOnRow);
        if (inSelection && (section == BasicCodeAreaSection.CODE_MATRIX)) {
            if (charOnRow == codeLastCharPos) {
                inSelection = false;
            }
        }

        if (inSelection) {
            return section == caretPosition.getSection() ? colorsProfile.getColor(CodeAreaBasicColors.SELECTION_BACKGROUND) : colorsProfile.getColor(CodeAreaBasicColors.SELECTION_MIRROR_BACKGROUND);
        }

        if (showUnprintables && unprintable) {
//            return colors.getColorType.UNPRINTABLES_BACKGROUND;
        }

        return null;
    }

    @Nullable
    @Override
    public PositionScrollVisibility computePositionScrollVisibility(CaretPosition caretPosition) {
        int bytesPerRow = structure.getBytesPerRow();
        int previewCharPos = structure.getPreviewCharPos();
        int characterWidth = metrics.getCharacterWidth();
        int rowHeight = metrics.getRowHeight();
        int dataViewWidth = dimensions.getDataViewWidth();
        int dataViewHeight = dimensions.getDataViewHeight();
        int rowsPerPage = dimensions.getRowsPerPage();
        int charactersPerPage = dimensions.getCharactersPerPage();

        long shiftedPosition = caretPosition.getDataPosition();
        long rowPosition = shiftedPosition / bytesPerRow;
        int byteOffset = (int) (shiftedPosition % bytesPerRow);
        int charPosition;
        if (caretPosition.getSection() == BasicCodeAreaSection.TEXT_PREVIEW) {
            charPosition = previewCharPos + byteOffset;
        } else {
            charPosition = structure.computeFirstCodeCharacterPos(byteOffset) + caretPosition.getCodeOffset();
        }

        return scrolling.computePositionScrollVisibility(rowPosition, charPosition, bytesPerRow, previewCharPos, rowsPerPage, charactersPerPage, dataViewWidth, dataViewHeight, characterWidth, rowHeight);
    }

    @Nullable
    @Override
    public CodeAreaScrollPosition computeRevealScrollPosition(CaretPosition caretPosition) {
        int bytesPerRow = structure.getBytesPerRow();
        int previewCharPos = structure.getPreviewCharPos();
        int characterWidth = metrics.getCharacterWidth();
        int rowHeight = metrics.getRowHeight();
        int dataViewWidth = dimensions.getDataViewWidth();
        int dataViewHeight = dimensions.getDataViewHeight();
        int rowsPerPage = dimensions.getRowsPerPage();
        int charactersPerPage = dimensions.getCharactersPerPage();

        long shiftedPosition = caretPosition.getDataPosition();
        long rowPosition = shiftedPosition / bytesPerRow;
        int byteOffset = (int) (shiftedPosition % bytesPerRow);
        int charPosition;
        if (caretPosition.getSection() == BasicCodeAreaSection.TEXT_PREVIEW) {
            charPosition = previewCharPos + byteOffset;
        } else {
            charPosition = structure.computeFirstCodeCharacterPos(byteOffset) + caretPosition.getCodeOffset();
        }

        return scrolling.computeRevealScrollPosition(rowPosition, charPosition, bytesPerRow, previewCharPos, rowsPerPage, charactersPerPage, dataViewWidth, dataViewHeight, characterWidth, rowHeight);
    }

    @Override
    public CodeAreaScrollPosition computeCenterOnScrollPosition(CaretPosition caretPosition) {
        int bytesPerRow = structure.getBytesPerRow();
        int previewCharPos = structure.getPreviewCharPos();
        int characterWidth = metrics.getCharacterWidth();
        int rowHeight = metrics.getRowHeight();
        int dataViewWidth = dimensions.getDataViewWidth();
        int dataViewHeight = dimensions.getDataViewHeight();
        int rowsPerRect = dimensions.getRowsPerRect();
        int charactersPerRect = dimensions.getCharactersPerRect();

        long shiftedPosition = caretPosition.getDataPosition();
        long rowPosition = shiftedPosition / bytesPerRow;
        int byteOffset = (int) (shiftedPosition % bytesPerRow);
        int charPosition;
        if (caretPosition.getSection() == BasicCodeAreaSection.TEXT_PREVIEW) {
            charPosition = previewCharPos + byteOffset;
        } else {
            charPosition = structure.computeFirstCodeCharacterPos(byteOffset) + caretPosition.getCodeOffset();
        }

        return scrolling.computeCenterOnScrollPosition(rowPosition, charPosition, bytesPerRow, previewCharPos, rowsPerRect, charactersPerRect, dataViewWidth, dataViewHeight, characterWidth, rowHeight);
    }

    /**
     * Paints row text.
     *
     * @param g graphics
     * @param rowDataPosition row data position
     * @param rowPositionX row position X
     * @param rowPositionY row position Y
     */
    public void paintRowText(Graphics g, long rowDataPosition, int rowPositionX, int rowPositionY) {
        int previewCharPos = structure.getPreviewCharPos();
        int charactersPerRow = structure.getCharactersPerRow();
        int rowHeight = metrics.getRowHeight();
        int characterWidth = metrics.getCharacterWidth();
        int subFontSpace = metrics.getSubFontSpace();

        g.setFont(font);
        int positionY = rowPositionY + rowHeight - subFontSpace;

        Color lastColor = null;
        Color renderColor = null;

        boolean unprintables = false;
        int visibleCharStart = visibility.getVisibleCharStart();
        int visibleCharEnd = visibility.getVisibleCharEnd();
        int renderOffset = visibleCharStart;
        for (int charOnRow = visibleCharStart; charOnRow < visibleCharEnd; charOnRow++) {
            CodeAreaSection section;
            int byteOnRow;
            if (charOnRow >= previewCharPos) {
                byteOnRow = charOnRow - previewCharPos;
                section = BasicCodeAreaSection.TEXT_PREVIEW;
            } else {
                byteOnRow = structure.computePositionByte(charOnRow);
                section = BasicCodeAreaSection.CODE_MATRIX;
            }

            boolean currentUnprintables = false;
            if (showUnprintables) {
                currentUnprintables = (rowDataCache.unprintables[byteOnRow >> 3] & (1 << (byteOnRow & 7))) != 0;
            }

            char currentChar = rowDataCache.rowCharacters[charOnRow];
            if (currentChar == ' ' && renderOffset == charOnRow) {
                renderOffset++;
                continue;
            }

            Color color = getPositionTextColor(rowDataPosition, byteOnRow, charOnRow, section, currentUnprintables);
            if (color == null) {
                color = colorsProfile.getColor(CodeAreaBasicColors.TEXT_COLOR);
            }

            boolean sequenceBreak = false;
            if (!CodeAreaSwingUtils.areSameColors(color, renderColor)) {
                if (renderColor == null) {
                    renderColor = color;
                }

                sequenceBreak = true;
            }

            if (unprintables != currentUnprintables) {
                sequenceBreak = true;
            }

            if (sequenceBreak) {
                if (!CodeAreaSwingUtils.areSameColors(lastColor, renderColor)) {
                    g.setColor(renderColor);
                    lastColor = renderColor;
                }

                if (charOnRow > renderOffset) {
                    drawCenteredChars(g, rowDataCache.rowCharacters, renderOffset, charOnRow - renderOffset, characterWidth, rowPositionX + renderOffset * characterWidth, positionY);
                }

                renderColor = color;
                if (!CodeAreaSwingUtils.areSameColors(lastColor, renderColor)) {
                    g.setColor(renderColor);
                    lastColor = renderColor;
                }

                renderOffset = charOnRow;
                unprintables = currentUnprintables;
            }
        }

        if (renderOffset < charactersPerRow) {
            if (!CodeAreaSwingUtils.areSameColors(lastColor, renderColor)) {
                g.setColor(renderColor);
            }

            drawCenteredChars(g, rowDataCache.rowCharacters, renderOffset, charactersPerRow - renderOffset, characterWidth, rowPositionX + renderOffset * characterWidth, positionY);
        }
    }

    /**
     * Returns background color for particular code.
     *
     * @param rowDataPosition row data position
     * @param byteOnRow byte on current row
     * @param charOnRow character on current row
     * @param section current section
     * @param unprintable flag for unprintable characters
     * @return color or null for default color
     */
    @Nullable
    public Color getPositionTextColor(long rowDataPosition, int byteOnRow, int charOnRow, CodeAreaSection section, boolean unprintable) {
        SelectionRange selectionRange = structure.getSelectionRange();
        CodeAreaCaretPosition caretPosition = structure.getCaretPosition();
        boolean inSelection = selectionRange != null && selectionRange.isInSelection(rowDataPosition + byteOnRow);

        if (unprintable && section == BasicCodeAreaSection.TEXT_PREVIEW) {
            return colorsProfile.getColor(CodeAreaUnprintablesColorType.UNPRINTABLES_COLOR, CodeAreaBasicColors.TEXT_COLOR);
        }

        if (inSelection) {
            return section == caretPosition.getSection() ? colorsProfile.getColor(CodeAreaBasicColors.SELECTION_COLOR) : colorsProfile.getColor(CodeAreaBasicColors.SELECTION_MIRROR_COLOR);
        }

        return null;
    }

    @Override
    public void paintCursor(Graphics g) {
        if (!codeArea.hasFocus()) {
            return;
        }

        if (caretChanged) {
            updateCaret();
        }

        int maxBytesPerChar = metrics.getMaxBytesPerChar();
        Rectangle mainAreaRect = dimensions.getMainAreaRect();
        CodeType codeType = structure.getCodeType();
        CodeAreaViewMode viewMode = structure.getViewMode();
        if (cursorDataCache == null) {
            cursorDataCache = new CursorDataCache();
        }
        int cursorCharsLength = codeType.getMaxDigitsForByte();
        if (cursorDataCache.cursorCharsLength != cursorCharsLength) {
            cursorDataCache.cursorCharsLength = cursorCharsLength;
            cursorDataCache.cursorChars = new char[cursorCharsLength];
        }
        int cursorDataLength = maxBytesPerChar;
        if (cursorDataCache.cursorDataLength != cursorDataLength) {
            cursorDataCache.cursorDataLength = cursorDataLength;
            cursorDataCache.cursorData = new byte[cursorDataLength];
        }

        DefaultCodeAreaCaret caret = (DefaultCodeAreaCaret) ((CaretCapable) codeArea).getCaret();
        Rectangle cursorRect = getCursorPositionRect(caret.getDataPosition(), caret.getCodeOffset(), caret.getSection());
        if (cursorRect.isEmpty()) {
            return;
        }

        Rectangle scrolledCursorRect = new Rectangle(cursorRect.x, cursorRect.y, cursorRect.width, cursorRect.height);
        Rectangle clipBounds = g.getClipBounds();
        Rectangle intersection = scrolledCursorRect.intersection(mainAreaRect);
        boolean cursorVisible = caret.isCursorVisible() && !intersection.isEmpty();

        if (cursorVisible) {
            g.setClip(intersection);
            DefaultCodeAreaCaret.CursorRenderingMode renderingMode = caret.getRenderingMode();
            g.setColor(colorsProfile.getColor(CodeAreaBasicColors.CURSOR_COLOR));

            paintCursorRect(g, intersection.x, intersection.y, intersection.width, intersection.height, renderingMode, caret);
        }

        // Paint mirror cursor
        if (viewMode == CodeAreaViewMode.DUAL && showMirrorCursor) {
            updateMirrorCursorRect(caret.getDataPosition(), caret.getSection());
            Rectangle mirrorCursorRect = cursorDataCache.mirrorCursorRect;
            if (!mirrorCursorRect.isEmpty()) {
                intersection = mainAreaRect.intersection(mirrorCursorRect);
                boolean mirrorCursorVisible = !intersection.isEmpty();
                if (mirrorCursorVisible) {
                    g.setClip(intersection);
                    g.setColor(colorsProfile.getColor(CodeAreaBasicColors.CURSOR_COLOR));
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setStroke(cursorDataCache.dashedStroke);
                    g2d.drawRect(mirrorCursorRect.x, mirrorCursorRect.y, mirrorCursorRect.width - 1, mirrorCursorRect.height - 1);
                }
            }
        }
        g.setClip(clipBounds);
    }

    private void paintCursorRect(Graphics g, int cursorX, int cursorY, int width, int height, DefaultCodeAreaCaret.CursorRenderingMode renderingMode, DefaultCodeAreaCaret caret) {
        switch (renderingMode) {
            case PAINT: {
                g.fillRect(cursorX, cursorY, width, height);
                break;
            }
            case XOR: {
                g.setXORMode(colorsProfile.getColor(CodeAreaBasicColors.TEXT_BACKGROUND));
                g.fillRect(cursorX, cursorY, width, height);
                g.setPaintMode();
                break;
            }
            case NEGATIVE: {
                int characterWidth = metrics.getCharacterWidth();
                int rowHeight = metrics.getRowHeight();
                int maxBytesPerChar = metrics.getMaxBytesPerChar();
                int subFontSpace = metrics.getSubFontSpace();
                int dataViewX = dimensions.getDataViewX();
                int dataViewY = dimensions.getDataViewY();
                int previewRelativeX = visibility.getPreviewRelativeX();

                CodeAreaViewMode viewMode = structure.getViewMode();
                CodeAreaScrollPosition scrollPosition = scrolling.getScrollPosition();
                long dataSize = structure.getDataSize();
                CodeType codeType = structure.getCodeType();
                g.fillRect(cursorX, cursorY, width, height);
                g.setColor(colorsProfile.getColor(CodeAreaBasicColors.CURSOR_NEGATIVE_COLOR));
                BinaryData contentData = codeArea.getContentData();
                int row = (cursorY + scrollPosition.getRowOffset() - dataViewY) / rowHeight;
                int scrolledX = cursorX + scrollPosition.getCharPosition() * characterWidth + scrollPosition.getCharOffset();
                int posY = dataViewY + (row + 1) * rowHeight - subFontSpace - scrollPosition.getRowOffset();
                long dataPosition = caret.getDataPosition();
                if (viewMode != CodeAreaViewMode.CODE_MATRIX && caret.getSection() == BasicCodeAreaSection.TEXT_PREVIEW) {
                    int charPos = (scrolledX - previewRelativeX) / characterWidth;
                    if (dataPosition >= dataSize) {
                        break;
                    }

                    char[] previewChars = new char[1];

                    if (maxBytesPerChar > 1) {
                        int charDataLength = maxBytesPerChar;
                        if (dataPosition + maxBytesPerChar > dataSize) {
                            charDataLength = (int) (dataSize - dataPosition);
                        }

                        if (contentData == null) {
                            previewChars[0] = ' ';
                        } else {
                            contentData.copyToArray(dataPosition, cursorDataCache.cursorData, 0, charDataLength);
                            String displayString = new String(cursorDataCache.cursorData, 0, charDataLength, charset);
                            if (!displayString.isEmpty()) {
                                previewChars[0] = displayString.charAt(0);
                            }
                        }
                    } else {
                        if (charMappingCharset == null || charMappingCharset != charset) {
                            buildCharMapping(charset);
                        }

                        if (contentData == null) {
                            previewChars[0] = ' ';
                        } else {
                            previewChars[0] = charMapping[contentData.getByte(dataPosition) & 0xFF];
                        }
                    }
                    int posX = previewRelativeX + charPos * characterWidth - scrollPosition.getCharPosition() * characterWidth - scrollPosition.getCharOffset();
                    drawCenteredChars(g, previewChars, 0, 1, characterWidth, posX, posY);
                } else {
                    int charPos = (scrolledX - dataViewX) / characterWidth;
                    int byteOffset = structure.computePositionByte(charPos);
                    int codeCharPos = structure.computeFirstCodeCharacterPos(byteOffset);

                    if (contentData != null && dataPosition < dataSize) {
                        byte dataByte = contentData.getByte(dataPosition);
                        CodeAreaUtils.byteToCharsCode(dataByte, codeType, cursorDataCache.cursorChars, 0, codeCharactersCase);
                    } else {
                        Arrays.fill(cursorDataCache.cursorChars, ' ');
                    }
                    int posX = dataViewX + codeCharPos * characterWidth - scrollPosition.getCharPosition() * characterWidth - scrollPosition.getCharOffset();
                    int charsOffset = charPos - codeCharPos;
                    drawCenteredChars(g, cursorDataCache.cursorChars, charsOffset, 1, characterWidth, posX + (charsOffset * characterWidth), posY);
                }
                break;
            }
            default:
                throw new IllegalStateException("Unexpected rendering mode " + renderingMode.name());
        }
    }

    @Nonnull
    @Override
    public CaretPosition mousePositionToClosestCaretPosition(int positionX, int positionY, PositionOverflowMode overflowMode) {
        CodeAreaCaretPosition caret = new CodeAreaCaretPosition();
        CodeAreaScrollPosition scrollPosition = scrolling.getScrollPosition();
        int characterWidth = metrics.getCharacterWidth();
        int rowHeight = metrics.getRowHeight();
        int rowPositionAreaWidth = dimensions.getRowPositionAreaWidth();
        int headerAreaHeight = dimensions.getHeaderAreaHeight();

        int diffX = 0;
        if (positionX < rowPositionAreaWidth) {
            if (overflowMode == PositionOverflowMode.OVERFLOW) {
                diffX = 1;
            }
            positionX = rowPositionAreaWidth;
        }
        int cursorCharX = (positionX - rowPositionAreaWidth + scrollPosition.getCharOffset()) / characterWidth + scrollPosition.getCharPosition() - diffX;
        if (cursorCharX < 0) {
            cursorCharX = 0;
        }

        int diffY = 0;
        if (positionY < headerAreaHeight) {
            if (overflowMode == PositionOverflowMode.OVERFLOW) {
                diffY = 1;
            }
            positionY = headerAreaHeight;
        }
        long cursorRowY = (positionY - headerAreaHeight + scrollPosition.getRowOffset()) / rowHeight + scrollPosition.getRowPosition() - diffY;
        if (cursorRowY < 0) {
            cursorRowY = 0;
        }

        CodeAreaViewMode viewMode = structure.getViewMode();
        int previewCharPos = structure.getPreviewCharPos();
        int bytesPerRow = structure.getBytesPerRow();
        CodeType codeType = structure.getCodeType();
        long dataSize = structure.getDataSize();
        long dataPosition;
        int codeOffset = 0;
        int byteOnRow;
        if ((viewMode == CodeAreaViewMode.DUAL && cursorCharX < previewCharPos) || viewMode == CodeAreaViewMode.CODE_MATRIX) {
            caret.setSection(BasicCodeAreaSection.CODE_MATRIX);
            byteOnRow = structure.computePositionByte(cursorCharX);
            if (byteOnRow >= bytesPerRow) {
                codeOffset = 0;
            } else {
                codeOffset = cursorCharX - structure.computeFirstCodeCharacterPos(byteOnRow);
                if (codeOffset >= codeType.getMaxDigitsForByte()) {
                    codeOffset = codeType.getMaxDigitsForByte() - 1;
                }
            }
        } else {
            caret.setSection(BasicCodeAreaSection.TEXT_PREVIEW);
            byteOnRow = cursorCharX;
            if (viewMode == CodeAreaViewMode.DUAL) {
                byteOnRow -= previewCharPos;
            }
        }

        if (byteOnRow >= bytesPerRow) {
            byteOnRow = bytesPerRow - 1;
        }

        dataPosition = byteOnRow + (cursorRowY * bytesPerRow);
        if (dataPosition < 0) {
            dataPosition = 0;
            codeOffset = 0;
        }

        if (dataPosition >= dataSize) {
            dataPosition = dataSize;
            codeOffset = 0;
        }

        caret.setDataPosition(dataPosition);
        caret.setCodeOffset(codeOffset);
        return caret;
    }

    @Override
    public CaretPosition computeMovePosition(CaretPosition position, MovementDirection direction) {
        return structure.computeMovePosition(position, direction, dimensions.getRowsPerPage());
    }

    @Nonnull
    @Override
    public CodeAreaScrollPosition computeScrolling(CodeAreaScrollPosition startPosition, ScrollingDirection direction) {
        int rowsPerPage = dimensions.getRowsPerPage();
        long rowsPerDocument = structure.getRowsPerDocument();
        return scrolling.computeScrolling(startPosition, direction, rowsPerPage, rowsPerDocument);
    }

    /**
     * Returns relative cursor position in code area or null if cursor is not
     * visible.
     *
     * @param dataPosition data position
     * @param codeOffset code offset
     * @param section section
     * @return cursor position or null
     */
    @Nullable
    public Point getPositionPoint(long dataPosition, int codeOffset, CodeAreaSection section) {
        int bytesPerRow = structure.getBytesPerRow();
        int rowsPerRect = dimensions.getRowsPerRect();
        int characterWidth = metrics.getCharacterWidth();
        int rowHeight = metrics.getRowHeight();

        CodeAreaScrollPosition scrollPosition = scrolling.getScrollPosition();
        long row = dataPosition / bytesPerRow - scrollPosition.getRowPosition();
        if (row < -1 || row > rowsPerRect) {
            return null;
        }

        int byteOffset = (int) (dataPosition % bytesPerRow);

        Rectangle dataViewRect = dimensions.getDataViewRectangle();
        int caretY = (int) (dataViewRect.y + row * rowHeight) - scrollPosition.getRowOffset();
        int caretX;
        if (section == BasicCodeAreaSection.TEXT_PREVIEW) {
            caretX = dataViewRect.x + visibility.getPreviewRelativeX() + characterWidth * byteOffset;
        } else {
            caretX = dataViewRect.x + characterWidth * (structure.computeFirstCodeCharacterPos(byteOffset) + codeOffset);
        }
        caretX -= scrollPosition.getCharPosition() * characterWidth + scrollPosition.getCharOffset();

        return new Point(caretX, caretY);
    }

    private void updateMirrorCursorRect(long dataPosition, CodeAreaSection section) {
        CodeType codeType = structure.getCodeType();
        Point mirrorCursorPoint = getPositionPoint(dataPosition, 0, section == BasicCodeAreaSection.CODE_MATRIX ? BasicCodeAreaSection.TEXT_PREVIEW : BasicCodeAreaSection.CODE_MATRIX);
        if (mirrorCursorPoint == null) {
            cursorDataCache.mirrorCursorRect.setSize(0, 0);
        } else {
            cursorDataCache.mirrorCursorRect.setBounds(mirrorCursorPoint.x, mirrorCursorPoint.y, metrics.getCharacterWidth() * (section == BasicCodeAreaSection.TEXT_PREVIEW ? codeType.getMaxDigitsForByte() : 1), metrics.getRowHeight());
        }
    }

    @Override
    public int getMouseCursorShape(int positionX, int positionY) {
        int dataViewX = dimensions.getDataViewX();
        int dataViewY = dimensions.getDataViewY();
        int scrollPanelWidth = dimensions.getScrollPanelWidth();
        int scrollPanelHeight = dimensions.getScrollPanelHeight();
        if (positionX >= dataViewX && positionX < dataViewX + scrollPanelWidth
                && positionY >= dataViewY && positionY < dataViewY + scrollPanelHeight) {
            return Cursor.TEXT_CURSOR;
        }

        return Cursor.DEFAULT_CURSOR;
    }

    @Override
    public BasicCodeAreaZone getPositionZone(int positionX, int positionY) {
        return dimensions.getPositionZone(positionX, positionY);
    }

    @Nonnull
    @Override
    public CodeAreaColorsProfile getColorsProfile() {
        return colorsProfile;
    }

    @Override
    public void setColorsProfile(CodeAreaColorsProfile colorsProfile) {
        this.colorsProfile = colorsProfile;
        codeArea.repaint();
    }

    @Nonnull
    @Override
    public ExtendedCodeAreaLayoutProfile getLayoutProfile() {
        return ExtendedCodeAreaLayoutProfile.createCopy(layoutProfile);
    }

    @Override
    public void setLayoutProfile(ExtendedCodeAreaLayoutProfile layoutProfile) {
        this.layoutProfile = ExtendedCodeAreaLayoutProfile.createCopy(layoutProfile);
        resetLayout();
    }

    @Override
    public ExtendedCodeAreaThemeProfile getThemeProfile() {
        return ExtendedCodeAreaThemeProfile.createCopy(themeProfile);
    }

    @Override
    public void setThemeProfile(ExtendedCodeAreaThemeProfile themeProfile) {
        this.themeProfile = ExtendedCodeAreaThemeProfile.createCopy(themeProfile);
        codeArea.repaint();
    }

    /**
     * Draws characters centering it to cells of the same width.
     *
     * @param g graphics
     * @param drawnChars array of chars
     * @param charOffset index of target character in array
     * @param length number of charaters to draw
     * @param cellWidth width of cell to center into
     * @param positionX X position of drawing area start
     * @param positionY Y position of drawing area start
     */
    protected void drawCenteredChars(Graphics g, char[] drawnChars, int charOffset, int length, int cellWidth, int positionX, int positionY) {
        int pos = 0;
        int group = 0;
        while (pos < length) {
            int charWidth = metrics.getCharWidth(drawnChars[charOffset + pos]);

            boolean groupable;
            if (metrics.hasUniformLineMetrics()) {
                groupable = charWidth == cellWidth;
            } else {
                int charsWidth = metrics.getCharsWidth(drawnChars, charOffset + pos - group, group + 1);
                groupable = charsWidth == cellWidth * (group + 1);
            }

            if (groupable) {
                group++;
            } else {
                if (group > 0) {
                    drawShiftedChars(g, drawnChars, charOffset + pos - group, group, positionX + (pos - group) * cellWidth, positionY);
                    group = 0;
                }
                drawShiftedChars(g, drawnChars, charOffset + pos, 1, positionX + pos * cellWidth + ((cellWidth - charWidth) >> 1), positionY);
            }
            pos++;
        }
        if (group > 0) {
            drawShiftedChars(g, drawnChars, charOffset + pos - group, group, positionX + (pos - group) * cellWidth, positionY);
        }
    }

    protected void drawShiftedChars(Graphics g, char[] drawnChars, int charOffset, int length, int positionX, int positionY) {
        g.drawChars(drawnChars, charOffset, length, positionX, positionY);
    }

    /**
     * Precomputes widths for basic ascii characters.
     *
     * @param charset
     */
    private void buildCharMapping(Charset charset) {
        for (int i = 0; i < 256; i++) {
            charMapping[i] = new String(new byte[]{(byte) i}, charset).charAt(0);
        }
        charMappingCharset = charset;
    }

    private int getRowPositionLength() {
        if (minRowPositionLength > 0 && minRowPositionLength == maxRowPositionLength) {
            return minRowPositionLength;
        }

        PositionCodeType positionCodeType = ((PositionCodeTypeCapable) codeArea).getPositionCodeType();
        long dataSize = codeArea.getDataSize();
        if (dataSize == 0) {
            return 1;
        }

        double natLog = Math.log(dataSize == Long.MAX_VALUE ? dataSize : dataSize + 1);
        int positionLength = (int) Math.ceil(natLog / positionCodeType.getBaseLog());
        if (minRowPositionLength > 0 && positionLength < minRowPositionLength) {
            positionLength = minRowPositionLength;
        }
        if (maxRowPositionLength > 0 && positionLength > maxRowPositionLength) {
            positionLength = maxRowPositionLength;
        }

        return positionLength == 0 ? 1 : positionLength;
    }

    /**
     * Returns cursor rectangle.
     *
     * @param dataPosition data position
     * @param codeOffset code offset
     * @param section section
     * @return cursor rectangle or empty rectangle
     */
    @Nonnull
    public Rectangle getCursorPositionRect(long dataPosition, int codeOffset, CodeAreaSection section) {
        Rectangle rect = new Rectangle();
        updateRectToCursorPosition(rect, dataPosition, codeOffset, section);
        return rect;
    }

    protected void updateRectToCursorPosition(Rectangle rect, long dataPosition, int codeOffset, CodeAreaSection section) {
        int characterWidth = metrics.getCharacterWidth();
        int rowHeight = metrics.getRowHeight();
        Point cursorPoint = getPositionPoint(dataPosition, codeOffset, section);
        if (cursorPoint == null) {
            rect.setBounds(0, 0, 0, 0);
        } else {
            DefaultCodeAreaCaret.CursorShape cursorShape = editationOperation == EditationOperation.INSERT ? DefaultCodeAreaCaret.CursorShape.INSERT : DefaultCodeAreaCaret.CursorShape.OVERWRITE;
            int cursorThickness = DefaultCodeAreaCaret.getCursorThickness(cursorShape, characterWidth, rowHeight);
            rect.setBounds(cursorPoint.x, cursorPoint.y, cursorThickness, rowHeight);
        }
    }

    /**
     * Renders sequence of background rectangles.
     *
     * Doesn't include character at offset end.
     */
    private void renderBackgroundSequence(Graphics g, int startOffset, int endOffset, int rowPositionX, int positionY) {
        int characterWidth = metrics.getCharacterWidth();
        int rowHeight = metrics.getRowHeight();
        g.fillRect(rowPositionX + startOffset * characterWidth, positionY, (endOffset - startOffset) * characterWidth, rowHeight);
    }

//    @Override
//    public void paintHeader(Graphics g) {
//        CodeArea.ScrollPosition scrollPosition = codeArea.getScrollPosition();
//        Rectangle compRect = codeArea.getComponentRectangle();
//        Rectangle codeRect = codeArea.getCodeSectionRectangle();
//        boolean monospaceFont = codeArea.isMonospaceFontDetected();
//        FontMetrics fontMetrics = codeArea.getFontMetrics();
//        int codeDigits = codeArea.getCodeType().getMaxDigits();
//        if (codeArea.getViewMode() != ViewMode.TEXT_PREVIEW) {
//            int charWidth = codeArea.getCharWidth();
//            int bytesPerLine = codeArea.getBytesPerLine();
//            int charsPerLine = codeArea.computeByteCharPos(bytesPerLine, false);
//            int headerX = codeRect.x - scrollPosition.getScrollCharPosition() * charWidth - scrollPosition.getScrollCharOffset();
//            int headerY = codeArea.getInsets().top + codeArea.getLineHeight() - codeArea.getSubFontSpace();
//
//            int visibleCharStart = (scrollPosition.getScrollCharPosition() * charWidth + scrollPosition.getScrollCharOffset()) / charWidth;
//            if (visibleCharStart < 0) {
//                visibleCharStart = 0;
//            }
//            int visibleCharEnd = (codeRect.width + (scrollPosition.getScrollCharPosition() + charsPerLine) * charWidth + scrollPosition.getScrollCharOffset()) / charWidth;
//            if (visibleCharEnd > charsPerLine) {
//                visibleCharEnd = charsPerLine;
//            }
//            int visibleStart = codeArea.computeByteOffsetPerCodeCharOffset(visibleCharStart);
//            int visibleEnd = codeArea.computeByteOffsetPerCodeCharOffset(visibleCharEnd - 1) + 1;
//
//            if (codeArea.getBackgroundMode() == CodeArea.BackgroundMode.GRIDDED) {
//                ColorsGroup stripColors = codeArea.getAlternateColors();
//                g.setColor(stripColors.getBackgroundColor());
//                int positionX = codeRect.x - scrollPosition.getScrollCharOffset() - scrollPosition.getScrollCharPosition() * charWidth;
//                for (int i = visibleStart / 2; i < visibleEnd / 2; i++) {
//                    g.fillRect(positionX + charWidth * codeArea.computeByteCharPos(i * 2 + 1), compRect.y, charWidth * codeDigits, codeRect.y - compRect.y);
//                }
//            }
//
//            g.setColor(codeArea.getForeground());
//            char[] headerChars = new char[charsPerLine];
//            Arrays.fill(headerChars, ' ');
//            CodeArea.CharRenderingMode charRenderingMode = codeArea.getCharRenderingMode();
//
//            boolean upperCase = codeArea.getHexCharactersCase() == HexCharactersCase.UPPER;
//            boolean interleaving = false;
//            int lastPos = 0;
//            for (int index = visibleStart; index < visibleEnd; index++) {
//                int codePos = codeArea.computeByteCharPos(index);
//                if (codePos == lastPos + 2 && !interleaving) {
//                    interleaving = true;
//                } else {
//                    CodeAreaUtils.longToBaseCode(headerChars, codePos, index, codeArea.getPositionCodeType().getBase(), 2, true, upperCase);
//                    lastPos = codePos;
//                    interleaving = false;
//                }
//            }
//
//            int renderOffset = visibleCharStart;
//            ColorsGroup.ColorType renderColorType = null;
//            Color renderColor = null;
//            for (int charOnLine = visibleCharStart; charOnLine < visibleCharEnd; charOnLine++) {
//                int byteOnLine;
//                byteOnLine = codeArea.computeByteOffsetPerCodeCharOffset(charOnLine);
//                boolean sequenceBreak = false;
//                boolean nativeWidth = true;
//
//                int currentCharWidth = 0;
//                ColorsGroup.ColorType colorType = ColorsGroup.ColorType.TEXT;
//                if (charRenderingMode != CodeArea.CharRenderingMode.LINE_AT_ONCE) {
//                    char currentChar = ' ';
//                    if (colorType == ColorsGroup.ColorType.TEXT) {
//                        currentChar = headerChars[charOnLine];
//                    }
//                    if (currentChar == ' ' && renderOffset == charOnLine) {
//                        renderOffset++;
//                        continue;
//                    }
//                    if (charRenderingMode == CodeArea.CharRenderingMode.AUTO && monospaceFont) {
//                        // Detect if character is in unicode range covered by monospace fonts
//                        if (currentChar > MIN_MONOSPACE_CODE_POINT && (int) currentChar < MAX_MONOSPACE_CODE_POINT
//                                && currentChar != INV_SPACE_CODE_POINT
//                                && currentChar != EXCEPTION1_CODE_POINT && currentChar != EXCEPTION2_CODE_POINT) {
//                            currentCharWidth = charWidth;
//                        }
//                    }
//
//                    if (currentCharWidth == 0) {
//                        currentCharWidth = fontMetrics.charWidth(currentChar);
//                        nativeWidth = currentCharWidth == charWidth;
//                    }
//                } else {
//                    currentCharWidth = charWidth;
//                }
//
//                Color color = getHeaderPositionColor(byteOnLine, charOnLine);
//                if (renderColorType == null) {
//                    renderColorType = colorType;
//                    renderColor = color;
//                    g.setColor(color);
//                }
//
//                if (!nativeWidth || !areSameColors(color, renderColor) || !colorType.equals(renderColorType)) {
//                    sequenceBreak = true;
//                }
//                if (sequenceBreak) {
//                    if (renderOffset < charOnLine) {
//                        g.drawChars(headerChars, renderOffset, charOnLine - renderOffset, headerX + renderOffset * charWidth, headerY);
//                    }
//
//                    if (!colorType.equals(renderColorType)) {
//                        renderColorType = colorType;
//                    }
//                    if (!areSameColors(color, renderColor)) {
//                        renderColor = color;
//                        g.setColor(color);
//                    }
//
//                    if (!nativeWidth) {
//                        renderOffset = charOnLine + 1;
//                        if (charRenderingMode == CodeArea.CharRenderingMode.TOP_LEFT) {
//                            g.drawChars(headerChars, charOnLine, 1, headerX + charOnLine * charWidth, headerY);
//                        } else {
//                            drawShiftedChar(g, headerChars, charOnLine, charWidth, headerX + charOnLine * charWidth, headerY, (charWidth + 1 - currentCharWidth) >> 1);
//                        }
//                    } else {
//                        renderOffset = charOnLine;
//                    }
//                }
//            }
//
//            if (renderOffset < charsPerLine) {
//                g.drawChars(headerChars, renderOffset, charsPerLine - renderOffset, headerX + renderOffset * charWidth, headerY);
//            }
//        }
//
//        int decorationMode = codeArea.getDecorationMode();
//        if ((decorationMode & DECORATION_HEADER_LINE) > 0) {
//            g.setColor(codeArea.getDecorationLineColor());
//            g.drawLine(compRect.x, codeRect.y - 1, compRect.x + compRect.width, codeRect.y - 1);
//        }
//        if ((decorationMode & DECORATION_BOX) > 0) {
//            g.setColor(codeArea.getDecorationLineColor());
//            g.drawLine(codeRect.x - 1, codeRect.y - 1, codeRect.x + codeRect.width, codeRect.y - 1);
//        }
//        if ((decorationMode & DECORATION_PREVIEW_LINE) > 0) {
//            int lineX = codeArea.getPreviewX() - scrollPosition.getScrollCharPosition() * codeArea.getCharWidth() - scrollPosition.getScrollCharOffset() - codeArea.getCharWidth() / 2;
//            if (lineX >= codeRect.x) {
//                g.setColor(codeArea.getDecorationLineColor());
//                g.drawLine(lineX, compRect.y, lineX, codeRect.y);
//            }
//        }
//    }
//
//    @Override
//    public void paintBackground(Graphics g) {
//        Rectangle clipBounds = g.getClipBounds();
//        Rectangle codeRect = codeArea.getCodeSectionRectangle();
//        ColorsGroup mainColors = codeArea.getMainColors();
//        ColorsGroup stripColors = codeArea.getAlternateColors();
//        int bytesPerLine = codeArea.getBytesPerLine();
//        int lineHeight = codeArea.getLineHeight();
//        int startX = clipBounds.x;
//        int width = clipBounds.width;
//        if (!codeArea.isLineNumberBackground() && codeArea.isShowLineNumbers()) {
//            int lineNumberWidth = codeRect.x - 1 - codeArea.getLineNumberSpace() / 2;
//            if (startX < lineNumberWidth) {
//                int diff = lineNumberWidth - startX;
//                startX = lineNumberWidth;
//                width -= diff;
//            }
//        }
//        if (codeArea.getBackgroundMode() != CodeArea.BackgroundMode.NONE) {
//            g.setColor(mainColors.getBackgroundColor());
//            g.fillRect(startX, clipBounds.y, width, clipBounds.height);
//        }
//
//        CodeArea.ScrollPosition scrollPosition = codeArea.getScrollPosition();
//        long line = scrollPosition.getScrollLinePosition();
//        long maxDataPosition = codeArea.getDataSize();
//        int maxY = clipBounds.y + clipBounds.height;
//
//        int positionY;
//        long dataPosition = line * bytesPerLine;
//        if (codeArea.getBackgroundMode() == CodeArea.BackgroundMode.STRIPPED || codeArea.getBackgroundMode() == CodeArea.BackgroundMode.GRIDDED) {
//            g.setColor(stripColors.getBackgroundColor());
//
//            positionY = codeRect.y - scrollPosition.getScrollLineOffset();
//            if ((line & 1) == 0) {
//                positionY += lineHeight;
//                dataPosition += bytesPerLine;
//            }
//            while (positionY <= maxY && dataPosition < maxDataPosition) {
//                g.fillRect(startX, positionY, width, lineHeight);
//                positionY += lineHeight * 2;
//                dataPosition += bytesPerLine * 2;
//            }
//        }
//    }
//
//    @Override
//    public void paintLineNumbers(Graphics g) {
//        Rectangle clipBounds = g.getClipBounds();
//        Rectangle compRect = codeArea.getComponentRectangle();
//        Rectangle codeRect = codeArea.getCodeSectionRectangle();
//        int bytesPerLine = codeArea.getBytesPerLine();
//        int lineHeight = codeArea.getLineHeight();
//
//        CodeArea.ScrollPosition scrollPosition = codeArea.getScrollPosition();
//        long line = scrollPosition.getScrollLinePosition();
//        long maxDataPosition = codeArea.getDataSize();
//        int maxY = clipBounds.y + clipBounds.height + lineHeight;
//        long dataPosition = line * bytesPerLine - scrollPosition.getLineByteShift();
//        int charWidth = codeArea.getCharWidth();
//        int positionY = codeRect.y - codeArea.getSubFontSpace() - scrollPosition.getScrollLineOffset() + codeArea.getLineHeight();
//
//        g.setColor(codeArea.getForeground());
//        int lineNumberLength = codeArea.getLineNumberLength();
//        char[] lineNumberCode = new char[lineNumberLength];
//        boolean upperCase = codeArea.getHexCharactersCase() == HexCharactersCase.UPPER;
//        while (positionY <= maxY && dataPosition <= maxDataPosition) {
//            CodeAreaUtils.longToBaseCode(lineNumberCode, 0, dataPosition < 0 ? 0 : dataPosition, codeArea.getPositionCodeType().getBase(), lineNumberLength, true, upperCase);
//            if (codeArea.getCharRenderingMode() == CodeArea.CharRenderingMode.LINE_AT_ONCE) {
//                g.drawChars(lineNumberCode, 0, lineNumberLength, compRect.x, positionY);
//            } else {
//                for (int i = 0; i < lineNumberLength; i++) {
//                    drawCenteredChar(g, lineNumberCode, i, charWidth, compRect.x + charWidth * i, positionY);
//                }
//            }
//            positionY += lineHeight;
//            dataPosition += bytesPerLine;
//        }
//
//        // Draw decoration lines
//        int decorationMode = codeArea.getDecorationMode();
//        if ((decorationMode & DECORATION_LINENUM_LINE) > 0) {
//            g.setColor(codeArea.getDecorationLineColor());
//            int lineX = codeRect.x - 1 - codeArea.getLineNumberSpace() / 2;
//            g.drawLine(lineX, compRect.y, lineX, codeRect.y + codeRect.height);
//        }
//        if ((decorationMode & DECORATION_BOX) > 0) {
//            g.setColor(codeArea.getDecorationLineColor());
//            g.drawLine(codeRect.x - 1, codeRect.y - 1, codeRect.x - 1, codeRect.y + codeRect.height);
//        }
//    }
//
//    public void paintMainArea(Graphics g, PaintData paintData) {
//        if (paintData.viewMode != ViewMode.TEXT_PREVIEW && codeArea.getBackgroundMode() == CodeArea.BackgroundMode.GRIDDED) {
//            g.setColor(paintData.alternateColors.getBackgroundColor());
//            int positionX = paintData.codeSectionRect.x - paintData.scrollPosition.getScrollCharOffset() - paintData.scrollPosition.getScrollCharPosition() * paintData.charWidth;
//            for (int i = paintData.visibleCodeStart / 2; i < paintData.visibleCodeEnd / 2; i++) {
//                g.fillRect(positionX + paintData.charWidth * codeArea.computeByteCharPos(i * 2 + 1), paintData.codeSectionRect.y, paintData.charWidth * paintData.codeDigits, paintData.codeSectionRect.height);
//            }
//        }
//
//        int positionY = paintData.codeSectionRect.y - paintData.scrollPosition.getScrollLineOffset();
//        paintData.line = paintData.scrollPosition.getScrollLinePosition();
//        int positionX = paintData.codeSectionRect.x - paintData.scrollPosition.getScrollCharPosition() * paintData.charWidth - paintData.scrollPosition.getScrollCharOffset();
//        paintData.lineDataPosition = paintData.line * paintData.bytesPerLine - paintData.scrollPosition.getLineByteShift();
//        long dataSize = codeArea.getDataSize();
//
//        do {
//            if (paintData.showUnprintableCharacters) {
//                Arrays.fill(paintData.unprintableChars, ' ');
//            }
//            int lineBytesLimit = paintData.bytesPerLine;
//            if (paintData.lineDataPosition < dataSize) {
//                int lineDataSize = paintData.bytesPerLine + paintData.maxCharLength - 1;
//                if (paintData.lineDataPosition + lineDataSize > dataSize) {
//                    lineDataSize = (int) (dataSize - paintData.lineDataPosition);
//                }
//                if (paintData.lineDataPosition < 0) {
//                    paintData.lineStart = (int) -paintData.lineDataPosition;
//                } else {
//                    paintData.lineStart = 0;
//                }
//                codeArea.getData().copyToArray(paintData.lineDataPosition + paintData.lineStart, paintData.lineData, paintData.lineStart, lineDataSize - paintData.lineStart);
//                if (paintData.lineDataPosition + lineBytesLimit > dataSize) {
//                    lineBytesLimit = (int) (dataSize - paintData.lineDataPosition);
//                }
//            } else {
//                lineBytesLimit = 0;
//            }
//
//            // Fill codes
//            if (paintData.viewMode != ViewMode.TEXT_PREVIEW) {
//                for (int byteOnLine = Math.max(paintData.visibleCodeStart, paintData.lineStart); byteOnLine < Math.min(paintData.visibleCodeEnd, lineBytesLimit); byteOnLine++) {
//                    byte dataByte = paintData.lineData[byteOnLine];
//                    CodeAreaUtils.byteToCharsCode(dataByte, codeArea.getCodeType(), paintData.lineChars, codeArea.computeByteCharPos(byteOnLine), codeArea.getHexCharactersCase());
//                }
//                if (paintData.bytesPerLine > lineBytesLimit) {
//                    Arrays.fill(paintData.lineChars, codeArea.computeByteCharPos(lineBytesLimit), paintData.lineChars.length, ' ');
//                }
//            }
//
//            // Fill preview characters
//            if (paintData.viewMode != ViewMode.CODE_MATRIX) {
//                for (int byteOnLine = paintData.visiblePreviewStart; byteOnLine < Math.min(paintData.visiblePreviewEnd, lineBytesLimit); byteOnLine++) {
//                    byte dataByte = paintData.lineData[byteOnLine];
//
//                    if (paintData.maxCharLength > 1) {
//                        if (paintData.lineDataPosition + paintData.maxCharLength > dataSize) {
//                            paintData.maxCharLength = (int) (dataSize - paintData.lineDataPosition);
//                        }
//
//                        int charDataLength = paintData.maxCharLength;
//                        if (byteOnLine + charDataLength > paintData.lineData.length) {
//                            charDataLength = paintData.lineData.length - byteOnLine;
//                        }
//                        String displayString = new String(paintData.lineData, byteOnLine, charDataLength, paintData.charset);
//                        if (!displayString.isEmpty()) {
//                            paintData.lineChars[paintData.previewCharPos + byteOnLine] = displayString.charAt(0);
//                        }
//                    } else {
//                        if (charMappingCharset == null || charMappingCharset != paintData.charset) {
//                            buildCharMapping(paintData.charset);
//                        }
//
//                        paintData.lineChars[paintData.previewCharPos + byteOnLine] = charMapping[dataByte & 0xFF];
//                    }
//
//                    if (paintData.showUnprintableCharacters || paintData.charRenderingMode == CodeArea.CharRenderingMode.LINE_AT_ONCE) {
//                        if (unprintableCharactersMapping == null) {
//                            buildUnprintableCharactersMapping();
//                        }
//                        Character replacement = unprintableCharactersMapping.get(paintData.lineChars[paintData.previewCharPos + byteOnLine]);
//                        if (replacement != null) {
//                            if (paintData.showUnprintableCharacters) {
//                                paintData.unprintableChars[paintData.previewCharPos + byteOnLine] = replacement;
//                            }
//                            paintData.lineChars[paintData.previewCharPos + byteOnLine] = ' ';
//                        }
//                    }
//                }
//                if (paintData.bytesPerLine > lineBytesLimit) {
//                    Arrays.fill(paintData.lineChars, paintData.previewCharPos + lineBytesLimit, paintData.previewCharPos + paintData.bytesPerLine, ' ');
//                }
//            }
//            paintLineBackground(g, positionX, positionY, paintData);
//            paintLineText(g, positionX, positionY, paintData);
//            paintData.lineDataPosition += paintData.bytesPerLine;
//            paintData.line++;
//            positionY += paintData.lineHeight;
//        } while (positionY - paintData.lineHeight < paintData.codeSectionRect.y + paintData.codeSectionRect.height);
//
//        // Draw decoration lines
//        int decorationMode = codeArea.getDecorationMode();
//        if ((decorationMode & CodeArea.DECORATION_PREVIEW_LINE) > 0) {
//            int lineX = codeArea.getPreviewX() - paintData.scrollPosition.getScrollCharPosition() * codeArea.getCharWidth() - paintData.scrollPosition.getScrollCharOffset() - codeArea.getCharWidth() / 2;
//            if (lineX >= paintData.codeSectionRect.x) {
//                g.setColor(codeArea.getDecorationLineColor());
//                g.drawLine(lineX, paintData.codeSectionRect.y, lineX, paintData.codeSectionRect.y + paintData.codeSectionRect.height);
//            }
//        }
//    }
//
//    public void paintLineBackground(Graphics g, int linePositionX, int linePositionY, PaintData paintData) {
//        int renderOffset = paintData.visibleCharStart;
//        ColorsGroup.ColorType renderColorType = null;
//        Color renderColor = null;
//        for (int charOnLine = paintData.visibleCharStart; charOnLine < paintData.visibleCharEnd; charOnLine++) {
//            CodeAreaSection section;
//            int byteOnLine;
//            if (charOnLine >= paintData.previewCharPos && paintData.viewMode != ViewMode.CODE_MATRIX) {
//                byteOnLine = charOnLine - paintData.previewCharPos;
//                section = Section.TEXT_PREVIEW;
//            } else {
//                byteOnLine = codeArea.computeByteOffsetPerCodeCharOffset(charOnLine);
//                section = CodeAreaSection.CODE_MATRIX;
//            }
//            boolean sequenceBreak = false;
//
//            ColorsGroup.ColorType colorType = ColorsGroup.ColorType.BACKGROUND;
//            if (paintData.showUnprintableCharacters) {
//                if (paintData.unprintableChars[charOnLine] != ' ') {
//                    colorType = ColorsGroup.ColorType.UNPRINTABLES_BACKGROUND;
//                }
//            }
//
//            Color color = getPositionColor(byteOnLine, charOnLine, section, colorType, paintData);
//            if (renderColorType == null) {
//                renderColorType = colorType;
//                renderColor = color;
//                g.setColor(color);
//            }
//
//            if (!areSameColors(color, renderColor) || !colorType.equals(renderColorType)) {
//                sequenceBreak = true;
//            }
//            if (sequenceBreak) {
//                if (renderOffset < charOnLine) {
//                    if (renderColor != null) {
//                        renderBackgroundSequence(g, renderOffset, charOnLine, linePositionX, linePositionY, paintData);
//                    }
//                }
//
//                if (!colorType.equals(renderColorType)) {
//                    renderColorType = colorType;
//                }
//                if (!areSameColors(color, renderColor)) {
//                    renderColor = color;
//                    g.setColor(color);
//                }
//
//                renderOffset = charOnLine;
//            }
//        }
//
//        if (renderOffset < paintData.charsPerLine) {
//            if (renderColor != null) {
//                renderBackgroundSequence(g, renderOffset, paintData.charsPerLine, linePositionX, linePositionY, paintData);
//            }
//        }
//    }
//
//    public void paintLineText(Graphics g, int linePositionX, int linePositionY, PaintData paintData) {
//        int positionY = linePositionY + paintData.lineHeight - codeArea.getSubFontSpace();
//
//        int renderOffset = paintData.visibleCharStart;
//        ColorsGroup.ColorType renderColorType = null;
//        Color renderColor = null;
//        for (int charOnLine = paintData.visibleCharStart; charOnLine < paintData.visibleCharEnd; charOnLine++) {
//            Section section;
//            int byteOnLine;
//            if (charOnLine >= paintData.previewCharPos) {
//                byteOnLine = charOnLine - paintData.previewCharPos;
//                section = Section.TEXT_PREVIEW;
//            } else {
//                byteOnLine = codeArea.computeByteOffsetPerCodeCharOffset(charOnLine);
//                section = Section.CODE_MATRIX;
//            }
//            boolean sequenceBreak = false;
//            boolean nativeWidth = true;
//
//            int currentCharWidth = 0;
//            ColorsGroup.ColorType colorType = ColorsGroup.ColorType.TEXT;
//            if (paintData.charRenderingMode != CodeArea.CharRenderingMode.LINE_AT_ONCE) {
//                char currentChar = ' ';
//                if (paintData.showUnprintableCharacters) {
//                    currentChar = paintData.unprintableChars[charOnLine];
//                    if (currentChar != ' ') {
//                        colorType = ColorsGroup.ColorType.UNPRINTABLES;
//                    }
//                }
//                if (colorType == ColorsGroup.ColorType.TEXT) {
//                    currentChar = paintData.lineChars[charOnLine];
//                }
//                if (currentChar == ' ' && renderOffset == charOnLine) {
//                    renderOffset++;
//                    continue;
//                }
//                if (paintData.charRenderingMode == CodeArea.CharRenderingMode.AUTO && paintData.monospaceFont) {
//                    // Detect if character is in unicode range covered by monospace fonts
//                    if (currentChar > MIN_MONOSPACE_CODE_POINT && (int) currentChar < MAX_MONOSPACE_CODE_POINT
//                            && currentChar != INV_SPACE_CODE_POINT
//                            && currentChar != EXCEPTION1_CODE_POINT && currentChar != EXCEPTION2_CODE_POINT) {
//                        currentCharWidth = paintData.charWidth;
//                    }
//                }
//
//                if (currentCharWidth == 0) {
//                    currentCharWidth = paintData.fontMetrics.charWidth(currentChar);
//                    nativeWidth = currentCharWidth == paintData.charWidth;
//                }
//            } else {
//                currentCharWidth = paintData.charWidth;
//                if (paintData.showUnprintableCharacters) {
//                    char currentChar = paintData.unprintableChars[charOnLine];
//                    if (currentChar != ' ') {
//                        colorType = ColorsGroup.ColorType.UNPRINTABLES;
//                        currentCharWidth = paintData.fontMetrics.charWidth(currentChar);
//                        nativeWidth = currentCharWidth == paintData.charWidth;
//                    }
//                }
//            }
//
//            Color color = getPositionColor(byteOnLine, charOnLine, section, colorType, paintData);
//            if (renderColorType == null) {
//                renderColorType = colorType;
//                renderColor = color;
//                g.setColor(color);
//            }
//
//            if (!nativeWidth || !areSameColors(color, renderColor) || !colorType.equals(renderColorType)) {
//                sequenceBreak = true;
//            }
//            if (sequenceBreak) {
//                if (renderOffset < charOnLine) {
//                    renderCharSequence(g, renderOffset, charOnLine, linePositionX, positionY, renderColorType, paintData);
//                }
//
//                if (!colorType.equals(renderColorType)) {
//                    renderColorType = colorType;
//                }
//                if (!areSameColors(color, renderColor)) {
//                    renderColor = color;
//                    g.setColor(color);
//                }
//
//                if (!nativeWidth) {
//                    renderOffset = charOnLine + 1;
//                    if (paintData.charRenderingMode == CodeArea.CharRenderingMode.TOP_LEFT) {
//                        g.drawChars(
//                                renderColorType == ColorsGroup.ColorType.UNPRINTABLES ? paintData.unprintableChars : paintData.lineChars,
//                                charOnLine, 1, linePositionX + charOnLine * paintData.charWidth, positionY);
//                    } else {
//                        drawShiftedChar(g,
//                                renderColorType == ColorsGroup.ColorType.UNPRINTABLES ? paintData.unprintableChars : paintData.lineChars,
//                                charOnLine, paintData.charWidth, linePositionX + charOnLine * paintData.charWidth, positionY, (paintData.charWidth + 1 - currentCharWidth) >> 1);
//                    }
//                } else {
//                    renderOffset = charOnLine;
//                }
//            }
//        }
//
//        if (renderOffset < paintData.charsPerLine) {
//            renderCharSequence(g, renderOffset, paintData.charsPerLine, linePositionX, positionY, renderColorType, paintData);
//        }
//    }
//
//    /**
//     * Returns color of given type for specified position.
//     *
//     * Child implementation can override this to change rendering colors.
//     *
//     * @param byteOnLine byte on line
//     * @param charOnLine character on line
//     * @param section rendering section
//     * @param colorType color type
//     * @param paintData cached paint data
//     * @return color
//     */
//    public Color getPositionColor(int byteOnLine, int charOnLine, Section section, ColorsGroup.ColorType colorType, PaintData paintData) {
//        long dataPosition = paintData.lineDataPosition + byteOnLine;
//        SelectionRange selection = codeArea.getSelection();
//        if (selection != null && dataPosition >= selection.getFirst() && dataPosition <= selection.getLast() && (section == Section.TEXT_PREVIEW || charOnLine < paintData.charsPerCodeArea)) {
//            Section activeSection = codeArea.getActiveSection();
//            if (activeSection == section) {
//                return codeArea.getSelectionColors().getColor(colorType);
//            } else {
//                return codeArea.getMirrorSelectionColors().getColor(colorType);
//            }
//        }
//        if (colorType == ColorsGroup.ColorType.BACKGROUND) {
//            // Background is prepainted
//            return null;
//        }
//        if (((paintData.backgroundMode == CodeArea.BackgroundMode.STRIPPED || paintData.backgroundMode == CodeArea.BackgroundMode.GRIDDED) && (paintData.line & 1) > 0)
//                || (paintData.backgroundMode == CodeArea.BackgroundMode.GRIDDED && ((byteOnLine & 1) > 0)) && section == Section.CODE_MATRIX) {
//            return codeArea.getAlternateColors().getColor(colorType);
//        }
//
//        return codeArea.getMainColors().getColor(colorType);
//    }
//
//
//    @Override
//    public void paintCursor() {
//        int bytesPerLine = codeArea.getBytesPerLine();
//        if (bytesPerLine > 0) {
//            int lineHeight = codeArea.getLineHeight();
//            int charWidth = codeArea.getCharWidth();
//            int linesPerRect = codeArea.getLinesPerRect();
//            Rectangle cursorRect = getCursorRect(bytesPerLine, lineHeight, charWidth, linesPerRect);
//            if (cursorRect != null) {
//                codeArea.paintImmediately(cursorRect);
//            }
//        }
//    }
//
//    @Override
//    public void notifyModified() {
//        computePaintData();
//        validateLineOffset();
//    }
//
//    private int computeCharsPerRect(int width) {
//        if (showLineNumbers) {
//            width -= paintDataCache.charWidth * paintDataCache.lineNumbersLength + getLineNumberSpace();
//        }
//
//        return width / paintDataCache.charWidth;
//    }
//
//    /**
//     * Computes how many bytes would fit into given number of characters.
//     *
//     * @param charsPerRect available characters space
//     * @return maximum byte offset index
//     */
//    public int computeFittingBytes(int charsPerRect) {
//        if (viewMode == ViewMode.TEXT_PREVIEW) {
//            return charsPerRect;
//        }
//
//        int fittingBytes;
//        if (byteGroupSize == 0) {
//            if (spaceGroupSize == 0) {
//                fittingBytes = (charsPerRect - 1)
//                        / (codeType.getMaxDigits() + 1);
//            } else {
//                fittingBytes = spaceGroupSize
//                        * (int) ((charsPerRect - 1) / (long) ((codeType.getMaxDigits() + 1) * spaceGroupSize + 2));
//                int remains = (int) ((charsPerRect - 1) % (long) ((codeType.getMaxDigits() + 1) * spaceGroupSize + 2)) / (codeType.getMaxDigits() + 1);
//                fittingBytes += remains;
//            }
//        } else if (spaceGroupSize == 0) {
//            fittingBytes = byteGroupSize
//                    * (int) ((charsPerRect - 1) / (long) ((codeType.getMaxDigits() + 1) * byteGroupSize + 1));
//            int remains = (int) ((charsPerRect - 1) % (long) ((codeType.getMaxDigits() + 1) * byteGroupSize + 1)) / (codeType.getMaxDigits() + 1);
//            fittingBytes += remains;
//        } else {
//            fittingBytes = 0;
//            int charsPerLine = 1;
//            while (charsPerLine < charsPerRect) {
//                charsPerLine += codeType.getMaxDigits() + 1;
//                fittingBytes++;
//                if ((fittingBytes % byteGroupSize) == 0) {
//                    if ((fittingBytes % spaceGroupSize) == 0) {
//                        charsPerLine += 2;
//                    } else {
//                        charsPerLine++;
//                    }
//                } else if ((fittingBytes % spaceGroupSize) == 0) {
//                    charsPerLine += 2;
//                }
//                if (charsPerLine > charsPerRect) {
//                    return fittingBytes - 1;
//                }
//            }
//
//            if (computeCharsPerLine(fittingBytes + 1) <= charsPerRect) {
//                fittingBytes++;
//            }
//        }
//
//        return fittingBytes;
//    }
//
//    /**
//     * Computes byte offset index for given code line offset.
//     *
//     * @param charOffset char offset position
//     * @return byte offset index
//     */
//    public int computeByteOffsetPerCodeCharOffset(int charOffset) {
//        int byteOffset;
//        if (byteGroupSize == 0) {
//            if (spaceGroupSize == 0) {
//                byteOffset = charOffset / codeType.getMaxDigits();
//            } else {
//                byteOffset = spaceGroupSize
//                        * (int) (charOffset / (long) (codeType.getMaxDigits() * spaceGroupSize + 2));
//                int remains = (int) (charOffset % (long) (codeType.getMaxDigits() * spaceGroupSize + 2)) / codeType.getMaxDigits();
//                if (remains >= spaceGroupSize) {
//                    remains = spaceGroupSize - 1;
//                }
//                byteOffset += remains;
//            }
//        } else if (spaceGroupSize == 0) {
//            byteOffset = byteGroupSize
//                    * (int) (charOffset / (long) (codeType.getMaxDigits() * byteGroupSize + 1));
//            int remains = (int) (charOffset % (long) (codeType.getMaxDigits() * byteGroupSize + 1)) / codeType.getMaxDigits();
//            if (remains >= byteGroupSize) {
//                remains = byteGroupSize - 1;
//            }
//            byteOffset += remains;
//        } else {
//            byteOffset = 0;
//            int charsPerLine = 0;
//            while (charsPerLine < charOffset) {
//                charsPerLine += codeType.getMaxDigits();
//                byteOffset++;
//                if ((byteOffset % byteGroupSize) == 0) {
//                    if ((byteOffset % spaceGroupSize) == 0) {
//                        charsPerLine += 2;
//                    } else {
//                        charsPerLine++;
//                    }
//                } else if ((byteOffset % spaceGroupSize) == 0) {
//                    charsPerLine += 2;
//                }
//                if (charsPerLine > charOffset) {
//                    return byteOffset - 1;
//                }
//            }
//        }
//
//        return byteOffset;
//    }
//
//    /**
//     * Computes number of characters for given number of bytes / offset.
//     *
//     * @param bytesPerLine number of bytes per line
//     * @return characters count
//     */
//    public int computeCharsPerLine(int bytesPerLine) {
//        if (viewMode == ViewMode.TEXT_PREVIEW) {
//            return bytesPerLine;
//        }
//
//        int charsPerLine = computeByteCharPos(bytesPerLine, false);
//
//        if (viewMode == ViewMode.DUAL) {
//            charsPerLine += bytesPerLine + 1;
//        }
//
//        return charsPerLine;
//    }
//
//    /**
//     * Computes character position for byte code of given offset position.
//     *
//     * @param byteOffset byte start offset
//     * @return characters position
//     */
//    public int computeByteCharPos(int byteOffset) {
//        return computeByteCharPos(byteOffset, true);
//    }
//
//    public int computeByteCharPos(int byteOffset, boolean includeTail) {
//        int charsPerLine = codeType.getMaxDigits() * byteOffset;
//        if (!includeTail) {
//            byteOffset--;
//        }
//        if (byteGroupSize == 0) {
//            if (spaceGroupSize != 0) {
//                charsPerLine += (byteOffset / spaceGroupSize) * 2;
//            }
//        } else if (spaceGroupSize == 0) {
//            charsPerLine += (byteOffset / byteGroupSize);
//        } else {
//            for (int index = 1; index <= byteOffset; index++) {
//                if ((index % byteGroupSize) == 0) {
//                    if ((index % spaceGroupSize) == 0) {
//                        charsPerLine += 2;
//                    } else {
//                        charsPerLine++;
//                    }
//                } else if ((index % spaceGroupSize) == 0) {
//                    charsPerLine += 2;
//                }
//            }
//        }
//
//        return charsPerLine;
//    }
//
//    public void computePaintData() {
//        if (paintDataCache.fontMetrics == null) {
//            return;
//        }
//
//        boolean verticalScrollBarVisible;
//        boolean horizontalScrollBarVisible;
//
//        Insets insets = getInsets();
//        Dimension size = getSize();
//        Rectangle compRect = paintDataCache.componentRectangle;
//        compRect.x = insets.left;
//        compRect.y = insets.top;
//        compRect.width = size.width - insets.left - insets.right;
//        compRect.height = size.height - insets.top - insets.bottom;
//
//        switch (lineNumberLength.getLineNumberType()) {
//            case AUTO: {
//                long dataSize = getDataSize();
//                if (dataSize > 0) {
//                    double natLog = Math.log(dataSize);
//                    paintDataCache.lineNumbersLength = (int) Math.ceil(natLog / positionCodeType.getBaseLog());
//                    if (paintDataCache.lineNumbersLength == 0) {
//                        paintDataCache.lineNumbersLength = 1;
//                    }
//                } else {
//                    paintDataCache.lineNumbersLength = 1;
//                }
//                break;
//            }
//            case SPECIFIED: {
//                paintDataCache.lineNumbersLength = lineNumberLength.getLineNumberLength();
//                break;
//            }
//        }
//
//        int charsPerRect = computeCharsPerRect(compRect.width);
//        int bytesPerLine;
//        if (wrapMode) {
//            bytesPerLine = computeFittingBytes(charsPerRect);
//            if (bytesPerLine == 0) {
//                bytesPerLine = 1;
//            }
//        } else {
//            bytesPerLine = lineLength;
//        }
//        long lines = ((data.getDataSize() + scrollPosition.lineByteShift) / bytesPerLine) + 1;
//        CodeAreaSpace.SpaceType headerSpaceType = headerSpace.getSpaceType();
//        switch (headerSpaceType) {
//            case NONE: {
//                paintDataCache.headerSpace = 0;
//                break;
//            }
//            case SPECIFIED: {
//                paintDataCache.headerSpace = headerSpace.getSpaceSize();
//                break;
//            }
//            case QUARTER_UNIT: {
//                paintDataCache.headerSpace = paintDataCache.lineHeight / 4;
//                break;
//            }
//            case HALF_UNIT: {
//                paintDataCache.headerSpace = paintDataCache.lineHeight / 2;
//                break;
//            }
//            case ONE_UNIT: {
//                paintDataCache.headerSpace = paintDataCache.lineHeight;
//                break;
//            }
//            case ONE_AND_HALF_UNIT: {
//                paintDataCache.headerSpace = (int) (paintDataCache.lineHeight * 1.5f);
//                break;
//            }
//            case DOUBLE_UNIT: {
//                paintDataCache.headerSpace = paintDataCache.lineHeight * 2;
//                break;
//            }
//            default:
//                throw new IllegalStateException("Unexpected header space type " + headerSpaceType.name());
//        }
//
//        CodeAreaSpace.SpaceType lineNumberSpaceType = lineNumberSpace.getSpaceType();
//        switch (lineNumberSpaceType) {
//            case NONE: {
//                paintDataCache.lineNumberSpace = 0;
//                break;
//            }
//            case SPECIFIED: {
//                paintDataCache.lineNumberSpace = lineNumberSpace.getSpaceSize();
//                break;
//            }
//            case QUARTER_UNIT: {
//                paintDataCache.lineNumberSpace = paintDataCache.charWidth / 4;
//                break;
//            }
//            case HALF_UNIT: {
//                paintDataCache.lineNumberSpace = paintDataCache.charWidth / 2;
//                break;
//            }
//            case ONE_UNIT: {
//                paintDataCache.lineNumberSpace = paintDataCache.charWidth;
//                break;
//            }
//            case ONE_AND_HALF_UNIT: {
//                paintDataCache.lineNumberSpace = (int) (paintDataCache.charWidth * 1.5f);
//                break;
//            }
//            case DOUBLE_UNIT: {
//                paintDataCache.lineNumberSpace = paintDataCache.charWidth * 2;
//                break;
//            }
//            default:
//                throw new IllegalStateException("Unexpected line number space type " + lineNumberSpaceType.name());
//        }
//
//        Rectangle hexRect = paintDataCache.codeSectionRectangle;
//        hexRect.y = insets.top + (showHeader ? paintDataCache.lineHeight + paintDataCache.headerSpace : 0);
//        hexRect.x = insets.left + (showLineNumbers ? paintDataCache.charWidth * paintDataCache.lineNumbersLength + paintDataCache.lineNumberSpace : 0);
//
//        if (verticalScrollBarVisibility == ScrollBarVisibility.IF_NEEDED) {
//            verticalScrollBarVisible = lines > paintDataCache.linesPerRect;
//        } else {
//            verticalScrollBarVisible = verticalScrollBarVisibility == ScrollBarVisibility.ALWAYS;
//        }
//        if (verticalScrollBarVisible) {
//            charsPerRect = computeCharsPerRect(compRect.x + compRect.width - paintDataCache.scrollBarThickness);
//            if (wrapMode) {
//                bytesPerLine = computeFittingBytes(charsPerRect);
//                if (bytesPerLine <= 0) {
//                    bytesPerLine = 1;
//                }
//                lines = ((data.getDataSize() + scrollPosition.lineByteShift) / bytesPerLine) + 1;
//            }
//        }
//
//        paintDataCache.bytesPerLine = bytesPerLine;
//        paintDataCache.charsPerLine = computeCharsPerLine(bytesPerLine);
//
//        int maxWidth = compRect.x + compRect.width - hexRect.x;
//        if (verticalScrollBarVisible) {
//            maxWidth -= paintDataCache.scrollBarThickness;
//        }
//
//        if (horizontalScrollBarVisibility == ScrollBarVisibility.IF_NEEDED) {
//            horizontalScrollBarVisible = paintDataCache.charsPerLine * paintDataCache.charWidth > maxWidth;
//        } else {
//            horizontalScrollBarVisible = horizontalScrollBarVisibility == ScrollBarVisibility.ALWAYS;
//        }
//        if (horizontalScrollBarVisible) {
//            paintDataCache.linesPerRect = (hexRect.height - paintDataCache.scrollBarThickness) / paintDataCache.lineHeight;
//        }
//
//        hexRect.width = compRect.x + compRect.width - hexRect.x;
//        if (verticalScrollBarVisible) {
//            hexRect.width -= paintDataCache.scrollBarThickness;
//        }
//        hexRect.height = compRect.y + compRect.height - hexRect.y;
//        if (horizontalScrollBarVisible) {
//            hexRect.height -= paintDataCache.scrollBarThickness;
//        }
//
//        paintDataCache.bytesPerRect = hexRect.width / paintDataCache.charWidth;
//        paintDataCache.linesPerRect = hexRect.height / paintDataCache.lineHeight;
//
//        // Compute sections positions
//        paintDataCache.previewStartChar = 0;
//        if (viewMode == ViewMode.CODE_MATRIX) {
//            paintDataCache.previewX = -1;
//        } else {
//            paintDataCache.previewX = hexRect.x;
//            if (viewMode == ViewMode.DUAL) {
//                paintDataCache.previewStartChar = paintDataCache.charsPerLine - paintDataCache.bytesPerLine;
//                paintDataCache.previewX += (paintDataCache.charsPerLine - paintDataCache.bytesPerLine) * paintDataCache.charWidth;
//            }
//        }
//
//        // Compute scrollbar positions
//        boolean scrolled = false;
//        verticalScrollBar.setVisible(verticalScrollBarVisible);
//        if (verticalScrollBarVisible) {
//            int verticalScrollBarHeight = compRect.y + compRect.height - hexRect.y;
//            if (horizontalScrollBarVisible) {
//                verticalScrollBarHeight -= paintDataCache.scrollBarThickness - 2;
//            }
//            verticalScrollBar.setBounds(compRect.x + compRect.width - paintDataCache.scrollBarThickness, hexRect.y, paintDataCache.scrollBarThickness, verticalScrollBarHeight);
//
//            int verticalVisibleAmount;
//            scrollPosition.verticalMaxMode = false;
//            int verticalMaximum;
//            if (verticalScrollMode == VerticalScrollMode.PIXEL) {
//                if (lines * paintDataCache.lineHeight > Integer.MAX_VALUE) {
//                    scrollPosition.verticalMaxMode = true;
//                    verticalMaximum = Integer.MAX_VALUE;
//                    verticalVisibleAmount = (int) (hexRect.height * Integer.MAX_VALUE / lines);
//                } else {
//                    verticalMaximum = (int) (lines * paintDataCache.lineHeight);
//                    verticalVisibleAmount = hexRect.height;
//                }
//            } else if (lines > Integer.MAX_VALUE) {
//                scrollPosition.verticalMaxMode = true;
//                verticalMaximum = Integer.MAX_VALUE;
//                verticalVisibleAmount = (int) (hexRect.height * Integer.MAX_VALUE / paintDataCache.lineHeight / lines);
//            } else {
//                verticalMaximum = (int) lines;
//                verticalVisibleAmount = hexRect.height / paintDataCache.lineHeight;
//            }
//            if (verticalVisibleAmount == 0) {
//                verticalVisibleAmount = 1;
//            }
//            verticalScrollBar.setMaximum(verticalMaximum);
//            verticalScrollBar.setVisibleAmount(verticalVisibleAmount);
//
//            // Cap vertical scrolling
//            if (!scrollPosition.verticalMaxMode && verticalVisibleAmount < verticalMaximum) {
//                long maxLineScroll = verticalMaximum - verticalVisibleAmount;
//                if (verticalScrollMode == VerticalScrollMode.PER_LINE) {
//                    long lineScroll = scrollPosition.scrollLinePosition;
//                    if (lineScroll > maxLineScroll) {
//                        scrollPosition.scrollLinePosition = maxLineScroll;
//                        scrolled = true;
//                    }
//                } else {
//                    long lineScroll = scrollPosition.scrollLinePosition * paintDataCache.lineHeight + scrollPosition.scrollLineOffset;
//                    if (lineScroll > maxLineScroll) {
//                        scrollPosition.scrollLinePosition = maxLineScroll / paintDataCache.lineHeight;
//                        scrollPosition.scrollLineOffset = (int) (maxLineScroll % paintDataCache.lineHeight);
//                        scrolled = true;
//                    }
//                }
//            }
//        } else if (scrollPosition.scrollLinePosition > 0 || scrollPosition.scrollLineOffset > 0) {
//            scrollPosition.scrollLinePosition = 0;
//            scrollPosition.scrollLineOffset = 0;
//            scrolled = true;
//        }
//
//        horizontalScrollBar.setVisible(horizontalScrollBarVisible);
//        if (horizontalScrollBarVisible) {
//            int horizontalScrollBarWidth = compRect.x + compRect.width - hexRect.x;
//            if (verticalScrollBarVisible) {
//                horizontalScrollBarWidth -= paintDataCache.scrollBarThickness - 2;
//            }
//            horizontalScrollBar.setBounds(hexRect.x, compRect.y + compRect.height - paintDataCache.scrollBarThickness, horizontalScrollBarWidth, paintDataCache.scrollBarThickness);
//
//            int horizontalVisibleAmount;
//            int horizontalMaximum = paintDataCache.charsPerLine;
//            if (horizontalScrollMode == HorizontalScrollMode.PIXEL) {
//                horizontalVisibleAmount = hexRect.width;
//                horizontalMaximum *= paintDataCache.charWidth;
//            } else {
//                horizontalVisibleAmount = hexRect.width / paintDataCache.charWidth;
//            }
//            horizontalScrollBar.setMaximum(horizontalMaximum);
//            horizontalScrollBar.setVisibleAmount(horizontalVisibleAmount);
//
//            // Cap horizontal scrolling
//            int maxByteScroll = horizontalMaximum - horizontalVisibleAmount;
//            if (horizontalVisibleAmount < horizontalMaximum) {
//                if (horizontalScrollMode == HorizontalScrollMode.PIXEL) {
//                    int byteScroll = scrollPosition.scrollCharPosition * paintDataCache.charWidth + scrollPosition.scrollCharOffset;
//                    if (byteScroll > maxByteScroll) {
//                        scrollPosition.scrollCharPosition = maxByteScroll / paintDataCache.charWidth;
//                        scrollPosition.scrollCharOffset = maxByteScroll % paintDataCache.charWidth;
//                        scrolled = true;
//                    }
//                } else {
//                    int byteScroll = scrollPosition.scrollCharPosition;
//                    if (byteScroll > maxByteScroll) {
//                        scrollPosition.scrollCharPosition = maxByteScroll;
//                        scrolled = true;
//                    }
//                }
//            }
//        } else if (scrollPosition.scrollCharPosition > 0 || scrollPosition.scrollCharOffset > 0) {
//            scrollPosition.scrollCharPosition = 0;
//            scrollPosition.scrollCharOffset = 0;
//            scrolled = true;
//        }
//
//        if (scrolled) {
//            updateScrollBars();
//            notifyScrolled();
//        }
//    }
    private void buildUnprintableCharactersMapping() {
        unprintableCharactersMapping = new HashMap<>();
        // Unicode control characters, might not be supported by font
        for (int i = 0; i < 32; i++) {
            unprintableCharactersMapping.put((char) i, Character.toChars(9216 + i)[0]);
        }
        // Space -> Middle Dot
        unprintableCharactersMapping.put(' ', Character.toChars(183)[0]);
        // Tab -> Right-Pointing Double Angle Quotation Mark
        unprintableCharactersMapping.put('\t', Character.toChars(187)[0]);
        // Line Feed -> Currency Sign
        unprintableCharactersMapping.put('\r', Character.toChars(164)[0]);
        // Carriage Return -> Pilcrow Sign
        unprintableCharactersMapping.put('\n', Character.toChars(182)[0]);
        // Ideographic Space -> Degree Sign
        unprintableCharactersMapping.put(Character.toChars(127)[0], Character.toChars(176)[0]);
    }

    @Override
    public void updateScrollBars() {
        int characterWidth = metrics.getCharacterWidth();
        int rowHeight = metrics.getRowHeight();
        long rowsPerDocument = structure.getRowsPerDocument();
        recomputeScrollState();

        adjusting = true;
        JScrollBar verticalScrollBar = scrollPanel.getVerticalScrollBar();
        scrollPanel.setVerticalScrollBarPolicy(CodeAreaSwingUtils.getVerticalScrollBarPolicy(scrolling.getVerticalScrollBarVisibility()));
        JScrollBar horizontalScrollBar = scrollPanel.getHorizontalScrollBar();
        scrollPanel.setHorizontalScrollBarPolicy(CodeAreaSwingUtils.getHorizontalScrollBarPolicy(scrolling.getHorizontalScrollBarVisibility()));

        int verticalScrollValue = scrolling.getVerticalScrollValue(rowHeight, rowsPerDocument);
        verticalScrollBar.setValue(verticalScrollValue);

        int horizontalScrollValue = scrolling.getHorizontalScrollValue(characterWidth);
        horizontalScrollBar.setValue(horizontalScrollValue);

        adjusting = false;
    }

    protected int getCharactersPerRow() {
        return structure.getCharactersPerRow();
    }

    private int getHorizontalScrollBarSize() {
        JScrollBar horizontalScrollBar = scrollPanel.getHorizontalScrollBar();
        int size;
        if (horizontalScrollBar.isVisible()) {
            size = horizontalScrollBar.getHeight();
        } else {
            size = 0;
        }

        return size;
    }

    private int getVerticalScrollBarSize() {
        JScrollBar verticalScrollBar = scrollPanel.getVerticalScrollBar();
        int size;
        if (verticalScrollBar.isVisible()) {
            size = verticalScrollBar.getWidth();
        } else {
            size = 0;
        }

        return size;
    }

    private class VerticalAdjustmentListener implements AdjustmentListener {

        public VerticalAdjustmentListener() {
        }

        @Override
        public void adjustmentValueChanged(AdjustmentEvent e) {
            if (e == null || adjusting) {
                return;
            }

            int scrollBarValue = scrollPanel.getVerticalScrollBar().getValue();
            int maxValue = Integer.MAX_VALUE - scrollPanel.getVerticalScrollBar().getVisibleAmount();
            long rowsPerDocumentToLastPage = structure.getRowsPerDocument() - dimensions.getRowsPerRect();
            scrolling.updateVerticalScrollBarValue(scrollBarValue, metrics.getRowHeight(), maxValue, rowsPerDocumentToLastPage);
            ((ScrollingCapable) codeArea).setScrollPosition(scrolling.getScrollPosition());
            notifyScrolled();
            codeArea.repaint();
//            dataViewScrolled(codeArea.getGraphics());
        }
    }

    private class HorizontalAdjustmentListener implements AdjustmentListener {

        public HorizontalAdjustmentListener() {
        }

        @Override
        public void adjustmentValueChanged(@Nullable AdjustmentEvent e) {
            if (e == null || adjusting) {
                return;
            }

            int scrollBarValue = scrollPanel.getHorizontalScrollBar().getValue();
            scrolling.updateHorizontalScrollBarValue(scrollBarValue, metrics.getCharacterWidth());
            ((ScrollingCapable) codeArea).setScrollPosition(scrolling.getScrollPosition());
            notifyScrolled();
            codeArea.repaint();
//            dataViewScrolled(codeArea.getGraphics());
        }
    }

    private void notifyScrolled() {
        recomputeScrollState();
        ((ScrollingCapable) codeArea).notifyScrolled();
    }

    private static class RowDataCache {

        char[] headerChars;
        byte[] rowData;
        char[] rowPositionCode;
        char[] rowCharacters;
        byte[] unprintables;
    }

    private static class CursorDataCache {

        Rectangle caretRect = new Rectangle();
        Rectangle mirrorCursorRect = new Rectangle();
        final Stroke dashedStroke = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{2}, 0);
        int cursorCharsLength;
        char[] cursorChars;
        int cursorDataLength;
        byte[] cursorData;
    }

    protected enum ScrollingState {
        NO_SCROLLING,
        SCROLLING_BY_SCROLLBAR,
        SCROLLING_BY_MOVEMENT
    }
}
