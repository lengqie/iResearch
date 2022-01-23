package com.iresearch.config;

import com.iresearch.shiro.realm.UserRealm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    /**
     * Realm
     * SecurityManager
     * ShiroFilterFactoryBean
     * RedisSessionManager
     */

    @Autowired(required = false)
    RedisSessionDAO redisSessionDAO;

    @Resource
    RedisCacheManager redisCacheManager;

    @Bean
    public UserRealm userRealm(){
        return new UserRealm();
    }

    @Bean
    public DefaultWebSecurityManager securityManager(SessionManager sessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm());

        // inject sessionManager
        // securityManager.setSessionManager(sessionManager);
        // // inject redisCacheManager
        // securityManager.setCacheManager(redisCacheManager);
        return securityManager;
    }

    @Bean(name = "shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager) {

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setUnauthorizedUrl("/401");

        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 可以访问的url
        //swagger
        filterChainDefinitionMap.put("/swagger-ui/**","anon");
        filterChainDefinitionMap.put("/swagger-resources/**","anon");
        filterChainDefinitionMap.put("/v3/api-docs/**","anon");
        //druid
        filterChainDefinitionMap.put("/druid/**","anon");

        // 需要权限的url
        filterChainDefinitionMap.put("/admin/**", "authc");

        filterChainDefinitionMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;

    }

    @Bean
    public SessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();

        RedisManager redisManager = new RedisManager();
        redisManager.setTimeout(100);

        redisSessionDAO.setRedisManager(redisManager);
        redisSessionDAO.setExpire(7200);

        sessionManager.setSessionDAO(redisSessionDAO);
        return sessionManager;
    }
}
