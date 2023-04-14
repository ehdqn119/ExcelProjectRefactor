package org.example;


import lombok.extern.java.Log;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.example.config.SqlSessionFactoryManager;
import org.example.controller.ExcelUiController;
import org.example.controller.UiController;
import org.example.dao.MemberDAO;
import org.example.dto.Member;
import org.example.exception.FileNameNotFoundException;
import org.example.service.ExcelService;
import org.example.service.ExcelServiceImpl;

import java.io.*;
import java.util.List;
import java.util.Scanner;


// TODO Refactor: 23 DB 연결,  연결이 안되면 오류 완료되면 Message 출력
// TODO Refactor: 24 클래스로  따로 빼기, Error  Response 만들기

public class App {

    public static void main(String[] args) throws IOException {

        ExcelUiController excelUiController = new ExcelUiController(new ExcelServiceImpl());
        excelUiController.showMenu();
    }
}
