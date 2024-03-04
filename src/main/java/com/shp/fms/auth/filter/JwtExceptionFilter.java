package com.shp.fms.auth.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shp.fms.common.exception.AuthenticationException;
import com.shp.fms.model.response.CommonResponse;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtExceptionFilter extends OncePerRequestFilter {
	
	private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    	log.info("[JwtExceptionFilter] doFilterInternal start");
        response.setCharacterEncoding("utf-8");

        try{
            filterChain.doFilter(request, response);
        } catch (JwtException e){
           String message = e.getMessage();
           log.error("JWTException!!", e);
           setResponse(response, 401900, message);
        } catch (AuthenticationException e) {
        	String message = e.getMessage();
        	setResponse(response, e.getCode(), message);
            log.error("AuthenticationException!!", e);
        } catch (Exception e) {
        	String message = e.getMessage();
            setResponse(response, 401999, message);
            log.error("Exception!!", e);
        }
        
        log.info("[JwtExceptionFilter] doFilterInternal end");
    }
    
    private void setResponse(HttpServletResponse response, int errorCode, String errorMessage) throws RuntimeException, IOException {
    	CommonResponse commRes = new CommonResponse();
    	commRes = commRes.failResponse(errorCode, errorMessage);
    	String result = objectMapper.writeValueAsString(commRes);
    	log.info("############ {}", result);
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(401);
        response.getWriter().print(result);
    }
}