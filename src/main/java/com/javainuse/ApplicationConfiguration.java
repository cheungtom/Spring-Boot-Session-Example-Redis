package com.javainuse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.session.data.redis.config.ConfigureRedisAction;

@Configuration
public class ApplicationConfiguration {

 private Log log = LogFactory.getLog(getClass());

 /*
 	@Bean
	//@LoadBalanced
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
*/
 @Bean
 public static ConfigureRedisAction configureRedisAction() {
     return ConfigureRedisAction.NO_OP;
 }
 
 @Bean
 static PropertySourcesPlaceholderConfigurer pspc() {
  return new PropertySourcesPlaceholderConfigurer();
 }

 // <3>
 @Bean
 InitializingBean which(Environment e) {
   return () -> {
   log.info("ApplicationConfiguration Loaded");
  };
 }
}

