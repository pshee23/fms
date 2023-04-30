package com.shp.fms.common.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ServiceType {
	BRANCH("Branch"),
	EMPLOYEE("Employee"),
	LESSON("Lesson"),
	LESSON_HISTORY("LessonHistory"),
	LOCKER("Locker"),
	MEMBER("Member"),
	MEMBERSHIP("MemberShip"),
	PAY_HISTORY("PayHistory"),
	PRODUCT("Product"),
	PURCHASE_HISTORY("PurchaseHistory");
	
	private final String name;
}
