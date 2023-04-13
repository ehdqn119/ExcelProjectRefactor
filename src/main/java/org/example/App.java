package org.example;


import lombok.extern.java.Log;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.example.config.SqlSessionFactoryManager;
import org.example.dao.MemberDAO;
import org.example.dto.Member;
import org.example.service.ExcelService;
import org.example.service.ExcelServiceImpl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


// TODO Refactor: 23 DB 연결,  연결이 안되면 오류 완료되면 Message 출력
// TODO Refactor: 24 클래스로  따로 빼기, Error  Response 만들기

public class App {

    public static void main( String[] args ) throws IOException {


        MemberDAO memberDAO = new MemberDAO(SqlSessionFactoryManager.getSqlSessionFactory());

//        Member newMember = new Member();
//        newMember.setName("Lee Sang Hyup");
//        newMember.setAge((30));
//        newMember.setAddress(("대구광역시 동구 큰고개로35-2"));
//        newMember.setHeight((175));
//        newMember.setGender("Man");
//        memberDAO.insert(newMember);
//
//
//        List<Member> memberList = memberDAO.selectAll();
//        for(Member member : memberList){
//            System.out.println("Member ID: "+  member.getId());
//            System.out.println("Member Age: "+  member.getName());
//            System.out.println("Member Address: "+  member.getAge());
//            System.out.println("Member Height: "+  member.getAddress());
//            System.out.println("Member getGender: "+  member.getGender());
//            System.out.println("Member getHeight: "+  member.getHeight());
//            System.out.println("End");
//        }
        ExcelServiceImpl ex = new ExcelServiceImpl();

        System.out.println("==POI LINE==");

        ex.importData();
        ex.exportData();
    }
}
