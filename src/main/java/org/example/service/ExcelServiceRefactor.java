package org.example.service;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.config.SqlSessionFactoryManager;
import org.example.dao.MemberDAO;
import org.example.dto.Member;
import org.example.exception.DuplicatePolicyException;
import org.example.exception.IpduplicateException;
import org.example.mapper.ExcelMapper;
import org.example.mapper.MemberMapper;
import org.example.validation.RegexUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ExcelServiceRefactor implements ExcelService {
    private final static Logger LOG = Logger.getGlobal();
    private MemberDAO memberDAO = new MemberDAO(SqlSessionFactoryManager.getSqlSessionFactory());
    ExcelMapper<Member> excelMapper = new MemberMapper();
    @Override
    public void importData(String path) throws IOException {
        LOG.setLevel(Level.INFO);
        LOG.info("설정경로 " + path);
        LOG.info("import 시작.");
        LOG.info("import 완료가 뜨지 않았다면,경로 설정을 다시 확인해주세요.");

        Workbook wb = WorkbookFactory.create(new File(path));
        Sheet sheet1 = wb.getSheetAt(0);
        List<Member> list = new ArrayList<>();

        for (int i = 0; i <=sheet1.getLastRowNum(); i++) {
            if(i  == 0) {
                continue;
            }
            Row row = sheet1.getRow(i);
            Member cellToMember = excelMapper.toObject(row);
            list.add(cellToMember);
        }

        // save || update
        for (Member member : list) {
            // Validation
            RegexUtil.checkIp(member.getSourceIp());
            RegexUtil.checkIp(member.getDesIp());
            RegexUtil.checkPortNumber(String.valueOf(member.getPortNumber()));

            // 정책 Validation
            if(member.getSourceIp() == member.getDesIp()) {
                throw new IpduplicateException("출발지와 목적지 Ip 는 같을 수 없습니다.");
            }

            // 기존 정책 확인 Validation
            boolean duplicate = memberDAO.duplicatePolicy(member);
            if(duplicate) {
                throw new DuplicatePolicyException("이미 해당 정책이 있습니다.");
            }
            else {
                member.setIsSave(memberDAO.selectById(member.getName()) == null);
                if (member.getIsSave()) {
                    memberDAO.insert(member);
                } else {
                    memberDAO.update(member);
                }
            }
        }
        LOG.info("import를 완료하였습니다.");
    }

    @Override
    public Member getRows() {
        return null;
    }
    @Override
    public void deleteRows() {

    }

    public void exportData(String resultPath) throws IOException {
        LOG.info("설정경로" + resultPath);
        LOG.setLevel(Level.INFO);
        LOG.info("export 를 진행중입니다.");

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Members");

        // Cell Style Define
        CellStyle cellStyle = workbook.createCellStyle();
        CellStyle headStyle = workbook.createCellStyle();

        headStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_BLUE.getIndex());
        headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Font font = workbook.createFont();
        font.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
        font.setFontHeightInPoints((short) 13);
        headStyle.setFont(font);



        Row header = sheet.createRow(0);
        excelMapper.toHeader(header);


        sheet.setColumnWidth(0, 3000);
        sheet.setColumnWidth(1, 3000);
        sheet.setColumnWidth(2, 4000);
        sheet.setColumnWidth(3, 8000);
        sheet.setColumnWidth(4, 8000);
        sheet.setColumnWidth(5, 8000);

        List<Member> memberList = memberDAO.selectAll();

        for (int i = 0; i <= 8; i++) {
            header.getCell(i).setCellStyle(headStyle);
        }

        for (int i = 0; i < memberList.size(); i++) {
            header.getCell(i).setCellStyle(headStyle);
            Row row = sheet.createRow(i+1);
            excelMapper.toRow(row, memberList.get(i));
        }

        File file = new File(resultPath + "\\result.xlsx"); // 파일 확장자 .xlsx로 고정
        FileOutputStream fos = new FileOutputStream(file);

        workbook.write(fos);
        if (fos != null) {
            fos.close();
        }
        LOG.info(file.getAbsolutePath() + " 에 생성되었습니다.");
    }


    @Override
    public void printRows(String path) {

    }


}
