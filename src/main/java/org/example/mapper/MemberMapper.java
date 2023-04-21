package org.example.mapper;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.example.dto.Member;

public class MemberMapper implements ExcelMapper<Member> {
    private static final ExcelInterface<Member>[] MEMBER_INFORMATION = MemberInformation.values();

    @Override
    public Member toObject(Row row) {
        DataFormatter formatter = new DataFormatter();
        Member mem = new Member();
        for (ExcelInterface<Member> memberExcelInterface : MEMBER_INFORMATION) {
            Cell cell = row.getCell(memberExcelInterface.getCellIndex());
            //  formatter.formatCellValue(cell);
            boolean isNotNull = cellTypeVerify(cell);
            if(isNotNull) {
                memberExcelInterface.getConsumer().accept(mem, cell);
            }
            else  {
                System.out.println(row.getRowNum() + "번 셀이 빈 셀 입니다. 확인해주세요.");
            }
        }
        return mem;
    }

    @Override
    public void toRow(Row row, Member obj) {
        for (ExcelInterface<Member> memberExcelInterface : MEMBER_INFORMATION) {
            Cell cell = row.createCell(memberExcelInterface.getCellIndex());
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

    /**
     * 빈 셀을 검증하기 위한 메소드 입니다.
     * @return
     */
    @Override
    public boolean cellTypeVerify(Cell cell) {
        return (cell == null) ? false : true;
    }


}
