<<<<<<< HEAD
package com.mon4cc.utils;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.io.Resources;
public class MybatisUtils {
	
	  private static SqlSessionFactory sqlSessionFactory ;

	    static{
	        try {
	            //每个基于 MyBatis 的应用都是以一个 SqlSessionFactory 的实例为核心的。
	            // SqlSessionFactory 的实例可以通过 SqlSessionFactoryBuilder 获得
	            String resource = "mybatis-config.xml";
	            InputStream inputStream;
	            inputStream = Resources.getResourceAsStream(resource);
	            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    //既然有了 SqlSessionFactory，顾名思义，我们可以从中获得 SqlSession 的实例。
	    public static SqlSession getSqlSession(){
	        return sqlSessionFactory.openSession() ;

	    }
}
=======
package com.mon4cc.utils;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.io.Resources;
public class MybatisUtils {
	
	  private static SqlSessionFactory sqlSessionFactory ;

	    static{
	        try {
	            //每个基于 MyBatis 的应用都是以一个 SqlSessionFactory 的实例为核心的。
	            // SqlSessionFactory 的实例可以通过 SqlSessionFactoryBuilder 获得
	            String resource = "mybatis-config.xml";
	            InputStream inputStream;
	            inputStream = Resources.getResourceAsStream(resource);
	            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    //既然有了 SqlSessionFactory，顾名思义，我们可以从中获得 SqlSession 的实例。
	    public static SqlSession getSqlSession(){
	        return sqlSessionFactory.openSession() ;

	    }
}
>>>>>>> branch 'master' of https://github.com/xuejunY/mon4cc.git
