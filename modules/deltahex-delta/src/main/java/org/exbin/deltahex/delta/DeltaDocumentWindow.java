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

import org.exbin.deltahex.delta.list.DefaultDoublyLinkedList;
import org.exbin.utils.binary_data.BinaryData;
import org.exbin.utils.binary_data.OutOfBoundsException;

/**
 * Access window for delta document.
 *
 * @version 0.1.1 2016/10/20
 * @author ExBin Project (http://exbin.org)
 */
public class DeltaDocumentWindow {

    private final DeltaDocument document;

    private final DataPointer pointer = new DataPointer();

    public DeltaDocumentWindow(DeltaDocument document) {
        this.document = document;
    }

    public long getDataSize() {
        return document.getDataSize();
    }

    public byte getByte(long position) {
        focusSegment(position);

        if (pointer.segment instanceof FileSegment) {
            return ((FileSegment) pointer.segment).getByte(((FileSegment) pointer.segment).getStartPosition() + (position - pointer.position));
        } else {
            return ((MemorySegment) pointer.segment).getByte(position - pointer.position);
        }
    }

    public void setByte(long position, byte value) {
        DefaultDoublyLinkedList<DataSegment> segments = document.getSegments();
        SegmentsRepository repository = document.getRepository();
        focusSegment(position);

        if (pointer.segment instanceof FileSegment) {
            if (pointer.position != position) {
                splitSegment(position);
                focusSegment(position);
            }

            DataSegment prev = segments.prevTo(pointer.segment);
            if (prev instanceof MemorySegment) {
                repository.setMemoryByte((MemorySegment) prev, prev.getLength(), value);
            } else {
                MemorySegment segment = repository.createMemorySegment(repository.openMemorySource(), 0, 0);
                repository.setMemoryByte(segment, 0, value);
                segments.addBefore(pointer.segment, segment);
            }
            pointer.position++;
            FileSegment documentSegment = ((FileSegment) pointer.segment);
            documentSegment.setStartPosition(documentSegment.getStartPosition() + 1);
            if (documentSegment.getLength() == 1) {
                segments.remove(documentSegment);
                repository.dropSegment(documentSegment);
            } else {
                documentSegment.setLength(documentSegment.getLength() - 1);
            }
        } else {
            if (pointer.segment == null) {
                pointer.segment = repository.createMemorySegment(repository.openMemorySource(), 0, 0);
                segments.add(pointer.segment);
            }
            repository.setMemoryByte((MemorySegment) pointer.segment, position - pointer.position, value);
        }

        if (position >= getDataSize()) {
            document.setDataLength(position + 1);
        }
    }

    public void insertUninitialized(long startFrom, long length) {
        if (length == 0) {
            return;
        }

        DefaultDoublyLinkedList<DataSegment> segments = document.getSegments();
        SegmentsRepository repository = document.getRepository();

        focusSegment(startFrom);
        document.setDataLength(document.getDataSize() + length);
        if (pointer.segment instanceof MemorySegment) {
            repository.insertUninitializedMemoryData((MemorySegment) pointer.segment, startFrom - pointer.position, length);
        } else {
            if (startFrom > pointer.position) {
                splitSegment(startFrom);
                focusSegment(startFrom);
            }
            MemorySegment insertedSegment = repository.createMemorySegment();
            repository.insertUninitializedMemoryData((MemorySegment) insertedSegment, 0, length);
            if (pointer.segment == null) {
                segments.add(0, insertedSegment);
            } else {
                segments.addBefore(pointer.segment, insertedSegment);
            }
            pointer.segment = insertedSegment;
        }
    }

    public void insert(long startFrom, long length) {
        DefaultDoublyLinkedList<DataSegment> segments = document.getSegments();
        SegmentsRepository repository = document.getRepository();
        if (length == 0) {
            return;
        }

        focusSegment(startFrom);
        document.setDataLength(document.getDataSize() + length);
        if (pointer.segment instanceof MemorySegment) {
            repository.insertMemoryData((MemorySegment) pointer.segment, startFrom - pointer.position, length);
        } else {
            if (startFrom > pointer.position) {
                splitSegment(startFrom);
                focusSegment(startFrom);
            }
            MemorySegment insertedSegment = repository.createMemorySegment();
            repository.insertMemoryData((MemorySegment) insertedSegment, 0, length);
            if (pointer.segment == null) {
                segments.add(0, insertedSegment);
            } else {
                segments.addBefore(pointer.segment, insertedSegment);
            }
            pointer.segment = insertedSegment;
        }
    }

    public void insert(long startFrom, byte[] insertedData) {
        DefaultDoublyLinkedList<DataSegment> segments = document.getSegments();
        SegmentsRepository repository = document.getRepository();
        if (insertedData.length == 0) {
            return;
        }

        focusSegment(startFrom);
        document.setDataLength(document.getDataSize() + insertedData.length);
        if (pointer.segment instanceof MemorySegment) {
            repository.insertMemoryData((MemorySegment) pointer.segment, startFrom - pointer.position, insertedData);
        } else {
            if (startFrom > pointer.position) {
                splitSegment(startFrom);
                focusSegment(startFrom);
            }
            MemorySegment insertedSegment = repository.createMemorySegment();
            repository.insertMemoryData((MemorySegment) insertedSegment, 0, insertedData);
            if (pointer.segment == null) {
                segments.add(0, insertedSegment);
            } else {
                segments.addBefore(pointer.segment, insertedSegment);
            }
            pointer.segment = insertedSegment;
        }
    }

    public void insert(long startFrom, byte[] insertedData, int insertedDataOffset, int insertedDataLength) {
        DefaultDoublyLinkedList<DataSegment> segments = document.getSegments();
        SegmentsRepository repository = document.getRepository();

        focusSegment(startFrom);
        document.setDataLength(document.getDataSize() + insertedDataLength);
        if (pointer.segment instanceof MemorySegment) {
            repository.insertMemoryData((MemorySegment) pointer.segment, startFrom - pointer.position, insertedData);
        } else {
            if (startFrom > pointer.position) {
                splitSegment(startFrom);
                focusSegment(startFrom);
            }
            MemorySegment insertedSegment = repository.createMemorySegment();
            repository.insertMemoryData((MemorySegment) insertedSegment, 0, insertedData);
            if (pointer.segment == null) {
                segments.add(0, insertedSegment);
            } else {
                segments.addBefore(pointer.segment, insertedSegment);
            }
            pointer.segment = insertedSegment;
        }
    }

    public void insert(long startFrom, BinaryData insertedData) {
        if (insertedData.isEmpty()) {
            return;
        }

        DefaultDoublyLinkedList<DataSegment> segments = document.getSegments();
        SegmentsRepository repository = document.getRepository();

        focusSegment(startFrom);
        document.setDataLength(document.getDataSize() + insertedData.getDataSize());
        if (insertedData instanceof DeltaDocument) {
            if (pointer.position < startFrom) {
                splitSegment(startFrom);
                focusSegment(startFrom);
            }

            // Copy all segments from inserted document
            DeltaDocument insertedDocument = (DeltaDocument) insertedData;
            DataSegment segment = insertedDocument.getSegments().first();
            DataSegment copy = repository.copySegment(segment);
            DataSegment first = copy;
            segments.addBefore(pointer.segment, copy);
            DataSegment next = segment.getNext();
            while (next != null) {
                DataSegment nextCopy = repository.copySegment(next);
                segments.addAfter(copy, nextCopy);
                copy = nextCopy;
                next = next.getNext();
            }
            pointer.segment = first;
            tryMergeArea(startFrom, insertedData.getDataSize());
        } else if (pointer.segment instanceof MemorySegment) {
            repository.insertMemoryData((MemorySegment) pointer.segment, startFrom - pointer.position, insertedData);
        } else {
            if (pointer.position < startFrom) {
                splitSegment(startFrom);
                focusSegment(startFrom);
            }
            MemorySegment insertedSegment = repository.createMemorySegment();
            repository.insertMemoryData((MemorySegment) insertedSegment, 0, insertedData);
            if (pointer.segment == null) {
                segments.add(0, insertedSegment);
            } else {
                segments.addBefore(pointer.segment, insertedSegment);
            }
            pointer.segment = insertedSegment;
        }
    }

    public void insert(long startFrom, BinaryData insertedData, long insertedDataOffset, long insertedDataLength) {
        if (insertedDataLength == 0) {
            return;
        }

        DefaultDoublyLinkedList<DataSegment> segments = document.getSegments();
        SegmentsRepository repository = document.getRepository();
        focusSegment(startFrom);
        document.setDataLength(document.getDataSize() + insertedDataLength);
        if (insertedData instanceof DeltaDocument) {
            if (pointer.position < startFrom) {
                splitSegment(startFrom);
                focusSegment(startFrom);
            }

            // Copy all segments from inserted document
            DeltaDocument insertedDocument = (DeltaDocument) insertedData;

            long position = insertedDataOffset;
            long length = insertedDataLength;
            DataSegment segment = insertedDocument.getPartCopy(position, length);
            position += segment.getLength();
            length -= segment.getLength();
            DataSegment first = segment;
            if (pointer.segment == null) {
                segments.add(segment);
            } else {
                segments.addBefore(pointer.segment, segment);
            }
            DataSegment next = segment.getNext();
            while (length > 0) {
                DataSegment nextSegment = insertedDocument.getPartCopy(position, length);
                position += nextSegment.getLength();
                length -= nextSegment.getLength();
                segments.addAfter(segment, nextSegment);
                segment = nextSegment;
                next = next.getNext();
            }
            pointer.segment = first;
            tryMergeArea(startFrom, insertedData.getDataSize());
        } else if (pointer.segment instanceof MemorySegment) {
            repository.insertMemoryData((MemorySegment) pointer.segment, startFrom - pointer.position, insertedData, insertedDataOffset, insertedDataLength);
        } else {
            if (pointer.position < startFrom) {
                splitSegment(startFrom);
                focusSegment(startFrom);
            }
            MemorySegment insertedSegment = repository.createMemorySegment();
            repository.insertMemoryData((MemorySegment) insertedSegment, 0, insertedData, insertedDataOffset, insertedDataLength);
            if (pointer.segment == null) {
                segments.add(0, insertedSegment);
            } else {
                segments.addBefore(pointer.segment, insertedSegment);
            }
            pointer.segment = insertedSegment;
        }
    }

    public void insert(long startFrom, DataSegment segment) {
        DefaultDoublyLinkedList<DataSegment> segments = document.getSegments();
        focusSegment(startFrom);
        if (pointer.position < startFrom) {
            splitSegment(startFrom);
            focusSegment(startFrom);
        }
        if (pointer.segment == null) {
            segments.add(0, segment);
        } else {
            segments.addAfter(pointer.segment, segment);
        }
    }

    public void remove(long startFrom, long length) {
        if (startFrom + length > document.getDataSize()) {
            throw new OutOfBoundsException("Removed area is out of bounds");
        }

        DefaultDoublyLinkedList<DataSegment> segments = document.getSegments();
        SegmentsRepository repository = document.getRepository();
        if (length > 0) {
            document.setDataLength(document.getDataSize() - length);
            focusSegment(startFrom + length);
            splitSegment(startFrom + length);
            focusSegment(startFrom);
            splitSegment(startFrom);
            focusSegment(startFrom);

            // Save position to return to
            DataSegment prevSegment = (DataSegment) pointer.segment.getPrev();
            long prevPointerPosition = prevSegment == null ? 0 : pointer.position - prevSegment.getLength();

            // Drop all segments in given range
            while (length > 0) {
                length -= pointer.segment.getLength();
                DataSegment next = segments.nextTo(pointer.segment);
                repository.dropSegment(pointer.segment);
                segments.remove(pointer.segment);
                pointer.segment = next;
            }

            // Set pointer position
            pointer.segment = prevSegment;
            pointer.position = prevPointerPosition;
            tryMergeSegments(startFrom);
        }
    }

    public void reset() {
        pointer.setPointer(0, document.getSegments().first());
    }

    public void setDataSize(long dataSize) {
        document.setDataSize(dataSize);
    }

    public BinaryData copy() {
        SegmentsRepository repository = document.getRepository();
        DefaultDoublyLinkedList<DataSegment> segments = document.getSegments();
        DeltaDocument copy = repository.createDocument();
        copy.setDataLength(getDataSize());
        for (DataSegment segment : segments) {
            copy.getSegments().add(repository.copySegment(segment));
        }
        return copy;
    }

    public BinaryData copy(long startFrom, long length) {
        SegmentsRepository repository = document.getRepository();
        DeltaDocument copy = repository.createDocument();
        copy.setDataLength(length);
        focusSegment(startFrom);

        DefaultDoublyLinkedList<DataSegment> segments = document.getSegments();
        DataSegment segment = pointer.segment;
        long offset = startFrom - pointer.position;
        while (length > 0) {
            long segmentLength = segment.getLength();
            long copyLength = segmentLength - offset;
            if (copyLength > length) {
                copyLength = length;
            }

            if (offset == 0 && copyLength == segmentLength) {
                copy.getSegments().add(repository.copySegment(pointer.segment));
            } else if (pointer.segment instanceof MemorySegment) {
                MemorySegment memorySegment = (MemorySegment) pointer.segment;
                copy.getSegments().add(repository.createMemorySegment(memorySegment.getSource(), memorySegment.getStartPosition() + offset, copyLength));
            } else {
                FileSegment fileSegment = (FileSegment) pointer.segment;
                copy.getSegments().add(repository.createFileSegment(fileSegment.getSource(), fileSegment.getStartPosition() + offset, copyLength));
            }
            length -= copyLength;
            offset = 0;
            segment = segments.nextTo(segment);
        }

        return copy;
    }

    public void copyToArray(long startFrom, byte[] target, int offset, int length) {
        document.copyToArray(startFrom, target, offset, length);
    }

    /**
     * Splits current pointer segment on given absolute position.
     *
     * @param position split position
     */
    public void splitSegment(long position) {
        if (position < pointer.position || position > pointer.position + pointer.segment.getLength()) {
            throw new IllegalStateException("Split position is out of current segment");
        }

        if (pointer.position == position || pointer.segment.getStartPosition() + pointer.segment.getLength() == position) {
            // No action needed
            return;
        }

        DefaultDoublyLinkedList<DataSegment> segments = document.getSegments();
        SegmentsRepository repository = document.getRepository();
        long firstPartSize = position - pointer.position;
        if (pointer.segment instanceof MemorySegment) {
            MemorySegment memorySegment = (MemorySegment) pointer.segment;
            MemorySegment newSegment = repository.createMemorySegment(memorySegment.getSource(), memorySegment.getStartPosition() + firstPartSize, memorySegment.getLength() - firstPartSize);
            memorySegment.setLength(firstPartSize);
            segments.addAfter(pointer.segment, newSegment);
        } else {
            FileSegment fileSegment = (FileSegment) pointer.segment;
            FileSegment newSegment = repository.createFileSegment(fileSegment.getSource(), fileSegment.getStartPosition() + firstPartSize, fileSegment.getLength() - firstPartSize);
            fileSegment.setLength(firstPartSize);
            segments.addAfter(fileSegment, newSegment);
        }
    }

    public DataSegment getSegment(long position) {
        focusSegment(position);
        return pointer.segment;
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
        focusSegment(position);
        if (pointer.segment == null) {
            return null;
        }

        SegmentsRepository repository = document.getRepository();
        long offset = position - pointer.position;
        long partLength = length;
        if (pointer.segment.getLength() - offset < partLength) {
            partLength = pointer.segment.getLength() - offset;
        }
        return repository.copySegment(pointer.segment, offset, partLength);
    }

    /**
     * Focuses segment starting at or before given position and ending after it.
     *
     * Returns null if position is at the end of the document and throws out of
     * bounds exception otherwise.
     *
     * @param position requested position
     * @throws OutOfBoundsException if position is before or after document
     */
    private void focusSegment(long position) {
        DefaultDoublyLinkedList<DataSegment> segments = document.getSegments();
        SegmentsRepository repository = document.getRepository();
        if (position == 0) {
            pointer.position = 0;
            pointer.segment = segments.first();
            return;
        } else if (position == getDataSize()) {
            pointer.position = getDataSize();
            pointer.segment = null;
            return;
        } else if (position < 0 || position > getDataSize()) {
            throw new OutOfBoundsException("Position index out of range");
        }

        if (position < pointer.position) {
            if (pointer.segment == null && position == getDataSize()) {
                pointer.segment = segments.last();
                if (pointer.segment == null) {
                    throw new IllegalStateException("Unexpected null segment");
                }
                pointer.position -= pointer.segment.getLength();
            }

            while (position < pointer.position) {
                pointer.segment = (DataSegment) segments.prevTo(pointer.segment);
                if (pointer.segment == null) {
                    throw new IllegalStateException("Unexpected null segment");
                }
                pointer.position -= pointer.segment.getLength();
            }
        } else {
            if (pointer.segment == null) {
                throw new IllegalStateException("Unexpected null segment");
            }

            while (position >= pointer.position + pointer.segment.getLength()) {
                if ((pointer.segment.getNext() == null) && (position == pointer.position + pointer.segment.getLength())) {
                    break;
                }
                pointer.position += pointer.segment.getLength();
                pointer.segment = (DataSegment) segments.nextTo(pointer.segment);
                if (pointer.segment == null) {
                    throw new IllegalStateException("Unexpected null segment");
                }
            }
        }
    }

    private void tryMergeArea(long position, long length) {
        tryMergeSegments(position);
        // TODO
        tryMergeSegments(position + length);
    }

    /**
     * Attempts to merge segments at specified position.
     *
     * @param position target position
     */
    private boolean tryMergeSegments(long position) {
        if (position == 0 || position >= getDataSize()) {
            return false;
        }

        DefaultDoublyLinkedList<DataSegment> segments = document.getSegments();
        SegmentsRepository repository = document.getRepository();
        focusSegment(position);
        DataSegment nextSegment = pointer.segment;
        focusSegment(position - 1);
        DataSegment segment = pointer.segment;
        if (segment == nextSegment) {
            return false;
        }

        if (segment instanceof FileSegment && nextSegment instanceof FileSegment) {
            if (((FileSegment) segment).getStartPosition() + segment.getLength() == ((FileSegment) nextSegment).getStartPosition()) {
                ((FileSegment) segment).setLength(segment.getLength() + nextSegment.getLength());
                repository.dropSegment(nextSegment);
                segments.remove(nextSegment);
                return true;
            }

            return false;
        }

        if (segment instanceof MemorySegment && nextSegment instanceof MemorySegment) {
            MemorySegment memorySegment = (MemorySegment) segment;
            MemorySegment nextMemorySegment = (MemorySegment) nextSegment;
            if (memorySegment.getSource() == nextMemorySegment.getSource()) {
                if (memorySegment.getStartPosition() + segment.getLength() == nextMemorySegment.getStartPosition()) {
                    memorySegment.setLength(segment.getLength() + nextSegment.getLength());
                    repository.dropSegment(nextSegment);
                    segments.remove(nextSegment);
                    return true;
                }
            }
            // TODO join two single memory segments?
        }

        return false;
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