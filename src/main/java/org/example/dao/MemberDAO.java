package org.example.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.example.dto.Member;

import java.util.List;
import java.util.Optional;

public class MemberDAO {

    private SqlSessionFactory  sqlSessionFactory = null;

    public MemberDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }
    public List<Member> selectAll(){
        List<Member> list = null;
        SqlSession session = sqlSessionFactory.openSession();

        try {
            list = session.selectList("MemberMapper.selectAll");
        } finally {
            session.close();
        }

        return list;
    }

    public Member selectById(String name){
        Member member = null;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            member = session.selectOne("MemberMapper.selectByName", name);
        } finally {
            session.close();
        }
        return member;
    }

    public void insert(Member member){
        SqlSession session = sqlSessionFactory.openSession();

        try {
            session.insert("MemberMapper.insert", member);
        } finally {
            session.commit();
            session.close();
        }
        System.out.println("Insert 성공!~");
    }

    public void update(Member member){

        SqlSession session = sqlSessionFactory.openSession();
        System.out.println(member.toString());
        try {
            session.update("MemberMapper.update", member);
        } finally {
            session.commit();
            session.close();
        }
        System.out.println("update 성공!~");
    }


}
