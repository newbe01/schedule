package com.sparta.schedule.repository;

import static com.sparta.schedule.domain.QSchedule.schedule;
import static com.sparta.schedule.domain.QUser.user;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
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


@RequiredArgsConstructor
public class ScheduleRepositoryQueryImpl implements ScheduleRepositoryQuery {

    private final JPAQueryFactory factory;

    @Override
    public Page<ScheduleListResponse> getAllSchedules(String titleCond, Pageable pageable) {
        List<Tuple> query = factory
            .select(user.username, schedule)
            .from(schedule)
            .leftJoin(schedule.user, user)
            .where(titleLike(titleCond))
            .orderBy(user.username.asc())
            .orderBy(schedule.createdAt.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        List<ScheduleListResponse> result = query.stream()
            .filter(t -> t.get(user.username) != null)
            .collect(Collectors.groupingBy(t -> t.get(user.username), LinkedHashMap::new,
                Collectors.toList()))
            .entrySet().stream()
            .map(entry -> new ScheduleListResponse(entry.getKey(),
                mapToScheduleList(entry.getValue())))
            .toList();

        return new PageImpl<>(result, pageable, query.size());
    }

    private BooleanExpression titleLike(String titleCond) {
        return Objects.nonNull(titleCond) ? schedule.title.contains(titleCond) : null;
    }

    private List<ScheduleResponse> mapToScheduleList(List<Tuple> tuples) {
        return tuples.stream()
            .map(tuple -> tuple.get(schedule)).filter(Objects::nonNull)
            .map(ScheduleResponse::new)
            .collect(Collectors.toList());
    }
}
