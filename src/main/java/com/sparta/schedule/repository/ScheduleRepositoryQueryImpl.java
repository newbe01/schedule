package com.sparta.schedule.repository;

import static com.sparta.schedule.domain.QSchedule.schedule;
import static com.sparta.schedule.domain.QUser.*;

import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.schedule.domain.QUser;
import com.sparta.schedule.domain.Schedule;
import com.sparta.schedule.dto.schedule.ScheduleListResponse;
import com.sparta.schedule.dto.schedule.ScheduleResponse;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;


@RequiredArgsConstructor
public class ScheduleRepositoryQueryImpl implements ScheduleRepositoryQuery {

    private final JPAQueryFactory factory;

    @Override
    public Page<ScheduleListResponse> getAllSchedules(Pageable pageable) {
        List<Tuple> query = factory
            .select(user.username, schedule)
            .from(schedule)
            .leftJoin(schedule.user, user)
            .orderBy(user.username.asc())
            .orderBy(schedule.creatAt.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        List<ScheduleListResponse> result = query.stream()
            .filter(t -> t.get(user.username) != null)
            .collect(Collectors.groupingBy(t -> t.get(user.username), LinkedHashMap::new,
                Collectors.toList()))
            .entrySet().stream()
            .map(entry -> new ScheduleListResponse(entry.getKey(), mapToScheduleList(entry.getValue())))
            .toList();

        return new PageImpl<>(result, pageable, query.size());
    }

    private List<ScheduleResponse> mapToScheduleList(List<Tuple> tuples) {
        return tuples.stream()
            .map(tuple -> tuple.get(schedule)).filter(Objects::nonNull)
            .map(ScheduleResponse::new)
            .collect(Collectors.toList());
    }
}
