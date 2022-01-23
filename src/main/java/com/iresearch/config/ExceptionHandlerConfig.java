package com.iresearch.config;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author lengqie
 */
@ControllerAdvice
@RestController
public class ExceptionHandlerConfig {

        /**
         * AuthenticationException Shiro认证异常
         */
        @ExceptionHandler(value = {AuthenticationException.class, AuthorizationException.class})
        public String authenticationExceptionHandler(Exception e, HttpServletResponse response){
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return "👻 权限异常！";
        }

        @ExceptionHandler(value = Exception.class)
        public String exceptionHandler(Exception e, HttpServletResponse response){
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return "👻 系统异常！";
        }
}
