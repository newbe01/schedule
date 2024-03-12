package com.sparta.schedule.repository;

import static com.sparta.schedule.domain.QComment.comment;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.schedule.domain.Comment;
import com.sparta.schedule.dto.comment.CommentResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class CommentRepositoryQueryImpl implements CommentRepositoryQuery {

    private final JPAQueryFactory factory;

    @Override
    public Page<CommentResponse> findAllComments(Long scheduleId, Pageable pageable) {
        List<Comment> query = factory.select(comment)
            .from(comment)
            .join(comment.schedule).fetchJoin()
            .where(comment.schedule.id.eq(scheduleId))
            .orderBy(comment.createdAt.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        List<CommentResponse> result = query.stream()
            .map(CommentResponse::new)
            .toList();

        return new PageImpl<>(result, pageable, query.size());
    }
}
