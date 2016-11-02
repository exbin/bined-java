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
package org.exbin.deltahex.delta;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.exbin.deltahex.delta.list.DefaultDoublyLinkedList;
import org.exbin.utils.binary_data.BinaryData;
import org.exbin.utils.binary_data.EditableBinaryData;

/**
 * Delta document defined as sequence of segments.
 *
 * @version 0.1.1 2016/11/02
 * @author ExBin Project (http://exbin.org)
 */
public class DeltaDocument implements EditableBinaryData {

    private final SegmentsRepository repository;
    private FileDataSource fileSource;
    private final DefaultDoublyLinkedList<DataSegment> segments = new DefaultDoublyLinkedList<>();

    private long dataLength = 0;
    private final DeltaDocumentWindow window = new DeltaDocumentWindow(this);

    public DeltaDocument(SegmentsRepository repository, FileDataSource fileSource) throws IOException {
        this.repository = repository;
        this.fileSource = fileSource;
        dataLength = fileSource.getFileLength();
        DataSegment fullFileSegment = repository.createFileSegment(fileSource, 0, dataLength);
        segments.add(fullFileSegment);
        window.reset();
    }

    public DeltaDocument(SegmentsRepository repository) {
        this.repository = repository;
        dataLength = 0;
        window.reset();
    }

    // Temporary method for accessing data pages
    public DefaultDoublyLinkedList<DataSegment> getSegments() {
        return segments;
    }

    public DataSegment getSegment(long position) {
        return window.getSegment(position);
    }

    @Override
    public boolean isEmpty() {
        return dataLength == 0;
    }

    @Override
    public long getDataSize() {
        return dataLength;
    }

    @Override
    public byte getByte(long position) {
        return window.getByte(position);
    }

    @Override
    public void setByte(long position, byte value) {
        window.setByte(position, value);
    }

    @Override
    public void insertUninitialized(long startFrom, long length) {
        window.insertUninitialized(startFrom, length);
    }

    @Override
    public void insert(long startFrom, long length) {
        window.insert(startFrom, length);
    }

    @Override
    public void insert(long startFrom, byte[] insertedData) {
        window.insert(startFrom, insertedData);
    }

    @Override
    public void insert(long startFrom, byte[] insertedData, int insertedDataOffset, int insertedDataLength) {
        window.insert(startFrom, insertedData, insertedDataOffset, insertedDataLength);
    }

    @Override
    public void insert(long startFrom, BinaryData insertedData) {
        window.insert(startFrom, insertedData);
    }

    @Override
    public void insert(long startFrom, BinaryData insertedData, long insertedDataOffset, long insertedDataLength) {
        window.insert(startFrom, insertedData, insertedDataOffset, insertedDataLength);
    }

    public void insert(long startFrom, DataSegment segment) {
        window.insert(startFrom, segment);
    }

    @Override
    public long insert(long l, InputStream in, long l1) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void replace(long targetPosition, BinaryData replacingData) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void replace(long targetPosition, BinaryData replacingData, long startFrom, long length) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void replace(long targetPosition, byte[] replacingData) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void replace(long targetPosition, byte[] replacingData, int replacingDataOffset, int length) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void fillData(long startFrom, long length) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void fillData(long startFrom, long length, byte fill) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void remove(long startFrom, long length) {
        window.remove(startFrom, length);
    }

    @Override
    public void clear() {
        dataLength = 0;
        for (DataSegment segment : segments) {
            repository.dropSegment(segment);
        }
        segments.clear();
        window.reset();
    }

    public void destroy() {
        repository.dropDocument(this);
    }

    @Override
    public void loadFromStream(InputStream in) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void saveToStream(OutputStream out) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public BinaryData copy() {
        return window.copy();
    }

    @Override
    public BinaryData copy(long startFrom, long length) {
        return window.copy(startFrom, length);
    }

    @Override
    public void copyToArray(long startFrom, byte[] target, int offset, int length) {
        // TODO optimalization later
        for (int i = 0; i < length; i++) {
            target[offset + i] = getByte(startFrom + i);
        }
    }

    @Override
    public OutputStream getDataOutputStream() {
        return new DeltaDocumentOutputStream(this);
    }

    @Override
    public InputStream getDataInputStream() {
        return new DeltaDocumentInputStream(this);
    }

    @Override
    public void setDataSize(long dataSize) {
        if (dataSize < dataLength) {
            remove(dataSize, dataLength - dataSize);
        } else if (dataSize > dataLength) {
            insert(dataLength, dataSize - dataLength);
        }
    }

    void setDataLength(long dataSize) {
        this.dataLength = dataSize;
    }

    /**
     * Returns segment starting from given position or copy of part of the
     * segment starting from given position up to the end of length.
     *
     * @param position position
     * @param length length
     * @return data segment
     */
    public DataSegment getPartCopy(long position, long length) {
        return window.getPartCopy(position, length);
    }

    public FileDataSource getFileSource() {
        return fileSource;
    }

    public void setFileSource(FileDataSource fileSource) {
        this.fileSource = fileSource;
    }

    public SegmentsRepository getRepository() {
        return repository;
    }

    /**
     * POJO structure for sliding data pointer.
     */
    private static class DataPointer {

        long position;
        DataSegment segment;

        void setPointer(long position, DataSegment segment) {
            this.position = position;
            this.segment = segment;
        }
    }
}
