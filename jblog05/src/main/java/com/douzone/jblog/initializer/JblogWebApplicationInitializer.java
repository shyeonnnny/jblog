package com.douzone.jblog.initializer;

import javax.servlet.Filter;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.douzone.jblog.config.AppConfig;
import com.douzone.jblog.config.WebConfig;

public class JblogWebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	
	/*// Appconfig
	 *  <context-param>
      <param-name>contextClass</param-name>
      <param-value>org.springframework.web.context.support.AnnotationConfigWebApplicationContext</param-value>
   </context-param>
   
   <context-param>
      <param-name>contextConfigLocation</param-name>
      <param-value>com.douzone.jblog.config.AppConfig</param-value>
      <!-- <aop:aspectj-autoproxy />를 없애주고 위에코드를 변경해줌 -->
   </context-param
	 * 
	 * */
	@Override
	protected Class<?>[] getRootConfigClasses() {
		
		return new Class<?>[] {AppConfig.class};
	}

	/*// 얘가 WebConfig입니다
	 *   <servlet>
    <servlet-name>spring</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet
		</servlet-class>
		<!-- init-param은 springservlet지우고 추가해줘야함 -->
		<init-param>
			<param-name>contextClass</param-name>
			<param-value>org.springframework.web.context.support.AnnotationConfigWebApplicationContext</param-value>
		</init-param>
		<init-param>
			<param-name>contextConfigLocation</param-name>
			<param-value>com.douzone.jblog.config.WebConfig</param-value>
		</init-param>
		<!-- 여기까지  -->
  </servlet>
	 * 
	 * 
	 * */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		// TODO Auto-generated method stub
		return new Class<?>[] {WebConfig.class};
	}
	
	/*
	 *   <servlet-mapping>
    <servlet-name>spring</servlet-name>
    <url-pattern>/</url-pattern>
  </servlet-mapping>
	 * 
	 * */
	@Override
	protected String[] getServletMappings() {
		// TODO Auto-generated method stub
		return new String[] {"/"};
	}
	
	
	// web.xml : <!-- Encoding Filter -->
	@Override
	protected Filter[] getServletFilters() {
		return new Filter[] {new CharacterEncodingFilter("UTF-8", false)};
	}
	
	@Override
	protected void customizeRegistration(Dynamic registration) {
		registration.setInitParameter("throwExceptionIfNoHandlerFound", "true");
	}
	
	
	// 리스너는 스프링에 있어서 따로 안적어주ㅓ도됨
	/*
	 * 
	 *   <listener>
    <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
  </listener>
	 * 
	 * */
	
}
