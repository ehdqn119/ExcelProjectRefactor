package org.example.service;

import org.example.dto.Member;

import java.io.IOException;

public interface ExcelService {

    public void printRows(String path) throws IOException;

    public void importData(String path) throws IOException;

    public void exportData(String resultPath) throws IOException;

    public Member getRows();

    public void deleteRows();

}
