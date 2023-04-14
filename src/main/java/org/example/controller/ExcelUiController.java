package org.example.controller;

import org.example.service.ExcelService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class ExcelUiController {
    public ExcelUiController(ExcelService excelService) {
        this.ex = excelService;
    }

    private final String numberMsg = "1.DB에 Excel 데이터 저장하기 2.DB데이터 Excel로 내보내기 3. 종료하기";
    private final String typeMismatchMsg = "잘못된 타입을 입력했습니다. 다시 입력해주세요.";

    private final String exitMsg = "정상적으로 종료되었습니다.";
    private final ExcelService ex;

    public void showMenu() {
        Scanner sc = new Scanner(System.in);
        boolean isNotExit = true;
        int number = 0;
        do {
            try {
                System.out.printf(numberMsg);
                System.out.println("");
                while (!sc.hasNextInt()) {
                    System.out.println(typeMismatchMsg);
                    System.out.println(numberMsg);
                    sc.next();
                }
                number = sc.nextInt();
                if (number == 1) {
                    sc.nextLine();
                    System.out.println("DB에 Excel 데이터를 저장합니다.");
                    System.out.println("드라이브 이름 부터 전체 경로를 입력해주세요. ex) C:\\Users\\user\\Desktop\\testcase01.xlsx");
                    String path = sc.nextLine();
                    ex.importData(path);
                } else if (number == 2) {
                    sc.nextLine();
                    System.out.println("DB 데이터를 Excel 로 내보내기 위한 작업입니다.");
                    System.out.println("생성될 Excel 의 경로를 입력해주세요. ex) C:\\Users\\user\\Desktop ");
                    String resultPath = sc.nextLine();
                    ex.exportData(resultPath);
                } else if (number == 3) {
                    System.out.println(exitMsg);
                    isNotExit = false;
                    break;
                }

            } catch (FileNotFoundException fe) {
                System.out.println(fe.getMessage());
            } catch (IOException e) {

            }

        } while (isNotExit);

    }
}
