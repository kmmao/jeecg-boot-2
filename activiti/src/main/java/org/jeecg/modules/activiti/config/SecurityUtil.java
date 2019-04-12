package org.jeecg.modules.activiti.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {

    private Logger logger = LoggerFactory.getLogger(SecurityUtil.class);



    public void logInAs(String username) {

        org.activiti.engine.impl.identity.Authentication.setAuthenticatedUserId(username);
    }
}