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
package org.exbin.bined.color;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Enumeration of color types for main group.
 *
 * @version 0.2.0 2018/11/17
 * @author ExBin Project (https://exbin.org)
 */
public enum CodeAreaBasicColors implements CodeAreaColorType {

    TEXT_COLOR("textColor"),
    TEXT_BACKGROUND("textBackground"),
    ALTERNATE_COLOR("alternateColor"),
    ALTERNATE_BACKGROUND("alternateBackground"),
    SELECTION_COLOR("selectionColor"),
    SELECTION_BACKGROUND("selectionBackground"),
    MIRROR_SELECTION_COLOR("mirrorSelectionColor"),
    MIRROR_SELECTION_BACKGROUND("mirrorSelectionBackground");

    @Nonnull
    private final String typeId;

    private CodeAreaBasicColors(@Nonnull String typeId) {
        this.typeId = typeId;
    }

    @Nonnull
    @Override
    public String getId() {
        return typeId;
    }

    @Nullable
    @Override
    public CodeAreaColorGroup getGroup() {
        return null;
    }
}
