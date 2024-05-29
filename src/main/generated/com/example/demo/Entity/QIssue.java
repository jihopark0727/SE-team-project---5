package com.example.demo.Entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QIssue is a Querydsl query type for Issue
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QIssue extends EntityPathBase<Issue> {

    private static final long serialVersionUID = 5950344L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QIssue issue = new QIssue("issue");

    public final StringPath assigneeId = createString("assigneeId");

    public final StringPath description = createString("description");

    public final StringPath fixerId = createString("fixerId");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.util.Date> last_modified_time = createDateTime("last_modified_time", java.util.Date.class);

    public final StringPath priority = createString("priority");

    public final QProject project;

    public final DateTimePath<java.util.Date> reported_time = createDateTime("reported_time", java.util.Date.class);

    public final StringPath reporterId = createString("reporterId");

    public final StringPath status = createString("status");

    public final StringPath title = createString("title");

    public QIssue(String variable) {
        this(Issue.class, forVariable(variable), INITS);
    }

    public QIssue(Path<? extends Issue> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QIssue(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QIssue(PathMetadata metadata, PathInits inits) {
        this(Issue.class, metadata, inits);
    }

    public QIssue(Class<? extends Issue> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.project = inits.isInitialized("project") ? new QProject(forProperty("project")) : null;
    }

}

