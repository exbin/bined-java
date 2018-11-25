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

import java.awt.Color;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Color profile.
 *
 * @version 0.2.0 2018/11/17
 * @author ExBin Project (https://exbin.org)
 */
public interface CodeAreaColorsProfile {

    /**
     * Returns color of the specified type.
     *
     * @param colorType color type
     * @return color
     */
    @Nullable
    Color getColor(@Nonnull CodeAreaColorType colorType);

    /**
     * Returns color of the specified type.
     *
     * @param colorType color type
     * @param basicAltColor basic color type used as fallback
     * @return color
     */
    @Nullable
    Color getColor(@Nonnull CodeAreaColorType colorType, @Nonnull CodeAreaBasicColors basicAltColor);
}
