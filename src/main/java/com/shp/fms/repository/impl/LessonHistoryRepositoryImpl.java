package com.shp.fms.repository.impl;

import static com.shp.fms.model.entity.QLessonHistory.lessonHistory;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shp.fms.model.entity.LessonHistory;
import com.shp.fms.repository.DslLessonHistoryRepository;

@Repository
public class LessonHistoryRepositoryImpl implements DslLessonHistoryRepository {
	
	private final JPAQueryFactory jpaQueryFactory;
	
	public LessonHistoryRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

	@Override
	public List<LessonHistory> findFilteredOrderedSearchResults(long employeeId, int orderByTime, int offset, int limit, LocalDate startDate, LocalDate endDate) {
		return jpaQueryFactory.selectFrom(lessonHistory)
				.where(lessonHistory.employee.memberId.eq(employeeId))
				.where(
						overStartDate(startDate, endDate),
						belowEndDate(startDate, endDate)
				)
				.orderBy(orderByTime(orderByTime))
				.offset(offset)
				.limit(limit)
				.fetch();
	}
	
	private BooleanExpression overStartDate(LocalDate startDate, LocalDate endDate) {
		if(startDate == null || endDate == null) {
			return null;
		}
		return lessonHistory.startDateTime.goe(startDate.atStartOfDay());
	}

	private BooleanExpression belowEndDate(LocalDate startDate, LocalDate endDate) {
		if(startDate == null || endDate == null) {
			return null;
		}
		return lessonHistory.endDateTime.loe(endDate.plusDays(1).atStartOfDay().minusNanos(1));
	}
	
	private OrderSpecifier<?> orderByTime(int orderByTime) {
		if(orderByTime == 0) {
			return lessonHistory.lessonHistoryId.asc();
		} else if(orderByTime == -1) {
			return lessonHistory.startDateTime.desc();
		} else if(orderByTime == 1) {
			return lessonHistory.startDateTime.asc();
		}
		return null;
	}
}
