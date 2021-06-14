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
package org.exbin.bined.basic;

import org.exbin.bined.CodeAreaSection;

/**
 * Enumeration of basic cursor position section.
 *
 * @version 0.2.0 2018/10/24
 * @author ExBin Project (https://exbin.org)
 */
public enum BasicCodeAreaSection implements CodeAreaSection {

    /**
     * Section of code area with codes for binary data representation.
     */
    CODE_MATRIX,
    /**
     * Section of code area with textual preview characters.
     */
    TEXT_PREVIEW
}
