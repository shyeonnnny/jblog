package com.douzone.config.app;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBatisConfig {

	// <!-- MyBatis SqlSessionFactoryBean --> 옮기기
	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource, ApplicationContext applicationContext)
			throws Exception {
		SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
		sqlSessionFactory.setDataSource(dataSource);
		sqlSessionFactory.setConfigLocation(
				applicationContext.getResource("classpath:com/douzone/jblog/config/app/mybatis/configuration.xml"));
				// configuratio.xml 파일의 경로 전부 수정해줘야함

		return sqlSessionFactory.getObject();
	}

	// <!-- MyBatis SqlSessionTemplate --> 부분 수정
	@Bean
	public SqlSession sqlSession(SqlSessionFactory sqlSessionFactory) {

		return new SqlSessionTemplate(sqlSessionFactory); // 이름
	}
}
