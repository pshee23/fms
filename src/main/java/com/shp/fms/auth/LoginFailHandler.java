//package com.shp.fms.auth;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.http.MediaType;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.AuthenticationFailureHandler;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//public class LoginFailHandler implements AuthenticationFailureHandler {
//
//	@Override
//	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
//			AuthenticationException exception) throws IOException, ServletException {
//	  try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            Map<String, Object> responseMap = new HashMap<>();
//            String message = AuthenticationType.getExceptionMessage(exception.getClass().getSimpleName());
//            responseMap.put("status", 401);
//            responseMap.put("error", message);
//
//            String json = objectMapper.writeValueAsString(responseMap);
//            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//            response.getWriter().write(json);
//            response.getWriter().flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//	}
//}
