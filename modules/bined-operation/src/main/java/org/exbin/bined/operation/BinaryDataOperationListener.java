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
package org.exbin.bined.operation;

import javax.annotation.Nonnull;

/**
 * Operation execution listener.
 *
 * @version 0.2.0 2018/02/13
 * @author ExBin Project (https://exbin.org)
 */
public interface BinaryDataOperationListener {

    /**
     * Notifies about change in document content.
     *
     * @param event change event
     */
    void notifyChange(@Nonnull BinaryDataOperationEvent event);
}
