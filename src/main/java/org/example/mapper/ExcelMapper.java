package org.example.mapper;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

public interface ExcelMapper<T> {

    T toObject(Row row);
    void toRow(Row row, T obj);
    void toHeader(Row row);

    boolean cellTypeVerify(Cell cell);
}
