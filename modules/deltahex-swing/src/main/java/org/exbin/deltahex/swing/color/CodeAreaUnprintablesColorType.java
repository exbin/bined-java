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
package org.exbin.deltahex.swing.color;

/**
 * Enumeration of unprintable color types.
 *
 * @version 0.2.0 2017/04/14
 * @author ExBin Project (http://exbin.org)
 */
public enum CodeAreaUnprintablesColorType implements CodeAreaColorType {

    UNPRINTABLE("unprintable.color");

    private CodeAreaUnprintablesColorType(String preferencesId) {
        this.preferencesId = preferencesId;
    }

    private final String preferencesId;

    @Override
    public String getPreferencesId() {
        return preferencesId;
    }
}