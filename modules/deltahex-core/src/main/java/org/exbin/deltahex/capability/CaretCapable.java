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
package org.exbin.deltahex.capability;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.exbin.deltahex.CaretMovedListener;
import org.exbin.deltahex.CaretPosition;
import org.exbin.deltahex.CodeAreaCaret;

/**
 * Support for caret / cursor capability.
 *
 * @version 0.2.0 2017/12/15
 * @author ExBin Project (http://exbin.org)
 */
public interface CaretCapable {

    @Nonnull
    CodeAreaCaret getCaret();

    void revealCursor();

    void revealPosition(@Nonnull CaretPosition caretPosition);

    @Nullable
    CaretPosition mousePositionToCaretPosition(int positionX, int positionY);

    void notifyCaretMoved();

    void notifyCaretChanged();

    void addCaretMovedListener(@Nullable CaretMovedListener caretMovedListener);

    void removeCaretMovedListener(@Nullable CaretMovedListener caretMovedListener);

    /**
     * Returns cursor shape type for given position.
     *
     * @param x x-coordinate
     * @param y y-coordinate
     * @return cursor type from java.awt.Cursor
     */
    int getCursorShape(int x, int y);

    public static class CaretCapability implements WorkerCapability {

    }
}
