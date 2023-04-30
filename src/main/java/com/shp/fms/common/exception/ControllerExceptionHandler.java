package com.shp.fms.common.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.shp.fms.controller.response.CommonResponse;

@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(NoDataReturnedException.class)
	public ResponseEntity<CommonResponse> handleNoDataException(HttpServletRequest request, NoDataReturnedException ex) {
		String message = String.format("No Data is Returned when select from %s.", ex.getTarget());
		return ResponseEntity.ok(new CommonResponse().failExceptionResponse(message));
	}
	
	@ExceptionHandler(NoResultByIdException.class)
	public ResponseEntity<CommonResponse> handleNoResultByIdException(HttpServletRequest request, NoResultByIdException ex) {
		String message = String.format("No Data Available from %s by %s_Id = %d", ex.getTarget(), ex.getIdName(), ex.getId());
		return ResponseEntity.ok(new CommonResponse().failExceptionResponse(message));
	}
}
