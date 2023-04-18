package org.example.mapper;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.example.dto.Member;

public class MemberMapper implements ExcelMapper<Member> {
    private static final ExcelInterface<Member>[] MEMBER_INFORMATION = MemberInformation.values();
    //멤버 인포 -> 모든 컬럼대한 정보[] -> 하나의 로우

    @Override
    public Member toObject(Row row) {
        DataFormatter formatter = new DataFormatter();
        Member mem = new Member();
        for (ExcelInterface<Member> memberExcelInterface : MEMBER_INFORMATION) {
            Cell cell = row.getCell(memberExcelInterface.getCellIndex());
            formatter.formatCellValue(cell);
            memberExcelInterface.getConsumer().accept(mem, cell);
        }
        return mem;
    }

    @Override
    public void toRow(Row row, Member obj) {
        for (ExcelInterface<Member> memberExcelInterface : MEMBER_INFORMATION) {
            Cell cell = row.createCell(memberExcelInterface.getCellIndex());
            System.out.println("DDDD" + obj.toString());
            memberExcelInterface.getFunction().accept(obj,cell);
        }

    }
    @Override
    public void toHeader(Row row){
        for (ExcelInterface<Member> memberExcelInterface : MEMBER_INFORMATION) {
            Cell cell = row.createCell(memberExcelInterface.getCellIndex());
            cell.setCellValue(memberExcelInterface.getHeader());
        }
    }


}
