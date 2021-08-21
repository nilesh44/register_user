package com.ace.registeruser.config;

import com.ace.registeruser.entity.UserSession;
import com.ace.registeruser.filter.SessionFilter;
import com.ace.registeruser.repository.UserSessionRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Configuration
@ComponentScan
public class Config {

    @Value("${applicationConstant.sessionTimeout}")
    private long sessionTimeOut;

    @Bean
    public FilterRegistrationBean<SessionFilter> userSessionFilter(UserSessionRepository sessionRepository, UserSession userinfoSession) {

        FilterRegistrationBean<SessionFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new SessionFilter(sessionRepository,userinfoSession,sessionTimeOut));
        registrationBean.setUrlPatterns(getUrlPattern());
        return registrationBean;
    }

    private Collection<String> getUrlPattern(){
        List<String> urlPattern= new ArrayList<String>();
        urlPattern.add("/user/update/*");
        urlPattern.add("/user/expire/*");
        urlPattern.add("/user/fetch/*");
        urlPattern.add("/user/phone/*");
        return urlPattern;
    }
}
