package org.example.mapper;

import org.apache.poi.ss.usermodel.CellType;

import java.util.function.BiConsumer;
import java.util.function.Function;

public interface ExcelInterface<T> {
    String getHeader();
    int getCellIndex();
    CellType getCellType();
    <U> BiConsumer<T,U> getConsumer();
    <U> BiConsumer<T,U> getFunction();

}
