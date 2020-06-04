package com.javainuse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

//@EnableRedisHttpSession 
@Configuration
public class Config {

	@Value("${spring.redis.host}")
	 private String host;
	 @Value("${spring.redis.port}")
	 private Integer port;

	  @Bean
	  public LettuceConnectionFactory redisConnectionFactory() {
	
	    return new LettuceConnectionFactory(new RedisStandaloneConfiguration(this.host, this.port));
	  }

}