package org.example.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class SqlSessionFactoryManager {
    private final static Logger LOG = Logger.getGlobal();
    private static SqlSessionFactory sqlSessionFactory;

    static {

        try {
            LOG.setLevel(Level.INFO);
            LOG.info("PostGresql 을 연결중입니다. ");

            String resource = "Config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);

            if (sqlSessionFactory == null) {
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
                LOG.info("Session Factory 생성 완료" + sqlSessionFactory.toString());
            }
        }
        catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
            catch (IOException iOException) {
                iOException.printStackTrace();
            }
        }

    public static SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }
}
