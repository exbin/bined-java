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
 * Interface for binary data operation.
 *
 * @version 0.2.0 2018/02/13
 * @author ExBin Project (https://exbin.org)
 */
public interface BinaryDataOperation {

    /**
     * Returns caption as text.
     *
     * @return text caption
     */
    @Nonnull
    public String getCaption();

    /**
     * Performs operation on given document.
     *
     * @throws BinaryDataOperationException for operation handling issues
     */
    public void execute() throws BinaryDataOperationException;

    /**
     * Performs operation on given document and returns undo operation.
     *
     * @return undo operation or null if not available
     * @throws BinaryDataOperationException for operation handling issues
     */
    @Nonnull
    public BinaryDataOperation executeWithUndo() throws BinaryDataOperationException;

    /**
     * Disposes command.
     *
     * @throws BinaryDataOperationException for operation handling issues
     */
    public void dispose() throws BinaryDataOperationException;
}
