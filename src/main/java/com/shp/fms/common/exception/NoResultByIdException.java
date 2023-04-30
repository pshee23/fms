package com.shp.fms.common.exception;

import lombok.Getter;

@Getter
public class NoResultByIdException extends NoDataReturnedException {
	private static final long serialVersionUID = 1L;
	
	public NoResultByIdException(long id, String target) {
		super(target, id, target);
	}
	
	public NoResultByIdException(String idName, long id, String target) {
		super(idName, id, target);
	}
}
