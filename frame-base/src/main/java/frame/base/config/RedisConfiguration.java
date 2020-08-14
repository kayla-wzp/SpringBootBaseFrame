package frame.base.config;

import frame.base.service.cache.CacheService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.Resource;


@Configuration
public class RedisConfiguration {

	@Resource
	private CacheService cacheService;

	@Bean
	public JedisPoolConfig jedisPoolConfig() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxTotal(getCacheParam("REDIS_MAX_TOTAL"));
		jedisPoolConfig.setMaxIdle(getCacheParam("REDIS_MAX_IDLE"));
		jedisPoolConfig.setMaxWaitMillis(getCacheParam("REDIS_MAX_WAIT_MILLIS"));
		return jedisPoolConfig;
	}

	private Integer getCacheParam(String parameterIdStr) {
		return Integer.valueOf(cacheService.getParamValueAvail(parameterIdStr));
	}

	@Bean
	public JedisConnectionFactory jedisConnectionFactory(JedisPoolConfig jedisPoolConfig) {
		JedisConnectionFactory factory = new JedisConnectionFactory(jedisPoolConfig);
		RedisStandaloneConfiguration redisStandaloneConfiguration = factory.getStandaloneConfiguration();
		if (redisStandaloneConfiguration != null) {
			String redisIp = cacheService.getParamValueAvail("REDIS_IP");
			redisStandaloneConfiguration.setHostName(redisIp);
			int redisPort = Integer.parseInt(cacheService.getParamValueAvail("REDIS_PORT"));
			redisStandaloneConfiguration.setPort(redisPort);
			redisStandaloneConfiguration.setDatabase(0);
		}
		return factory;
	}
}
