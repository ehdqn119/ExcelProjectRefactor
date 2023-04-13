package org.example.service;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.config.SqlSessionFactoryManager;
import org.example.dao.MemberDAO;
import org.example.dto.Member;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExcelServiceImpl implements ExcelService {
    private final static Logger LOG = Logger.getGlobal();
    private MemberDAO memberDAO = new MemberDAO(SqlSessionFactoryManager.getSqlSessionFactory());

    /**
     * case 1. 비밀번호가 있는케이스 인지 확인하세요.
     * case 2. 엑셀 창이 열려있다면, 닫아주세요. (다른 프로세스가 사용 중이면 불가능 합니다.)
     *
     * @param path
     */
    @Override
    public void printRows(String path) throws IOException {
        Workbook wb = WorkbookFactory.create(new File("C:\\Users\\user\\Desktop\\testcase01.xlsx"));
        DataFormatter formatter = new DataFormatter();
        Sheet sheet1 = wb.getSheetAt(0);


        for (Row row : sheet1) {
            for (Cell cell : row) {
                CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());
                System.out.print(cellRef.formatAsString());
                System.out.print(" - ");
                // get the text that appears in the cell by getting the cell value and applying any data formats (Date, 0.00, 1.23e9, $1.23, etc)
                String text = formatter.formatCellValue(cell);
                System.out.println(text);
                // Alternatively, get the value and format it yourself
                switch (cell.getCellType()) {
                    case STRING:
                        System.out.println("StringType");
                        System.out.println(cell.getRichStringCellValue().getString());
                        break;
                    case NUMERIC:
                        System.out.println("NUMERIC");
                        if (DateUtil.isCellDateFormatted(cell)) {
                            System.out.println(cell.getDateCellValue());
                        } else {
                            System.out.println(cell.getNumericCellValue());
                        }
                        break;
                    case BOOLEAN:
                        System.out.println("BOOLEAN");
                        System.out.println(cell.getBooleanCellValue());
                        break;
                    case FORMULA:
                        System.out.println("FORMULA");
                        System.out.println(cell.getCellFormula());
                        break;
                    case BLANK:
                        System.out.println("BLANK");
                        System.out.println();
                        break;
                    default:
                        System.out.println();
                }
            }
        }

    }

    @Override
    public void importData() throws IOException {
        LOG.setLevel(Level.INFO);
        LOG.info("import를 진행중입니다.");

        Workbook wb = WorkbookFactory.create(new File("C:\\Users\\user\\Desktop\\testcase01.xlsx"));
        DataFormatter formatter = new DataFormatter();
        Sheet sheet1 = wb.getSheetAt(0);

        for (Row row : sheet1) {
            Member member = new Member();
            for (Cell cell : row) {
                if (row.getRowNum() == 0) {
                    break;
                }
                CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());
                //System.out.print(cellRef.formatAsString());
                //System.out.print(" - ");
                //System.out.println(cell.getColumnIndex());
                // get the text that appears in the cell by getting the cell value and applying any data formats (Date, 0.00, 1.23e9, $1.23, etc)
                String text = formatter.formatCellValue(cell);
                //System.out.println(text);
                // Alternatively, get the value and format it yourself
                switch (cell.getCellType()) {
                    case STRING:
                        // 이름 기반 Search
                        if (cell.getColumnIndex() == 0) {
                            Member searchMember = memberDAO.selectById(cell.getRichStringCellValue().getString());
                            if (searchMember == null) {
                                member.setName(cell.getRichStringCellValue().getString());
                                member.setIsSave(true);
                            } else {
                                member.setName(cell.getRichStringCellValue().getString());
                                member.setIsSave(false);
                            }
                        }
                        // address
                        if (cell.getColumnIndex() == 2) {
                            member.setAddress(cell.getRichStringCellValue().getString());
                        }
                        // gender
                        if (cell.getColumnIndex() == 3) {
                            member.setGender(cell.getRichStringCellValue().getString());
                        }
                        //System.out.println("StringType");
                        //System.out.println(cell.getRichStringCellValue().getString());
                        break;
                    case NUMERIC:
                        if (cell.getColumnIndex() == 1) {
                            if (DateUtil.isCellDateFormatted(cell)) {
                                //System.out.println(cell.getDateCellValue());
                            } else {
                                //System.out.println(cell.getNumericCellValue());
                                member.setAge((int) cell.getNumericCellValue());
                                //System.out.println("Ages is " + member.getAge());
                            }
                        } else if (cell.getColumnIndex() == 4) {
                            if (DateUtil.isCellDateFormatted(cell)) {
                                //System.out.println(cell.getDateCellValue());
                            } else {
                                //System.out.println(cell.getNumericCellValue());
                                member.setHeight((int) cell.getNumericCellValue());
                                //System.err.println(member.getHeight());
                            }
                        }
                        //System.out.println("NUMERIC");
                        break;
                    case BOOLEAN:
                        //System.out.println("BOOLEAN");
                        //System.out.println(cell.getBooleanCellValue());
                        break;
                    case FORMULA:
                        //System.out.println("FORMULA");
                        //System.out.println(cell.getCellFormula());
                        break;
                    case BLANK:
                        //System.out.println("BLANK");
                        //System.out.println();
                        break;
                    default:
                        //System.out.println();
                }
            }
            if (row.getRowNum() != 0) {
                // 여기서 dao 호출해서 save
                if (member.getIsSave() == true) {
                    memberDAO.insert(member);
                } else {
                    memberDAO.update(member);
                }
            }

        }

    }

    @Override
    public Member getRows() {
        return null;
    }

    @Override
    public void deleteRows() {

    }

    public void exportData() throws IOException {
        LOG.setLevel(Level.INFO);
        LOG.info("export 를 진행중입니다.");

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Members");
        int rowNo = 0;

        // Cell Style Define
        CellStyle cellStyle = workbook.createCellStyle();
        CellStyle headStyle = workbook.createCellStyle();
        headStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_BLUE.getIndex());
        headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Font font = workbook.createFont();
        font.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
        font.setFontHeightInPoints((short) 13);
        headStyle.setFont(font);


        Row header = sheet.createRow(rowNo++);
        header.createCell(0).setCellValue("id");
        header.createCell(1).setCellValue("name");
        header.createCell(2).setCellValue("age");
        header.createCell(3).setCellValue("address");
        header.createCell(4).setCellValue("gender");
        header.createCell(5).setCellValue("height");
        for (int i = 0; i <= 5; i++) {
            header.getCell(i).setCellStyle(headStyle);
        }

        List<Member> memberList = memberDAO.selectAll();
        for (Member member : memberList) {
            Row row = sheet.createRow(rowNo++);
            row.createCell(0).setCellValue(member.getId());
            row.createCell(1).setCellValue(member.getName());
            row.createCell(2).setCellValue(member.getAge());
            row.createCell(3).setCellValue(member.getAddress());
            row.createCell(4).setCellValue(member.getGender());
            row.createCell(5).setCellValue(member.getHeight());
            System.out.println(member.toString());
        }

        sheet.setColumnWidth(0, 3000);
        sheet.setColumnWidth(1, 3000);
        sheet.setColumnWidth(2, 4000);
        sheet.setColumnWidth(3, 8000);
        sheet.setColumnWidth(4, 8000);
        sheet.setColumnWidth(5, 8000);

        //파일 생성
        File file = new File("C:\\Users\\user\\Desktop\\result.xlsx"); // 파일 확장자 .xlsx로 고정
        FileOutputStream fos = new FileOutputStream(file);

        // 파일 쓰기
        workbook.write(fos);
        if (fos != null) {
            fos.close();
        }
        LOG.info(file.getAbsolutePath() + " 에 생성되었습니다.");
    }


}
