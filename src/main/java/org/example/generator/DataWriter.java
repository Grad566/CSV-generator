package org.example.generator;

import org.example.exception.DataWriterException;

import java.util.List;

public interface DataWriter {
    <T> void writeDataToFile(T[] data, String path) throws DataWriterException;
    <T> void writeDataToFile(List<T> data, String path) throws DataWriterException;
}
