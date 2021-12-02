package com.douzone.jblog.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.douzone.jblog.config.web.FileUploadConfig;
import com.douzone.jblog.config.web.MessageConfig;
import com.douzone.jblog.config.web.MvcConfig;
import com.douzone.jblog.config.web.SecurityConfig;

@Configuration
@EnableAspectJAutoProxy //<!-- auto proxy -->
@ComponentScan({"com.douzone.jblog.controller", "com.douzone.jblog.exception"}) //<context:annotation-config /> 삭제
@Import({MvcConfig.class, MessageConfig.class, FileUploadConfig.class, SecurityConfig.class}) // 순서가상관있나없나 봐야함
public class WebConfig extends WebMvcConfigurerAdapter {
	// extends나 Configuration 꼭 달아줘야 실행됨 ( 둘중뭔지는 확인해봐야함 )
	
	
}
