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
import org.example.service.ExcelServiceRefactor;

import java.io.*;
import java.util.List;
import java.util.Scanner;


public class App {

    public static void main(String[] args) {

        ExcelUiController excelUiController = new ExcelUiController(new ExcelServiceRefactor());
        excelUiController.showMenu();

        // ExcelUiController excelUiController = new ExcelUiController(new ExcelServiceImpl());
        // excelUiController.showMenu();
    }
}
