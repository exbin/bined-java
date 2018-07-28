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
package org.exbin.bined.swing.basic;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.exbin.bined.DataProvider;
import org.exbin.bined.ScrollBarVisibility;
import org.exbin.bined.swing.ScrollingDirection;
import org.exbin.bined.swing.capability.ScrollingCapable;

/**
 * Code area scrolling.
 *
 * @version 0.2.0 2018/07/28
 * @author ExBin Project (http://exbin.org)
 */
public class BasicCodeAreaScrolling {

    @Nonnull
    private final CodeAreaScrollPosition scrollPosition = new CodeAreaScrollPosition();
    @Nonnull
    private ScrollBarVerticalScale scrollBarVerticalScale = ScrollBarVerticalScale.NORMAL;

    @Nonnull
    private VerticalScrollUnit verticalScrollUnit = VerticalScrollUnit.PIXEL;
    @Nonnull
    private ScrollBarVisibility verticalScrollBarVisibility = ScrollBarVisibility.IF_NEEDED;
    @Nonnull
    private HorizontalScrollUnit horizontalScrollUnit = HorizontalScrollUnit.CHARACTER;
    @Nonnull
    private ScrollBarVisibility horizontalScrollBarVisibility = ScrollBarVisibility.IF_NEEDED;
    @Nonnull
    private final CodeAreaScrollPosition maximumScrollPosition = new CodeAreaScrollPosition();

    public void updateCache(@Nonnull DataProvider worker) {
        verticalScrollUnit = ((ScrollingCapable) worker).getVerticalScrollUnit();
        verticalScrollBarVisibility = ((ScrollingCapable) worker).getVerticalScrollBarVisibility();
        horizontalScrollUnit = ((ScrollingCapable) worker).getHorizontalScrollUnit();
        horizontalScrollBarVisibility = ((ScrollingCapable) worker).getHorizontalScrollBarVisibility();
    }

    public void updateHorizontalScrollBarValue(int scrollBarValue, int characterWidth) {
        if (horizontalScrollUnit == HorizontalScrollUnit.CHARACTER) {
            scrollPosition.setCharPosition(scrollBarValue);
        } else {
            if (characterWidth == 0) {
                scrollPosition.setCharPosition(0);
                scrollPosition.setCharOffset(0);
            } else if (horizontalScrollUnit == HorizontalScrollUnit.CHARACTER) {
                int charPosition = scrollBarValue / characterWidth;
                if (scrollBarValue % characterWidth > 0) {
                    charPosition++;
                }
                scrollPosition.setCharPosition(charPosition);
                scrollPosition.setCharOffset(0);
            } else {
                scrollPosition.setCharPosition(scrollBarValue / characterWidth);
                scrollPosition.setCharOffset(scrollBarValue % characterWidth);
            }
        }
    }

    public void updateVerticalScrollBarValue(int scrollBarValue, int rowHeight, int maxValue, long rowsPerDocumentToLastPage) {
        if (scrollBarVerticalScale == ScrollBarVerticalScale.SCALED) {
            long targetRow;
            if (scrollBarValue > 0 && rowsPerDocumentToLastPage > maxValue / scrollBarValue) {
                targetRow = scrollBarValue * (rowsPerDocumentToLastPage / maxValue);
                long rest = rowsPerDocumentToLastPage % maxValue;
                targetRow += (rest * scrollBarValue) / maxValue;
            } else {
                targetRow = (scrollBarValue * rowsPerDocumentToLastPage) / Integer.MAX_VALUE;
            }
            scrollPosition.setRowPosition(targetRow);
            if (verticalScrollUnit != VerticalScrollUnit.ROW) {
                scrollPosition.setRowOffset(0);
            }
        } else {
            if (rowHeight == 0) {
                scrollPosition.setRowPosition(0);
                scrollPosition.setRowOffset(0);
            } else if (verticalScrollUnit == VerticalScrollUnit.ROW) {
                int rowPosition = scrollBarValue / rowHeight;
                if (scrollBarValue % rowHeight > 0) {
                    rowPosition++;
                }
                scrollPosition.setRowPosition(rowPosition);
                scrollPosition.setRowOffset(0);
            } else {
                scrollPosition.setRowPosition(scrollBarValue / rowHeight);
                scrollPosition.setRowOffset(scrollBarValue % rowHeight);
            }
        }
    }

    public int getVerticalScrollValue(int rowHeight, long rowsPerDocument) {
        if (scrollBarVerticalScale == ScrollBarVerticalScale.SCALED) {
            int scrollValue;
            if (scrollPosition.getCharPosition() < Long.MAX_VALUE / Integer.MAX_VALUE) {
                scrollValue = (int) ((scrollPosition.getRowPosition() * Integer.MAX_VALUE) / rowsPerDocument);
            } else {
                scrollValue = (int) (scrollPosition.getRowPosition() / (rowsPerDocument / Integer.MAX_VALUE));
            }
            return scrollValue;
        } else if (verticalScrollUnit == VerticalScrollUnit.ROW) {
            return (int) scrollPosition.getRowPosition() * rowHeight;
        } else {
            return (int) (scrollPosition.getRowPosition() * rowHeight + scrollPosition.getRowOffset());
        }
    }

    public int getHorizontalScrollValue(int characterWidth) {
        if (horizontalScrollUnit == HorizontalScrollUnit.CHARACTER) {
            return scrollPosition.getCharPosition() * characterWidth;
        } else {
            return scrollPosition.getCharPosition() * characterWidth + scrollPosition.getCharOffset();
        }
    }

    @Nonnull
    public CodeAreaScrollPosition computeScrolling(@Nonnull CodeAreaScrollPosition startPosition, @Nonnull ScrollingDirection direction, int rowsPerPage, long rowsPerDocument) {
        CodeAreaScrollPosition targetPosition = new CodeAreaScrollPosition();
        targetPosition.setScrollPosition(startPosition);

        switch (direction) {
            case UP: {
                if (startPosition.getRowPosition() == 0) {
                    targetPosition.setRowOffset(0);
                } else {
                    targetPosition.setRowPosition(startPosition.getRowPosition() - 1);
                }
                break;
            }
            case DOWN: {
                if (maximumScrollPosition.isRowPositionGreaterThan(startPosition)) {
                    targetPosition.setRowPosition(startPosition.getRowPosition() + 1);
                }
                break;
            }
            case LEFT: {
                if (startPosition.getCharPosition() == 0) {
                    targetPosition.setCharOffset(0);
                } else {
                    targetPosition.setCharPosition(startPosition.getCharPosition() - 1);
                }
                break;
            }
            case RIGHT: {
                if (maximumScrollPosition.isCharPositionGreaterThan(startPosition)) {
                    targetPosition.setCharPosition(startPosition.getCharPosition() + 1);
                }
                break;
            }
            case PAGE_UP: {
                if (startPosition.getRowPosition() < rowsPerPage) {
                    targetPosition.setRowPosition(0);
                    targetPosition.setRowOffset(0);
                } else {
                    targetPosition.setRowPosition(startPosition.getRowPosition() - rowsPerPage);
                }
                break;
            }
            case PAGE_DOWN: {
                if (startPosition.getRowPosition() <= rowsPerDocument - rowsPerPage * 2) {
                    targetPosition.setRowPosition(startPosition.getRowPosition() + rowsPerPage);
                } else if (rowsPerDocument > rowsPerPage) {
                    targetPosition.setRowPosition(rowsPerDocument - rowsPerPage);
                } else {
                    targetPosition.setRowPosition(0);
                }
                break;
            }
            default:
                throw new IllegalStateException("Unexpected scrolling direction type: " + direction.name());
        }

        return targetPosition;
    }

    @Nullable
    public CodeAreaScrollPosition computeRevealScrollPosition(long rowPosition, int charPosition, int bytesPerRow, int previewCharPos, int rowsPerPage, int charactersPerPage, int dataViewWidth, int dataViewHeight, int characterWidth, int rowHeight) {

        CodeAreaScrollPosition targetScrollPosition = new CodeAreaScrollPosition();
        targetScrollPosition.setScrollPosition(scrollPosition);

        boolean scrolled = false;
        if (rowPosition < scrollPosition.getRowPosition()) {
            // Scroll row up
            targetScrollPosition.setRowPosition(rowPosition);
            targetScrollPosition.setRowOffset(0);
            scrolled = true;
        } else if ((rowPosition == scrollPosition.getRowPosition() && scrollPosition.getRowOffset() > 0)) {
            // Scroll row offset up
            targetScrollPosition.setRowOffset(0);
            scrolled = true;
        } else {
            int bottomRowOffset;
            if (verticalScrollUnit == VerticalScrollUnit.ROW) {
                bottomRowOffset = 0;
            } else {
                if (dataViewHeight < rowHeight) {
                    bottomRowOffset = 0;
                } else {
                    bottomRowOffset = dataViewHeight % rowHeight;
                }
            }

            if (rowPosition >= scrollPosition.getRowPosition() + rowsPerPage) {
                // Scroll row down
                long targetRowPosition = rowPosition - rowsPerPage;
                if (verticalScrollUnit == VerticalScrollUnit.ROW && (dataViewHeight % rowHeight) > 0) {
                    targetRowPosition++;
                }
                targetScrollPosition.setRowPosition(targetRowPosition);
                targetScrollPosition.setRowOffset(bottomRowOffset);
                scrolled = true;
            } else if (rowPosition == scrollPosition.getRowPosition() + rowsPerPage && scrollPosition.getRowOffset() > bottomRowOffset) {
                // Scroll row offset down
                targetScrollPosition.setRowOffset(bottomRowOffset);
                scrolled = true;
            }
        }

        if (charPosition < scrollPosition.getCharPosition()) {
            // Scroll characters left
            targetScrollPosition.setCharPosition(charPosition);
            targetScrollPosition.setCharOffset(0);
            scrolled = true;
        } else if (charPosition == scrollPosition.getCharPosition() && scrollPosition.getCharOffset() > 0) {
            // Scroll character offset left
            targetScrollPosition.setCharOffset(0);
            scrolled = true;
        } else {
            int rightCharOffset;
            if (horizontalScrollUnit == HorizontalScrollUnit.CHARACTER) {
                rightCharOffset = 0;
            } else {
                if (dataViewWidth < characterWidth) {
                    rightCharOffset = 0;
                } else {
                    rightCharOffset = dataViewWidth % characterWidth;
                }
            }

            if (charPosition > scrollPosition.getCharPosition() + charactersPerPage) {
                // Scroll character right
                targetScrollPosition.setCharPosition(charPosition - charactersPerPage);
                targetScrollPosition.setCharOffset(rightCharOffset);
                scrolled = true;
            } else if (charPosition == scrollPosition.getCharPosition() + charactersPerPage && scrollPosition.getCharOffset() > rightCharOffset) {
                // Scroll row offset down
                targetScrollPosition.setCharOffset(rightCharOffset);
                scrolled = true;
            }
        }
        return scrolled ? targetScrollPosition : null;
    }

    @Nonnull
    public CodeAreaScrollPosition computeCenterOnScrollPosition(long rowPosition, int charPosition, int bytesPerRow, int previewCharPos, int rowsPerRect, int charactersPerRect, int dataViewWidth, int dataViewHeight, int characterWidth, int rowHeight) {
        CodeAreaScrollPosition targetScrollPosition = new CodeAreaScrollPosition();
        targetScrollPosition.setScrollPosition(scrollPosition);

        long centerRowPosition = rowPosition - rowsPerRect / 2;
        int rowCorrection = (rowsPerRect & 1) == 0 ? rowHeight : 0;
        int heightDiff = (rowsPerRect * rowHeight + rowCorrection - dataViewHeight) / 2;
        int rowOffset;
        if (verticalScrollUnit == VerticalScrollUnit.ROW) {
            rowOffset = 0;
        } else {
            if (heightDiff > 0) {
                rowOffset = heightDiff;
            } else {
                rowOffset = 0;
            }
        }
        if (centerRowPosition < 0) {
            centerRowPosition = 0;
            rowOffset = 0;
        } else if (centerRowPosition > maximumScrollPosition.getRowPosition() || (centerRowPosition == maximumScrollPosition.getRowPosition() && rowOffset > maximumScrollPosition.getRowOffset())) {
            centerRowPosition = maximumScrollPosition.getRowPosition();
            rowOffset = maximumScrollPosition.getRowOffset();
        }
        targetScrollPosition.setRowPosition(centerRowPosition);
        targetScrollPosition.setRowOffset(rowOffset);

        int centerCharPosition = charPosition - charactersPerRect / 2;
        int charCorrection = (charactersPerRect & 1) == 0 ? rowHeight : 0;
        int widthDiff = (charactersPerRect * characterWidth + charCorrection - dataViewWidth) / 2;
        int charOffset;
        if (horizontalScrollUnit == HorizontalScrollUnit.CHARACTER) {
            charOffset = 0;
        } else {
            if (widthDiff > 0) {
                charOffset = widthDiff;
            } else {
                charOffset = 0;
            }
        }
        if (centerCharPosition < 0) {
            centerCharPosition = 0;
            charOffset = 0;
        } else if (centerCharPosition > maximumScrollPosition.getCharPosition() || (centerCharPosition == maximumScrollPosition.getCharPosition() && charOffset > maximumScrollPosition.getCharOffset())) {
            centerCharPosition = maximumScrollPosition.getCharPosition();
            charOffset = maximumScrollPosition.getCharOffset();
        }
        targetScrollPosition.setCharPosition(centerCharPosition);
        targetScrollPosition.setCharOffset(charOffset);
        return targetScrollPosition;
    }

    @Nonnull
    public void updateMaximumScrollPosition(long rowsPerDocument, int rowsPerPage, int charactersPerRow, int charactersPerPage, int dataViewWidth, int dataViewHeight, int characterWidth, int rowHeight) {
        maximumScrollPosition.reset();
        if (rowHeight == 0 || characterWidth == 0) {
            return;
        }
        if (rowsPerDocument > rowsPerPage) {
            maximumScrollPosition.setRowPosition(rowsPerDocument - rowsPerPage);
        }
        if (verticalScrollUnit == VerticalScrollUnit.PIXEL) {
            maximumScrollPosition.setRowOffset(dataViewHeight % rowHeight);
        }

        if (charactersPerRow > charactersPerPage) {
            maximumScrollPosition.setCharPosition(charactersPerRow - charactersPerPage);
        }
        if (horizontalScrollUnit == HorizontalScrollUnit.PIXEL) {
            maximumScrollPosition.setCharOffset(dataViewWidth % characterWidth);
        }
    }

    @Nonnull
    public CodeAreaScrollPosition getScrollPosition() {
        return scrollPosition;
    }

    public void setScrollPosition(@Nonnull CodeAreaScrollPosition scrollPosition) {
        this.scrollPosition.setScrollPosition(scrollPosition);
    }

    @Nonnull
    public ScrollBarVerticalScale getScrollBarVerticalScale() {
        return scrollBarVerticalScale;
    }

    public void setScrollBarVerticalScale(ScrollBarVerticalScale scrollBarVerticalScale) {
        this.scrollBarVerticalScale = scrollBarVerticalScale;
    }

    @Nonnull
    public VerticalScrollUnit getVerticalScrollUnit() {
        return verticalScrollUnit;
    }

    @Nonnull
    public ScrollBarVisibility getVerticalScrollBarVisibility() {
        return verticalScrollBarVisibility;
    }

    @Nonnull
    public HorizontalScrollUnit getHorizontalScrollUnit() {
        return horizontalScrollUnit;
    }

    @Nonnull
    public ScrollBarVisibility getHorizontalScrollBarVisibility() {
        return horizontalScrollBarVisibility;
    }

    @Nonnull
    public CodeAreaScrollPosition getMaximumScrollPosition() {
        return maximumScrollPosition;
    }

    /**
     * Enumeration of vertical scaling modes.
     */
    protected enum ScrollBarVerticalScale {
        /**
         * Normal ratio 1 on 1.
         */
        NORMAL,
        /**
         * Height is more than available range and scaled.
         */
        SCALED
    }
}
