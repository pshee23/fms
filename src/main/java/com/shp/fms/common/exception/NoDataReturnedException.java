package com.shp.fms.common.exception;

import lombok.Getter;

@Getter
public class NoDataReturnedException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private String idName;
	private long id;
	private String target;
	
	// XXX 다른 서비스에서도 공통을 받아서 하나의 Exception으로 쓸수도 있지만.. 이 Exception을 상속받아서 서비스 마다 다른 Exception으로 처리 받는것도 권장
	// 혹은 데이터가 없을 경우 어떤 동작을 할지에 따라서 상속 받아서 따로 처리할수도 있겟다
	public NoDataReturnedException(String target) {
		super(target);
	}
	
	public NoDataReturnedException(String idName, long id, String target) {
		this.idName = idName;
		this.id = id;
		this.target = target;
	}
}
