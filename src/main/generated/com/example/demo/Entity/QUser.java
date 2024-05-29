package com.example.demo.Entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 1801664316L;

    public static final QUser user = new QUser("user");

    public final NumberPath<Integer> career = createNumber("career", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> created_at = createDateTime("created_at", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> edited_at = createDateTime("edited_at", java.time.LocalDateTime.class);

    public final StringPath id = createString("id");

    public final DateTimePath<java.time.LocalDateTime> last_login_at = createDateTime("last_login_at", java.time.LocalDateTime.class);

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final SetPath<Project, QProject> projects = this.<Project, QProject>createSet("projects", Project.class, QProject.class, PathInits.DIRECT2);

    public final StringPath tel = createString("tel");

    public final StringPath token = createString("token");

    public final StringPath user_type = createString("user_type");

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

