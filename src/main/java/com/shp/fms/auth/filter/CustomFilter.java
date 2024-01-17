//package com.shp.fms.auth.filter;
//
//import java.io.IOException;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import com.shp.fms.auth.jwt.JwtProperties;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//public class CustomFilter implements Filter {
//
//	@Override
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//			throws IOException, ServletException {
//		log.info("[CustomFilter] 실행");
//		HttpServletRequest req = (HttpServletRequest) request;
//		HttpServletResponse res = (HttpServletResponse) response;
//		
//		if(req.getMethod().equals("POST")) {
//			String headerAuth = req.getHeader(JwtProperties.HEADER_STRING);
//			log.info("CustomFilter doFilter headerAuth={}", headerAuth);
//			
//			if(JwtProperties.SECRET.equals(headerAuth)) {
//				log.info("secret key equals");
//			} else {
//				log.error("CustomFilter doFilter Authentication FAIL. Secret key not match.");
//			}
//		}
//		
//		log.info("[CustomFilter] 종료");
//		chain.doFilter(req, res);
//	}
//
//}
