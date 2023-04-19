package org.example.mapper;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.example.dto.Member;

import java.util.function.BiConsumer;
import java.util.function.Function;

public enum MemberInformation implements ExcelInterface<Member> { //단일
    NAME(CellType.STRING,"name",0,(member, cell) -> member.setName( cell.getStringCellValue()),
            (member,cell) -> cell.setCellValue(member.getName())
            ),
    AGE(CellType.NUMERIC,"age",1,(member, cell) -> member.setAge((int) cell.getNumericCellValue()),
        (member,cell) -> cell.setCellValue(member.getAge())
    ),
    ADDRESS(CellType.STRING,"address",2,(member,cell) -> member.setAddress(cell.getStringCellValue()),
            (member,cell) -> cell.setCellValue(member.getAddress())
            ),
    GENDER(CellType.STRING,"gender",3,(member, cell) -> member.setGender(cell.getStringCellValue()),
            (member,cell) -> cell.setCellValue(member.getGender())
            ),
    HEIGHT(CellType.NUMERIC,"height",4,(member, cell) -> member.setHeight((int) cell.getNumericCellValue()),
            (member,cell) -> cell.setCellValue(member.getHeight())
            ),
    SOURCEIP(CellType.STRING,"sourceIp",5,(member, cell) -> member.setSourceIp(cell.getStringCellValue()),
            (member,cell) -> cell.setCellValue(member.getSourceIp())),
    DESIP(CellType.STRING,"desIp",6,(member, cell) -> member.setDesIp(cell.getStringCellValue()),
            (member,cell) -> cell.setCellValue(member.getDesIp())),
    PORTNUMBER(CellType.STRING,"portNumber",7,(member, cell) -> member.setPortNumber(cell.getStringCellValue()),
            (member, cell) -> cell.setCellValue(member.getPortNumber()));
    private CellType cellType;
    private String header;
    private int index;
    private BiConsumer<Member,Cell> biConsumer;

    private BiConsumer<Member,Cell> func;

    MemberInformation(CellType cellType, String header, int index, BiConsumer<Member,Cell> biConsumer,
                      BiConsumer<Member,Cell> func
    ) {
        this.cellType = cellType;
        this.header = header;
        this.index = index;
        this.biConsumer = biConsumer;
        this.func = func;
    }

    boolean typeCheck(CellType cellType)  {
        return this.getCellType()  == cellType;
    }

    @Override
    public String getHeader() {
        return this.header;
    }

    @Override
    public int getCellIndex() {
        return this.index;
    }

    @Override
    public CellType getCellType() {
        return this.cellType;
    }

    @Override
    public  BiConsumer<Member, Cell> getFunction() {
        return this.func;
    }

    @Override
    public BiConsumer<Member, Cell> getConsumer() {
        return this.biConsumer;
    }
}
