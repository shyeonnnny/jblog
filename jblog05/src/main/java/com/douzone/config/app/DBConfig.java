package com.douzone.config.app;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:com/douzone/jblog/config/app/jdbc.properties") // src/main/resources 안에 프로퍼티파일 만들어줌 (이 파일로
																			// 통신)
public class DBConfig {

	@Autowired
	private Environment env;// 환경변수 관리하는 bean ( jdbc.properties를 읽어서 저장 ?한다 )
							// 아래 DataSource 부분에서 값 받아와서 사용 가능
	
	
	// <!-- Connection Pool DataSource--> 옮겨주기
	//
	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
//		dataSource.setDriverClassName("org.mariadb.jdbc.Driver");
		dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
//		dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/jblog?characterEncoding=utf8");
		dataSource.setUrl(env.getProperty("jdbc.url"));
		dataSource.setUsername(env.getProperty("jdbc.username"));
		dataSource.setPassword(env.getProperty("jdbc.password"));
		dataSource.setInitialSize(env.getProperty("jdbc.initilSize", Integer.class));
		dataSource.setMaxActive(env.getProperty("jdbc.maxActive", Integer.class));
		// 정수는 Integer로 변경해줘야함 String 값으로 받아오기 때문에

		return dataSource;
	}
}
